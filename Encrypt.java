
public final class Encrypt {

    // This value can be customized to generate longer key size
    private final static int keySize = 32;

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


    /** Method that converts a message using a key (provided the authentication) */
    private synchronized static String convert(String MSG, String authentication, Encoding Encoding) {
        try {Thread.sleep(100);} catch (InterruptedException e) {}
        if (MSG == null || authentication == null || MSG.length() == 0 || authentication.length() == 0)
            throw new ArithmeticException("Message couldn't be converted");
        char[] convertedMSG = MSG.toCharArray();
        char[] keyAt;
        if (!Encoding.equals(Encrypt.Encoding.KEY_DECRYPT))
            keyAt = (generateKey(authentication)).toCharArray();
        else
            keyAt = authentication.toCharArray();
        for (int i=0; i<convertedMSG.length; i++) {
            int character = convertedMSG[i];
            int key = keyAt[i%keyAt.length];
            if (Encoding.equals(Encrypt.Encoding.ENCRYPT))
                convertedMSG[i] = (char) (character + key);
            else
                convertedMSG[i] = (char) (character - key);
        }
        return String.valueOf(convertedMSG);
    }



    /** Generates a key using the input Authentication (password) */
    public final static String generateKey(String password) {
        char[] enlongedPassword = new char[keySize];
        int aux = 0;
        int len = password.length();
        for (int i = 0; i < keySize; i++) {
            int key1 = password.charAt(i%len) * 2 + 13;
            int key2 = i + i/3 + i%2 + len + keySize + 17;
            aux = aux % keySize;
            aux = incrementalSwitch(aux, key1, i);
            aux = incrementalSwitch(aux, key2, i);
            for (int j = 0; j < len; j++) {
                int key3 = password.charAt((i+j)%len) * 3 + i + len - 47;
                int key4 = password.charAt(Math.abs((len-(i+j)))%len) * 4 + j - 56;
                int key5 = password.charAt((len/2+(i+j)) % len) * 5 -89 + (i+len*j)%(keySize);
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


    enum Encoding {
        ENCRYPT, DECRYPT, KEY_DECRYPT
    }
}