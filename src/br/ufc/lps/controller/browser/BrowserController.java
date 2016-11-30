package br.ufc.lps.controller.browser;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.cr;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import br.ufc.lps.model.visualization.chartjs.config.AxesSet;
import br.ufc.lps.model.visualization.chartjs.config.BuyerData;
import br.ufc.lps.model.visualization.chartjs.config.ConfigSet;
import br.ufc.lps.model.visualization.chartjs.config.DataSet;
import br.ufc.lps.model.visualization.chartjs.config.HoverSet;
import br.ufc.lps.model.visualization.chartjs.config.LegendSet;
import br.ufc.lps.model.visualization.chartjs.config.OptionsSet;
import br.ufc.lps.model.visualization.chartjs.config.ScaleLabelSet;
import br.ufc.lps.model.visualization.chartjs.config.ScalesSet;
import br.ufc.lps.model.visualization.chartjs.config.TitleSet;
import br.ufc.lps.model.visualization.chartjs.config.types.bar.DataSetBar;
import br.ufc.lps.model.visualization.chartjs.config.types.bubble.DataBubble;
import br.ufc.lps.model.visualization.chartjs.config.types.bubble.DataSetBubble;
import br.ufc.lps.model.visualization.chartjs.config.types.line.DataSetLine;
import br.ufc.lps.model.visualization.d3.config.Children;
import br.ufc.lps.model.visualization.d3.config.Config;
import br.ufc.lps.repository.MeasuresContexts;
import br.ufc.lps.repository.SchemeXml;

public class BrowserController{
       
