package model.auth;

/**
 * Defines different roles for users and provides
 * a String representation for each role.
 *
 * @author Cole Taylor
 * @version 1.0
 */
public enum Role {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String title;

    /**
     * Create role
     * @param title is the string representation of the Role
     */
    Role(String title) {
        this.title = title;
    }

    /**
     * Returns String representation
     * @return the string representation of the role.
     */
    @Override
    public String toString() {
        return this.title;
    }
}
