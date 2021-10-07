package main;

import java.io.IOException;

public class Main {
	public static void main(String []args) throws IOException  {
		
		// na configuração que está roda o arquivo "teste.he"
		CompilerCliParser cli_parser = new CompilerCliParser(args);
		Compiler comp = new Compiler();
		
		char []input = cli_parser.getInput();
		
		char opt = cli_parser.getOption();
		
		comp.compile(input, opt);
		
	}
}
