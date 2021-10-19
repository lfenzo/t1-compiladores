package ast;

import java.util.*;

public class AddExpr {
    
	private MultExpr expr_esq;
	private Type type;
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
	
	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		if (this.type == null)
			return this.expr_esq.getType();
		else
			return this.type;
	}
	
	public int genC(int ident) {
		ident = this.expr_esq.genC(ident);
		
		if (this.expr_dir != null) {
			for(int i = 0; i < this.operators.size(); i++) {
				System.out.print(" ");
				ident = this.operators.get(i).genC(ident);
				System.out.print(" ");
				ident = this.expr_dir.get(i).genC(ident);
			}
		}
		return ident;
	}

	public void setDirExpr(MultExpr multExpr) {
		if(this.expr_dir == null)
			this.expr_dir = new ArrayList<>();
		this.expr_dir.add(multExpr);
	}
	
	public Object eval() {
		
		Object value1 = this.expr_esq.eval();
		Object value2;
		Object finalValue = value1;
		
//		if (this.expr_dir != null) {
//		
//			for (int i = 0; i < this.operators.size(); i++) {
//			
//				value2 = this.expr_dir.get(i).eval();
//			
//				if(this.operators.get(i).getOperador() == '+')
//					finalValue += value2;
//				else
//					finalValue -= value2;
//			}
//		}
		
		return finalValue;
	}
}

