package pl.protka.britanica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//http://www.britannica.com/search/ajax/autocomplete?nb=5&query=Leonardo+Da+Vinci

public class BritanicaURLBuilder {

	
	private String queryURL = "http://www.britannica.com/search/ajax/autocomplete?nb=5&query=";

	
	public String getBritanicaURL(String name){
				
		String url = null;		
		
		HTTPRequestManager http = new HTTPRequestManager();		
		try {
			String peparedQueryURL = queryURL + name.trim().replaceAll(" ", "+");			
			String urlData = http.doGetMethod(peparedQueryURL);
			String sufix = parseURLsufix(urlData);
			url = "http://www.britannica.com" + sufix;
			
		} catch (JSONException e) {
			System.out.println("Unable to obtain URL sufix for: " + name);
			e.printStackTrace();				
		} catch (Exception e) {
			System.out.println("Unable to get URL data for: " + name);
			e.printStackTrace();
		}
		return url;
			
	}
	
	
	private String parseURLsufix(String json) throws JSONException{
		JSONArray array = new JSONArray(json);
		JSONObject fieldsJson = (JSONObject) array.get(0);	
		String ala = fieldsJson.getString("url");
		return ala;
	}
		
	
}
