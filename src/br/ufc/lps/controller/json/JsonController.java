package br.ufc.lps.controller.json;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonController {
	
	public static String getJSON(String arquivo){
		JsonElement jsonObject;
   		JsonParser parser = new JsonParser();
   		try {
			jsonObject = parser.parse(new FileReader("html/"+arquivo));
			return jsonObject.toString();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
   		return null;
     }
}
