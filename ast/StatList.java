import java.util.*;

public class StatList {

    private ArrayList<Stat> statements; // lista de statements com aubclasses de Stat

    public void addStat(Stat newstat) {
        this.statements.add(newstat);
    }

    public void genC() {
        for (Stat s : this.statements) {
            System.out.println("\t{");
            s.genC(); // cada um dos statements deve gerar a sua propria saida;
            System.out.println("\t}");
        }
    }
}
