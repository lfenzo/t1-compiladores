package ast;

public class Expr {
    
	private AndExpr expr_esq;
	private AndExpr expr_dir = null;
	
	public Expr(AndExpr expr1, AndExpr expr2) {
		this.expr_esq = expr1;
		this.expr_dir = expr2;
	}
	
	public Expr(AndExpr expr) {
		this.expr_esq = expr;
	}

	public void genC() {
		this.expr_esq.genC();
		
		if (this.expr_dir != null) {
			System.out.printf("||");
			this.expr_dir.genC();
		}
	}
}
