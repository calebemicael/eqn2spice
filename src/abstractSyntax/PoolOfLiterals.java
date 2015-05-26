/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax;

import java.util.HashMap;
import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class PoolOfLiterals {
	private static HashMap<Symbol,LiteralExpression>base= new HashMap<>();
	private static HashMap<LiteralExpression,Symbol>reverseBase= new HashMap<>();
	private static char currentNode = (char)65;
	public PoolOfLiterals() {
		base = new HashMap<>();
	}

	public static LiteralExpression get(Symbol sym) {
		LiteralExpression le = base.get(sym);
		if(le == null){
			le = new LiteralExpression(sym);
			add(sym,le);
			addReverse(le, sym);
		}
		return le;
	}
	
	public static Symbol get(LiteralExpression le) {
		Symbol s = reverseBase.get(le);
		if(s == null){
			s = Symbol.symbol(createNodeName());
			add(s,le);
			addReverse(le,s);
		}
		return s;
	}
	
	
	
	private static String createNodeName(){
		String aux="node_";
		aux+=currentNode;
		currentNode++;
		return aux;
	}
	
	private static void add(Symbol sym, LiteralExpression le) {
		base.put(sym, le);
	}
	
	private static void addReverse(LiteralExpression le,Symbol sym) {
		reverseBase.put(le,sym);
	}
}
