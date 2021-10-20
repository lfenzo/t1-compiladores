package ast;

abstract public class Expr  {
	
	abstract public int genC(int identation);
	abstract public Type getType();
	abstract public Object eval();

}
