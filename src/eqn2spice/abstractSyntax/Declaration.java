/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.abstractSyntax;

import eqn2spice.abstractSyntax.visitor.GenericVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author calebemicael
 */
public class Declaration {
	List<LiteralExpression> inputs;
	List<LiteralExpression> outputs;
	List<AssignExpression> assigns;

	public Declaration() {
		assigns = new ArrayList<>();
	}
	
	public void setInputs(List<LiteralExpression> inputs) {
		this.inputs = inputs;
	}

	public List<LiteralExpression> getInputs() {
		return inputs;
	}

	public List<LiteralExpression> getOutputs() {
		return outputs;
	}

	public List<AssignExpression> getAssigns() {
		return assigns;
	}

	public void setOutputs(List<LiteralExpression> outputs) {
		this.outputs = outputs;
	}
	
	public void assign(LiteralExpression le, Expression e ){
		assigns.add(new AssignExpression(le,e));
	}
	
	public void accept(GenericVisitor visitor) {
		visitor.visit(this);
	}
}
