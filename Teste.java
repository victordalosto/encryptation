
public class Teste {

    public static void main(String[] args) {

        String MSG = "I will save this String, generate a key using authentication, and use it to encrypt and decrypt a MSG.";
        String authentication = "12345";

        System.out.println("\n String input: " + MSG + "\n");
        System.out.println(" Authentication: " + authentication);
        System.out.println(" Generated key: " + Encrypt.generateKey(authentication) + "\n");
        System.out.println(" Encrypted MSG: " + Encrypt.encrypt(MSG, authentication));
        System.out.println(" Decrypted MSG: " + Encrypt.decrypt(Encrypt.encrypt(MSG, authentication), authentication) + "\n");
        System.out.println(" Decryption using Key : " + Encrypt.decryptUsingKey(Encrypt.encrypt(MSG, authentication), Encrypt.generateKey(authentication)) + "\n");

    }
}
