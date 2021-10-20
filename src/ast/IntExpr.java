package ast;

public class IntExpr extends Expr {

	private int value;

	@Override
	public int genC(int identation) {
		System.out.printf("%d", this.value);
		return identation;
	}

	@Override
	public Type getType() {
		return Type.intType;
	}

	@Override
	public Object eval() {
		return this.value;
	}	
}
