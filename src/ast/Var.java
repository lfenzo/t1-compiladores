package ast;

public class Var extends SimpleExpr {

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
    
    @Override
    public void genC() {
        System.out.printf("\tint %s;\n", this.id);
    }
}
