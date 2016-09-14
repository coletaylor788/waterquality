package model.auth;

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

    public User(String username, String passwordHash, String salt, String firstName, String lastName, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }
}
