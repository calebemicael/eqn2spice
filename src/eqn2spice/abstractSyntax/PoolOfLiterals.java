/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.abstractSyntax;

import java.util.HashMap;
import eqn2spice.parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class PoolOfLiterals {
	private static HashMap<Symbol,LiteralExpression>base= new HashMap<>();
	private static HashMap<LiteralExpression,Symbol>reverseBase= new HashMap<>();
	private static int currentNode = 1;
	public PoolOfLiterals() {
		base = new HashMap<>();
	}
        
        public static void reset(){
            currentNode = 1;
            base = new HashMap<>();
            reverseBase= new HashMap<>();
        }
        
        
	public static LiteralExpression get(Symbol sym) {
		LiteralExpression le = base.get(sym);
		if(le == null){
			le = new LiteralExpression(sym);
			le.setSymbol(sym);
			add(sym,le);
			add(le, sym);
		}
		return le;
	}
	
	public static void update(Symbol s, LiteralExpression le){
		Symbol s_old = reverseBase.get(le);
		base.remove(s_old); // removes the LiteralExpression that was key to le.
		LiteralExpression le_old = base.get(s);
		reverseBase.remove(le_old); // removes the LiteralExpression that was key to s.
		base.put(s, le);
		reverseBase.put(le, s);
	}
	
	public static Symbol get(LiteralExpression le) {
		Symbol s = reverseBase.get(le);
		if(s == null){
			s = Symbol.symbol(createNodeName());
			le.setSymbol(s);
			add(s,le);
			add(le,s);
		}
		return s;
	}
	
	
	
	private static String createNodeName(){
		String aux="node_";
		aux+=currentNode;
		currentNode++;
		return aux;
	}
	
	private static void add(Symbol key, LiteralExpression value) {
		base.put(key, value);
	}
	
	private static void add(LiteralExpression key,Symbol value) {
		reverseBase.put(key,value);
	}
}
