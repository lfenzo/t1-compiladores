package ast;

public class WhileStat extends Stat {

    private Expr expr; // 'expr' é uma subclasse de Expr
    private StatList statlist; //  "statements" é uma classe com uma lista de subclasses de Stat

    public WhileStat(Expr expr, StatList statlist) {
        this.expr = expr;
        this.statlist = statlist;
    }

    @Override
    public void genC() {

        // while expr {
        //   stat1
        //   stat2
        // }
        System.out.printf("\twhile (");
        this.expr.genC();
        System.out.printf(") ");

        this.statlist.genC();
    }
}
