public class PrintStat extends Stat {

    private Expr expr;

    public PrintStat(Expr expr) {
        this.expr = expr;
    }

    @Override
    public void genC() {
        System.out.println("printf(\"");
        this.expr.genC();
        System.out.println("\");");
    }

}
