package ast;
//
public class AssignStat extends Stat {

    private Var v;
    private Expr expr; // 'expr' é uma subclasse de Expr

    public AssignStat(Var v, Expr expr) {
        this.v = v;
        this.expr = expr;
    }

    @Override
    public int genC(int ident) {
        for(int i = 0; i < ident; i++)
        	System.out.print("\t");
    	System.out.printf("%s = ", this.v.getId());
    	ident = this.expr.genC(ident);
        System.out.printf(";\n");
        
        return ident;
    }
    
    @Override
    public void eval() {
    	this.v.setValue(this.expr.eval());
    }
}
