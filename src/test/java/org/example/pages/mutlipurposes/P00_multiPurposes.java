package org.example.pages.mutlipurposes;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P00_multiPurposes {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;
    public P00_multiPurposes() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P00_multiPurposes(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }




    @FindBy(xpath = "//div[contains(@class,\"p-tooltip-bottom\")]/div[@class=\"p-tooltip-text\"]")
    public WebElement bottomToolTip;
    @FindBy(xpath = "//div[@class=\"loader-line\"]")
    public WebElement loadingAnimation;



    public void waitLoading(){
        wait.withTimeout(Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(loadingAnimation));
    }
}
