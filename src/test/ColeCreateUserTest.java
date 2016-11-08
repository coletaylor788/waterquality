package test;

import model.auth.Role;
import model.auth.State;
import model.auth.User;
import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidEmailException;
import model.auth.exceptions.UnableToHashPasswordException;
import model.exceptions.EmptyRequiredFieldException;
import org.junit.Test;

/**
 * Tests the creation of a User
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class ColeCreateUserTest {

    /**
     * Tests a valid call to create a user
     */
    @Test
    public void testValidParameters() {
        User u = null;
        try {
            u = new User("user", "password", "first", "last", Role.USER, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
        assert(checkUser(u, "user", "password", "first", "last", Role.USER, "c@c.com", "title",
                "address", "city", State.ALABAMA, 12345));
    }

    /**
     * Test creating a user with an empty password
     * @throws EmptyRequiredFieldException signaling password is empty
     */
    @Test(expected = EmptyRequiredFieldException.class)
    public void testEmptyPassword() throws EmptyRequiredFieldException {
        try {
            new User("user", "", "first", "last", Role.USER, "c@c.com", "title", "address",
                    "city", State.ALABAMA, 12345);
        } catch (AuthenticationException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with an empty first name
     * @throws EmptyRequiredFieldException signaling first name is empty
     */
    @Test(expected = EmptyRequiredFieldException.class)
    public void testEmptyFirstName() throws EmptyRequiredFieldException {
        try {
            new User("user", "password", "", "last", Role.USER, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with an empty last name
     * @throws EmptyRequiredFieldException signaling last name is empty
     */
    @Test(expected = EmptyRequiredFieldException.class)
    public void testEmptyLastName() throws EmptyRequiredFieldException {
        try {
            new User("user", "password", "first", "", Role.USER, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with an empty role
     * @throws EmptyRequiredFieldException signaling role is empty
     */
    @Test(expected = EmptyRequiredFieldException.class)
    public void testEmptyRole() throws EmptyRequiredFieldException {
        try {
            new User("user", "password", "first", "last", null, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with role = WORKER
     */
    @Test
    public void testWorkerRole() {
        User u = null;
        try {
            u = new User("user1", "password1", "first1", "last1", Role.WORKER, "c1@c.com", "title1",
                    "address1", "city1", State.GEORGIA, 12346);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
        assert(checkUser(u, "user1", "password1", "first1", "last1", Role.WORKER, "c1@c.com", "title1",
                "address1", "city1", State.GEORGIA, 12346));
    }

    /**
     * Tests creating a user with role = MANAGER
     */
    @Test
    public void testManagerRole() {
        User u = null;
        try {
            u = new User("user", "password", "first", "last", Role.MANAGER, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
        assert(checkUser(u, "user", "password", "first", "last", Role.MANAGER, "c@c.com", "title",
                "address", "city", State.ALABAMA, 12345));
    }

    /**
     * Tests creating a user with role = ADMIN
     */
    @Test
    public void testAdminRole() {
        User u = null;
        try {
            u = new User("user", "password", "first", "last", Role.ADMIN, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
        assert(checkUser(u, "user", "password", "first", "last", Role.ADMIN, "c@c.com", "title",
                "address", "city", State.ALABAMA, 12345));
    }

    /**
     * Tests creating a user with an email missing the '@' character
     */
    @Test
    public void testEmailWithoutAt() {
        try {
            new User("user", "password", "first", "last", Role.USER, "cc.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (InvalidEmailException e) {
            assert(true);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with an email missing characters before the '@' symbol
     */
    @Test
    public void testEmailWithoutPrefix() {
        try {
            new User("user", "password", "first", "last", Role.USER, "@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (InvalidEmailException e) {
            assert(true);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with an email missing the domain name. (i.e. gmail, gatech)
     */
    @Test
    public void testEmailWithoutDomain() {
        try {
            new User("user", "password", "first", "last", Role.USER, "c@.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (InvalidEmailException e) {
            assert(true);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with an email missing the domain suffix (i.e. com, org, edu)
     */
    @Test
    public void testEmailWithoutSuffix() {
        try {
            new User("user", "password", "first", "last", Role.USER, "c@c.", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (InvalidEmailException e) {
            assert(true);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            assert(false);
        }
    }

    /**
     * Tests creating a user with an email missing the dot
     */
    @Test
    public void testEmailWithoutDot() {
        try {
            new User("user", "password", "first", "last", Role.USER, "c@com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (InvalidEmailException e) {
            assert(true);
        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            System.out.println(e);
            assert(false);
        }
    }

    /**
     * Checks the user against a series of arguments
     *
     * @param u is the user to check against
     * @param username is the username to match
     * @param password is the password to match
     * @param first is the first name to match
     * @param last is the last name to match
     * @param role is the role to match
     * @param email is the email to match
     * @param title is the title to match
     * @param address is the address to match
     * @param city is the city to match
     * @param state is the state to match
     * @param zip is the zip code to match
     *
     * @return true if the fields of u equal the provided arguments, false otherwise
     */
    private boolean checkUser(User u, String username, String password, String first, String last,
                              Role role, String email, String title, String address, String city,
                              State state, int zip) {
        if (u == null) {
            return false;
        }
        try {
            boolean equal = true;
            if (!username.equals(u.getUsername())) {
                equal = false;
            } else if (!u.isPasswordValid(password)) {
                equal = false;
            } else if (!first.equals(u.getFirstName())) {
                equal = false;
            } else if (!last.equals(u.getLastName())) {
                equal = false;
            } else if (role != u.getRole()) {
                equal = false;
            } else if (!email.equals(u.getEmail())) {
                equal = false;
            } else if (!title.equals(u.getTitle())) {
                equal = false;
            } else if (!address.equals(u.getAddress())) {
                equal = false;
            } else if (!city.equals(u.getCity())) {
                equal = false;
            } else if (state != u.getState()) {
                equal = false;
            } else if (zip != u.getZipCode()) {
                equal = false;
            }
            return equal;
        } catch (UnableToHashPasswordException e) {
            return false;
        }
    }
}
