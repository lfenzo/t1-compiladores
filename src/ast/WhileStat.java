package ast;

public class WhileStat extends Stat {

    private Expr expr;
    private StatList statlist; //  "statements" é uma classe com uma lista de subclasses de Stat

    public WhileStat(Expr expr, StatList statlist) {
        this.expr = expr;
        this.statlist = statlist;
    }

    @Override
    public int genC(int ident) {

    	for(int i = 0; i < ident; i++)
        	System.out.print("\t");
        
    	System.out.printf("while ( ");
        ident = this.expr.genC(ident);
        System.out.printf(") {\n");
        
        ident++;
        ident = this.statlist.genC(ident);
        ident--;
        
        for(int i = 0; i < ident; i++)
        	System.out.print("\t");
        System.out.println("}");
        
        return ident;
    }
    
    public void eval() {
    	int val = this.expr.eval();
    	
    	while(val != 0) {
    		this.statlist.eval();
    		
    		val = this.expr.eval();
    	}
    }
}
