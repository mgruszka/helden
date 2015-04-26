package pl.protka.britanica;

import java.io.IOException;

public class BritanicaCrawler {

	
	public String getInfo(String url) throws IOException{
		
		HTTPRequestManager http = new HTTPRequestManager();		
		String rawData = http.doGetMethod(url);
			
		return rawData;
	}
	
	
	public static void main(String[] args) {
		
		BritanicaURLBuilder brit = new BritanicaURLBuilder();
		BritanicaCrawler cr = new BritanicaCrawler();
		
		String url = brit.getBritanicaURL("Leonardo+Da+Vinci");
		System.out.println("URL:" + url);
		
		try {
			String body = cr.getInfo(url);
			System.out.println(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
}
