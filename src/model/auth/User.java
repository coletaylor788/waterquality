package model.auth;

import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidEmailException;
import model.auth.exceptions.UnableToCreateUserException;
import model.auth.exceptions.UnableToHashPasswordException;
import model.exceptions.EmptyRequiredFieldException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains information for a User
 *
 * @author Cole Taylor
 * @version 1.1
 *
 * Change Log:
 *
 * 1.1
 * - Added role, title, address, city, state, and zipCode instance variables.
 * - Added data validation functionality with exceptions. (moved from UsersData)
 * - Added password hashing ability. (moved from UsersData)
 * - Added setters to allow the user object to be changed after registration.
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

    public User(String username, String password, String firstName, String lastName,
                Role role, String email)
            throws AuthenticationException, EmptyRequiredFieldException {
        this(username, password, firstName, lastName, role, email, "", "", "", null, 0);
    }

    public User(String username, String password, String firstName, String lastName,
                Role role, String email, String title, String address, String city,
                State state, int zipCode)
            throws AuthenticationException, EmptyRequiredFieldException {
        this.username = username;
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.role = role;
        setEmail(email);
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

    public boolean isPasswordValid(String password) throws UnableToHashPasswordException {
        String enteredPasswordHash = hashPassword(password, this.salt);
        return enteredPasswordHash.equals(this.passwordHash);
    }

    /* ====== SETTERS ====== */
    public void setPassword(String password) throws UnableToHashPasswordException,
            EmptyRequiredFieldException {
        if (password.isEmpty()) {
            throw new EmptyRequiredFieldException("Password cannot be empty");
        }
        // Generate random Salt
        SecureRandom randomGen = new SecureRandom();
        int saltInt = randomGen.nextInt();
        String salt = Integer.toString(saltInt);
        this.salt = salt;
        this.passwordHash = hashPassword(password, salt);
    }

    public void setFirstName(String firstName) throws EmptyRequiredFieldException {
        if (firstName.isEmpty()) {
            throw new EmptyRequiredFieldException("First name cannot be empty");
        }
        this.firstName = firstName;
    }
    public void setLastName(String lastName) throws EmptyRequiredFieldException {
        if (lastName.isEmpty()) {
            throw new EmptyRequiredFieldException("Last name cannot be empty");
        }
        this.lastName = lastName;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) throws InvalidEmailException {
        if (!email.isEmpty() && !validate(email)) {
            throw new InvalidEmailException("Email has an invalid format");
        }
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
    private String hashPassword(String password, String salt) throws UnableToHashPasswordException {
        String saltedPassword = salt + password;
        return sha256(saltedPassword);
    }

    /**
     * Returns the SHA-256 hash of a string
     * @param input is the string to hash
     * @return the hashed string
     * @throws UnableToCreateUserException if the SHA-256 algorithm isn't provided from provider
     */
    private String sha256(String input) throws UnableToHashPasswordException {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnableToHashPasswordException("Unable to get provider for SHA-256 algorithm");
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
