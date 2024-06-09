package alia.nazeel.pages.reservations;


import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class P03_6_EndReservationPopUp extends BasePage {
    public P03_6_EndReservationPopUp(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;
    final String baseXpath = "//checkout-wizard//kendo-dialog";

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

    @FindBy(xpath = baseXpath + "//label[contains(text(),\"Payment\")]/following-sibling::kendo-combobox")
    public KendoComboBox paymentMethodComboBoc;

    @FindBy(xpath = baseXpath+"//button[contains(@class,\"button--primary ng-star-inserted\")]")
    public WebElement confirmationButton;
    @FindBy(xpath = baseXpath+"//div[contains(@class,\"skip\")]")
    public List<WebElement> skipButton;
    @FindBy(xpath = baseXpath+"//button[contains(@class,\"n-button--danger\")]")
    public WebElement confirmCancelButton;

    @FindBy(xpath = baseXpath+"//label[contains(text(),\"reason\")]/following-sibling::kendo-combobox")
    public KendoComboBox reasons;


    @FindBy(xpath = "//checkout-wizard//div[contains(text(),\"Penalty\")]")
    public WebElement penaltyTabHeader;
}
