package dalosto.encrypt;
import dalosto.encrypt.encoding.Vcrypt;


public class App {
    public static void main(String[] args) {
        String encode = Vcrypt.encode("212329");
        System.out.println(encode);
    }
}
