package br.ufc.lps.view.export;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import br.ufc.lps.model.contextaware.Resolution;

public class WriteXMLmodel {
		 
	 
	    public static Node getContext(Document doc, String name, List<Resolution> resolutions, List<String> constraints) {
	        Element context = doc.createElement("context");
	        context.setAttribute("name", name);
	        
	        for(Resolution resolution : resolutions)
	        	context.appendChild(getResolution(	doc, context, resolution.getNameFeature(), resolution.getIdFeature(), resolution.getStatus()));
	        
	        context.appendChild(getConstraint(doc, context, constraints));
	        
	        return context;
	    }
	 
	   
	    public static Node getResolution(Document doc, Element element, String name, String id, boolean status) {
	        Element node = doc.createElement("resolution");
	        node.setAttribute("feature", name);
	        node.setAttribute("id", id);
	        node.setAttribute("status", status ? "true" : "false");
	        
	        return node;
	    }
	    
	    public static Node getConstraint(Document doc, Element element, List<String> constraints){
	    	
	    	Element node = doc.createElement("constraints");
	    	for(String constraint : constraints){
	    		Element elemConstraint = doc.createElement("constraint");
	    		elemConstraint.setTextContent(constraint);
	    		node.appendChild(elemConstraint);
	    	}
	    		
	    	
	    	return node;
	    }
	 
	
	
}
