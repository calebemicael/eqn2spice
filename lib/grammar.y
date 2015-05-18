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
 non terminal Expression declarations;
 non terminal Expression input_dec;
 non terminal Expression input_list;
 non terminal Expression output_dec;
 non terminal Expression output_list;
 non terminal Expression expr_dec;
 non terminal Expression expr_0;
 non terminal Expression expr_1;
 non terminal Expression expr_2;
 non terminal Expression expr_3;
  
 start with declarations;

 /*****************************
 *DEFINICAO DA GRAMATICA LALR *
 ******************************/

declarations ::= input_dec output_dec expr_dec
								;

input_dec ::= INORDER ASSIGN input_list SEMICOLON
						  ;

input_list ::= input_list INPUT_IDENTIFIER
							|INPUT_IDENTIFIER
							;

output_dec ::= OUTORDER ASSIGN output_list SEMICOLON
						   ;

output_list ::= output_list OUTPUT_IDENTIFIER 
							|OUTPUT_IDENTIFIER
							;

expr_dec ::= L_BRACE OUTPUT_IDENTIFIER R_BRACE ASSIGN expr_0:r SEMICOLON
						{:
							RESULT = new AssignExpression(r);
						:}
						|OUTPUT_IDENTIFIER ASSIGN expr_0:r
						{:
							RESULT = new AssignExpression(r);
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
						RESULT = new TerminalExpression(Symbol.symbol(""+id));
					:}
					;