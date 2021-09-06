public class Main {
    public static void main( String []args ) {
        char []input = "".toCharArray();

        Compilador compiler = new Compilador();

        try {
            compiler.compile(input);
        }
        catch (RuntimeException e) {
            System.out.println("Deu errado");
        }
    }
}
