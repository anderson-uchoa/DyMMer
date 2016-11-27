package br.ufc.lps.view.panels.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.teamdev.jxbrowser.chromium.bo;

import br.ufc.lps.controller.tree.ControllerTree;
import br.ufc.lps.model.MappingItensRnfObjecJson;
import br.ufc.lps.model.adaptation.ValueQuantificationBool;
import br.ufc.lps.view.list.ConstraintsListModelItensRnfs;
import br.ufc.lps.view.trees.rnf.PNFValue;

public class JOptionPaneListItensRnfs{
	
		private JList<String> itens;
		private JButton botaoOk;
		private JDialog dialog;
		private final String[] padraoStrings = {"Normal", "Low",  "Medium", "High"};
		private String padraoSelecionado = "Normal";
		private JCheckBox check;
		private JComboBox<String> listaPadrao;

		private DefaultMutableTreeNode node;
		private String textoSelecionado;
		private JTree tree;
		
		private int nivel;
		
		private JTextField novoItem;
	
	 	public void displayGUI(JFrame frame, DefaultMutableTreeNode node, JTree tree, boolean lastNivel, String name) {

	 		 this.tree = tree;
	 		 this.node = node;
	 		 this.nivel = node.getLevel();
	 		 this.listaPadrao = new JComboBox<>(padraoStrings);
	 		 
	 		 listaPadrao.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					padraoSelecionado = (String) ((JComboBox)e.getSource()).getSelectedItem();
				}
			});
	 		 
	 		 MappingItensRnfObjecJson node2 = ControllerTree.getMappingItensRnfObjectJson();
	 		 
	 		 ConstraintsListModelItensRnfs a = new ConstraintsListModelItensRnfs(ControllerTree.getChildrenByLevel(node.getLevel(), node2, node.toString()));
	 		 
	 		 itens = new JList<>(a);
	 		 itens.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					textoSelecionado = itens.getSelectedValue();
				}
			});
	 		 
	 		 dialog = new JDialog(frame);
	         dialog.setTitle(name);
	         dialog.setSize(new Dimension(300, 400));
	         dialog.setContentPane(getPanel(lastNivel));
	         dialog.setResizable(false);
	         dialog.setLocationRelativeTo(frame);
	         dialog.setVisible(true);
	    }

	    private JPanel getPanel(boolean lastNivel) {

	    	novoItem = new JTextField();
	    	botaoOk = new JButton("Add");
	    	check = new JCheckBox("New", false);
	    	setStatusTxt(false);
	    	
	    	check.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setStatusTxt(check.isSelected());
				}
			});
	    	
	    	botaoOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(!lastNivel){
						if(!verifyTextSelected())
	    					return;
						node.add(new PNFValue(textoSelecionado, padraoSelecionado));
					}else{
						if(check.isSelected()){
							if(!verifyTextChecked())
								return;
							node.add(new PNFValue(novoItem.getText().trim(), padraoSelecionado));
						}else{
							if(!verifyTextSelected())
								return;
							node.add(new PNFValue(textoSelecionado, padraoSelecionado));
						}
					}
					dialog.dispose();
					tree.updateUI();
				}
			});
	    	
	    	JPanel geral = new JPanel(new BorderLayout());
	    	geral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    	JScrollPane sc = new JScrollPane(itens);
	    	geral.add(sc, BorderLayout.CENTER);
	    	
	    	JPanel painelSouth = new JPanel(new BorderLayout());
	    	painelSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    	if(lastNivel){
	    		painelSouth.add(listaPadrao, BorderLayout.NORTH);
		    	painelSouth.add(check, BorderLayout.LINE_START);
		    	painelSouth.add(novoItem, BorderLayout.CENTER);
		    	painelSouth.add(botaoOk, BorderLayout.LINE_END);
	    	}else{
	    		painelSouth.add(botaoOk, BorderLayout.CENTER);
	    	}
	    	geral.add(painelSouth, BorderLayout.SOUTH);
	        
	    	return geral;
	    }
	    
	    private boolean verifyTextSelected(){
	    	if(textoSelecionado == null || textoSelecionado.trim().isEmpty()){
				JOptionPane.showMessageDialog(null, "Select the item");
				return false;
			}
	    	
	    	return true;
	    }
	    
	    private boolean verifyTextChecked(){
	    	if(novoItem.getText().trim().isEmpty()){
				JOptionPane.showMessageDialog(null, "Type the text");
				return false;
			}
	    	
	    	return true;
	    }
	    
	    private void setStatusTxt(boolean status){
	    	novoItem.setEnabled(status);
	    }
	    
	    public static void main(String[] args) {
			new JOptionPaneListItensRnfs().displayGUI(null, null, null, false, "Type the characteristics name:");
		}
		
}