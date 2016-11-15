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
 * Created by Klest Sula on 11/14/2016.
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


    @Test
    public void LoginTest() throws EmptyRequiredFieldException, AuthenticationException {
        MainController.getInstance().getFacade().getUsers().addUser("user", "password", "first", "last", Role.USER, "c@c.com");
        try {
            MainController.getInstance().getFacade().getUsers().login("a", "password");
            assert(false);
        } catch (InvalidUsernameException e) {

        } catch (AuthenticationException e) {
            assert(false);
        }
        try {
            MainController.getInstance().getFacade().getUsers().login("user", "a");
            assert(false);
        } catch (InvalidPasswordException e) {

        } catch (AuthenticationException e) {
            assert(false);
        }
        assert(loginSuccessful("user", "password"));
    }

    private boolean loginSuccessful(String username, String password) throws AuthenticationException {
        if (username == null || password == null) {
            return false;
        }
        try {
            MainController.getInstance().getFacade().getUsers().login(username, password);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

}
