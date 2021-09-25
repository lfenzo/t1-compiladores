package ast;

public class RelOp {

    private String operador;

    public RelOp (String operador) {
        switch (operador) {
            case "<": this.operador = operador; break;
            case "<=": this.operador = operador; break;
            case ">": this.operador = operador; break;
            case ">=": this.operador = operador; break;
            case "==": this.operador = operador; break;
            case "!=": this.operador = operador; break;
            default: throw new RuntimeException();
        }
    }

    public String getOperador() {
        return this.operador;
    }

    public int genC(int ident) {
        System.out.print(this.operador);
        return ident;
    }
} 
