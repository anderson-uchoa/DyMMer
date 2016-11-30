package br.ufc.lps.controller.xml;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import br.ufc.lps.model.adaptation.ValueQuantificationBool;
import br.ufc.lps.model.adaptation.ValueQuantificationPadrao;
import br.ufc.lps.model.contextaware.Resolution;
import br.ufc.lps.model.rnf.ContextoRnf;
import br.ufc.lps.model.rnf.ValorContextoRnf;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeData;
import br.ufc.lps.view.trees.rnf.NFP;

public class WriteXMLmodel {

	public static Node getContext(Document doc, String name, List<Resolution> resolutions, List<String> constraints) {
		Element context = doc.createElement("context");
		context.setAttribute("name", name);

		for (Resolution resolution : resolutions)
			context.appendChild(getResolution(doc, context, resolution.getNameFeature(), resolution.getIdFeature(),
					resolution.getStatus()));

		context.appendChild(getConstraint(doc, context, constraints));

		return context;
	}

	public static Node getResolution(Document doc, Element element, String name, String id, boolean status) {
		Element node = doc.createElement("resolution");
		node.setAttribute("feature", name);
		node.setAttribute("id", id);
		node.setAttribute("status", status ? "true" : "false");

		return node;
	}

	public static Node getConstraint(Document doc, Element element, List<String> constraints) {

		Element node = doc.createElement("constraints");
		for (String constraint : constraints) {
			Element elemConstraint = doc.createElement("constraint");
			elemConstraint.setTextContent(constraint);
			node.appendChild(elemConstraint);
		}
		return node;
	}

	public static Node getAdaptacao(Document doc, DefaultMutableTreeNode root, String nome) {
		Element adaptacao = doc.createElement("adaptacao");
		adaptacao.setAttribute("nome", nome);

		for (int i = 0; i < root.getChildCount(); i++)
			adaptacao.appendChild(getAdaptacaoContexto(doc, (DefaultMutableTreeNode) root.getChildAt(i)));

		return adaptacao;
	}

	public static Node getAdaptacaoContexto(Document doc, DefaultMutableTreeNode contexto) {
		Element context = doc.createElement("contexto");
		context.setAttribute("nome", contexto.toString());

		for (int i = 0; i < contexto.getChildCount(); i++) {
			DefaultMutableTreeNode filho = (DefaultMutableTreeNode) contexto.getChildAt(i);
			CheckBoxNodeData dado = (CheckBoxNodeData) filho.getUserObject();
			context.appendChild(getAdaptacaoContextoValor(doc, dado.getText(), dado));
		}
		return context;
	}

	private static Node getAdaptacaoContextoValor(Document doc, String name, CheckBoxNodeData check) {
		Element node = doc.createElement("valor");
		node.setAttribute("nome", name);
		node.setAttribute("status", check.isChecked() ? "true" : "false");
		
		if(check.getValueQuantification()==null){
			System.out.println("check -> get Value in Quantification is NULL");
			return node;
		}

		if(check.getValueQuantification() instanceof ValueQuantificationBool){
			ValueQuantificationBool vb = (ValueQuantificationBool) check.getValueQuantification();
			node.setAttribute("type", "bool");
			node.setAttribute("padrao", vb.getPadrao());
			node.setAttribute("is_quantification", vb.getIsQuantification() ? "true" : "false");
			if(vb.getIsQuantification())
				node.setAttribute("value_quantification", vb.getValueQuantification());
		}else if(check.getValueQuantification() instanceof ValueQuantificationPadrao){
			ValueQuantificationPadrao vp = (ValueQuantificationPadrao) check.getValueQuantification();
			node.setAttribute("type", "padrao");
			node.setAttribute("padrao", vp.getPadrao());
			node.setAttribute("value_quantification_1", vp.getValueQuantification1());
			node.setAttribute("quantification_1", vp.getQuantification1());

			if(vp.getIsInterval()){
				node.setAttribute("quantification_2", vp.getQuantification2());
				node.setAttribute("value_quantification_2", vp.getValueQuantification2());
			}
			
			node.setAttribute("is_interval", vp.getIsInterval() ? "true" : "false");
		}
		return node;
	}

	public static Node getArvoreAdaptacao(Document doc, DefaultMutableTreeNode root) {

		Element adaptacao = doc.createElement("arvore_adaptacao");

		for (int i = 0; i < root.getChildCount(); i++)
			adaptacao.appendChild(getAdaptacaoContexto(doc, (DefaultMutableTreeNode) root.getChildAt(i)));

		return adaptacao;
	}
	
	// ARVORE RNFs
	public static Node getArvoreRnf(Document doc, DefaultMutableTreeNode root) {

		Element arvore = doc.createElement("arvore_rnf");

		for (int i = 0; i < root.getChildCount(); i++)
			arvore.appendChild(getCaracteristicaRnf(doc, (DefaultMutableTreeNode) root.getChildAt(i)));

		return arvore;
	}
	
	private static Node getCaracteristicaRnf(Document doc, DefaultMutableTreeNode carac) {

		Element caracteristica = doc.createElement("caracteristica");
		caracteristica.setAttribute("nome", carac.toString());
		
		for (int i = 0; i < carac.getChildCount(); i++)
			caracteristica.appendChild(getSubcaracteristicaRnf(doc, (DefaultMutableTreeNode) carac.getChildAt(i)));

		return caracteristica;
	}
	
	private static Node getSubcaracteristicaRnf(Document doc, DefaultMutableTreeNode sub) {

		Element subcaracteristica = doc.createElement("subcaracteristica");
		subcaracteristica.setAttribute("nome", sub.toString());
		
		for (int i = 0; i < sub.getChildCount(); i++)
			subcaracteristica.appendChild(getPropriedadeNFuncionalRnf(doc, (DefaultMutableTreeNode) sub.getChildAt(i)));

		return subcaracteristica;
	}
	
	private static Node getPropriedadeNFuncionalRnf(Document doc, DefaultMutableTreeNode pnf) {
		
		NFP vpnf = (NFP) pnf;
		
		Element propriedadenf = doc.createElement("propriedade_n_funcional");
		propriedadenf.setAttribute("nome", pnf.toString());
		propriedadenf.setAttribute("padrao", vpnf.getPadrao());

		return propriedadenf;
	}
	
	//CONTEXTO RNFS
	
	public static Node getContextoRnf(Document doc, ContextoRnf contexto) {
		
		Element contextornf = doc.createElement("contexto_rnf");
		contextornf.setAttribute("nome", contexto.getNome());
		
		for (ValorContextoRnf valorContextoRnf : contexto.getValorContextoRnf())
			contextornf.appendChild(getValorContextoRnf(doc, valorContextoRnf));

		return contextornf;
	}
	
	private static Node getValorContextoRnf(Document doc, ValorContextoRnf valorContextoRnf) {

		Element propriedadenf = doc.createElement("valor_rnf");
		propriedadenf.setAttribute("id_feature", valorContextoRnf.getIdFeature());
		propriedadenf.setAttribute("id_rnf", valorContextoRnf.getIdRnf());
		propriedadenf.setAttribute("impacto", valorContextoRnf.getImpacto()+"");
		propriedadenf.setAttribute("nome_feature",  valorContextoRnf.getNomeFeature());

		return propriedadenf;
	}
}
