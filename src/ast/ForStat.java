package ast;

public class ForStat extends Stat {
    
    private Var iter; // variável para o iterador do 'for'
    private Expr begin; // expressão que resulta no valor de inicio do for
    private Expr end; // expressão que resulta no valor final do 'for' 
    private StatList statlist; // lista de statements que devem ir dentro do for.

    public ForStat(Var iter, Expr begin_expr, Expr end_expr, StatList statList) {
        this.iter = iter;
        this.begin = begin_expr;
        this.end = begin_expr;
        this.statlist = statList;
    }

    @Override
    public void genC() {
        // TODO Decidir se ter as funções retornando strings 
        // ao inves de printando não seria melhor. Isso permite que os assignments montassem
        // as proprios prints de forma mais facil
        System.out.printf("\tfor(int %s; %s < )", this.iter, this.iter);
        this.statlist.genC();
    }
}
