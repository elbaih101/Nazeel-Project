package org.example.pages.vouchersPages;

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

public class P16_VouchersPopUp {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P16_VouchersPopUp() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P16_VouchersPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


//todo :check the  fontcolor is  #979a97!important and for received from #636562  background-color #fafafa
    //date picker ,time, payment method and amount go above input to span to get background-color color


    ////String expectedColor ="#979a97!important";
    //        String actualColor = (flash.getCssValue("background-color"));
    //        actualColor = Color.fromString(actualColor).asHex();


    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;


    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement voucherPopUp;
/// header ///
    public WebElement voucherNo() {
        return voucherPopUp.findElement(By.xpath(".//div[contains(text(),\"Voucher No\")]/following-sibling::div[1]"));
    }

    public WebElement reservationNo() {
        return voucherPopUp.findElement(By.xpath(".//div[contains(text(),\"Res. No\")]/following-sibling::div[1]"));
    }

    public WebElement draftCollectedAmount() {
        return voucherPopUp.findElement(By.xpath(".//div[contains(text(),\"Collected\")]/following-sibling::div[1]"));
    }

    public WebElement guestName() {
        return voucherPopUp.findElement(By.xpath("//div[contains(text(),\"Guest\")]/following-sibling::div[1]"));
    }
    /////// tabs //////
    public WebElement receiptTab(){return voucherPopUp.findElement(By.xpath("(.//kendo-tabstrip//li//span[contains(text(),\"Receipt\")])[1]"));}
    public WebElement securityDepositetTab(){return voucherPopUp.findElement(By.xpath("(.//kendo-tabstrip//li//span[contains(text(),\"Receipt\")])[2]"));}
    public WebElement draftTab(){return voucherPopUp.findElement(By.xpath(".//kendo-tabstrip//li//span[contains(text(),\"Draft\")]"));}
    public WebElement refundTab(){return voucherPopUp.findElement(By.xpath("(.//kendo-tabstrip//li//span[contains(text(),\"Refund\")])[1]"));}
    public WebElement securityDeposuteRefundTab(){return voucherPopUp.findElement(By.xpath("(.//kendo-tabstrip//li//span[contains(text(),\"Refund\")])[2]"));}
////// bdy ////////
    public WebElement dateField() {
        return voucherPopUp.findElement(By.xpath(".//kendo-datepicker//input"));
    }

    public WebElement draftMaturityDate() {
        return voucherPopUp.findElement(By.xpath("(.//kendo-datepicker//input)[2]"));
    }

    public WebElement timeField() {
        return voucherPopUp.findElement(By.xpath(".//kendo-timepicker//input"));
    }
    public WebElement selctGuestButton() {
        return voucherPopUp.findElement(By.xpath(".//div[@class=\"form__action-icon\"]"));
    }
    public WebElement guestField(String voucherType) {
        if (voucherType.equalsIgnoreCase("receipt")) {
            return voucherPopUp.findElement(By.xpath(".//label[contains(text(),\"Received From\")]/following-sibling::div//input"));
        } else if (voucherType.equalsIgnoreCase("refund")) {
            return voucherPopUp.findElement(By.xpath(".//label[contains(text(),\"Paid to\")]/following-sibling::div//input"));
        } else {
            return voucherPopUp.findElement(By.xpath(".//label[contains(text(),\"Guest\")]/../following-sibling::input"));
        }

    }
public WebElement paymentMethodField(){return voucherPopUp.findElement(By.xpath(".//label[contains(text(),\"Payment\")]/following-sibling::kendo-combobox//input"));}
    public List<WebElement> paymentMethos() {
        voucherPopUp.findElement(By.xpath(".//label[contains(text(),\"Payment\")]/following-sibling::kendo-combobox//span/span/span")).click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    public WebElement amountField() {
        return voucherPopUp.findElement(By.xpath(".//kendo-numerictextbox//input"));

    }
    public List<WebElement> banks(){
        voucherPopUp.findElement(By.xpath(".//label[contains(text(),\"Receiving Bank\")]/../following-sibling::kendo-combobox//span/span/span")).click();
        return genralListBox.findElements(By.xpath("\"//li[@role=\\\"option\\\"]\""));
    }

    public WebElement submitButton() {
        return voucherPopUp.findElement(By.xpath(".//button[@class=\"n-button n-button--primary\"]"));
    }

    public WebElement discardButton() {
        return voucherPopUp.findElement(By.xpath(".//button[contains(@class,\"n-button--danger-border\")]"));
    }



}
