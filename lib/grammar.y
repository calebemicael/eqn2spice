/******************************************************
 * UNIVERSIDADE FEDERAL DO RIO GRANDE DO SUL  - UFRGS *
 * INSTITUTO DE INFORMATICA	                  - INF   *
 * PROGRAMA DE POS GRADUACAO EM COMPUTACAO    - PPGC  *
 * Autor: Calebe Micael de Oliveira Conceicao         *
 * Orientador: Ricardo Augusto da Luz Reis            *
 ******************************************************/
package parser;
import java_cup.runtime.*;
import java.util.*;
import abstractSyntax.*;
/***************************************
 *DECLARACAO DOS TERMINAIS DA GRAMATICA*
 ***************************************/

/*********************
 *PALAVRAS RESERVADAS*
 *********************/
 terminal INORDER,OUTORDER;

/*************
 *SEPARADORES*
 *************/
 terminal L_PAR,R_PAR,L_BRACE,R_BRACE,SEMICOLON;

/*************
 *OPERADORES*
 *************/
 terminal EXCLAMATION,APOSTROPHE,PLUS,TIMES,ASSIGN;

/*****************************
 *TOKENS COM LEXEMA ASSOCIADO*
 *****************************/
 terminal INPUT_IDENTIFIER,OUTPUT_IDENTIFIER;

/*******************************************
 *DECLARACAO DOS NAO-TERMINAIS DA GRAMATICA*
 *******************************************/
/**********************
 *DECLARACAO DA CLASSE*
 **********************/
 non terminal Declaration declarations;
 non terminal List<LiteralExpression> input_dec;
 non terminal List<LiteralExpression> input_list;
 non terminal List<LiteralExpression> output_dec;
 non terminal List<LiteralExpression> output_list;
 non terminal AssignExpression expr_dec;
 non terminal Expression expr_0;
 non terminal Expression expr_1;
 non terminal Expression expr_2;
 non terminal Expression expr_3;
  
 start with declarations;

 /*****************************
 *DEFINICAO DA GRAMATICA LALR *
 ******************************/

declarations ::= input_dec:in output_dec:out expr_dec:assignExp
								{:
									Declaration d = new Declaration();
									d.setInputs(in);
									d.setOutputs(out);
									d.assign(PoolOfLiterals.get(assignExp.getTarget()),assignExp.getExp());
									RESULT = d;
								:}
								;

input_dec ::= INORDER ASSIGN input_list:i_list SEMICOLON
							{:
								RESULT = i_list;
							:}
						  ;

input_list ::= INPUT_IDENTIFIER:iid input_list:list 
							{:
								list.add(PoolOfLiterals.get(Symbol.symbol(""+iid)));
								RESULT = list;
							:}
							|INPUT_IDENTIFIER:iid
							{:
								List<LiteralExpression> list = new ArrayList<>();
								list.add(PoolOfLiterals.get(Symbol.symbol(""+iid)));
								RESULT = list;
							:}
							;

output_dec ::= OUTORDER ASSIGN output_list:o_list SEMICOLON
							{:
								RESULT = o_list;
							:}
						   ;

output_list ::= OUTPUT_IDENTIFIER:oid output_list:list 
								{:
									list.add(PoolOfLiterals.get(Symbol.symbol(""+oid)));
									RESULT = list;
								:}
								|OUTPUT_IDENTIFIER:oid
								{:
									List<LiteralExpression> list = new ArrayList<>();
									list.add(PoolOfLiterals.get(Symbol.symbol(""+oid)));
									RESULT = list;
								:}
								;

expr_dec ::= L_BRACE OUTPUT_IDENTIFIER:output R_BRACE ASSIGN expr_0:r SEMICOLON
						{:
							//System.out.print(output);
							AssignExpression ae = new AssignExpression(r);
							ae.setTarget(PoolOfLiterals.get(Symbol.symbol(""+output)));
							RESULT = ae;
						:}
						|OUTPUT_IDENTIFIER:output ASSIGN expr_0:r SEMICOLON
						{:
							AssignExpression ae = new AssignExpression(r);
							ae.setTarget(PoolOfLiterals.get(Symbol.symbol(""+output)));
							RESULT = ae;
						:}
						;

expr_0 ::= expr_0:l PLUS expr_1:r
					{:
						RESULT = new DisjunctionExpression(l,r);
					:}
					|expr_1:c
					{:
						RESULT = c;
					:}
					;

expr_1 ::= expr_1:l TIMES expr_2:r
					{:
						RESULT = new ConjunctionExpression(l,r);
					:}
					|expr_2:c
					{:
						RESULT = c;
					:}
					;

expr_2 ::= EXCLAMATION expr_3:c
					{:
						RESULT = new NegativeExpression(c);
					:}
					|expr_3:c APOSTROPHE
					{:
						RESULT = new NegativeExpression(c);
					:}
					|expr_3:c
					{:
						RESULT = c;
					:}
					;

expr_3 ::= L_PAR expr_0:c R_PAR
					{:
						RESULT = c;
					:}
					|INPUT_IDENTIFIER:id
					{:
						RESULT = PoolOfLiterals.get(Symbol.symbol(""+id));
					:}
					;