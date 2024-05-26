package alia.nazeel.pages.setuppages.outlets;

import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P30_OutletsSetup {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P30_OutletsSetup(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newOutletButton;
    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> opStates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> codes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> descriptions;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> categories_Items;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"6\"]")
    List<WebElement> moreActions;


    public WebElement outletEditButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[1]"));
    }

    public WebElement outletDeleteButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[2]"));
    }

    public WebElement outletName(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement outletOPStatus(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement outletCode(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement outletDescription(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement outletStatus(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]"));
    }
    //end grid //


    /// pop up ///
    public List<WebElement> opStatusesList() {
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//kendo-combobox//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@name=\"outlet-code\"]")
    public WebElement outletCodeField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[contains(@class,\"dropdown-toggle form-control\")]")
    public WebElement outletNameField;
    @FindBy(name = "text-area")
    public WebElement descriptionField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[@type=\"submit\"]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;

    @FindBy(xpath = "//div[@role=\"dialog\"]//span[@title=\"clear\"]")
    public WebElement clearOpStateSelection;
    @FindBy(xpath = "//div[@class=\"swal2-actions\"]//button[contains(@class,\"confirm\")]")
    public WebElement popUpCOnfirmButton;
    /// end pop up ///

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

    public List<WebElement> opstatusesFilterList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"operating-status\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @FindBy(id = "name")
    public WebElement nameFilterField;
    @FindBy(name = "outlet-code")
    public WebElement outletFilterCodeField;

    @FindBy(xpath = "//button[@class=\"button button--primary\"]")
    public WebElement searchFilterButton;


    /* to be used in the Step Defination
      costMap.put("categ", costCenter.costCenterCategory(selectedCost).getText());
        if (costCenter.costCenterStatus(selectedCost).getAttribute("xlink:href").contains("icon-check"))
            costMap.put("stat", "Acive");
        else if (costCenter.costCenterStatus(selectedCost).getAttribute("xlink:href").contains("icon-minus"))
            costMap.put("stat", "Inacive");
     */
}
