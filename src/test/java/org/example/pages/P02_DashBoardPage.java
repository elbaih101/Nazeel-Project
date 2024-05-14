package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("ALL")
public class P02_DashBoardPage {

    public P02_DashBoardPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//div[@class=\"n-topbar__logo ng-star-inserted\"]")
    public WebElement homePageLink;
    @FindBy(xpath = "//a[@href=\"/reservations\"]")
    public WebElement ReservationsLink;
    @FindBy(xpath = "//app-announcement-popup//button")
    public WebElement closeAnnouncementButton;
    @FindBy(xpath = "//app-service-expiration-warning//button[contains(@class,\"danger\")]")
    public WebElement closeSubscriptionsAlertButton;
    @FindBy(xpath = "//a[@href=\"/setup\"]")
    public WebElement setupPageLink;
    @FindBy(className = "n-topbar__logo")
    public WebElement dashBoardLink;
    @FindBy(xpath = "//button[contains(text(),\" Agree on contract terms \")]")
    public WebElement contractAgreementButton;
    @FindBy(xpath = "//div[@class=\"n-topbar__item\"]")
    public List<WebElement> onlineReservationsLink;

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
    @FindBy(xpath = "//span[contains(text(),\"Customers\")]")
    public WebElement customersDropList;
    @FindBy(xpath = "//a[@href=\"/customers/guest-profile\"]")
    public WebElement guestsLink;
    @FindBy(xpath = "//a[@href=\"/customers/Corporate\"]")
    public WebElement corporatesLink;
    @FindBy(xpath = "//a[@href=\"/customers/vendor\"]")
    public WebElement vendorssLink;
    @FindBy(xpath = "//span[contains(text(),\"SMS\")]")
    public WebElement smsDropList;
    @FindBy(xpath = "//span[(text()=\" Outlets \")]")
    public WebElement outletsDropList;

    @FindBy(xpath = "//a[@href=\"/outlets/property-outlets\"]")
    public WebElement outletsPageLink;
    @FindBy(xpath = "//a[@href=\"/notifications/sms-logs\"]")
    public WebElement smsLogPage;
    @FindBy(xpath = "//a[@href=\"/financial/cash-drawer-balance\"]")
    public WebElement cashDrawer;

    @FindBy(xpath = "//a[@href=\"/reports/menu\"]")
    public WebElement reportsPageLink;
}
