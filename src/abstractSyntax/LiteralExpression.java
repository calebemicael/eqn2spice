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
public class LiteralExpression extends Expression{
	Symbol s;
	// TODO: use this flag to interpret if an input must be marked or not to be
	// inverted in the input. If it is 1, this must be inverted. If 0, it doesn't.
	// if it is more than one, an inverted have to be addedd to the final spice
	// in order to have both signals.
	int inverted = 0;

	public LiteralExpression(Symbol s) {
		this.s = s;
		inverted = 0;
	}
	
	@Override
	public Object accept(ExpressionVisitor visitor) {
		return visitor.visit(this);
	}

	public Symbol getSymbol() {
		return s;
	}

	public void setSymbol(Symbol s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return this.s.toString();
	}
	
	
	
	public void invert(){
		this.inverted++;
	}
	
	
}
