package br.ufc.lps.controller.features;

import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.fm.RootNode;
import br.ufc.lps.splar.core.fm.SolitaireFeature;

public class ControllerFeatures {

	private String arvoreDesenhada = "";

	public boolean addFeatures(FeatureTreeNode feature, TypeFeature typeFeatures, String name) {
		try {
			feature.add(getTypeFeature(feature, typeFeatures, name));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean removeFeatures(FeatureTreeNode feature){
		try {
			feature.removeFromParent();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public String getArvoreDesenhada() {
		return arvoreDesenhada;
	}
	
	public void drawTree(FeatureTreeNode root){
		arvoreDesenhada = "";
		drawTreeChildren(root);
		arvoreDesenhada = "\n"+root.toString() + "(_r)\n" + arvoreDesenhada;
	}
	
	private void drawTreeChildren(FeatureTreeNode feature){
		
		if(feature == null)
			return;
		
		if(feature.getChildCount() > 0){
			for(int i=0; i < feature.getChildCount(); i++){
				FeatureTreeNode filha = (FeatureTreeNode)feature.getChildAt(i);
				
				for(int j=0; j < feature.getLevel()+1; j++)
					arvoreDesenhada+="\t";
				
				if(filha.getTypeFeature().equals(TypeFeature.GROUP_OR) ||
						filha.getTypeFeature().equals(TypeFeature.GROUP_XOR)){
					FeatureGroup fg = (FeatureGroup) filha;
					if(fg.getMax() == -1)
						arvoreDesenhada+=":g ("+filha.getID()+") [1,*]\n";
					else
						arvoreDesenhada+=":g ("+filha.getID()+") [1,1]\n";
				}else
					arvoreDesenhada+=filha.toString()+"("+filha.getID()+")"+"\n";
				
				drawTreeChildren(filha);
			}
		}
	}
	
	
	private String getNextId(FeatureTreeNode pai){
		String id;
		
		if(pai == null)
			return null;
		
		int quantidadeFilhos = pai.getChildCount();
		
		if(quantidadeFilhos > 0){
			int idMaior = 1;
			for(int i=0; i < quantidadeFilhos; i++){
				FeatureTreeNode filho = (FeatureTreeNode) pai.getChildAt(i);
				String idFilho = filho.getID();
				String [] idParticionado = idFilho.split("_");
				String ultimoItemId = idParticionado[idParticionado.length-1];
				try{
					int ultimoValor = Integer.parseInt(ultimoItemId);
					if(ultimoValor > idMaior)
						idMaior = ultimoValor;
				}catch (Exception e) {
					System.err.println("MODELO MAL FORMADO");
					return null;
				}
			}
			id = pai.getID()+"_"+(idMaior+1);
		}else{
			id = pai.getID()+"_"+1;
		}	
		return id;
	}


	private FeatureTreeNode getTypeFeature(FeatureTreeNode featurePai, TypeFeature typeFeatures, String name) {

		String id = getNextId(featurePai);

		switch (typeFeatures) {
		case GROUP_OR:
			return new FeatureGroup(id, name, 1, -1, null);
		case GROUP_XOR:
			return new FeatureGroup(id, name, 1, 1, null);
		case OPTIONAL:
			return new SolitaireFeature(true, id, name, null);
		case MANDATORY:
			return new SolitaireFeature(false, id, name, null);
		default:
			return new RootNode(id, name, null);
		}
	}
}
