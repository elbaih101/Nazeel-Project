package alia.nazeel.templates;
import alia.nazeel.pojos.User;
import io.cucumber.testng.AbstractTestNGCucumberTests;

public class BaseTestNGCucumberRunner extends AbstractTestNGCucumberTests {

    protected static final ThreadLocal<User> user = new ThreadLocal<>();

    public static void setUser(User value) {
        user.set(value);
    }

    public static User getUSer() {
        return user.get();
    }
}