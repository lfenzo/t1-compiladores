package ast;

public class BooleanType extends Type {
	
	public BooleanType() {
		super("boolean");
	}

	public void genC() {
		System.out.println("bool");
	}
	
	@Override
	public String getCname() {
		return "int"; // apesar de ser boolean em C vai ser int...
	}
}