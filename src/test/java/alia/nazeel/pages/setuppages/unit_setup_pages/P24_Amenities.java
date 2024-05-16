package alia.nazeel.pages.setuppages.unit_setup_pages;

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

public class P24_Amenities {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;



    public P24_Amenities(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newAmenityButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    //// amenity Pop up ///
    public List<WebElement> amenitysList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Amenity\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(xpath = "//textArea")
    public WebElement descriptionField;
    @FindBy(xpath = "//button[contains(@class,\"n-button--primary \")]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Status\")]/following-sibling::span")
    public WebElement amenityStatus;


    //// grid ////
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> Types;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> descriptions;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    List<WebElement> moreActions;

    public WebElement deleteButton(WebElement amenity) {
        amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//div/div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--red\")]"));
    }
    public WebElement applyButton(WebElement amenity) {
        amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//div/div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[@class=\"k-popup\"]//div[contains(text(),\"Apply\")]"));
    }

    public WebElement editButton(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[1]"));

    }

    public WebElement viewButton(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[2]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"n-button--danger \")]")
    public WebElement confirmDeleteButton;

    public WebElement amenityStatus(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement amenityDescription(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    //////end grid /////
}
