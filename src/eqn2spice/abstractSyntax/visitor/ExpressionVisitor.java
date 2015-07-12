/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.abstractSyntax.visitor;

import eqn2spice.abstractSyntax.AssignExpression;
import eqn2spice.abstractSyntax.Expression;
import eqn2spice.abstractSyntax.ConjunctionExpression;
import eqn2spice.abstractSyntax.DisjunctionExpression;
import eqn2spice.abstractSyntax.NegativeExpression;
import eqn2spice.abstractSyntax.LiteralExpression;

/**
 *
 * @author calebemicael
 */
public interface ExpressionVisitor{
	
	public Object visit(NegativeExpression negExp);
	public Object visit(ConjunctionExpression conjExp);
	public Object visit(DisjunctionExpression disjExp);
	public Object visit(LiteralExpression termExp);
	public Object visit(AssignExpression assExp);
	public Object visit(Expression exp);
}
