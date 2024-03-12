package org.example.pages.setuppages.outlets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P31_OutletCategories {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P31_OutletCategories(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newCategoryButton;

    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> nTMPCateg;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> outlets;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    List<WebElement> moreActions;

    public WebElement categoryEditButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[1]"));
    }

    public WebElement categoryDeleteButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[2]"));
    }

    public WebElement categoryName(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement categoryOutlet(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }
    public WebElement categoryNTMP(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement categoryStatus(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]"));
    }
    //end grid //

    /// pop up ///
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[contains(@class,\"dropdown-toggle form-control\")]")
    public WebElement categoryNameField;
    @FindBy(name = "text-area")
    public WebElement descriptionField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[@type=\"submit\"]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;
    public List<WebElement> outletsList() {
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//kendo-combobox[@name=\"outlet\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> nTMPCategoriesList() {
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//kendo-combobox[@name=\"ntmp\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }
    @FindBy(xpath = "//div[@class=\"swal2-actions\"]//button[contains(@class,\"confirm\")]")
    public WebElement popUpCOnfirmButton;

    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"outlet\"]//span[@title=\"clear\"]")
    public WebElement clearOutletSelectionButton;
    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"ntmp\"]//span[@title=\"clear\"]")
    public WebElement clearNTMPSelectionButton;
    /// end popup///

    /// filter ///
    @FindBy(xpath = "//div[@class=\"n-table__top-btns\"]//button[contains(@class,\"button--primary\")]")
    public WebElement filterButton;

    public List<WebElement> statusesFilterList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"status\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }
    public List<WebElement> filterOutletsList() {
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//kendo-combobox[@name=\"outlet\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> nTMPFilterList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"ntmp\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @FindBy(id = "name")
    public WebElement nameFilterField;
    @FindBy(xpath = "//button[@class=\"button button--primary\"]")
    public WebElement searchFilterButton;

}
