package ast;

public class PrintStat extends Stat {

    private SimpleExpr expr;
    private boolean is_print_line;

    public PrintStat(SimpleExpr expr, boolean is_print_line) {
        this.expr = expr;
        // especifica se deve ser printado uma linha ou não ao final
        this.is_print_line = is_print_line;
    }

    @Override
    public void genC() {

        System.out.println("printf(\"");
        this.expr.genC();

        if (this.is_print_line) {
            System.out.println("\n\");");
        } else {
            System.out.println("\");"); }
    }

}
