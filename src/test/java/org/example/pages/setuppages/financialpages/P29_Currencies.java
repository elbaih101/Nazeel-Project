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

public class P29_Currencies {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P29_Currencies(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newCurrencyButton;
    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> currenciesNames;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> symbols;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> exchangeRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> settings;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
     List<WebElement> moreActions;
    public WebElement deleteButton(WebElement discount) {
        discount.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//div/div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--red\") and contains(text(),\"Delete\")]"));
    }

    public WebElement currencyEditButton(WebElement currency) {
        return currency.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//button[1]"));
    }public WebElement currencyViewButton(WebElement currency) {
        return currency.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//button[2]"));
    }
    public WebElement currencyExchangeRate (WebElement currency){
        return currency.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }
    public WebElement currencyStatus (WebElement currency){
        return currency.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }
    public WebElement currencySymbol (WebElement currency){
        return currency.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }
    public WebElement currencySetting(WebElement currency){
        return currency.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }  public WebElement currencyName(WebElement currency){
        return currency.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]"));
    }
    //end grid //


    public List<WebElement> currenciesList() {
        driver.findElement(By.xpath("//kendo-dropdownlist[@name=\"currency\"]//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(xpath = "//kendo-numerictextbox[@name=\"exchange-rate\"]//input")
    public WebElement exchangeRateField;
    @FindBy(name = "is-default")
    public WebElement defaultWitch;
    @FindBy(name = "status")
    public WebElement statusSwitch;
    @FindBy(xpath = "//button[contains(@class,\"button--primary \")]")
    public WebElement submit_EditButton;
    @FindBy(xpath = "//button[contains(@class,\"n-button--danger \")]")
    public WebElement dialogConfirmButton;


    @FindBy(xpath = "//div[contains(text(),\"Total\")]")
    public WebElement totalCount;
}
