/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.abstractSyntax.visitor;

import eqn2spice.abstractSyntax.Declaration;

/**
 *
 * @author calebemicael
 */
public interface GenericVisitor{
	
	public Object visit(Declaration dec);
}
