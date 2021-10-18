package ast;

public class StringType extends Type {
	
	public StringType() {
		super("string");
	}

	public void genC() {
		// linguagem C não tem strings como java....
		System.out.println("char *");
	}

	@Override
	public String getCname() {
		// TODO Auto-generated method stub
		return null;
	}
}