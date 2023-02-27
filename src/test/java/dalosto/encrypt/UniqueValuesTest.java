package dalosto.encrypt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class UniqueValuesTest {

    private int size = 50000;


    @Test
    public void shouldGenerateUniqueSalts() {
        List<String> listSalts = new ArrayList<>();
        for (int i=0; i<size; i++) {
            String tmp = new String(Salt.generateNewSalt());
            assertTrue(!containsInList(listSalts, tmp));
            listSalts.add(tmp);
        }
    }



    @Test
    public void shouldGenerateUniqueEncodedPasswords() {
        List<String> listPasswords = new ArrayList<>();
        char[] pass = "12345".toCharArray();
        for (int i=0; i<size; i++) {
            String tmp = new String(Encoder.encode(pass));
            assertTrue(!containsInList(listPasswords, tmp));
            listPasswords.add(tmp);
        }
    }



    @Test
    public void shouldGenerateUniqueEncodedPasswordsUsingSalt() {
        List<String> listPasswords = new ArrayList<>();
        char[] pass = "12345".toCharArray();
        for (int i=0; i<size; i++) {
            char[] tmpSalt = Salt.generateNewSalt();
            String tmp = new String(Encoder.encode(pass, tmpSalt));
            assertTrue(!containsInList(listPasswords, tmp));
            listPasswords.add(tmp);
        }
    }


    
    @Test
    public void shouldGenerateSamePasswordUsingSameSalt() {
        char[] pass = "12345".toCharArray();
        char[] salt = Salt.generateNewSalt();
        char[] encodedPass = Encoder.encode(pass, salt);
        for (int i=0; i<size; i++) {
            char[] tmp = Encoder.encode(pass, salt);
            assertEquals(new String(encodedPass), new String(tmp));
        }
    }



    @Test
    public void shouldMatchRightPasswords() {
        char[] pass = "12345".toCharArray();
        char[] salt = Salt.generateNewSalt();
        char[] encodedPass = Encoder.encode(pass, salt);
        assertTrue(Encoder.matches(pass, encodedPass));
    }



    @Test
    public void shouldNotMatchWrongPasswords() {
        char[] pass = "12345".toCharArray();
        char[] salt = Salt.generateNewSalt();
        char[] encodedPass = Encoder.encode(pass, salt);
        assertFalse(Encoder.matches("123456".toCharArray(), encodedPass));
    }



    private boolean containsInList(List<String> list, String pass) {
        return list.stream().anyMatch(x -> x.equals(pass));
    }
}
