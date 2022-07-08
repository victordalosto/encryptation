
package test;

import src.Encrypt;

public class Test {

    public static String MSG = "I will save this String, generate a key using authentication, and encrypt and decrypt a MSG.";
    public static String authentication ="12345";
    public static String pathFile = "assets\\File.txt";


    public static void main(String[] args) {

        // ===== Encryption using plain Text ===== //
        System.out.println("Authentication: " + authentication);
        System.out.println("Generated key: " + Encrypt.generateKey(authentication));
        System.out.println("Encrypted MSG: " + Encrypt.encrypt(MSG, authentication));
        System.out.println("Decrypted MSG: " + Encrypt.decrypt(Encrypt.encrypt(MSG, authentication), authentication));
        System.out.println("Decryption using Key : " + Encrypt.decryptUsingKey(Encrypt.encrypt(MSG, authentication), Encrypt.generateKey(authentication)) + "\n");

        // ===== Encryption using whole ASCII files ===== //
        System.out.println("Encrypting text file in assets directory");
        Encrypt.encryptFile(pathFile, authentication); // Generates assets\\File_Encrypted.txt
        Encrypt.decryptFile(pathFile, authentication); // Generates assets\\File_Decrypted.txt

    }
}
