package edu.wctc.jls.MyEcomApp.util;

import edu.wctc.jls.MyEcomApp.exeption.InvalidParameterException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * @author Jennifer
 * Class provided by instructor. Generates a salted, SHA-512 hashed password
 * based on the username and password field values in the users table in the db.
 * Used to initially seed the database with the hashed passwords.
 */
public class ShaHashGeneratorApp {

    /**
     * @param args the command line arguments - not used.
     */
    public static void main(String[] args) {
        
        try {
            String salt = "testuser@isp.com";
            String password = "testuser";
            System.out.println(password + ": " + sha512(password, salt));

            salt = "testmgr@isp.com";
            password = "testmgr";
            System.out.println(password + ": " + sha512(password, salt));
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Method to salt and has the password
     *
     * @param pwd password to be hashed and salted
     * @param salt- the salt to be used in the pw hashing
     * @return the newly salted and hashed password
     * @throws InvalidParameterException
     */
    public static String sha512(String pwd, String salt) throws InvalidParameterException {
        if (pwd.isEmpty() || pwd == null || salt.isEmpty() || salt == null) {
            throw new InvalidParameterException();
        }
        ShaPasswordEncoder pe = new ShaPasswordEncoder(512);
        pe.setIterations(1024);
        String hash = pe.encodePassword(pwd, salt);

        return hash;

    }
}
