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
	LiteralExpression targetOutput;

	public AssignExpression(LiteralExpression target, Expression exp) {
		this.targetOutput = target;
		this.exp = exp;
	}

	@Override
	public Object accept(ExpressionVisitor visitor) {
		return visitor.visit(this);
	}
	
	public LiteralExpression getTarget(){
		return targetOutput;
	}

	public void setTarget(LiteralExpression output){
		targetOutput = output;
	}
	
	public Expression getExp() {
		return exp;
	}
}
