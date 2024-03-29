package dalosto.encrypt;


public final class ConversionService {

    private ConversionService() {throw new RuntimeException("Cant instantiate this class");}

    private static final int range = 90;


    /** 
     * Converts an int to a char in the ASCI Table. 
     * equivalent from ! to z                    
     * @return  char in the ASCII table from: 33 to 122;
    */
    protected final static char convertToChar(int value) {
        value = 33 + (Math.abs(value) % range);
        return (char) value;
    }



    /** 
     * Returns the number of character used to convert
     * number into character in the ASCI table.
     */
    protected final static int getRange() {
        return range;
    }



    protected final static char[] formatPassword(char[] password, char[] salt) {
        return ("{" + new String(salt) + "}" + new String(password)).toCharArray();
    }


    
    protected final static char[] getSaltFromEncodedPassword(char[] encodedPassword) {
        return new String(encodedPassword).split("}")[0].replace("{", "").toCharArray();
    }

}
