package alia.nazeel.pages.reservations;

import alia.nazeel.kendoelements.Grid;
import alia.nazeel.kendoelements.KendoDropDownList;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;

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

    final JavascriptExecutor js;


    public P03_1_ReservationMainDataPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        actions = new Actions(this.driver);
        js = (JavascriptExecutor) this.driver;
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//div[contains(text(),\"Financial & Payments\")]")
    public WebElement financialAndPaymentsPage;
    @FindBy(xpath = "//label[contains(text(),\"Reservation type\")]/following-sibling::div/kendo-buttongroup/button")
    public List<WebElement> resTypesButtons;

    ///more actions menue ///
    @FindBy(xpath = "//div[contains(@class,\"popup__btn\")]")
    public WebElement reservatinMoreActionsMenu;

    @FindBy(xpath = "//kendo-popup//span[contains(text(),\"Check-In\")]")
    public WebElement checkInMenuButton;
    @FindBy(xpath = "//kendo-popup//div[contains(text(),\"Check-Out\")]")
    public WebElement checkoutMenuButton;
    @FindBy(xpath = "//kendo-popup//div[contains(text(),\"Cancel reservation\")]")
    public WebElement cancelReservationButton;
    /////end actions menu///
    @FindBy(xpath = "//div[@class=\"financial-summary__title\"][contains(text(),\"Rent\")]/following-sibling::div")
    public WebElement renttotal;


    //waitfor that
    //http://staging.nazeel.net:9002/reservations/wizard/add

    @FindBy(xpath = "//label[contains(text(),\"Reservation source \")]//following-sibling::kendo-dropdownlist")
    public WebElement reservationSourceDropList;

    public List<WebElement> reservationSources() {
        new P00_multiPurposes(driver).waitLoading();
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

    ////Reservation Guest List Wizard /////
    @FindBy(xpath = "//reservation-guest-list-wizard//button[contains(@class,\"green-border\")]")
    public WebElement addDependentButton;

    @FindBy(xpath = "//span[contains(text(),\"Select guest now\")]/..")
    public WebElement selectGestButton;
    @FindBy(xpath = "//app-guests-step//span[contains(text(),\"Dependents\")]")
    public WebElement dpendentsButton;
    @FindBy(id = "guestFormDialogContainer")
    public WebElement guestFormDialogContainer;
    @FindBy(xpath = "//button[contains(text(),\"Save reservation\")]")
    public WebElement saveReservationButton;
    @FindBy(xpath = "//button[contains(@class,\"button--green-border\")]")
    public WebElement checkInButton;

    @FindBy(xpath = "//kendo-dialog-actions//button")
    public WebElement reservationSummarySaveButton;
    @FindBy(xpath = "//div[@class=\"toast-message\"]")
    public WebElement toastMessage;

    @FindBy(xpath = "//div[@class=\"page-header\"]//h2")
    public WebElement reservationNumber;
    @FindBy(xpath = "//div[@class=\"page-header\"]//h2//span")
    public WebElement reservationStatus;

    @FindBy(xpath = "//span[contains(text(),\"Select corporate\")]")
    public WebElement selectCorporateButton;

    @FindBy(xpath = "//div[@class=\"financial-summary__title\" and contains(text(),\"Taxes\")]/span[contains(@class,\"form__action-icon\")]")
    public WebElement veiwTaxesButton;

    @FindBy(xpath = "//input[@placeholder=\"Different rates\"]")
    public WebElement diffrentRatesInput;
    @FindBy(tagName = "app-reservation-failed-popup")
    public WebElement resFailedPopUp;

    ///REservation periods bar ///
    @FindBy(xpath = "//label[contains(text(),\"Check-in\")]/following-sibling::div//kendo-datepicker//input")
    public WebElement startDateField;
    @FindBy(xpath = "//label[contains(text(),\"Check-out\")]/following-sibling::div//kendo-datepicker//input")
    public WebElement endDateField;
    @FindBy(xpath = "//label[contains(text(),\"Check-out\")]/following-sibling::div/div[@class=\"input-group\"]/span")
    public WebElement endtimeButton;
    @FindBy(xpath = "//app-reservation-time-picker//input[@placeholder=\"HH\"]")
    public WebElement HoursField;
    @FindBy(xpath = "//app-reservation-time-picker//input[@placeholder=\"MM\"]")
    public WebElement minautesField;
    @FindBy(xpath = "//app-reservation-time-picker//button[@class=\"btn btn-outline-primary\"]")
    public WebElement dayNightButton;
    @FindBy(xpath = "//app-reservation-time-picker//a")
    public WebElement setTimeButton;
    @FindBy(xpath = "//span[contains(text(),\"Rental type\")]/../../following-sibling::div//kendo-dropdownlist")
    WebElement rentalPeriodDropList;

    public KendoDropDownList rentalPeriodDropList() {
        return new KendoDropDownList(rentalPeriodDropList);
    }

    @FindBy(xpath = "//label[contains(text(),\"Period \")]/../following-sibling::div//input")
    public WebElement rentalPeriodField;

    ///Reservation Units Grid //
    @FindBy(xpath = "//select-units-wizard//th[8]//div[contains(@class,\"header-action\")]//*[name()=\"use\" and contains(@*,\"icon-add\") ]")
    public WebElement addUnitButton;

    @FindBy(xpath = "//label[contains(text(),\"Unit(s) \")]/../following-sibling::kendo-grid//kendo-grid-list")
    WebElement unitsGrid;

    Grid getResUnitsGrid() {
        return new Grid(unitsGrid);
    }

    public List<WebElement> resUnits() {
        return getResUnitsGrid().getGridCells(0);
    }

    public List<WebElement> unitsTypes() {
        return getResUnitsGrid().getGridCells(1);
    }

    public WebElement unitChangeButton(WebElement unit) {
        return getResUnitsGrid().getGridCell(unit, 7).findElement(By.xpath(".//button//*[name()=\"use\" and contains(@*,\"icon-change-unit\")]/.."));
    }
/////end units ///////

    ///unit change confirmation popup ///
    @FindBy(xpath = "//div[contains(@class,\"swal2-container\")]//button[contains(text(),\"OK\")]")
    public WebElement confirmChangeUnitButton;
    @FindBy(id = "swal2-content")
    public WebElement confirmChangeUnitMessage;
    ///////end popup //////

    //unitsRates page popup////
    @FindBy(xpath = "//div[@class=\"form__action-icon u-pointer-events-all\"]")
    public WebElement unitsRatesPopUpButton;
///end popup ///
}
