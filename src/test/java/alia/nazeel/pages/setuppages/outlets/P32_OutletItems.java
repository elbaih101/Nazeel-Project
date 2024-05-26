package alia.nazeel.pages.setuppages.outlets;

import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;
import java.util.List;

public class P32_OutletItems {
    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;


    public P32_OutletItems(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newItemButton;

    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> prices;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> categories;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> outlets;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    List<WebElement> moreActions;

    public WebElement itemEditButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//button[1]"));
    }

    public WebElement itemDeleteButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//button[2]"));
    }

    public WebElement itemName(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement itemOutlet(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement itemPrice(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement itemCategory(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement itemStatus(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]"));
    }
    //end grid //

    /// data entery///
    @FindBy(xpath = "//input[contains(@class,\"dropdown-toggle form-control\")]")
    public WebElement itemNameField;
    @FindBy(name = "text-area")
    public WebElement descriptionField;
    @FindBy(xpath = "//button[@type=\"submit\" and text()=\" Save \"]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;
    @FindBy(xpath = "//kendo-switch[@name=\"no-tax\"]")
    public WebElement taxExemptedSwitch;
    @FindBy(xpath = "//kendo-switch[@name=\"no-price\"]")
    public WebElement freeItemSwitch;
    @FindBy(xpath = "//kendo-switch[@name=\"priceIsUserDefined\"]")
    public WebElement userDefinedPriceSwitch;

    public List<WebElement> outletsList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"outlet\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> categoriesList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"category\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> itemTypesList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"type\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @FindBy(xpath = "//kendo-numerictextbox[@name=\"price\"]//input")
    public WebElement priceInput_FilterField;
    @FindBy(xpath = "//div[@class=\"swal2-actions\"]//button[contains(@class,\"confirm\")]")
    public WebElement popUpCOnfirmButton;

    @FindBy(xpath = "//kendo-combobox[@name=\"outlet\"]//span[@title=\"clear\"]")
    public WebElement clearOutletSelectionButton;
    @FindBy(xpath = "//kendo-combobox[@name=\"category\"]//span[@title=\"clear\"]")
    public WebElement clearCategorySelectionButton;
    @FindBy(xpath = "//kendo-combobox[@name=\"type\"]//span[@title=\"clear\"]")
    public WebElement clearTypeSelectionButton;
    /// end Data entery///

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
        driver.findElement(By.xpath("//kendo-combobox[@name=\"outlet\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> categoryFilterList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"category\"]//span[@class=\"k-select\"]")).click();
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
