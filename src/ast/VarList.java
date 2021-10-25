package ast;

import java.util.*;

public class VarList {

    private ArrayList<Variable> variaveis = new ArrayList<Variable>();

    public void addVar(Variable newvar) {
    	
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
    
    public Variable varExists(String ident) {
    	
        for (Variable v : this.variaveis)
        	if (ident.compareTo(v.getId()) == 0)
        		return v;

        return null;
    }
    
    public int getSize() {
    	return this.variaveis.size();
    }
    
    public Variable getElement(int position) {
    	return this.variaveis.get(position);
    }

    public int genC(int ident) {
    	
        for (Variable v : this.variaveis) {
            
        	for (int i = 0; i < ident; i++)
            	System.out.print("\t");
        
        	if (v.getType() == Type.stringType)
        		System.out.printf("char *%s;\n", v.getId());
        	
        	else if (v.getType() == Type.booleanType)
        		System.out.printf("bool %s;\n", v.getId());
        	
        	else if (v.getType() == Type.intType)
        		System.out.printf("int %s;\n", v.getId());
        }
        
        return ident;
    }
}
