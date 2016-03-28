/******************************************************
 * UNIVERSIDADE FEDERAL DO RIO GRANDE DO SUL  - UFRGS *
 * INSTITUTO DE INFORMATICA	                  - INF   *
 * PROGRAMA DE POS GRADUACAO EM COMPUTACAO    - PPGC  *
 * Autor: Calebe Micael de Oliveira Conceicao         *
 * Orientador: Ricardo Augusto da Luz Reis            *
 ******************************************************/
package eqn2spice.parser;
import java_cup.runtime.Symbol;

%%
/*****************************************
 *DIRETIVAS DE PRE-PROCESSAMENTO DO JFLEX*
 *****************************************/
%unicode
//%debug
%public
%cup
%line
%column

/**************************************************
 *CODIGO JAVA A SER ACRESCENTADO NA SAIDA DO JFLEX*
 **************************************************/
%{

StringBuffer string = new StringBuffer();
/* Funcao que recebe o id(tipo) do token encontrado, e retorna
 * um simbolo com os atributos:
 * id(tipo) do token encontrado;
 * linha da posicao do input em que foi encontrado;
 * coluna da posicao do input em que foi encontrado;
 */
	private Symbol symbol(int type){
		return new Symbol(type, yyline, yycolumn);
	}

/* Funcao que recebe o id(tipo) do token encontrado, e retorna
 * um simbolo com os atributos:
 * id(tipo) do token encontrado;
 * linha da posicao do input em que foi encontrado;
 * coluna da posicao do input em que foi encontrado;
 * o valor do lexema do token
 */
	private Symbol symbol(int type, Object value){
		return new Symbol(type, yyline, yycolumn, value);
	}
	int commentLevel = 0;
%}
/***************************************************
 *MACROS(ABREVIACOES) DAS EXP. REGULARES UTILIZADAS*
 ***************************************************/
line_terminator = \r|\n|\r\n
input_character = [^\r\n]
white_space = {line_terminator} | [ \t\f]

//COMENTARIOS
//TODO: Permitir comentarios alinhados
//traditional_comment = "{#" [^"#}"]* "#}"
end_of_line_comment = "##"{input_character}* {line_terminator}
comment = {end_of_line_comment} //| {traditional_comment}

//SINTAXE
upper_case     = [A-Z]|Á|À|Ã|Â|Ê|É|Í|Ó|Ô|Õ|Ú|Ç
lower_case     = [a-z]|á|à|ã|â|ê|é|ó|ô|õ|ú|ç
digit          = [0-9]
hexadecimal    = {digit}|[a-f]|[A-F]
letter         = {upper_case} | {lower_case} | "_"
identifier     = {letter} ( {letter} | {digit} )*
number_literal = {digit}+ | "\H"{hexadecimal}+
%%

<YYINITIAL> "INORDER"					{ 
							return symbol(sym.INORDER); }

<YYINITIAL> "inorder"					{ 
							return symbol(sym.INORDER); }

<YYINITIAL> "OUTORDER"					{ 
							return symbol(sym.OUTORDER); }

<YYINITIAL> "outorder"					{ 
							return symbol(sym.OUTORDER); }

<YYINITIAL> ";"					{ 
							return symbol(sym.SEMICOLON); }

<YYINITIAL> "="					{ 
							return symbol(sym.ASSIGN); }

<YYINITIAL> "!"					{ 
								return symbol(sym.EXCLAMATION); }

<YYINITIAL> "'"					{ 
							return symbol(sym.APOSTROPHE); }

<YYINITIAL> "+"					{ 
							return symbol(sym.PLUS); }

<YYINITIAL> "*"					{ 
							return symbol(sym.TIMES); }

<YYINITIAL> "("					{ 
							return symbol(sym.L_PAR); }

<YYINITIAL> ")"					{ 
							return symbol(sym.R_PAR); }

<YYINITIAL> "{"					{ 
							return symbol(sym.L_BRACE); }

<YYINITIAL> "}"					{ 
							return symbol(sym.R_BRACE); }


<YYINITIAL> {identifier}	{ 
								return symbol(sym.IDENTIFIER, yytext()); 
							}

<YYINITIAL> "//"[^\r\n]*[\r|\n|\r\n]	{; }

<YYINITIAL> " "					{;}
<YYINITIAL> [\t]				{;}
<YYINITIAL> [\n]				{;}

<YYINITIAL> .						{ System.out.println("eqn2spice: Unexpected character |"+ yytext()+"|"); }