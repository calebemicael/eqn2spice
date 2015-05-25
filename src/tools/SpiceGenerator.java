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
import abstractSyntax.SubNetwork;
import abstractSyntax.visitor.ExpressionVisitor;
import abstractSyntax.visitor.GenericVisitor;
import java.util.ArrayList;
import java.util.List;
import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class SpiceGenerator implements ExpressionVisitor,GenericVisitor{
	Declaration d;
	String name;
	StringBuilder sb;
	
	//NOTE: por enquanto eu to chamando de subnetwork, mas ainda estou apenas no
	// nivel de transistor. Vai virar de fato subnetworks quando eu começar a com-
	// por conjuntos de transistores em subredes, como ta marcado em um TODO ali 
	// na frente.
	
	// NOTE2: apenas pra testar a logica, vou manter uma lista global de transis-
	// tores cadastrados, e, apos fazer todas as alteracoes de lista de nodos que 
	// sao fonte e dreno, vou imprimir as descricoes de cada um, pra ver o resultado.
	List<SubNetwork> transistorList;
	
	public SpiceGenerator(Declaration d, String name) {
		this.name = name;
		this.d = d;
		sb = new StringBuilder();
		transistorList = new ArrayList<>();
	}

	@Override
	public Object visit(Declaration dec) {
		sb.append(".subckt ");
		sb.append(this.name);
		sb.append(" ");
		for(LiteralExpression le: dec.getInputs()){
			visit(le);
			sb.append(" ");
		}
		for(LiteralExpression le: dec.getOutputs()){
			visit(le);
			sb.append(" ");
		}
		sb.append("vcc gnd\n");
		// aqui comeca a brincadeira.
		
		
		for(LiteralExpression le: dec.getAssigns().keySet()){
			visit(le);
			sb.append(" = ");
			ready=true;
			visit(dec.getAssigns().get(le));
		}
		sb.append(";\n");
		
		includeTransistors();
		
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		return null;
	}
	public void run(){
		d.accept(this);
	}

	@Override
	public Object visit(AssignExpression assExp) {
		sb.append(assExp.getTarget());
		sb.append(" = ");
		assExp.getExp().accept(this);
		return null;
	}
	
	//TODO: implement the linkage by reference, not by list of node names, like it
	// is now implemented.
	
	@Override
	public Object visit(ConjunctionExpression conjExp) {
		//sb.append("(");
		Object left = conjExp.getLeft().accept(this);
		sb.append("*");
		Object right = conjExp.getRight().accept(this);
		//sb.append(")");
		

		SubNetwork l = (SubNetwork)left;
		SubNetwork r = (SubNetwork)right;
		// I'm doing this because it is a + operation
		// TODO: In fact, what I want to do is to compose a new SubNetwork based on
		// these two. The linking logic will be the same.
		l.linkDrainTo(r.getSource());
		// r.linkDrainTo(l.getSource());	// this option brings different electrical
																			// characteristics.
		// AQUI: no lugar de return null, tem que return  a subnet composta. < TODO
		//return null;
		// THINK ABOUT IT: You, asshole programmer, don't need to implement a subnet
		// here, maybe you just need to update node information of the transistors
		// related to this operation. Maybe if you handle the nodes like refereces,
		// not like lists, you can do better than this shit. Signed: myself.
		return l;
	}

	@Override
	public Object visit(DisjunctionExpression disjExp) {
		//sb.append("(");
		Object left = disjExp.getLeft().accept(this);
		sb.append("+");
		Object right = disjExp.getRight().accept(this);
		//sb.append(")");
		
		SubNetwork l = (SubNetwork)left;
		SubNetwork r = (SubNetwork)right;
		// I'm doing this because it is a + operation
		l.linkDrainTo(r.getDrain());
		l.linkSourceTo(r.getSource());
		
		// AQUI: no lugar de return null, tem que return  a subnet composta. < TODO
		//return null;
		return l;
	}

	@Override
	public Object visit(NegativeExpression negExp) {
		sb.append("!");
		boolean printPar = false; 
		printPar |= negExp.getExp() instanceof ConjunctionExpression;
		printPar |= negExp.getExp() instanceof DisjunctionExpression;
		
		if(printPar){
			sb.append("(");
		}		
		Object o = negExp.getExp().accept(this);
		if(printPar){
			sb.append(")");
		}
		// AQUI: no lugar de return null, tem que return  a subnet composta. < TODO
		//return null;
		return (SubNetwork)o;
	}
	int node = 65;
	boolean ready = false;
	@Override
	public Object visit(LiteralExpression termExp) {
		sb.append(termExp.getSymbol());
		
		SubNetwork sn = new SubNetwork();
		
		List<Symbol> l = new ArrayList<>();
		l.add(Symbol.symbol(""+(char)node+"1"));
		sn.linkSourceTo(l);
		node++;
		
		sn.setGate(termExp.getSymbol());
		
		l = new ArrayList<>();
		l.add(Symbol.symbol(""+(char)node+"1"));
		sn.linkDrainTo(l);
		
		node++;
		
		sn.setBulk(Symbol.symbol("GND"));
		if(ready){
			transistorList.add(sn);
		}
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
	
	public void includeTransistors(){
		for(SubNetwork sn: transistorList){
			sb.append(sn.toString()+"\n");
		}
	}
	
}
