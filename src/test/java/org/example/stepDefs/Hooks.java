package org.example.stepDefs;

//import com.microsoft.edge.seleniumtools.EdgeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.TestData;
import org.example.Utils;
import org.example.pages.P01_LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class Hooks {
    public static WebDriver driver;
    private static Scenario scenario;

    @Before
    public static void start(Scenario scenario) {
        Hooks.scenario = scenario;
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(TestData.stageUrl);
        //EdgeOptions options =new EdgeOptions();

        //options.setExperimentalOption("prefs", new String[]{"download.default_directory", "download_path"});

        //FixMe : printing and  download configurations

//        //printer config
//        options.addArguments("--kiosk-printing");
//        //download config    // relates to this import ::   import com.microsoft.edge.seleniumtools.EdgeOptions;
//        HashMap<String, Object> edgePrefs= new HashMap<String, Object>();
//        edgePrefs.put("download.default_directory", "F:\\java maven projects\\Nazeel-Project\\src\\main\\resources\\downloaded");
//        options.setExperimentalOption("prefs", edgePrefs);

//        options.addArguments("print.printer_Microsoft_Print_to_PDF.print_to_filename", "F:\java maven projects\Nazeel-Project\src\main\resources\downloaded");

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
