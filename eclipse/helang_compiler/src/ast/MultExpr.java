package ast;

public class MultExpr {

    private SimpleExpr simple_expr = null;
    private MultOp mult_operador = null;

    public MultExpr(MultOp op, SimpleExpr expr) {
        this.simple_expr = expr;
        this.mult_operador = op;
    }

    public MultExpr(SimpleExpr expr) {
        this.simple_expr = expr;
    }

    public void genC() {
//        if (this.mult_operador == null) {
//            System.out.println(this.mult_operador.getValue());
//            System.out.println(this.simple_expr.getValue());
//        } else {
//            System.out.println();
//        }
    }

//    public Number getValue() {
//
//    }
}
