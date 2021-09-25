package ast;

import java.util.*;

public class StatList {

    private ArrayList<Stat> statements = new ArrayList<Stat>(); // lista de statements com aubclasses de Stat

    public void addStat(Stat newstat) {
        this.statements.add(newstat);
    }

    public int genC(int ident) {
        for (Stat s : this.statements) {
            ident = s.genC(ident); // cada um dos statements deve gerar a sua propria saida;
        }
        return ident;
    }
    
    public void eval() {
    	for (Stat s : this.statements) {
    		s.eval();
    	}
    }
}
