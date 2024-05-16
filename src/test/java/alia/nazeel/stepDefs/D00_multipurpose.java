package alia.nazeel.stepDefs;

import io.cucumber.java.en.And;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D00_multipurpose {

    WebDriver driver = DriverManager.getDriver();

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P00_multiPurposes multiPurposes=new P00_multiPurposes(driver);


    @And("Close the open popup")
    public void closeTheOpenPopup() {
        WebElement closeButton= multiPurposes.closeButton();
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        closeButton.click();
    }


}
