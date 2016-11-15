package test;

import model.auth.Role;
import model.auth.User;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Nkosi Kee on 11/14/2016.
 */
public class NkosiTest {

    /**
     * Test whether the set last name method works
     * @throws AuthenticationException if the user takes in invalid data
     * @throws EmptyRequiredFieldException if the required field is empty
     */
    @Test
    public void properUserSetLastNameSet() throws AuthenticationException, EmptyRequiredFieldException {
        User user = new User("Km22","3040322","Nkosi","Kee",Role.USER,"juan@gogo.com");
        String bart = "Bart"
        user.setLastName(bart);
        Boolean isBart = bart.equals(user.getLastName());
        assert(isBart);
    }

    /**
     * Test whether the set Last name method throws an EmptyRequiredFieldException
     * if the required field is empty
     * @throws AuthenticationException if the user takes in invalid data
     * @throws EmptyRequiredFieldException if the required field is empty
     */
    @Test (expected = EmptyRequiredFieldException.class)
    public void testEmptyLastName() throws AuthenticationException, EmptyRequiredFieldException {
        User user = new User("Km22","3040322","Nkosi","Kee",Role.USER,"juan@gogo.com");
        user.setLastName(null);
    }
}
