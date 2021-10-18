package ast;

import java.util.*;

public class VarList {

    private ArrayList<Var> variaveis = new ArrayList<Var>();

    public void addVar(Var newvar) {
        if (varExists(newvar.getId()) == null) {
            this.variaveis.add(newvar);
        }
        // TODO erro
    }
    
    public void removeVar(String ident) {
    	for(int i = 0; i < this.variaveis.size(); i++) {
    		if(this.variaveis.get(i).getId().equals(ident))
    			this.variaveis.remove(i);
    	}
    }
    
    public Var varExists(String ident) {
        for (Var v : this.variaveis) {

        	// se for igual a 0 significa que as atrings s�o iguais
        	if (ident.compareTo(v.getId()) == 0) {
        		return v;
        	}
        }
        return null;
    }
    
    public int getSize() {
    	return this.variaveis.size();
    }
    
    public Var getElement(int position) {
    	return this.variaveis.get(position);
    }

    public int genC(int ident) {
    	
        for (Var v : this.variaveis) {
            
        	for(int i = 0; i < ident; i++)
            	System.out.print("\t");
        
        	if (v.getType() == Type.stringType)
        		System.out.printf("char *%s;\n", v.getId());
        	else
        		System.out.printf("int %s;\n", v.getId());
        }
        
        return ident;
    }
}
