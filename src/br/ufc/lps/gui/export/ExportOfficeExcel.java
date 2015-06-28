package br.ufc.lps.gui.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import br.ufc.lps.contextaware.Context;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.context.IContextModel;
import br.ufc.lps.model.context.SplotContextModel;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.model.normal.Model;
import br.ufc.lps.model.normal.SplotModel;

//TODO Needs to refactor this class
public class ExportOfficeExcel {

	private File files[];
	private static final String PATH_EXPORT_DIRECTORY = "exported";
	private List<ContextModel> modelsContext;
	private List<Model> models; 
	
	private static final String[] METRICS = {"Non-Functional Commonality (NFC)", "Number of features (NF)", "Number of top features (NTop)",
										"Number of leaf Features (NLeaf)", "Depth of tree Max (DT Max)", "Depth of tree Median (DT Median)", "Cognitive Complexity of a Feature Model (I)", 
										"Feature EXtendibility (FEX)", "Flexibility of conﬁguration (FoC)", "Single Cyclic Dependent Features (SCDF)",
										"Multiple Cyclic Dependent Features (MCDF)", "Cyclomatic complexity", "Compound Complexity",
										"Cross-tree constraints (CTC)", "Cross-tree constraints Rate (CTCR)", "Coefﬁcient of connectivity-density (CoC)", "Number of variable features", 
										"Number of variation points", "Single Hotspot Features", "Multiple Hotspot Features", "Rigid Nohotspot Features",
										"Ratio of variability (RoV)", "Number of valid conﬁgurations (NVC)", "Branching Factor Max", "Branching Factor Mean", "Branching Factor Median",
										"Or Rate", "Xor Rate"};
	
	private static final String[] METRICS_CONTEXT = {"Number of Activated Features", "Number of Deactivated Features", "Number of Context Constraints"};
	private static final String[] METRICS_WITHOUT_CONTEXT = {"Number of Contexts"};
	
	public ExportOfficeExcel(File files[]) {
		
		this.files = files;
		modelsContext = new ArrayList<ContextModel>();
		models = new ArrayList<Model>();
		createModels();
	}
	
	private void createModels(){
		
		
		for(int i = 0; i < files.length; i++){
							
			//models.add(i, new SplotModel(files[i].getAbsolutePath()));
			modelsContext.add(i,new SplotContextModel(files[i].getAbsolutePath()));
		}
		
	}
	
	private void createExportDirectory(){
		
		File exportDirectory = new File(PATH_EXPORT_DIRECTORY);
		if(!exportDirectory.exists())
			exportDirectory.mkdir();
		
	}
	
	
	 
	
	public void standardExport(String fileName){

		List<Object[]> datas = new ArrayList<Object[]>();
		
		for(int i = 0; i < modelsContext.size(); i++){
			
			ContextModel model = modelsContext.get(i);
			
			model.setFeatureModel(model.getContexts().get(ContextModel.DEFAULT_CONTEXT));
			
			String modelName = model.getModelName();
			
			Object dataModel[] = {model.nonFunctionCommonality(), model.numberOfFeatures(), model.numberOfTopFeatures(),
						  model.numberOfLeafFeatures(), model.depthOfTreeMax(),model.depthOfTreeMedian(), model.cognitiveComplexityOfFeatureModel(),
						  model.featureExtendibility(), model.flexibilityOfConfiguration(), model.singleCyclicDependentFeatures(),
						  model.multipleCyclicDependentFeatures(), model.cyclomaticComplexity(), model.compoundComplexity(),
						  model.crossTreeConstraints(), model.crossTreeConstraintsRate(), model.coefficientOfConnectivityDensity(), model.numberOfVariableFeatures(),
						  model.numberOfVariationPoints(), model.singleVariationPointsFeatures(), model.multipleVariationPointsFeatures(),
						  model.rigidNotVariationPointsFeatures(), model.productLineTotalVariability(), model.numberOfValidConfigurations(), 
						  model.branchingFactorsMax(), model.branchingFactorsMean(), model.branchingFactorsMedian(), model.orRate(), model.xorRate(),model.numberOfContexts(),modelName};
		
			
			datas.add(dataModel);
			
		}
		
		export(datas, fileName, false);
	}
	
