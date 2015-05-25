/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax;

import abstractSyntax.visitor.ExpressionVisitor;

/**
 *
 * @author calebemicael
 */
public class DisjunctionExpression extends Expression{
	Expression left;
	Expression right;

	public DisjunctionExpression(Expression left, Expression right) {
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
