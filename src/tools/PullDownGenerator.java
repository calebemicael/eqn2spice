/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import abstractSyntax.AssignExpression;
import abstractSyntax.ConjunctionExpression;
import abstractSyntax.Declaration;
import abstractSyntax.DisjunctionExpression;
import abstractSyntax.Expression;
import abstractSyntax.NegativeExpression;
import abstractSyntax.LiteralExpression;
import abstractSyntax.Network;
import abstractSyntax.PoolOfLiterals;
import abstractSyntax.Transistor;
import abstractSyntax.visitor.ExpressionVisitor;
import abstractSyntax.visitor.GenericVisitor;
import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class PullDownGenerator implements ExpressionVisitor,GenericVisitor{
	Declaration d;
	StringBuilder sb;
	float baseSizeW;
	float baseSizeL;
	// TODO: Play with transistor sizes.
	
	public PullDownGenerator(Declaration d) {
		this.d = d;
		sb = new StringBuilder();
	}

	public void setBaseSizeW(float baseSize) {
		this.baseSizeW = baseSize;
	}

	public void setBaseSizeL(float baseSizeL) {
		this.baseSizeL = baseSizeL;
	}
	
	@Override
	public Object visit(Declaration dec) {
		for(AssignExpression ae: dec.getAssigns()){
			visit(ae);
		}
		return null;
	}
	
	public void run(){
		d.accept(this);
	}

	@Override
	public Object visit(AssignExpression assExp) {
		assExp.getTarget().accept(this);
		
		Network pullDown = (Network) assExp.getExp().accept(this);
		
		PoolOfLiterals.update(Symbol.symbol("gnd"), pullDown.getDrain());
		PoolOfLiterals.update(assExp.getTarget().getSymbol(), pullDown.getSource());
		
		printTransistors(pullDown);
		
		return null;
	}
	
	@Override
	public Object visit(ConjunctionExpression conjExp) {
		// sizing for logical effort
		this.baseSizeW = baseSizeW*2.0f;
		Network l = (Network)conjExp.getLeft().accept(this);
		Network r = (Network)conjExp.getRight().accept(this);
		// sizing for logical effort
		this.baseSizeW = baseSizeW/2.0f;
		
		l.linkDrainTo(r.getSource());
		// r.linkDrainTo(l.getSource());	// this option brings different electrical
																			// characteristics. When trying this, re-
																			// member to adjust Network connectTo
																			// operations, to reflect the new behavior
		Network n = new Network(true);
		n.setSubNetA(l);
		n.setSubNetB(r);
		n.setSource(l.getSource());
		n.setDrain(r.getDrain());
		return n;
	}

	@Override
	public Object visit(DisjunctionExpression disjExp) {
		// sizing for logical effort
		this.baseSizeW = baseSizeW/2.0f;
		Network l = (Network)disjExp.getLeft().accept(this);
		Network r = (Network)disjExp.getRight().accept(this);
		// sizing for logical effort
		this.baseSizeW = baseSizeW*2.0f;
		// I'm doing this because it is a + operation, in a PullDown Network
		l.linkDrainTo(r.getDrain());
		l.linkSourceTo(r.getSource());
		
		Network n = new Network(false);
		n.setSubNetA(l);
		n.setSubNetB(r);
		n.setSource(l.getSource());
		n.setDrain(l.getDrain());
		
		return n;
	}

	@Override
	public Object visit(NegativeExpression negExp) {
		// Sizing for logical effort. Negative expression has no impact.
		this.baseSizeW = baseSizeW;
		Object o = negExp.getExp().accept(this);
		return (Network)o;
	}
	
	@Override
	public Object visit(LiteralExpression termExp) {
		Transistor sn = new Transistor();
		sn.setW(this.baseSizeW);
		sn.setL(this.baseSizeL);

		sn.linkSourceTo(new LiteralExpression(null));
		sn.setGate(PoolOfLiterals.get(termExp.getSymbol()));
		sn.linkDrainTo(new LiteralExpression(null));
		sn.setBulk(PoolOfLiterals.get(Symbol.symbol("gnd")));

		return sn;
	}

	@Override
	public Object visit(Expression exp) {
		if(exp instanceof NegativeExpression){
			visit((NegativeExpression) exp);
		}else if(exp instanceof ConjunctionExpression){
			visit((ConjunctionExpression) exp);
		}else if(exp instanceof DisjunctionExpression){
			visit((DisjunctionExpression) exp);
		}else if(exp instanceof LiteralExpression){
			visit((LiteralExpression) exp);
		}else if(exp instanceof AssignExpression){
			visit((AssignExpression) exp);
		}
		return null;
	}
	
	public String toString(){
		return sb.toString();
	}
	
	public void printTransistors(Network n){
		if(n instanceof Transistor){
			Transistor t = (Transistor) n;
			sb.append(t.toNMosString()+"\n");
		}else{
			printTransistors(n.getSubNetA());
			printTransistors(n.getSubNetB());
		}
	}
	
}
