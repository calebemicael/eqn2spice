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
public class Transistor extends Network{
	LiteralExpression gate; // vai ser sempre o literal.
	LiteralExpression bulk;  // vai ser sempre o GND pra o pulldown e o VCC pro pullup
	
	public Transistor() {
		super(false);
	}

	public int count(boolean isSerial) {
		return 1;
	}

	@Override
	public void setL(float L) {
		this.L=L;
	}

	
	public void setW(float W) {
		this.W = W;
	}
	
	public void setW(float W, boolean isSerial) {
		this.W = W;
	}
	
	@Override
	public void linkSourceTo(LiteralExpression node){
		this.source = node;
	}
	@Override
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
	
	public String toNMosString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mn_1 ");
		sb.append(PoolOfLiterals.get(source).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(gate).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(drain).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(bulk).toString());
		sb.append(" modp W=");
		sb.append(String.format("%.2f", this.W));
		sb.append("n L=");
		sb.append(String.format("%.2f", this.L));
		sb.append("n");
		return sb.toString();
	}
	
	public String toPMosString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mp_1 ");
		sb.append(PoolOfLiterals.get(source).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(gate).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(drain).toString());
		sb.append(" ");
		sb.append(PoolOfLiterals.get(bulk).toString());
		sb.append(" modp W=");
		sb.append(String.format("%.2f", this.W));
		sb.append("n L=");
		sb.append(String.format("%.2f", this.L));
		sb.append("n");
		return sb.toString();
	}
}
