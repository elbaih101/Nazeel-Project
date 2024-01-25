package org.example.testRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;
import java.util.Map;

public class Depuging {
    public static void main(String[] args) {

        // set the driver path- You can also use WebDriverManager for drivers
        WebDriverManager.edgedriver().setup();

// Create object of ChromeOptions Class

        EdgeOptions opt = new EdgeOptions();

// pass the debuggerAddress and pass the port along with host. Since I am running test on local so using localhost
        opt.setExperimentalOption("debuggerAddress", "localhost:60560");

// pass ChromeOptions object to ChromeDriver constructor
        EdgeDriver driver = new EdgeDriver(opt);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("http://staging.nazeel.net:9002/");
// now you can use now existing Browser

//        new D08_Vouchers(driver).clickOnTheAddVoucherButton();
//        new D08_Vouchers(driver).selectPaymentMethodAndEnterAmount("Cash", "50");

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
