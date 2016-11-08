package model.auth;

import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidPasswordException;
import model.auth.exceptions.InvalidUsernameException;
import model.auth.exceptions.UnableToCreateUserException;
import model.exceptions.EmptyRequiredFieldException;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Forward facing authentication class
 *
 * Example Usage:
 *   UsersData ud = new UsersData()
 *   ud.add(...)
 *   User currentUser = ud.login(username, password)
 *
 * @author Cole Taylor
 * @version 1.1
 *
 * Change Log:
 *
 * 1.1
 * - Removed password hashing ability (moved to User)
 * - Removed data validation (moved to User)
 * - Restructured to only create and authenticate users, not manage them.
 */
public class UsersData implements Serializable {

    private final HashMap<String, User> users;
    private User currentUser;

    public UsersData() {
        users = new HashMap<>();
        currentUser = null;
    }

    public HashMap<String, User> getUserMap() {
        return users;
    }

    /**
     * Adds a user account with requirements + email. (backwards compatibility)
     *
     * @param username is the username of the user
     * @param password is the password of the user. It will be hashed
     * @param firstName is the first name of the user.
     * @param lastName is the last name of the user.
     * @param role is the role of the user.
     * @param email is the email of the user
     * @throws AuthenticationException if there is a problem with authentication.
     * @throws EmptyRequiredFieldException if a required field is empty
     */
    public void addUser(String username, String password, String firstName,
                        String lastName, Role role, String email)
            throws AuthenticationException, EmptyRequiredFieldException {
        if (username.isEmpty()) {
            throw new EmptyRequiredFieldException("Username cannot be empty");
        } else if (users.containsKey(username)) {
            throw new UnableToCreateUserException("Username: " + username + " already exists.");
        }

        User newUser = new User(username, password, firstName, lastName, role, email, "", "", "", null, 0);

        // Add user to users map
        users.put(username, newUser);
    }

    /**
     * Logs the given user in to the system
     *
     * @param username is the username of the account to be logged in
     * @param password is the password of the account to be logged in
     * @throws AuthenticationException if their is an error with authentication
     */
    public void login(String username, String password) throws AuthenticationException {
        if (!users.containsKey(username)) {
            throw new InvalidUsernameException();
        }
        User user = users.get(username);

        if (user.isPasswordValid(password)) {
            currentUser = user;
        } else {
            throw new InvalidPasswordException();
        }
    }

    /**
     * Logs out the current user
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Returns the logged in user. This method can be used to edit
     * the mutable instance variables of a user.
     *
     * @return the currently logged in user, null if no one is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

}
