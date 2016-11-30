package br.ufc.lps.view.panels.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.ufc.lps.repository.SchemeXml;
import br.ufc.lps.view.Main.listItens;
import br.ufc.lps.view.list.ConstraintsListModelItensSelectOrderModelVersion;

public class JOptionPaneListItensSelectOrderModelsVersion{
	
		private JList<SchemeXml> itens;
		private JButton botaoUp;
		private JButton botaoDown;
		private JButton botaoOk;
		private JDialog dialog;
		private boolean itsOK = false;
		private listItens ready;

		private ConstraintsListModelItensSelectOrderModelVersion model;
		
	 	public void displayGUI(JFrame frame, List<SchemeXml> lista, listItens ready) {

	 		 this.ready = ready;
	 		 
	 		 model = new ConstraintsListModelItensSelectOrderModelVersion(lista);
	 		 
	 		 itens = new JList<>(model);
		 	 itens.setCellRenderer(new DefaultListCellRenderer() {
		 		 @Override
		 		 public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	 		        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	 		        SchemeXml xml = (SchemeXml) value;
	 		        label.setText(xml.getNameXml());
	 		        return label;
		 		  }
		 	 });

		 	 dialog = new JDialog(frame);
	         dialog.setTitle("Order Itens by Version (Lowest to highest)");
	         dialog.setSize(new Dimension(300, 400));
	         dialog.setContentPane(getPanel());
	         dialog.setResizable(false);
	         dialog.setLocationRelativeTo(frame);
	         dialog.setVisible(true);
	    }
	 	
	    private JPanel getPanel() {

	    	botaoUp = new JButton("Up");
	    	
	    	botaoUp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				    int moveMe = itens.getSelectedIndex();

				    if (moveMe != 0) {
	                    swap(moveMe, moveMe - 1);
	                    itens.setSelectedIndex(moveMe - 1);
	                    itens.ensureIndexIsVisible(moveMe - 1);
	                }
				}
			});
	    	
	    	botaoDown = new JButton("Down");

	    	botaoDown.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				   int moveMe = itens.getSelectedIndex();
				   if (moveMe != model.getSize() - 1) {
	                    swap(moveMe, moveMe + 1);
	                    itens.setSelectedIndex(moveMe + 1);
	                    itens.ensureIndexIsVisible(moveMe + 1);
	                }
				}
			});
	    	

	    	botaoOk = new JButton("Ok");
	    	
	    	botaoOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ready.ready(model.getListSchemeXml());
					dialog.dispose();
				}
			});
	    	
	    	JPanel geral = new JPanel(new BorderLayout());
	    	geral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    	JScrollPane sc = new JScrollPane(itens);
	    	geral.add(sc, BorderLayout.CENTER);
	    	
	    	JPanel painelSouth = new JPanel(new GridLayout(1,0));
	    	painelSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    	painelSouth.add(botaoDown);
	    	painelSouth.add(botaoUp);
	    	painelSouth.add(botaoOk);
	    	geral.add(painelSouth, BorderLayout.SOUTH);
	        
	    	return geral;
	    }
	    
	    private void swap(int a, int b) {
	        SchemeXml aObject = model.getElementAt(a);
	        SchemeXml bObject = model.getElementAt(b);
	        model.set(a, bObject);
	        model.set(b, aObject);
	    }
	    
}
