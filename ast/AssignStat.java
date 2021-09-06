public class AssignStat {

    private String id;
    private Expr expr;

    public AssignStat(Var v, Expr expr) {
        this.id = v.getId();
        this.expr = expr;
    }

    public void genC() {
        System.out.printf("\t%s = ", this.id, this.expr.genC());
        this.expr.genC();
    }

}
