package main;

import java.io.IOException;

public class Main {
	public static void main(String []args) throws IOException  {
		
		// na configuração que está roda o arquivo "teste.he"
		CompilerCliParser cli_parser = new CompilerCliParser(args);
		Compiler comp = new Compiler();

		char []input = cli_parser.getInput();
		
		char opt = cli_parser.getOption();
		
//		System.out.println(funcao(0));
		
		comp.compile(input, opt);		
	}

	public static Object funcao(int coisa) {
		
		Object value = 3;
		
//		value = String.valueOf("coisa");
//		value = Integer.valueOf(10);
		
//		return value;
		
		if (coisa > 0)
			return value;
		else
			return "coisa";
	}

}

