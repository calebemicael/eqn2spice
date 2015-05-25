/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax;

import java.util.ArrayList;
import java.util.List;
import parser.Symbol;

/**
 *
 * @author calebemicael
 */
public class SubNetwork {
	List<Symbol> source; // ISSO EH O QUE MUDA.
	Symbol gate;         // vai ser sempre a porta.
	List<Symbol> drain;  // ISSO EH O QUE MUDA.
	Symbol bulk;         // vai ser sempre o GND pra o pulldown e o VCC pro pullup
	
	public SubNetwork() {
		source = new ArrayList<>();
		drain  = new ArrayList<>();
	}
	
	public void linkSourceTo(List<Symbol> node){
		source.addAll(node);
		node.addAll(source);
	}
	
	public void linkDrainTo(List<Symbol> node){
		drain.addAll(node);
		node.addAll(source);
	}
	
	public void setGate(Symbol s){
		this.gate = s;
	}
	
	public void setBulk(Symbol s){
		this.bulk = s;
	}

	public Symbol getBulk() {
		return bulk;
	}

	public Symbol getGate() {
		return gate;
	}

	public List<Symbol> getSource() {
		return source;
	}

	public List<Symbol> getDrain() {
		return drain;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mn_1 ");
		// o par [] indica agrupamento de nodos.
		sb.append("[");
		for(Symbol s: getSource()){
			sb.append(s.toString());
			sb.append(" ");
		}
		sb.append("] ");
		sb.append(gate);
		sb.append(" [");
		for(Symbol s: getDrain()){
			sb.append(s.toString());
			sb.append(" ");
		}
		sb.append("] ");
		sb.append(bulk);
		sb.append(" modn L=XXXu W=YYYu");
		return sb.toString();
	}
	
	
	
}
