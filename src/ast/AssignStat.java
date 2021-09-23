package ast;

public class AssignStat extends Stat {

    private String id;
    private SimpleExpr expr; // 'expr' é uma subclasse de Expr

    public AssignStat(String id, SimpleExpr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public void genC() {
        System.out.printf("\t%s = ", this.id);
        this.expr.genC();
        System.out.printf(";\n");
    }
}
