package br.ufc.lps.gui.visualization.jchart;

import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import br.ufc.lps.repositorio.SchemeXml;

public class PieFeatures{
	
	   private static PieDataset createDataset(List<SchemeXml> lista) {

		  DefaultPieDataset dataset = new DefaultPieDataset( );
		  for(SchemeXml s : lista){
			  dataset.setValue( s.getNameXml()+" = "+s.getNumberOfFeatures() , new Double( s.getNumberOfFeatures() ) );
		  }
		  return dataset;         
	   }
	   private static JFreeChart createChart( PieDataset dataset ){
	      JFreeChart chart = ChartFactory.createPieChart3D(      
	         "Número de Features",  // chart title 
	         dataset,        // data    
	         true,           // include legend   
	         true, 
	         false);

	      return chart;
	   }
	   public static JPanel createDemoPanel( List<SchemeXml> lista){
	      JFreeChart chart = createChart( createDataset(lista) );  
	      return new ChartPanel( chart ); 
	   }
}
