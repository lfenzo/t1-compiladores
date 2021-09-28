package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	public static void main(String []args) throws IOException {
		
		// carregando o arquivo a ser compilado para uma String
		Path fileName = Path.of(System.getProperty("user.dir"), "src/main/teste_original.he");		
		String str = Files.readString(fileName);
		char []input = str.toCharArray();

		
		Compiler comp = new Compiler();
		comp.compile(input);
		System.out.println("Compilado com sucesso!");
	}
}
