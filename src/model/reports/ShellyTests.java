package model.reports;
import model.auth.Role;
import model.auth.State;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import model.auth.Role;
import model.auth.State;
import model.auth.User;
import org.junit.Test;

import java.util.Date;

public class ShellyTests {
    @Test
    public void properSourceReport() throws Exception{
        Role myRole = Role.USER;
        State myState = State.GEORGIA;
        User myUser = new User("ShellyS", "Password", "Shelly", "Saville",
                myRole, "s@gmail.com", "Hello", "100 Atlanta St", "Atlanta",
                myState, 30318);
        Location myLocation = new Location(33.7490, 84.3880);
        WaterType myWaterType = WaterType.BOTTLED;
        WaterCondition myWaterCondition = WaterCondition.POTABLE;

        SourceReport mySourceReport = new SourceReport(myUser, myLocation,
                myWaterType, myWaterCondition);
        // assert statements

        assertEquals("Report user must be user", myUser, mySourceReport.getReportedUser());
        assertEquals("Report location must be location", myLocation, mySourceReport.getLocation());
        assertEquals("Report water condition must be water condition", myWaterCondition, mySourceReport.getWaterCondition());
        assertEquals("Report water type must be water type", myWaterType, mySourceReport.getWaterType());
    }

    @Test(expected=EmptyRequiredFieldException.class)
    public void testNullWaterTypeExcetion() throws Exception{
        Role myRole = Role.USER;
        State myState = State.GEORGIA;
        User myUser = new User("ShellyS", "Password", "Shelly", "Saville",
                myRole, "s@gmail.com", "Hello", "100 Atlanta St", "Atlanta",
                myState, 30318);
        Location myLocation = new Location(33.7490, 84.3880);
        WaterCondition myWaterCondition = WaterCondition.POTABLE;

        SourceReport mySourceReport = new SourceReport(myUser, myLocation,
                null, myWaterCondition);
    }

    @Test(expected=EmptyRequiredFieldException.class)
    public void testNullWaterConditionExcetion() throws Exception{
        Role myRole = Role.USER;
        State myState = State.GEORGIA;
        User myUser = new User("ShellyS", "Password", "Shelly", "Saville",
                myRole, "s@gmail.com", "Hello", "100 Atlanta St", "Atlanta",
                myState, 30318);
        Location myLocation = new Location(33.7490, 84.3880);
        WaterType myWaterType = WaterType.BOTTLED;

        SourceReport mySourceReport = new SourceReport(myUser, myLocation,
                myWaterType, null);
    }
}