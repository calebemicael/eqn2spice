/******************************************************
 * UNIVERSIDADE FEDERAL DO RIO GRANDE DO SUL  - UFRGS *
 * INSTITUTO DE INFORMATICA	                  - INF   *
 * PROGRAMA DE POS GRADUACAO EM COMPUTACAO    - PPGC  *
 * Autor: Calebe Micael de Oliveira Conceicao         *
 * Orientador: Ricardo Augusto da Luz Reis            *
 ******************************************************/
package eqn2spice.parser;
import java_cup.runtime.*;
import java.util.*;
import eqn2spice.abstractSyntax.*;
parser code {:
    
    /* Change the method report_error so it will display the line and
       column of where the error occurred in the input as well as the
       reason for the error which is passed into the method in the
       String 'message'. */
    public void report_error(String message, Object info) {
   
        /* Create a StringBuffer called 'm' with the string 'Error' in it. */
        StringBuffer m = new StringBuffer("Error");
   
        /* Check if the information passed to the method is the same
           type as the type java_cup.runtime.Symbol. */
        if (info instanceof java_cup.runtime.Symbol) {
            /* Declare a java_cup.runtime.Symbol object 's' with the
               information in the object info that is being typecasted
               as a java_cup.runtime.Symbol object. */
            java_cup.runtime.Symbol s = ((java_cup.runtime.Symbol) info);
   
            /* Check if the line number in the input is greater or
               equal to zero. */
            if (s.left >= 0) {                
                /* Add to the end of the StringBuffer error message
                   the line number of the error in the input. */
                m.append(" in line "+(s.left+1));   
                /* Check if the column number in the input is greater
                   or equal to zero. */
                if (s.right >= 0)
                    /* Add to the end of the StringBuffer error message
                       the column number of the error in the input. */
                    m.append(", column "+(s.right+1));
            }
        }
   
        /* Add to the end of the StringBuffer error message created in
           this method the message that was passed into this method. */
        m.append(" : "+message);
   
        /* Print the contents of the StringBuffer 'm', which contains
           an error message, out on a line. */
        System.err.println(m);
    }
   
    /* Change the method report_fatal_error so when it reports a fatal
       error it will display the line and column number of where the
       fatal error occurred in the input as well as the reason for the
       fatal error which is passed into the method in the object
       'message' and then exit.*/
    public void report_fatal_error(String message, Object info) {
        report_error(message, info);
        System.exit(1);
    }
:};
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
 terminal IDENTIFIER;

/*******************************************
 *DECLARACAO DOS NAO-TERMINAIS DA GRAMATICA*
 *******************************************/
/**********************
 *DECLARACAO DA CLASSE*
 **********************/
 non terminal Declaration declarations;
 non terminal List<LiteralExpression> input_dec;
 non terminal List<LiteralExpression> ident_list;
 non terminal List<LiteralExpression> output_dec;
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
									d.assign(assignExp.getTarget(),assignExp.getExp());
									RESULT = d;
								:}
								;

input_dec ::= INORDER ASSIGN ident_list:i_list SEMICOLON
							{:
								RESULT = i_list;
							:}
						  ;

ident_list ::= IDENTIFIER:iid ident_list:list 
							{:
								list.add(PoolOfLiterals.get(Symbol.symbol(""+iid)));
								RESULT = list;
							:}
							|IDENTIFIER:iid
							{:
								List<LiteralExpression> list = new ArrayList<>();
								list.add(PoolOfLiterals.get(Symbol.symbol(""+iid)));
								RESULT = list;
							:}
							;

output_dec ::= OUTORDER ASSIGN ident_list:o_list SEMICOLON
							{:
								RESULT = o_list;
							:}
						   ;


expr_dec ::= L_BRACE IDENTIFIER:output R_BRACE ASSIGN expr_0:r SEMICOLON
						{:
							//System.out.print(output);
							AssignExpression ae = new AssignExpression(PoolOfLiterals.get(Symbol.symbol(""+output)),r);
							RESULT = ae;
						:}
						|IDENTIFIER:output ASSIGN expr_0:r SEMICOLON
						{:
							AssignExpression ae = new AssignExpression(PoolOfLiterals.get(Symbol.symbol(""+output)),r);
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
					|IDENTIFIER:id
					{:
						RESULT = PoolOfLiterals.get(Symbol.symbol(""+id));
					:}
					;