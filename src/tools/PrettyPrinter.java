/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import abstractSyntax.AssignExpression;
import abstractSyntax.ConjunctionExpression;
import abstractSyntax.DisjunctionExpression;
import abstractSyntax.NegativeExpression;
import abstractSyntax.TerminalExpression;
import abstractSyntax.visitor.ExpressionVisitor;
import java.beans.Expression;
import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class PrettyPrinter implements ExpressionVisitor{
	AssignExpression e;
	StringBuilder sb;
	public PrettyPrinter(AssignExpression e) {
		this.e = e;
		sb = new StringBuilder();
	}
	
	public void run(){
		e.accept(this);
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
	public Object visit(TerminalExpression termExp) {
		sb.append(termExp.getSymbol());
		return null;
	}
	
	public String toString(){
		return sb.toString();
	}
	
}
