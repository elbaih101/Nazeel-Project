package org.example.testRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Map;

public class Depuging {


    public static void main(String[] args) {
        EdgeDriver driver;
     //   initiateDepuggingBrowser();
        driver = attatchDepugger();
        JavascriptExecutor js = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
       //  driver.get(TestData.stageUrl);
        //new D01_MakingReservation().chooseReservationStatusAs("out");

    }


    public static EdgeDriver attatchDepugger() {
        // set the driver path- You can also use WebDriverManager for drivers
        WebDriverManager.edgedriver().setup();

// Create object of ChromeOptions Class

        EdgeOptions opt = new EdgeOptions();

// pass the debuggerAddress and pass the port along with host. Since I am running test on local so using localhost
        opt.setExperimentalOption("debuggerAddress", "localhost:64705");

// pass ChromeOptions object to ChromeDriver constructor
        EdgeDriver driver = new EdgeDriver(opt);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        return driver;
    }

    public static void initiateDepuggingBrowser() {
        System.out.println("Hello world!");

        // set the driver path- You can also use WebDriverManager for drivers
        WebDriverManager.edgedriver().setup();


// Create object of ChromeDriver class
        EdgeDriver driver = new EdgeDriver();

// getCapabilities will return all browser capabilities
        Capabilities cap = driver.getCapabilities();

// asMap method will return all capability in MAP
        Map<String, Object> myCap = cap.asMap();

// print the map data-
        System.out.println(myCap);
    }
}
