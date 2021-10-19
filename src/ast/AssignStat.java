package ast;
//
public class AssignStat extends Stat {

    private Var v;
    private Expr expr;

    public AssignStat(Var v, Expr expr) {
        this.v = v;
        this.expr = expr;
        
        this.eval();
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
    	
    	Object value = this.expr.eval(); 
    	
    	if (expr.getType() == Type.stringType) {
    		this.v.setValue(String.valueOf(value.toString()));
    	}
    	else if (expr.getType() == Type.intType) {
        	this.v.setValue(Integer.valueOf(value.toString()));    		
    	}
    }
}
