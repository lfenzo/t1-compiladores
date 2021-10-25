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
		
		if (this.getType() == Type.stringType) {
			
			System.out.print("plusPlus(");
			
			//
			// conversão de tipos da expressao a esquerda
			//
			if (this.left.getType() == Type.intType) {
				System.out.print("int2str(");
				this.left.genC(identation);
				System.out.print(")");
			}
			else if (this.left.getType() == Type.booleanType) {
				System.out.print("bool2str(");
				this.left.genC(identation);
				System.out.print(")");
			}
			else if (this.left.getType() == Type.stringType) {
				this.left.genC(identation);
			}
			
			System.out.print(", ");
			
			//
			// Conversão de tipos da expressao a direita
			//
			if (this.right.getType() == Type.intType) {
				System.out.print("int2str(");
				this.right.genC(identation);
				System.out.print(")");
			}
			else if (this.right.getType() == Type.booleanType) {
				System.out.print("bool2str(");
				this.right.genC(identation);
				System.out.print(")");
			}
			else if (this.right.getType() == Type.stringType) {
				this.right.genC(identation);
			}
			
			System.out.print(")");
		}
		// tipos bool e integer
		else {
			System.out.print("(");
			this.left.genC(identation);
			
			System.out.print(" " + oper.toString() + " ");
			
			this.right.genC(identation);
			System.out.print(")");
		}

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
			
			// integers
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
			// boolean
			else if ((left_value.getClass() == Boolean.class) && (right_value.getClass() == Boolean.class)) {
				
				boolean left  = (boolean) left_value;
				boolean right = (boolean) right_value;
				
				//
				// >> Gambiarra ***ExTrEmE*** <<
				//
				int bool_left = String.valueOf(left).length();
				int bool_right = String.valueOf(right).length();
				
				if      (oper == Symbol.AND) return (left && right);
				else if (oper == Symbol.OR)  return (left || right);
				else if (oper == Symbol.EQ)  return (left == right);
				else if (oper == Symbol.NEQ) return (left != right);
				
				else if (oper == Symbol.GT)  return !(bool_left >  bool_right);
				else if (oper == Symbol.GE)  return !(bool_left >= bool_right);
				else if (oper == Symbol.LT)  return !(bool_left <  bool_right);
				else if (oper == Symbol.LE)  return !(bool_left <= bool_right);
			}
			// strings
			else if ((left_value.getClass() == String.class) && (right_value.getClass() == String.class)) {
				
				String left  = (String) left_value;
				String right = (String) right_value;	
				
				if 		(oper == Symbol.CONCAT) return left + right; // isso deveria estar aqui?
				else if (oper == Symbol.EQ)		return left.equals(right);
				else if (oper == Symbol.NEQ)	return !left.equals(right);
				
				else if (oper == Symbol.GT)     return (left.compareTo(right) >  0);
				else if (oper == Symbol.GE)     return (left.compareTo(right) >= 0);
				else if (oper == Symbol.LT)     return (left.compareTo(right) <  0);
				else if (oper == Symbol.LE)     return (left.compareTo(right) <= 0);
			}
		}
		// experimental
		else if (type == Type.stringType) {
			
			Object left_value = this.left.eval();
			Object right_value = this.right.eval();
			
			String left = (String) left_value.toString();
			String right = (String) right_value.toString();
			
			if (oper == Symbol.CONCAT)
				return left + right;
		}
		
		return null; // <-- apenas para o eclipse não reclamar...
	}
	
}
