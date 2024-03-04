package org.example.pages.setuppages.financialpages;

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

public class P26_CostCenter {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P26_CostCenter(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newCostCenterButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    public List<WebElement> categoriesFilterList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Category\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> statusFilterList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Status\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(id = "name")
    public WebElement nameFilterField;

    //// amenity Pop up ///
    public List<WebElement> categoriesList() {
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//label[contains(text(),\"Category\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(xpath = "//label[contains(text(),\"Name\")]/following-sibling::div//input")
    public WebElement costCenterNameField;
    @FindBy(xpath = "//textArea")
    public WebElement descriptionField;
    @FindBy(xpath = "//kendo-dialog-actions//button[contains(@class,\"button--primary \")]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;


    //// grid ////
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> costStatus;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> costNames;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> categories;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> descriptions;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    List<WebElement> moreActions;

    public WebElement deleteButton(WebElement costCenter) {
        return costCenter.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[2]"));
    }


    public WebElement editButton(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[1]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"n-button--danger \")]")
    public WebElement confirmDeleteButton;

    public WebElement costCenterStatus(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]"));
    }

    public WebElement costCenterCategory(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement costCenterDescription(WebElement amenity) {
        return amenity.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    //////end grid /////
}
