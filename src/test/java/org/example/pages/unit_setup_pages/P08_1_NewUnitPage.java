package org.example.pages.unit_setup_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class P08_1_NewUnitPage {

    public P08_1_NewUnitPage() {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P08_1_NewUnitPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(id = "unit-number")
    public WebElement unitNumberField;

    @FindBy(xpath = "//label[contains(text(),\"Unit Class\")]/..//span/span/span")
    WebElement unitClassDropList;
    public List<WebElement> unitClasses() {
        unitClassDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    @FindBy(xpath = "//label[contains(text(),\"Unit Type\")]/..//span/span/span")
    WebElement unitTypeDropList;
    public List<WebElement> unitTypes() {
        unitTypeDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    @FindBy(xpath = "//kendo-switch[@name=\"can-be-merged\"]")
    public WebElement canBeMergedButton;
    @FindBy(xpath = "//label[contains(text(),\"Block\")]/..//span/span/span")
    WebElement blocksDroplistButton;
    public List<WebElement> blocks() {
        blocksDroplistButton.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }
    @FindBy(xpath = "//label[contains(text(),\"Floor\")]/..//span/span/span")
    WebElement floorsDroplistButton;
    public List<WebElement> floors() {
        floorsDroplistButton.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }
    @FindBy(id = "phone-extension")
    public WebElement phoneExtensionField;
    @FindBy(xpath = "//input[@role=\"spinbutton\"]")
    public WebElement numOfToiletsField;
    @FindBy(xpath = "//label[contains(text(),\"Kitchen\")]/..//span/span/span")
    WebElement kitchenssDroplistButton;
    public List<WebElement> kitchens() {
        kitchenssDroplistButton.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }
    @FindBy(xpath = "//label[contains(text(),\"Hall\")]/..//span/span/span")
    WebElement hallsDroplistButton;
    public List<WebElement> halls() {
        hallsDroplistButton.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    @FindBy(id = "unit-area")
    public WebElement unitAreaField;
    @FindBy(id = "single-beds")
    public WebElement singleBedsField;
    @FindBy(id="double-beds")
    public WebElement doubleBedsField;
    @FindBy(xpath = "//div[@class=\"chips-multiselect__item\"]")
    public List<WebElement> ammentiesList;
    @FindBy(xpath = "//textarea[@name=\"text-area\"][1]")
    public WebElement descriptionField;

    @FindBy(xpath = "//Button[contains(text(),\"Add Unit\")]")
    public WebElement addUnitButton;
    @FindBy(xpath = "//Button[contains(text(),\" Save & Add More \")]")
    public WebElement save_addMoreButton;


    //editmode
    @FindBy(xpath = "//Button[contains(text(),\"Edit Unit\")]")
    public WebElement editButton;
    @FindBy(xpath = "//Button[contains(text(),\"Save Changes\")]")
    public WebElement saveChangesButton;



}
