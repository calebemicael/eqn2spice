/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.abstractSyntax;

import eqn2spice.abstractSyntax.visitor.ExpressionVisitor;

/**
 *
 * @author calebemicael
 */
public abstract class Expression {
	public abstract Object accept(ExpressionVisitor visitor);
}
