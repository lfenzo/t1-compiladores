package main;

public class Main {
	public static void main(String []args) {
		
		char []input = "var Int s;".toCharArray();

		Compiler comp = new Compiler();
		
		comp.compile(input);		
	}
}


