package ast;

public class Var {

    private String id;
    private int value;
    
    public Var(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    
    public void setValue(int value) {
    	this.value = value;
    }
    
    public int getValue() {
    	return this.value;
    }
    
    public void genC() {
        System.out.printf("\tint %s;\n", this.id);
    }
}
