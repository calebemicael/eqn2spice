/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import abstractSyntax.AssignExpression;
import abstractSyntax.ConjunctionExpression;
import abstractSyntax.Declaration;
import abstractSyntax.DisjunctionExpression;
import abstractSyntax.Expression;
import abstractSyntax.NegativeExpression;
import abstractSyntax.LiteralExpression;
import abstractSyntax.visitor.ExpressionVisitor;
import abstractSyntax.visitor.GenericVisitor;
import parser.Symbol;

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
		for(LiteralExpression le: dec.getAssigns().keySet()){
			visit(le);
			sb.append(" = ");
			visit(dec.getAssigns().get(le));
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
