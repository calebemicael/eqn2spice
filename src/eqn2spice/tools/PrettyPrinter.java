/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.tools;

import eqn2spice.abstractSyntax.AssignExpression;
import eqn2spice.abstractSyntax.ConjunctionExpression;
import eqn2spice.abstractSyntax.Declaration;
import eqn2spice.abstractSyntax.DisjunctionExpression;
import eqn2spice.abstractSyntax.Expression;
import eqn2spice.abstractSyntax.NegativeExpression;
import eqn2spice.abstractSyntax.LiteralExpression;
import eqn2spice.abstractSyntax.visitor.ExpressionVisitor;
import eqn2spice.abstractSyntax.visitor.GenericVisitor;
import eqn2spice.parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class PrettyPrinter implements ExpressionVisitor,GenericVisitor{
	Declaration d;
	StringBuilder sb;
	public PrettyPrinter(Declaration d) {
		this.d = d;
		sb = new StringBuilder();
	}

	@Override
	public Object visit(Declaration dec) {
		sb.append("INORDER = ");
		for(LiteralExpression le: dec.getInputs()){
			visit(le);
			sb.append(" ");
		}
		sb.append(";\n");
		sb.append("OUTORDER = ");
		for(LiteralExpression le: dec.getOutputs()){
			visit(le);
			sb.append(" ");
		}
		sb.append(";\n");
		for(AssignExpression ae: dec.getAssigns()){
			visit(ae.getTarget());
			sb.append(" = ");
			visit(ae.getExp());
		}
		sb.append(";\n");
		
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		return null;
	}
	public void run(){
		d.accept(this);
	}

	@Override
	public Object visit(AssignExpression assExp) {
		sb.append(assExp.getTarget());
		sb.append(" = ");
		assExp.getExp().accept(this);
		return null;
	}

	@Override
	public Object visit(ConjunctionExpression conjExp) {
		//sb.append("(");
		conjExp.getLeft().accept(this);
		sb.append("*");
		conjExp.getRight().accept(this);
		//sb.append(")");
		return null;
	}

	@Override
	public Object visit(DisjunctionExpression disjExp) {
		//sb.append("(");
		disjExp.getLeft().accept(this);
		sb.append("+");
		disjExp.getRight().accept(this);
		//sb.append(")");
		return null;
	}

	@Override
	public Object visit(NegativeExpression negExp) {
		sb.append("!");
		boolean printPar = false; 
		printPar |= negExp.getExp() instanceof ConjunctionExpression;
		printPar |= negExp.getExp() instanceof DisjunctionExpression;
		
		if(printPar){
			sb.append("(");
		}		
		negExp.getExp().accept(this);
		if(printPar){
			sb.append(")");
		}
		return null;
	}

	@Override
	public Object visit(LiteralExpression termExp) {
		sb.append(termExp.getSymbol());
		return null;
	}

	@Override
	public Object visit(Expression exp) {
		if(exp instanceof NegativeExpression){
			visit((NegativeExpression) exp);
		}else if(exp instanceof ConjunctionExpression){
			visit((ConjunctionExpression) exp);
		}else if(exp instanceof DisjunctionExpression){
			visit((DisjunctionExpression) exp);
		}else if(exp instanceof LiteralExpression){
			visit((LiteralExpression) exp);
		}else if(exp instanceof AssignExpression){
			visit((AssignExpression) exp);
		}
		return null;
	}
	public String toString(){
		return sb.toString();
	}
	
}
