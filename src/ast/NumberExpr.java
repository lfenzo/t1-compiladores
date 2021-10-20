package ast;

public class NumberExpr extends Expr {

    private int value;

    public NumberExpr(int value) {
        this.value = value;
    }
    
    @Override
    public Type getType() {
    	return Type.intType;
    }
    
    @Override
    public int genC(int identation) {
        System.out.printf("%d", this.value);
        return identation;
    }
    
    @Override
    public Object eval() {
    	return Integer.valueOf(value);
    }

    public int getValue() {
        return this.value;
    }
} 