package ast;

import java.util.ArrayList;

public class MultExpr {

    private SimpleExpr simple_expr = null;
    private ArrayList<MultOp> mult_operador = null;
    private ArrayList<SimpleExpr> expr_dir = null;
    private Type type;
    
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
    
    public void setType(Type type) {
    	this.type = type;
    }
    
    public Type getType() {
    	if (this.type == null)
			return this.simple_expr.getType();
		else
			return this.type;
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
    
    public Object eval() {
    	
    	Object value1 = this.simple_expr.eval();
    	Object value2;
    	Object finalValue = value1;
    	
//    	if (this.expr_dir != null) {
//	    	
//    		for (int i = 0; i < this.expr_dir.size(); i++) {
//	    		
//    			value2 = this.expr_dir.get(i).eval();
//	    		
//    			if(this.mult_operador.get(i).getOperador() == '*')
//	    			finalValue *= value2;
//	    		else if (this.mult_operador.get(i).getOperador() == '/')
//	    			finalValue /= value2;
//	    		else
//	    			finalValue %= value2;
//	    	}
//    	}
    	
    	return finalValue;
    }
}
