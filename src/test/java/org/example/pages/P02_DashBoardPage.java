package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("ALL")
public class P02_DashBoardPage {
    public P02_DashBoardPage() {
        PageFactory.initElements(Hooks.driver, this);

    }

    public P02_DashBoardPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class=\"n-topbar__logo ng-star-inserted\"]")
    public WebElement homePageLink;
    @FindBy(xpath = "//a[@href=\"/reservations\"]")
    public WebElement ReservationsLink;
    @FindBy(xpath = "//Button[contains(text(),\"Close\")]")
    public WebElement closeAnnouncementButton;
    @FindBy(xpath = "//a[@href=\"/setup\"]")
    public WebElement setupPageLink;

    @FindBy(xpath = "//button[contains(text(),\" Agree on contract terms \")]")
    public WebElement contractAgreementButton;

    @FindBy(xpath = "//a[@href=\"/master-data\"]")
    public List<WebElement> masterDataLink;
    @FindBy(xpath = "//span[contains(text(),\"Vouchers\")]")
    public WebElement vouchersDropList;

    @FindBy(xpath = "//a[@href=\"/financial/receipt-vouchers\"]")
    public WebElement receiptsLink;
    @FindBy(xpath = "//a[@href=\"/financial/payment-vouchers\"]")
    public WebElement paymentsLink;
    @FindBy(xpath = "//a[@href=\"/financial/draft-vouchers\"]")
    public WebElement draftsLink;

    @FindBy(xpath = "//a[@href=\"/invoices\"]")
    public WebElement invoicesLink;
    @FindBy(xpath = "//a[@href=\"/invoices/manage-credit-invoices\"]")
    public WebElement creditNotesLink;

    @FindBy(xpath = "//a[@href=\"/vouchers/drop-cash\"]")
    public WebElement dropCashVoucherssLink;

    @FindBy(xpath = "//span[contains(text(),\"SMS\")]")
    public WebElement smsDropList;
    @FindBy(xpath = "//a[@href=\"/notifications/sms-logs\"]")
    public WebElement smsLogPage;
    @FindBy(xpath = "//a[@href=\"/financial/cash-drawer-balance\"]")
    public WebElement cashDrawer;

    @FindBy(xpath = "//a[@href=\"/reports/menu\"]")
    public WebElement reportsPageLink;
}
