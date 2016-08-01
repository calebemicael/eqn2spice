/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.tools;

import eqn2spice.abstractSyntax.AssignExpression;
import eqn2spice.abstractSyntax.ConjunctionExpression;
import eqn2spice.abstractSyntax.Declaration;
import eqn2spice.abstractSyntax.DisjunctionExpression;
import eqn2spice.abstractSyntax.Expression;
import eqn2spice.abstractSyntax.NegativeExpression;
import eqn2spice.abstractSyntax.LiteralExpression;
import eqn2spice.abstractSyntax.Network;
import eqn2spice.abstractSyntax.PoolOfLiterals;
import eqn2spice.abstractSyntax.Transistor;
import eqn2spice.abstractSyntax.visitor.ExpressionVisitor;
import eqn2spice.abstractSyntax.visitor.GenericVisitor;
import eqn2spice.parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class PullDownGenerator implements ExpressionVisitor,GenericVisitor{
	Declaration d;
	StringBuilder sb;
	// TODO: Play with transistor sizes.
	float baseW;
	float baseL;
	
	float minW; // TODO: uses this value.
	float minL; // TODO: uses this value.
	
	public PullDownGenerator(Declaration d) {
		this.d = d;
		sb = new StringBuilder();
	}

	public void setBaseW(float baseW) {
		this.baseW = baseW;
	}

	public void setBaseL(float baseL) {
		this.baseL = baseL;
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
		
		/**
		 * Aqui abaixo eu chamo pra fazer o sizing dos transistores.
		 */
		pullDown.setW(baseW);
		pullDown.setL(baseL);
		printTransistors(pullDown);
		
		return null;
	}
	
	@Override
	public Object visit(ConjunctionExpression conjExp) {
		Network l = (Network)conjExp.getLeft().accept(this);
		Network r = (Network)conjExp.getRight().accept(this);
		
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
		// a melhor solucao que encontrei ate agora eh fazer a primeira passada,
		// contando quantos transistores tem em um dado branch, para entao fazer o
		// sizing. Considero para a contagem os nos folhas, que sao as literais, ou
		// as operacoes diferentes daquela que estou considerando.
		// sizing for logical effort
		
		return n;
	}

	@Override
	public Object visit(DisjunctionExpression disjExp) {
		Network l = (Network)disjExp.getLeft().accept(this);
		Network r = (Network)disjExp.getRight().accept(this);
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
		Object o = negExp.getExp().accept(this);
		return (Network)o;
	}
	int num = 0;
	@Override
	public Object visit(LiteralExpression termExp) {
		Transistor sn = new Transistor();
		
		sn.setNumber(num);num++;
		
		sn.linkSourceTo(new LiteralExpression(null));
		sn.setGate(PoolOfLiterals.get(termExp.getSymbol()));
		sn.linkDrainTo(new LiteralExpression(null));
		// TODO verificar a conexao do bulk. Ta dando shits.
		sn.setBulk(PoolOfLiterals.get(Symbol.symbol("GND")));

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
