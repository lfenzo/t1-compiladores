package ast;

abstract public class SimpleExpr {
    abstract int genC(int ident);
    abstract int eval();
}