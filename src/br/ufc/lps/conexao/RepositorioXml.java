package br.ufc.lps.conexao;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RepositorioXml {
	
private OkHttpClient http;
	
	public RepositorioXml(OkHttpClient okHttpClient) {
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
