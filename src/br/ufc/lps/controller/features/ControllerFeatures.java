package br.ufc.lps.controller.features;

import java.util.Random;

import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.fm.RootNode;
import br.ufc.lps.splar.core.fm.SolitaireFeature;

public class ControllerFeatures {

	private FeatureTreeNode tree;

	public ControllerFeatures(FeatureTreeNode tree) {
		this.tree = tree;
	}

	public boolean addFeatures(int position, TypeFeature typeFeatures, String name) {
		try {
			FeatureTreeNode featureSelecionada = (FeatureTreeNode) this.tree.getChildAt(position);
			featureSelecionada.add(getTypeFeature(typeFeatures, name));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private FeatureTreeNode getTypeFeature(TypeFeature typeFeatures, String name) {

		Random r = new Random();
		int id = r.nextInt(999999999);

		switch (typeFeatures) {
		case GROUP_OR:
			return new FeatureGroup(id + "", name, 1, -1, null);
		case GROUP_XOR:
			return new FeatureGroup(id + "", name, 1, 1, null);
		case OPTIONAL:
			return new SolitaireFeature(true, id + "", name, null);
		case MANDATORY:
			return new SolitaireFeature(false, id + "", name, null);
		default:
			return new RootNode(id + "", name, null);
		}
	}
}
