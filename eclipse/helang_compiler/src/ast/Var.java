package ast;

public class Var {

    private String id;
    private int value;

    public Var(String id, int value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public int getValue() {
        return this.value;
    }
}
