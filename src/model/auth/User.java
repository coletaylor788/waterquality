package model.auth;

import javafx.beans.property.*;
import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidEmailException;
import model.auth.exceptions.UnableToHashPasswordException;
import model.exceptions.EmptyRequiredFieldException;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains information for a User
 *
 * @author Cole Taylor
 * @version 1.2
 *
 * Change Log:
 *
 * 1.1
 * - Added role, title, address, city, state, and zipCode instance variables.
 * - Added data validation functionality with exceptions. (moved from UsersData)
 * - Added password hashing ability. (moved from UsersData)
 * - Added setters to allow the user object to be changed after registration.
 *
 * 1.2
 * - Changed instance variables to Properties (StringProperty, ObjectProperty, and IntegerProperty)
 * - Made role a required field. (Need to know what role a user is)
 */
public class User implements Serializable {

    private final SimpleStringProperty username = new SimpleStringProperty();
    private final SimpleStringProperty passwordHash = new SimpleStringProperty();
    private final SimpleStringProperty salt = new SimpleStringProperty();
    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleStringProperty city = new SimpleStringProperty();
    private final SimpleObjectProperty<State> state = new SimpleObjectProperty<>();
    private final SimpleIntegerProperty zipCode = new SimpleIntegerProperty();
    private final SimpleObjectProperty<Role> role = new SimpleObjectProperty<>();

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Creates a user
     * @param username is the username
     * @param password is the password
     * @param firstName the first name
     * @param lastName the last name
     * @param role the role
     * @param email the email
     * @throws AuthenticationException if there is a problem with authenticating
     * @throws EmptyRequiredFieldException if a required field is empty
     */
    public User(String username, String password, String firstName, String lastName,
                Role role, String email)
            throws AuthenticationException, EmptyRequiredFieldException {
        this(username, password, firstName, lastName, role, email, "", "", "", null, 0);
    }

    /**
     * Creates a user
     * @param username the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param role the role
     * @param email the email
     * @param title the title
     * @param address the address
     * @param city the city
     * @param state the state
     * @param zipCode the zip code
     * @throws AuthenticationException if there is a problem with authentication
     * @throws EmptyRequiredFieldException if a required field is empty
     */
    public User(String username, String password, String firstName, String lastName,
                Role role, String email, String title, String address, String city,
                State state, int zipCode)
            throws AuthenticationException, EmptyRequiredFieldException {
        this.username.set(username);

        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);

        setRole(role);
        setEmail(email);

        this.title.set(title);
        this.address.set(address);
        this.city.set(city);
        this.state.set(state);
        this.zipCode.set(zipCode);
    }

    /* ====== GETTERS ====== */

    /**
     * @return the username
     */
    public String getUsername() {
        return username.get();
    }

    /**
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash.get();
    }

    /**
     * @return the password hash's salt
     */
    public String getSalt() {
        return salt.get();
    }

    /**
     * @return the first name
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * @return the last name
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role.get();
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city.get();
    }

    /**
     * @return the state
     */
    public State getState() {
        return state.get();
    }

    /**
     * @return the zip code
     */
    public int getZipCode() {
        return zipCode.get();
    }

    /**
     * Checks password
     * @param password is the password
     * @return true if hashed password matches the stored hash
     * @throws UnableToHashPasswordException if there is a problem hashing
     */
    public boolean isPasswordValid(String password) throws UnableToHashPasswordException {
        String enteredPasswordHash = hashPassword(password, this.salt.get());

        return enteredPasswordHash.equals(this.passwordHash.get());
    }

    /* ====== SETTERS ====== */

    /**
     * Sets the password hash given the password
     * @param password is the password
     * @throws UnableToHashPasswordException if there is a problem hashing
     * @throws EmptyRequiredFieldException if password is empty
     */
    public void setPassword(String password) throws UnableToHashPasswordException,
            EmptyRequiredFieldException {
        if (password.isEmpty()) {
            throw new EmptyRequiredFieldException("Password cannot be empty");
        }
        // Generate random Salt
        SecureRandom randomGen = new SecureRandom();
        int saltInt = randomGen.nextInt();
        String salt = Integer.toString(saltInt);
        this.salt.set(salt);
        this.passwordHash.set(hashPassword(password, salt));
    }

    /**
     * Sets the first name
     * @param firstName the first name
     * @throws EmptyRequiredFieldException if first name is empty
     */
    public void setFirstName(String firstName) throws EmptyRequiredFieldException {
        if (firstName.isEmpty()) {
            throw new EmptyRequiredFieldException("First name cannot be empty");
        }
        this.firstName.set(firstName);
    }

    /**
     * Sets the last name
     * @param lastName is the last name
     * @throws EmptyRequiredFieldException if the last name is empty
     */
    public void setLastName(String lastName) throws EmptyRequiredFieldException {
        if (lastName.isEmpty()) {
            throw new EmptyRequiredFieldException("Last name cannot be empty");
        }
        this.lastName.set(lastName);
    }

    /**
     * Sets the role
     * @param role the role
     * @throws EmptyRequiredFieldException if the role is empty
     */
    public void setRole(Role role) throws EmptyRequiredFieldException {
        if (role == null) {
            throw new EmptyRequiredFieldException("Role cannot be empty");
        }
        this.role.set(role);
    }

    /**
     * Sets the email
     * @param email the email
     * @throws InvalidEmailException if email is of an invalid format
     */
    public void setEmail(String email) throws InvalidEmailException {
        if (!email.isEmpty() && !validate(email)) {
            throw new InvalidEmailException();
        }
        this.email.set(email);
    }

    /**
     * Sets the title
     * @param title the title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Sets the address
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Sets the city
     * @param city the city
     */
    public void setCity(String city) {
        this.city.set(city);
    }

    /**
     * Sets the state
     * @param state the state
     */
    public void setState(State state) {
        this.state.set(state);
    }

    /**
     * Sets the zip code
     * @param zipCode the zip code
     */
    public void setZipCode(int zipCode) {
        this.zipCode.set(zipCode);
    }

    /**
     * Hashes the password
     *
     * @param password is the password to hash
     * @param salt is the salt (randomly generated number) to prepend to the password
     * @return the hashed password
     * @throws UnableToHashPasswordException if hashing algorithm isn't available from provider
     */
    private String hashPassword(String password, String salt) throws UnableToHashPasswordException {
        String saltedPassword = salt + password;
        return sha256(saltedPassword);
    }

    /**
     * Returns the SHA-256 hash of a string
     * @param input is the string to hash
     * @return the hashed string
     * @throws UnableToHashPasswordException if the SHA-256 algorithm isn't provided from provider
     */
    private String sha256(String input) throws UnableToHashPasswordException {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte resultByte: result) {
                sb.append(Integer.toString((resultByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnableToHashPasswordException();
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

    /**
     * Returns the string
     * @return a string representation of the User
     */
    @Override
    public String toString() {
        return lastName.get() + ", " + firstName.get();
    }
}
