package ast;

public class RelExpr {
    
	private AddExpr expr_esq;
	private AddExpr expr_dir = null;
	private RelOp operador = null;
	
	// construtor para [ AddExpr RelOp Addexpr ] 
	public RelExpr(AddExpr expr1, RelOp operador, AddExpr expr2) {
		this.expr_esq = expr1;
		this.operador = operador;
		this.expr_dir = expr2;
	}
	
	// construtor para o caso [ AddExpr ]
	public RelExpr(AddExpr expr) {
		this.expr_esq = expr;
	}

	public void genC() {
		this.expr_esq.genC();
		
		if (this.expr_dir != null) {
			this.operador.genC();
			this.expr_dir.genC();
		}
	}
}
