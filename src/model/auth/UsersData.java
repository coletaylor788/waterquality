package model.auth;

import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidPasswordException;
import model.auth.exceptions.InvalidUsernameException;
import model.auth.exceptions.UnableToCreateUserException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.security.SecureRandom;

/**
 * Forward facing authentication class
 *
 * Example Usage:
 *   UsersData ud = new UsersData()
 *   ud.add(...)
 *   User currentUser = ud.login(username, password)
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class UsersData {

    private HashMap<String, User> users;
    private User currentUser;

    public UsersData() {
        users = new HashMap<>();
        try {
            addUser("user", "pass", "Sample", "User", "sampleuser@sample.com");
        } catch (UnableToCreateUserException e) {
            System.out.println("[WARN] unable to add sample user");
        }
        currentUser = null;

    }

    /**
     * Adds a user account
     *
     * @param username is the username of the user
     * @param password is the password of the user. It will be hashed
     * @param firstName is the first name of the user.
     * @param lastName is the last name of the user.
     * @param email is the email of the user.
     * @throws UnableToCreateUserException if user creation failed
     */
    public void addUser(String username, String password, String firstName, String lastName,
                               String email) throws UnableToCreateUserException {

        // Generate random Salt
        SecureRandom randomGen = new SecureRandom();
        int saltInt = randomGen.nextInt();
        String salt = Integer.toString(saltInt);

        // Apply the SHA-256 hashing algorithm to the password
        String passwordHash = hashPassword(password, salt);

        // Add user to users map
        users.put(username, new User(username, passwordHash, salt, firstName, lastName, email));
    }

    /**
     * Logs the given user in to the system
     *
     * @param username is the username of the account to be logged in
     * @param password is the password of the account to be logged in
     * @return User object that is logged in.
     * @throws AuthenticationException if their is an error with authentication
     */
    public User login(String username, String password) throws AuthenticationException {
        if (!users.containsKey(username)) {
            throw new InvalidUsernameException("Username is incorrect");
        }
        User user = users.get(username);
        try {
            if (hashPassword(password, user.getSalt()).equals(user.getPasswordHash())) {
                currentUser = user;
                return currentUser;
            } else {
                throw new InvalidPasswordException("Password is incorrect");
            }
        } catch (UnableToCreateUserException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    /**
     * Logs out the current user
     *
     * @return true if their was a user logged in, false otherwise
     */
    public boolean logout() {
        if (currentUser == null) {
            return false;
        } else {
            currentUser = null;
            return true;
        }
    }

    /**
     * Returns the logged in user
     *
     * @return the currently logged in user, null if no one is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @return true if there is a user logged in
     */
    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    /**
     * Hashes the password
     *
     * @param password is the password to hash
     * @param salt is the salt (randomly generated number) to prepend to the password
     * @return the hashed password
     * @throws UnableToCreateUserException if hashing algorithm isn't available from provider
     */
    private String hashPassword(String password, String salt) throws UnableToCreateUserException {
        String saltedPassword = salt + password;
        return sha256(saltedPassword);
    }

    /**
     * Returns the SHA-256 hash of a string
     * @param input is the string to hash
     * @return the hashed string
     * @throws UnableToCreateUserException if the SHA-256 algorithm isn't provided from provider
     */
    private String sha256(String input) throws UnableToCreateUserException {
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
