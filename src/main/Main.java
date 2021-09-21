package main;

public class Main {
	public static void main(String []args) {
		
		char []input = "var Int s; var Int i; s = 0; for j in 1..100 { s = s+j } println s;".toCharArray();
		
		Compiler comp = new Compiler();

		comp.compile(input);
		
		System.out.println("Compilado com sucesso!");
	}
}
