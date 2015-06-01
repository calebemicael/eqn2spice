/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractSyntax;

/**
 *
 * @author calebemicael
 */
public class Network {
	LiteralExpression source;
	LiteralExpression drain;
	protected Network subNetA;
	protected Network subNetB;
	boolean isSerial;
	
	public Network(boolean isSerial) {
		this.isSerial = isSerial;
	}
	
	/* There is a convetion here. If subNetA is parallel subNetB, no problems, we
   * we have a drain-drain, source-source connection. But if it is serial, we
	 * have drain of subNetA is connected to source of subNetB. That must be took
	 * into account when executing linkSourceTo and linkDrainTo. We have to update
	 * the subNets properly. ;-)
	 */
	
	public void linkSourceTo(LiteralExpression node){
		this.source = node;
		if(isSerial){
			// only source of subNetA have to be updated
			this.subNetA.linkSourceTo(node);
		}else{ //if it is parallel
			// source of both subnets have to be updated
			this.subNetA.linkSourceTo(node);
			this.subNetB.linkSourceTo(node);
		}
	}
	
	public void linkDrainTo(LiteralExpression node){
		this.drain = node;
		if(isSerial){
			// only drain of subNetB have to be updated
			this.subNetB.linkDrainTo(node);
		}else{ //if it is parallel
			// drain of both subnets have to be updated
			this.subNetA.linkDrainTo(node);
			this.subNetB.linkDrainTo(node);
		}
	}
	
	public LiteralExpression getSource() {
		return source;
	}

	public LiteralExpression getDrain() {
		return drain;
	}

	public void setDrain(LiteralExpression drain) {
		this.drain = drain;
	}

	public void setSource(LiteralExpression source) {
		this.source = source;
	}
	
	public Network getSubNetA() {
		return subNetA;
	}

	public Network getSubNetB() {
		return subNetB;
	}
	
	public void setSubNetA(Network subNetA) {
		this.subNetA = subNetA;
	}

	public void setSubNetB(Network subNetB) {
		this.subNetB = subNetB;
	}
		
}