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
	private String string;
	private boolean bool;
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
		
		if (token != Symbol.EOF) {
			error(token + " Inesperado na linha " + lineNumber);
		}
		
		Program p = new Program(vList, s);
		
		return p;
	}
		
	// VarList ::= { "var" Type Ident ";" }
	private void varlist() {
				
		while (token == Symbol.VAR) {
			this.vList.addVar(var());
		}
	}
	
	private Type type() {
		
		Type result;
		
		switch (token) {
		
		case INT :
			result = Type.intType;
			break;
		
		case BOOLEAN :
			result = Type.booleanType;
			break;
			
		case STRING :
			result = Type.stringType;
			break;
		
		default :
			error("Tipo da variavel esperado...");
			result = null;
		}
		
		this.nextToken(); // come o token do tipo
		
		return result;
	}
	
	private Variable var() {
		
		this.nextToken(); // come o token 'var'
		
		String id;
		Type type = null;
		
		if (token == Symbol.BOOLEAN || token == Symbol.INT || token == Symbol.STRING) {
			type = type(); // type() é responsável por comer o token do tipo
		}
		else
			error("Identificador de tipo esperado...");
			
		this.checkSymbol(Symbol.ID); // tome o token que identifica a variavel
				
		id = this.ident;
		
		Variable v = new Variable(id, type);
		
		this.checkSymbol(Symbol.SEMICOLON);
		
		return v;
	}
	
	
	private Variable getVar(String id, boolean is_for) {
		
		for(int i = 0; i < this.vList.getSize(); i++) {
			if(this.vList.getElement(i).getId() == id) {
				return this.vList.getElement(i);
			}
		}
		
		if(!is_for)
			error("Variavel nao declarada");
		
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
		
		Variable v = this.getVar(id, true);
		
		if(v != null)
			error("Vari�vel do for n�o pode ter sido declarada antes");
		
		this.checkSymbol(Symbol.IN);
		
		Expr begin_expr = expr();
		
		v = new Variable(id);
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
		
		Variable actual_var = this.vList.varExists(ident);
		
		// verifica se a variavel a esquerda do '=' ja foi declarada
		if ( vList.varExists(ident) == null ) {
			error("Variavel '" + ident + "' nao declarada.");
		}
		
		this.nextToken(); // come o token "identificador"
		
		this.checkSymbol(Symbol.ASSIGN);
		Expr e = expr();
		this.checkSymbol(Symbol.SEMICOLON);		
	
		if (e.getType() == actual_var.getType()) {
			
			AssignStat a = new AssignStat(actual_var, e);
			return a;
		
		}
		else {
			error("Assignment da variavel '" + actual_var.getId() + "' incompativel com o seu tipo.");
			return null; // so para o eclpse não reclamar
		}		
	}

	// Expr ::= OrExpr { "++" OrExpr }
	private Expr expr() {
		
		Expr left, right;
		left = orExpr();
				
		if (token == Symbol.CONCAT) {
			
			this.nextToken(); // come o operador "++"
			right = orExpr();
			
			if (left.getType() != Type.stringType || right.getType() != Type.stringType)
				error("[Line " + this.lineNumber + "]: Expression of StringType expected...");
			
			left = new CompositeExpr(left, Symbol.CONCAT, right);
		}
		
		return left;
	}

	// OrExpr ::= AndExpr [ "||" AndExpr ]
	private Expr orExpr() {
		
		Expr left, right;
		left = andExpr();
		
		if (token == Symbol.OR) {
			
			this.nextToken(); // come o "||"
			right = andExpr();
			
			if (left.getType() != Type.booleanType || right.getType() != Type.booleanType) 
				error("[Line " + this.lineNumber + "]: Expression of BooleanType expected...");
			
			left = new CompositeExpr(left, Symbol.OR, right);
		}
		
		return left;
	}
	
	// AndExpr ::= RelExpr [ "&&" RelExpr ]
	private Expr andExpr() {
		
		Expr left, right;
		left = relExpr();
		
		if (token == Symbol.AND) {
			
			this.nextToken(); // come o operador "&&"
			right = relExpr();
			
			if (left.getType() != Type.booleanType || right.getType() != Type.booleanType) 
				error("[Line " + this.lineNumber + "]: Expression of BooleanType expected...");
			
			left = new CompositeExpr(left, Symbol.AND, right);
		}
		
		return left;
	}

	// RelExpr ::= AddExpr [ RelOp AddExpr ]
	private Expr relExpr() {
		
		Symbol op = token;
		Expr left, right;
		left = addExpr();
		
		if (op == Symbol.EQ || op == Symbol.NEQ ||
			op == Symbol.LE || op == Symbol.LT  ||
			op == Symbol.GE || op == Symbol.GT) {
			
			this.nextToken(); // come o operador (seja la ele qual for)
			right = addExpr();
			
			if (left.getType() != right.getType())
				error("[Line " + this.lineNumber + "]: Type error in expression...");
		
			left = new CompositeExpr(left, op, right);
		}
		
		return left;
	}
	
	//	AddExpr ::= MultExpr { AddOp MultExpr }
	private Expr addExpr() {
		
		Symbol op;
		Expr left, right;
		left = multExpr();
		
		while ( (op = token) == Symbol.PLUS || op == Symbol.MINUS) {
			
			this.nextToken(); // come o token do operador
			right = multExpr();
			
			if (left.getType() != Type.intType || right.getType() != Type.intType)
				error("[Line " + this.lineNumber + "]: Expression of type Int expected...");
			
			left = new CompositeExpr(left, op, right);
		}
		
		return left;
	}

	// MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	private Expr multExpr() {	
		
		Symbol op;
		Expr left, right;
		left = simpleExpr();
		
		while ( (op = token) == Symbol.MULT || op == Symbol.DIV || op == Symbol.PERC) {
			
			this.nextToken(); // come o token do operador atual
			right = simpleExpr();
			
			if (left.getType() != Type.intType || right.getType() != Type.intType)
				error("[Line " + this.lineNumber + "]: Expression of type Int expected...");
			
			left = new CompositeExpr(left, op, right); 
		}
		
		return left;
	}

	//	SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr| AddOp SimpleExpr | Ident
	private Expr simpleExpr() {
		
		Expr e;
				
		switch (token) {
		
		case NUMBER:
			return number();
			
		case TRUE:
			this.nextToken(); // come o token "true"
			return new BooleanExpr(true);
			
		case FALSE:
			this.nextToken(); // come o token "false"
			return new BooleanExpr(false);
			
		case QUOTE:
			return stringExpr();
		
		case OPEN_PAR:
			this.nextToken(); // come o (
			e = expr();
			this.checkSymbol(Symbol.CLOSE_PAR);
			return e;
			
		case NOT:
			this.nextToken(); // come o "!"
			e = expr();
			
			if (e.getType() != Type.booleanType)
				error("[Line " + this.lineNumber + "]: Expression of type Boolean expected...");
			
			return new UnaryExpr(e, Symbol.NOT);
			
		case PLUS:
			this.nextToken(); // come o operador "+"
			e = expr();
			
			if (e.getType() != Type.intType)
				error("[Line " + this.lineNumber + "]: Expression of type Int expected...");
			
			return new UnaryExpr(e, Symbol.PLUS);
			
		case MINUS:
			this.nextToken(); // come o operador "-"
			e = expr();
			
			if (e.getType() != Type.intType)
				error("[Line " + this.lineNumber + "]: Expression of type Int expected...");
			
			return new UnaryExpr(e, Symbol.MINUS);
	
		default: // token == ID

			if (token != Symbol.ID)
				error("[Line " + this.lineNumber + "]: Expression of type Int expected...");
			
			Variable variable = this.vList.varExists(this.ident);
			
			if (variable == null)
				error("[Line " + this.lineNumber + "]: Variable " + this.ident + " not declared...");
			
			this.nextToken(); // come o identificador da variavel
			
			return new VariableExpr(variable);
		}
	}
	
	private Expr stringExpr() {
		
		this.checkSymbol(Symbol.QUOTE); // come o "token" que é a string inteira
		Expr string_expr = new StringExpr(this.string); // 'string' eh o conteudo local da string
		return string_expr;
	}

	// Number ::= [’+’|’-’] Digit { Digit }
	private NumberExpr number() {
		
		this.checkSymbol(Symbol.NUMBER); // come o token do numero e armazena o seu valor
		NumberExpr number_expr = new NumberExpr(this.localValue);
		return number_expr;
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
				else if (tokenPos + 3 < input.length
							&& input[tokenPos    ] == 't'
							&& input[tokenPos + 1] == 'r'
							&& input[tokenPos + 2] == 'u'
							&& input[tokenPos + 3] == 'e') {
					bool = true;
					token = Symbol.TRUE;
					tokenPos += 4;
				}
				else if (tokenPos + 4 < input.length
							&& input[tokenPos    ] == 'f'
							&& input[tokenPos + 1] == 'a'
							&& input[tokenPos + 2] == 'l'
							&& input[tokenPos + 3] == 's'
							&& input[tokenPos + 4] == 'e') {
					bool = false;
					token = Symbol.FALSE;
					tokenPos += 5;
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
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == 'B'
							&& input[tokenPos + 1] == 'o'
							&& input[tokenPos + 2] == 'o'
							&& input[tokenPos + 3] == 'l'
							&& input[tokenPos + 4] == 'e'
							&& input[tokenPos + 5] == 'a'
							&& input[tokenPos + 6] == 'n') {
					token = Symbol.BOOLEAN;
					tokenPos += 7;
				} 
				else if (tokenPos + 2 < input.length
							&& input[tokenPos    ] == 'S'
							&& input[tokenPos + 1] == 't'
							&& input[tokenPos + 2] == 'r'
							&& input[tokenPos + 3] == 'i'
							&& input[tokenPos + 4] == 'n'
							&& input[tokenPos + 5] == 'g') {
					token = Symbol.STRING;
					tokenPos += 6;
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
				
				tokenPos++;
				
				while ( tokenPos < input.length && Character.isDigit(ch = input[tokenPos]) ) {
					this.localValue = this.localValue * 10 + (ch - '0');
					tokenPos++;
				}
				
				token = Symbol.NUMBER;
			}
			// tokens com 1 ou 2 characteres.
			else {
				switch (ch) {
				
					case '"':
						
					String s = "";
					
					tokenPos++; // come o token (") -- abrir string
					
					while ( tokenPos < input.length && input[tokenPos] != '"' ) {
						s += input[tokenPos];
						tokenPos++;
					}
					
					this.string = s;
					
					tokenPos++; // passa o último (")
					
					token = Symbol.QUOTE;
					
					break;
				
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
					
					tokenPos++;
					
					// operador ++
					if (tokenPos < input.length & input[tokenPos] == '+') {
						token = Symbol.CONCAT;
						tokenPos++;
					} else
						token = Symbol.PLUS;
					
					break;
					
				case '-':
					token = Symbol.MINUS;
					tokenPos++;
					break;
					
				case '*':
					
					tokenPos++;
					token = Symbol.MULT;
					
					break;
				
				case '/':
					
					tokenPos++; // come o primeiro '/'
					
					// '/*': comenrário de multiplas linhas
					if (tokenPos < input.length & input[tokenPos] == '*') {
						
						tokenPos++; // come o '*'
						
						// come todos os caracteres até achar um "*/"
						while (tokenPos < input.length + 1
								&& !(input[tokenPos] == '*' && input[tokenPos + 1] == '/') ) {
							
							if (input[tokenPos] == '\n')
								lineNumber++;
							
							tokenPos++;
						}
						
						tokenPos += 2; // posiciona o tokenPos no começo do proximo token
						nextToken();
						
					}
					// '//': comentário de uma única linha
					else if (tokenPos < input.length & input[tokenPos] == '/') {
						
						tokenPos ++; // come o segundo '/'
					
						// come todos os caracteres até achar um '\n'
						while (tokenPos < input.length && input[tokenPos] != '\n') {
							tokenPos++;
						}
					
						lineNumber++;
						nextToken();
					}
					else 
						token = Symbol.DIV;
					
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
