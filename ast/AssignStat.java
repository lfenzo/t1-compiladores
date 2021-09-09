public class AssignStat extends Stat {

    private String id;
    private Expr expr; // 'expr' é uma subclasse de Expr

    public AssignStat(Var v, Expr expr) {
        this.id = v.getId();
        this.expr = expr;
    }

    @Override
    public void genC() {
        System.out.printf("\t%s = ", this.id);
        this.expr.genC();
        System.out.printf(";\n");
    }

}
