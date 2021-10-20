package ast;
//
public class AssignStat extends Stat {

    private Variable v;
    private Expr expr;

    public AssignStat(Variable v, Expr expr) {
        this.v = v;
        this.expr = expr;
        
        //this.eval(); // acho que tudo bem deixar o assignment fazer duas vezes
    }

    @Override
    public int genC(int ident) {
        
    	for (int i = 0; i < ident; i++)
        	System.out.print("\t");
    	
    	System.out.printf("%s = ", this.v.getId());
    	ident = this.expr.genC(ident);
        System.out.printf(";\n");
        
        return ident;
    }
    
    @Override
    public Object eval() {
    	
    	this.v.setValue(this.expr.eval());
    	return null;
    }
}
