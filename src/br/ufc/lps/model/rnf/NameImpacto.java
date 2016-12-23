package br.ufc.lps.model.rnf;

public class NameImpacto {
	
	public static String getNameByImpacto(int impacto){
		if(impacto == 1)
			return "+";
		else if(impacto == 2)
			return "++";
		else if(impacto == -1)
			return "-";

		return "--";
	}
	
	public static int getImpactoByName(String impacto){
		
		if(impacto.equals("+"))
			return 1;
		else if(impacto.equals("++"))
			return 2;
		else if(impacto.equals("-"))
			return -1;

		return -2;
	}
}
