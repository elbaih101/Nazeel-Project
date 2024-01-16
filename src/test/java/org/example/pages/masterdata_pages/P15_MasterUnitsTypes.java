package org.example.pages.masterdata_pages;

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

public class P15_MasterUnitsTypes {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P15_MasterUnitsTypes() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P15_MasterUnitsTypes(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }
    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newUnitTypeButton;
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    @FindBy(xpath = "//input[@id=\"name\"]")
    public WebElement nameSearchField;

    @FindBy(xpath = "//label[contains(text(),\"Status\")]/..//span/span/span")
    WebElement satausSearchDropList;
    public List<WebElement> statuses() {
        satausSearchDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }
   ///  grid  ///////

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> typesNames;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]/span")
    public List<WebElement> typesStatuses;
    public WebElement typeStatus(WebElement typeName){
        return typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"1\"]/span"));
    }


    public List<WebElement> moreActions(WebElement typeName) {
        WebElement moreMenu= typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"3\"]//div/div/div"));
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu));
        moreMenu.click();
        return moreMenu.findElements(By.xpath("..//div[@class=\"popup__content\"]/div"));
    } public WebElement viewButton(WebElement typeName) {
        return typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"3\"]//div/button[2]"));
    }public WebElement editButton(WebElement typeName) {
       return typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"3\"]//div/button[1]"));

    }



    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement unitTypeDialog;


    public WebElement typeNameField(){
        return unitTypeDialog.findElement(By.xpath(".//input"));
    }

    public WebElement submitButton(){
        return unitTypeDialog.findElement(By.xpath("./kendo-dialog-actions/button[contains(@class,\"button--primary\")]"));
    }
    public WebElement modifyTypeButton(){
        return unitTypeDialog.findElement(By.xpath("./kendo-dialog-actions/button[contains(@class,\"button--primary\")][2]"));
    }
    public WebElement confirmDeleteButton(){
        return unitTypeDialog.findElement(By.xpath(".//button[contains(@class,\"n-button--danger \")]"));
    }

    //todo : containue the rest


}
