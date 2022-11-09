
import src.Encoding;
import src.Salt;

public class Testa {

    public static void main(String[] args) {

        String password = "12345";
        char[] generateSalt = Salt.generateSalt();
        System.out.println("Salt: " + new String(generateSalt));
        System.out.println("password: " + password);
        char[] generateKey = Encoding.generateKey(password, generateSalt);
        System.out.println("generated key: " + new String(generateKey));

       
    }
    
}
