package ast;

public class Variable {

	private String id; // id é o nome da variavel
	private Type type;
	private Object value;
	
	// declaração da variaval com tipo
	public Variable(String name, Type type) {
		this.id = name;
		this.type = type;
	}
	
	// declaracao da variaval sem tipo (provavelmente vai ser retirada depois)
	public Variable(String name) {
		this.id = name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return this.value;
	}
}
