package ast;

public class StringType extends Type {
	
	public StringType(String name) {
		super(name);
	}

	public void genC() {
		// linguagem C não tem strings como java....
		System.out.println("char *");
	}
}