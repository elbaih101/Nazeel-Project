package alia.nazeel.pages.setuppages.properties_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@SuppressWarnings("unused")
public class P06_1_PropeertyDataPage extends BasePage
{

    public P06_1_PropeertyDataPage(WebDriver driver){
        super(driver);
    }
    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;
    @FindBy(xpath = "//input[@placeholder=\"Proberty Name\"][1]")
    public WebElement propertyNameField;

    @FindBy(xpath = "//input[@placeholder=\"Report Name\"][1]")
    public WebElement reportNameField;

    @FindBy(xpath = "//kendo-combobox[@name=\"propertyType\"]/span/span")
     WebElement propertyTypeSelectionbox;


    @FindBy(xpath = "//kendo-combobox[@name=\"unitClass\"]/span/span")
     WebElement unitClassSelectionBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"propertyAccount\"]/span/span/span")
     WebElement propertAccountSelectionBox;

    @FindBy(xpath = "//label[contains(text(),\"Units\")] /following-sibling::kendo-numerictextbox//input[@role=\"spinbutton\"]")
    public WebElement numberOfUnitsField;

    @FindBy(xpath = " //label[contains(text(),\"Property Classification\")]/following-sibling::kendo-combobox[@name=\"Classification\"]/span/span/span")
     WebElement propertyClassificationSelectionBox;
 @FindBy(xpath = "//Button[contains(text(),\"Next\")]")
    public WebElement nextButton;


 public List<WebElement> propertyType()
 {
    propertyTypeSelectionbox.click();
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

 }
 public List<WebElement> propertyClass()
 {
    propertyClassificationSelectionBox.click();
     try {
         Thread.sleep(300);
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
     return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
 }
 public List<WebElement> unitClass()
 {
    unitClassSelectionBox.click();

     return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
 }public List<WebElement> propertyAccount()
 {
    propertAccountSelectionBox.click();

     return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
 }




}
