package ast;

public class AndExpr {
    
	private RelExpr expr_esq;
	private RelExpr expr_dir = null;
	
	public AndExpr(RelExpr expr1, RelExpr expr2) {
		this.expr_esq = expr1;
		this.expr_dir = expr2;
	}
	
	public AndExpr(RelExpr expr) {
		this.expr_esq = expr;
	}

	public void genC() {
		this.expr_esq.genC();
		
		if (this.expr_dir != null) {
			System.out.printf("&&");
			this.expr_dir.genC();
		}
	}
}
