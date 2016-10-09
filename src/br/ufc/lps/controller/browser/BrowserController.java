package br.ufc.lps.controller.browser;

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
import br.ufc.lps.repositorio.MedidasContexto;
import br.ufc.lps.repositorio.SchemeXml;

public class BrowserController {
       
       public static JComponent getBar(SchemeXml scheme){
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
    		   String rgbBack = "rgba("+r+", "+g+", "+b+", 1)";
    		   String rgbHover = "rgba("+r+", "+g+", "+b+", 0.5)";
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
	    	   exemplo.setY(Double.parseDouble(scheme.get(i).getDepthOfTreeMax().toString()));
	    	   exemplo.setX(Double.parseDouble(scheme.get(i).getNumberOfLeafFeatures().toString()));
	    	   exemplo.setR(20.0);
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
    	   xlabelSet.setLabelString("Número de Features Folhas");
    	   xaxes.setScaleLabel(xlabelSet);
    	   List<AxesSet> listax = new ArrayList<>();
    	   listax.add(xaxes);
    	   scales.setxAxes(listax);
    	   //yAxes of SCALES
    	   AxesSet yaxes = new AxesSet();
    	   yaxes.setDisplay(true);
    	   ScaleLabelSet ylabelSet = new ScaleLabelSet();
    	   ylabelSet.setDisplay(true);
    	   ylabelSet.setLabelString("Profundidade Máxima da Árvore");
    	   yaxes.setScaleLabel(ylabelSet);
    	   List<AxesSet> listay = new ArrayList<>();
    	   listay.add(yaxes);
    	   scales.setyAxes(listay);
    	   
    	   optionsSet.setScales(scales);
    	   
    	   //TITLE OF OPTIONS
    	   TitleSet titleSet = new TitleSet();
    	   titleSet.setDisplay(true);
    	   titleSet.setText("Relação entre Profundidade Máxima da Árvore e Número de Features Folhas dos modelos");
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

}
