/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import abstractSyntax.AssignExpression;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import parser.Analizador;
import parser.Yylex;
import tools.PrettyPrinter;

/**
 *
 * @author calebemicael
 */
public class Main {
		
	public static void main(String [] args)	{
		
	BufferedReader buff = null;
		try {
			//buff = new BufferedReader(new FileReader(args[0]));
			buff = new BufferedReader(new FileReader(new File("lib/nor.eqn")));
			//buff = new BufferedReader(new InputStreamReader(System.in));
			//
			Analizador p = new Analizador(new Yylex(buff));
			AssignExpression assExp = (AssignExpression)p.parse().value;
			PrettyPrinter pp = new PrettyPrinter(assExp);
			pp.run();
			System.out.println(pp.toString());
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
