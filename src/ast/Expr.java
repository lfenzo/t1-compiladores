package ast;

public class Expr extends SimpleExpr {
    
	private OrExpr expr_esq;
	private OrExpr expr_dir = null;
	private String or_op = null;
	private Type type;
	
	public Expr(OrExpr expr1, OrExpr expr2) {
		this.expr_esq = expr1;
		this.expr_dir = expr2;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public Expr(OrExpr expr) {
		this.expr_esq = expr;
	}
	
	public void setExprDir(OrExpr expr_dir) {
		this.or_op = "||";
		this.expr_dir = expr_dir;
	}
	
	public int genC(int ident) {
		ident = this.expr_esq.genC(ident);
		
		if (this.expr_dir != null) {
			System.out.printf("||");
			ident = this.expr_dir.genC(ident);
		}
		return ident;
	}
	
	public int eval() {
		
		int value1 = this.expr_esq.eval();
		int value2 = 0;
		
		if(this.expr_dir != null) {
			value2 = this.expr_dir.eval();
			
			if(value1 != 0 || value2 != 0)
				return 1;
			
			return 0;
		}
		
		return value1;
	}
}
