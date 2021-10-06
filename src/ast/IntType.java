package ast;

public class IntType extends Type {
	
	public IntType(String name) {
		super(name);
	}

	public void genC() {
		System.out.println("int");
	}
}