package br.ufc.lps.splar.plugins.reasoners.sat.sat4j;

import org.sat4j.core.VecInt;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;

import br.ufc.lps.splar.core.constraints.CNFClause;
import br.ufc.lps.splar.core.constraints.CNFLiteral;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.fm.FeatureModel;


public class FMReasoningWithSAT extends FTReasoningWithSAT {

	
	public FMReasoningWithSAT(String solverName, FeatureModel featureModel, int timeout) {
		super(solverName, featureModel, timeout);
	}
	
	protected void addSolverClauses(ISolver solver) throws Exception {
		super.addSolverClauses(solver);		
		// add extra constraints
		for( PropositionalFormula formula : featureModel.getConstraints() ) {
			for( CNFClause clause : formula.toCNFClauses() ) {
				IVecInt vectInt = new VecInt(clause.countLiterals());
				for( CNFLiteral literal : clause.getLiterals() ) {
					int signal = literal.isPositive() ? 1 : -1;
					int varID = getVariableIndex(literal.getVariable().getID());
					vectInt.push(signal * varID);
				}
				solver.addClause(vectInt);
//				System.out.println("EC: " + vectInt);
			}
		}		
	}		
}
