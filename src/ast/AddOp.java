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

    public int genC(int ident) {
        System.out.printf("%c", this.operador);
        return ident;
    }
} 
