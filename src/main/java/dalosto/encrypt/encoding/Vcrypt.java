package dalosto.encrypt.encoding;


public final class Vcrypt {

    /** Encode the raw password */
    public final static String encode(String password) {
        return new String(Encoder.encode(password.toCharArray()));
    }


    /** Returns a encoded hash in the format: {hashSalt}hashPassword */
    public final static String encodeUsingSalt(String password, String salt) {
        return new String(Encoder.encode(password.toCharArray(), salt.toCharArray()).toString());
    }


    /** Verify if the password after encoded matches the encodedpassword*/
    public final static boolean matches(String password, String encodedPassword) {
        return Encoder.matches(password.toCharArray(), encodedPassword.toCharArray());
    }
    
}
