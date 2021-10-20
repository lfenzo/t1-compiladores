package ast;

abstract public class Stat {
    abstract public int genC(int ident);
    abstract public Object eval();
}
