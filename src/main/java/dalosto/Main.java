package dalosto;

import dalosto.encrypt.VcryptImp;

public class Main {

    static Vcrypt vcrypt = new VcryptImp();
    public static void main(String[] args) {
        String encode = vcrypt.encode("12345");

        System.out.println(encode);
        
    }
    
}
