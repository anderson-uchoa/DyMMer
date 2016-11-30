package br.ufc.lps.view.trees.rnf;

import javax.swing.tree.DefaultMutableTreeNode;

public class NFP extends DefaultMutableTreeNode{
	private String padrao;
	
	public NFP(String name, String padrao) {
		super(name);
		this.padrao = padrao;
	}
	
	public NFP() {}
	
	public String getPadrao() {
		return padrao;
	}
	
	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}
}
