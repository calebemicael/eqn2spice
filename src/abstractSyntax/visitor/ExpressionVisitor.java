/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax.visitor;

import abstractSyntax.AssignExpression;
import abstractSyntax.ConjunctionExpression;
import abstractSyntax.DisjunctionExpression;
import abstractSyntax.NegativeExpression;
import abstractSyntax.TerminalExpression;

/**
 *
 * @author calebemicael
 */
public interface ExpressionVisitor{
	
	public Object visit(NegativeExpression negExp);
	public Object visit(ConjunctionExpression conjExp);
	public Object visit(DisjunctionExpression disjExp);
	public Object visit(TerminalExpression termExp);
	public Object visit(AssignExpression assExp);
}
