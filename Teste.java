
public class Teste {

    public static void main(String[] args) {

        String MSG = "I ill save this entire string into a object and i want it to be indecipherable";
        String password = "12345";

        System.out.println();
        System.out.println("Input: " + MSG + "\n");
        System.out.println("Encrypted MSG: " + Encrypt.encrypt(MSG, password) + "\n");
        System.out.println("Generated key: " + Encrypt.generatekeyString(password) + "\n");
        System.out.println("Encrypted MSG: " + Encrypt.decrypt(Encrypt.encrypt(MSG, password), password) + "\n");

    }
    
}
