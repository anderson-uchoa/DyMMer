package br.ufc.lps.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/*
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXlsDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
*/
public class ReportUtils {
	/*private static JRXlsDataSource getDataSource1() throws JRException
	  {
		JRXlsDataSource ds;
	    try
	    {
	      String[] columnNames = new String[]{"Features Model", "NF"};
	      int[] columnIndexes = new int[]{1, 2};
	      ds = new JRXlsDataSource(JRLoader.getLocationInputStream("./relatorios/Full Measures Dataset - Adaptado.xls"));
	      ds.setColumnNames(columnNames, columnIndexes);
	      
	      //uncomment the below line to see how sheet selection works
//	    ds.setSheetSelection("xlsdatasource2");
	    }
	    catch (IOException e)
	    {
	      throw new JRException(e);
	    }

	    return ds;
	  }
 
   
	public void fill1() throws JRException{
	    long start = System.currentTimeMillis();
	    //Preparing parameters
	    Map parameters = new HashMap();
	    parameters.put("ReportTitle", "Address Report");
	    parameters.put("DataFile", "XlsDataSource.data.xls - XLS data source");
	    Set states = new HashSet();
	    states.add("Active");
	    states.add("Trial");
	    parameters.put("IncludedStates", states);

	    
	    JasperReport jasperReport = JasperCompileManager.compileReport("./relatorios/DyMMer.jrxml");
	    JasperPrint jasperPrint=JasperFillManager.fillReport("./relatorios/DyMMer.jasper", parameters, getDataSource1());
	    
	    JasperViewer.viewReport(jasperPrint,false);
	    JasperPrintManager.printReport(jasperPrint,false);
	    System.err.println("Filling time : " + (System.currentTimeMillis() - start));
	  }*/
 
}

	