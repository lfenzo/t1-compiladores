package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CompilerCliParser {
	
	private char opcao;
	private String source_file;

	public CompilerCliParser(String []cli_args) {
		
		if (cli_args.length != 2) {
			throw new RuntimeException("Esperados exatamente 2 argumentos: " + cli_args.length + " passados.");
		}
		 
		// opções do compilador
		switch (cli_args[0]) {
		
		case "-run":
			this.opcao = 'r';
			break;
	 
		case "-gen":
			this.opcao = 'g';
			break;
			
		default:
			throw new RuntimeException("Opção inválida: '" + cli_args[0] + "' passada.");
		}
		
		// codigo fonte a ser compilado/interpretado
		this.source_file = cli_args[1];	 
	}

	public char getOption() {
		return this.opcao;
	}

	public char[] getInput() throws IOException {

		// carregando o arquivo a ser compilado para uma String
		Path fileName = Path.of( this.source_file );		
		String str = Files.readString(fileName);
		
		return str.toCharArray();
	}	
}
