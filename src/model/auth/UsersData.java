package model.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.security.SecureRandom;

/**
 * Created by cole on 9/14/16.
 */
public class UsersData {
    private static HashMap<String, User> users = new HashMap<>();
    private static User currentUser = null;

    public static void addUser(String username, String password, String firstName, String lastName, String email) throws UnableToCreateUserException {
        // Generate random Salt
        SecureRandom randomGen = new SecureRandom();
        int saltInt = randomGen.nextInt();
        String salt = Integer.toString(saltInt);

        // Apply the SHA-256 hashing algorithm to the password
        String passwordHash = hashPassword(password, salt);

        // Add user to users map
        users.put(username, new User(username, passwordHash, salt, firstName, lastName, email));
    }

    public static boolean login(String username, String password) throws AuthenticationException {
        User user = users.get(username);
        try {
            boolean isValid = hashPassword(password, user.getSalt()).equals(user.getPasswordHash());
            if (isValid) {
                currentUser = user;
            }
            return isValid;
        } catch (UnableToCreateUserException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    private static String

    private static String hashPassword(String password, String salt) throws UnableToCreateUserException {
        String saltedPassword = salt + password;
        return sha256(saltedPassword);
    }

    private static String sha256(String input) throws UnableToCreateUserException {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnableToCreateUserException("Unable to get provider for SHA-256 algorithm");
        }
    }
}
