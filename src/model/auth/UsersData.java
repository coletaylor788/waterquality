package model.auth;

import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidPasswordException;
import model.auth.exceptions.InvalidUsernameException;
import model.auth.exceptions.UnableToCreateUserException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty()
                || lastName.isEmpty() || email.isEmpty()) {
            throw new UnableToCreateUserException("Please fill in all fields.");
        } else if (!validate(email)) {
            throw new UnableToCreateUserException("Email address is not valid.");
        } else if (users.containsKey(username)) {
            throw new UnableToCreateUserException("Username: " + username + " already exists.");
        }
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


}
