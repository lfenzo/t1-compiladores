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
    	    	
        System.out.println("#include <stdio.h>");
        System.out.println("#include <stdlib.h>");
        System.out.println("#include <stdbool.h>");
        
        System.out.println("\n#define MAXSIZE 100\n");

        // funções auxiliares para realizar a concatenação de strings.
    	System.out.println("char* int2str(int value)\n"
    			+ "{\n"
    			+ "    char *result = (char*) malloc(sizeof(char) * MAXSIZE);\n"
    			+ "    sprintf(result, \"%d\", value);\n"
    			+ "\n"
    			+ "    return result;\n"
    			+ "}\n"
    			+ "\n"
    			+ "char* bool2str(bool value)\n"
    			+ "{\n"
    			+ "    if (value == true) {\n"
    			+ "        char *ret = \"true\";\n"
    			+ "        return ret;\n"
    			+ "    }\n"
    			+ "    else {\n"
    			+ "        char *ret = \"false\";\n"
    			+ "        return ret;\n"
    			+ "    }\n"
    			+ "}\n"
    			+ "\n"
    			+ "char* plusPlus(char *left, char *right)\n"
    			+ "{\n"
    			+ "    int len_left, len_right;\n"
    			+ "\n"
    			+ "    for (len_left = 0; left[len_left] != '\\0'; len_left++);\n"
    			+ "    for (len_right = 0; right[len_right] != '\\0'; len_right++);\n"
    			+ "\n"
    			+ "    int result_length = len_left + len_right + 1;\n"
    			+ "    char *result = (char*) malloc(sizeof(char) * result_length);\n"
    			+ "\n"
    			+ "    for (int i = 0; i < len_left; i++)\n"
    			+ "        result[i] = left[i];\n"
    			+ "\n"
    			+ "    for (int j = 0; j < len_right; j++)\n"
    			+ "        result[j + len_left] = right[j];\n"
    			+ "\n"
    			+ "    result[result_length] = '\\0';\n"
    			+ "\n"
    			+ "    return result;\n"
    			+ "}");
        
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
