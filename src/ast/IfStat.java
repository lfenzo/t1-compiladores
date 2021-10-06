package ast;

public class IfStat extends Stat {

    private Expr expr;
    private StatList statlist_if; // statements para o caso do IF
    private StatList statlist_else = null; // statements para o caso do ELSE
    
    // construtor para o IF e ELSE
    public IfStat(Expr expr, StatList statlist_if, StatList statlist_else) {
        this.expr = expr;
        this.statlist_if = statlist_if;
        this.statlist_else = statlist_else;
    }

    // construtor apenas para o IF
    public IfStat(Expr expr, StatList statlist_if) {
        this.expr = expr;
        this.statlist_if = statlist_if;
    }
    
    public void setStatlistElse(StatList statlist_else) {
    	this.statlist_else = statlist_else;
    }

    @Override
    public int genC(int ident) {
    	for(int i = 0; i < ident; i++)
        	System.out.print("\t");
        System.out.printf("if (");
        ident = this.expr.genC(ident);
        System.out.printf(") {\n");
        ident++;
        ident = this.statlist_if.genC(ident);
        ident--;
        for(int i = 0; i < ident; i++)
        	System.out.print("\t");
        System.out.println("}");
        if (this.statlist_else != null) {
        	for(int i = 0; i < ident; i++)
            	System.out.print("\t");
        	System.out.printf(" else {\n");
        	ident++;
            ident = this.statlist_else.genC(ident);
            ident--;
            for(int i = 0; i < ident; i++)
            	System.out.print("\t");
            System.out.println("}\n");
        }
        return ident;
    }
    
    public void eval() {
    	int val = this.expr.eval();
    	
    	if(val != 0) {
    		statlist_if.eval();
    	} else {
    		if(statlist_else != null)
    			statlist_else.eval();
    	} 		
    }
}
