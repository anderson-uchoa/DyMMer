package br.ufc.lps.gui.visualization.browser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JComponent;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import br.ufc.lps.gui.visualization.chartjs.config.BuyerData;
import br.ufc.lps.gui.visualization.chartjs.config.ConfigSet;
import br.ufc.lps.gui.visualization.chartjs.config.DataSet;
import br.ufc.lps.gui.visualization.chartjs.config.OptionsSet;
import br.ufc.lps.gui.visualization.chartjs.config.TitleSet;
import br.ufc.lps.gui.visualization.chartjs.config.types.bar.DataSetBar;
import br.ufc.lps.gui.visualization.chartjs.config.types.line.DataSetLine;
import br.ufc.lps.repositorio.MedidasContexto;
import br.ufc.lps.repositorio.SchemeXml;

public class BrowserController {
       
       public static JComponent getBar(SchemeXml scheme){
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("horizontalBar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   
    	   List<String> labels = Arrays.asList("Number of activated features", "Number of desactivated features",
    			   "Number of context Constraints", "Activated features by context adaptation",
    			   "Desactivated features by context adaptation", "Non Context Features");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.getMedidasContexto().size(); i++){
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 0.5)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 1)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);
    	   }
    	   
    	   List<Double> listaData = null;
    	   DataSetBar dataSet = null;
    	   
    	   for(int j=0; j < scheme.getMedidasContexto().size(); j++){
    		   
    		   MedidasContexto medida = scheme.getMedidasContexto().get(j);
    		   
    		   dataSet = new DataSetBar	();
	    	   dataSet.setBorderWidth(3);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(medida.getNameContext());
    		   
	    	   listaData.add(Double.parseDouble(medida.getNumberOfActivatedFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfDeactivatedFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfContextConstraints().toString()));
	    	   listaData.add(Double.parseDouble(medida.getActivatedFeaturesByContextAdaptation().toString()));
	    	   listaData.add(Double.parseDouble(medida.getDesactivatedFeaturesByContextAdaptation().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNonContextFeatures().toString()));
    		   dataSet.setData(listaData);
    		   List<String> coresBackground2 = new ArrayList<>();
        	   List<String> coresHoverBackground2 = new ArrayList<>();
    		   for(int i=0; i < 6; i++){
	    		   coresBackground2.add(coresBackground.get(j));
	    		   coresHoverBackground2.add(coresHoverBackground.get(j));
    		   }
    		   dataSet.setBackgroundColor(coresBackground2);
    		   dataSet.setHoverBackgroundColor(coresHoverBackground2);
	    	   datasets.add(dataSet);
	    	  
    	   }
    	  // dataSet.setBackgroundColor(Arrays.asList("rgba(255, 99, 132, 0.5)", "rgba(54, 162, 235, 0.5)", "rgba(255, 206, 86, 0.5)", "rgba(75, 192, 192, 0.5)", "rgba(153, 102, 255, 0.5)", "rgba(255, 159, 64, 0.5)"));
    	   //dataSet.setHoverBackgroundColor(Arrays.asList("rgba(255,99,132,1)", "rgba(54, 162, 235, 1)", "rgba(255, 206, 86, 1)", "rgba(75, 192, 192, 1)", "rgba(153, 102, 255, 1)", "rgba(255, 159, 64, 1)"));
    	
    	   //datasets.add(dataSet);
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Comparação entre contextos do modelo: "+scheme.getNameXml());
    	   optionsSet.setTitle(titleSet);
    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/teste.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   return new BrowserView(browser);
       }
     
       public static JComponent getLine(SchemeXml scheme){
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("line");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   
    	   List<String> labels = Arrays.asList("Number of activated features", "Number of desactivated features",
    			   "Number of context Constraints", "Activated features by context adaptation",
    			   "Desactivated features by context adaptation", "Non Context Features");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.getMedidasContexto().size(); i++){
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 0.5)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 1)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);
    	   }
    	   
    	   List<Double> listaData = null;
    	   DataSetLine dataSet = null;
    	   
    	   for(int j=0; j < scheme.getMedidasContexto().size(); j++){
    		   
    		   MedidasContexto medida = scheme.getMedidasContexto().get(j);
    		   
    		   dataSet = new DataSetLine();
	    	   dataSet.setBorderWidth(3);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(medida.getNameContext());
    		   
	    	   listaData.add(Double.parseDouble(medida.getNumberOfActivatedFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfDeactivatedFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfContextConstraints().toString()));
	    	   listaData.add(Double.parseDouble(medida.getActivatedFeaturesByContextAdaptation().toString()));
	    	   listaData.add(Double.parseDouble(medida.getDesactivatedFeaturesByContextAdaptation().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNonContextFeatures().toString()));
    		   dataSet.setData(listaData);
    		   
    		   dataSet.setBackgroundColor(coresBackground.get(j));
    		   dataSet.setFill(false);
    		   dataSet.setBorderColor(coresHoverBackground.get(j));
    		   
	    	   datasets.add(dataSet);
	    	  
    	   }
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Comparação entre contextos do modelo: "+scheme.getNameXml());
    	   optionsSet.setTitle(titleSet);
    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/teste.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   return new BrowserView(browser);
       }

       public static JComponent getPie(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("pie");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   
    	   List<String> labels = new ArrayList<>();
    	   
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.size(); i++){
    		   labels.add(scheme.get(i).getNameXml());
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 0.5)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 1)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);
    	   }
    	   
    	   List<Double> listaData = null;
    	   DataSetBar dataSet = null;
    	   
		   dataSet = new DataSetBar();
		   listaData = new ArrayList<>();
		 
    	   for(int j=0; j < scheme.size(); j++){
    		   SchemeXml medida = scheme.get(j);
	    	   listaData.add(Double.parseDouble(medida.getNumberOfFeatures().toString()));
    	   }
    	   dataSet.setBackgroundColor(coresBackground);
    	   dataSet.setHoverBackgroundColor(coresHoverBackground);
    	   dataSet.setData(listaData);
		   
    	   datasets.add(dataSet);
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Comparação entre número de features dos modelos.");
    	   optionsSet.setTitle(titleSet);
    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/teste.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   return new BrowserView(browser);
       }

}
