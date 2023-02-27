package dalosto.encrypt;

import dalosto.Vcrypt;


public class VcryptImp implements Vcrypt {

    public VcryptImp() {}

    
    /** Encode the raw password */
    public final String encode(String password) {
        return new String(Encoder.encode(password.toCharArray()));
    }


    /** Returns an encoded hash in the format: {hashSalt}hashPassword */
    public final String encodeUsingSalt(String password, String salt) {
        return new String(Encoder.encode(password.toCharArray(), salt.toCharArray()).toString());
    }


    /** Verify if the password after encoding matches the encodedpassword*/
    public final boolean matches(String password, String encodedPassword) {
        return Encoder.matches(password.toCharArray(), encodedPassword.toCharArray());
    }
    
}
