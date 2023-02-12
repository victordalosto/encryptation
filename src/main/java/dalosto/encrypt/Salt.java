package dalosto.encrypt;
import java.security.SecureRandom;
import java.util.Random;


public final class Salt {

    private Salt() {throw new RuntimeException();}

    private final static int saltSize = 32;
    private final static Random random = new SecureRandom();


    /**
     *  Generates a random salt using: 
     *   (i)  CurrentTime, and 
     *   (ii) Math.random library
     *   (iii) Security.SecureRandom library
     */
    protected final static char[] generateSalt() {
        char[] salt = new char[saltSize];
        char[] now = ("" + System.currentTimeMillis()).toCharArray();
        int randomNumber = 0;
        for (int i=0; i < saltSize; i++) {
            randomNumber += getRandomNumber(337);
            randomNumber += getRandomNumber(150);
            randomNumber += getRandomNumber(79);
            randomNumber += Integer.valueOf(now[i%now.length]);
            randomNumber += Integer.valueOf(now[Math.abs(now.length-i)%now.length]);
            randomNumber += Integer.valueOf(now[(i+now.length%2)%now.length]);
            randomNumber += Integer.valueOf(now[(now.length-5)+(i)%5]);
            randomNumber += Integer.valueOf(now[(now.length-7)+(i)%7]);
            salt[i] = ConversionService.convertToChar(randomNumber);
            randomNumber = randomNumber%331;
        }
        return salt;
    }


    private static int getRandomNumber(int interval) {
        return (int) Math.abs(random.nextInt(interval) +
                              Math.random()*interval*1.0/3 + 
                              Math.random()*interval*2.0/3);
    }
    
}
