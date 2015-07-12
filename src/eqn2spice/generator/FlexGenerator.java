/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqn2spice.generator;

import JFlex.SilentExit;
import java.io.File;

/**
 *
 * @author calebemicael
 */
public class FlexGenerator {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try{
			String [] options = new String[2];
			options[0]="-d";
			options[1]="src/eqn2spice/parser/";
			JFlex.Main.parseOptions(options);
		}catch(SilentExit e){
			e.printStackTrace();
		}
		
		JFlex.Main.generate(new File("lib/scanner.l"));
	}
	
}
