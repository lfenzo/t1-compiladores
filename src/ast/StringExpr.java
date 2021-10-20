package ast;

public class StringExpr extends Expr {

	private String value;
	
	public StringExpr(String string) {
		this.value = string;
	}
	
	@Override
	public int genC(int ident) {
		System.out.printf("\"%s\"", this.value);
        return ident;
	}

	@Override
	public Object eval() {
		return String.valueOf(this.value); // na pratica retorna uma string
	}
	
	@Override
	public Type getType() {
		return Type.stringType;
	}
	
	public String getValue() {
		return this.value;
	}
}