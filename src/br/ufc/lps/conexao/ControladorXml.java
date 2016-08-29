package br.ufc.lps.conexao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import br.ufc.lps.conexao.constantes.Conexao;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ControladorXml{
	
	private RepositorioXml repositorioXml;
	private Gson gson;
	private static ControladorXml controladorXml;
	
	public ControladorXml() {
		this.repositorioXml = new RepositorioXml(new OkHttpClient());
		this.gson = new Gson();
	}
	
	public static ControladorXml getInstance(){
		if(controladorXml!=null)
			return controladorXml;
		
		return controladorXml = new ControladorXml();
	}
	
	public List<SchemeXml> get(){
		
		Request request = new Request.Builder()
			      .url(Conexao.url)
			      .build();
		
		String resultado = repositorioXml.get(request);
		
		if(resultado==null)
			return null;
		
		Type listType = new TypeToken<ArrayList<SchemeXml>>(){}.getType();
		
		return gson.fromJson(resultado, listType);
	}
	
	public boolean save(SchemeXml xml){
		
		RequestBody requestBody;
		FormBody.Builder builder = new FormBody.Builder()
				.add("xml", xml.getXml());
		
		if(xml.get_id()!=null)
				builder.add("id", xml.get_id())
				.build();
		
		requestBody = builder.build();
		
		Request request = new Request.Builder()
		    .url(Conexao.url)
		    .post(requestBody)
		    .build();
		
		return repositorioXml.save(request);
	} 
	
	private String schemeXmlToJson(SchemeXml schemeXml){
		return gson.toJson(schemeXml);
	}
	
	public static File createFileFromXml(String xml){
		
		String myRandom = UUID.randomUUID().toString();
		
		try {
			File file = File.createTempFile("feature"+myRandom, ".xml");
			   
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		    bw.write(xml);
		    bw.close();
		    
		    return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String createXmlFromFile(File file){
		
		try {
			
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(file));//new File(filename)));
			String line;

			while((line=br.readLine())!= null){
			    sb.append(line);
			    sb.append("\n");
			}
			
			return sb.toString().trim();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
