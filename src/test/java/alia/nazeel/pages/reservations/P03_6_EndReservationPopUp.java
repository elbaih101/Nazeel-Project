package alia.nazeel.pages.reservations;


import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class P03_6_EndReservationPopUp extends BasePage {
    public P03_6_EndReservationPopUp(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;
    final String checkOutWizardPath = "//checkout-wizard//kendo-dialog";
    final String cancelWizardPath = "//cancel-noshow-wizard//kendo-dialog";

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

    @FindBy(xpath = checkOutWizardPath + "//label[contains(text(),\"Payment\")]/following-sibling::kendo-combobox")
    public KendoComboBox paymentMethodComboBoc;

    @FindAll({@FindBy(xpath = checkOutWizardPath + "//button[contains(@class,\"button--primary ng-star-inserted\")]"),
            @FindBy(xpath = cancelWizardPath + "//button[contains(@class,\"button--primary ng-star-inserted\")]")})
    public WebElement confirmationButton;
    @FindAll({@FindBy(xpath = checkOutWizardPath + "//div[contains(@class,\"skip\")]"),
            @FindBy(xpath = cancelWizardPath + "//div[contains(@class,\"skip\")]")})
    public List<WebElement> skipButton;
    @FindBy(xpath = "//app-cancel-reservation-warning-popup//button[contains(@class,\"n-button--danger\")]")
    public WebElement confirmCancelButton;

    @FindAll({@FindBy(xpath = checkOutWizardPath + "//label[contains(text(),\"reason\")]/following-sibling::kendo-combobox"),
            @FindBy(xpath = cancelWizardPath + "//label[contains(text(),\"reason\")]/following-sibling::kendo-combobox")
    })
    public KendoComboBox reasons;


    @FindBy(xpath = "//checkout-wizard//div[contains(text(),\"Penalty\")]")
    public WebElement penaltyTabHeader;
}
