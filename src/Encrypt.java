
package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class Encrypt {

    // This value can be customized to generate longer key size
    private final static int keySize = 16;

    private enum Encoding {
        ENCRYPT, DECRYPT, KEY_DECRYPT
    }

    private Encrypt() {};


    /** Encrypt a MSG using a authentication password */
    public final static String encryptMSG(String MSG, String authentication) {
        return convert(MSG, authentication, Encoding.ENCRYPT);
    }

    
    /** Decrypt a MSG using a authentication password */
    public final static String decryptMSG(String MSG, String authentication) {
        return convert(MSG, authentication, Encoding.DECRYPT);
    }

    
    /** Decrypt a MSG using the key, generated by a authentication password */
    public final static String decryptMSGUsingKey(String MSG, String key) {
        return convert(MSG, key, Encoding.KEY_DECRYPT);
    }


    /** Encrypt a ASCII file using a authentication password */
    public final static void encryptFile(String path, String authentication) {
        String outputPath = path + "_Encrypted";
        HandleFile(path, outputPath, authentication, Encoding.ENCRYPT);
    }
    

    /** Decrypt a ASCII file using a authentication password */
    public final static void decryptFile(String path, String authentication) {
        String outputPath = path + "_Decrypted";
        HandleFile(path + "_Encrypted", outputPath, authentication, Encoding.DECRYPT);
    }

    
    /** Generates a key using a input Authentication password */
    public final static String generateKey(String password) {
        if (password == null || password.length() == 0)
            throw new ArithmeticException("Could not generate a key using the password");
        char[] enlongedPassword = new char[keySize];
        int len = password.length();
        int aux = 0;
        for (int i = 0; i < keySize; i++) {
            aux = aux % 20000;
            int key0 = i - i/3 + i%7 + i%5 + i%3 + i%2;
            int key1 = (len - i + i%4 - keySize%15000);
            int key2 = password.charAt(i%len)*(i%3000);
            aux = incrementalSwitch(aux, key0, i);
            aux = incrementalSwitch(aux, key1, i);
            aux = incrementalSwitch(aux, key2, i);
            for (int j = 0; j < len; j++) {
                int key3 = (i % 11) * 9  * password.charAt(Math.abs(i - j)%len) * (j%400) - j%7 + i%13 + j%18;
                int key4 = (i % 7)  * 11 * password.charAt(Math.abs(len - Math.abs(i-j))%len) * (j%500) - j%5;
                int key5 = (i % 17) * 7  * password.charAt(Math.abs(len / 2 - j)%len) * (j%600) - i%40 + j%50;
                int key6 = (i % 9)  * 13 * password.charAt(Math.abs(len / 2 + j)%len) * (j%700) - i%(keySize);
                int key7 = (i % 13) * 5  * password.charAt(Math.abs(len / 4 - j)%len) * (j%800) + (len-i)%120;
                int key8 = (i % 5)  * 17 * password.charAt(Math.abs(len / 4 + j)%len) * (j%900) - j%12 + i%32;
                int key9 = keySize/3 + i%13 + j%4 + ((i%7) * (j%3)) - 3*password.charAt(j%len)%15 - 32 + j%62;
                aux = incrementalSwitch(aux, key3, j);
                aux = incrementalSwitch(aux, key4, j + 1);
                aux = incrementalSwitch(aux, key5, j);
                aux = incrementalSwitch(aux, key6, j + 1);
                aux = incrementalSwitch(aux, key7, j);
                aux = incrementalSwitch(aux, key8, j);
                aux = incrementalSwitch(aux, key9, j);
            }
            enlongedPassword[i] = convertToChar(aux);
        }
        return String.valueOf(enlongedPassword);
    }




    /** Method that converts a message using a key or authentication */
    private synchronized static String convert(String MSG, String keyOrAuthentication, Encoding encodingType) {
        try {Thread.sleep(200);} catch (InterruptedException e) {}
        if (MSG == null || keyOrAuthentication == null)
            throw new ArithmeticException("Message couldn't be converted");
        char[] convertedMSG = MSG.toCharArray();
        char[] keyAt;
        if (!encodingType.equals(Encoding.KEY_DECRYPT))
            keyAt = (generateKey(keyOrAuthentication)).toCharArray();
        else
            keyAt = keyOrAuthentication.toCharArray();
        int len = keyAt.length;
        for (int i=0; i<convertedMSG.length; i++) {
            int character = convertedMSG[i];
            int key = keyAt[i%len];
            key += keyAt[((len-1)-i%len)]%62 - keyAt[(1*len/2+i)%len]%61;
            key += keyAt[(1*len/8+i)%len]%16 - keyAt[(5*len/8+i)%len]%15;
            key += keyAt[(2*len/8+i)%len]%11 - keyAt[(6*len/8+i)%len]%12;
            key += keyAt[(3*len/8+i)%len]%10 - keyAt[(7*len/8+i)%len]%13;
            key += keyAt[(1*len/3+i)%len]%31 - keyAt[(2*len/3+i)%len]%33;
            if (encodingType.equals(Encoding.ENCRYPT))
                convertedMSG[i] = (char) ((character + key)%65535);
            else
                convertedMSG[i] = (char) ((character - key)%65353);
        }
        return String.valueOf(convertedMSG);
    }


    /** Method that Open the ASCII file and stores its character into a String Buffer */
    private final static void HandleFile(String inPath, String outPath, String authentication, Encoding encodingType) {
        String line = "";
        StringBuffer sb = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(inPath, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(new FileWriter(outPath, StandardCharsets.UTF_8));) {
            while ((line = reader.readLine()) != null) {
                sb.append(line + System.lineSeparator());
            }
            if (encodingType.equals(Encoding.ENCRYPT))
                line = encryptMSG(sb.toString(), authentication);
            else {
                sb.setLength(sb.length()-System.lineSeparator().length());
                line = decryptMSG(sb.toString(), authentication);
            }
            writer.print(line);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }





    /** A simple switch that returns (num1+num2) if bool is even and (num1-num2) if bool is odd. */
    private static int incrementalSwitch(int num1, int num2, int bool) {
        num1 = num1%(50000000 + keySize%1500000);
        num2 = num2%(2500000);
        if (bool % 2 == 0)
            return (num1 + num2) + num1%13 + num1%17;
        return (num1 - num2) + num1%15 + num1%11;
    }



    /** Converts an int to a char.
      * @return char in the interval: [0-9,A-Z,a-z]*/
    private static char convertToChar(int value) {
        int range = 62; // 10 Numerical values, and 26 constants *2 from a-z, lower and uppercase()
        value = Math.abs(value)%range;
        if (value < 10)        // value is converted to a Numeric Value.
            value += 48;
        else if (value < 36)   // value is converted to a char [A-Z].
            value += (65 - 10);
        else                   // value is converted to a char [a-z].
            value += (97 - 36);
        return (char) value;
    }
}