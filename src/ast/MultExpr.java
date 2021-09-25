package ast;

import java.util.ArrayList;

public class MultExpr {

    private SimpleExpr simple_expr = null;
    private ArrayList<MultOp> mult_operador = null;
    private ArrayList<SimpleExpr> expr_dir = null;
    
    public MultExpr(SimpleExpr expr_esq, MultOp op, SimpleExpr expr_dir) {
        this.simple_expr = expr_esq;
        if(this.expr_dir == null) {
        	this.expr_dir = new ArrayList<>();
        	this.mult_operador = new ArrayList<>();
        }
        this.mult_operador.add(op);
        this.expr_dir.add(expr_dir);
    }

    public MultExpr(SimpleExpr expr) {
        this.simple_expr = expr;
    }
    
    public void setOperator(char op) {
    	MultOp m = new MultOp(op);
    	
    	if(this.mult_operador == null)
    		this.mult_operador = new ArrayList<>();
    	
    	this.mult_operador.add(m);
    }
    
    public void setExprDir(SimpleExpr expr_dir) {
		if(this.expr_dir == null)
			this.expr_dir = new ArrayList<>();
    	this.expr_dir.add(expr_dir);
	}
    
    public int genC(int ident) {
        
        ident = this.simple_expr.genC(ident);

        if (this.mult_operador != null) {
            for(int i = 0; i < this.expr_dir.size(); i++) {
            	System.out.print(" ");
            	ident = this.mult_operador.get(i).genC(ident);
            	System.out.print(" ");
            	ident = this.expr_dir.get(i).genC(ident);
            }
        }
        return ident;
    }
}
