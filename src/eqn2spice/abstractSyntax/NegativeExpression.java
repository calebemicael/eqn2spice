/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.abstractSyntax;

import eqn2spice.abstractSyntax.visitor.ExpressionVisitor;

/**
 *
 * @author calebemicael
 */
public class NegativeExpression extends Expression{
	Expression exp;

	public NegativeExpression(Expression exp) {
		this.exp = exp;
	}
	
	@Override
	public Object accept(ExpressionVisitor visitor) {
		return visitor.visit(this);
	}
	
	public Expression getExp() {
		return exp;
	}
	
}
