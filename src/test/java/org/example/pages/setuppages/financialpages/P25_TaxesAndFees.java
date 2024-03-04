package org.example.pages.setuppages.financialpages;

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

public class P25_TaxesAndFees {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P25_TaxesAndFees(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newTax_FeeButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;
    @FindBy(xpath = "//button[contains(@class,\"n-button--secondary\")]")
    public WebElement taxSettingButton;
    @FindBy(xpath = "//kendo-switch[@name=\"inclusive\"]")
    public WebElement inclisuveSwitch;
    @FindBy(xpath = "//div[contains(@class,\"u-m-end\")]//div[2]")
    public WebElement calcstate;
    @FindBy(xpath = "//div[@role=\"popover\"]//button[contains(@class,\"button--primary\")]")
    public WebElement saveCalcButton;


    public List<WebElement> calculations() {
        driver.findElement(By.xpath("//label[contains(text(),\"Calculation \")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    /////end controls /////
    //// grid ////
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> types;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> appliedOns;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    List<WebElement> methods;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> amounts;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"6\"]")
    public List<WebElement> chargedOns;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"7\"]")
    public List<WebElement> startDates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"8\"]")
    public List<WebElement> endDates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"9\"]")
    List<WebElement> moreActions;

    public WebElement editButton(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"9\"]//button[2]"));

    }

    public WebElement viewButton(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"9\"]//button[1]"));
    }

    public WebElement deleteButton(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"9\"]//button[3]"));
    }

    public WebElement taxType(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement taxStatus(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement taxAppliedOn(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement taxMethod(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement taxAmount(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]"));
    }

    public WebElement taxChargedOn(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]"));
    }

    public WebElement taxStartDate(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"7\"]"));
    }

    public WebElement taxEndDate(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"8\"]"));
    }


    ///// new\edit tax page /////

    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;
    @FindBy(xpath = "//button[@tabindex=\"0\"]")
    public WebElement taxesButton;
    @FindBy(xpath = "//button[@tabindex=\"-1\"]")
    public WebElement feesButton;

    public List<WebElement> taxes_FeesList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"tax\"]//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> methodsList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"method\"]//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }
    @FindBy(xpath = "//input[@placeholder=\"Select the method\"]")
    public WebElement methodField;

    public List<WebElement> appliedForList() {
        driver.findElement(By.xpath("//kendo-multiselect[@name=\"applied-for\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(xpath = "//kendo-multiselect[@name=\"applied-for\"]//li")
    public List<WebElement> selectedAppliedFor;

    @FindBy(id = "amount")
    public WebElement amountField;
    @FindBy(xpath = "//input[@placeholder=\"Start Date\"]")
    public WebElement startDate;
    @FindBy(xpath = "//input[@placeholder=\"End Date\"]")
    public WebElement endDate;
    @FindBy(xpath = "//kendo-switch[@name=\"isExpenses\"]")
    public WebElement useForExpensesSwitch;

    @FindBy(xpath = "//kendo-switch[@name=\"enable-chargedon\"]")
    public WebElement chargedOnSwitch;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"n-button--primary\")]")
    public List<WebElement> popUpConfirmButton;

    public List<WebElement> chargedOnList() {
        driver.findElement(By.xpath("//kendo-multiselect[@name=\"activeCustomizedFees\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(xpath = "//kendo-multiselect[@name=\"activeCustomizedFees\"]//li")
    public List<WebElement> selectedChrgdOn;


    public WebElement deleteselctedButtton(WebElement selected) {
        return selected.findElement(By.xpath("./span[@aria-label=\"delete\"]"));
    }

    @FindBy(xpath = "//button[contains(@class,\"button--primary\")]")
    public WebElement submitButon;

    @FindBy(xpath = "//button[contains(@class,\"n-button--danger \")]")
    public WebElement confirmDeleteButton;
}
