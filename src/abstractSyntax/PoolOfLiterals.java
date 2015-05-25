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

	public PoolOfLiterals() {
		base = new HashMap<>();
	}

	public static LiteralExpression get(Symbol sym) {
		LiteralExpression le = base.get(sym);
		if(le == null){
			le = new LiteralExpression(sym);
			add(sym,le);
		}
		return le;
	}

	private static void add(Symbol sym, LiteralExpression wire) {
		base.put(sym, wire);
	}
}
