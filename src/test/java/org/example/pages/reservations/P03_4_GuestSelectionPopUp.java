package org.example.pages.reservations;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
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
public class P03_4_GuestSelectionPopUp {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P03_4_GuestSelectionPopUp() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P03_4_GuestSelectionPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
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

    @FindBy(xpath = "//button[contains(text(),\"Ignore Discount\")]")
    public List<WebElement> ignoreDiscountButton;
    @FindBy(xpath = "//button[contains(text(),\"Apply Discount\")]")
    public WebElement applyDiscountButton;



}
