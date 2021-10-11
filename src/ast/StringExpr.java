package ast;

public class StringExpr extends SimpleExpr {

	private String content;
	
	@Override
	public int genC(int ident) {
		System.out.printf("%s", this.content);
        return ident;
	}

	@Override
	public String eval() {
		return this.content;
	}	
}
