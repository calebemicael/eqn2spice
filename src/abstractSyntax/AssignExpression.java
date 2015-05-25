/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax;

import abstractSyntax.visitor.ExpressionVisitor;
import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class AssignExpression extends Expression{
	Expression exp;
	Symbol targetOutput;

	public AssignExpression(Expression exp) {
		this.exp = exp;
	}

	@Override
	public Object accept(ExpressionVisitor visitor) {
		return visitor.visit(this);
	}
	
	public Symbol getTarget(){
		return targetOutput;
	}

	public void setTarget(LiteralExpression output){
		targetOutput = output.getSymbol();
	}
	
	public Expression getExp() {
		return exp;
	}
}
