package main;

import lexer.Symbol;

public class Main {
	public static void main(String []args) {
		
		char []input = "var Int s;".toCharArray();

		Compiler comp = new Compiler();
		
		Symbol a = Symbol.WHILE;
		Symbol b = Symbol.ID;

		Symbol esperado = a;
		
		if (a == b) {
			System.out.println("funcionou;");
		}
		else 
			System.out.println("'"+ esperado + "' esperado.");
//		comp.compile(input);		
	}
}


