package br.ufc.lps.view.trees;

import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.model.contextaware.Resolution;
import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tree;
import prefuse.data.parser.DataParseException;
import prefuse.data.parser.DataParser;
import prefuse.data.parser.ParserFactory;

public class FeaturesTreePrefuseControl {
	
	private HashMap<String, Node> listaDeNosArvore;
	private ParserFactory parser;
	boolean entrou = false;
	
	public FeaturesTreePrefuseControl() {
		listaDeNosArvore = new HashMap<>();
	    parser = ParserFactory.getDefaultFactory();
	}
	
	private Object parse(String s, Class type) throws DataParseException{
		DataParser dp = parser.getParser(type);
		return dp.parse(s);
	}
	
	public HashMap<String, Node> getLista(){
		return this.listaDeNosArvore;
	}
	
	public void bindRestrict(Tree tree, List<String> a){
		
		String id1 = "";
		String id2 = "";
		boolean primeiro = true;
		for (int i = 0; i < a.size(); i++) {
			if(primeiro)
				id1 = a.get(i);
			else
				id2 = a.get(i);
			
			primeiro = false;
		}
		
		if(listaDeNosArvore.containsKey(id1) && listaDeNosArvore.containsKey(id2)){
			//tree.addChildEdge(listaDeNosArvore.get(id1), listaDeNosArvore.get(id2));
			//listaDeNosArvore.get(id1).getDepth();
			tree.addEdge(listaDeNosArvore.get(id1), listaDeNosArvore.get(id2));
		}
		else{
			System.out.println("n entrou");
		}
	}
		
	public FeatureTreeNode getTree(Context context, Tree tree, Node pai, FeatureTreeNode a){
		
		Table table  = tree.getNodeTable();
		
		if(pai==null){
			pai = tree.addNode();
			Object val;
			try {
				val = parse(a.getName(), table.getColumnType("name"));
				pai.set("name", val);
				listaDeNosArvore.put(a.getID(), pai);
			} catch (DataParseException e) {
				e.printStackTrace();
			}
		}
		
		if(a.getChildCount() > 0){
			for (int i = 0; i < a.getChildCount(); i++) {
				FeatureTreeNode f = (FeatureTreeNode) a.getChildAt(i); 
			
				Node node = tree.addChild(pai);
				
				FeatureTreeNode fe = getTree(context, tree, node, f);
				if(fe!=null){
				Object val;
				try {
					
					boolean status = true;
					boolean inContext = false;
					for(Resolution resolution : context.getResolutions()){
						if(resolution.getIdFeature().equalsIgnoreCase(fe.getID())){
							status = resolution.getStatus();
							inContext = true;
							break;
						}
					}
					
					val = parse(fe.toString(), table.getColumnType("name"));
					node.set("name", val);
					
					listaDeNosArvore.put(fe.getID(), node);
					
					if (fe instanceof FeatureGroup) {

						if (((FeatureGroup) fe).getMax() == 1) {
							node.set("image", new ImageIcon("images/xor.png"));
						} else {
							node.set("image", new ImageIcon("images/or.png"));
						}
					}else{
						if(!inContext){
							node.set("image", new ImageIcon("images/normal.png"));
						}else{
							
							if (status) {
								node.set("image", new ImageIcon("images/activate.png"));
							}
							else{
								node.set("image", new ImageIcon("images/deactivate.png"));
							}
						}
					}
					
				} catch (DataParseException e) {
					e.printStackTrace();
				}
				}
				
			}
			listaDeNosArvore.put(a.getID(), pai);
			return a;
		}else{
			listaDeNosArvore.put(a.getID(), pai);
			return a;
		}
	}
	
}
