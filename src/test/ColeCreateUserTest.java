package test;

import model.Facade;
import model.auth.Role;
import model.auth.State;
import model.auth.User;
import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.UnableToHashPasswordException;
import model.exceptions.EmptyRequiredFieldException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the creation of a User
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class ColeCreateUserTest {

    @Before
    public void setup() {

    }

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

    @Test(expected = EmptyRequiredFieldException.class)
    public void testEmptyPassword() throws EmptyRequiredFieldException {
        User u = null;
        try {
            u = new User("user", "", "first", "last", Role.USER, "c@c.com", "title", "address",
                    "city", State.ALABAMA, 12345);
        } catch (AuthenticationException e) {
            assert(false);
        }
    }

    @Test(expected = EmptyRequiredFieldException.class)
    public void testEmptyFirstName() throws EmptyRequiredFieldException {
        User u = null;
        try {
            u = new User("user", "password", "", "last", Role.USER, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException e) {
            assert(false);
        }
    }

    @Test(expected = EmptyRequiredFieldException.class)
    public void testEmptyLastName() throws EmptyRequiredFieldException {
        User u = null;
        try {
            u = new User("user", "password", "first", "", Role.USER, "c@c.com", "title",
                    "address", "city", State.ALABAMA, 12345);
        } catch (AuthenticationException e) {
            assert(false);
        }
    }

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
