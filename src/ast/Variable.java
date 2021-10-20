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
	
	public String getId() {
		return this.id;
	}
	
	
//    private String id;
//    private Type type;
////    private Object value; // pode ser uma classe String, Int ou Boolean
//    private int 	value_int;
//    private String  value_string;
//    private boolean value_bool;
//    private boolean isDeclared;
//    
    // orecisanis sumir ocm esse construtor para apenas poder constriur
    // variaveis ja com tips.
//    public Var(String id) {
//        this.id = id;
//        isDeclared = false;
//    }
//    
//    public Var(String id, Type type) {
//        this.id = id;
//        this.type = type;
//        isDeclared = false;
//    }
//
//    public String getId() {
//        return this.id;
//    }
//    
//    public void setValue(String string) {
//    	this.value_string = string;
//    }
//    
//    public void setValue(int valor) {
//    	this.value_int = valor;
//    }
//    
//    public void setValue(boolean bool) {
//    	this.value_bool = bool;
//    }
//    
////    public void setValue(int value) {
////    	this.value = value;
////    }
////    
////    public void setValue(String string) {
////    	this.string_value = string;
////    }
//    
//    public Object getValue() {
//    	
//    	if (this.type == Type.booleanType)
//    		return this.value_bool;
//    
//    	else if (this.type == Type.intType)
//    		return this.value_int;
//    
//    	else
//    		return this.value_string;
//    }
//    
//    public Type getType() {
//    	return this.type;
//    }
//    
//    public void setType(Type type) {
//    	this.type = type;
//    }
//    
//    @Override
//    public int genC(int ident) {
//    	if(!this.isDeclared) {
//    		System.out.printf("%s", this.id);
//    		this.isDeclared = true;
//    	}
//    	else
//    		System.out.printf("%s", this.id);
//    	return ident;
//    }
//    
//    @Override
//    public Object eval() {
//    	
//    	if (this.type == Type.intType) {
//    		return value_int;
//    	}
//    	else if (this.type == Type.stringType) {
//    		return value_string;
//    	}
//    	else if (this.type == Type.booleanType) {
//    		return value_bool;
//    	}
//    	else
//    		return null;
//    }
}
