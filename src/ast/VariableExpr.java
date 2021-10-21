package ast;

public class VariableExpr extends Expr {

	private Variable v;
	
	public VariableExpr(Variable var) {
		this.v = var;
	}

	@Override
	public int genC(int identation) {
		System.out.print(v.getId());
		return identation;
	}

	@Override
	public Type getType() {
		return v.getType();
	}

	@Override
	public Object eval() {
		return this.v.getValue(); // "eval" na variavel significa obter o valor dela.
	}	
}
