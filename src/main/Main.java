package main;

public class Main {
	public static void main(String []args) {
		
		char []input = "var Int boi;;".toCharArray();

		Compiler comp = new Compiler();
		
		comp.compile(input);
		
		System.out.println("Compilado com sucesso!");
	}
}
