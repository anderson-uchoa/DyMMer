package br.ufc.lps.view.trees;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.model.contextaware.Resolution;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;

public class FeaturesTreeCellRenderer extends DefaultTreeCellRenderer {

	private Icon activateFeatureIcon = new ImageIcon("images/activate.png");
	private Icon deactivateFeatureIcon = new ImageIcon("images/deactivate.png");
	private Icon normalFeatureIcon = new ImageIcon("images/normal.png");
	private Context context;
	
	
	private static final long serialVersionUID = 8897226446176762667L;

	public FeaturesTreeCellRenderer(Context context) {
		this.context = context;
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		
		FeatureTreeNode treeNode = (FeatureTreeNode) value;
		boolean status = true;
		boolean inContext = false;
		for(Resolution resolution : context.getResolutions()){
			if(resolution.getIdFeature().equalsIgnoreCase(treeNode.getID())){
				status = resolution.getStatus();
				inContext = true;
				break;
			}
		}
		
		if(!inContext){
			setIcon(normalFeatureIcon);
		}else{
			
			if (status) {
				setIcon(activateFeatureIcon);
			}
			else{
				setIcon(deactivateFeatureIcon);
			}
			
		}
		
		return this;
	}

	
}
