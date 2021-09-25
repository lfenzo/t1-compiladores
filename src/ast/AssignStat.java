package ast;

public class AssignStat extends Stat {

    private String id;
    private Expr expr; // 'expr' é uma subclasse de Expr

    public AssignStat(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public int genC(int ident) {
        for(int i = 0; i < ident; i++)
        	System.out.print("\t");
    	System.out.printf("%s = ", this.id);
    	ident = this.expr.genC(ident);
        System.out.printf(";\n");
        
        return ident;
    }
}
