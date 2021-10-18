package ast;

// colocar "extends Expr" também da erro pq o eval das duas é Int
// faz mais sentido que StringExpr seja subclasse de SimpleExpr do que de Expr
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

	public String eval() {
		return 0;
	}
	
	public String getValue() {
		return this.content;
	}
	
	public Type getType() {
		return Type.stringType;
	}
}