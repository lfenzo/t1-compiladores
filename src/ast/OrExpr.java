package ast;

public class OrExpr extends SimpleExpr{
    
	private AndExpr expr_esq;
	private AndExpr expr_dir = null;
	private String or_op;
	private Type type; // deve ser BooleanType
	
	public OrExpr(AndExpr expr1, AndExpr expr2) {
		this.expr_esq = expr1;
		this.expr_dir = expr2;
	}
	
	public OrExpr(AndExpr expr) {
		this.expr_esq = expr;
	}
	
	public void setExprDir(AndExpr expr_dir) {
		this.or_op = "||";
		this.expr_dir = expr_dir;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		if (this.type == null)
			return this.expr_esq.getType();
		else
			return this.type;
	}
	
	public int genC(int ident) {
		ident = this.expr_esq.genC(ident);
		
		if (this.expr_dir != null) {
			System.out.printf("||");
			ident = this.expr_dir.genC(ident);
		}
		return ident;
	}
	
	public Object eval() {
		
		Object value1 = this.expr_esq.eval();
		Object value2 = 0;
		
		if (this.expr_dir != null) {
			
			value2 = this.expr_dir.eval();
		
			if (value1 != null || value2 != null)
				return 1;
			
			return 0;
		}
		
		return value1;
	}
}
