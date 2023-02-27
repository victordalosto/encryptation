package dalosto.encrypt;
import java.security.SecureRandom;
import java.util.Random;


public final class Salt {

    private Salt() {throw new RuntimeException("Cant instantiate this class");}

    private final static int saltSize = 32;
    private final static Random random = new SecureRandom();


    /**
     *  Generates a random salt using: 
     *   (i)  CurrentTime, and 
     *   (ii) Math.random library
     *   (iii) Security.SecureRandom library
     */
    protected final static char[] generateNewSalt() {
        char[] salt = new char[saltSize];
        char[] now = ("" + System.currentTimeMillis()).toCharArray();
        int randomNumber = 0;
        for (int i=0; i < saltSize; i++) {
            randomNumber = randomNumber%331;
            randomNumber += 1*getRandomNumber(337);
            randomNumber += 2*getRandomNumber(150);
            randomNumber += 3*getRandomNumber(79);
            randomNumber += 4*getRandomNumber(11);
            randomNumber += 5*Integer.valueOf(now[i%now.length]);
            randomNumber += 6*Integer.valueOf(now[Math.abs(now.length-i)%now.length]);
            randomNumber += 7*Integer.valueOf(now[(i+now.length%2)%now.length]);
            randomNumber += 8*Integer.valueOf(now[(now.length-5)+(i)%5]);
            randomNumber += 9*Integer.valueOf(now[(now.length-7)+(i)%7]);
            salt[i] = ConversionService.convertToChar(randomNumber);
        }
        return salt;
    }


    /** Generates a random  int value */
    private static int getRandomNumber(int interval) {
        return (int) Math.abs(random.nextInt(interval)
                              + Math.random()*interval*1.0/3
                              - Math.random()*interval*2.0/3);
    }
    
}
