package pl.protka.britanica;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.mysql.fabric.xmlrpc.base.Array;

import pl.protka.db.CrawledSource;
import pl.protka.db.DatabaseDriver;
import pl.protka.db.PersonEntity;

public class BritanicaCrawler {

	DatabaseDriver dbdriver = DatabaseDriver.getInstance();
	
	public String getInfo(String url) throws IOException{
		
		HTTPRequestManager http = new HTTPRequestManager();		
		String rawData = http.doGetMethod(url);
			
		return rawData;
	}
	
	private String getInfoBox(String rawData){
		
		 Pattern p = Pattern.compile("<dl class=\"bio-data row\">(.|\n)*?</dl>");
		 Matcher m = p.matcher(rawData);
		 boolean exist = m.find();
		 if (exist){
			 return m.group(0);
		 }
		 else{
			 return null;
		 }
		
	}
	
	private String parseDate(String rawDate){
		
		String withoutSpaces = rawDate.replaceAll("\\s+", "");
		DateFormat df = new SimpleDateFormat("MMMMMdd,yyyy",Locale.US);
		String result = null;
		try {
			Date formated = df.parse(withoutSpaces);
			Calendar cal = Calendar.getInstance();
			cal.setTime(formated);
			result = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH);		
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		
		return result;
	}
	
	private void processDates(PersonEntity person, String infoBox){
		Pattern p = Pattern.compile("<p>(.|\n)*?</p>");
		Matcher m = p.matcher(infoBox);
		
		m.find();
		String rawBirthDate = m.group(0);
		m.find();
		//String rawBornPlace = m.group(0);	
		m.find();
		String rawDeathDate = m.group(0);
		m.find();
		//String rawDeathPlace = m.group(0);
		
		String birthDate = parseDate(rawBirthDate.replace("<p>","").replace("</p>", "").trim());
		String deathDate = parseDate(rawDeathDate.replace("<p>","").replace("</p>", "").trim());
		person.setBirthDate(birthDate);
		person.setDeathDate(deathDate);

	}
	
	
	private String getDateFromContent(String content,String type){
		
		Pattern p = Pattern.compile(type + " <span>(.|\n)*?</span>");
		 Matcher m = p.matcher(content);
		 boolean exist = m.find();
		 if (exist){
			 System.out.println(type + " date exists");
			 String result = m.group(0);
			 result = result.replaceAll("\\[.*\\]", "");			 
			 result = result.replace(type, "");
			 result = result.replace("<span>", "");
			 result = result.replace("</span>", "");
			 return result.trim();
		 }
		 else{
			 //System.out.println(type + " date does not exists");
			 return null;
		 }	
	}
	
	private void procesDatesFromContent(String name,String url,PersonEntity p){
		
		BritanicaContentManager mgr = new BritanicaContentManager();
		
		try {
			String content = mgr.getPersonContent(name,url);
			String born = getDateFromContent(content,"born");
			String died = getDateFromContent(content,"died");
			p.setBirthDate(parseDate(born));
			p.setDeathDate(parseDate(died));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void startCrawler(){
		
		List<String> people = dbdriver.getPeopleNotCrowled(CrawledSource.BRITANICA);
		
		//List<String> people = new ArrayList<String>();
		//people.add("Robert Boyle");
		//people.add("Isaac Newton");
		//people.add("Leonhard Euler");
		
		BritanicaURLBuilder brit = new BritanicaURLBuilder();
		
		for (String name : people){	
			try {
				System.out.println("Processing " + name);
				String url = brit.getBritanicaURL(name);
				String body = getInfo(url);
			
				String infoBox = getInfoBox(body);
				PersonEntity p = new PersonEntity();
				p.setName(name);
				p.setBritURL(url);
				if (infoBox != null){		
					System.out.println("Infobox exists");
					processDates(p,infoBox);				
				}
				else{
					System.out.println("Infobox does not exists");
					procesDatesFromContent(name,url,p);
				}
				
				dbdriver.savePerson(p);
			} catch (IOException e) {
				System.out.println("Unable to get URL data for: " + name);
				e.printStackTrace();
			}catch (JSONException e) {
				System.out.println("Unable to obtain URL sufix for: " + name);
				e.printStackTrace();				
			} 
		}
		
	}
	
	
	public static void main(String[] args) {
		
		BritanicaCrawler cr = new BritanicaCrawler();
		cr.startCrawler();
		
		
				
	}
	
}
