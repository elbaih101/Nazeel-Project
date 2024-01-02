package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unused")
public class P10_VouchersPage {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;
    public P10_VouchersPage() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P10_VouchersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }
@FindBy(xpath = "//div[contains(@class,\"button--green\")]/button[contains(@class,\"arrow\")]")
    WebElement moreAddOptionsButton;

    @FindBy(xpath = "//div[contains(text(),\"Digital Payment\")]")
    WebElement digitalPaymentOption;

public WebElement digialPaymentButton(){
    wait.until(ExpectedConditions.elementToBeClickable(moreAddOptionsButton));
    try {
        Thread.sleep(300);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    moreAddOptionsButton.click();
    return digitalPaymentOption;
}
}