	public void exportWithContexts(String fileName){

		List<Object[]> datas = new ArrayList<Object[]>();
		
		for(int i = 0; i < modelsContext.size(); i++){
			
			
			ContextModel modelContext = modelsContext.get(i);
				
			for(Entry<String, Context> contexts : modelContext.getContexts().entrySet()){
				
				if(contexts.getKey().equals(ContextModel.DEFAULT_CONTEXT))
					continue;
				
				modelContext.setFeatureModel(contexts.getValue());
				String contextName = modelContext.getModelName() + " (" + contexts.getKey() + ")";
				Object dataModelContext[] = {modelContext.nonFunctionCommonality(), modelContext.numberOfFeatures(), modelContext.numberOfTopFeatures(),
						modelContext.numberOfLeafFeatures(), modelContext.depthOfTreeMax(),modelContext.depthOfTreeMedian(), modelContext.cognitiveComplexityOfFeatureModel(),
						modelContext.featureExtendibility(), modelContext.flexibilityOfConfiguration(), modelContext.singleCyclicDependentFeatures(),
						modelContext.multipleCyclicDependentFeatures(), modelContext.cyclomaticComplexity(), modelContext.compoundComplexity(),
						modelContext.crossTreeConstraints(), modelContext.crossTreeConstraintsRate(), modelContext.coefficientOfConnectivityDensity(), modelContext.numberOfVariableFeatures(),
						modelContext.numberOfVariationPoints(), modelContext.singleVariationPointsFeatures(), modelContext.multipleVariationPointsFeatures(),
						modelContext.rigidNotVariationPointsFeatures(), modelContext.productLineTotalVariability(), modelContext.numberOfValidConfigurations(),
						modelContext.branchingFactorsMax(), modelContext.branchingFactorsMean(), modelContext.branchingFactorsMedian(), modelContext.orRate(), modelContext.xorRate(),
						modelContext.numberActivatedFeatures(), modelContext.numberDeactivatedFeatures(), modelContext.numberContextConstraints(), contextName};
						
				datas.add(dataModelContext);
				
			}
			
		}
		

		export(datas, fileName, true);

		
	}
	
	private void export(List<Object[]> datas, String fileName, boolean withContext){
		
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("First sheet");
		
		
		
		CellStyle styleHeader = workbook.createCellStyle();
		styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
		styleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		Font font = workbook.createFont();
		font.setColor(IndexedColors.WHITE.index);
		
		styleHeader.setFont(font);
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Metrics");
		cell.setCellStyle(styleHeader);
			
		int countCell = 1;
		for(Object[] data : datas){
			Cell tempCell = row.createCell(countCell);
			tempCell.setCellValue((String)data[data.length-1]);
			tempCell.setCellStyle(styleHeader);
			countCell++;
		}
		
	
		for(int i = 0; i < METRICS.length; i++){
			
			
			String metric = METRICS[i];	
			Row currentRow = sheet.createRow(i+1);
			
			int currentCell = 0;
			
			Cell cellMetric = currentRow.createCell(currentCell);
			cellMetric.setCellValue(metric);
			currentCell++;
			
			for(int j = 0; j < datas.size(); j++){
				Object[] data = datas.get(j);	
				Cell cellTemp = currentRow.createCell(currentCell);
				
				if(data[i] instanceof Integer)
					cellTemp.setCellValue((Integer)data[i]);
				else
					cellTemp.setCellValue((Double)data[i]);
				currentCell++;

				
			}
			
			
		}
		
		int currentPosition = METRICS.length;
		
		if(withContext){
			
			for(int i = 0; i < METRICS_CONTEXT.length; i++){
				
				
				String metric = METRICS_CONTEXT[i];	
				Row currentRow = sheet.createRow(currentPosition+1);
				
				
				int currentCell = 0;
				
				Cell cellMetric = currentRow.createCell(currentCell);
				cellMetric.setCellValue(metric);
				currentCell++;
				
				for(int j = 0; j < datas.size(); j++){
					Object[] data = datas.get(j);	
										
						Cell cellTemp = currentRow.createCell(currentCell);
											
						
						if(data[currentPosition] instanceof Integer)
							cellTemp.setCellValue((Integer)data[currentPosition]);
						else
							cellTemp.setCellValue((Double)data[currentPosition]);
						currentCell++;
						
				}
						
					
				currentPosition++;

					
			}
			
			
			
		}else{
			
			for(int i = 0; i < METRICS_WITHOUT_CONTEXT.length; i++){
	
			
				String metric = METRICS_WITHOUT_CONTEXT[i];	
				Row currentRow = sheet.createRow(currentPosition+1);
				
				
				int currentCell = 0;
				
				Cell cellMetric = currentRow.createCell(currentCell);
				cellMetric.setCellValue(metric);
				currentCell++;
				
				for(int j = 0; j < datas.size(); j++){
					Object[] data = datas.get(j);	
					Cell cellTemp = currentRow.createCell(currentCell);
					
					if(data[currentPosition] instanceof Integer)
						cellTemp.setCellValue((Integer)data[currentPosition]);
					else
						cellTemp.setCellValue((Double)data[currentPosition]);
					currentCell++;

					
				}
				
				currentPosition++;
				
				
			}
			
		}
			
		
		for(short i = 0; i < sheet.getRow(0).getLastCellNum(); i++)
			sheet.autoSizeColumn(i);
		
		
		
		createExportDirectory();
		
		try {
		    FileOutputStream out = 
		            new FileOutputStream(new File(PATH_EXPORT_DIRECTORY +"/"+ fileName + ".xls"));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
	
}
