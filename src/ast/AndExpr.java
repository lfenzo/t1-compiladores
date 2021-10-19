package ast;

public class AndExpr {
    
	private RelExpr expr_esq;
	private String and_op;
	private Type type;
	private RelExpr expr_dir = null;
	
	public AndExpr(RelExpr expr1, RelExpr expr2) {
		this.expr_esq = expr1;
		this.expr_dir = expr2;
	}
	
	public AndExpr(RelExpr expr) {
		this.and_op = "&&";
		this.expr_esq = expr;
	}
	
	public Type getType() {
		if (this.type == null)
			return this.expr_esq.getType();
		else
			return this.type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
		
	public void setOperator(String and_op) {
		this.and_op = and_op;
	}
	
	public void setExprDir(RelExpr expr_dir) {
		this.expr_dir = expr_dir;
	}
	
	public int genC(int ident) {
		ident = this.expr_esq.genC(ident);
		
		if (this.expr_dir != null) {
			System.out.print(" ");
			System.out.printf("&&");
			System.out.print(" ");
			ident = this.expr_dir.genC(ident);
		}
		
		return ident;
	}
	
	public Object eval() {
		
		Object value1 = this.expr_esq.eval();
		Object value2;
		
//		if(this.expr_dir != null) {
//			
//			value2 = this.expr_dir.eval();
//			
//			if(value1 != 0 && value2 != 0)
//				return 1;
//			return 0;	
//		}
		
		return value1;
	}
}
