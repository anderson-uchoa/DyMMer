package br.ufc.lps.model.rnf;

public class NameImpacto {
	public static String getNameimpacto(int impacto){
		if(impacto == 1)
			return "+";
		else if(impacto == 2)
			return "++";
		else if(impacto == -1)
			return "-";

		return "--";
			
	}
}
