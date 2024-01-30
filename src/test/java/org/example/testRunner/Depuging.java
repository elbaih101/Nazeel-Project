package org.example.testRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.TestData;
import org.example.Utils;
import org.example.pages.P17_CashDrawerPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.vouchersPages.P10_VouchersPage;
import org.example.pages.vouchersPages.P16_VouchersPopUp;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Map;

public class Depuging {


    public static void main(String[] args) {

        EdgeDriver driver;
       //  initiateDepuggingBrowser();
        driver = attatchDepugger();
        WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(3));
      //  driver.get(TestData.baseUrl);
        WebElement closeButton=new  P00_multiPurposes(driver).closeButton();
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        closeButton.click();

    }








    public static EdgeDriver attatchDepugger() {
        // set the driver path- You can also use WebDriverManager for drivers
        WebDriverManager.edgedriver().setup();

// Create object of ChromeOptions Class

        EdgeOptions opt = new EdgeOptions();

// pass the debuggerAddress and pass the port along with host. Since I am running test on local so using localhost
        opt.setExperimentalOption("debuggerAddress", "localhost:59587");

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
