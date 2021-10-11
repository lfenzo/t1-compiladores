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
    public int genC(int ident) {
    	for(int i = 0; i < ident; i++)
        	System.out.print("\t");
        System.out.printf("for(int %s = ", this.iter.getId());
        ident = this.begin.genC(ident);
        System.out.printf("; %s <= ", this.iter.getId());
        ident = this.end.genC(ident);
        System.out.printf("; %s++) {\n", this.iter.getId());
        ident++;
        ident = this.statlist.genC(ident);
        ident--;
        for(int i = 0; i < ident; i++)
        	System.out.print("\t");
        System.out.println("}");
        return ident;
    }
    
    public void eval() {
    	int beg = this.begin.eval();
    	int end = this.end.eval();
    	this.iter.setValue(beg);
    	
    	for(this.iter.setValue(beg); 
			this.iter.getValue() <= end; 
    		this.iter.setValue(this.iter.getValue() + 1)) 
    	{
    		this.statlist.eval();
    	}
    }
}
