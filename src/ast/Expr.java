package ast;

public class Expr extends SimpleExpr{
    
	private AndExpr expr_esq;
	private AndExpr expr_dir = null;
	private String or_op;
	
	public Expr(AndExpr expr1, AndExpr expr2) {
		this.expr_esq = expr1;
		this.expr_dir = expr2;
	}
	
	public Expr(AndExpr expr) {
		this.expr_esq = expr;
	}
	
	public void setExprDir(AndExpr expr_dir) {
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
}
