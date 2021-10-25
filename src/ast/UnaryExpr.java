package ast;

import lexer.Symbol;

public class UnaryExpr extends Expr {
	
    private Expr expr;
    private Symbol oper;

    public UnaryExpr(Expr e, Symbol oper) {
        this.expr = e;
        this.oper = oper;
    }

	@Override
	public int genC(int identation) {
		
		switch (this.oper) {
		
		case PLUS:
			System.out.print("+");
			this.expr.genC(identation);
			break;
			
		case MINUS:
			System.out.print("-(");
			this.expr.genC(identation);
			System.out.print(")");
			break;
			
		case NOT:
			System.out.print("!");
			this.expr.genC(identation);
			break;
		
		default: break;
		
		}
		
		return identation;
	}
	
    @Override
    public Object eval() {
    	
    	Type type = this.getType();
    	
    	if (type == Type.intType) {
    		
    		int value = (int) this.expr.eval();
    		
    		if (this.oper == Symbol.PLUS)
    			return value;
    	
    		else if (this.oper == Symbol.MINUS)
    			return -value;
    		
    	}
    	else if (type == Type.booleanType) {
    		
    		boolean value = (boolean) this.expr.eval();
    		
    		if (this.oper == Symbol.NOT)
    			return !value;
    		
    		else
    			return value;
    	}
    	// não há expressões unarias com strings...
 
    	return null;
    }

	@Override
	public Type getType() {
		return this.expr.getType();
	}
}