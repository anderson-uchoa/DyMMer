package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import br.ufc.lps.model.ValueQuantification;
import br.ufc.lps.model.ValueQuantificationBool;
import br.ufc.lps.model.ValueQuantificationPadrao;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeData;

public class JOptionPaneQB implements ActionListener{
	
		private String[] padraoStrings = {"True", "False"};
		private String[] tipoStrings = {"Não se aplica", " Se aplica"};
		
		private String padraoSelecionado = "True";
		private boolean seaplica = false;
		
		private JComboBox padrao, tipo;
		private JButton botaoOk;
		private JDialog dialog;

		private ValueQuantificationBool quantificationBool;
		
		private JPanel forTipo;
		private DefaultMutableTreeNode node;
		private JTree tree;
		
		private JTextField tfs1;
	
	 	public void displayGUI(JFrame frame, DefaultMutableTreeNode node, JTree tree) {
	 		 this.tree = tree;
	 		 this.node = node;
	 		 dialog = new JDialog(frame);
	         dialog.setTitle("Select Quantification");
	         dialog.setSize(new Dimension(700, 114));
	         dialog.setContentPane(getPanel());
	         dialog.setResizable(false);
	         dialog.setLocationRelativeTo(frame);
	         dialog.setVisible(true);
	    }

	    private JPanel getPanel() {
	    	quantificationBool = new ValueQuantificationBool();
	    	tfs1 = new JTextField();
	    	botaoOk = new JButton("Add");
	    	
	    	botaoOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					if(!getDate().isComplete()){
						JOptionPane.showMessageDialog(null, "Type the informations correctly");
						return;
					}
					
					CheckBoxNodeData check = new CheckBoxNodeData(getDate().toString(), false);
					check.setValueQuantification(getDate());
					node.add(new br.ufc.lps.view.trees.adaptation.ValorAdaptacao(check));
				
					tree.updateUI();
					
					dialog.dispose();
				}
			});
	    	
	    	JPanel forPadrao = new JPanel(new BorderLayout());
	    	padrao = new JComboBox<>(padraoStrings);
	    	forPadrao.add(new JLabel("Boolean"), BorderLayout.NORTH);
	    	forPadrao.add(padrao, BorderLayout.CENTER);
	    	padrao.addActionListener(this);
	    	
	    	forTipo = new JPanel(new BorderLayout());
	    	forTipo.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	    	tipo = new JComboBox<>(tipoStrings);
	    	forTipo.add(new JLabel("Quantificação"), BorderLayout.NORTH);
	    	forTipo.add(tipo, BorderLayout.CENTER);
	    	tipo.addActionListener(this);
	    	
	    	JPanel forAplica = new JPanel(new BorderLayout());
	    	forAplica.add(new JLabel(" "), BorderLayout.NORTH);
	    	forAplica.add(createLayout(), BorderLayout.CENTER);
	    	
	    	JPanel forButtonOk = new JPanel(new BorderLayout());
	    	forButtonOk.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	    	forButtonOk.add(new JLabel(" "), BorderLayout.NORTH);
	    	forButtonOk.add(botaoOk, BorderLayout.CENTER);
	    	
	    	JPanel geral = new JPanel(new BorderLayout(20,20));
	    	geral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        
	        JPanel botoes = new JPanel(new BorderLayout());
	        JPanel layoutB = new JPanel(new GridBagLayout());
	        
	    	GridBagConstraints c = new GridBagConstraints();

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.gridx = 0;
			c.gridy = 0;
	        
	        layoutB.add(forPadrao, c);
	        
	        c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.gridx = 1;
			c.gridy = 0;
			
	        layoutB.add(forTipo, c);
	        
	        c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.gridx = 2;
			c.gridy = 0;
			c.ipadx = 350;
			
	        layoutB.add(forAplica, c);
	        
	        c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.gridx = 3;
			c.gridy = 0;
			c.ipadx = 0;
			
	        layoutB.add(forButtonOk, c);
	        
	        botoes.add(layoutB, BorderLayout.NORTH);
	        
	        geral.add(botoes, BorderLayout.LINE_START);
	        
	        return geral;
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			 JComboBox cb = (JComboBox)e.getSource();
		     if(cb.equals(padrao)){
		    	 padraoSelecionado = (String)cb.getSelectedItem();
		     }else if(cb.equals(tipo)){
		    	 	String padraoSelecionado = (String)cb.getSelectedItem();
		    	 	if(cb.getSelectedIndex() == 1){
		    	 		changeType(true);
		    	 	}else{
		    	 		changeType(false);	
		    	 	}
		     }
		}
		
		private void changeType(boolean t){
			tfs1.setEnabled(t);
			seaplica = t;
		}
		
		private JPanel createLayout(){
			
			JPanel mos1 = new JPanel(new BorderLayout());

			JLabel l = new JLabel("=");
			l.setBorder(BorderFactory.createEmptyBorder(10, 10 ,10 , 10));
			mos1.add(l, BorderLayout.LINE_START);
			mos1.add(tfs1, BorderLayout.CENTER);

			changeType(false);

			return mos1;
		}
		
		public ValueQuantification getDate(){
			quantificationBool.setIsQuantification(seaplica);
			quantificationBool.setPadrao(padraoSelecionado);
			
			if(seaplica)
				quantificationBool.setValueQuantification(tfs1.getText());
			
			return quantificationBool;
		}
}
