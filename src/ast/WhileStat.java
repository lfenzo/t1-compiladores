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

    	for (int i = 0; i < ident; i++)
        	System.out.print("\t");
        
    	System.out.printf("while ( ");
        ident = this.expr.genC(ident);
        System.out.printf(") {\n");
       
        ident++;
        ident = this.statlist.genC(ident);
        ident--;
        
        for (int i = 0; i < ident; i++)
        	System.out.print("\t");
        
        System.out.println("}");
        
        return ident;
    }
    
    public Object eval() {
    	
    	boolean val = (boolean) this.expr.eval(); // condição do while
    	
    	while (val == true) {
    		this.statlist.eval();
    		val = (boolean) this.expr.eval();
    	}
    	
    	return null;
    }
}
