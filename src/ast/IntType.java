package ast;

public class IntType extends Type {
	
	public IntType() {
		super("integer");
	}

	public void genC() {
		System.out.println("int");
	}
	
	@Override
	public String getCname() {
		return "int";
	}
}