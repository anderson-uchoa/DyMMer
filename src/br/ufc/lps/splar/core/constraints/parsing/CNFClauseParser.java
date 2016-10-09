package br.ufc.lps.splar.core.constraints.parsing;

import br.ufc.lps.splar.core.constraints.BooleanVariable;
import br.ufc.lps.splar.core.constraints.CNFClause;
import br.ufc.lps.splar.core.constraints.CNFLiteral;



/*
 * CODING OF THIS CLASS HAS NOT BEEN FINISHED 
 */

public class CNFClauseParser {

	protected int index;
	protected static String negationToken = "~";
	protected static String tokens = negationToken + "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public CNFClauseParser() {
	}
	
	public CNFClause parse(String cnfClause) throws CNFClauseParseException {
		
		index = 0;
		
		cnfClause = cnfClause.trim();
		if ( cnfClause.length() == 0 ) {
			throw new CNFClauseParseException("Error parsing CNF clause (empty clause not permitted).");
		}
		//Se há parenteses, não é lido
		if ( cnfClause.indexOf(")") != -1 || cnfClause.indexOf("(") != -1) {
			throw new CNFClauseParseException("Error parsing CNF clause (do not use paranthesis).");
		}
		
		//Cria uma nova clause
		CNFClause cnfClauseObj = new CNFClause();
		//Pula os espaços em branco entre as variaveis
		skipBlanks(cnfClause);
		
		//Verifica se o index não chegou ao final da clause
		while( !EOF(cnfClause) ) {
			
			//Cria uma string com o primeiro token valido até um não valido (o caractere de espaço entre as variaveis ou or, por exemplo)
			String literal = parseLiteral(cnfClause);
			//Cria a variavel literal, falsa ou positiva
			createLiteral(cnfClauseObj, cnfClause, literal);
			skipBlanks(cnfClause);
			if ( !EOF(cnfClause)) {
				//Apenas percore a operação
				parseOR(cnfClause);
				skipBlanks(cnfClause);
				if ( EOF(cnfClause)) {
					throw new CNFClauseParseException("Error parsing CNF clause (a literal is missing after operator 'OR').");
				}
			}
		}
		return cnfClauseObj;
	}
	
	protected void createLiteral(CNFClause cnfClauseObj, String cnfClause, String literal) throws CNFClauseParseException {
		// if variable name does not exist, throws exception				
		CNFLiteral cnfLiteralObj = null;
		if (literal.startsWith(negationToken)) {					
			if (literal.length()-negationToken.length()==0) {
				throw new CNFClauseParseException("Error parsing CNF clause '" + cnfClause + "' (a variable name is missing).");
			}
			cnfLiteralObj = new CNFLiteral(new BooleanVariable(literal.substring(negationToken.length())),false);
		}
		else {
			cnfLiteralObj = new CNFLiteral(new BooleanVariable(literal), true);
		}
		cnfClauseObj.addLiteral(cnfLiteralObj);
	}
	
	protected boolean parseOR(String cnfClause) throws CNFClauseParseException {
		if ( currentChar(cnfClause).compareToIgnoreCase("O") == 0 ) {
			index++;
			if ( currentChar(cnfClause).compareToIgnoreCase("R") == 0 ) {
				index++;
				return true;
			}
		}
		return false;
	}
	
	protected String currentChar(String cnfClause) {
		return cnfClause.substring(index,index+1);
	}
	
	protected String parseLiteral(String cnfClause) throws CNFClauseParseException {
		String literal = "";
		if ( isValidToken(currentChar(cnfClause)) ) {
			literal += currentChar(cnfClause);
			index++;
			while( !EOF(cnfClause) && isValidToken(currentChar(cnfClause))) {
				literal += currentChar(cnfClause);
				index++;
			}		
		}
		else {
			throw new CNFClauseParseException("Error parsing CNF clause '" + cnfClause + "' (please use only letter, digits and symbol '_' to name variables - to negate a variable use symbol '" + negationToken + "').");
		}
		return literal;		
	}
	
	protected boolean EOF(String cnfClause) {
		if ( index >= cnfClause.length() ) {
			return true;
		}
		return false;
	}
	
	protected boolean isValidToken(String str) {
		if (tokens.indexOf(str)!= -1) {
			return true;
		}
		return false;
	}
	
	
	protected void skipBlanks(String cnfClause) {
		if (!EOF(cnfClause)) { 
			while (currentChar(cnfClause).startsWith(" ")) {
				index++;
			}
		}
	}	
}
