import java.util.*;

public class VarList {

    private ArrayList<Var> variaveis;

    public void addVar(Var newvar) {
        if (!varExists(newvar)) {
            this.variaveis.add(newvar);
        }
        // TODO erro
    }

    public boolean varExists(Var newvar) {
        for (Var v : this.variaveis) {
            if (newvar.getId() == v.getId()) {
                return true;
            }
        }
        return false;
    }

    public void genC() {
        for (Var v : this.variaveis) {
            System.out.printf("\tint %s;\n", v.getId());
        }
    }
}
