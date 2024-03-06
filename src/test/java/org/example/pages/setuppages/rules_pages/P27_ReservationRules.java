package org.example.pages.setuppages.rules_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P27_ReservationRules {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P27_ReservationRules(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//kendo-buttongroup/button[1]")
    public WebElement listViewButton;
    @FindBy(xpath = "//kendo-buttongroup/button[2]")
    public WebElement unitsViewButton;
    @FindBy(xpath = "//kendo-buttongroup/button[3]")
    public WebElement calenderViewButton;
    // Times //
    @FindBy(xpath = "//label[contains(text(),\"Check-In time\")]/following-sibling::kendo-timepicker//input")
    public WebElement checkInTimeField;
    @FindBy(xpath = "//label[contains(text(),\"Check-Out time\")]/following-sibling::kendo-timepicker//input")
    public WebElement checkOutTimeField;

    public List<WebElement> gracePeriodsList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Grace \")]/parent::div/following-sibling::kendo-dropdownlist")).click();
        return listItems;
    }


    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Activate previous day calculations\")]/preceding-sibling::kendo-switch")
    public WebElement previousDayClacSwitch;
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Auto extend for daily \")]/preceding-sibling::kendo-switch")
    public WebElement autoExtendDailySwitch;
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"extend for monthly\")]/preceding-sibling::kendo-switch")
    public WebElement autoExtendForMonthlySwitch;
    @FindBy(xpath = "//label[contains(text(),\"Auto Extend\")]/parent::div/following-sibling::kendo-timepicker//input")
    public WebElement autoExtendAfterTimeField;
    ///End Times ///
    ///  unit allownace //
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Restrict changing the units\")]/preceding-sibling::kendo-switch")
    public WebElement restrictChangeUnitSwitch;
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Reason \")]/preceding-sibling::kendo-switch")
    public WebElement reasonsRequireSwitch;

    public List<WebElement> unitAllownces() {
        driver.findElement(By.xpath("//label[contains(text(),\"Allowance\")]/parent::div/following-sibling::kendo-dropdownlist")).click();
        return listItems;
    }

    /// End  unit allownace //
    /// Other Settings ///
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),'Enable \"UnConfirmed\"')]/preceding-sibling::kendo-switch")
    public WebElement enableUnconfirmedSwitch;
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Enable Monthly\")]/preceding-sibling::kendo-switch")
    public WebElement enableMonthlySwitch;
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"'No Show\")]/preceding-sibling::kendo-switch")
    public WebElement autoNoShowSwitch;

    public List<WebElement> autoNoShowReasons() {
        driver.findElement(By.xpath("//label[contains(text(),\"Reason\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        return listItems;

    }

    @FindBy(xpath = "//label[contains(text(),\"No Show Time\")]/following-sibling::kendo-timepicker//input")
    public WebElement autoNoShowTimeField;


    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Rejected OTA\")]/preceding-sibling::kendo-switch")
    public WebElement autoRejectOTASwitch;

    public List<WebElement> cancelREasonsList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Cancel Reason\")]/parent::div/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        return listItems;
    }

    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Mandatory Check\")]/preceding-sibling::kendo-switch")
    public WebElement mandatoryCheckInTodaySwitch;
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"balance exist\")]/preceding-sibling::kendo-switch")
    public WebElement skipBalancePaySwitch;
    @FindBy(xpath = "//span[contains(@class,\"switch-label--sm\") and contains(text(),\"Sequence\")]/preceding-sibling::kendo-switch")
    public WebElement resetSequenceSwitch;
    /// End Other Settings ///

    @FindBy(xpath = "//button[contains(@class,\"n-button--primary\")]")
    public WebElement submitButton;
    //// error msg ///
    @FindBy(id="swal2-content")
    public WebElement msg;
    @FindBy(xpath = "//div[@class=\"swal2-actions\"]//button[contains(@class,\"swal2\")]")
    public WebElement msgConfirmButton;
}