       public static Component getBar(SchemeXml scheme){
    	   
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("radar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList("Number of activated features", "Number of desactivated features",
    			   "Number of context Constraints", "Activated features by context adaptation",
    			   "Desactivated features by context adaptation", "Non Context Features");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.getMeasuresContexts().size(); i++){
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
    	   
    	   for(int j=0; j < scheme.getMeasuresContexts().size(); j++){
    		   
    		   MeasuresContexts medida = scheme.getMeasuresContexts().get(j);
    		   
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
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Comparação entre contextos do modelo: "+scheme.getNameXml());
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
      	  
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
			}
		});
    	 
    	   return new BrowserView(browser);
    	   
       }

       public static Component getBarNumberConfigurations(List<SchemeXml> scheme){
    	   
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("bar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList("Number of activated features");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.size(); i++){
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
    	   
    	   for(int j=0; j < scheme.size(); j++){
    		   
    		   SchemeXml medida = scheme.get(j);
    		   
    		   dataSet = new DataSetBar	();
	    	   dataSet.setBorderWidth(3);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(medida.getNameXml());
    		   
	    	   listaData.add(Double.parseDouble(medida.getNumberOfValidConfigurations().toString()));
	    	   
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
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Number of Configurations");
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
      	  
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
			}
		});
    	 
    	   return new BrowserView(browser);
    	   
       }
       //Q 2 9
       public static Component getBarNumberImpactoLinha(List<SchemeXml> scheme){
    	   
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("radar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList("Número de Features (NF), Número de Features Mandatórias (NM), Número de Features Top (Ntop), Restrições Cross-tree (CTC)");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.size(); i++){
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
    	   
    	   for(int j=0; j < scheme.size(); j++){
    		   
    		   SchemeXml medida = scheme.get(j);
    		   
    		   dataSet = new DataSetBar	();
	    	   dataSet.setBorderWidth(3);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(medida.getNameXml());
    		   
	    	   listaData.add(Double.parseDouble(medida.getNumberOfFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfMandatoryFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfTopFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getCrossTreeConstraintsRate().toString()));
	    	   
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
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Impacto dos Modelos de Features");
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
      	  
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
			}
		});
    	 
    	   return new BrowserView(browser);
    	   
       }
       
       public static Component getBarComplexity(SchemeXml scheme){
    	   
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("bar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList("Number of Features (NF)", "Number of Mandatory Features (NM)", "Number of Top Features (Ntop)", "Cross-tree Constraints (CTC)");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   	   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 0.5)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 1)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);

    	   List<Double> listaData = null;
    	   DataSetBar dataSet = null;
    	   
    		   
    		   SchemeXml medida = scheme;
    		   
    		   dataSet = new DataSetBar	();
	    	   dataSet.setBorderWidth(3);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(medida.getNameXml());
    		   
	    	   listaData.add(Double.parseDouble(medida.getNumberOfFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfMandatoryFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfTopFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getCrossTreeConstraintsRate()!=null ? medida.getCrossTreeConstraintsRate().toString() : "0"));
	    	   
    		   dataSet.setData(listaData);
    		   List<String> coresBackground2 = new ArrayList<>();
        	   List<String> coresHoverBackground2 = new ArrayList<>();
    		   for(int i=0; i < 6; i++){
	    		   coresBackground2.add(coresBackground.get(0));
	    		   coresHoverBackground2.add(coresHoverBackground.get(0));
    		   }
    		   dataSet.setBackgroundColor(coresBackground2);
    		   dataSet.setHoverBackgroundColor(coresHoverBackground2);
	    	   datasets.add(dataSet);
	    	  
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Complexity of Features Model");
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson go = new Gson();
    	   String saida = go.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
      	  
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
			}
		});
    	 
    	   return new BrowserView(browser);
    	   
       }

       public static Component getBarVariabilityDynamic(SchemeXml scheme){
    	   
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("bar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList("Number of Features (NF)", "Number of Mandatory Features (NM)", "Number of Top Features (Ntop)", "Cross-tree Constraints (CTC)");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   	   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 0.5)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 1)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);

    	   List<Double> listaData = null;
    	   DataSetBar dataSet = null;
    	   
    		   
    		   SchemeXml medida = scheme;
    		   
    		   dataSet = new DataSetBar	();
	    	   dataSet.setBorderWidth(3);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(medida.getNameXml());
    		   
	    	   listaData.add(Double.parseDouble(medida.getNumberOfFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfMandatoryFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfTopFeatures().toString()));
	    	   listaData.add(Double.parseDouble(medida.getCrossTreeConstraintsRate()!=null ? medida.getCrossTreeConstraintsRate().toString() : "0"));
	    	   
    		   dataSet.setData(listaData);
    		   List<String> coresBackground2 = new ArrayList<>();
        	   List<String> coresHoverBackground2 = new ArrayList<>();
    		   for(int i=0; i < 6; i++){
	    		   coresBackground2.add(coresBackground.get(0));
	    		   coresHoverBackground2.add(coresHoverBackground.get(0));
    		   }
    		   dataSet.setBackgroundColor(coresBackground2);
    		   dataSet.setHoverBackgroundColor(coresHoverBackground2);
	    	   datasets.add(dataSet);
	    	  
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Complexity of Features Model");
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson go = new Gson();
    	   String saida = go.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
      	  
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
			}
		});
    	 
    	   return new BrowserView(browser);
    	   
       }
       
       public static Component getRadarVariabilidadeAdaptativa(List<SchemeXml> scheme){
    	   
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("radar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList(
    			   "Number of optional features removed",
    			   "Number of alternative features removed",
    			   "Number of mandatory features removed",
    			   "Number of features removed",
    			   
    			   "Number of optional features adicioned",
    			   "Number of alternative features adicioned",
    			   "Number of mandatory features adicioned",
    			   "Number of features");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.size(); i++){
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   	   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 0.4)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 1)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);
    	   }
    	   
    	   List<Double> listaData = null;
    	   DataSetBar dataSet = null;
    	   
    	   int removedNF = 0;
    	   int removedNA = 0;
    	   int removedNO = 0;
    	   int removedNM = 0;
    	   
    	   int adicionedNF = 0;
    	   int adicionedNA = 0;
    	   int adicionedNO = 0;
    	   int adicionedNM = 0;
    	   
    	   for(int i=1; i < scheme.size(); i++){
    		   
    		   if((i+1) < scheme.size()){
    			   int result = scheme.get(i+1).getNumberOfFeatures() - scheme.get(i).getNumberOfFeatures();
    			   
    			   System.out.println(result);
    			   
    			   if(result < 0)
    				   removedNF+=(result*-1);
    			   else if(result > 0)
    				   adicionedNF+=result;
    			   
    			   result = scheme.get(i+1).getNumberOfAlternativeFeatures()!=null ? scheme.get(i+1).getNumberOfAlternativeFeatures() : 1  - (scheme.get(i).getNumberOfAlternativeFeatures()!=null ? scheme.get(i).getNumberOfAlternativeFeatures() : 2);
    			   
    			   System.out.println(result);
    			   
    			   if(result < 0)
    				   removedNA+=(result*-1);
    			   else if(result > 0)
    				   adicionedNA+=result;
    			   
    			   result = scheme.get(i+1).getNumberOfOptionalFeatures() - scheme.get(i).getNumberOfOptionalFeatures();

    			   System.out.println(result);

    			   if(result < 0)
    				   removedNO+=(result*-1);
    			   else if(result > 0)
    				   adicionedNO+=result;
    			   
    			   result = scheme.get(i+1).getNumberOfMandatoryFeatures() - scheme.get(i).getNumberOfMandatoryFeatures();
    			   
    			   System.out.println(result);
    			   
    			   if(result < 0)
    				   removedNM+=(result*-1);
    			   else if(result > 0)
    				   adicionedNM+=result;
    		   }
    		   
    		   dataSet = new DataSetBar();
	    	   dataSet.setBorderWidth(2);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(scheme.get(i).getNameXml());
    		   
	    	   listaData.add(Double.parseDouble(removedNO+""));
	    	   listaData.add(Double.parseDouble(removedNA+""));
	    	   listaData.add(Double.parseDouble(removedNM+""));
	    	   listaData.add(Double.parseDouble(removedNF+""));
	    	   
	    	   listaData.add(Double.parseDouble(adicionedNO+""));
	    	   listaData.add(Double.parseDouble(adicionedNA+""));
	    	   listaData.add(Double.parseDouble(adicionedNM+""));
	    	   listaData.add(Double.parseDouble(adicionedNF+""));
	    	   
	    	   dataSet.setData(listaData);
	    	   
	    	   List<String> coresBackground2 = new ArrayList<>();
        	   List<String> coresHoverBackground2 = new ArrayList<>();
        	   
    		   for(int j=0; j < 8; j++){
	    		   coresBackground2.add(coresBackground.get(i));
	    		   coresHoverBackground2.add(coresHoverBackground.get(i));
    		   }
    		   
    		   dataSet.setBackgroundColor(coresBackground2);
    		   dataSet.setHoverBackgroundColor(coresHoverBackground2);
	    	
	    	   
	    	   removedNF = 0;
	    	   removedNA = 0;
	    	   removedNO = 0;
	    	   removedNM = 0;
	    	   
	    	   adicionedNF = 0;
	    	   adicionedNA = 0;
	    	   adicionedNO = 0;
	    	   adicionedNM = 0;
	    	   
	    	   datasets.add(dataSet);
    	   }
    	   
    	  /* for(int j=0; j < scheme.getMeasuresContexts().size(); j++){
    		   
    		   MeasuresContexts medida = scheme.getMeasuresContexts().get(j);
    		   
    		   dataSet = new DataSetBar();
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
	    	  
    	   }*/
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Complexidade Estrutural: "+scheme.get(0).getNameXml());
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
      	  
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
			}
		});
    	 
    	   return new BrowserView(browser);
    	   
       }
       
       public static Component getRadarImpactoQ2(List<SchemeXml> scheme){
    	   
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("radar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList(
    			   "Number of Top features features removed",
    			   "Number of mandatory features removed",
    			   "Number of features removed",
    			   
    			   "Cross-tree constraints",
    			   "Number of Top features features adicioned",
    			   "Number of mandatory features adicioned",
    			   "Number of features adicioned");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < scheme.size(); i++){
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   	   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 0.4)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 1)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);
    	   }
    	   
    	   List<Double> listaData = null;
    	   DataSetBar dataSet = null;
    	   
    	   int removedTop = 0;
    	   int removedNF = 0;
    	   int removedNM = 0;
    	   
    	   int cross = 0;
    	   int adicionedTop = 0;
    	   int adicionedNF = 0;
    	   int adicionedNM = 0;
    	   
    	   for(int i=1; i < scheme.size(); i++){
    		   
    		   if((i+1) < scheme.size()){
    			   int result = 0;
    			   
    			   result = scheme.get(i+1).getNumberOfTopFeatures()!=null ? scheme.get(i+1).getNumberOfTopFeatures() : 1  - (scheme.get(i).getNumberOfTopFeatures()!=null ? scheme.get(i).getNumberOfTopFeatures() : 2);
    			   
    			   System.out.println(result);
    			   
    			   if(result < 0)
    				   removedTop+=(result*-1);
    			   else if(result > 0)
    				   adicionedTop+=result;
    			   
    			   result = scheme.get(i+1).getNumberOfFeatures() - scheme.get(i).getNumberOfFeatures();

    			   System.out.println(result);

    			   if(result < 0)
    				   removedNF+=(result*-1);
    			   else if(result > 0)
    				   adicionedNF+=result;
    			   
    			   result = scheme.get(i+1).getNumberOfMandatoryFeatures() - scheme.get(i).getNumberOfMandatoryFeatures();
    			   
    			   System.out.println(result);
    			   
    			   if(result < 0)
    				   removedNM+=(result*-1);
    			   else if(result > 0)
    				   adicionedNM+=result;
    			   
    			   cross = Integer.parseInt(scheme.get(i).getCrossTreeConstraintsRate() != null ? scheme.get(i).getCrossTreeConstraintsRate().toString() : "0");
    		   }
    		   
    		   dataSet = new DataSetBar();
	    	   dataSet.setBorderWidth(2);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(scheme.get(i).getNameXml());
    		   
	    	   listaData.add(Double.parseDouble(removedTop+""));
	    	   listaData.add(Double.parseDouble(removedNM+""));
	    	   listaData.add(Double.parseDouble(removedNF+""));
	    	   
	    	   listaData.add(Double.parseDouble(cross+""));
	    	   listaData.add(Double.parseDouble(adicionedTop+""));
	    	   listaData.add(Double.parseDouble(adicionedNM+""));
	    	   listaData.add(Double.parseDouble(adicionedNF+""));
	    	   
	    	   dataSet.setData(listaData);
	    	   
	    	   List<String> coresBackground2 = new ArrayList<>();
        	   List<String> coresHoverBackground2 = new ArrayList<>();
        	   
    		   for(int j=0; j < 8; j++){
	    		   coresBackground2.add(coresBackground.get(i));
	    		   coresHoverBackground2.add(coresHoverBackground.get(i));
    		   }
    		   
    		   dataSet.setBackgroundColor(coresBackground2);
    		   dataSet.setHoverBackgroundColor(coresHoverBackground2);
	    	
	    	   
	    	   removedTop = 0;
	    	   removedNF = 0;
	    	   removedNM = 0;
	    	   
	    	   cross = 0;
	    	   adicionedTop = 0;
	    	   adicionedNF = 0;
	    	   adicionedNM = 0;
	    	   
	    	   datasets.add(dataSet);
    	   }
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Complexidade Estrutural: "+scheme.get(0).getNameXml());
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
      	  
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
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
    	   for(int i=0; i < scheme.getMeasuresContexts().size(); i++){
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 1)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 0.5)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);
    	   }
    	   
    	   List<Double> listaData = null;
    	   DataSetLine dataSet = null;
    	   
    	   for(int j=0; j < scheme.getMeasuresContexts().size(); j++){
    		   
    		   MeasuresContexts medida = scheme.getMeasuresContexts().get(j);
    		   
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
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
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

       public static JComponent getLineStaticVariability(List<SchemeXml> schemes){
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("line");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   
    	   List<String> labels = Arrays.asList("Number of Valid Configuration (NVC)", "Ratio of Variability (RoV)",
    			   "Number of Optional Features (NO)");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   List<String> coresBackground = new ArrayList<>();
    	   List<String> coresHoverBackground = new ArrayList<>();
    	   
    	   Random rand = new Random();
    	   for(int i=0; i < schemes.size(); i++){
    		   int r = rand.nextInt(255);
    		   int g = rand.nextInt(255);
    		   int b = rand.nextInt(255);
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 1)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 0.5)";
    		   coresBackground.add(rgbBack);
    		   coresHoverBackground.add(rgbHover);
    	   }
    	   
    	   List<Double> listaData = null;
    	   DataSetLine dataSet = null;
    	   
    	   for(int j=0; j < schemes.size(); j++){
    		   
    		   SchemeXml medida = schemes.get(j);
    		   
    		   dataSet = new DataSetLine();
	    	   dataSet.setBorderWidth(3);
	    	   	
	    	   listaData = new ArrayList<>();
    		   dataSet.setLabel(medida.getNameXml());
    		   
	    	   listaData.add(Double.parseDouble(medida.getNumberOfValidConfigurations().toString()));
	    	   listaData.add(Double.parseDouble(medida.getRatioOfVariability().toString()));
	    	   listaData.add(Double.parseDouble(medida.getNumberOfOptionalFeatures().toString()));
    		   dataSet.setData(listaData);
    		   
    		   dataSet.setBackgroundColor(coresBackground.get(j));
    		   dataSet.setFill(false);
    		   dataSet.setBorderColor(coresHoverBackground.get(j));
    		   
	    	   datasets.add(dataSet);
	    	  
    	   }
    	   
    	   OptionsSet optionsSet = new OptionsSet();
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Static Variability");
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
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
       
       
       private static String getRamdomColor(int transparencia){
    	   Random rand = new Random();
    	   int r = rand.nextInt(255);
		   int g = rand.nextInt(255);
		   int b = rand.nextInt(255);
		   return "rgba("+r+", "+g+", "+b+", "+transparencia+")";
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
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	   
    	   //LEGEND OF OPTIONS
    	   LegendSet legend = new LegendSet();
    	   legend.setPosition("bottom");
    	   optionsSet.setLegend(legend);

    	   
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
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
      
       public static JComponent getBubble(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("bubble");
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<DataSet> datasets = new ArrayList<>(); 
    	   
    	   for(int i=0; i < scheme.size(); i++){
	    	   DataSetBubble dataSetBubble = new DataSetBubble();
	    	   dataSetBubble.setLabel(scheme.get(i).getNameXml());
	    	   List<DataBubble> data = new ArrayList<>();    	   
	    	   DataBubble exemplo = new DataBubble();
	    	   exemplo.setY(Double.parseDouble(scheme.get(i).getRatioOfVariability().toString()));
	    	   exemplo.setX(Double.parseDouble(scheme.get(i).getNumberOfValidConfigurations().toString()));
	    	   exemplo.setR(15.0);
	    	   dataSetBubble.setBackgroundColor(getRamdomColor(1));
	    	   data.add(exemplo);
	    	   dataSetBubble.setData(data);
	    	   datasets.add(dataSetBubble);
    	   }    	   
    	   
    	   //OPTIONS
    	   OptionsSet optionsSet = new OptionsSet();
    	   optionsSet.setResponsive(true);
    	   
    	   //HOVER OPTIONS
    	   HoverSet hover = new HoverSet();
    	   hover.setMode("label");
    	   
    	   //SCALES OPTIONS
    	   ScalesSet scales = new ScalesSet();
    	   //xAxes of SCALES
    	   AxesSet xaxes = new AxesSet();
    	   xaxes.setDisplay(true);
    	   ScaleLabelSet xlabelSet = new ScaleLabelSet();
    	   xlabelSet.setDisplay(true);
    	   xlabelSet.setLabelString("NVC");
    	   xaxes.setScaleLabel(xlabelSet);
    	   List<AxesSet> listax = new ArrayList<>();
    	   listax.add(xaxes);
    	   scales.setxAxes(listax);
    	   //yAxes of SCALES
    	   AxesSet yaxes = new AxesSet();
    	   yaxes.setDisplay(true);
    	   ScaleLabelSet ylabelSet = new ScaleLabelSet();
    	   ylabelSet.setDisplay(true);
    	   ylabelSet.setLabelString("RoV");
    	   yaxes.setScaleLabel(ylabelSet);
    	   List<AxesSet> listay = new ArrayList<>();
    	   listay.add(yaxes);
    	   scales.setyAxes(listay);
    	   
    	   optionsSet.setScales(scales);
    	   
    	   //TITLE OF OPTIONS
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("RoV x NVC (Variabilidade estática)");
    	   titleSet.setFontSize(20);
    	   optionsSet.setTitle(titleSet);
    	
    	   cnf.setOptions(optionsSet);
    	   
    	   buyerData.setDatasets(datasets);
    	   cnf.setData(buyerData);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(cnf);
    	   System.out.println(saida);
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/generic.html");
    	   
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
     
       public static JComponent getD3(List<SchemeXml> scheme){
    	   Browser browser = new Browser();

    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("Nleaf x DTMax (largura x profundidade)");
    	   config.setRoot(c);
    	   
    	   List<Children> NleafXDTMax = new ArrayList<>();
    	   Children nLeaf = new Children();
    	   nLeaf.setName("Nleaf");
    	   NleafXDTMax.add(nLeaf);
    	   Children dTMax = new Children();
    	   dTMax.setName("DTMax");
    	   NleafXDTMax.add(dTMax);
    	   c.setChildren(NleafXDTMax);
    	   
    	   List<Children> listaNleaf = new ArrayList<>();
    	   nLeaf.setChildren(listaNleaf);
    	   List<Children> listaDTMax = new ArrayList<>();
    	   dTMax.setChildren(listaDTMax);
    	   
    	   for(int i=0; i < scheme.size(); i++){
    		   Children addC = new Children();
    		   SchemeXml sch = scheme.get(i);
			   addC.setName(sch.getNameXml());
			   
    		   double nlf = sch.getNumberOfLeafFeatures();
    		   double dtm = sch.getDepthOfTreeMax();
    		   if(nlf > dtm){
    			   addC.setSize(nlf);
    			   listaNleaf.add(addC);
    		   }else{
    			   addC.setSize(dtm);
    			   listaDTMax.add(addC);
    		   }
    	   }
    	   Children aaa = new Children();
    	   aaa.setSize(45d);
    	   aaa.setName("suhasua");
		   listaDTMax.add(aaa);
		   
		   Children aaab = new Children();
    	   aaab.setSize(10d);
    	   aaab.setName("suheuh");
		   listaDTMax.add(aaab);
    	   
    	   
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   System.out.println(saida);
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/treeMapTitle.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
    	   
    	   return new BrowserView(browser);
       }
      
       public static JComponent getD3Bubble(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	   
    	   
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("");
    	   config.setRoot(c);
    	   
    	   List<Children> NleafXDTMax = new ArrayList<>();
    	   Children nLeaf = new Children();
    	   nLeaf.setName("Nleaf");
    	   NleafXDTMax.add(nLeaf);
    	   Children dTMax = new Children();
    	   dTMax.setName("DTMax");
    	   NleafXDTMax.add(dTMax);
    	   c.setChildren(NleafXDTMax);
    	   
    	   List<Children> listaNleaf = new ArrayList<>();
    	   nLeaf.setChildren(listaNleaf);
    	   List<Children> listaDTMax = new ArrayList<>();
    	   dTMax.setChildren(listaDTMax);
    	   
    	   for(int i=0; i < scheme.size(); i++){
    		   Children addC = new Children();
    		   SchemeXml sch = scheme.get(i);
			   addC.setName(sch.getNameXml());
			   
    		   double nlf = sch.getNumberOfLeafFeatures();
    		   double dtm = sch.getDepthOfTreeMax();
    		   if(nlf > dtm){
    			   addC.setSize(nlf);
    			   listaNleaf.add(addC);
    		   }else{
    			   addC.setSize(dtm);
    			   listaDTMax.add(addC);
    		   }
    	   }
    	   Children aaa = new Children();
    	   aaa.setSize(45d);
    	   aaa.setName("suhasua");
		   listaDTMax.add(aaa);
		   
		   Children aaab = new Children();
    	   aaab.setSize(10d);
    	   aaab.setName("suheuh");
		   listaDTMax.add(aaab);
    	   
    	   
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   System.out.println(saida);
    	   
    	   
    	    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/pack-hierarchy.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
    	   
    	   return new BrowserView(browser);
       }
       
       public static JComponent getD3BubbleRoVxNVC(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	   
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("");
    	   config.setRoot(c);
    	   
    	   List<Children> RovxNVC = new ArrayList<>();
    	   Children nLeaf = new Children();
    	   nLeaf.setName("RoV");
    	   RovxNVC.add(nLeaf);
    	   Children dTMax = new Children();
    	   dTMax.setName("NVC");
    	   RovxNVC.add(dTMax);
    	   c.setChildren(RovxNVC);
    	   
    	   List<Children> listaRov = new ArrayList<>();
    	   nLeaf.setChildren(listaRov);
    	   List<Children> listaNVC = new ArrayList<>();
    	   dTMax.setChildren(listaNVC);
    	   
    	   for(int i=0; i < scheme.size(); i++){
    		   Children addC = new Children();
    		   SchemeXml sch = scheme.get(i);
			   addC.setName(sch.getNameXml());
			   
    		   double nlf = sch.getRatioOfVariability();
    		   double dtm = sch.getNumberOfValidConfigurations();
    		   if(nlf > dtm){
    			   addC.setSize(nlf);
    			   listaRov.add(addC);
    		   }else{
    			   addC.setSize(dtm);
    			   listaNVC.add(addC);
    		   }
    	   }
    	   
    	   Children ccc = new Children();
    	   ccc.setName("teste");
    	   ccc.setSize(45d);
    	   
    	   Children cccc = new Children();
    	   cccc.setName("teste 2");
    	   cccc.setSize(45d);
    	   
    	   listaRov.add(ccc);
    	   listaRov.add(cccc);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   System.out.println(saida);
    	   
    	   
    	    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/pack-hierarchy.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
    	   
    	   return new BrowserView(browser);
       }

       public static Component getD3FoC(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	   
    	   
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("FoC - Flexibilidade do Modelo");
    	   config.setRoot(c);
    	   
    	   List<Children> foc = new ArrayList<>();
    	  
    	   for(int i=0; i < scheme.size(); i++){
    		   Children addC = new Children();
    		   SchemeXml sch = scheme.get(i);
			   addC.setName(sch.getNameXml());
			   addC.setSize(sch.getFlexibilityOfConfiguration());
			   foc.add(addC);
    	   }
    	   
    	   c.setChildren(foc);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   System.out.println(saida);
    	   
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/d3Bubble.html");

    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
  
    	return new BrowserView(browser);
       }

       public static JComponent getD3FEX(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	   
    	   
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("FEX Extensibility");
    	   config.setRoot(c);
    	   
    	   List<Children> foc = new ArrayList<>();
    	  
    	   for(int i=0; i < scheme.size(); i++){
    		   Children addC = new Children();
    		   SchemeXml sch = scheme.get(i);
			   addC.setName(sch.getNameXml());
			   addC.setSize(Double.parseDouble(sch.getFeatureExtendibility()+""));
			   foc.add(addC);
    	   }
    	   
    	   c.setChildren(foc);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   System.out.println(saida);
    	   
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/visualization/d3Bubble.html");
    	   
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
    	   
    	   return new BrowserView(browser);
       }
       
       public static JComponent getD3Tree(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	  
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("Nleaf x DTMax (largura x profundidade)");
    	   config.setRoot(c);
    	   
    	   List<Children> NleafXDTMax = new ArrayList<>();
    	   Children nLeaf = new Children();
    	   nLeaf.setName("Nleaf");
    	   NleafXDTMax.add(nLeaf);
    	   Children dTMax = new Children();
    	   dTMax.setName("DTMax");
    	   NleafXDTMax.add(dTMax);
    	   c.setChildren(NleafXDTMax);
    	   
    	   List<Children> listaNleaf = new ArrayList<>();
    	   nLeaf.setChildren(listaNleaf);
    	   List<Children> listaDTMax = new ArrayList<>();
    	   dTMax.setChildren(listaDTMax);
    	   
    	   for(int i=0; i < scheme.size(); i++){
    		   Children addC = new Children();
    		   SchemeXml sch = scheme.get(i);
			   addC.setName(sch.getNameXml());
			   
			   double nlf = sch.getNumberOfLeafFeatures();
			   double dtm = sch.getDepthOfTreeMax();
			   
			   System.out.println("Name: "+sch.getNameXml()+" nlf: "+nlf+" dtm: "+dtm);
			   
    		   if(nlf > dtm){
    			   addC.setSize(nlf);
    			   listaNleaf.add(addC);
    		   }else{
    			   addC.setSize(dtm);
    			   listaDTMax.add(addC);
    		   }
    	   }
    	   Children aaa = new Children();
    	   aaa.setSize(45d);
    	   aaa.setName("teste 1");
		   listaDTMax.add(aaa);
		   
		   Children aaab = new Children();
    	   aaab.setSize(10d);
    	   aaab.setName("teste 2");
		   listaDTMax.add(aaab);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/treeMapTitleNameResize.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
    	   return new BrowserView(browser);
    	   
       }

       public static JComponent getD3TreeNumberOfConfigurations(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	  
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("Number of Configurations");
    	   config.setRoot(c);
    	   
    	   List<Children> NOC = new ArrayList<>();
    	   
    	   for(int i=0; i < scheme.size(); i++){
    		   List<Children> POC = new ArrayList<>();
    		   
    		   Children addC = new Children();
    		   SchemeXml sch = scheme.get(i);
			   addC.setName(sch.getNameXml());
			   
			   Double novc = sch.getNumberOfValidConfigurations();
			   addC.setSize(novc);
			   
			   POC.add(addC);
    	   }
    	   
    	   c.setChildren(NOC);
    	   
    	   config.setRoot(c);

    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   System.out.println(saida);
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/treeMapTitleNameResize.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
    	   return new BrowserView(browser);
    	   
       }
       
       public static JComponent getD3TreeDynamicVariabilidade(SchemeXml scheme){
    	   Browser browser = new Browser();
    	  
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("Dynamic Variability - "+scheme.getNameXml()+ " ( NC = "+scheme.getNumberOfContexts()+" )");
    	   config.setRoot(c);
    	   
    	   List<Children> contextos = new ArrayList<>();
    	   c.setChildren(contextos);
    	   
    	   for(int i=0; i < scheme.getMeasuresContexts().size(); i++){
    		   
    		   Children addC = new Children();
    		   MeasuresContexts sch = scheme.getMeasuresContexts().get(i);
    		   
    		   if(sch.getNameContext().equals("default"))
    			   continue;
    		   
    		   List<Children> variabilidade = new ArrayList<>();
    		   
    		   addC.setChildren(variabilidade);
    		   addC.setName(sch.getNameContext());
    		   
    		   //
    		   Children addC2 = new Children();
    		   addC2.setName("CFC = "+(sch.getContextFeaturesContraints()!=null ? sch.getContextFeaturesContraints() : "0"));
    		   addC2.setSize(Double.parseDouble((sch.getContextFeaturesContraints()!=null ? sch.getContextFeaturesContraints()+"" : "0")));
    		   
    		   //
    		   Children addC3 = new Children();
    		   addC3.setName("NAF = "+sch.getNumberOfActivatedFeatures());
    		   addC3.setSize(Double.parseDouble(sch.getNumberOfActivatedFeatures()+""));
    		   
    		   //
    		   Children addC4 = new Children();
    		   addC4.setName("NDF = "+sch.getNumberOfDeactivatedFeatures());
    		   addC4.setSize(Double.parseDouble(sch.getNumberOfDeactivatedFeatures()+""));

    		   //
    		   Children addC5 = new Children();
    		   addC5.setName("NCC = "+sch.getNumberOfContextConstraints());
    		   addC5.setSize(Double.parseDouble(sch.getNumberOfContextConstraints()+""));
		
    		   //
    		   Children addC6 = new Children();
    		   addC6.setName("DFCA = "+sch.getDesactivatedFeaturesByContextAdaptation());
    		   addC6.setSize(Double.parseDouble(sch.getDesactivatedFeaturesByContextAdaptation()+""));
    		   
    		   //
    		   Children addC7 = new Children();
    		   addC7.setName("AFCA = "+sch.getActivatedFeaturesByContextAdaptation());
    		   addC7.setSize(Double.parseDouble(sch.getActivatedFeaturesByContextAdaptation()+""));
    		   
    		   //
    		   Children addC8 = new Children();
    		   addC8.setName("CF = "+sch.getNumberOfFeatures());
    		   addC8.setSize(Double.parseDouble(sch.getNumberOfFeatures()+""));
    		   
    		   variabilidade.add(addC2);
    		   variabilidade.add(addC3);
    		   variabilidade.add(addC4);
    		   variabilidade.add(addC5);
    		   variabilidade.add(addC6);
    		   variabilidade.add(addC7);
    		   variabilidade.add(addC8);
    		   
    		   contextos.add(addC);
    	   }
    	  
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/treeMapTitle.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.getMessage());
				
			}
		});
    	   return new BrowserView(browser);
    	   
       }
       
       public static JComponent getD3TreeMapEvolucao(List<SchemeXml> scheme){
    	   Browser browser = new Browser();
    	   
    	   int removedNF = 0;
    	   int removedCTC = 0;
    	   int removedNTop = 0;
    	   int removedNM = 0;
    	   int adicionedNF = 0;
    	   int adicionedCTC = 0;
    	   int adicionedNTop = 0;
    	   int adicionedNM = 0;
    	   
    	   for(int i=0; i < scheme.size(); i++){

    		   if((i+1) < scheme.size()){
    			   int result = scheme.get(i).getNumberOfFeatures() - scheme.get(i+1).getNumberOfFeatures();
    			   
    			   if(result < 0)
    				   removedNF+=(result*-1);
    			   else if(result > 0)
    				   adicionedNF+=result;
    			   
    			   result = scheme.get(i).getVariableCrosstreeConstraints()!=null ? scheme.get(i).getVariableCrosstreeConstraints() : 1  - (scheme.get(i+1).getVariableCrosstreeConstraints()!=null ? scheme.get(i+1).getVariableCrosstreeConstraints() : 2);
    			   
    			   if(result < 0)
    				   removedCTC+=(result*-1);
    			   else if(result > 0)
    				   adicionedCTC+=result;
    			   
    			   result = scheme.get(i).getNumberOfTopFeatures() - scheme.get(i+1).getNumberOfTopFeatures();
    			   
    			   if(result < 0)
    				   removedNTop+=(result*-1);
    			   else if(result > 0)
    				   adicionedNTop+=result;
    			   
    			   result = scheme.get(i).getNumberOfMandatoryFeatures() - scheme.get(i+1).getNumberOfMandatoryFeatures();
    			   
    			   if(result < 0)
    				   removedNM+=(result*-1);
    			   else if(result > 0)
    				   adicionedNM+=result;
    		   }
    	   }
    	   
    	   Config config = new Config();
    	   Children c = new Children();
    	   c.setName("Evolução da Complexidade Estrutural");
    	   //NUMERO FEATURES - NUMERO ALTERNATIVA - NUMERO OPCIONAL - NUMERO MANDATORY 
    	   config.setRoot(c);
    	   
    	   List<Children> groupsTree = new ArrayList<>();
    	   Children removed = new Children();
    	   removed.setName("Removed Features");
    	   groupsTree.add(removed);
    	   Children adicioned = new Children();
    	   adicioned.setName("Adicioned Features");
    	   groupsTree.add(adicioned);
    	   c.setChildren(groupsTree);
    	   
    	   List<Children> listaRemoved = new ArrayList<>();
    	   removed.setChildren(listaRemoved);
    	   List<Children> listaAdicioned = new ArrayList<>();
    	   adicioned.setChildren(listaAdicioned);

    	   Children rnf = new Children();
    	   rnf.setSize(Double.parseDouble(removedNF+""));
    	   rnf.setName("NF = "+removedNF);
		   listaRemoved.add(rnf);
		   
		   Children rna = new Children();
    	   rna.setSize(Double.parseDouble(removedCTC+""));
    	   rna.setName("CTC = "+removedCTC);
		   listaRemoved.add(rna);
		   
		   Children rno = new Children();
    	   rno.setSize(Double.parseDouble(removedNTop+""));
    	   rno.setName("NTop = "+removedNTop);
		   listaRemoved.add(rno);
		   
		   Children rnm = new Children();
    	   rnm.setSize(Double.parseDouble(removedNM+""));
    	   rnm.setName("NTop = "+removedNM);
		   listaRemoved.add(rnm);
		   
		   //
		   
		   Children anf = new Children();
    	   anf.setSize(Double.parseDouble(adicionedNF+""));
    	   anf.setName("NF = "+adicionedNF);
		   listaAdicioned.add(anf);
		   
		   Children ana = new Children();
    	   ana.setSize(Double.parseDouble(adicionedCTC+""));
    	   ana.setName("CTC = "+adicionedCTC);
		   listaAdicioned.add(ana);
		   
		   Children ano = new Children();
    	   ano.setSize(Double.parseDouble(adicionedNTop+""));
    	   ano.setName("NTop = "+adicionedNTop);
		   listaAdicioned.add(ano);
		   
		   Children anm = new Children();
    	   anm.setSize(Double.parseDouble(adicionedNM+""));
    	   anm.setName("NM = "+adicionedNM);
		   listaAdicioned.add(anm);
    	   
    	   Gson g = new Gson();
    	   String saida = g.toJson(config);
    	   
    	   System.out.println(saida);
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/treeMapTitle.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("receber('"+saida+"')");
                   }
               }
           });
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getMessage());
				
			}
		});
    	   
    	   return new BrowserView(browser);
       }
       
       public static JComponent getAnd(){
    	   Browser browser = new Browser();
    	   
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/and.html");
    	   browser.addLoadListener(new LoadAdapter() {
               @Override
               public void onFinishLoadingFrame(FinishLoadingEvent event) {
                   if (event.isMainFrame()) {
                       event.getBrowser().executeJavaScript("preencherGrafico('"+getJSON("json/and.json")+"')");
                   }
               }
           });
    	   
    	   
    	   browser.addConsoleListener(new ConsoleListener() {
			
			@Override
			public void onMessage(ConsoleEvent arg0) {
				System.out.println(arg0.toString());
			}	
		});
    	   
    	   return new BrowserView(browser);
       }
       
       public static String getJSON(String arquivo){
    	   JsonElement jsonObject;
	   		//Cria o parse de tratamento
	   		JsonParser parser = new JsonParser();
	   		try {
				jsonObject = parser.parse(new FileReader("html/"+arquivo));
				return jsonObject.toString();
			} catch (JsonIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   		return null;
	    	   
        }
 
}
