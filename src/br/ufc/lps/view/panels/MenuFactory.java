package br.ufc.lps.view.panels;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import br.ufc.lps.controller.features.TypeFeature;
import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.fm.RootNode;
import br.ufc.lps.splar.core.fm.SolitaireFeature;
import br.ufc.lps.view.panels.command.CommandSetActiveNode;
import br.ufc.lps.view.trees.TypeTree;

public class MenuFactory {
	
	private static EditorPanel editorPanel;
	private static FeatureTreeNode featureTreeNode;
	private static MenuFactory instancia;
	
	public static MenuFactory getIntance(FeatureTreeNode feature) {
		featureTreeNode = feature;
		if (instancia == null){
			instancia = new MenuFactory(editorPanel, featureTreeNode);
		}
			return instancia;
		}

	
	
	
	public MenuFactory(EditorPanel editorPanel, FeatureTreeNode featureTreeNode) {
		this.editorPanel = editorPanel;
		this.featureTreeNode = featureTreeNode;
		
	}
	
	
	
	public JPopupMenu verificarMenuDeSelecao (TypeFeature typeFeature){
		JPopupMenu menu = new JPopupMenu();
		
		switch (typeFeature) {
		
		case ROOT:
			
			JMenuItem setActive = new JMenuItem("Set as active node");
		
			
			
			setActive.addActionListener(new CommandSetActiveNode(featureTreeNode, editorPanel));
			
			menu.add(setActive);
			
		case GROUP_XOR:
		case GROUP_OR:
		case MANDATORY:
		default:
		}
		
		return menu;
	

}

}
