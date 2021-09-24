package ast;

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
    
    public void setStatlistElse(StatList statlist_else) {
    	this.statlist_else = statlist_else;
    }

    @Override
    public void genC() {
        System.out.printf("if (");
        this.expr.genC();
        System.out.printf(") {\n");

        this.statlist_if.genC();
        System.out.println("}");
        if (this.statlist_else != null) {
            System.out.printf(" else {\n");
            this.statlist_else.genC();
            System.out.println("\n");
        }
    }
}
