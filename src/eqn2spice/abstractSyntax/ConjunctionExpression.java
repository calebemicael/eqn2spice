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
public class ConjunctionExpression extends Expression{
	Expression left;
	Expression right;

	public ConjunctionExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object accept(ExpressionVisitor visitor) {
		return visitor.visit(this);
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}
	
	
}
