/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import abstractSyntax.LiteralExpression;
import abstractSyntax.PoolOfLiterals;
import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class Tests {
	public static void main(String [] args){
		
		LiteralExpression le1 = new LiteralExpression(Symbol.symbol("a"));
		LiteralExpression le2 = new LiteralExpression(Symbol.symbol("b"));
		LiteralExpression le3 = new LiteralExpression(Symbol.symbol("c"));
		
		
		System.out.println(PoolOfLiterals.get(le1).toString());
		System.out.println(PoolOfLiterals.get(le2).toString());
		System.out.println(PoolOfLiterals.get(le3).toString());
		System.out.println(PoolOfLiterals.get(le2).toString());
	}
}
