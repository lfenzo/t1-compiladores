public class IfStat extends Stat {

    private Expr expr;
    private StatList statlist_if; // statements para o caso do IF
    private StatList statlist_else = null; // statements para o caso do ELSE
    
    // construtor para o IF e ELSE
    public IfStat(Expr expr, StatList statlist_if, StatList statlist_else) {
        this.expr = expr;
        this.statlist_if = statlist_if;
        this.statlist_else = statlist_else;
    }

    // construtor apenas para o IF
    public IfStat(Expr expr, StatList statlist_if) {
        this.expr = expr;
        this.statlist_if = statlist_if;
    }

    @Override
    public void genC() {

        // if expre {
        //  statlist_if
        // } else {
        //  statlist_else
        // }

        System.out.printf("\tif (");
        this.expr.genC();
        System.out.printf(")");

        this.statlist_if.genC();

        if (this.statlist_else != null) {
            System.out.printf(" else ");
            this.statlist_else.genC();
        }
    }
}
