public class Main {
    public static void main( String []args ) {
        String input = "teste do commpilador";

        Compilador compiler = new Compilador();

        try {
            compiler.compile(input);
        }
        catch (RuntimeException e) {
            System.out.println("Deu errado");
        }
    }
}
