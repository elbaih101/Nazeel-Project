package alia.nazeel.stepDefs;

import alia.nazeel.templates.BaseTestNGCucumberRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import alia.nazeel.tools.DriverManager;

import alia.nazeel.tools.Utils;
import alia.nazeel.enums.Driver_Mode;
import alia.nazeel.enums.Drivers;
import alia.nazeel.pages.P01_LoginPage;
import alia.nazeel.pojos.User;
import alia.nazeel.pojos.UserDataReader;
import org.openqa.selenium.WebDriver;

import java.time.Duration;


public class Hooks {
    public WebDriver driver;
    private Scenario scenario;
    public final static String stageUrl = "https://staging.nazeel.net:9002/";



    @Before
    public void start(Scenario scenario) {

        this.scenario = scenario;
        DriverManager.initializeDriver(Drivers.Chrome, Driver_Mode.Headless);
        this.driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(stageUrl);

    }


    @After
    public void end() throws Exception {
        Utils.screenShotOnFailure(scenario, driver);
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


    public static void endUserLogin(WebDriver driver, String username, String password, String acc) {
        //initiating Waits and Pages
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        //logging in
        loginPage.usernameField.sendKeys(username);
        loginPage.passwordField.sendKeys(password);
        loginPage.accField.sendKeys(acc);
        loginPage.loginButton.click();


    }

    public static void superUserLogin(WebDriver driver, String username, String password) {
        User user = BaseTestNGCucumberRunner.getUSer() == null ?UserDataReader.getNextUser():BaseTestNGCucumberRunner.getUSer();

        //initiating Waits and Pages
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        if (user != null) {
            //logging in

            loginPage.usernameField.sendKeys(user.getUserName());
            loginPage.passwordField.sendKeys(user.getPassword());
            loginPage.loginButton.click();
        } else {
            System.out.println("No users available");
        }

    }
}
