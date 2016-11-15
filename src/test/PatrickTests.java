package test;

import model.auth.Role;
import model.auth.State;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;
import model.reports.Location;
import model.reports.OverallCondition;
import org.junit.Test;
import model.auth.User;
import model.reports.PurityReport;

import java.security.AccessControlException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Patrick on 11/10/2016.
 */
public class PatrickTests {

    /**
     * Tests the Purity report constructor
     * @throws EmptyRequiredFieldException
     * @throws AuthenticationException
     */
    @Test
    public void properPurityReport() throws EmptyRequiredFieldException, AuthenticationException {
        Role role = Role.WORKER;
        State state = State.GEORGIA;
        User user = new User("PatrickB", "Password", "Patrick", "Black",
                role, "p@gmail.com", "Hello", "100 Atlanta St", "Atlanta",
                state, 30318);

        OverallCondition condition = OverallCondition.SAFE;
        Location location = new Location(33.7490, 84.3880);
        double virus = 0d;
        double contaminant = 0d;

        PurityReport purityReport = new PurityReport(user, condition, location, virus, contaminant);

        boolean virusTrue = false;
        boolean contaminantTrue = false;
        if (virus == purityReport.getVirusPPM()) {
            virusTrue = true;
        }
        if (contaminant == purityReport.getContaminantPPM()) {
            contaminantTrue = true;
        }

        //asserts statement
        assertEquals("Report user must be user", user, purityReport.getReportedWorker());
        assertEquals("Report location must be location", location, purityReport.getLocation());
        assertEquals("Report overall condition must be overall condition", condition, purityReport.getOverallCondition());
        assertEquals("Report virus ppm must be virus ppm", true, virusTrue);
        assertEquals("Report contaminant ppm must be contaminant ppm", true, contaminantTrue);

    }

    /**
     * Tests if user tries to create report
     * @throws EmptyRequiredFieldException
     * @throws AuthenticationException
     */
    @Test (expected = AccessControlException.class)
    public void testAuthentication() throws EmptyRequiredFieldException, AuthenticationException {
        Role role = Role.USER;
        State state = State.GEORGIA;
        User user = new User("PatrickB", "Password", "Patrick", "Black",
                role, "p@gmail.com", "Hello", "100 Atlanta St", "Atlanta",
                state, 30318);

        OverallCondition condition = OverallCondition.SAFE;
        Location location = new Location(33.7490, 84.3880);

        new PurityReport(user, condition, location, 0d, 0d);
    }

    /**
     * Tests if location is empty
     * @throws AuthenticationException
     * @throws EmptyRequiredFieldException
     */
    @Test (expected = EmptyRequiredFieldException.class)
    public void testEmptyLocation() throws AuthenticationException, EmptyRequiredFieldException {
        Role role = Role.WORKER;
        State state = State.GEORGIA;
        User user = new User("PatrickB", "Password", "Patrick", "Black",
                role, "p@gmail.com", "Hello", "100 Atlanta St", "Atlanta",
                state, 30318);

        OverallCondition condition = OverallCondition.SAFE;
        new PurityReport(user, condition, null, 0d, 0d);
    }

    /**
     * Tests if overall condition is empty
     * @throws AuthenticationException
     * @throws EmptyRequiredFieldException
     */
    @Test (expected = EmptyRequiredFieldException.class)
    public void testEmptyOverallCondition() throws AuthenticationException, EmptyRequiredFieldException {
        Role role = Role.WORKER;
        State state = State.GEORGIA;
        User user = new User("PatrickB", "Password", "Patrick", "Black",
                role, "p@gmail.com", "Hello", "100 Atlanta St", "Atlanta",
                state, 30318);

        Location location = new Location(33.7490, 84.3880);

        new PurityReport(user, null, location, 0d, 0d);
    }
}
