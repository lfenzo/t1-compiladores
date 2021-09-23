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
	private VarList vList;
	
	public Compiler() {
		System.out.println("Voce instanciou um compilador");
	}
	
	public Program compile(char []p_input) {
        input = p_input;
        //input[input.length] '\0';
        tokenPos = 0;
        this.vList = new VarList();
        nextToken();
        Program p = program();
        
        // token pos estará 1 posiç[ao apos o termino da string de entrada.
        if (tokenPos == (input.length + 1)) {
        	error("Fim de arquivo não esparado.");
        }
        
        return p;
    }
	
	// Program ::= VarList { Stat } 
	private Program program() {
		
		StatList s = new StatList();
		
		varlist();
		
		while (token == Symbol.FOR
				|| token == Symbol.WHILE
				|| token == Symbol.IF
				|| token == Symbol.ID // assignStat é iniciado por um identificador
				|| token == Symbol.PRINT
				|| token == Symbol.PRINT_LINE) {
			s.addStat(stat());
		}
		
		Program p = new Program(vList, s);
		
		return p;
	}
		
	// VarList ::= { "var" Int Ident ";" }
	private void varlist() {
				
		while (token == Symbol.VAR) {
			this.vList.addVar(var());
		}
	}
	
	// var ::= "var Int" ident ";"
	private Var var() {
		
		String id;
		
		this.nextToken(); // come o token 'var'
		
		this.checkSymbol(Symbol.INT);
		this.checkSymbol(Symbol.ID);
		
		id = this.ident;
		Var v = new Var(id);
		
		this.checkSymbol(Symbol.SEMICOLON);
		
		return v;
	}
	
	private Var getVar(String id) {
		for(int i = 0; i < this.vList.getSize(); i++) {
			if(this.vList.getElement(i).getId() == id) {
				return this.vList.getElement(i);
			}
		}
		error("Vari�vel n�o declarada");
		return null;
	}
	
	// Stat ::= AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
	private Stat stat() {
		switch (token) {
		
		case ID: return assignStat(); break; // assignStat é iniciado por um identificador
			
		case IF: return ifStat(); break;
			
		case FOR: return forStat(); break;
		
		case WHILE: return whileStat(); break;
				
		case PRINT: return printStat(); break;
			
		case PRINT_LINE: return printlnStat(); break;
		
		default:
			error("Erro interno do compilador...");
			return null;
		}
	}
	
	// WhileStat ::= "while" Expr StatList
	private WhileStat whileStat() {
		this.nextToken(); // come o token "while"
				
		Expr e = expr();
		StatList s = statList();
		
		WhileStat w = new WhileStat(e, s);
		
		return w;
	}

	// IfStat ::= "if" Expr StatList [ "else" StatList ]
	private IfStat ifStat() {
		this.nextToken(); // come o token "if"
		
		Expr e = expr();
		StatList s = statList();
		
		IfStat i = new IfStat(e, s);
		
		if (token == Symbol.ELSE) {
			this.nextToken(); // come o token "else"
//			expr();
			i.setStatlistElse(statList());
		}
		
		return i;
	}

	// StatList ::= "{" { Stat } "}"
	private StatList statList() {
				
		this.checkSymbol(Symbol.OPEN_CBRACES);
		
		StatList s = new StatList();
		
		while (token == Symbol.FOR
				|| token == Symbol.WHILE
				|| token == Symbol.IF
				|| token == Symbol.ID // assignStat começa com um identificador
				|| token == Symbol.PRINT
				|| token == Symbol.PRINT_LINE) {
			s.addStat(stat());
		}
		
		this.checkSymbol(Symbol.CLOSE_CBRACES);
		
		return s;
	}

	// PrintStat ::= "print" Expr ";"
	private PrintStat printStat() {
		
		this.nextToken(); // come o token "print"
		Expr e = expr();
		this.checkSymbol(Symbol.SEMICOLON);
		
		PrintStat prStat = new PrintStat(e, false);
		
		return prStat;
	}
	
	// PrintStat ::= "println" Expr ";"
	private PrintStat printlnStat() {
		
		this.nextToken(); // come o token "println"		
		Expr e = expr();
		this.checkSymbol(Symbol.SEMICOLON);
		
		PrintStat prlStat = new PrintStat(e, true);
		
		return prlStat;
	}

	// "for" Id "in" Expr ".." Expr StatList
	private ForStat forStat() {
		
		String id;
		
		this.nextToken(); // come o token 'for'
		this.checkSymbol(Symbol.ID);
		
		id = this.ident;
		
		this.checkSymbol(Symbol.IN);
		
		Expr begin_expr = expr();
		
		this.checkSymbol(Symbol.TWO_DOTS);
		
		Expr end_expr = expr();
		StatList s = statList();
		Var v = this.getVar(id);
		
		ForStat for_stat = new ForStat(v, begin_expr, end_expr, s);
		
		return for_stat;
	}

	// AssignStat ::= Ident "=" Expr ";"
	private AssignStat assignStat() {
		String ident = this.ident;
		this.nextToken(); // come o token "identificador"
		
		this.checkSymbol(Symbol.ASSIGN);
		Expr e = expr();
		this.checkSymbol(Symbol.SEMICOLON);
		
		AssignStat a = new AssignStat(ident, e);
		
		return a;
	}

	//  Expr ::= AndExpr [ "||" AndExpr ]
	private Expr expr() {
		
		Expr e = new Expr (andExpr());
		
		if (token == Symbol.OR) {
			this.nextToken(); // come o "||"
			e.setExprDir(andExpr()); 
		}
		
		return e;
	}
	
	// AndExpr ::= RelExpr [ "&&" RelExpr ]
	private AndExpr andExpr() {
		
		AndExpr a = new AndExpr(relExpr());
		
		if (token == Symbol.AND) {
			this.nextToken(); // come o '&&'
			a.setExprDir(relExpr());
		}
		
		return a;
	}

	// RelExpr ::= AddExpr [ RelOp AddExpr ]
	private RelExpr relExpr() {
		
		RelExpr r = new RelExpr(addExpr());
				
		if (token == Symbol.LT || token == Symbol.LE ||
			token == Symbol.GT || token == Symbol.GE ||
			token == Symbol.EQ || token == Symbol.NEQ) {
			
			this.nextToken(); // come o token do operador
			r.setDirExpr(addExpr());
		}
		
		return r;
	}
	
	//	AddExpr ::= MultExpr { AddOp MultExpr }
	private AddExpr addExpr() {
		
		AddExpr a = new AddExpr(multExpr());
		
		while (token == Symbol.PLUS || token == Symbol.MINUS) {
			this.nextToken(); // come o operador (+ ou -)
			a.setDirExpr(multExpr());
		}
		
		return a;
	}

	// MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	private MultExpr multExpr() {	
		
		MultExpr m = new MultExpr(simpleExpr());
		
		while (token == Symbol.MULT || token == Symbol.DIV || token == Symbol.PERC) {
			this.nextToken(); // come o operador (+ ou -)
			m.setExprDir(simpleExpr());
		}
		
		return m;
	}

	//	SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr| AddOp SimpleExpr | Ident
	private SimpleExpr simpleExpr() {
		
		switch (token) {
		
		case NUMBER:
			return number();
			break;
		
		case OPEN_PAR:
			this.nextToken(); // come o (
			return expr();
			this.checkSymbol(Symbol.CLOSE_PAR);
			break;
			
		case NOT:
			this.nextToken(); // come o "!"
			return simpleExpr();
			break;
			
		case PLUS:
			this.nextToken(); // come o operador +
			return simpleExpr();
			break;
			
		case MINUS:
			this.nextToken(); // come o operador -
			return simpleExpr();
			break;
		
		case ID:
			String var_name = this.ident;
			this.nextToken(); // come o token do identificado
			
		default:
			break;
		}
	}

	// Number ::= [’+’|’-’] Digit { Digit }
	private Number number() {
		if (token == Symbol.PLUS || token == Symbol.MINUS) {
			this.nextToken();
		}
		
		this.checkSymbol(Symbol.NUMBER);
		
		return this.number;
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
							&& input[tokenPos + 4] == 'e') {
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
					
				case '*':
					token = Symbol.MULT;
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
