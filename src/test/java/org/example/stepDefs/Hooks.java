package org.example.stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.DriverManager;
import org.example.TestData;
import org.example.Utils;
import org.example.enums.Driver_Mode;
import org.example.enums.Drivers;
import org.example.pages.P01_LoginPage;
import org.example.pojos.User;
import org.example.pojos.UserDataReader;
import org.openqa.selenium.WebDriver;

import java.time.Duration;


public class Hooks {
    public WebDriver driver;
    private Scenario scenario;
    private static int numberofUsers = 0;


    @Before
    public void start(Scenario scenario) {
        this.scenario = scenario;
        DriverManager.initializeDriver(Drivers.Chrome, Driver_Mode.UI);
        this.driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.get(TestData.stageUrl);

    }


    @After
    public void end() throws Exception {
        Utils.screenShotOnFailure(scenario, driver);
        try {
            Thread.sleep(5000);
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
        User user = UserDataReader.getNextUser();
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
