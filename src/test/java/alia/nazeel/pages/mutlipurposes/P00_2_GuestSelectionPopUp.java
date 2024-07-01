package alia.nazeel.pages.mutlipurposes;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

@SuppressWarnings("unused")
public class P00_2_GuestSelectionPopUp extends BasePage
{
    public P00_2_GuestSelectionPopUp(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(id = "guestFormDialogContainer")
    WebElement guestFormDialogContainer;


    public WebElement nameButton() {
        return guestFormDialogContainer.findElement(By.xpath(".//button[contains(text(),\"Name\")]"));
    }

    public WebElement mobileButton() {
        return guestFormDialogContainer.findElement(By.xpath(".//button[contains(text(),\"Mobile Number\")]"));
    }

    public WebElement searchField() {
        return guestFormDialogContainer.findElement(By.xpath(".//input[contains(@class,\"form-control\")]"));
    }

    public List<WebElement> guestClasses() {
        WebElement guestClassesDropList = guestFormDialogContainer.findElement(By.xpath(".//label[contains(text(),\"Guest Classes\")]/following-sibling::kendo-combobox/span/span/span"));
        wait.until(ExpectedConditions.elementToBeClickable(guestClassesDropList));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.waitLoading();
        guestClassesDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    public WebElement searchButton() {
        return guestFormDialogContainer.findElement(By.xpath(".//button[contains(text(),\"Search\")]"));
    }

    public List<WebElement> guestsNames() {
        return guestFormDialogContainer.findElements(By.xpath(".//td[@data-kendo-grid-column-index=\"0\"]//span"));
    }

    public WebElement confirmButton() {
        return guestFormDialogContainer.findElement(By.xpath(".//button[contains(text(),\"Confirm\")]"));
    }
    public WebElement newGuestButton() {
        return guestFormDialogContainer.findElement(By.xpath(".//button[contains(@class,\"n-button--green\")]"));
    }

    @FindBy(xpath = "//button[contains(text(),\"Ignore Discount\")]")
    public List<WebElement> ignoreDiscountButton;
    @FindBy(xpath = "//button[contains(text(),\"Apply Discount\")]")
    public WebElement applyDiscountButton;
}
