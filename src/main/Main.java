package main;

public class Main {
	public static void main(String []args) {
		
		char []input = "var Int s; var Int i; s = 0; for coisa in((3 + 9) - (10 - (10 - 9)))..100 { s = s + j; } println s; println abscdf;".toCharArray();
		
		Compiler comp = new Compiler();

		comp.compile(input);
		
		System.out.println("Compilado com sucesso!");
	}
}
