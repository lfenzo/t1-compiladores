package ast;

public class AndExpr {
    
	private RelExpr expr_esq;
	private String and_op;
	private RelExpr expr_dir = null;
	
	public AndExpr(RelExpr expr1, RelExpr expr2) {
		this.expr_esq = expr1;
		this.expr_dir = expr2;
	}
	
	public AndExpr(RelExpr expr) {
		this.and_op = "&&";
		this.expr_esq = expr;
	}
		
	public void setOperator(String and_op) {
		this.and_op = and_op;
	}
	public void setExprDir(RelExpr expr_dir) {
		this.expr_dir = expr_dir;
	}
	
	public void genC() {
		this.expr_esq.genC();
		
		if (this.expr_dir != null) {
			System.out.print(" ");
			System.out.printf("&&");
			System.out.print(" ");
			this.expr_dir.genC();
		}
	}
}
