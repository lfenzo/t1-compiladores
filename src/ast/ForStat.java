package ast;

public class ForStat extends Stat {
    
    private Var iter; // variável para o iterador do 'for'
    private Expr begin; // expressão que resulta no valor de inicio do for
    private Expr end; // expressão que resulta no valor final do 'for' 
    private StatList statlist; // lista de statements que devem ir dentro do for.

    public ForStat(Var iter, Expr begin_expr, Expr end_expr, StatList statList) {
        this.iter = iter;
        this.begin = begin_expr;
        this.end = end_expr;
        this.statlist = statList;
    }

    @Override
    public void genC() {
        System.out.printf("for(int %s = ", this.iter.getId());
        this.begin.genC();
        System.out.printf("; %s < ", this.iter.getId());
        this.end.genC();
        System.out.printf("; %s++) {\n", this.iter.getId());
        
        this.statlist.genC();
        
        System.out.println("}");
    }
}
