
package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class Encrypt {

    // This value can be customized to generate longer key size
    private final static int keySize = 52;

    private enum Encoding {
        ENCRYPT, DECRYPT, KEY_DECRYPT
    }

    private Encrypt() {};


    public final static String encrypt(String MSG, String authentication) {
        return convert(MSG, authentication, Encoding.ENCRYPT);
    }

    
    public final static String decrypt(String MSG, String authentication) {
        return convert(MSG, authentication, Encoding.DECRYPT);
    }

    
    public final static String decryptUsingKey(String MSG, String key) {
        return convert(MSG, key, Encoding.KEY_DECRYPT);
    }


    public final static void encryptFile(String path, String authentication) {
        String outputPath = path + "_Encrypted";
        HandleFile(path, outputPath, authentication, Encoding.ENCRYPT);
    }
    

    public final static void decryptFile(String path, String authentication) {
        String outputPath = path + "_Decrypted";
        HandleFile(path + "_Encrypted", outputPath, authentication, Encoding.DECRYPT);
    }


    private final static void HandleFile(String inPath, String outPath, String authentication, Encoding encodingType) {
        String line = "";
        StringBuffer sb = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(inPath, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(new FileWriter(outPath, StandardCharsets.UTF_8));) {
            while ((line = reader.readLine()) != null) {
                sb.append(line + System.lineSeparator());
            }
            if (encodingType.equals(Encoding.ENCRYPT))
                line = encrypt(sb.toString(), authentication);
            else {
                sb.setLength(sb.length()-System.lineSeparator().length());
                line = decrypt(sb.toString(), authentication);
            }
            writer.print(line);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    /** Generates a key using the input Authentication (password) */
    public final static String generateKey(String password) {
        char[] enlongedPassword = new char[keySize];
        int len = password.length();
        int aux = 0;
        for (int i = 0; i < keySize; i++) {
            int key1 = password.charAt(i%len) * 2 + 13;
            int key2 = i + i/3 + i%2 + len + keySize + 17;
            aux = aux % keySize;
            aux = incrementalSwitch(aux, key1, i);
            aux = incrementalSwitch(aux, key2, i);
            for (int j = 0; j < len; j++) {
                int key3 = password.charAt((i+j)%len) * 3 + i + len - 47;
                int key4 = password.charAt(Math.abs((len-(i+j)))%len) * 4 + j - 56;
                int key5 = password.charAt((len/2+(i+j)) % len) * 5 - 89 + (i+len*j)%(keySize);
                int key6 = i + j + i*j + j/3 + j%2 - keySize - 111;
                aux = incrementalSwitch(aux, key3, i);
                aux = incrementalSwitch(aux, key4, j);
                aux = incrementalSwitch(aux, key5, i);
                aux = incrementalSwitch(aux, key6, i);
            }
            enlongedPassword[i] = convertToChar(aux);
        }
        return String.valueOf(enlongedPassword);
    }


    /** Method that converts a message using a key (provided the authentication) */
    private synchronized static String convert(String MSG, String authentication, Encoding encodingType) {
        try {Thread.sleep(100);} catch (InterruptedException e) {}
        if (MSG == null || authentication == null)
            throw new ArithmeticException("Message couldn't be converted");
        char[] convertedMSG = MSG.toCharArray();
        char[] keyAt;
        if (!encodingType.equals(Encoding.KEY_DECRYPT))
            keyAt = (generateKey(authentication)).toCharArray();
        else
            keyAt = authentication.toCharArray();
        for (int i=0; i<convertedMSG.length; i++) {
            int character = convertedMSG[i];
            int key = keyAt[i%keyAt.length];
            if (encodingType.equals(Encoding.ENCRYPT))
                convertedMSG[i] = (char) (character + key);
            else
                convertedMSG[i] = (char) (character - key);
        }
        return String.valueOf(convertedMSG);
    }



    /** A simple switch that returns (num1+num2) if bool is even and (num1-num2) if bool is odd. */
    private static int incrementalSwitch(int num1, int num2, int bool) {
        if (bool % 2 == 0)
            return (num1 + num2);
        return (num1 - num2);
    }



    /** Converts an int to a char.
      * @return char in the interval: [0-9,A-Z,a-z]*/
    private static char convertToChar(int value) {
        int range = 62; // 10 Numerical values, and 26 constants *2 from a-z, lower and uppercase()
        value = Math.abs(value)%range;
        if (value < 10) {        // value is converted to a Numeric Value.
            value += 48;
        } else if (value < 36) { // value is converted to a char [A-Z].
            value += (65 - 10);
        } else {                 // value is converted to a char [a-z].
            value += (97 - 36);
        }
        return (char) value;
    }


    
}