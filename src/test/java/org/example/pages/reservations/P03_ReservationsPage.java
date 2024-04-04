package org.example.pages.reservations;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@SuppressWarnings("unused")
public class P03_ReservationsPage {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P03_ReservationsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(css = "button[class=\"n-button n-button--green ng-star-inserted\"]")
    public WebElement newReservationButton;


    // Reservations Grid
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> reservationsNumbers;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> reservationsStatueses;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> reservationsGuests_Corps;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> reservationsUnits_Types;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> reservationsCheckInDates;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"6\"]")
    public List<WebElement> reservationsCheckOutDates;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"7\"]")
    public List<WebElement> reservationsNights;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"8\"]")
    public List<WebElement> reservationsTotalRents;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"9\"]")
    public List<WebElement> reservationsAmounts;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"10\"]")
    public List<WebElement> reservationsTaxes;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"11\"]")
    public List<WebElement> reservationsTotals;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"12\"]")
    public List<WebElement> reservationsSD;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"13\"]")
    public List<WebElement> reservationsPaidAmounts;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"14\"]")
    public List<WebElement> reservationsBalances;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"15\"]")
    List<WebElement> moreActions;
// end Grid //

    // filter //
    @FindBy(xpath = "//div[@class=\"n-table__top-btns\"]//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//div[@class=\"filter-form\"]//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    //TODO Containue the reservation page filters
}
