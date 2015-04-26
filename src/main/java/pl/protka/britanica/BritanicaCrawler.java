package pl.protka.britanica;

import java.io.IOException;

public class BritanicaCrawler {

	
	public String getInfo(String url) throws IOException{
		
		HTTPRequestManager http = new HTTPRequestManager();		
		String rawData = http.doGetMethod(url);
			
		return rawData;
	}
	
}
