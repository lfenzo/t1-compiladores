package ast;

import java.util.*;

public class AddExpr {
    
	private MultExpr expr_esq;
	private ArrayList<AddOp> operators = new ArrayList<>(); // lista de statements com aubclasses de Stat
	private ArrayList<MultExpr> expr_dir = null;
	
	public AddExpr(MultExpr expr1, AddOp operador, MultExpr expr2) {
		this.expr_esq = expr1;
		this.operators.add(operador);
		if(this.expr_dir == null)
			this.expr_dir.add(expr2);
	}
	
	public AddExpr(MultExpr expr) {
		this.expr_esq = expr;
	}
	
	public void setOperator(char op) {
		
		AddOp new_op = new AddOp(op);
		
		this.operators.add(new_op);
	}

	public void genC() {
		this.expr_esq.genC();
		
		if (this.expr_dir != null) {
			for(int i = 0; i < this.operators.size(); i++) {
				this.operators.get(i).genC();
				this.expr_dir.get(i).genC();
			}
		}
	}

	public void setDirExpr(MultExpr multExpr) {
		if(this.expr_dir == null)
			this.expr_dir = new ArrayList<>();
		this.expr_dir.add(multExpr);
	}
}

