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
public class PullUpGenerator implements ExpressionVisitor, GenericVisitor {

    Declaration d;
    StringBuilder sb;
    float baseW;
    float baseL;
    float minW; // TODO: uses this value.
    float minL; // TODO: uses this value.
    // TODO: Play with transistor sizes.

    public PullUpGenerator(Declaration d) {
        this.d = d;
        sb = new StringBuilder();
    }

    public void setBaseSizeW(float baseSize) {
        this.baseW = baseSize;
    }

    public void setBaseSizeL(float baseSizeL) {
        this.baseL = baseSizeL;
    }

    @Override
    public Object visit(Declaration dec) {
        for (AssignExpression ae : dec.getAssigns()) {
            visit(ae);
        }
        return null;
    }

    public void run() {
        d.accept(this);
    }

    @Override
    public Object visit(AssignExpression assExp) {

        assExp.getTarget().accept(this);

        Network pullUp = (Network) assExp.getExp().accept(this);

        PoolOfLiterals.update(assExp.getTarget().getSymbol(), pullUp.getDrain());
        PoolOfLiterals.update(Symbol.symbol("vcc"), pullUp.getSource());

        /**
         * Aqui abaixo eu chamo pra fazer o sizing dos transistores.
         */
        pullUp.setW(baseW);
        pullUp.setL(baseL);

        printTransistors(pullUp);

        return null;
    }

    @Override
    public Object visit(ConjunctionExpression conjExp) {
        Network l = (Network) conjExp.getLeft().accept(this);
        Network r = (Network) conjExp.getRight().accept(this);
        // I'm doing this because it is a + operation, in a PullUp network
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
    public Object visit(DisjunctionExpression disjExp) {
        Network l = (Network) disjExp.getLeft().accept(this);
        Network r = (Network) disjExp.getRight().accept(this);
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
    public Object visit(NegativeExpression negExp) {
        // Sizing for logical effort. Negative expression has no impact.
        Object o = negExp.getExp().accept(this);
        return (Network) o;
    }

    int num = 0;

    @Override
    public Object visit(LiteralExpression termExp) {
        Transistor sn = null;

        sn = new Transistor();
        sn.setNumber(num);
        num++;
        // yes, null. I want to play with references. Doesn't matter the name.
        sn.linkSourceTo(new LiteralExpression(null));
        sn.setGate(PoolOfLiterals.get(termExp.getSymbol()));
        sn.linkDrainTo(new LiteralExpression(null));
        sn.setBulk(PoolOfLiterals.get(Symbol.symbol("VCC")));

        return sn;
    }

    @Override
    public Object visit(Expression exp) {
        if (exp instanceof NegativeExpression) {
            visit((NegativeExpression) exp);
        } else if (exp instanceof ConjunctionExpression) {
            visit((ConjunctionExpression) exp);
        } else if (exp instanceof DisjunctionExpression) {
            visit((DisjunctionExpression) exp);
        } else if (exp instanceof LiteralExpression) {
            visit((LiteralExpression) exp);
        } else if (exp instanceof AssignExpression) {
            visit((AssignExpression) exp);
        }
        return null;
    }

    public String toString() {
        return sb.toString();
    }

    public void printTransistors(Network n) {
        if (n instanceof Transistor) {
            Transistor t = (Transistor) n;
            sb.append(t.toPMosString() + "\n");
        } else {
            printTransistors(n.getSubNetA());
            printTransistors(n.getSubNetB());
        }
    }

}
