
package test;

public class Test {

    public static String MSG = "I will save this String, generate a key using authentication, and encrypt and decrypt a MSG.";
    public static String authentication ="12345";
    public static String pathFile = "assets\\File.txt";


    // public static void main(String[] args) {

    //     // ===== Encryption using plain Text ===== //
    //     System.out.println("Authentication: " + authentication);
    //     System.out.println("Generated key: " + VCrypt.generateKey(authentication));
    //     System.out.println("Encrypted MSG: " + VCrypt.encryptMSG(MSG, authentication));
    //     System.out.println("Decrypted MSG: " + VCrypt.decryptMSG(VCrypt.encryptMSG(MSG, authentication), authentication));
    //     System.out.println("Decryption using Key : " + VCrypt.decryptMSGUsingKey(VCrypt.encryptMSG(MSG, authentication), VCrypt.generateKey(authentication)) + "\n");

    //     // ===== Encryption using whole ASCII files ===== //
    //     System.out.println("Encrypting text file in assets directory");
    //     VCrypt.encryptFile(pathFile, authentication); // Generates assets\\File_Encrypted.txt
    //     VCrypt.decryptFile(pathFile, authentication); // Generates assets\\File_Decrypted.txt

    // }
}
