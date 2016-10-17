package br.ufc.lps.model.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufc.lps.model.Adaptacao;
import br.ufc.lps.model.ContextoAdaptacao;
import br.ufc.lps.model.ModelFactory;
import br.ufc.lps.model.ValorAdaptacao;
import br.ufc.lps.model.contextaware.Constraint;
import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.model.contextaware.Resolution;
import br.ufc.lps.model.normal.MeasuresWithoutContextCalculat;
import br.ufc.lps.splar.core.constraints.BooleanVariable;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureModelException;
import br.ufc.lps.splar.core.fm.FeatureModelStatistics;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.heuristics.FTPreOrderSortedECTraversalHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristicsManager;
import br.ufc.lps.splar.plugins.reasoners.bdd.javabdd.FMReasoningWithBDD;
import br.ufc.lps.splar.plugins.reasoners.bdd.javabdd.ReasoningWithBDD;
import br.ufc.lps.splar.plugins.reasoners.sat.sat4j.ReasoningWithSAT;

public class MeeasuresWithContextCalcula extends MeasuresWithoutContextCalculat implements IMeasuresWithContext {

	public static final String DEFAULT_CONTEXT = "default";
	private FeatureModel featureModel;
	private ReasoningWithBDD bddReasoner;
	private ReasoningWithSAT satReasoner;
	private FeatureModelStatistics featureModelStatistics;

	private String pathModelFile;

	private int modelID;

	private Map<String, Context> contexts;
	private Map<String, Adaptacao> adaptations;
	private Adaptacao adaptationTree;
	private Map<Context, FeatureModelStatistics> statisticsByContext;
	private Map<Context, ReasoningWithBDD> bddByContext;
	private Context currentContext;
	private String modelName;

	public MeeasuresWithContextCalcula() {
	}

	public MeeasuresWithContextCalcula(String pathModelFile, int modelID) {

		this.modelID = modelID;

		this.pathModelFile = pathModelFile;
		contexts = new HashMap<String, Context>();
		adaptations = new HashMap<String, Adaptacao>();
		adaptationTree = new Adaptacao();
		statisticsByContext = new HashMap<Context, FeatureModelStatistics>();
		bddByContext = new HashMap<Context, ReasoningWithBDD>();

		parseXMLToGetContexts();
		parseXMLToGetArvoreAdaptacao();
		parseXMLToGetAdaptacoes();
		createFeatureModelByContext();
	}

