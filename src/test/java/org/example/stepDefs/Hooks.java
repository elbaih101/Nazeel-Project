package org.example.stepDefs;

//import com.microsoft.edge.seleniumtools.EdgeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.example.TestData;
import org.example.Utils;
import org.example.enums.Driver_Mode;
import org.example.enums.Drivers;
import org.example.pages.P01_LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import java.time.Duration;


public class Hooks {
    public static WebDriver driver;
    private static Scenario scenario;
    public static DevTools devTools;

    @Before
    public static void start(Scenario scenario) {
        Hooks.scenario = scenario;

        driver = Utils.setDriver(Drivers.Chrome, Driver_Mode.UI);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get(TestData.stageUrl);

    }


    @After
    public static void end() throws Exception {
        Utils.screenShotOnFailure(scenario, driver);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


    public static void endUserLogin(String username, String password, String acc) {
        //initiating Waits and Pages
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        //logging in
        loginPage.usernameField.sendKeys(username);
        loginPage.passwordField.sendKeys(password);
        loginPage.accField.sendKeys(acc);
        loginPage.loginButton.click();


    }

    public static void superUserLogin(String username, String password) {

        //initiating Waits and Pages
        P01_LoginPage loginPage = new P01_LoginPage(driver);

        //logging in
        loginPage.usernameField.sendKeys(username);
        loginPage.passwordField.sendKeys(password);
        loginPage.loginButton.click();


    }
}
