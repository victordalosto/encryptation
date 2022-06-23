
public final class Encrypt {

    private final static int passwordSize = 128;

    private Encrypt() {};


    public final static String encrypt(String frase, String password) {
        return convertMessage(frase, password, true);
    }

    public final static String decrypt(String frase, String password) {
        return convertMessage(frase, password, false);
    }


    private synchronized static String convertMessage(String frase, String authentication, boolean toDecrypt) {
        try {Thread.sleep(100);} catch (InterruptedException e) {}
        if (frase == null || authentication == null || frase.length() == 0 || authentication.length() == 0)
            throw new ArithmeticException("Couldn't convert the message");
        char[] fraseToBeConverted = frase.toCharArray();
        char[] keyAt = generateKey(authentication);
        for (int i=0; i<fraseToBeConverted.length; i++) {
            int caracter = fraseToBeConverted[i];
            int key = keyAt[i%keyAt.length];
            if (toDecrypt == true) {
                fraseToBeConverted[i] = (char) (caracter + key);
            } else {
                fraseToBeConverted[i] = (char) (caracter - key);
            }
        }
        return String.valueOf(fraseToBeConverted);
    }



    /** Generates a 512 digits password long using the input Authentication (password) 
     *  @return key in char[]*/
    public synchronized final static char[] generateKey(String password) {
        char[] enlongedPassword = new char[passwordSize];
        int aux;
        for (int i = 0; i < passwordSize; i++) {
            int key1 = i + i/3 + i%2 + password.length() + passwordSize;
            int key2 = password.charAt(i%password.length()) *2;
            aux = incrementalSwitch(key1, key2, i);
            for (int j = 0; j < password.length() * 2 / 3; j++) {
                int key3 = password.charAt((i + j)%password.length()) * 3  -  47  +  i  +  password.length()  +  password.length()/2;
                int key4 = password.charAt(Math.abs((password.length()-(i+j)))%password.length()) * 4  -  56  +  j;
                int key5 = password.charAt((password.length()/2+(i+j)) % password.length()) * 5  +  89  +  i*j;
                int key6 = i*j + j + j/3 + j%2 - passwordSize*2;
                aux = incrementalSwitch(aux, key3, i);
                aux = incrementalSwitch(aux, key4, j);
                aux = incrementalSwitch(aux, key5, i);
                aux = incrementalSwitch(aux, key6, i);
            }
            enlongedPassword[i] = convertToChar(aux);
        }
        return enlongedPassword;
    }


    public synchronized final static String generatekeyString(String password) {
        return String.valueOf(generateKey(password));
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