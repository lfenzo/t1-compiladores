package ast;

public class StringExpr extends SimpleExpr {

	private String content;
	
	public StringExpr(String string) {
		this.content = string;
	}
	
	@Override
	public int genC(int ident) {
		System.out.printf("\"%s\"", this.content);
        return ident;
	}

	@Override
	public Object eval() {
		return String.valueOf(this.content); // na pratica retorna uma string
	}
	
	public String getValue() {
		return this.content;
	}
	
	public Type getType() {
		return Type.stringType;
	}
}