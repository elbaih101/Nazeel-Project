package alia.nazeel.pages.vouchersPages;


import alia.nazeel.enums.Vouchers;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;

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



    public P10_VouchersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//*[contains(text(),\"New\")]")
    public WebElement newVoucherButton;
    @FindBy(xpath = "//div[contains(@class,\"button--green\")]/button[contains(@class,\"arrow\")]")
    WebElement moreAddOptionsButton;

    @FindBy(xpath = "//div[contains(text(),\"Digital Payment\")]")
    WebElement digitalPaymentOption;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> vouchersNums;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> paymentMethods;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"8\"]")
    public List<WebElement> receitRelatedDrafts;


    public WebElement digialPaymentButton() {
        wait.until(ExpectedConditions.elementToBeClickable(moreAddOptionsButton));
        new P00_multiPurposes(driver).waitLoading();
        moreAddOptionsButton.click();
        return digitalPaymentOption;
    }

    //Grid //
public WebElement voucherOwner (WebElement voucher,String voucherType){
    WebElement owner;
    if (voucherType.equalsIgnoreCase(Vouchers.Draft.toString())) {
        owner = voucher.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"5\"]"));
    } else  {
        owner = voucher.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"4\"]"));
    }
    return owner;
}
    //FIXME : dont forget the error
    public List<WebElement> moreActions(WebElement voucherAmount, String voucherType) {
        WebElement moreMenu = null;
        if (voucherType.equalsIgnoreCase(Vouchers.Draft.toString())) {
            moreMenu = voucherAmount.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"11\"]//div/div"));
        } else if (voucherType.equalsIgnoreCase(Vouchers.Receipt.toString()) || voucherType.equalsIgnoreCase(Vouchers.Expenses.toString()) || voucherType.equalsIgnoreCase(Vouchers.Refund.toString()) || voucherType.equalsIgnoreCase(Vouchers.SAReceipt.toString()) || voucherType.equalsIgnoreCase(Vouchers.SD.toString()) || voucherType.equalsIgnoreCase(Vouchers.SDRefund.toString()) || voucherType.equalsIgnoreCase(Vouchers.GenReceipt.toString())) {
            moreMenu = voucherAmount.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"10\"]//div/div"));
        }
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu));
        moreMenu.click();
        return moreMenu.findElements(By.xpath("//div[@class=\"popup__content\"]/div"));
    }

    public List<WebElement> draftsRemainigAmounts() {
        wait.until(ExpectedConditions.urlContains("draft-vouchers"));
        return driver.findElements(By.xpath("//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement editButton(WebElement voucherAmount, String voucherType) {
        WebElement button = null;

        if (voucherType.equalsIgnoreCase(Vouchers.Draft.toString())) {
            button = voucherAmount.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"11\"]//div/button[1]"));
        } else if (voucherType.equalsIgnoreCase(Vouchers.Receipt.toString()) || voucherType.equalsIgnoreCase(Vouchers.Expenses.toString()) || voucherType.equalsIgnoreCase(Vouchers.Refund.toString()) || voucherType.equalsIgnoreCase(Vouchers.SAReceipt.toString()) || voucherType.equalsIgnoreCase(Vouchers.SD.toString()) || voucherType.equalsIgnoreCase(Vouchers.SDRefund.toString()) || voucherType.equalsIgnoreCase(Vouchers.GenReceipt.toString())) {
            button = voucherAmount.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"10\"]//div/button[1]"));
        }
        if (button==null) {
            throw new RuntimeException("the edit button wasn't retreaved");
        }
        return button;
    }


}