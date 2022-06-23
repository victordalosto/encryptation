
public class Teste {

    public static void main(String[] args) {

        String MSG = "I ill save this entire String into a object and i want it to be indecipherable.";
        String password = "12345";

        System.out.println();
        System.out.println("String input MSG: " + MSG);
        System.out.println("Password: " + password + "\n");
        System.out.println("Generated key: " + Encrypt.generateKey(password));
        System.out.println("Encrypted MSG: " + Encrypt.encrypt(MSG, password) + "\n");
        System.out.println("Decrypted MSG: " + Encrypt.decrypt(Encrypt.encrypt(MSG, password), password) + "\n");

    }
    
}
