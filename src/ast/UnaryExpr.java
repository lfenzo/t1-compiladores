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
		
		case PLUS: System.out.print("+"); break;
			
		case MINUS: System.out.print("-"); break;
			
		case NOT: System.out.print("!"); break;
		
		default: break;
		
		}
		
		this.expr.genC(identation);
		
		return identation;
	}
	
    @Override
    public Object eval() {
        
//    	if (oper == Symbol.PLUS)
//            return + expr.eval();
//        
//        else if (oper == Symbol.MINUS)
//            return - expr.eval();
//        
//        else
//        
//        	if (expr.eval() == 0)
//                return 1;
//            
//        	else
//                return 0;
    	return null;
    }

	@Override
	public Type getType() {
		return this.expr.getType();
	}
}