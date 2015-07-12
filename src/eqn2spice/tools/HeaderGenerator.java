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

/**
 *
 * @author calebemicael
 */
public class HeaderGenerator implements ExpressionVisitor,GenericVisitor{
	Declaration d;
	String name;
	StringBuilder sb;
	
	public HeaderGenerator(Declaration d, String name) {
		this.name = name;
		this.d = d;
		sb = new StringBuilder();
	}

	@Override
	public Object visit(Declaration dec) {
		sb.append(".subckt ");
		sb.append(this.name);
		sb.append(" ");
		for(LiteralExpression le: dec.getInputs()){
			visit(le);
			sb.append(" ");
		}
		for(LiteralExpression le: dec.getOutputs()){
			visit(le);
			sb.append(" ");
		}
		sb.append("vcc gnd\n");
		
		return null;
	}
	
	public void run(){
		d.accept(this);
	}

	@Override
	public Object visit(AssignExpression assExp) {
		return null;
	}

	

	@Override
	public Object visit(ConjunctionExpression conjExp) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Object visit(DisjunctionExpression disjExp) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Object visit(NegativeExpression negExp) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
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
