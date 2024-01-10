package org.example.pages;

import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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

    public WebElement digialPaymentButton() {
        wait.until(ExpectedConditions.elementToBeClickable(moreAddOptionsButton));
        new P00_multiPurposes(driver).waitLoading();
        moreAddOptionsButton.click();
        return digitalPaymentOption;
    }

    //Grid //

//todo : dont forget the error
    public List<WebElement> moreActions(WebElement draft_Amount) {
        WebElement moreMenu= draft_Amount.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"11\"]//div/div"));
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu));
        moreMenu.click();
        return moreMenu.findElements(By.xpath("//div[@class=\"popup__content\"]/div"));
    }
    public List<WebElement> draftsRemainigAmounts(){
        wait.until(ExpectedConditions.urlContains("draft-vouchers"));
       List<WebElement>rem = driver.findElements(By.xpath("//td[@data-kendo-grid-column-index=\"3\"]"));
        return rem;
    }

}
