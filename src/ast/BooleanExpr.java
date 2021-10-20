package ast;

public class BooleanExpr extends Expr {

	private boolean value;

	public static BooleanExpr True  = new BooleanExpr(true);
	public static BooleanExpr False = new BooleanExpr(true);
	
	public BooleanExpr(boolean bool) {
		this.value = bool;
	}
	
	@Override
	public int genC(int identation) {
		System.out.printf( this.value ? "1" : "0" );
        return identation;
	}

	@Override
	public Object eval() {
		return this.value;
	}
	
	@Override
	public Type getType() {
		return Type.booleanType;
	}
	
	public boolean getValue() {
		return this.value;
	}
}