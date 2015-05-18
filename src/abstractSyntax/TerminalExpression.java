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
public class TerminalExpression extends Expression{
	Symbol s;

	public TerminalExpression(Symbol s) {
		this.s = s;
	}
	
	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	public Symbol getSymbol() {
		return s;
	}
	
	
	
}
