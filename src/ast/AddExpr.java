package ast;

import java.util.*;

public class AddExpr {
    
	private MultExpr expr_esq;
//	private ArrayList<MultExpr> exprs; // lista de statements com aubclasses de Stat
//	private ArrayList<AddOp> operators; // lista de statements com aubclasses de Stat
	private AddOp operador;
	private MultExpr expr_dir;
	
	public AddExpr(MultExpr expr1, AddOp operador, MultExpr expr2) {
		this.expr_esq = expr1;
		this.operador = operador;
		this.expr_dir = expr2;
	}
	
	public AddExpr(MultExpr expr) {
		this.expr_esq = expr;
	}

	public void genC() {
		this.expr_esq.genC();
		
		if (this.expr_dir != null) {
			this.operador.genC();
			this.expr_dir.genC();
		}
	}

	public void setDirExpr(MultExpr multExpr) {
		this.expr_dir = multExpr;
	}
}

