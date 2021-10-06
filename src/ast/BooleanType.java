package ast;

public class BooleanType extends Type {
	
	public BooleanType(String name) {
		super(name);
	}

	public void genC() {
		System.out.println("bool");
	}
}