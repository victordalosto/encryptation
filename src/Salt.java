package src;

public class Salt {

    private final static int saltSize = 16;


    /**
     *  Generates a random salt using:
     *  (i) currentTime and Math.random Library
     *  @return char[saltSize]
     */
    public static char[] generateSalt() {
        char[] now = ("" + System.currentTimeMillis()).toCharArray();
        char[] salt = new char[saltSize];
        int randomNumber = 0;
        for (int i=0; i < saltSize; i++) {
            randomNumber += (int) (Math.random()*400);
            randomNumber += Integer.valueOf(now[i%now.length]);
            randomNumber += Integer.valueOf(now[(now.length-5)+(i)%5]);
            randomNumber += (int) (Math.random()*400);
            salt[i] = Conversion.convertToChar(randomNumber);
            randomNumber = randomNumber%252;
        }
        return salt;
    }
    
}
