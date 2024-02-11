package org.example.pages.reservations;

import org.apache.commons.lang.StringUtils;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unused")
public class P03_2_ReservationFinancialPage {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P03_2_ReservationFinancialPage() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P03_2_ReservationFinancialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//h2")
    WebElement reservationNumberHeader;
    @FindBy(xpath = "//th/div/button[contains(@class,\"arrow\")]")
    WebElement moreAddOptionsButton;
    @FindBy(xpath = "//button[contains(text(),\"Add\")]")
    public WebElement addVoucherButton;
    @FindBy(xpath = "//kendo-tabstrip//li//div[contains(text(),\"Receipt\")]")
    public WebElement receiptsTap;
    @FindBy(xpath = "//kendo-tabstrip//li//div[contains(text(),\"Refund\")]")
    public WebElement refundsTap;

    @FindBy(xpath = "//kendo-tabstrip//li//div[contains(text(),\"Invoices\")]")
    public WebElement invoicesTap;


    @FindBy(xpath = "//div[contains(text(),\"Digital Payment\")]")
    WebElement digitalPaymentOption;

    public WebElement digialPaymentButton() {
        moreAddOptionsButton.click();
        return digitalPaymentOption;
    }

    public int reservationNum() {
        String reservationNumber = StringUtils.substringAfter(reservationNumberHeader.getText().trim(), "Reservation ").trim();
        try {
            return Integer.parseInt(reservationNumber);
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string");
        }
        System.out.println("Continuing execution...");
        return 0;
    }

    public double reservationBalance (){
         WebElement balance = driver.findElement(By.xpath("//div[@class=\"financial-summary\"]//div[contains(text(),\"Balance\")]/following-sibling::div/span"));
        return Double.parseDouble(balance.getText().replaceFirst("-","").trim());
    }
    @FindBy(xpath = "//div[@class=\"financial-summary\"]//div[contains(text(),\"Balance\")]/following-sibling::div/span")
    public WebElement balance;


}
