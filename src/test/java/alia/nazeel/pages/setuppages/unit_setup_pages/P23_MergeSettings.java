package alia.nazeel.pages.setuppages.unit_setup_pages;

import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

public class P23_MergeSettings extends BasePage
{
    public P23_MergeSettings(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newMergeButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    @FindBy(xpath = "//label[contains(text(),\"Unit\")]/following-sibling::input")
    public WebElement unitSearchField;

    public List<WebElement> blocksList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Block\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> floorsList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Floor\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> classesList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Classes\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> unitA() {
        driver.findElement(By.xpath("//label[contains(text(),\"Unit A\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> unitB() {
        driver.findElement(By.xpath("//label[contains(text(),\"Unit B\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(xpath = "//button[contains(@class,\"n-button n-button--primary\")]")
    public WebElement saveButton;

    //// grid ////
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> unitsNumbers;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> unitsClasses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> unitsTypes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    List<WebElement> moreActions;

    public WebElement deleteButton(WebElement corporate) {
        return corporate.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]//div//*[name()=\"svg\"]"));
    }

    public WebElement mergeRecordUnitsClasses(WebElement corporate) {
        return corporate.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement mergeRecordUnitsTypes(WebElement corporate) {
        return corporate.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    //////end grid /////

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"n-button--danger \")]")
    public WebElement confirmDeleteButton;

}
