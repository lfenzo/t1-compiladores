package ast;

public class MultOp {

    private char operador;

    public MultOp (char operador) {
        switch (operador) {
            case '*': this.operador = operador; break;
            case '/': this.operador = operador; break;
            case '%': this.operador = operador; break;
            default: throw new RuntimeException();
        }
    }

    public char getOperador() {
        return this.operador;
    }

    public int genC(int ident) {
        System.out.print(this.operador);
        return ident;
    }
} 
