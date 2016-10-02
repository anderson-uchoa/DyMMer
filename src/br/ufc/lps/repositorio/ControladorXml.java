package br.ufc.lps.repositorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.ufc.lps.contextaware.Context;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.context.SplotContextModel;
import br.ufc.lps.repositorio.constantes.Conexao;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ControladorXml{
	
	private RepositorioXml repositorioXml;
	private Gson gson;
	private static ControladorXml controladorXml;
	
	public ControladorXml() {
		this.repositorioXml = new RepositorioXml(new OkHttpClient());
		this.gson = new Gson();
	}
	
	public static ControladorXml getInstance(){
		if(controladorXml!=null)
			return controladorXml;
		
		return controladorXml = new ControladorXml();
	}
	
	public List<SchemeXml> getXml(){
		
		Request request = new Request.Builder()
			      .url(Conexao.url)
			      .build();
		
		String resultado = repositorioXml.getStringBody(request);
		
		if(resultado==null)
			return null;
		
		Type listType = new TypeToken<ArrayList<SchemeXml>>(){}.getType();
		
		return gson.fromJson(resultado, listType);
	}
	
	public boolean save(SchemeXml xml){
		System.out.println(this.schemeXmlToJson(xml));
		RequestBody requestBody = new FormBody.Builder()
			.add("scheme", this.schemeXmlToJson(xml))
			.build();
	
		Request request = new Request.Builder()
		    .url(Conexao.url)
		    .post(requestBody)
		    .build();
		
		return repositorioXml.code200(request);
	}
	
	public boolean delete(SchemeXml xml){
		
		RequestBody requestBody = new FormBody.Builder()
			.add("id", xml.get_id())
			.build();
	
		Request request = new Request.Builder()
		    .url(Conexao.url)
		    .delete(requestBody)
		    .build();
		
		return repositorioXml.code200(request);
	}
	
	private String schemeXmlToJson(SchemeXml schemeXml){
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(schemeXml);
	}
	
	public static File createFileFromXml(String xml){
		
		String myRandom = UUID.randomUUID().toString();
		
		try {
			File file = File.createTempFile("feature"+myRandom, ".xml");
			   
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		    bw.write(xml);
		    bw.close();
		    
		    return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String createXmlFromFile(File file){
		
		try {
			
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while((line=br.readLine())!= null){
			    sb.append(line);
			    sb.append("\n");
			}
			br.close();
			
			return sb.toString().trim();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean salvarXMLRepositorio(File file, SchemeXml schemaAnterior){
		
		SchemeXml scheme = new SchemeXml();
		String xml = ControladorXml.createXmlFromFile(file);
		scheme.setXml(xml);
		
		if(schemaAnterior!=null)
			scheme.set_id(schemaAnterior.get_id());
		
		ContextModel model = new SplotContextModel(file.getAbsolutePath());
		
		model.setFeatureModel(model.getContexts().get(ContextModel.DEFAULT_CONTEXT));	
		
		scheme.setNameXml(model.getModelName());
		scheme.setNumberOfFeatures(model.numberOfFeatures());
		scheme.setNumberOfOptionalFeatures(model.numberOfOptionalFeatures());
		scheme.setNumberOfMandatoryFeatures(model.numberOfMandatoryFeatures());
		scheme.setNumberOfTopFeatures(model.numberOfTopFeatures());
		
		scheme.setNumberOfLeafFeatures(model.numberOfLeafFeatures());
		
		scheme.setDepthOfTreeMax(model.depthOfTreeMax());
		
		scheme.setDepthOfTreeMedian(model.depthOfTreeMedian());
		scheme.setCognitiveComplexityOfAFeatureModel(model.cognitiveComplexityOfFeatureModel());
		scheme.setFlexibilityOfConfiguration(model.flexibilityOfConfiguration());
		scheme.setSingleCyclicDependentFeatures(model.singleCyclicDependentFeatures());
		scheme.setMultipleCyclicDependentFeatures(model.multipleCyclicDependentFeatures());
		scheme.setFeatureExtendibility(model.featureExtendibility());
		scheme.setCyclomaticComplexity(model.cyclomaticComplexity());
		scheme.setVariableCrosstreeConstraints(model.crossTreeConstraintsVariables());
		scheme.setCompoundComplexity(model.compoundComplexity());
		scheme.setNumberOfGroupingFeatures(model.groupingFeatures());
		scheme.setCrossTreeConstraintsRate(model.crossTreeConstraintsRate());
		scheme.setCoeficientOfConnectivityDensity(model.coefficientOfConnectivityDensity());
		scheme.setNumberOfVariableFeatures(model.numberOfVariableFeatures());
		scheme.setSingleVariationPointsFeatures(model.singleVariationPointsFeatures());
		scheme.setMultipleVariationPointsFeatures(model.multipleVariationPointsFeatures());
		scheme.setRigidNoVariationPointsFeatures(model.rigidNotVariationPointsFeatures());
		scheme.setRatioOfVariability(model.ratioVariability());
		scheme.setNumberOfValidConfigurations(model.numberOfValidConfigurations());
		scheme.setBranchingFactorsMax(model.branchingFactorsMax());
		scheme.setOrRate(model.orRate());
		scheme.setXorRate(model.xorRate());
		scheme.setBranchingFactorsMedian(model.branchingFactorsMedian());
		scheme.setNonFunctionalCommonality(model.nonFunctionCommonality());
		scheme.setNumberOfContexts(model.numberOfContexts());
		
		List<MedidasContexto> listaDeMedidasPorContexto = new ArrayList<MedidasContexto>();
		
		for(Entry<String, Context> contexts : model.getContexts().entrySet()){
			
			model.setFeatureModel(contexts.getValue());
			
			MedidasContexto medida = new MedidasContexto();
			medida.setNameContext(contexts.getKey());
			medida.setNumberOfFeatures(model.numberOfFeatures());
			medida.setNumberOfOptionalFeatures(model.numberOfOptionalFeatures());
			medida.setNumberOfMandatoryFeatures(model.numberOfMandatoryFeatures());
			medida.setNumberOfTopFeatures(model.numberOfTopFeatures());
			medida.setNumberOfLeafFeatures(model.numberOfLeafFeatures());
			medida.setDepthOfTreeMax(model.depthOfTreeMax());
			medida.setDepthOfTreeMedian(model.depthOfTreeMedian());
			medida.setCognitiveComplexityOfAFeatureModel(model.cognitiveComplexityOfFeatureModel());
			medida.setFlexibilityOfConfiguration(model.flexibilityOfConfiguration());
			medida.setSingleCyclicDependentFeatures(model.singleCyclicDependentFeatures());
			medida.setMultipleCyclicDependentFeatures(model.multipleCyclicDependentFeatures());
			medida.setFeatureExtendibility(model.featureExtendibility());
			medida.setCyclomaticComplexity(model.cyclomaticComplexity());
			medida.setVariableCrosstreeConstraints(model.crossTreeConstraintsVariables());
			medida.setCompoundComplexity(model.compoundComplexity());
			medida.setNumberOfGroupingFeatures(model.groupingFeatures());
			medida.setCrossTreeConstraintsRate(model.crossTreeConstraintsRate());
			medida.setCoeficientOfConnectivityDensity(model.coefficientOfConnectivityDensity());
			medida.setNumberOfVariableFeatures(model.numberOfVariableFeatures());
			medida.setSingleVariationPointsFeatures(model.singleVariationPointsFeatures());
			medida.setMultipleVariationPointsFeatures(model.multipleVariationPointsFeatures());
			medida.setRigidNoVariationPointsFeatures(model.rigidNotVariationPointsFeatures());
			medida.setRatioOfVariability(model.ratioVariability());
			medida.setNumberOfValidConfigurations(model.numberOfValidConfigurations());
			medida.setBranchingFactorsMax(model.branchingFactorsMax());
			medida.setOrRate(model.orRate());
			medida.setXorRate(model.xorRate());
			medida.setBranchingFactorsMedian(model.branchingFactorsMedian());
			medida.setNonFunctionalCommonality(model.nonFunctionCommonality());
			medida.setNumberOfActivatedFeatures(model.numberActivatedFeatures());
			medida.setNumberOfDeactivatedFeatures(model.numberDeactivatedFeatures());
			medida.setNumberOfContextConstraints(model.numberContextConstraints());
			medida.setActivatedFeaturesByContextAdaptation(model.activatedFeaturesByContextAdaptation());
			medida.setDesactivatedFeaturesByContextAdaptation(model.desactivatedFeaturesByContextAdaptation());
			medida.setNonContextFeatures(model.contextFeatures());
			
			listaDeMedidasPorContexto.add(medida);
		}
		
		scheme.setMedidasContexto(listaDeMedidasPorContexto);
		
		return ControladorXml.getInstance().save(scheme);
	}	
	
}
