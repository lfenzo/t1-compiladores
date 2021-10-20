package ast;

import lexer.Symbol;

public class CompositeExpr extends Expr {

	private Expr left, right;
	private Symbol oper;
	
	public CompositeExpr(Expr left, Symbol oper, Expr right) {
		this.left = left;
		this.right = right;
		this.oper = oper;
	}

	@Override
	public int genC(int identation) {
		
		System.out.print("(");
		this.left.genC(identation);
		
		System.out.print(" " + oper.toString() + " ");
		
		this.right.genC(identation);
		System.out.print(")");

		return identation;
	}

	@Override
	public Type getType() {
		
		if (oper == Symbol.EQ  || oper == Symbol.NEQ ||
			oper == Symbol.LE  || oper == Symbol.LT  ||
			oper == Symbol.GE  || oper == Symbol.GT  ||
			oper == Symbol.AND || oper == Symbol.OR)
		{
			return Type.booleanType;
		}
		else if (oper == Symbol.CONCAT)
			return Type.stringType;
		
		else
			return Type.intType;
	}

	@Override
	public Object eval() {
		
		Type type = this.getType();
		
		if (type == Type.intType) {
			
			Object left_value = this.left.eval();
			Object right_value = this.right.eval();
			
			if (oper == Symbol.PLUS)
				return left_value + right_value; // corrigir essa bagaça...
		
			else if (oper == Symbol.MINUS)
				return left_value - right_value;
			
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	
}
