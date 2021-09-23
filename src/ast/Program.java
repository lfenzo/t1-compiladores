package ast;

public class Program {

    private VarList varlist;
    private StatList statements;

    public Program(VarList varlist, StatList statements) {
        this.varlist = varlist;
        this.statements = statements;
    }

    public void genC() {
        System.out.println("#include <stdio.h>");
        System.out.println();
        System.out.println("void main(int argc, void *argv) {");

        this.varlist.genC();
        this.statements.genC();

        System.out.println("}");
    }

    public int run() {
        return 0;
    }
}
