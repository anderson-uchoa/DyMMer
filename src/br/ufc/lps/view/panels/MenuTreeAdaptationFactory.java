package br.ufc.lps.view.panels;

import javax.swing.JPopupMenu;

import br.ufc.lps.controller.features.TypeFeature;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;

public class MenuTreeAdaptationFactory {

	private static EditorPanel editorPanel;
	private static FeatureTreeNode featureTreeNode;
	private static MenuTreeAdaptationFactory instancia;
	private static JPopupMenu jPopupMenu;

	public static MenuTreeAdaptationFactory getIntance(EditorPanel editor, FeatureTreeNode feature) {
		featureTreeNode = feature;
		editorPanel = editor;

		if (instancia == null) {
			instancia = new MenuTreeAdaptationFactory(editorPanel, featureTreeNode);
		}
		return instancia;
	}

	public MenuTreeAdaptationFactory(EditorPanel editorPanel, FeatureTreeNode featureTreeNode) {

		this.editorPanel = editorPanel;
		this.featureTreeNode = featureTreeNode;

	}

	
	

	}


