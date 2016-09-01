package br.ufc.lps.repositorio;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientHttp {
	
	private OkHttpClient http;
	
	public ClientHttp(OkHttpClient okHttpClient) {
		this.http = okHttpClient;
	}
	
	public String get(Request request){
		try {
			Response response = http.newCall(request).execute();
			//System.out.println(response.code());
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean save(Request request){
		try {
			Response response = http.newCall(request).execute();
			//System.out.println(response.code());
			return response.code()==200;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
}
