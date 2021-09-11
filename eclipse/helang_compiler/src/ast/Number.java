package ast;

public class Number extends SimpleExpr {

    private int value;

    public Number (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public void genC() {
        System.out.println(this.value);
    }
} 