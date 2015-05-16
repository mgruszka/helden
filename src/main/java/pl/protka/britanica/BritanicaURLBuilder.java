package pl.protka.britanica;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//http://www.britannica.com/search/ajax/autocomplete?nb=5&query=Leonardo+Da+Vinci

public class BritanicaURLBuilder {

	
	private String queryURL = "http://www.britannica.com/search/ajax/autocomplete?nb=5&query=";

	
	public String getBritanicaURL(String name) throws JSONException, IOException{
				
		String url = null;				
		HTTPRequestManager http = new HTTPRequestManager();		
		String peparedQueryURL = queryURL + name.trim().replaceAll(" ", "+");		
		System.out.println(peparedQueryURL);
		String urlData = http.doGetMethod(peparedQueryURL);
		String sufix = parseURLsufix(urlData);
		if (sufix != null)
			url = "http://www.britannica.com" + sufix;
		return url;
			
	}
	
	
	private String parseURLsufix(String json) throws JSONException{
		JSONArray array = new JSONArray(json);
		String result = null;
		if (array.length() != 0){
			JSONObject fieldsJson = (JSONObject) array.get(0);	
			result = fieldsJson.getString("url");
		}	
		return result;
	}
		
	
}
