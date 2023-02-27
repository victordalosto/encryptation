package dalosto;


public interface Vcrypt {

    /** Encode the raw password */
    String encode(String password);


    /** Returns an encoded hash in the format: {hashSalt}hashPassword */
    String encodeUsingSalt(String password, String salt);


    /** Verify if the password after encoding matches the encodedpassword*/
    boolean matches(String password, String encodedPassword);
    
}
