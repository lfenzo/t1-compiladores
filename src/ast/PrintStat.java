package ast;

public class PrintStat extends Stat {

    private Expr expr;
    private boolean is_print_line;

    public PrintStat(Expr expr, boolean is_print_line) {
        this.expr = expr;
        // especifica se deve ser printado uma linha ou não ao final
        this.is_print_line = is_print_line;
    }

    @Override
    public int genC(int ident) {
    	for(int i = 0; i < ident; i++)
        	System.out.print("\t");
        if(this.is_print_line)
        	System.out.print("printf(\"%d\\n\", ");
        else
        	System.out.print("printf(\"%d\", ");
        
        ident = this.expr.genC(ident);
        System.out.println(");");
        
        return ident;
    }
    
    public void eval() {
    	if(is_print_line)
    		System.out.println(this.expr.eval());
    	else
    		System.out.print(this.expr.eval());
    }

}
