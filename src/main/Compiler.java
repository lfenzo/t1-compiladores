package main;

import ast.*;
import lexer.Symbol;

public class Compiler {
	
	private Symbol token;
	private int tokenPos;
	private int lineNumber;
	private char []input;
	private char ident;
	private int number;
	
	public Compiler() {
		System.out.println("Voce instanciou um compilador");
	}
	
	public void compile(char []p_input) {
        input = p_input;
        input[input.length - 1] = '\0';
        tokenPos = 0;
        nextToken();
        program();
        if (token != Symbol.EOF)
        	error("Fim de arquivo não esparado.");
        
        //return e;

    }
	
	// Program ::= VarList { Stat } 
	private void program() {
		stat();
		while (token == Symbol.FOR
				|| token == Symbol.WHILE
				|| token == Symbol.IF
				|| token == Symbol.ASSIGN
				|| token == Symbol.PRINT
				|| token == Symbol.PRINT_LINE) {
			stat();
		}
	}
	
	// Stat ::= AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
	private void stat() {
		switch (token) {
		
		case ASSIGN:
			assignStat();
			break;
			
//		case IF:
//			ifStat();
//			break;
			
		case FOR:
			forStat();
			break;
				
		case PRINT:
			printStat();
			break;
			
		case PRINT_LINE:
			printlnStat();
			break;
		
//		z	break;
		
		default:
			error("Erro interno do compilador...");
		}
	}

	// PrintStat ::= "print" Expr ";"
	private void printStat() {
		this.nextToken(); // come o token "print"
		
		expr();
		
		if (token != Symbol.SEMICOLON)
			error("';' esperado");
		
		this.nextToken();
	}
	
	// PrintStat ::= "println" Expr ";"
	private void printlnStat() {
		this.nextToken(); // come o token "print"
		
		expr();
		
		if (token != Symbol.SEMICOLON)
			error("';' esperado");
		
		this.nextToken();
	}

	// "for" Id "in" Expr ".." Expr StatList
	private void forStat() {
		this.nextToken(); // come o token 'for'
		
		if (token == Symbol.ID) {
			
			char id = this.ident;
			this.nextToken();
			
			if (token != Symbol.IN)
				error("'in' esperado");
			
			this.nextToken();
			expr();
			
			if (token != Symbol.TWO_DOTS)
				error("'..' esperado");
			
			this.nextToken();
			expr();
			
			statList();
		}
		else
			error("'=' esperado");
		
	}

	// StatList ::= "{" { Stat } "}" 
	private void statList() {
		if (token != Symbol.OPEN_CBRACES) {
			error("{ esperado");
		}
		this.nextToken();
		
		while (token == Symbol.FOR
				|| token == Symbol.WHILE
				|| token == Symbol.IF
				|| token == Symbol.ASSIGN
				|| token == Symbol.PRINT
				|| token == Symbol.PRINT_LINE) {
			stat();
		}
		
		if (token != Symbol.CLOSE_CBRACES) {
			error("} esperado");
		}
		
		this.nextToken();
	}

	// AssignStat ::= Ident "=" Expr ";"
	private void assignStat() {
		char ident = this.ident;
		this.nextToken();
		
		
		// if not checkSymbol(....... 
		this.checkSymbol(Symbol.ASSIGN);
//		if (token == Symbol.ASSIGN)
//			this.nextToken();
//		else
//			error("'=' esperado");
		
		expr();
		
		this.checkSymbol(Symbol.SEMICOLON);
//		if (token == Symbol.SEMICOLON)
//			this.nextToken();
//		else
//			error("';' esperado");
	}

	private void error() {
		// TODO Auto-generated method stub
		
	}

	private Expr expr() {
		// TODO Auto-generated method stub
		return null;
	}

