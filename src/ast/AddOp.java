package ast;

public class AddOp {

    private char operador;

    public AddOp (char operador) {
        switch (operador) {
            case '+': this.operador = operador; break;
            case '-': this.operador = operador; break;
            default: throw new RuntimeException();
        }
    }

    public char getOperador() {
        return this.operador;
    }

    public void genC() {
        System.out.printf("%c", this.operador);
    }
} 
