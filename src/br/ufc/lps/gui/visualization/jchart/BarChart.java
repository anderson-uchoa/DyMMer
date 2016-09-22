package br.ufc.lps.gui.visualization.jchart;

import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import br.ufc.lps.repositorio.MedidasContexto;
import br.ufc.lps.repositorio.SchemeXml;

public class BarChart{
	   
	public static JPanel createChart(SchemeXml schema)
	   {      
	      JFreeChart barChart = ChartFactory.createBarChart(
	         "Diferenças entre medidas de contextos do modelo: "+schema.getNameXml(),           
	         "Contextos",            
	         "Valores",            
	         createDataset(schema),
	         PlotOrientation.HORIZONTAL,           
	         true, true, false);
	         
	      ChartPanel chartPanel = new ChartPanel( barChart );        
	      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
	      return chartPanel;
	   }
	
	   private static CategoryDataset createDataset( SchemeXml lista)
	   {    
	      final DefaultCategoryDataset dataset = 
	      new DefaultCategoryDataset( );  

	      
	      for(MedidasContexto s: lista.getMedidasContexto()){
	    	  dataset.addValue( s.getNumberOfActivatedFeatures() , s.getNameContext() , "Number of activated features" );        
	    	  dataset.addValue( s.getNumberOfDeactivatedFeatures() , s.getNameContext() , "Number of desactivated features" );        
	    	  dataset.addValue( s.getNumberOfContextConstraints() , s.getNameContext() , "Number of context Constraints" );        
	    	  
	    	  dataset.addValue( s.getActivatedFeaturesByContextAdaptation() , s.getNameContext() , "Activated features by context adaptation" );        
	    	  dataset.addValue( s.getDesactivatedFeaturesByContextAdaptation() , s.getNameContext() , "Desactivated features by context adaptation" );        
	    	  dataset.addValue( s.getNonContextFeatures() , s.getNameContext() , "Non Context Features" );          
	      }            

	      return dataset; 
	   }
}
