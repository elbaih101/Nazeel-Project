package org.example.pages.setuppages.properties_pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class P06_2_LocationDataPage {
    WebDriverWait wait;
    public P06_2_LocationDataPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;


    //DropLists
    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"country\"]/span/span/span")
     WebElement countrySelectionBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"region\"]/span/span/span")
     WebElement regionSelectionBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"city\"]/span/span/span")
     WebElement citySelectionBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"district\"]/span/span/span")
     WebElement districtSelectionBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"streetId\"]/span/span/span")
     WebElement streetSelectionBox;
@FindBy(xpath = "//input[@name=\"buildingNo\"]")
    public WebElement buildingNumberField;
@FindBy(xpath = "//input[@name=\"additionalNo\"]")
    public WebElement additionalNoField;
@FindBy(xpath = "//input[@placeholder=\"Address\"][1]")
    public WebElement addressField;
@FindBy(xpath = "//input[@name=\"countryDialCode\"]")
    public WebElement countryDialCode;
@FindBy(xpath = "//input[@name=\"cityDialCode\"]")
    public WebElement cityDialCode;
@FindBy(xpath = "//input[@name=\"phoneNumber\"]")
    public WebElement phoneNumberField;
@FindBy(xpath = "//input[@name=\"mobileNumber\"]")
    public WebElement mobileNumberField;
@FindBy(xpath = "//input[@name=\"email\"]")
    public WebElement emailField;
@FindBy(xpath = "//input[@name=\"postalCode\"]")
    public WebElement postalCodeField;
@FindBy(xpath = "//Button[contains(text(),\"Next\")]")
    public WebElement nextButton;





public List<WebElement> country()
{
    wait.until(ExpectedConditions.elementToBeClickable(countrySelectionBox));
    countrySelectionBox.click();
    wait.until(ExpectedConditions.visibilityOf(genralListBox));
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
}
public List<WebElement> region ()
{wait.until(ExpectedConditions.elementToBeClickable(regionSelectionBox));
    regionSelectionBox.click();
    wait.until(ExpectedConditions.visibilityOf(genralListBox));
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
}
public List<WebElement> city()
{wait.until(ExpectedConditions.elementToBeClickable(citySelectionBox));
    citySelectionBox.click();
    wait.until(ExpectedConditions.visibilityOf(genralListBox));
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
}
public List<WebElement> district()
{wait.until(ExpectedConditions.elementToBeClickable(districtSelectionBox));
    districtSelectionBox.click();
    wait.until(ExpectedConditions.visibilityOf(genralListBox));
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
}
public List<WebElement> street()
{wait.until(ExpectedConditions.elementToBeClickable(streetSelectionBox));
    streetSelectionBox.click();
    wait.until(ExpectedConditions.visibilityOf(genralListBox));
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
}







}
