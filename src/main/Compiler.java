package main;

import ast.*;

public class Compiler {
	
	private char token;
	private int token_pos;
	private int line_number;
	private char []input;
	
	public Compiler() {
		System.out.println("Voce instanciou um compilador");
	}
	
	public void compile(char []input) {
        this.input = input;
        this.input[input.length - 1] = '\0';

        nextToken();
    }
	
	public void nextToken() {
		
		char ch;
		
		while ((ch = this.input[token_pos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
	        
			if (ch == '\n') this.line_number++;
	        
	        this.token_pos++;
        }
	}
}
