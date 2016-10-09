package br.ufc.lps.repositorio;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RepositorioXml {
	
private OkHttpClient http;
	
	public RepositorioXml(OkHttpClient okHttpClient) {
		this.http = okHttpClient;
	}
	
	public String getStringBody(Request request){
		try {
			Response response = http.newCall(request).execute();
			System.out.println(response.code());
			String bodyString = response.body().string();
			response.close();
			return bodyString;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean code200(Request request){
		try {
			Response response = http.newCall(request).execute();
			System.out.println(response.code());
			response.close();
			boolean status = response.code()==200;
			return status;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
