package alia.nazeel.pages.reservations;


import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;
import java.util.List;

public class P03_6_EndReservationPopUp {

    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;

    public P03_6_EndReservationPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement endReservationPopup;

    public List<WebElement> header() {
        return driver.findElements(By.xpath("//kendo-dialog-titlebar"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(text(),\"Today\")]")
    public WebElement checkoutTodayButton;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(text(),\"Reservation\")]")
    public WebElement checkoutReservationDateButton;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"button--primary\")]")
    public WebElement confirmEndButton;

    public WebElement amountField() {
        return endReservationPopup.findElement(By.xpath(".//input[@id=\"amount\"]"));
    }

    public List<WebElement> paymentMethods() {
        endReservationPopup.findElement(By.xpath(".//label[contains(text(),\"Payment\")]/following-sibling::kendo-combobox//span/span/span")).click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    public WebElement confirmationButton() {
        return endReservationPopup.findElement(By.xpath(".//button[contains(@class,\"button--primary ng-star-inserted\")]"));
    }

    public List<WebElement> skipButton() {
        return endReservationPopup.findElements(By.xpath(".//div[contains(@class,\"skip\")]"));
    }

    public WebElement confirmCancelButton() {
        return endReservationPopup.findElement(By.xpath(".//button[contains(@class,\"n-button--danger\")]"));
    }

    public List<WebElement> reasons() {
        endReservationPopup.findElement(By.xpath(".//label[contains(text(),\"reason\")]/following-sibling::kendo-combobox//span/span/span")).click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//checkout-wizard//div[contains(text(),\"Penalty\")]")
    public WebElement penaltyTabHeader;
}
