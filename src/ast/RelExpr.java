package ast;

public class RelExpr {
    
	private AddExpr expr_esq;
	private AddExpr expr_dir = null;
	private RelOp operador = null;
	private Type tipo;
	
	// construtor para [ AddExpr RelOp Addexpr ] 
	public RelExpr(AddExpr expr1, RelOp operador, AddExpr expr2) {
		this.expr_esq = expr1;
		this.operador = operador;
		this.expr_dir = expr2;
		
		this.tipo = new BooleanType();
	}
	
	// construtor para o caso [ AddExpr ]
	public RelExpr(AddExpr expr) {
		this.expr_esq = expr;
	}

	public void setOperator(String op) {
		this.operador = new RelOp(op);
	}
	
	public int genC(int ident) {
		
		ident = this.expr_esq.genC(ident);
		
		if (this.expr_dir != null) {
			System.out.print(" ");
			ident = this.operador.genC(ident);
			System.out.print(" ");
			ident = this.expr_dir.genC(ident);
		}
		
		return ident;
	}

	public void setDirExpr(AddExpr addExpr) {
		this.expr_dir = addExpr;	
	}
	
	public int eval() {
		int value1 = this.expr_esq.eval();
		int value2;
		
		if(this.expr_dir != null) {
			value2 = this.expr_dir.eval();
			if(this.operador.getOperador().equals("<"))
				return value1 < value2 ? 1 : 0;
			else if(this.operador.getOperador().equals("<="))
				return value1 <= value2 ? 1 : 0;
			else if(this.operador.getOperador().equals("=="))
				return value1 == value2 ? 1 : 0;
			else if(this.operador.getOperador().equals(">="))
				return value1 >= value2 ? 1 : 0;
			else if(this.operador.getOperador().equals(">"))
				return value1 > value2 ? 1 : 0;
			else	
				return value1 != value2 ? 1 : 0;	
		}
		return value1;
	}
}
