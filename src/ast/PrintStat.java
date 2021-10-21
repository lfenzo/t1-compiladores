package ast;

public class PrintStat extends Stat {

    private Expr expr;
    private boolean is_print_line;

    public PrintStat(Expr expr, boolean is_print_line) {
        this.expr = expr;
        this.is_print_line = is_print_line;  // especifica se deve ser printado uma linha ou não ao final
    }

    @Override
    public int genC(int ident) {
    	
    	for(int i = 0; i < ident; i++)
        	System.out.print("\t");
    	
    	char percent;
    	
    	if (this.expr.getType() == Type.stringType)
    		percent = 's';
    	else
    		percent = 'd';
    	
        if (this.is_print_line)
        	System.out.print("printf(\"%" + percent + "\\n\", ");
        else
        	System.out.print("printf(\" "+ percent + "\", ");
        
        ident = this.expr.genC(ident);
        System.out.println(");");
        
        return ident;
    }
    
    public Object eval() {
    	
    	if (is_print_line)
    		System.out.println(this.expr.eval());
    	else
    		System.out.print(this.expr.eval());
		
    	return null;
    }
}
