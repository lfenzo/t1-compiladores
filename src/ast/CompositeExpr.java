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
			
			int left_value  = (int) this.left.eval(); // convertendo de Object para int
			int right_value = (int) this.right.eval();
			
			if      (oper == Symbol.PLUS)  return (left_value + right_value);
			else if (oper == Symbol.MINUS) return (left_value - right_value);
			else if (oper == Symbol.MULT)  return (left_value * right_value);
			else if (oper == Symbol.DIV)   return (left_value / right_value);
			else if (oper == Symbol.PERC)  return (left_value % right_value);
			
		}
		else if (type == Type.booleanType) {
			
			Object left_value  = this.left.eval();
			Object right_value = this.right.eval();
			
			if ((left_value.getClass() == Integer.class) && (right_value.getClass() == Integer.class)) {
				
				int left  = (int) left_value;
				int right = (int) right_value;
				
				if      (oper == Symbol.EQ)  return (left == right);
				else if (oper == Symbol.NEQ) return (left != right);
				else if (oper == Symbol.LT)  return (left <  right);
				else if (oper == Symbol.LE)  return (left <= right);
				else if (oper == Symbol.GT)  return (left >  right);
				else if (oper == Symbol.GE)  return (left >= right);
			}
			else if ((left_value.getClass() == Boolean.class) && (right_value.getClass() == Boolean.class)) {
				
				boolean left  = (boolean) left_value;
				boolean right = (boolean) right_value;
				
				if      (oper == Symbol.AND) return (left && right);
				else if (oper == Symbol.OR)  return (left || right);
			}
			else if ((left_value.getClass() == String.class) && (right_value.getClass() == String.class)) {
				
				String left  = (String) left_value;
				String right = (String) right_value;	
				
				if 		(oper == Symbol.CONCAT) return left + right;
				else if (oper == Symbol.EQ)		return left.equals(right);
				else if (oper == Symbol.NEQ)	return !left.equals(right);
				
			}
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	
}
