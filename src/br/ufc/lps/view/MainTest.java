package br.ufc.lps.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import br.ufc.lps.controller.tree.ControllerTree;

public class MainTest extends JFrame{
	
	public MainTest() {
		this.setVisible(true);
		JPanel j = new JPanel(new BorderLayout());
		DefaultTreeModel ft = new DefaultTreeModel(ControllerTree.getTreeItensRnfTree());
		JTree tr = new JTree(ft);
		JScrollPane s = new JScrollPane(tr);
		j.add(s, BorderLayout.CENTER);
		this.getContentPane().add(j);
	}
	
	public static void main(String[] args) {
		new MainTest();
	}
}
