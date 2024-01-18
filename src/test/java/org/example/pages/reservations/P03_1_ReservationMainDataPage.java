package org.example.pages.reservations;

import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
public class P03_1_ReservationMainDataPage {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    JavascriptExecutor js;

    public P03_1_ReservationMainDataPage() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P03_1_ReservationMainDataPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) this.driver;
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//div[contains(text(),\"Financial & Payments\")]")
    public WebElement financialAndPaymentsPage;
    @FindBy(css = "button[class=\"n-button n-button--green ng-star-inserted\"]")
    public WebElement newReservationButton;
    @FindBy(xpath = "//div[contains(@class,\"popup__btn\")]")
    public WebElement reservatinMoreActionsMenu;

    @FindBy(xpath = "//kendo-popup//span[contains(text(),\"Check-In\")]")
    public WebElement checkInMenuButton;
    @FindBy(xpath = "//kendo-popup//div[contains(text(),\"Check-Out\")]")
    public WebElement checkoutMenuButton;
    @FindBy(xpath = "//kendo-popup//div[contains(text(),\"Cancel reservation\")]")
    public WebElement cancelReservationButton;


    //waitfor that
    //http://staging.nazeel.net:9002/reservations/wizard/add

    @FindBy(xpath = "//label[contains(text(),\"Reservation source \")]//following-sibling::kendo-dropdownlist")
    public WebElement reservationSourceDropList;

    public List<WebElement> reservationSources() {
        wait.until(ExpectedConditions.invisibilityOf(new P00_multiPurposes().loadingAnimation));
        wait.until(ExpectedConditions.elementToBeClickable(reservationSourceDropList));
        reservationSourceDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }


    @FindBy(xpath = "//kendo-popup//div[contains(text(),\"test3\")]")
    public WebElement test3ReservationSource;

    @FindBy(xpath = "//label[contains(text(),\"Visit purpose\")]//following-sibling::kendo-dropdownlist")
    public WebElement visitPurposeDropList;

    @FindBy(xpath = "//kendo-popup//li[contains(text(),\"Family or friends\")]")
    public WebElement familyOrFriendsSelection;

    @FindBy(xpath = "//span[contains(text(),\"Select unit now\")]")
    public WebElement selectUnitNowSpan;


    @FindBy(xpath = "//kendo-dialog//kendo-panelbar-item")
    public WebElement panelBar;

    @FindBy(xpath = "//span[contains(text(),\"Select guest now\")]/..")
    public WebElement selectGestButton;

    @FindBy(id = "guestFormDialogContainer")
    public WebElement guestFormDialogContainer;
    @FindBy(xpath = "//button[contains(text(),\"Save reservation\")]")
    public WebElement saveReservationButton;
    @FindBy(xpath = "//button[contains(text(),\"Check-In\")]")
    public WebElement checkInButton;

    @FindBy(xpath = "//kendo-dialog-actions//button")
    public WebElement reservationSummarySaveButton;
    @FindBy(xpath = "//div[@class=\"toast-message\"]")
    public WebElement toastMessage;
    @FindBy(xpath = "//div[@class=\"page-header\"]//h2//span")
    public WebElement reservationStatus;


}
