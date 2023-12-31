package org.example.stepDefs;

//import com.microsoft.edge.seleniumtools.EdgeOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.TestData;
import org.example.pages.P01_LoginPage;
import org.example.pages.P02_DashBoardPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;

public class Hooks {
    public static WebDriver driver;

    @Before
    public static void start() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(TestData.baseUrl);
//        EdgeOptions options =new EdgeOptions();
       //TODO : printing and  download configurations

//        //printer config
//        options.addArguments("--kiosk-printing");
//        //download config    // relates to this import ::   import com.microsoft.edge.seleniumtools.EdgeOptions;
//        HashMap<String, Object> edgePrefs= new HashMap<String, Object>();
//        edgePrefs.put("download.default_directory", "F:\\java maven projects\\Nazeel-Project\\src\\main\\resources\\downloaded");
//        options.setExperimentalOption("prefs", edgePrefs);

//        options.addArguments("print.printer_Microsoft_Print_to_PDF.print_to_filename", "F:\java maven projects\Nazeel-Project\src\main\resources\downloaded");

    }


    @After
    public static void end() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


    public static void endUserLogin(String username, String password, String acc) {
        //initiating Waits and Pages
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        P02_DashBoardPage homePage = new P02_DashBoardPage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //logging in
        loginPage.usernameField.sendKeys(username);
        loginPage.passwordField.sendKeys(password);
        loginPage.accField.sendKeys(acc);
        loginPage.loginButton.click();



    }

    public static void superUserLogin(String username, String password) {

        //initiating Waits and Pages
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        P02_DashBoardPage homePage = new P02_DashBoardPage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //logging in
        loginPage.usernameField.sendKeys(username);
        loginPage.passwordField.sendKeys(password);
        loginPage.loginButton.click();


    }
}
