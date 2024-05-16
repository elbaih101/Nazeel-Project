package alia.nazeel.pages.setuppages.unit_setup_pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("unused")
public class P09_RatesPages {




    public P09_RatesPages(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(),\"Edit\")]")
    public WebElement editBaseRateButton;
    @FindBy(xpath = "//button[contains(@class,\"-button--green\")]")
    public WebElement addNewRateButton;
    @FindBy(xpath = "//input[@placeholder=\"Start Date\"]")
    public WebElement rateStartDateField;
    @FindBy(xpath = "//input[@placeholder=\"End Date\"]")
    public WebElement rateEndDateField;
    @FindBy(xpath = "//input[@required]")
    public WebElement rateName;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> unitTypes;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> lowWeekDaysRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> highWeekDaysRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> minimumRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> monthlyRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> minimumMonthlyRates;
    @FindBy(xpath = "//button[contains(@class,\"button--primary u-m-end\")]")
    public WebElement saveChangesButton;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green-border\")]")
    public WebElement highWeekDaysButton;

    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement highWeekDaysPopUp;


    public List<WebElement> highWeekDays() {
        return highWeekDaysPopUp.findElements(By.xpath(".//div[contains(@class,\"col-md-3 u-mb-15\")]"));
    }

    public WebElement saveHighWeekDaysButton() {
        return highWeekDaysPopUp.findElement(By.xpath(".//button[contains(@class,\"n-button--primary\")]"));

    }

    public WebElement lowRate(WebElement unitType) {
        return unitType.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]//input[@role=\"spinbutton\"]"));
    }

    public WebElement highRate(WebElement unitType) {
        return unitType.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]//input[@role=\"spinbutton\"]"));
    }

    public WebElement minRate(WebElement unitType) {
        return unitType.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]//input[@role=\"spinbutton\"]"));
    }

    public WebElement monRate(WebElement unitType) {
        return unitType.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//input[@role=\"spinbutton\"]"));
    }

    public WebElement monMinRate(WebElement unitType) {
        return unitType.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//input[@role=\"spinbutton\"]"));
    }
}