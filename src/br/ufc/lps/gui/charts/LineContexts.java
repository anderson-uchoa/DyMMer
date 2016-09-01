package br.ufc.lps.gui.charts;

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

public class LineContexts{
	   
	public static JPanel createChart(SchemeXml schema)
	   {      
	      JFreeChart lineChart = ChartFactory.createLineChart(
	         "Linha dos itens do contexto",
	         "Contextos","Valores",
	         LineContexts.createDataset(schema),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( lineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );     
	      return chartPanel;
	   }
	
	   private static CategoryDataset createDataset( SchemeXml lista)
	   {    
	      final DefaultCategoryDataset dataset = 
	      new DefaultCategoryDataset( );  

	      for(MedidasContexto s: lista.getMedidasContexto()){
	    	  dataset.addValue( s.getNumberOfActivatedFeatures() , "Number of activated features", s.getNameContext()); 
	    	  dataset.addValue( s.getNumberOfDeactivatedFeatures() , "Number of desactivated features", s.getNameContext());
	    	  dataset.addValue( s.getNumberOfContextConstraints() ,  "Number of context Constraints",s.getNameContext() );   
	    	  
		      dataset.addValue( s.getActivatedFeaturesByContextAdaptation()  , "Activated features by context adaptation" ,s.getNameContext());        
		      dataset.addValue( s.getDesactivatedFeaturesByContextAdaptation() , "Desactivated features by context adaptation",s.getNameContext() );        
		      dataset.addValue( s.getNonContextFeatures() , "Non Context Features",  s.getNameContext() ); 
	      }
	
	      return dataset; 
	   }
}
