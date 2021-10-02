package main;

import ast.*;
import ast.Number;
import lexer.Symbol;

public class Compiler {
	
	private Symbol token;
	private int tokenPos;
	private int lineNumber = 1;
	private char []input;
	private String ident;
	private Number number = new Number(0);
	private int localValue;
	private VarList vList;
	
	public Program compile(char []p_input, char opcao) {
		
        this.input = p_input;
        this.tokenPos = 0;
        this.vList = new VarList();
        
        nextToken();
        
        Program p = program();
        
        // token pos estará 1 posiç[ao apos o termino da string de entrada.
        if (tokenPos == (input.length + 1)) {
        	error("Fim de arquivo não esparado.");
        }
        
        if (opcao == 'g') {
            p.genC();
        }
        else if (opcao == 'r') {
            p.run();
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
		
		switch (token) {
		
		case BOOLEAN:
			BooleanType b = booleanType();
			break;
		
		case INT:
			IntType i = intType();
			break;
		
		case STRING:
			StringType s = stringType();
			break;
	
		default:
			error("Erro interno no compilador");
			break;
		}
		
		//this.checkSymbol(Symbol.INT);
		this.checkSymbol(Symbol.ID);
		
		id = this.ident;
		
		// TODO associar a cada variával o seu tipo
		Var v = new Var(id);
		
		this.checkSymbol(Symbol.SEMICOLON);
		
		return v;
	}
	
	private BooleanType booleanType() {
		
		this.nextToken(); // come o token "Boolean"
		BooleanType b = new BooleanType("boolean");
		
		return b;
	}	
	
	private IntType intType() {
		
		this.nextToken(); // come o token "Int"
		IntType i = new IntType("int");
		
		return i;
	}
	
	private StringType stringType() {
		
		this.nextToken(); // come o token "String"
		StringType s = new StringType("string");
		
		return s;
	}
	
	private Var getVar(String id, boolean is_for) {
		for(int i = 0; i < this.vList.getSize(); i++) {
			if(this.vList.getElement(i).getId() == id) {
				return this.vList.getElement(i);
			}
		}
		
		if(!is_for)
			error("Vari�vel n�o declarada");
		return null;
	}
	
	// Stat ::= AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
	private Stat stat() {
		switch (token) {
		
		case ID: return assignStat(); // assignStat é iniciado por um identificador
			
		case IF: return ifStat(); 
			
		case FOR: return forStat();
		
		case WHILE: return whileStat();
				
		case PRINT: return printStat();
			
		case PRINT_LINE: return printlnStat(); 
		
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
//			Expr e_else = expr();
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
		
		Var v = this.getVar(id, true);
		
		if(v != null)
			error("Vari�vel do for n�o pode ter sido declarada antes");
		
		this.checkSymbol(Symbol.IN);
		
		Expr begin_expr = expr();
		
		v = new Var(id);
		v.setValue(begin_expr.eval());
		
		this.vList.addVar(v);
		
		this.checkSymbol(Symbol.TWO_DOTS);
		
		Expr end_expr = expr();
		StatList s = statList();
						
		ForStat for_stat = new ForStat(v, begin_expr, end_expr, s);
		
		this.vList.removeVar(id);
		
		return for_stat;
	}

	// AssignStat ::= Ident "=" Expr ";"
	private AssignStat assignStat() {
		String ident = this.ident;
		
		Var actual_var = this.vList.varExists(ident);
		
		// verifica se a vari�vel � esquerda do '=' j� foi declarada
		if ( vList.varExists(ident) == null) {
			error("Vari�vel '" + ident + "' n�o declarada.");
		}
		
		this.nextToken(); // come o token "identificador"
		
		this.checkSymbol(Symbol.ASSIGN);
		Expr e = expr();
		this.checkSymbol(Symbol.SEMICOLON);
		
		actual_var.setValue(e.eval());
		AssignStat a = new AssignStat(actual_var, e);
		
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
			a.setOperator("&&");
			a.setExprDir(relExpr());
		}
		
		return a;
	}

	// RelExpr ::= AddExpr [ RelOp AddExpr ]
	private RelExpr relExpr() {
		
		RelExpr r = new RelExpr(addExpr());
		String op = "==";
		
		if (token == Symbol.LT || token == Symbol.LE ||
			token == Symbol.GT || token == Symbol.GE ||
			token == Symbol.EQ || token == Symbol.NEQ) {
			
			if(token == Symbol.LT)
				op = "<";
			else if(token == Symbol.LE)
				op = "<=";
			else if(token == Symbol.GT)
				op = ">";
			else if(token == Symbol.GE)
				op = ">=";
			else if(token == Symbol.EQ)
				op = "==";
			else if(token == Symbol.LE)
				op = "!=";
			
			r.setOperator(op);
			this.nextToken(); // come o token do operador
			r.setDirExpr(addExpr());
		}
		
		return r;
	}
	
	//	AddExpr ::= MultExpr { AddOp MultExpr }
	private AddExpr addExpr() {
		
		AddExpr a = new AddExpr(multExpr());
		char op = '+';
		
		while (token == Symbol.PLUS || token == Symbol.MINUS) {
			if(token == Symbol.PLUS)
				op = '+';
			else if(token == Symbol.MINUS)
				op = '-';
			a.setOperator(op);
			this.nextToken(); // come o operador (+ ou -)
			a.setDirExpr(multExpr());
		}
		
		return a;
	}

	// MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	private MultExpr multExpr() {	
		
		MultExpr m = new MultExpr(simpleExpr());
		char op = '*';
		
		while (token == Symbol.MULT || token == Symbol.DIV || token == Symbol.PERC) {
			if(token == Symbol.MULT)
				op = '*';
			else if(token == Symbol.DIV)
				op = '/';
			else if(token == Symbol.PERC)
				op = '%';
			
			m.setOperator(op);
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
		
		case OPEN_PAR:
			this.nextToken(); // come o (
			Expr e = expr();
			this.checkSymbol(Symbol.CLOSE_PAR);
			return e;
			
		case NOT:
			this.nextToken(); // come o "!"
			return simpleExpr();
			
		case PLUS:
			this.nextToken(); // come o operador +
			return simpleExpr();
			
		case MINUS:
			this.nextToken(); // come o operador -
			return simpleExpr();
		
		case ID:
			Var var_name = new Var(this.ident);
			var_name = this.vList.varExists(this.ident);
			this.nextToken(); 
			return var_name;
		default:
			break;
		}
		
		error("Simple Expr inv�lido!");
		return null;
	}

	// Number ::= [’+’|’-’] Digit { Digit }
	private Number number() {
		if (token == Symbol.PLUS || token == Symbol.MINUS) {
//			this.localValue *= -1;
			this.nextToken();
		}
		
		this.checkSymbol(Symbol.NUMBER);
		
		Number n = new Number(this.localValue);
		
		return n;
	}

	
	public void nextToken() {
		while (tokenPos < input.length &&
				(input[tokenPos] == ' ' || input[tokenPos] == '\n' || input[tokenPos] == '\t' || input[tokenPos] == '\r') ) {
			if(input[tokenPos] == '\n')
				this.lineNumber++;
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
				
				this.localValue = ch - '0';
				
//				this.number.setValue(ch - '0');
				tokenPos++;
				
				while ( tokenPos < input.length && Character.isDigit(ch = input[tokenPos]) ) {
//					this.number.setValue(this.number.getValue() * 10 + (ch - '0'));
					this.localValue = this.localValue * 10 + (ch - '0');
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
		
		System.out.println(this.tokenPos);
		throw new RuntimeException(msg);
	}
}
