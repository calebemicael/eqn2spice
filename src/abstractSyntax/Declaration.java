/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax;

import abstractSyntax.visitor.GenericVisitor;
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
	Map<LiteralExpression, Expression> assigns;

	public Declaration() {
		assigns = new HashMap<>();
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

	public Map<LiteralExpression, Expression> getAssigns() {
		return assigns;
	}

	public void setOutputs(List<LiteralExpression> outputs) {
		this.outputs = outputs;
	}
	
	public void assign(LiteralExpression le, Expression e ){
		assigns.put(le, e);
	}
	
	public void accept(GenericVisitor visitor) {
		visitor.visit(this);
	}
}
