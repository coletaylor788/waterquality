package test;

import com.google.common.util.concurrent.SettableFuture;
import controller.MainController;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.auth.Role;
import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidPasswordException;
import model.auth.exceptions.InvalidUsernameException;
import model.exceptions.EmptyRequiredFieldException;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import org.loadui.testfx.utils.UserInputDetector;

import java.util.concurrent.TimeUnit;

import static org.junit.Assume.assumeTrue;


/**
 * @author Klest Sula
 */
public class KlestTest extends GuiTest {

    private static final SettableFuture<Stage> stageFuture = SettableFuture.create();

    protected static class FXTestApp extends MainController {
        public FXTestApp() {
            super();
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            super.start(primaryStage);
            stageFuture.set(primaryStage);
        }
    }

    @Before
    @Override
    public void setupStage() throws Throwable {
        assumeTrue(!UserInputDetector.instance.hasDetectedUserInput());
        FXTestUtils.launchApp(FXTestApp.class);
        try {
            stage = targetWindow(stageFuture.get(25, TimeUnit.SECONDS));
            FXTestUtils.bringToFront(stage);
        } catch (Exception e) {
            throw new RuntimeException("Unable to show stage", e);
        }
    }

    @Override
    protected Parent getRootNode() {
        return stage.getScene().getRoot();
    }


    /**
     * Tests the login method. First ensures login fails for invalid username, then invalid
     * password then ensures its success for valid username and password
     *
     * @throws EmptyRequiredFieldException when a field is left empty
     * @throws AuthenticationException when login fails for a reason other than invalid username or password
     */
    @Test
    public void LoginTest() throws EmptyRequiredFieldException, AuthenticationException {
        MainController.getInstance().getFacade().getUsers().addUser("user", "password", "first", "last", Role.USER, "c@c.com");
        try {
            assert (!loginSuccessful("a", "password"));
        } catch (AuthenticationException e) {
            assert(false);
        }
        try {
            assert(!loginSuccessful("user", "a"));
        } catch (AuthenticationException e) {
            assert(false);
        }
        assert(loginSuccessful("user", "password"));
    }

    /**
     * checks if the user was successfully logged in successfully
     *
     * @param username username of the user to be logged in
     * @param password password of the user to be logged in
     * @return a boolean, true if the user was logged in successfully and false otherwise
     * @throws AuthenticationException if the user is not logged in for a reason other
     *                                 than an invalid username or password
     */
    private boolean loginSuccessful(String username, String password) throws AuthenticationException {
        if (username == null || password == null) {
            return false;
        }
        try {
            MainController.getInstance().getFacade().getUsers().login(username, password);
            return true;
        } catch (InvalidUsernameException e) {
            return false;
        } catch (InvalidPasswordException e) {
            return false;
        }
    }

}
