package main;

import ast.*;
import lexer.Symbol;

public class Compiler {
	
	private Symbol token;
	private int tokenPos;
	private int lineNumber;
	private char []input;
	private String ident;
	private int number;
	
	public Compiler() {
		System.out.println("Voce instanciou um compilador");
	}
	
	public void compile(char []p_input) {
        input = p_input;
        //input[input.length] '\0';
        tokenPos = 0;
        nextToken();
        program();
        
        // token pos estará 1 posiç[ao apos o termino da string de entrada.
        if (tokenPos == (input.length + 1)) {
        	error("Fim de arquivo não esparado.");
        }
        else
        	System.out.println("compilação terminada sem erros");

        //return e;
    }
	
	// Program ::= VarList { Stat } 
	private void program() {
		varlist();
		while (token == Symbol.FOR
				|| token == Symbol.WHILE
				|| token == Symbol.IF
				|| token == Symbol.ID // assignStat é iniciado por um identificador
				|| token == Symbol.PRINT
				|| token == Symbol.PRINT_LINE) {
			stat();
		}
	}
	
	// VarList ::= { "var" Int Ident ";" }
	private void varlist() {
		while (token == Symbol.VAR) {
			var();
		}
	}
	
	// var ::= "var Int" ident ";"
	private void var() {
		this.nextToken(); // come o token 'var'
		
		this.checkSymbol(Symbol.INT);
		this.checkSymbol(Symbol.ID);
		this.checkSymbol(Symbol.SEMICOLON);
	}

	// Stat ::= AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
	private void stat() {
		switch (token) {
		
		case ID: assignStat(); break; // assignStat é iniciado por um identificador
			
		case IF: ifStat(); break;
			
		case FOR: forStat(); break;
		
		case WHILE: whileStat(); break;
				
		case PRINT: printStat(); break;
			
		case PRINT_LINE: printlnStat(); break;
		
		default:
			error("Erro interno do compilador...");
		}
	}
	
	// WhileStat ::= "while" Expr StatList
	private void whileStat() {
		this.nextToken(); // come o token "while"
		
		expr();
		statList();
	}

	// IfStat ::= "if" Expr StatList [ "else" StatList ]
	private void ifStat() {
		this.nextToken(); // come o token "if"
		
		expr();
		statList();
		
		if (token == Symbol.ELSE) {
			this.nextToken(); // come o token "else"
			expr();
			statList();
		}	
	}

	// StatList ::= "{" { Stat } "}"
	private void statList() {
				
		this.checkSymbol(Symbol.OPEN_CBRACES);
		
		while (token == Symbol.FOR
				|| token == Symbol.WHILE
				|| token == Symbol.IF
				|| token == Symbol.ID // assignStat começa com um identificador
				|| token == Symbol.PRINT
				|| token == Symbol.PRINT_LINE) {
			stat();
		}
		
		this.checkSymbol(Symbol.CLOSE_CBRACES);
	}

	// PrintStat ::= "print" Expr ";"
	private void printStat() {
		
		this.nextToken(); // come o token "print"
		expr();
		this.checkSymbol(Symbol.SEMICOLON);
	}
	
	// PrintStat ::= "println" Expr ";"
	private void printlnStat() {
		
		this.nextToken(); // come o token "println"		
		expr();
		this.checkSymbol(Symbol.SEMICOLON);
	}

	// "for" Id "in" Expr ".." Expr StatList
	private void forStat() {
		
		this.nextToken(); // come o token 'for'
		
		this.checkSymbol(Symbol.ID);
		this.checkSymbol(Symbol.IN);
		
		expr();
		
		this.checkSymbol(Symbol.TWO_DOTS);
		
		expr();
		statList();	
	}

	// AssignStat ::= Ident "=" Expr ";"
	private void assignStat() {
		String ident = this.ident;
		this.nextToken(); // come o token "identificador"
		
		this.checkSymbol(Symbol.ASSIGN);
		expr();
		this.checkSymbol(Symbol.SEMICOLON);
	}

	//  Expr ::= AndExpr [ "||" AndExpr ]
	//	AndExpr ::= RelExpr [ "&&" RelExpr ]
	//	RelExpr ::= AddExpr [ RelOp AddExpr ]
	//	AddExpr ::= MultExpr { AddOp MultExpr }
	//	MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	//	SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr| AddOp SimpleExpr | Ident
	private void expr() {
		
		andExpr();
		
		if (token == Symbol.OR) {
			this.nextToken(); // come o "||"
			andExpr();
		}
	}
	
	// AndExpr ::= RelExpr [ "&&" RelExpr ]
	private void andExpr() {
		
		relExpr();
		
		if (token == Symbol.AND) {
			this.nextToken(); // come o '&&'
			relExpr();
		}
	}

	// RelExpr ::= AddExpr [ RelOp AddExpr ]
	private void relExpr() {
		
		addExpr();
				
		if (token == Symbol.LT || token == Symbol.LE ||
			token == Symbol.GT || token == Symbol.GE ||
			token == Symbol.EQ || token == Symbol.NEQ) {
			
			this.nextToken(); // come o token do operador
			addExpr();
		}
	}
	
