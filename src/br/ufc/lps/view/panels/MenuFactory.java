package br.ufc.lps.view.panels;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.ufc.lps.controller.features.TypeFeature;
import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.fm.RootNode;
import br.ufc.lps.splar.core.fm.SolitaireFeature;

import br.ufc.lps.view.trees.TypeTree;

public class MenuFactory {
	
	private static EditorPanel editorPanel;
	private static FeatureTreeNode featureTreeNode;
	private static MenuFactory instancia;
	private static JPopupMenu jPopupMenu;
	
	
	
	public static MenuFactory getIntance(EditorPanel editor, FeatureTreeNode feature) {
		featureTreeNode = feature;
		editorPanel = editor;
	
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

		switch (typeFeature) {
		
		case ROOT:
		break;
		case GROUP_XOR:
			break;
		case GROUP_OR:
			break;
		case MANDATORY:
		 return showMenuMandatory();
	
		default:
		
		}
	return null;

		
	

}
	
	public static JPopupMenu getjPopupMenu() {
		return jPopupMenu;
	
	}
	
	
	public JPopupMenu showMenuMandatory(){
	
		JPopupMenu menu = new JPopupMenu();

		JSeparator separator = new JSeparator();
		
		JMenuItem setActive = new JMenuItem("Set as active node");
		JMenuItem setDeactive = new JMenuItem("Set as deactive node");

		JMenuItem takeOffContext = new JMenuItem("Take it off from context");
		JMenuItem addConstraintPositive = new JMenuItem("Add to Constraint as Positive");
		JMenuItem addConstraintNegative = new JMenuItem("Add to Constraint as Negative");
		
		JMenuItem addOptionalFeature = new JMenuItem("Add a Optional Feature");
		JMenuItem addMandatoryFeature = new JMenuItem("Add a Mandatory Feature");
		JMenuItem addXORGroup = new JMenuItem("Add a XOR Group");	
		JMenuItem addORGroup = new JMenuItem("Add a OR Group");	
		JMenuItem remove = new JMenuItem("Remove");	
		
		setActive.setActionCommand("setActive");
		setDeactive.setActionCommand("setDeactive");
		takeOffContext.setActionCommand("takeOffContext");
		addConstraintPositive.setActionCommand("addConstraintPositive");
		addConstraintNegative.setActionCommand("addConstraintNegative");
		
		addOptionalFeature.setActionCommand("addOptionalFeature");
		addMandatoryFeature.setActionCommand("addMandatoryFeature");
		addXORGroup.setActionCommand("addXORGroup");	
		addORGroup.setActionCommand("addORGroup");	
		remove.setActionCommand("remove");	
		
		setActive.addActionListener(editorPanel);
		setDeactive.addActionListener(editorPanel);
		takeOffContext.addActionListener(editorPanel);
		addConstraintPositive.addActionListener(editorPanel);
		addConstraintNegative.addActionListener(editorPanel);

		addOptionalFeature.addActionListener(editorPanel);
		addMandatoryFeature.addActionListener(editorPanel);
		addXORGroup.addActionListener(editorPanel);
		addORGroup.addActionListener(editorPanel);	
		remove.addActionListener(editorPanel);	
		
	

		menu.add(setActive);
		menu.add(setDeactive);
		menu.add(takeOffContext);
	
		menu.add(separator);
		menu.add(addOptionalFeature);
		menu.add(addMandatoryFeature);
		menu.add(addXORGroup);
		menu.add(addORGroup);
		menu.add(remove);

		menu.add(addConstraintNegative);
		menu.add(addConstraintPositive);
		
		return menu;
		
		
		
	}
	
	
}
