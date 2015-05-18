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
	Symbol target;

	public AssignExpression(Expression exp) {
		this.exp = exp;
		this.target = Symbol.symbol("Y");
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	public String getTarget(){
		return target.toString();
	}

	public Expression getExp() {
		return exp;
	}
	
}
