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
	public static int wPUN=3600;
	public static int wPDN=2400;
	public static int L=350;
	public static int scale=1;
	public static String input;
	public static String output;
	public static String gateName = "gate_0";
	public static String USAGE_MESSAGE = "USAGE: eqn2spice <EQN input> -o <SPICE FILE>";
	
	

	private static int getScale() {
		return scale;
	}

	public static void setGateName(String gateName) {
		Main.gateName = gateName;
	}

	private static int getwPDN() {
		return wPDN;
	}

	private static int getwPUN() {
		return wPUN;
	}

	private static String getOutput() {
		return output;
	}

	private static int getL() {
		return L;
	}

	private static String getInput() {
		return input;
	}
	
	public static void setwPDN(int wPDN) {
		Main.wPDN = wPDN;
	}

	public static void setScale(int scale) {
		Main.scale = scale;
	}

	public static void setwPUN(int wPUN) {
		Main.wPUN = wPUN;
	}

	public static void setL(int L) {
		Main.L = L;
	}
	
	public static void main(String [] args)	{
		switch(args.length){
			case 1: 
				input = args[0]; 
				run(input);
				break;
			case 2:
				if(args[1].trim().equals("-o")){
					System.out.println(USAGE_MESSAGE);
					System.exit(0);
				}else{
					input = args[0];
					output = args[2];
					run(input,output);
				}
				break;
			case 3:
				if(args[1].trim().equals("-o")){
					input = args[0];
					output = args[2];
					run(input,output);
				}else{
					System.out.println(USAGE_MESSAGE);
					System.exit(0);
				}
			default:
				System.out.println(USAGE_MESSAGE);
				System.exit(0);
				break;
		}
	}
	/**
	 * 
	 * @param in EQN file. 
	 * @param out SPICE file to be created.
	 * It takes the EQN file and outputs the corresponding sized spice file.
	 */
	public static void run(String in, String out)	{
		input = in;
		output = out;
		BufferedWriter bwr = null;
		try {
			bwr = new BufferedWriter(new FileWriter(new File(output)));
			bwr.write(base());
			bwr.flush();
			bwr.close();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * 
	 * @param in EQN file. 
	 * It outputs the corresponding Spice file in standard output.
	 */
	public static void run(String in)	{
		input = in;
		System.out.println(base());
	}
	
	private static String base(){
		BufferedReader buff = null;
		StringBuilder sb = new StringBuilder();
		try {
			//buff = new BufferedReader(new FileReader(args[0]));
			buff = new BufferedReader(new FileReader(new File(getInput())));
			//buff = new BufferedReader(new InputStreamReader(System.in));
			//
			Analizador p = new Analizador(new Yylex(buff));
			Declaration dec = (Declaration)p.parse().value;
			
			HeaderGenerator header = new HeaderGenerator(dec,gateName);
			
			PullDownGenerator pdn = new PullDownGenerator(dec);
			pdn.setBaseL(getL());
			pdn.setBaseW(getwPDN()*getScale());
			
			PullUpGenerator pun = new PullUpGenerator(dec);
			pun.setBaseSizeL(getL());
			pun.setBaseSizeW(getwPUN()*getScale());
			header.run();
			pdn.run();
			pun.run();
			
			sb.append(header.toString());
			sb.append(pun.toString().replace("VCC", "vcc"));// a minor gambiarra
			sb.append(pdn.toString().replace("GND", "gnd"));// a minor gambiarra
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
		return sb.toString();
	}
}
