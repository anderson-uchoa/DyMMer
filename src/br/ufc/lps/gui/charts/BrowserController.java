package br.ufc.lps.gui.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;

import org.jfree.data.general.Dataset;

import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.bu;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FrameLoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import br.ufc.lps.repositorio.MedidasContexto;
import br.ufc.lps.repositorio.SchemeXml;

public class BrowserController {
	   private Browser browser;
       private BrowserView view;
       
       public BrowserController() {
    	   browser = new Browser();
    	   view = new BrowserView(browser);
       }
       
       public JComponent getPie(){
    	   browser.loadURL("File://"+System.getProperty("user.dir")+"/html/pie-customTooltips.html");
    	   return view;
       }
       
       public JComponent getLine(){
    	   DataSet a = new DataSet();
    	   
    	   List<String> backgroundColor = new ArrayList<>();
    	   
    	   backgroundColor.add("#FF6384");
    	   backgroundColor.add("#36A2EB");
    	   backgroundColor.add("#FFCE56");
    	   
    	   a.setBackgroundColor(backgroundColor);
    	   
    	   List<String> hoverBackgroundColor = new ArrayList<>();
    	   
    	   hoverBackgroundColor.add("#FF6384");
    	   hoverBackgroundColor.add("#36A2EB");
    	   hoverBackgroundColor.add("#FFCE56");
    	   
    	   a.setHoverBackgroundColor(hoverBackgroundColor);
    	   
    	   List<Double> lista = new ArrayList<>();
    	   lista.add(203d);
    	   lista.add(156d);
    	   lista.add(99d);
    	   a.setData(lista);
    	   
    	   BuyerData b = new BuyerData();
    	   List<DataSet> datasets = new ArrayList<>();
    	   datasets.add(a);
    	   b.setDatasets(datasets);
    	   
    	   List<String> labels = new ArrayList<>();
    	   labels.add("Red");
    	   labels.add("Blue");
    	   labels.add("Yellow");
    	   b.setLabels(labels);
    	   
    	   ConfigSet cnf = new ConfigSet();
    	   cnf.setType("doughnut");
    	   cnf.setData(b);
    	   
    	   
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
    	   
    	   return view;
       }
       
       /*
        * 
        * var data = {
    labels: ["January", "February", "March", "April", "May", "June", "July"],
    datasets: [
        {
            label: "My First dataset",
            backgroundColor: [
                "rgba(255, 99, 132, 0.2)",
                "rgba(54, 162, 235, 0.2)",
                "rgba(255, 206, 86, 0.2)",
                "rgba(75, 192, 192, 0.2)",
                "rgba(153, 102, 255, 0.2)",
                "rgba(255, 159, 64, 0.2)"
            ],
            borderColor: [
                "rgba(255,99,132,1)",
                "rgba(54, 162, 235, 1)",
                "rgba(255, 206, 86, 1)",
                "rgba(75, 192, 192, 1)",
                "rgba(153, 102, 255, 1)",
                "rgba(255, 159, 64, 1)"
            ],
            borderWidth: 1,
            data: [65, 59, 80, 81, 56, 55, 40],
        }
    ]
};
        */
       
       public JComponent getBar(SchemeXml scheme){
    	   ConfigSet cnf = new ConfigSet();
    	   
    	   cnf.setType("bar");
    	   
    	   BuyerData buyerData = new BuyerData();
    	   
    	   List<String> labels = Arrays.asList("Number of activated features", "Number of desactivated features",
    			   "Number of context Constraints", "Activated features by context adaptation",
    			   "Desactivated features by context adaptation", "Non Context Features");
    	   
    	   buyerData.setLabels(labels);
    	   
    	   List<DataSet> datasets = new ArrayList<>();
    	   
    	   
    	   //List<String> coresBackground = new ArrayList<>();
    	   List<Double> listaData = null;
    	   DataSet dataSet = null;
    	   
    	   for(MedidasContexto medida : scheme.getMedidasContexto()){

    		   dataSet = new DataSet();
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
    		   
	    	   datasets.add(dataSet);
	    	   dataSet.setBackgroundColor(Arrays.asList("rgba(255, 99, 132, 0.2)", "rgba(54, 162, 235, 0.2)", "rgba(255, 206, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(153, 102, 255, 0.2)", "rgba(255, 159, 64, 0.2)"));
	    	   dataSet.setHoverBackgroundColor(Arrays.asList("rgba(255,99,132,1)", "rgba(54, 162, 235, 1)", "rgba(255, 206, 86, 1)", "rgba(75, 192, 192, 1)", "rgba(153, 102, 255, 1)", "rgba(255, 159, 64, 1)"));
	    	  
    	   }
    	   
    	   //datasets.add(dataSet);
    	   
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
    	   
    	   return view;
       }
}
