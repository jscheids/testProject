
package edu.wctc.jls.MyEcomApp.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
/**
 *
 * @author Jennifer
 */


/**
 * Generates a salted, SHA-512 hashed password based on the 
 * username and password field values in the users table in the db. Used  
 * to initially seed the database with the hashed passwords.

 */
public class ShaHashGeneratorApp {

    /**
     * @param args the command line arguments - not used.
     */
    public static void main(String[] args) {
        String salt = "testuser@isp.com"; 
        String password = "testuser"; 
        System.out.println(password + ": " + sha512(password,salt));

        salt = "testmgr@isp.com"; 
        password = "testmgr"; 
        System.out.println(password + ": " + sha512(password,salt));

    }

    public static String sha512(String pwd, String salt) {

            ShaPasswordEncoder pe = new ShaPasswordEncoder(512);
            pe.setIterations(1024);
            String hash = pe.encodePassword(pwd, salt);

            return hash;
     
    }
}


