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
    @Override
    public int genC(int ident) {
        System.out.printf("%d", this.value);
        return ident;
    }
} 