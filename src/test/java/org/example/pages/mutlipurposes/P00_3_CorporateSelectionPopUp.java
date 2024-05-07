package org.example.pages.mutlipurposes;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

@SuppressWarnings("unused")
public class P00_3_CorporateSelectionPopUp {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P00_3_CorporateSelectionPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//app-reservation-corporate//button[contains(@class,\"n-button--green\")]")
    public WebElement newCorporateButton;
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//app-reservation-corporate//input")
    public WebElement searchFiled;
    @FindBy(xpath = "//app-reservation-corporate//button[contains(@class,\"n-button n-button--primary\")]")
    public WebElement saveButton;
    @FindBy(xpath = "//app-reservation-corporate//kendo-buttongroup/button")
    //Name,Phone.Email,VAT No.
    public List<WebElement> searchCriteriasButtons;
    @FindBy(xpath = "//app-reservation-corporate//button[contains(@class,\"button--raised button--primary\")]")
    public WebElement searchButton;

    @FindBy(xpath = "//app-reservation-corporate//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> corporatesNAmes;
    @FindBy(xpath = "//app-reservation-corporate//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> corporatesPhones;
    @FindBy(xpath = "//app-reservation-corporate//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> corporatesEmails;
    @FindBy(xpath = "//app-reservation-corporate//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> corporatesAddresses;
    @FindBy(xpath = "//app-reservation-corporate//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> corporatesVATs;
    @FindBy(xpath = "//app-reservation-corporate//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> corporatesDiscounts;


}
