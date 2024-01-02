package org.example.pages.unit_setup_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


@SuppressWarnings("unused")
public class P08_2_GroupOfUnitsPopUp {


    public P08_2_GroupOfUnitsPopUp() {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P08_2_GroupOfUnitsPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Block\")]//following-sibling::kendo-combobox/span/span/span")
    WebElement blocksDropList;

    public List<WebElement> blocks() {
        try {
            blocksDropList.click();

        } catch (NoSuchElementException e) {
            System.out.println("editing not seleceting");
            return null;

        }
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Floor\")]//following-sibling::kendo-combobox/span/span/span")
    WebElement floorsDropList;

    public List<WebElement> floorss() {
           floorsDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Unit type\")]//following-sibling::kendo-combobox/span/span/span")
    WebElement unitsTypeDroplist;

    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Format\")]//following-sibling::kendo-combobox/span/span/span")
    WebElement formatDroplist;

    public List<WebElement> unitType() {


        unitsTypeDroplist.click();

        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    public List<WebElement> formats() {
        formatDroplist.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@name=\"unit-number-from\"]")
    public WebElement fromNumber;

    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@name=\"unit-number-to\"]")
    public WebElement toNumber;

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[(text()=\" Save \")]")
    public WebElement saveButton;



    /////// delete ////

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[(text()=\"Next\")]")
    public WebElement nextButon;
    @FindBy(xpath = "//div[@role=\"dialog\"]//th//input[@type=\"checkbox\"]")
    public WebElement selectAllCheckBox;

    @FindBy(xpath = "//td[@aria-colindex=\"1\"]")
    public WebElement unitstoSelect;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@aria-colindex=\"2\"]/span")
    List<WebElement> featureNames;
    public WebElement featureCheckBox(String feature){

        return featureNames.stream().filter(element -> element.getText().contains(feature)).toList().get(0).findElement(By.xpath("../..//input[@type=\"checkbox\"]"));

    }


    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Select Block\"]/../following-sibling::span/span")
     WebElement editBlockDropList;

    public List<WebElement> editblocks(){
        try {

            editBlockDropList.click();
        }catch (NoSuchElementException i){
            System.out.println("no blocks drop list to click");
        }

        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Select Floor\"]/../following-sibling::span/span")
     WebElement editFloorDropList;

    public List<WebElement> editFloors()
    {
        editFloorDropList.click();

        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Select Unit Type\"]/../following-sibling::span/span")
    WebElement editUnitTypeDropList;
    public List<WebElement> editUnitType() {

        editUnitTypeDropList.click();

        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Select Hall\"]/../following-sibling::span/span")
     WebElement editHallDropList;
    public List<WebElement> halls() {
        editHallDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Select Kitchen\"]/../following-sibling::span/span")
     WebElement editKitchenDropList;
    public List<WebElement> kitchens() {
        editKitchenDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Number Of Single Beds\"]")
    public WebElement editSingleBedsField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Number Of Double Beds\"]")
    public WebElement editDoubleBedsField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[(text()=\"Save\")]")
    public WebElement editDialogSaveButton;


}