	public void nextToken() {
		while (tokenPos < input.length &&
				(input[tokenPos] == ' ' || input[tokenPos] == '\n' || input[tokenPos] == '\t') ) {
			tokenPos++;
		}
		
		if (tokenPos < input.length) {
			
			char ch = input[tokenPos];
			
			// recolhe os identificadores com letras (palavras chave + statements)
			if (Character.isLetter(ch)) {
				
				if (tokenPos + 2 < input.length
						&& input[tokenPos    ] == 'f'
						&& input[tokenPos + 1] == 'o'
						&& input[tokenPos + 2] == 'r') {
					// TODO resolver probelma com variavel a la (for[+])
					token = Symbol.FOR;
					tokenPos += 3;
				}
				else if (tokenPos + 1 < input.length
							&& input[tokenPos    ] == 'i'
							&& input[tokenPos + 1] == 'n') {
					token = Symbol.IN;
					tokenPos += 2;
				}
				else if (tokenPos + 1 < input.length
							&& input[tokenPos    ] == 'i'
							&& input[tokenPos + 1] == 'f') {
					token = Symbol.IF;
					tokenPos += 2;
				}
				else if (tokenPos + 3 < input.length
							&& input[tokenPos    ] == 'e'
							&& input[tokenPos + 1] == 'l'
							&& input[tokenPos + 2] == 's'
							&& input[tokenPos + 3] == 'e') {
					token = Symbol.ELSE;
					tokenPos += 4;
				}
				else if (tokenPos + 4 < input.length
							&& input[tokenPos    ] == 'p'
							&& input[tokenPos + 1] == 'r'
							&& input[tokenPos + 2] == 'i'
							&& input[tokenPos + 3] == 'n'
							&& input[tokenPos + 4] == 't') {
					token = Symbol.PRINT;
					tokenPos += 5;
				}
				else if (tokenPos + 6 < input.length
							&& input[tokenPos    ] == 'p'
							&& input[tokenPos + 1] == 'r'
							&& input[tokenPos + 2] == 'i'
							&& input[tokenPos + 3] == 'n'
							&& input[tokenPos + 3] == 't'
							&& input[tokenPos + 3] == 'l'							
							&& input[tokenPos + 4] == 'n') {
					token = Symbol.PRINT_LINE;
					tokenPos += 7;
				}
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == 'v'
							&& input[tokenPos + 1] == 'a'
							&& input[tokenPos + 2] == 'r') {
					token = Symbol.VAR;
					tokenPos += 3;
				}
				else if (tokenPos + 4 < input.length
							&& input[tokenPos    ] == 'w'
							&& input[tokenPos + 1] == 'h'
							&& input[tokenPos + 2] == 'i'
							&& input[tokenPos + 3] == 'l'
							&& input[tokenPos + 3] == 'e') {
					token = Symbol.WHILE;
					tokenPos += 5;
				}
				else {
					token = Symbol.ID;
					// TODO colocar while para pegar os identificadores com multiplas letras.
					tokenPos++;
					this.ident = ch;
				}

			// recolhe os números
			} else if (Character.isDigit(ch)) {
				
				this.number = ch - '0';
				tokenPos++;
				
				while ( tokenPos < input.length && Character.isDigit(ch = input[tokenPos]) ) {
					number = number * 10 + (ch - '0');
					tokenPos++;
				}
				
				token = Symbol.NUMBER;
			}
			// tokens com 1 ou dois characteres.
			else {
				switch (ch) {
				
				case '=':
					token = Symbol.ASSIGN;
					tokenPos++;
					break;
				
				case ';':
					token = Symbol.SEMICOLON;
					tokenPos++;
					break;
					
				case '+':
					token = Symbol.PLUS;
					tokenPos++;
					break;
					
				case '-':
					token = Symbol.MINUS;
					tokenPos++;
					break;
					
				case '{':
					token = Symbol.OPEN_CBRACES;
					tokenPos++;
					break;
					
				case '}':
					token = Symbol.CLOSE_CBRACES;
					tokenPos++;
					break;
					
				case '<':
					token = Symbol.LT;
					tokenPos++;
					break;
				}
			}
		}
		else
			token = Symbol.EOF;
	}
	
	// verifica se o token corrente é aquele esperado
	private void checkSymbol(Symbol esperado) {
		if (token == esperado)
			this.nextToken();
		else
			error("'" + esperado + "' esperado.");
	}
	
	private void error(String msg) {
		if (tokenPos == 0)
			tokenPos = 0;
		else
			if (tokenPos >= input.length)
				tokenPos = input.length;
		
		throw new RuntimeException("Minha mensagem de erro");
	}
}