	//	AddExpr ::= MultExpr { AddOp MultExpr }
	private void addExpr() {
		
		multExpr();
		
		while (token == Symbol.PLUS || token == Symbol.MINUS) {
			this.nextToken(); // come o operador (+ ou -)
			multExpr();
		}
	}

	// MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	private void multExpr() {	
		
		simpleExpr();
		
		while (token == Symbol.MULT || token == Symbol.DIV || token == Symbol.PERC) {
			this.nextToken(); // come o operador (+ ou -)
			simpleExpr();
		}
	}

	//	SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr| AddOp SimpleExpr | Ident
	private void simpleExpr() {
		
		switch (token) {
		
		case NUMBER:
			number();
			break;
		
		case OPEN_PAR:
			this.nextToken(); // come o (
			expr();
			this.checkSymbol(Symbol.CLOSE_PAR);
			break;
			
		case NOT:
			this.nextToken(); // come o "!"
			simpleExpr();
			break;
			
		case PLUS:
			this.nextToken(); // come o operador +
			simpleExpr();
			break;
			
		case MINUS:
			this.nextToken(); // come o operador -
			simpleExpr();
			break;
		
		case ID:
			String var_name = this.ident;
			this.nextToken(); // come o token do identificado
			
		default:
			break;
		}
	}

	// Number ::= [’+’|’-’] Digit { Digit }
	private void number() {
		if (token == Symbol.PLUS || token == Symbol.MINUS) {
			this.nextToken();
		}
		
		this.checkSymbol(Symbol.NUMBER);
	}

	
	public void nextToken() {
		while (tokenPos < input.length &&
				(input[tokenPos] == ' ' || input[tokenPos] == '\n' || input[tokenPos] == '\t') ) {
			tokenPos++;
		}
		
		if (tokenPos < input.length) {
			
			char ch = input[tokenPos];
			
			// recolhe os identificadores com letras (palavras chave + statements)
			if (Character.isLetter(ch)
					|| ch == '_'
					|| ch == '|'
					|| ch == '&'
					|| ch == '.') {
				
				if (tokenPos + 1 < input.length
							&& input[tokenPos    ] == '|'
							&& input[tokenPos + 1] == '|') {
					token = Symbol.OR;
					tokenPos += 2;
				}
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == '&'
							&& input[tokenPos + 1] == '&') {
					token = Symbol.AND;
					tokenPos += 2;
				}
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == '.'
							&& input[tokenPos + 1] == '.') {
					token = Symbol.TWO_DOTS;
					tokenPos += 2;
				}
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == 'f'
							&& input[tokenPos + 1] == 'o'
							&& input[tokenPos + 2] == 'r') {
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
				else if (tokenPos + 6 < input.length
							&& input[tokenPos    ] == 'p'
							&& input[tokenPos + 1] == 'r'
							&& input[tokenPos + 2] == 'i'
							&& input[tokenPos + 3] == 'n'
							&& input[tokenPos + 4] == 't'
							&& input[tokenPos + 5] == 'l'							
							&& input[tokenPos + 6] == 'n') {
					token = Symbol.PRINT_LINE;
					tokenPos += 7;
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
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == 'v'
							&& input[tokenPos + 1] == 'a'
							&& input[tokenPos + 2] == 'r') {
					token = Symbol.VAR;
					tokenPos += 3;
				}
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == 'I'
							&& input[tokenPos + 1] == 'n'
							&& input[tokenPos + 2] == 't') {
					token = Symbol.INT;
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
					String identifier = "";
					
					while (tokenPos < input.length && (Character.isLetter(ch = input[tokenPos])
													   || Character.isDigit(ch = input[tokenPos])
													   || ch == '_') ) {
						identifier += input[tokenPos];
						tokenPos++;
					}
					
					token = Symbol.ID;
					this.ident = identifier;
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
			// tokens com 1 ou 2 characteres.
			else {
				switch (ch) {
				
				case '=':
					
					tokenPos++;
					
					if (tokenPos < input.length && input[tokenPos] == '=') {
						token = Symbol.EQ;
						tokenPos++;
					} else
						token = Symbol.ASSIGN;
					
					break;
					
				case '<':
					
					tokenPos++;
					
					// <=
					if (tokenPos < input.length && input[tokenPos] == '=') {
						token = Symbol.LE;
						tokenPos++;
					} else
						token = Symbol.LT;
					
					break;

				case '>':
					
					tokenPos++;
					
					// >=
					if (tokenPos < input.length && input[tokenPos] == '=') {
						token = Symbol.GE;
						tokenPos++;
					} else
						token = Symbol.GT;
					
					break;
					
				case '!':
					
					tokenPos++;
					
					// !=
					if (tokenPos < input.length & input[tokenPos] == '=') {
						token = Symbol.NEQ;
						tokenPos++;
					} else
						token = Symbol.NOT;
					
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
					
				case '/':
					token = Symbol.DIV;
					tokenPos++;
					break;
					
				case '%':
					token = Symbol.PERC;
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
					
				case '(':
					token = Symbol.OPEN_PAR;
					tokenPos++;
					break;
					
				case ')':
					token = Symbol.CLOSE_PAR;
					tokenPos++;
					break;
				}
			}
		}
		else
			token = Symbol.EOF;
	}
	
	// verifica se o token corrente é aquele esperado (se for avança, caso contrário lança um erro)
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
		
		throw new RuntimeException(msg);
	}
}
