package dalosto.encrypt;

public final class Encoder {

    private Encoder() {throw new RuntimeException();}

    private final static int keySize = 128;


    protected final static boolean matches(char[] password, char[] encodedPassword) {
        char[] salt = ConversionService.getSaltFromEncodedPassword(encodedPassword);
        char[] newEncodedPassword = encode(password, salt);
        return (new String(encodedPassword).equals(new String(newEncodedPassword)));
    }

    
    public final static char[] encode(char[] password) {
        return encode(password, Salt.generateNewSalt());
    }


    protected final static char[] encode(char[] password, char[] salt) {
        char[] encodedPassword = new char[keySize];
        int len = password.length%10000;
        int saltLen = salt.length;
        int letterRange = ConversionService.getRange();
        int aux = 0;
        for (int i = 0; i < keySize; i++) {
            char carac = password[i%len];
            int key01 = (i + carac + (keySize*50)/(carac-30) + (len*30)/(carac-31) - i*carac + salt[i%saltLen] - salt[(carac*13)%saltLen])%10;
            int key02 = (i*salt[i%saltLen] - len%50*salt[i%saltLen] + carac*salt[i%saltLen]*2 + carac*salt[(i+1)%saltLen]*3 - keySize - salt[carac%saltLen])%10;
            int key03 = (i + i%2 + i%1 - i*13 + (i*salt[i%saltLen])%9 + len - keySize + len*keySize + salt[(carac*3)%saltLen] + 25*salt[(i+2)%saltLen]*carac)%97 + 11;
            int key04 = (i*salt[(carac*2)%saltLen] - keySize*carac + salt[i%saltLen]*30/carac + carac*salt[(len+1)%saltLen] + salt[((i+3)%50*len)%saltLen])%letterRange;
            int key05 = (salt[(carac*5)%saltLen] + carac - carac*(i%4+salt[i%saltLen]) + carac*(i+keySize)%101 + carac*len + (len*1000)%(i+1) + salt[(i+4)%saltLen])%letterRange;
            int key06 = (carac*((i+1)%9) + carac*(len*i) + carac*(keySize + i) + carac*salt[(i+carac)%saltLen] + carac*salt[i%saltLen] + carac*salt[(i+5)%saltLen])%letterRange;
            aux = incrementalMadness(aux, key01, i); 
            aux = incrementalMadness(aux, key02, i); 
            aux = incrementalMadness(aux, key03, i);
            aux = incrementalMadness(aux, key04, i); 
            aux = incrementalMadness(aux, key05, i); 
            aux = incrementalMadness(aux, key06, i);
            for (int j = 0; j < len; j++) {
                int key07 = ((((i+2) % 5  + 7) * password[salt[i%saltLen]%len] * password[salt[j%saltLen]%len]) - j%7 + i%13 + len%3 + salt[j%saltLen] + salt[i%saltLen])%letterRange;
                int key08 = ((((i+3) % 7  + 6) * password[Math.abs((len-Math.abs(i-j))%len)] * (j%7+11)) - i%11 + salt[j%saltLen] + salt[(i+j)%saltLen] - salt[(j%(i+3))%saltLen])%letterRange;
                int key09 = ((((i+4) % 17 + 5) * password[Math.abs(len/2-j)%len] * (j%6 + len%(j+5)+len%(i+7))) - i%40 + j%50 - salt[(j+5)%saltLen] + salt[carac%saltLen])%letterRange;
                int key10 = ((((i+5) % 9  + 4) * password[Math.abs(len/2+j)%len] * (j%7 + len%(j+7)+len%(i+9))) - keySize - i + salt[(j+7)%saltLen] + salt[carac*(j+1)%saltLen])%letterRange;
                int key11 = ((((i+6) % 13 + 3) * password[Math.abs(len/4-j)%len] * (j%8 + len%(j+9)+len%(i+5))) + (len-i)%120 + salt[(j+6)%saltLen] + salt[carac*(j+2)%saltLen])%letterRange;
                int key12 = ((((i+7) % 11 + 2) * password[Math.abs(len/4+j)%len] * (j%9 + len%(j+5)+len%(i+7))) - j%12 + i%32 + salt[(j+4)%saltLen] + salt[carac*(j+3)%saltLen])%letterRange;
                int key13 = keySize/3 + i%13 + j%4 + ((i%7) * (j%3)) - (30*password[j%len])%15 + j + len%3 + salt[j%saltLen] + salt[len%saltLen] + password[carac%len];
                int key14 = (carac*salt[(i+j)%len] + carac*salt[i%saltLen] - carac*salt[j%saltLen] - password[salt[j%saltLen]%len] + salt[(carac)%saltLen])%letterRange;
                int key15 = salt[(j)%saltLen]*300%(j+50);
                aux = incrementalMadness(aux, key07, i); 
                aux = incrementalMadness(aux, key08, j); 
                aux = incrementalMadness(aux, key09, j);
                aux = incrementalMadness(aux, key10, j); 
                aux = incrementalMadness(aux, key11, j); 
                aux = incrementalMadness(aux, key12, j);
                aux = incrementalMadness(aux, key13, j); 
                aux = incrementalMadness(aux, key14, j); 
                aux = incrementalMadness(aux, key15, j);
            }
            encodedPassword[i] = ConversionService.convertToChar(aux);
        }
        return ConversionService.formatPassword(encodedPassword, salt);
    }



    /**
     *  This is a simple function that: increment or decrement 
     *  two int based on a number used as a boolean (i%2==0)
     *  @return int result of the operation
     */
    private final static int incrementalMadness(int num1, int num2, int bool) {
        if (bool % 2 == 0)
            return (num1 + num2 + num1 % 13 + num1 % 17 + bool)%ConversionService.getRange();
        return (num1 - num2 + num1 % 15 + num1 % 11 - bool + 5)%ConversionService.getRange();
    }

    
}
