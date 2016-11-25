package br.ufc.lps.view.trees.rnf;

import javax.swing.tree.DefaultMutableTreeNode;

public class PNFValue extends DefaultMutableTreeNode{
	private String padrao;
	
	public PNFValue(String name, String padrao) {
		super(name);
		this.padrao = padrao;
	}
	
	public PNFValue() {}
	
	public String getPadrao() {
		return padrao;
	}
	
	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}
}
