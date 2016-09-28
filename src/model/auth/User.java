package model.auth;

import javafx.beans.property.*;
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
 */
public class User {

    private StringProperty username = new SimpleStringProperty();
    private StringProperty passwordHash = new SimpleStringProperty();
    private StringProperty salt = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private ObjectProperty<State> state = new SimpleObjectProperty<>();
    private IntegerProperty zipCode = new SimpleIntegerProperty();
    private ObjectProperty<Role> role = new SimpleObjectProperty<>();

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
        this.username.set(username);

        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.role.set(role);

        setEmail(email);

        this.title.set(title);
        this.address.set(address);
        this.city.set(city);
        this.state.set(state);
        this.zipCode.set(zipCode);
    }

    /* ====== GETTERS ====== */
    public String getUsername() {
        return username.get();
    }
    public String getPasswordHash() {
        return passwordHash.get();
    }
    public String getSalt() {
        return salt.get();
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public Role getRole() {
        return role.get();
    }
    public String getEmail() {
        return email.get();
    }
    public String getTitle() {
        return title.get();
    }
    public String getAddress() {
        return address.get();
    }
    public String getCity() {
        return city.get();
    }
    public State getState() {
        return state.get();
    }
    public int getZipCode() {
        return zipCode.get();
    }

    public boolean isPasswordValid(String password) throws UnableToHashPasswordException {
        String enteredPasswordHash = hashPassword(password, this.salt.get());

        return enteredPasswordHash.equals(this.passwordHash.get());
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
        this.salt.set(salt);
        this.passwordHash.set(hashPassword(password, salt));
    }

    public void setFirstName(String firstName) throws EmptyRequiredFieldException {
        if (firstName.isEmpty()) {
            throw new EmptyRequiredFieldException("First name cannot be empty");
        }
        this.firstName.set(firstName);
    }
    public void setLastName(String lastName) throws EmptyRequiredFieldException {
        if (lastName.isEmpty()) {
            throw new EmptyRequiredFieldException("Last name cannot be empty");
        }
        this.lastName.set(lastName);
    }
    public void setRole(Role role) {
        this.role.set(role);
    }

    public void setEmail(String email) throws InvalidEmailException {
        if (!email.isEmpty() && !validate(email)) {
            throw new InvalidEmailException("Email has an invalid format");
        }
        this.email.set(email);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
    public void setAddress(String address) {
        this.address.set(address);
    }
    public void setCity(String city) {
        this.city.set(city);
    }
    public void setState(State state) {
        this.state.set(state);
    }
    public void setZipCode(int zipCode) {
        this.zipCode.set(zipCode);
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
