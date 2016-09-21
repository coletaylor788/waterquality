package model.auth;

import model.auth.exceptions.UnableToCreateUserException;
import model.auth.exceptions.UnableToSetPasswordException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains information for a User
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class User {
    private String username;
    private String passwordHash;
    private String salt;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private String address;
    private String city;
    private State state;
    private int zipCode;
    private Role role;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public User(String username, String passwordHash, String salt,
                String firstName, String lastName, Role role, String email) {
        this(username, passwordHash, salt, firstName, lastName, role, email, "", "", "", null, 0);
    }

    public User(String username, String passwordHash, String salt,
                String firstName, String lastName, Role role, String email,
                String title, String address, String city, State state, int zipCode) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.title = title;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    /* ====== GETTERS ====== */
    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getSalt() {
        return salt;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public Role getRole() {
        return role;
    }
    public String getEmail() {
        return email;
    }
    public String getTitle() {
        return title;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public State getState() {
        return state;
    }
    public int getZipCode() {
        return zipCode;
    }

    /* ====== SETTERS ====== */
    public void setUsername(String username) {
         this.username = username;
    }
    public void setPassword(String password) throws UnableToSetPasswordException {
        // Generate random Salt
        SecureRandom randomGen = new SecureRandom();
        int saltInt = randomGen.nextInt();
        String salt = Integer.toString(saltInt);
        this.salt = salt;
        this.passwordHash = hashPassword(password, salt);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Hashes the password
     *
     * @param password is the password to hash
     * @param salt is the salt (randomly generated number) to prepend to the password
     * @return the hashed password
     * @throws UnableToCreateUserException if hashing algorithm isn't available from provider
     */
    private String hashPassword(String password, String salt) throws UnableToSetPasswordException {
        String saltedPassword = salt + password;
        return sha256(saltedPassword);
    }

    /**
     * Returns the SHA-256 hash of a string
     * @param input is the string to hash
     * @return the hashed string
     * @throws UnableToCreateUserException if the SHA-256 algorithm isn't provided from provider
     */
    private String sha256(String input) throws UnableToSetPasswordException {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnableToSetPasswordException("Unable to get provider for SHA-256 algorithm");
        }
    }

    /**
     * Validate an email string against a regex
     *
     * @param emailStr is the email to validate
     * @return true if the email is valid, false otherwise
     */
    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
