package alia.nazeel.pages.reservations;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.templates.BasePage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class P03_2_ReservationFinancialPage extends BasePage
{
    public P03_2_ReservationFinancialPage(WebDriver driver) {
      super(driver);
    }

    @FindBy(xpath = "//h2")
    WebElement reservationNumberHeader;
    @FindBy(xpath = "//receipt-vouchers//button[contains(@class,\"arrow\")]")
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
    public KendoComboBox discountMethodComboBox;

     @FindBy(xpath = "//div[contains(@class,\"table__tag--red\")]")
     public WebElement tableTagAlert;

    @FindBy(xpath = "//app-reservation-financial-payment-step//div[@class=\"financial-summary\"]")
    public WebElement financialSummary;


}
