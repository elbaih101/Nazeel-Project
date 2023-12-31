package org.example.pages.properties_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class P06_5_SubscriptionPage {


    public P06_5_SubscriptionPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }   public P06_5_SubscriptionPage(){
        PageFactory.initElements(Hooks.driver,this);
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]")
     WebElement genralListBox;


    @FindBy(xpath = "//kendo-datepicker[@name=\"startDate\"]/span/span/span")
    WebElement datePickerButton;
    @FindBy(xpath = "//kendo-calendar-header/span[@class=\"k-today\"]")
    WebElement todayButton;
@FindBy(xpath = "//kendo-datepicker[@name=\"startDate\"]//input")
    public WebElement startDateField;
@FindBy(xpath = "//kendo-numerictextbox//input")
    public WebElement subscriptionPeriodField;
@FindBy(xpath = "//input[@name=\"startingAmount\"]")
    public WebElement startingAmountField;
@FindBy(xpath = "//input[@name=\"startingAmountTax\"]")
    public WebElement startingAmountTaxField;
    @FindBy(xpath = "//input[@name=\"annualRenewalPrice\"]")
    public WebElement annualRenewalPriceField;
@FindBy(xpath = "//input[@name=\"depositer-name\"]")
    public WebElement depositerNameField;
@FindBy(xpath = "//input[@name=\"comment\"]")
    public WebElement commentField;
@FindBy(xpath = "//kendo-combobox[@name=\"sales-man\"]/span/span")
     WebElement salesManSelectionBox;

    @FindBy(xpath = "//Button[contains(text(),\"Next\")]")
    public WebElement nextButton;


    public List<WebElement> salesman(){
        salesManSelectionBox.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    public Void selecttoday(){
        datePickerButton.click();
        todayButton.click();
        return null;
    }
}
