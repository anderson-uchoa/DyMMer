package br.ufc.lps.model.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufc.lps.model.ModelFactory;
import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureModelException;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.fm.GroupedFeature;
import br.ufc.lps.splar.core.fm.RootNode;
import br.ufc.lps.splar.core.fm.SolitaireFeature;
import br.ufc.lps.splar.core.fm.TreeNodeRendererFactory;

public class XMLFamiliarModel extends FeatureModel implements IXMLmodel{

	private String fileName;
	private int idCounter;
	private int idCreationStrategy;
	
		
	static{
		ModelFactory.getInstance().registerModel(ModelID.FAMILIAR_MODEL.getId(), new XMLFamiliarModel());
	}
	
	public XMLFamiliarModel() {}
	
	private XMLFamiliarModel(String pathModelFile) {
		this.fileName = pathModelFile;
		System.out.println(fileName);
	}
	
	@Override
	protected FeatureTreeNode createNodes() throws FeatureModelException {
		FeatureTreeNode rootNode = null;
		
		idCounter = 0;
		// if model is already loaded just return the feature model root node 
		if ( getRoot() != null ) {
			rootNode = getRoot();
		}
		// otherwise, load it from the XML feature model file
		else {			
			//	get the factory
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				//Using factory get an instance of document builder
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				File file = new File(fileName);
				
				//parse using builder to get DOM representation of the XML file
				Document doc = db.parse(fileName);
				
				//	get the root element
				Element rootEle = doc.getDocumentElement();
							
				String featureModelName = file.getName();
				
				if ( featureModelName == null || featureModelName.equals("") ) {
					throw new FeatureModelException("Missing mandatory feature model name.");
				}
				
				setName(featureModelName);
				
				
				rootNode = parseFeatureTree(rootEle);
				
								
			}
			catch(FeatureModelException e) {
				throw e;
			}
			catch(ParserConfigurationException pce) {
				throw new FeatureModelException("XML error parsing SXFM file: " + pce.getMessage(), pce);
			}
			catch(SAXException se) {
				throw new FeatureModelException("XML SAX error parsing SXFM file: " + se.getMessage() , se);
			}
			catch(IOException ioe) {
				throw new FeatureModelException("Error reading SXFM file: " + ioe.getMessage(), ioe);
			}
		}
		return rootNode;
	}
	
	protected FeatureTreeNode parseFeatureTree( Element featureTree ) throws IOException, FeatureModelException {
		FeatureTreeNode rootNode = null;
		Stack<FeatureTreeNode> nodeStack = new Stack<FeatureTreeNode>();
		
				
		List<FeatureTreeNode> processedNodes = new ArrayList<FeatureTreeNode>();
		
		NodeList branches = featureTree.getElementsByTagName("branch");
		
		Node branchRoot = branches.item(0);
		NodeList attributes = branchRoot.getChildNodes();
		String name = attributes.item(1).getAttributes().getNamedItem("value").getTextContent();
		
		rootNode = new RootNode(name, name, TreeNodeRendererFactory.createRootRenderer());
		
		nodesMap.put(name, rootNode);
		
		for(int i = 1; i < branches.getLength(); i++){
			parseNode(branches.item(i));
		}
		
		NodeList leaves = featureTree.getElementsByTagName("leaf");
		
		for(int i = 0; i < leaves.getLength(); i++){
			parseNode(leaves.item(i));
		}
		
		return rootNode;
	}
	
	private void parseNode(Node node) throws FeatureModelException {
		
		FeatureTreeNode featureNode = null;
		NodeList attributes = node.getChildNodes();
		
		String name = attributes.item(1).getAttributes().getNamedItem("value").getTextContent();
		String solitary = attributes.item(3).getAttributes().getNamedItem("value").getTextContent();
		String group = attributes.item(5).getAttributes().getNamedItem("value").getTextContent();
		
		
		
		if(solitary.equals("32")){
				
			createConstraint(node);
		
		}else if(!group.equals("32")){
				
			if(group.equals("8")){
				
				featureNode = new FeatureGroup(name, name, 1, -1, TreeNodeRendererFactory.createFeatureGroupRenderer());
			}else if(group.equals("16")){
				
				featureNode = new FeatureGroup(name, name, 1, 1, TreeNodeRendererFactory.createFeatureGroupRenderer());
			}else if(solitary.equals("1")){
				
				featureNode = new SolitaireFeature(true, name, name, TreeNodeRendererFactory.createFeatureGroupRenderer());
			}else{
				
				Node parent = node.getParentNode();
				String groupParent = parent.getChildNodes().item(5).getAttributes().getNamedItem("value").getTextContent();
				
				if(groupParent.equals("8") || groupParent.equals("16")){
					
					featureNode = new GroupedFeature(name, name, TreeNodeRendererFactory.createFeatureGroupRenderer());
				}else{
					featureNode = new SolitaireFeature(false, name, name, TreeNodeRendererFactory.createFeatureGroupRenderer());
					
				}	
			}
			
			nodesMap.put(name, featureNode);
			
			//Adiciona a feature ao pai
			Node parent = node.getParentNode();
			String nameParent = parent.getChildNodes().item(1).getAttributes().getNamedItem("value").getTextContent();
			
			//System.out.println(nameParent + " -> " + name);
			
			FeatureTreeNode parentTree = nodesMap.get(nameParent);
			if(parentTree != null)
				parentTree.add(featureNode);

		}
	
	}
	

	private void createConstraint(Node node) {
		
		NodeList attributes = node.getChildNodes();
		String name = attributes.item(1).getAttributes().getNamedItem("value").getTextContent();
		
		//Elimina os parenteses
		
		
	}

	@Override
	protected void saveNodes() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public IXMLmodel getNewInstance(String pathFile) {
		
		return new XMLFamiliarModel(pathFile);
	}

}
