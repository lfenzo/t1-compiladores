package ast;

abstract public class Type {

	private String name;
	
	public Type(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	abstract public void genC();
}
