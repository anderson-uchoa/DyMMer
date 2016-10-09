package br.ufc.lps.model.xml;

import br.ufc.lps.model.ModelFactory;
import br.ufc.lps.splar.core.fm.XMLFeatureModel;

public class XMLSplotModel extends XMLFeatureModel implements IXMLmodel{

	static{
		ModelFactory.getInstance().registerModel(ModelID.SPLOT_MODEL.getId(), new XMLSplotModel());
	}
	
	public XMLSplotModel(){}
	
	private XMLSplotModel(String fileName) {
		super(fileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
		
	}

	@Override
	public IXMLmodel getNewInstance(String pathFile) {
		
		return new XMLSplotModel(pathFile);
	}
	
	

}
