package br.ufc.lps.gui.tree;

import javax.swing.ImageIcon;

import br.ufc.lps.contextaware.Context;
import br.ufc.lps.contextaware.Resolution;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tree;
import prefuse.data.parser.DataParseException;
import prefuse.data.parser.DataParser;
import prefuse.data.parser.ParserFactory;

public class FeaturesTreePerfuse {
	
	protected static Object parse(String s, Class type) throws DataParseException{
	    DataParser dp = ParserFactory.getDefaultFactory().getParser(type);
	    return dp.parse(s);
	}
		
	public static FeatureTreeNode readAllNodes(Context context, Tree tree, Node pai, FeatureTreeNode a){
		
		Table table  = tree.getNodeTable();
		
		if(pai==null){
			pai = tree.addNode();
			Object val;
			try {
				val = parse(a.getName(), table.getColumnType("name"));
				pai.set("name", val);
			} catch (DataParseException e) {
				e.printStackTrace();
			}
		}
		
		if(a.getChildCount() > 0){
			for (int i = 0; i < a.getChildCount(); i++) {
				FeatureTreeNode f = (FeatureTreeNode) a.getChildAt(i); 
				Node node = tree.addChild(pai);
				FeatureTreeNode fe = readAllNodes(context, tree, node, f);
		
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
					//node.set("ativa", fe.isActiveInContext());
					

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
					
				} catch (DataParseException e) {
					e.printStackTrace();
				}
				}
				
			}
			return a;
		}else{
			return a;
		}
	}
	
}
