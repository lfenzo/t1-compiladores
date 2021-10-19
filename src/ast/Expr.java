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
		
		// expressão unaria (pode ser NumberExpr, VariableExpr, StringExpr ...?)
		// desce até chegar ao tipo primitivo da expressão

		if (this.type == null)
			return this.expr_esq.getType();
		else
			return this.type;
//		
//		if (this.expr_dir == null) {
//			return this.expr_esq.getType();
//		}
//		else {
//			Type esq_type = expr_esq.getType();
//			Type dir_type = expr_dir.getType();
//
//			// mudar depois
//			if (esq_type == dir_type) {
//				return dir_type;
//			}
//			else 
//				return null;
//		}
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
	
	public Object eval() {
		
		if (this.expr_esq.getType() == Type.intType) {
			
			Object value1 = this.expr_esq.eval();
			Object value2 = null;
		
			if (this.expr_dir != null) {
				
				value2 = this.expr_dir.eval();
				
				if(value1 != null || value2 != null)
					return Integer.valueOf(1);
				
				return Integer.valueOf(0);
			}
			
			return value1;
		}
		else if (this.expr_esq.getType() == Type.stringType) {
			return String.valueOf(expr_esq.eval());
		}
		
		return null;
	}
		
		
}
