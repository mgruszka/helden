package pl.protka.britanica;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.json.JSONException;

public class BritanicaContentManager {

	HTTPRequestManager http = new HTTPRequestManager();
	
	private String getTopicSection(String rawData){
		 
		String result = "";
		String rest = null;
		
		int index = rawData.indexOf("class=\"eb-topic-section\"");
		
		if (index != -1){
			String tmp = rawData.substring(index);
			tmp = tmp.substring(tmp.indexOf(">") + 1);
			result = tmp.substring(0,tmp.indexOf("</section>"));
			rest = tmp.substring(tmp.indexOf("</section>"));			
			result = result + '\n' + getTopicSection(rest);
		}

		return result;
	}
	
	public String getPersonContent(String name,String url) throws IOException{
		
		String content = null;
		String rawText = http.doGetMethod(url);
		content = getTopicSection(rawText);
		return content;
	}
	
	
	public static void main(String[] args) {
		BritanicaURLBuilder builder = new BritanicaURLBuilder();
		BritanicaContentManager mgr = new BritanicaContentManager();
		
		
		String name = "Isaac Newton";
		String url;
		try {
			url = builder.getBritanicaURL(name);
			System.out.println("URL: " + url);
			String content = mgr.getPersonContent(name,url);
			
			/*try (PrintStream out = new PrintStream(new FileOutputStream("euler.html"))) {
			    out.print(content);
			}*/
			
			
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
}
