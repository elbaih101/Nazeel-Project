package org.example.pages.setuppages.unit_setup_pages;

import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("unused")
public class P07_UnitTypeCustomization {



    public P07_UnitTypeCustomization(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    //main page
    @FindBy(xpath = "//Button[contains(text(),\"New Unit Type\")]")
    public WebElement newTypeButton;

    @FindBy(xpath = "//div[contains(@class,\"n-pager__info\")]")
     public WebElement pagination;
    public boolean checkPagination(){
        int itemsCount=  Integer.parseInt(StringUtils.substringBetween(pagination.getText(),"-"," "));
        return unitTypesNames.size()==itemsCount;
    }
    //////////  /////////////////       ///////////////////     /////////////////////////////
    //new type page
    public final String newTypeUrl = "http://staging.nazeel.net:9002/units-management/unit-type-customization/add";
    @FindBy(xpath = "//kendo-combobox[@name=\"unitType\"]/span/span")
    WebElement unitTypeSelectionBox;
    @FindBy(xpath = "//textarea[1]")
    public WebElement unitDescription;
    @FindBy(xpath = "//input[@id=\"file-upload\"]")
    public WebElement imageUpload;
    @FindBy(xpath = "//button[@type=\"submit\"]")
    public WebElement submitButton;


    ////////////////////////////////////////////////////////////////////
    ///////////////////////units grid /////////////////////////
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> unitTypesNames;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> Descriptions;
    @FindBy(xpath = "//div[contains(@class,\"popup__item popup__item--red\")]")
    public WebElement unitTypeDeleteButton;

    public WebElement unitTypeDescription(String unitTypeName) {
        for (WebElement unitType : unitTypesNames) {
            if (unitType.getText().contains(unitTypeName)) {
                return unitType.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"1\"]"));
            }
        }
        return null;
    }

    public WebElement unitTypeEditButton(String unitTypeName) {
        for (WebElement unitType : unitTypesNames) {
            if (unitType.getText().contains(unitTypeName)) {
                return unitType.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"2\"]//button"));
            }
        }
        return null;
    }

    public WebElement moreMenuButton(String unitTypeName) {
        for (WebElement unitType : unitTypesNames) {
            if (unitType.getText().contains(unitTypeName)) {

                return unitType.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"2\"]/div/div/div"));
            }
        }
        return null;
    }


    public List<WebElement> selectUnitTypes() {
        unitTypeSelectionBox.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }


    //////// delete dialoge ///////
    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement deleteConfirmationDialog;

    public WebElement delDialogConfirmButton(){
        return deleteConfirmationDialog.findElement(By.xpath("//button[contains(text(),\"Confirm\")]"));
    }
    public WebElement deDialogUnitTypeName()
    {
        return deleteConfirmationDialog.findElement(By.xpath("//div[(text()=\"Unit Type\")]/following-sibling::div"));
    }
    public WebElement delDialogUnitTypeDescription(){
        return deleteConfirmationDialog.findElement(By.xpath("//div[(text()=\"Description\")]/following-sibling::div"));
    }
}
