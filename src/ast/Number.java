package ast;

public class Number extends SimpleExpr {

    private int value;

    public Number (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
    	this.value = value;
    }
    
    public Type getType() {
    	return Type.intType;
    }
    
    @Override
    public int genC(int ident) {
        System.out.printf("%d", this.value);
        return ident;
    }
    
    public Object eval() {
    	return Integer.valueOf(value);
    }
} 