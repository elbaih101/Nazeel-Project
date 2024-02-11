package org.example.pages.reservations;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P03_6_EndReservationPopUp {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P03_6_EndReservationPopUp() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P03_6_EndReservationPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement endReservationPopup;

    public List<WebElement> header(){
        return driver.findElements(By.xpath("//kendo-dialog-titlebar"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"button--primary\")]")
    public WebElement confirmCheckOutButton;


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

    public WebElement skipButton() {
        return endReservationPopup.findElement(By.xpath(".//div[contains(@class,\"skip\")]"));
    }

    public WebElement confirmCancelButton(){
        return endReservationPopup.findElement(By.xpath(".//button[contains(@class,\"n-button--danger\")]"));
    }

    public List<WebElement> reasons(){
        endReservationPopup.findElement(By.xpath(".//label[contains(text(),\"reason\")]/following-sibling::kendo-combobox//span/span/span")).click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
}
