package ast;

public class Var extends SimpleExpr {

    private String id;
    private Type type;
    private int value;
    private String string_value;
    private boolean isDeclared;
    
    public Var(String id) {
        this.id = id;
        isDeclared = false;
    }
    
    public Var(String id, Type type) {
        this.id = id;
        this.type = type;
        isDeclared = false;
    }

    public String getId() {
        return this.id;
    }
    
    public void setValue(int value) {
    	this.value = value;
    }
    
    public void setValue(String string) {
    	this.string_value = string;
    }
    
    public int getValue() {
    	return this.value;
    }
    
    public Type getType() {
    	return this.type;
    }
    
    public void setType(Type t) {
    	this.type = t;
    }
    
    @Override
    public int genC(int ident) {
    	if(!this.isDeclared) {
    		System.out.printf("%s", this.id);
    		this.isDeclared = true;
    	}
    	else
    		System.out.printf("%s", this.id);
    	return ident;
    }
    
    @Override
    public int eval() {
    	return this.getValue();
    }
}
