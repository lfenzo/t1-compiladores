package ast;

public class Var extends SimpleExpr {

    private String id;
    private int value;
    private boolean isDeclared;
    
    public Var(String id) {
        this.id = id;
        isDeclared = false;
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
    	if(!this.isDeclared) {
    		System.out.printf("%s", this.id);
    		this.isDeclared = true;
    	}
    	else
    		System.out.printf("%s", this.id);
    }
}
