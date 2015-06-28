package br.ufc.lps.model;

import java.util.HashMap;
import java.util.Map;

import br.ufc.lps.model.xml.IXMLmodel;

public class ModelFactory {

	private static ModelFactory factory;
	private Map<Integer, IXMLmodel> models;
	
	private ModelFactory(){
		models = new HashMap<Integer, IXMLmodel>();
	}
	
	public static ModelFactory getInstance(){
		if(factory == null)
			factory = new ModelFactory();
		
		return factory;
	}
	
	public void registerModel(Integer id, IXMLmodel model){
		models.put(id, model);
	}
	
	public IXMLmodel createModel(Integer id, String pathModelFile){
		
		return models.get(id).getNewInstance(pathModelFile);
	}
	
}
