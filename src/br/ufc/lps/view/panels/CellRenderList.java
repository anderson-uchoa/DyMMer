package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import br.ufc.lps.model.adaptation.Adaptacao;
import br.ufc.lps.model.adaptation.ContextoAdaptacao;
import br.ufc.lps.model.adaptation.ValorAdaptacao;

public class CellRenderList implements ListCellRenderer {
	
	  protected static Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  
	  private final Color selectionForeground, selectionBackground;
		private final Color textForeground, textBackground;
	  
	  public CellRenderList() {
			selectionForeground = UIManager.getColor("JList.selectionForeground");
			selectionBackground = UIManager.getColor("JList.selectionBackground");
			textForeground = UIManager.getColor("Tree.textForeground");
			textBackground = UIManager.getColor("Tree.textBackground");
	  }

	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  
		  JPanel panelMain = new JPanel(new BorderLayout());
		  JPanel panelItensList = new JPanel(new GridLayout(0,1));
		  panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		  JSeparator separator = new JSeparator();
		  
		  Adaptacao adaptacao = (Adaptacao) value;
		  
		  JLabel title = new JLabel("Scenario "+(index+1));
		  Font font = title.getFont();
		  Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		  title.setFont(boldFont);
		  
		  panelMain.add(title, BorderLayout.NORTH);
		  
		  for(ContextoAdaptacao valor: adaptacao.getValorAdaptacao()){
				for(ValorAdaptacao ada : valor.getValorAdaptacao()){
					if(ada.getStatus()){
						JLabel val = new JLabel(valor.getNome());
						val.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
						JLabel val2 = new JLabel(ada.getNome());
						val2.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
						panelItensList.add(val);
					  	panelItensList.add(val2);
						break;
					}
				}
		  }
		  panelMain.add(panelItensList, BorderLayout.CENTER);
		  panelMain.add(separator, BorderLayout.SOUTH);
		  
		  if (isSelected) {
			  panelMain.setForeground(selectionForeground);
			  panelMain.setBackground(selectionBackground);
			  panelItensList.setForeground(selectionForeground);
			  panelItensList.setBackground(selectionBackground);
			}
			else {
			  panelMain.setForeground(textForeground);
			  panelMain.setBackground(textBackground);
			  panelItensList.setForeground(textBackground);
			  panelItensList.setBackground(textBackground);
			}
		  
	    //JLabel renderer = (JLabel) 
		  //defaultRenderer.getListCellRendererComponent(list, value, index,
	        //isSelected, cellHasFocus);
	    
	   // renderer.setBorder(cellHasFocus ? focusBorder : noFocusBorder);
	    
	    return panelMain;
	  
	  }
	}