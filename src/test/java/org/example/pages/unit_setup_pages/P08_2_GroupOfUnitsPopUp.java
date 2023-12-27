package org.example.pages.unit_setup_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;

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
    public List<WebElement> blocks(){
        try {
            blocksDropList.click();

        }catch (NoSuchElementException e){
            System.out.println("");
        }
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Floor\")]//following-sibling::kendo-combobox/span/span/span")
     WebElement floorsDropList;
    public List<WebElement> floorss(){
        floorsDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Unit type\")]//following-sibling::kendo-combobox/span/span/span")
     WebElement unitsTypeDroplist;
    public List<WebElement> unitType(){
        unitsTypeDroplist.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Format\")]//following-sibling::kendo-combobox/span/span/span")
     WebElement formatDroplist;
    public List<WebElement> formats(){
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
  @FindBy(xpath = "//div[@role=\"dialog\"]//input[@placeholder=\"Select Block\"]/../following-sibling::span/span")
    public WebElement editBlockDropList;






}
