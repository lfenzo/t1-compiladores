package main;

import java.io.IOException;

public class Main {
	public static void main(String []args) throws IOException  {
		
		CompilerCliParser cli_parser = new CompilerCliParser(args);
		Compiler comp = new Compiler();

		char []input = cli_parser.getInput();
		
		char opt = cli_parser.getOption();

		comp.compile(input, opt);
}
	
	public static void teste() {
		
		String a = "abc";
		String b = "z";
		
		// negativo significa que o que está a esquerda tem que vir antes na ordem alfabetica
		System.out.println(a.compareTo(b));
		
	}
}

