package br.ufc.lps.view.trees;import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import br.ufc.lps.view.trees.adaptation.Adaptacao;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeData;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeEditor;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeRenderer;
import br.ufc.lps.view.trees.adaptation.ValorAdaptacao;


public class Teste extends JPanel{
	
	//public static void main(String [] args){
		
//	}
	
	public Teste(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Adaptações de Contexto");

		final DefaultTreeModel treeModel = new DefaultTreeModel(root);
		
		JTree tree = new JTree(treeModel);
	   
		final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);

		final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
		tree.setCellEditor(editor);
		tree.setEditable(true);
	   
	  // tree.setBounds(501, 0, 500, 500);
	   
	   //JFrame j = new JFrame("Arvore");
	   //j.setSize(1200, 720);
	   
	  
	   //j.setLocationRelativeTo(null);
	   
	   add(tree);

	   JLabel imgLabel = new JLabel(new ImageIcon("arq/Legenda.png"));
	   imgLabel.setBounds(0, 0, 500, 500);
	   
	   add(imgLabel);
	   //j.add(imgLabel);
	   
	   //j.show();

		// listen for changes in the model (including check box toggles)
		treeModel.addTreeModelListener(new TreeModelListener() {

			@Override
			public void treeNodesChanged(final TreeModelEvent e) {
				TreePath currentSelection = tree.getSelectionPath();
				System.out.println("nodes changed "+e.getSource().toString());
				
				if (currentSelection != null) {   				
					DefaultMutableTreeNode tipoSelecionado = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
					if(tipoSelecionado instanceof ValorAdaptacao){
						Object ob = tipoSelecionado.getUserObject();
						if(ob instanceof CheckBoxNodeData){
							CheckBoxNodeData check = (CheckBoxNodeData) ob;
							if(check.isChecked()){
								DefaultMutableTreeNode pai = (DefaultMutableTreeNode) tipoSelecionado.getParent();
								for(int i=0; i<pai.getChildCount(); i++){
									if(!pai.getChildAt(i).equals(tipoSelecionado)){
										CheckBoxNodeData c = (CheckBoxNodeData) ((DefaultMutableTreeNode)pai.getChildAt(i)).getUserObject();
										c.setChecked(false);
									}
								}
								tree.updateUI();
							}
						}
					}
				}
			}

			@Override
			public void treeNodesInserted(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": nodes inserted");
			}

			@Override
			public void treeNodesRemoved(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": nodes removed");
			}

			@Override
			public void treeStructureChanged(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": structure changed");
			}
		});
	   
	   tree.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(node.getLevel() == 0){
					JPopupMenu menu = new JPopupMenu();
					menu.add(new JLabel("Opções de Adaptação:"));
					menu.addSeparator();
					JMenuItem adicionar = new JMenuItem("Adicionar");
					
					menu.add(adicionar);
					
					adicionar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							String adaptacao = JOptionPane.showInputDialog("Adicione o nome da adaptação");
							if(adaptacao!=null && !adaptacao.trim().isEmpty()){
								node.add(new Adaptacao(adaptacao));
								tree.updateUI();
							}
						}
					});
					
					menu.show(tree, e.getX(), e.getY());
					
				}else if(node.getLevel() == 1){
					JPopupMenu menu = new JPopupMenu("Valor");
					menu.add(new JLabel("Opções de Valor:"));
					menu.addSeparator();
					JMenuItem adicionar = new JMenuItem("Adicionar");
					
					menu.add(adicionar);
					
					adicionar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							String valor = JOptionPane.showInputDialog("Adicione o nome do valor");
							if(valor!=null && !valor.trim().isEmpty()){
								CheckBoxNodeData check = new CheckBoxNodeData(valor, false);
								node.add(new ValorAdaptacao(check));
								tree.updateUI();
							}
						}
					});
					
					menu.add(new JLabel("Opções de Adaptação:"));
					menu.addSeparator();
					JMenuItem remover = new JMenuItem("Remover");
					
					menu.add(remover);
					
					remover.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							node.removeFromParent();
							tree.updateUI();
						}
					});
					
					menu.show(tree, e.getX(), e.getY());
					
				}else{
					JPopupMenu menu = new JPopupMenu("Adaptação");
					menu.add(new JLabel("Opções de Valor:"));
					menu.addSeparator();
					JMenuItem adicionar = new JMenuItem("Remover");
					
					menu.add(adicionar);
					
					adicionar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							node.removeFromParent();
							tree.updateUI();
						}
					});
					
					menu.show(tree, e.getX(), e.getY());
				
				}
			}
			}
	});
	   
	}
}
