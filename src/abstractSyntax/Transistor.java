/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax;

import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class Transistor {
	LiteralExpression source;
	LiteralExpression gate;         // vai ser sempre a porta.
	LiteralExpression drain;
	LiteralExpression bulk;         // vai ser sempre o GND pra o pulldown e o VCC pro pullup
	
	public Transistor() {
	}
	
	public void linkSourceTo(LiteralExpression node){
		this.source = node;
	}
	
	public void linkDrainTo(LiteralExpression node){
		this.drain = node;
	}
	
	public void setGate(LiteralExpression s){
		this.gate = s;
	}
	
	public void setBulk(LiteralExpression s){
		this.bulk = s;
	}

	public LiteralExpression getBulk() {
		return bulk;
	}

	public LiteralExpression getGate() {
		return gate;
	}

	public LiteralExpression getSource() {
		return source;
	}

	public LiteralExpression getDrain() {
		return drain;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mn_1 ");
		sb.append(PoolOfLiterals.get(source).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(gate).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(drain).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(bulk).toString());
		sb.append(" modn L=XXXu W=YYYu");
		return sb.toString();
	}
}
