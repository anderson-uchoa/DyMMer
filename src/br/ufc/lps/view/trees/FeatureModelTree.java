package br.ufc.lps.view.trees;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import br.ufc.lps.splar.core.fm.FeatureTreeNode;

public class FeatureModelTree implements TreeModel {

	private FeatureTreeNode root;
	
	public FeatureModelTree(FeatureTreeNode root) {
		this.root = root;
	}
	
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getChild(Object parent, int index) {
		
		return ((FeatureTreeNode)parent).getChildAt(index);
		
	}

	@Override
	public int getChildCount(Object parent) {
		
		return ((FeatureTreeNode)parent).getChildCount();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		
		return ((FeatureTreeNode)parent).getIndex((TreeNode) child);
	}

	@Override
	public Object getRoot() {
		
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		
		return ((FeatureTreeNode)node).isLeaf();
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
	}
	
}
