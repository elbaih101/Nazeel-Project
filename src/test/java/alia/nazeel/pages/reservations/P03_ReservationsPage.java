package alia.nazeel.pages.reservations;

import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.tools.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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


    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(css = "button[class=\"n-button n-button--green ng-star-inserted\"]")
    public WebElement newReservationButton;


    // Reservations Grid
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> reservationsNumbers;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> reservationsStatuses;
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



    public WebElement reservationCheckOutDate(WebElement reservation){
        return reservation.findElement(By.xpath("../td[@data-kendo-grid-column-index=\"6\"]"));
    }
// end Grid //

    // filter //
    @FindBy(xpath = "//div[@class=\"n-table__top-btns\"]//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//div[@class=\"filter-form\"]//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    @FindBy(xpath = "//input[@placeholder=\"Select unit type\"]")
    WebElement filterUnitTypeField;

    public List<WebElement> filterUnitTypes() {
        filterUnitTypeField.click();
        return listItems;
    }

    public List<WebElement> filterCorporates() {
        filterUnitTypeField.click();
        return listItems;
    }

    public List<WebElement> filterBlocks() {
        driver.findElement(By.xpath("//label[contains(text(),\"Block\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        return listItems;
    }

    public List<WebElement> filterResType() {
        driver.findElement(By.xpath("//label[contains(text(),\"Reservation type\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        return listItems;
    }

    public List<WebElement> filterRentType() {
        driver.findElement(By.xpath("//label[contains(text(),\"Rental type\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        return listItems;
    }

    @FindBy(id = "unit-number")
    public WebElement filterUnitNumField;

    @FindBy(xpath = "//label[contains(text(),\"Reservation Status\")]/../following-sibling::kendo-multiselect")
    public WebElement customStatusField;
    @FindBy(xpath = "//label[contains(text(),\"Reservation Status\")]/../following-sibling::kendo-combobox")
    public WebElement predefinedStatusComboBox;

    public List<WebElement> filterCustomStatuses() {
        driver.findElement(By.name("resStatusCustomId")).click();
        customStatusField.click();
        Utils.sleep(300);
        return listItems;

    }

    public List<WebElement> filterPreDefinedStatuses() {
        driver.findElement(By.name("resStatusId")).click();
        return  new P00_multiPurposes(driver).getListItems(predefinedStatusComboBox);
    }

    @FindBy(xpath = "//app-corporate-auto-complete//input")
    WebElement corporateFilterField;

    public WebElement selectCorp(String corpName) {
        corporateFilterField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        corporateFilterField.sendKeys(corpName);
        Utils.sleep(600);
        return listItems.getFirst();
    }

    //TODO Containue the reservation page filters
}
