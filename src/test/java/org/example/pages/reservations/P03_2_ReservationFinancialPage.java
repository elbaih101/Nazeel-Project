package org.example.pages.reservations;

import org.apache.commons.lang3.StringUtils;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@SuppressWarnings("unused")
public class P03_2_ReservationFinancialPage {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

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

    public Double reservationBalance (){
         WebElement balance = financialSummary.findElement(By.xpath(".//div[contains(text(),\"Balance\")]/following-sibling::div/span"));
        return Double.parseDouble(balance.getText().replaceFirst("-","").trim());
    }
    public Double reservationRent(){
        WebElement rent = financialSummary.findElement(By.xpath(".//div[contains(text(),\"Rent\")]/following-sibling::div"));
        return Double.parseDouble((rent.getText()));
    }
    public Double reservationExtras(){
        WebElement rent = financialSummary.findElement(By.xpath(".//div[contains(text(),\"Extras\")]/following-sibling::div"));
        return Double.parseDouble((rent.getText()));
    }
    public Double reservationPenalties(){
        WebElement rent = financialSummary.findElement(By.xpath(".//div[contains(text(),\"Penalties\")]/following-sibling::div"));
        return Double.parseDouble((rent.getText()));
    }
    public Double reservationDiscount(){
        WebElement discount = financialSummary.findElement(By.xpath(".//div[(text()=\" Discount \")]/following-sibling::div"));
        return Double.parseDouble((discount.getText()));
    }
    public Double reservationSubTotal(){
        WebElement rent = financialSummary.findElement(By.xpath(".//div[(text()=\"Sub-Total\")]/following-sibling::div"));
        return Double.parseDouble((rent.getText()));
    }
    public Double reservationTaxes(){
        WebElement taxAmount = financialSummary.findElement(By.xpath(".//div[contains(text(),\"Taxes\")]/following-sibling::div"));
        return Double.parseDouble((taxAmount.getText()));
    }
    public Double reservationTotal(){
        WebElement total = financialSummary.findElement(By.xpath(".//div[(text()=\" Total\")]/following-sibling::div"));
        return Double.parseDouble((total.getText()));
    }
    public boolean isTaxInclusive(){
        return !financialSummary.findElements(By.xpath(".//div[contains(text(),\"Taxes\")]/span")).isEmpty();
    }
    @FindBy(name="discount-amount")
    public WebElement discountAmountField;
    @FindBy(name="discount-method")
    public WebElement discountMethodComboBox;

     public List<WebElement>discountsList(){
         return new P00_multiPurposes(driver).getListItems(discountMethodComboBox);
     }
     @FindBy(xpath = "//div[contains(@class,\"table__tag--red\")]")
     public WebElement tableTagAlert;

    @FindBy(xpath = "//app-reservation-financial-payment-step//div[@class=\"financial-summary\"]")
    public WebElement financialSummary;


}