	// Parsea o XML para criar os contextos
	private void parseXMLToGetContexts() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {

			db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file

			Document doc = db.parse(pathModelFile);
			Element rootEle = doc.getDocumentElement();

			// parse contexts
			NodeList contexts = rootEle.getElementsByTagName("context");
			for (int i = 0; i < contexts.getLength(); i++) {
				Element elContext = (Element) contexts.item(i);
				String nameContext = elContext.getAttribute("name");
				List<Resolution> resolutions = new ArrayList<Resolution>();
				List<Constraint> constraintsContext = new ArrayList<Constraint>();

				NodeList elsResolutions = elContext.getElementsByTagName("resolution");
				for (int countRes = 0; countRes < elsResolutions.getLength(); countRes++) {
					Element elResolution = (Element) elsResolutions.item(countRes);
					String nameFeature = elResolution.getAttribute("feature");
					String idFeature = elResolution.getAttribute("id");
					boolean statusFeature = elResolution.getAttribute("status").equals("false") ? false : true;

					Resolution resolution = new Resolution(idFeature, nameFeature, statusFeature);
					resolutions.add(resolution);
				}

				NodeList elsConstraints = elContext.getElementsByTagName("constraint");
				for (int countCons = 0; countCons < elsConstraints.getLength(); countCons++) {
					Element elConstraint = (Element) elsConstraints.item(countCons);
					String stringConstraint = elConstraint.getTextContent();

					Constraint newConstraint = new Constraint(countCons, stringConstraint);
					constraintsContext.add(newConstraint);
				}

				Context context = new Context(nameContext, resolutions, constraintsContext);
				this.contexts.put(nameContext, context);

			}

			this.contexts.put(DEFAULT_CONTEXT,
					new Context(DEFAULT_CONTEXT, new ArrayList<Resolution>(), new ArrayList<Constraint>()));

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void parseXMLToGetAdaptacoes() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {

			db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file

			Document doc = db.parse(pathModelFile);
			Element rootEle = doc.getDocumentElement();

			// parse contexts
			NodeList contexts = rootEle.getElementsByTagName("adaptacao");
			String nomeAdaptacao = rootEle.getAttribute("nome");
			for (int i = 0; i < contexts.getLength(); i++) {
				Element elContext = (Element) contexts.item(i);
				String nameContext = elContext.getAttribute("nome");
				List<ContextoAdaptacao> contextos = new ArrayList<ContextoAdaptacao>();

				NodeList elsResolutions = elContext.getElementsByTagName("contexto");
				for (int countRes = 0; countRes < elsResolutions.getLength(); countRes++) {
					Element elResolution = (Element) elsResolutions.item(countRes);

					String nome = elResolution.getAttribute("nome");

					ContextoAdaptacao contextoAdaptacao = new ContextoAdaptacao();
					contextoAdaptacao.setNome(nome);

					List<ValorAdaptacao> listaAdaptacoes = new ArrayList<ValorAdaptacao>();
					NodeList valor = elContext.getElementsByTagName("contexto");
					for (int countValor = 0; countValor < valor.getLength(); countValor++) {
						Element elValor = (Element) valor.item(countRes);

						String nomeValor = elValor.getAttribute("nome");

						Boolean statusValor = false;
						if (elValor.getAttribute("status").equals("true"))
							statusValor = true;

						ValorAdaptacao valorAdap = new ValorAdaptacao();
						valorAdap.setNome(nomeValor);
						valorAdap.setStatus(statusValor);

						listaAdaptacoes.add(valorAdap);
					}

					contextoAdaptacao.setValorAdaptacao(listaAdaptacoes);
				}

				Adaptacao adaptacao = new Adaptacao();

				adaptacao.setNome(nomeAdaptacao);
				adaptacao.setValorAdaptacao(contextos);

				this.adaptations.put(nomeAdaptacao, adaptacao);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseXMLToGetArvoreAdaptacao() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {

			db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file

			Document doc = db.parse(pathModelFile);
			Element rootEle = doc.getDocumentElement();

			Adaptacao adaptacao = new Adaptacao();
			// parse contexts
			NodeList contexts = rootEle.getElementsByTagName("arvore_adaptacao");

			for (int i = 0; i < contexts.getLength(); i++) {

				Element elContext = (Element) contexts.item(i);

				List<ContextoAdaptacao> contextos = new ArrayList<ContextoAdaptacao>();

				NodeList elsResolutions = elContext.getElementsByTagName("contexto");

				for (int countRes = 0; countRes < elsResolutions.getLength(); countRes++) {

					Element elResolution = (Element) elsResolutions.item(countRes);

					String nome = elResolution.getAttribute("nome");

					ContextoAdaptacao contextoAdaptacao = new ContextoAdaptacao();
					contextoAdaptacao.setNome(nome);

					List<ValorAdaptacao> listaAdaptacoes = new ArrayList<ValorAdaptacao>();
					NodeList valor = elResolution.getElementsByTagName("valor");

					for (int countValor = 0; countValor < valor.getLength(); countValor++) {
						Element elValor = (Element) valor.item(countValor);

						String nomeValor = elValor.getAttribute("nome");

						Boolean statusValor = false;
						if (elValor.getAttribute("status").equals("true"))
							statusValor = true;

						ValorAdaptacao valorAdap = new ValorAdaptacao();
						valorAdap.setNome(nomeValor);
						valorAdap.setStatus(statusValor);

						listaAdaptacoes.add(valorAdap);
					}

					contextoAdaptacao.setValorAdaptacao(listaAdaptacoes);
					contextos.add(contextoAdaptacao);
				}

				adaptacao.setNome("");
				adaptacao.setValorAdaptacao(contextos);

			}

			this.adaptationTree = adaptacao;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Adaptacao getArvoreAdaptacao() {
		return adaptationTree;
	}

	public void setArvoreAdaptacao(Adaptacao arvoreAdaptacao) {
		this.adaptationTree = arvoreAdaptacao;
	}

	public FeatureModel setFeatureModel(Context context) {
		this.featureModel = context.getFeatureModel();
		this.currentContext = context;

		if (!statisticsByContext.containsKey(context)) {

			FeatureModelStatistics stats = new FeatureModelStatistics(featureModel);
			stats.update();
			ReasoningWithBDD reasoner = createBDDReasoner(context);

			statisticsByContext.put(context, stats);
			bddByContext.put(context, reasoner);
		}

		featureModelStatistics = statisticsByContext.get(context);
		bddReasoner = bddByContext.get(context);

		return context.getFeatureModel();
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Map<String, Context> getContexts() {
		return contexts;
	}

	public void setContexts(Map<String, Context> contexts) {
		this.contexts = contexts;
	}

	public Map<String, Adaptacao> getAdaptacoes() {
		return adaptations;
	}

	public void setAdaptacoes(Map<String, Adaptacao> adaptacoes) {
		this.adaptations = adaptacoes;
	}

	// For each context, it creates a model for and set active and deactive
	// features;
	private void createFeatureModelByContext() {

		for (Map.Entry<String, Context> conts : contexts.entrySet()) {
			FeatureModel otherModel = (FeatureModel) ModelFactory.getInstance().createModel(modelID, pathModelFile);
			try {

				otherModel.loadModel();
				modelName = otherModel.getName();
			} catch (FeatureModelException e) {
				e.printStackTrace();
			}

			Context context = conts.getValue();
			Map<String, Boolean> status = new HashMap<String, Boolean>();
			for (Resolution resolution : context.getResolutions()) {
				status.put(resolution.getIdFeature(), resolution.getStatus());
			}

			for (Map.Entry<String, FeatureTreeNode> node : otherModel.getNodesMap().entrySet()) {

				String idNode = node.getKey();
				if (status.containsKey(idNode) && status.get(idNode) == false)
					node.getValue().setActiveInContext(false);

			}

			context.setFeatureModel(otherModel);

		}

	}

	private ReasoningWithBDD createBDDReasoner(Context context) {

		FeatureModel otherModel = createFeatureModel(context);
		try {

			// create BDD variable order heuristic
			new FTPreOrderSortedECTraversalHeuristic("Pre-CL-MinSpan", otherModel,
					FTPreOrderSortedECTraversalHeuristic.FORCE_SORT);
			VariableOrderingHeuristic heuristic = VariableOrderingHeuristicsManager.createHeuristicsManager()
					.getHeuristic("Pre-CL-MinSpan");

			// Creates the BDD reasoner
			ReasoningWithBDD reasoner = new FMReasoningWithBDD(otherModel, heuristic, 50000, 50000, 60000, "pre-order");

			// Initialize the reasoner (BDD is created at this moment)

			reasoner.init();

			return reasoner;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * Cria um novo feature model e retira os n�s e restri��es que n�o compoem o
	 * contexto para repassar na cria��o do BDD
	 */
	private FeatureModel createFeatureModel(Context context) {

		// Cria um novo modelo
		FeatureModel otherModel = (FeatureModel) ModelFactory.getInstance().createModel(modelID, pathModelFile);

		try {
			otherModel.loadModel();
		} catch (FeatureModelException e) {
			e.printStackTrace();
		}

		// Configura os n�s que n�o far�o parte do modelo no devido contexto
		Map<String, Boolean> status = new HashMap<String, Boolean>();
		for (Resolution resolution : context.getResolutions()) {
			status.put(resolution.getIdFeature(), resolution.getStatus());
		}

		for (Map.Entry<String, FeatureTreeNode> node : otherModel.getNodesMap().entrySet()) {

			String idNode = node.getKey();
			if (status.containsKey(idNode) && status.get(idNode) == false)
				node.getValue().setActiveInContext(false);

		}

		// Busca os n�s que ser�o deletados conforme sejam desativados pelo
		// modelo
		List<FeatureTreeNode> nodesToDelete = new ArrayList<FeatureTreeNode>();

		for (FeatureTreeNode node : otherModel.getNodes()) {
			if (!node.isActiveInContext()) {
				if (!nodesToDelete.contains(node)) {
					nodesToDelete.add(node);

					otherModel.getSubtreeNodes(node, nodesToDelete);
				}
			}
		}

		// Busca as constraints que possuem n�s que n�o fazem parte do modelo
		List<PropositionalFormula> constraintsToDelete = new ArrayList<PropositionalFormula>();

		for (PropositionalFormula constraint : otherModel.getConstraints()) {
			for (BooleanVariable variable : constraint.getVariables()) {
				FeatureTreeNode auxNode = new FeatureTreeNode(variable.getID(), null, null);
				if (nodesToDelete.contains(auxNode)) {
					constraintsToDelete.add(constraint);
					break;
				}
			}
		}

		// Deleta os n�s da arvore
		for (FeatureTreeNode node : nodesToDelete) {
			otherModel.removeNodeFromParent(node);

		}

		// Deleta todos os n�s e constraints registrados
		otherModel.getNodes().removeAll(nodesToDelete);
		otherModel.getConstraints().removeAll(constraintsToDelete);

		return otherModel;

	}

	@Override
	public int numberActivatedFeatures() {
		int count = 0;

		for (Resolution resolution : currentContext.getResolutions()) {
			if (resolution.getStatus())
				count++;
		}

		return count;
	}

	@Override
	public int numberDeactivatedFeatures() {
		int count = 0;

		for (Resolution resolution : currentContext.getResolutions()) {
			if (!resolution.getStatus())
				count++;
		}

		return count;
	}

	@Override
	public int numberContextConstraints() {
		return currentContext.getConstraints().size();
	}

	@Override
	public int numberOfContexts() {
		return contexts.size() - 1;

	}

	@Override
	public int numberOfContextAffectingProductConfiguration() {

		int count = 0;

		for (Map.Entry<String, Context> c : contexts.entrySet()) {
			count += c.getValue().getConstraints().size();
		}

		return count;
	}

	@Override
	public int numberOfContextAdaptation() {
		return numberOfContextAffectingProductConfiguration();
	}

	@Override
	public int contextAdaptationExtensibility() {
		return numberOfContextAdaptation() + numberOfLeafFeatures();
	}

	@Override
	public int contextAdaptationFlexibility() {
		return numberOfContextAdaptation()
				+ (featureModelStatistics.countOptional() / featureModelStatistics.countFeatures());
	}

	@Override
	public int contextFeatures() {
		ArrayList<String> deactivated_fetures = getAllDeactivatedFeatures();
		int root = 1;

		return featureModel.countNodes() - root - deactivated_fetures.size();
	}

	@Override
	public ArrayList<String> getAllDeactivatedFeatures() {
		ArrayList<String> deactivated_fetures = new ArrayList<String>();
		String id_feature;

		for (Context context : contexts.values()) {
			for (Resolution resolution : context.getResolutions()) {
				id_feature = resolution.getIdFeature();
				if (!resolution.getStatus())
					if (!deactivated_fetures.contains(id_feature))
						deactivated_fetures.add(id_feature);
			}
		}

		return deactivated_fetures;
	}

	@Override
	public double desactivatedFeaturesByContextAdaptation() {
		int count = 0;
		for (Context context : contexts.values()) {
			for (Resolution resolution : context.getResolutions()) {
				if (!resolution.getStatus())
					count++;
			}
		}
		if (count == 0)
			return count;
		return (double) count / numberOfContexts();
	}

	@Override
	public double activatedFeaturesByContextAdaptation() {
		int count = 0;
		for (Context context : contexts.values()) {
			for (Resolution resolution : context.getResolutions()) {
				if (resolution.getStatus())
					count++;
			}
		}
		if (count == 0)
			return count;
		return (double) count / numberOfContexts();
	}

	@Override
	public int contextFeaturesContraints() {
		ArrayList<FeatureTreeNode> feature_in_constraints = new ArrayList<FeatureTreeNode>();
		
		for(Context context : contexts.values()){
			if(context.getName() != "default"){
				FeatureModel fm = context.getFeatureModel();
			
				for( Iterator<FeatureTreeNode> it = fm.nodesIterator(); it.hasNext();) {
					FeatureTreeNode node = it.next();
					if(node.isActiveInContext()){
						if(fm.isExtraConstraintVariable(node)){
							if(!feature_in_constraints.contains(node)){
								feature_in_constraints.add(node);
							}
						}
					}
				}
			}
		}	
		
		return feature_in_constraints.size();
	}

}
