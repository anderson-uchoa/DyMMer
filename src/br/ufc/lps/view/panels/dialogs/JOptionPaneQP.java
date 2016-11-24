package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import br.ufc.lps.model.adaptation.ValueQuantification;
import br.ufc.lps.model.adaptation.ValueQuantificationPadrao;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeData;

public class JOptionPaneQP implements ActionListener{
		
		final String[] padraoStrings = {"Normal", "Baixo",  "Medio", "Alto", "Não se aplica"};
		final String[] tipoStrings = {"Normal", "Interval"};
		final String[] quantificadorStrings = {">", "<",  "<=",  ">=", "=", "<>"};
		final String[] quantificadorStringsNA = {"="};
		
		private String padraoSelecionado = "Normal";
		private boolean interval = false;
		
		private JDialog dialog;
		
		private JButton botaoOk;
		
		private JComboBox padrao, tipo, cbqts1, cbqts2;
		
		private JPanel panelQuantificadorTexto;
		private JPanel geral;
		
		private ValueQuantificationPadrao quantificationPadrao;
		private DefaultMutableTreeNode node;
		
		private JTextField tfs1, tfs2;
	
		private JTree tree;
		
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
	    	
	    	quantificationPadrao = new ValueQuantificationPadrao();
	    	
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
	    	forPadrao.add(new JLabel("Padrão"), BorderLayout.NORTH);
	    	forPadrao.add(padrao, BorderLayout.CENTER);
	    	padrao.addActionListener(this);
	    	
	    	JPanel forTipo = new JPanel(new BorderLayout());
	    	tipo = new JComboBox<>(tipoStrings);
	    	forTipo.add(new JLabel("Type Quantification"), BorderLayout.NORTH);
	    	forTipo.add(tipo, BorderLayout.CENTER);
	    	tipo.addActionListener(this);
	    	
	    	JPanel geral = new JPanel(new BorderLayout(20,20));
	    	geral.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	        panelQuantificadorTexto = new JPanel(new GridLayout(0, 1));
	        
	        JPanel botoes = new JPanel(new BorderLayout());
	        JPanel layoutB = new JPanel(new GridLayout(0, 1));
	        
	        layoutB.add(forPadrao);
	        layoutB.add(forTipo);
	        
	        botoes.add(layoutB, BorderLayout.NORTH);
	        
	        geral.add(botoes, BorderLayout.LINE_START);
	        JPanel p = new JPanel(new BorderLayout());
	        JPanel jp = new JPanel(new BorderLayout());
	        jp.add(botaoOk, BorderLayout.LINE_END);
	        p.add(jp, BorderLayout.NORTH);
	        p.add(panelQuantificadorTexto, BorderLayout.SOUTH);
	        geral.add(p ,BorderLayout.CENTER);
	        
	        panelQuantificadorTexto.add(createLayout());
	        
	        return geral;
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			 JComboBox cb = (JComboBox)e.getSource();
		     if(cb.equals(padrao)){
		    	 padraoSelecionado = (String)cb.getSelectedItem();
		    	 if(padraoSelecionado.equals("Não se aplica")){
		    		 DefaultComboBoxModel model = new DefaultComboBoxModel( quantificadorStringsNA );
		    		 cbqts1.setModel( model );
		    		 changeType(false);
		    		 tipo.setEnabled(false);
		    	 }else{
		    		 DefaultComboBoxModel model = new DefaultComboBoxModel( quantificadorStrings );
		    		 cbqts1.setModel( model );
		    		 tipo.setEnabled(true);
		    		 if(tipo.getSelectedItem().equals("Interval")){
		    			 changeType(true);
		    		 }
		    	 }
		     }else if(cb.equals(tipo)){
		    	 	String padraoSelecionado = (String)cb.getSelectedItem();
		    	 	if(padraoSelecionado.equals("Interval")){
		    	 		changeType(true);
		    	 	}else{
		    	 		changeType(false);	
		    	 	}
		     }
		}
		
		private void changeType(boolean t){
			tfs2.setEnabled(t);
			cbqts2.setEnabled(t);
			interval = t;
		}
		
		private JPanel createLayout(){
			
			cbqts1 = new JComboBox<>(quantificadorStrings);
			tfs1 = new JTextField();

			JPanel mos1 = new JPanel(new BorderLayout());

			mos1.add(cbqts1, BorderLayout.LINE_START);
			mos1.add(tfs1, BorderLayout.CENTER);

			cbqts2 = new JComboBox<>(quantificadorStrings);
			tfs2 = new JTextField();

			tfs2.setEnabled(false);
			cbqts2.setEnabled(false);

			JPanel mos2 = new JPanel(new BorderLayout());
			mos2.add(cbqts2, BorderLayout.LINE_START);
			mos2.add(tfs2, BorderLayout.CENTER);
			
			JPanel painelCompMos = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridx = 0;
			c.gridy = 0;
			painelCompMos.add(mos1, c);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.gridx = 1;
			c.gridy = 0;
			JLabel t = new JLabel("And");
			t.setHorizontalAlignment(JTextField.CENTER);
			painelCompMos.add(t, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridx = 2;
			c.gridy = 0;
			painelCompMos.add(mos2, c);
			
			JPanel panelFinal = new JPanel(new BorderLayout());
			
			panelFinal.add(painelCompMos, BorderLayout.CENTER);

			return panelFinal;
		}
		
		public ValueQuantification getDate(){
			quantificationPadrao.setPadrao(padraoSelecionado);
			quantificationPadrao.setIsInterval(interval);
			quantificationPadrao.setQuantification1(tfs1.getText());
			quantificationPadrao.setValueQuantification1(cbqts1.getSelectedItem().toString());
			if(interval){
				quantificationPadrao.setQuantification2(tfs2.getText());
				quantificationPadrao.setValueQuantification2(cbqts2.getSelectedItem().toString());
			}
			return quantificationPadrao;
		}
}
