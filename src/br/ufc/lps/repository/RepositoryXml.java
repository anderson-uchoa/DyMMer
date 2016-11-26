package br.ufc.lps.repository;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RepositoryXml {
	
private OkHttpClient http;
	
	public RepositoryXml(OkHttpClient okHttpClient) {
		this.http = okHttpClient;
	}
	
	public String requestString(Request request){
		try {
			Response response = http.newCall(request).execute();
			String bodyString = response.body().string();
			response.close();
			return bodyString;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean requestBoolean(Request request){
		try {
			Response response = http.newCall(request).execute();
			response.close();
			return response.code()==200;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
