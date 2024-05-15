package org.example.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P17_CashDrawerPage {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;



    public P17_CashDrawerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//div[contains(@class,\"popup__btn--purple\")]")
    WebElement moreActionsButton;

    public WebElement dropCashButton() {
        wait.until(ExpectedConditions.elementToBeClickable(moreActionsButton));
        moreActionsButton.click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),\"Drop Cash\")]"))));
        return driver.findElement(By.xpath("//span[contains(text(),\"Drop Cash\")]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement popup;

    public WebElement dateto() {
        return popup.findElement(By.xpath(".//kendo-datepicker//input"));
    }

    public WebElement checkButton() {
        return popup.findElement(By.xpath(".//button[@type=\"submit\"]"));
    }

    public WebElement customAmountRadioButton() {
        return popup.findElement(By.xpath(".//label[@id=\"customAmount\"]"));
    }

    public WebElement customAmountField() {
        return popup.findElement(By.xpath(".//input[@name=\"amount\"]"));
    }

    public WebElement saveButton() {
        return popup.findElement(By.xpath(".//td[@data-kendo-grid-column-index=\"4\"]//button[1]"));
    }

    public WebElement nextButton() {
        return popup.findElement(By.xpath(".//button[contains(text(),\"Next\")]"));
    }




}
