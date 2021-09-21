package lexer;

public enum Symbol {
	 
	EOF("eof"),
    
    
    // tokens com 1 charactare
    PLUS			("+"),
    MINUS			("-"),
    MULT			("*"),
    DIV				("/"),
    PERC			("%"),
    LT				("<"),
    GT				(">"),
    ASSIGN			("="),
    OPEN_PAR		("("),
    CLOSE_PAR		(")"),
    SEMICOLON		(";"),
    COMMA			(","),
    OPEN_CBRACES	("{"),
    CLOSE_CBRACES	("}"),
    NOT				("!"),

    // tokens com 2 caracteres
    LE				("<="),
    GE				(">="),
    NEQ				("!="),
    EQ				("=="),
    IN				("in"),
    TWO_DOTS		(".."),
    
    // tokens de keywords e outros
    IF 				("if"),
    INT				("Int"),
    ELSE			("else"),
    VAR				("var"),
    FOR				("for"),
    WHILE			("while"),
    PRINT			("print"),
    PRINT_LINE		("println"),
    ID				("Identifier"),
    NUMBER			("Number"),

    WRITE			("write");

	Symbol(String name) {
        this.name = name;
    }
     
    @Override
    public String toString() {
    	return name;
    }
    
    public String name;
}
