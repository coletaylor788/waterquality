package test;

import model.auth.Role;
import model.auth.User;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import model.exceptions.EmptyRequiredFieldException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Created by Nkosi Kee on 11/14/2016.
 */
public class NkosiTest {

    @Test
    public void properUserSetLastNameSet() throws AuthenticationException, EmptyRequiredFieldException {
        User user = new User("Km22","3040322","Nkosi","Kee",Role.USER,"juan@gogo.com");
        user.setLastName("Bart");
        assertEqauls("Last Name must be Bart", "Bart", user.getLastName());
    }

    @Test (expected = EmptyRequiredFieldException.class)
    public void testEmptyLastName() throws AuthenticationException, EmptyRequiredFieldException {
        User user = new User("Km22","3040322","Nkosi","Kee",Role.USER,"juan@gogo.com");
        user.setLastName("Bart");
    }
}
