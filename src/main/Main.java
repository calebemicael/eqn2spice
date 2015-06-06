/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import abstractSyntax.Declaration;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import parser.Analizador;
import parser.Yylex;
import tools.HeaderGenerator;
import tools.PullDownGenerator;
import tools.PullUpGenerator;

/**
 *
 * @author calebemicael
 */
public class Main {
		
	public static void main(String [] args)	{
	
		
	BufferedReader buff = null;
		try {
			//buff = new BufferedReader(new FileReader(args[0]));
			buff = new BufferedReader(new FileReader(new File(args[0])));
			//buff = new BufferedReader(new InputStreamReader(System.in));
			//
			Analizador p = new Analizador(new Yylex(buff));
			Declaration dec = (Declaration)p.parse().value;
			
			HeaderGenerator header = new HeaderGenerator(dec,"gate_0");
			
			PullDownGenerator pdn = new PullDownGenerator(dec);
			pdn.setBaseL(350);
			pdn.setBaseW(2400);
			
			PullUpGenerator pun = new PullUpGenerator(dec);
			pun.setBaseSizeL(350);
			pun.setBaseSizeW(3600);
			//PrettyPrinter pp = new PrettyPrinter(dec);
			header.run();
			pdn.run();
			pun.run();
			
			String output="lib/out.sp";
			if(args.length>1){
				if(args.length<3){
					System.out.println("USAGE: eqn2spice <EQN input> -o <SPICE FILE>");
					System.exit(0);
				}else{
					output = args[2];
				}
			}
			BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(output)));
			bwr.write(header.toString());
			bwr.write(pun.toString().replace("VCC", "vcc"));// a minor gambiarra
			bwr.write(pdn.toString().replace("GND", "gnd"));// a minor gambiarra
			bwr.flush();
			bwr.close();
			
			//System.out.println(header.toString());
			//System.out.println(pun.toString());
			//System.out.println(pdn.toString());
			//p.parse();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}finally {
			try {
				buff.close();
			} catch (IOException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	
	}
}
