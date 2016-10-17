package br.ufc.lps.controller.xml;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import br.ufc.lps.model.contextaware.Resolution;
import br.ufc.lps.view.trees.CheckBoxNodeData;

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

	public static void changeArvoreXml(Document doc, String xml) {
		doc.getElementById("feature_tree").setNodeValue(xml);
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
			context.appendChild(getAdaptacaoContextoValor(doc, dado.getText(), dado.isChecked()));
		}
		return context;
	}

	public static Node getAdaptacaoContextoValor(Document doc, String name, boolean status) {
		Element node = doc.createElement("valor");
		node.setAttribute("nome", name);
		node.setAttribute("status", status ? "true" : "false");

		return node;
	}

	public static Node getArvoreAdaptacao(Document doc, DefaultMutableTreeNode root) {

		Element adaptacao = doc.createElement("arvore_adaptacao");

		for (int i = 0; i < root.getChildCount(); i++)
			adaptacao.appendChild(getAdaptacaoContexto(doc, (DefaultMutableTreeNode) root.getChildAt(i)));

		return adaptacao;
	}

}
