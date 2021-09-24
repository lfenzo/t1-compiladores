package ast;

import java.util.*;

public class VarList {

    private ArrayList<Var> variaveis = new ArrayList<Var>();

    public void addVar(Var newvar) {
        if (!varExists(newvar)) {
            this.variaveis.add(newvar);
        }
       
        // TODO erro
    }

    public boolean varExists(Var newvar) {
        for (Var v : this.variaveis) {
        	
        	String new_id = newvar.getId();
        	String var_id = v.getId();
        	        	
        	// se for igual a 0 significa que as atrings são iguais
        	if (var_id.compareTo(new_id) == 0) {
        		return true;
        	}
        }
        return false;
    }
    
    public int getSize() {
    	return this.variaveis.size();
    }
    
    public Var getElement(int position) {
    	return this.variaveis.get(position);
    }

    public void genC() {
        for (Var v : this.variaveis) {
            System.out.printf("int %s;\n", v.getId());
        }
    }
}
