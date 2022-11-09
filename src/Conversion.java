package src;

public class Conversion {

    private static int range = 90;


    /** 
     * Converts an int to a char in the ASCI Table. 
     * returns a char in the table from: 33 to 122
     * equivalent from ! to z                    
    */
    public static char convertToChar(int value) {
        value = Math.abs(value) % range;
        value = value + 33;
        return (char) value;
    }


    /** 
     * Returns the number of character used to convert
     * number into character in the ASCI table.
     */
    public static int getRange() {
        return range;
    }



    public static char[] formatPassword(char[] password, char[] salt) {
        return ("{" + new String(salt) + "}" + new String(password)).toCharArray();
    }


    
    
}
