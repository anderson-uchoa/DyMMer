package br.ufc.lps.view.panels.command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.view.panels.EditorPanel;



public class CommandSetActiveNode implements ActionListener{

	
	private FeatureTreeNode featureTreeNode;
	private EditorPanel editorPanel;
	
	public CommandSetActiveNode( FeatureTreeNode selectedNode, EditorPanel editorPanel) {
		
		this.featureTreeNode = selectedNode;
		this.editorPanel = editorPanel;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(editorPanel.isFeatureGroup(featureTreeNode))
			return;
		
		editorPanel.changeStatusFeature(true, "Selected Feature is already activated");
	}
	}
		

