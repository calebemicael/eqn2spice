/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.*;

public class Symbol {
	public static int BASE_HASH = 10;
	private String name;
	private int hashCd;
	private static Map<String, Symbol> dict = new HashMap<String, Symbol>();

	private Symbol(String n) {
		name = n;
		hashCd = hashcode_();
	}
	
	/**
	 * @return retorna o lexema do simbolo
	 */
	public String toString() {
		return name;
	}

	public static Symbol symbol(String n) {
		Symbol s = dict.get(n);
		if (s == null) {
			s = new Symbol(n);
			dict.put(n, s);
		}
		return s;
	}
	
	/**
	 * 
	 * @return calcula o codigo hash do simbolo
	 */
	private int hashcode_() {
		int valor = 0;
		for (int i = 0; i < name.length(); i++) {
			valor = valor + Character.getNumericValue(name.charAt(i));
		}
		return valor % BASE_HASH;
	}

	/**
	 * @return retorna o codigo hash do simbolo na tabela de simbolos
	 */
	public int hashcode() {
		return hashCd;
	}
}