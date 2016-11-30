package br.ufc.lps.view.trees;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.view.trees.rnf.Characteristic;
import br.ufc.lps.view.trees.rnf.NFP;
import br.ufc.lps.view.trees.rnf.SubCharacteristic;

public class FeaturesRnfTreeCellRenderer extends DefaultTreeCellRenderer {

	private Icon qc = new ImageIcon("images/qc.png");
	private Icon nfp = new ImageIcon("images/nfp.png");
	private Icon scq = new ImageIcon("images/scq.png");
	private Icon normal = new ImageIcon("images/normal.png");

	private static final long serialVersionUID = 8897226446176762667L;

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
		
		if (treeNode instanceof Characteristic)
			setIcon(qc);
		else if(treeNode instanceof SubCharacteristic)
			setIcon(scq);
		else if(treeNode instanceof NFP)
			setIcon(nfp);
		else
			setIcon(normal);

		return this;
	}

}
