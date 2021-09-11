package ast;

public class Program {

    private VarList varlist;
    private Stat statements;

    public Program(VarList varlist, Stat statements) {
        this.varlist = varlist;
        this.statements = statements;
    }

    public void genC() {
        System.out.println("#include <stdio.h>");
        System.out.println("void main(int argc, void *argv) {");

        this.varlist.genC();
        this.statements.genC();

        System.out.println("}");
    }

    // TODO 
    public int run() {
        return 0;
    }
}
