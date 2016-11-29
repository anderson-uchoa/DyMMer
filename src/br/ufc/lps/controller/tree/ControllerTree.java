package br.ufc.lps.controller.tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.google.gson.Gson;

import br.ufc.lps.controller.json.JsonController;
import br.ufc.lps.model.MappingItensRnfObjecJson;

public class ControllerTree {
	
	public static DefaultMutableTreeNode getTreeItensRnfTree(){
		
		String json  = JsonController.getJSON("json/and.json");
		
		if(json==null)
			return null;
		
		Gson g = new Gson();
		
		MappingItensRnfObjecJson mappingItensRnfObjecJson = g.fromJson(json, MappingItensRnfObjecJson.class);
		
		return itensRnfToTreeNode(null, mappingItensRnfObjecJson);
	}
	
	public static MappingItensRnfObjecJson getMappingItensRnfObjectJson(){
		
		String json  = JsonController.getJSON("json/and.json");
		
		if(json==null)
			return null;
		
		Gson g = new Gson();
		
		return  g.fromJson(json, MappingItensRnfObjecJson.class);
	}
	
	private static DefaultMutableTreeNode itensRnfToTreeNode(DefaultMutableTreeNode root, MappingItensRnfObjecJson mappingItensRnfObjecJson){
		
		if(root == null)
			root = new DefaultMutableTreeNode(mappingItensRnfObjecJson.getName());
		
		if(mappingItensRnfObjecJson.getChildren()==null)
			return root;
		
		for(int i = 0; i < mappingItensRnfObjecJson.getChildren().size(); i++){
			DefaultMutableTreeNode filho = new DefaultMutableTreeNode(mappingItensRnfObjecJson.getChildren().get(i).getName());
			DefaultMutableTreeNode re = itensRnfToTreeNode(filho, mappingItensRnfObjecJson.getChildren().get(i));
			root.add(re);
		}
		
		return root;
	}
	
	public static List<String> getChildrenByLevel(int level, MappingItensRnfObjecJson root, String name){
		
		List<String> l = new ArrayList<>();
		
		System.out.println(level);
		System.out.println(name);
		
		if(level == 0){
			for(MappingItensRnfObjecJson ob : root.getChildren()){
				l.add(ob.getName());
			}
			return l;
		}else if(level == 1){
			for(MappingItensRnfObjecJson ob : root.getChildren()){
				if(ob.getName().equals(name)){
					for(MappingItensRnfObjecJson ob2 : ob.getChildren()){
						l.add(ob2.getName());
					}
					return l;
				}
			}
		}else{
			for(MappingItensRnfObjecJson ob : root.getChildren()){
				for(MappingItensRnfObjecJson ob2 : ob.getChildren()){
					if(ob2.getName().equals(name)){
						if(ob2.getChildren()!=null)
							for(MappingItensRnfObjecJson ob3 : ob2.getChildren()){
								l.add(ob3.getName());
							}
						return l;
					}
				}
			}
		}
		return l;
	}

	public static void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
}
