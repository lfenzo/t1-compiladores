package ast;

public class Program {

    private VarList varlist;
    private StatList statements;
    private int ident;
    
    public Program(VarList varlist, StatList statements) {
        this.varlist = varlist;
        this.statements = statements;
        this.ident = 0;
    }

    public void genC() {
        System.out.println("#include <stdbool.h>"); // adicionado para trabalhar com o tipo 'bool'
        System.out.println("#include <stdio.h>");
        System.out.println();
        System.out.println("void main(int argc, void *argv) {");
        
        this.ident++;
        
        this.ident = this.varlist.genC(this.ident);
        this.ident = this.statements.genC(this.ident);

        System.out.println("}");
    }

    public int run() {
        
    	this.statements.eval();
    	
    	return 0;
    }
}
