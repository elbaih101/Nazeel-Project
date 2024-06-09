package alia.nazeel.pages.reservations;

import alia.nazeel.kendoelements.KendoDropDownList;
import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.kendoelements.SwalPopUp;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@SuppressWarnings("unused")
public class P03_1_ReservationMainDataPage extends BasePage {
    public P03_1_ReservationMainDataPage(WebDriver driver) {
        super(driver);
    }



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
    public KendoDropDownList reservationSourceDropList;


    @FindBy(xpath = "//label[contains(text(),\"Visit purpose\")]//following-sibling::kendo-dropdownlist")
    public KendoDropDownList visitPurposeDropList;

    @FindBy(xpath = "//span[contains(text(),\"Select guest now\")]/..")
    public WebElement selectGestButton;
    @FindBy(xpath = "//app-guests-step//div[contains(@class,\"primary-link\")]/span")

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
    public KendoDropDownList rentalPeriodDropList;

    @FindBy(xpath = "//label[contains(text(),\"Period \")]/../following-sibling::div//input")
    public WebElement rentalPeriodField;

    ///Reservation Units Grid //
    @FindBy(xpath = "//select-units-wizard//th[8]//div[contains(@class,\"header-action\")]//*[name()=\"use\" and contains(@*,\"icon-add\") ]")
    public WebElement addUnitButton;

    @FindBy(xpath = "//label[contains(text(),\"Unit(s) \")]/../following-sibling::kendo-grid//kendo-grid-list")
    WebElement unitsGrid;

    KendoGrid getResUnitsGrid() {
        return new KendoGrid(unitsGrid);
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
    @FindBy(css = "div.swal2-container")
    public SwalPopUp alert;
    ///////end popup //////

    //unitsRates page popup////
    @FindBy(xpath = "//div[@class=\"form__action-icon u-pointer-events-all\"]")
    public WebElement unitsRatesPopUpButton;
///end popup ///
}
