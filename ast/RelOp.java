public class RelOp {

    private char operador;

    public RelOp (int operador) {
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

    public int getOperador() {
        return this.operador;
    }

    @Override
    public void genC() {
        System.out.println(this.operador);
    }
} 
