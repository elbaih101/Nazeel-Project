package alia.nazeel.pages.masterdata_pages;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.KendoSwitch;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

public class P15_MasterUnitsTypes extends BasePage {

    public P15_MasterUnitsTypes(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newUnitTypeButton;
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//app-master-unit-type//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    @FindBy(xpath = "//input[@id=\"name\"]")
    public WebElement nameSearchField;

    @FindBy(css = "kendo-combobox[id=\"status\"]")
    public KendoComboBox satausSearchDropList;


    ///  grid  ///////

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> typesNames;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]/span")
    public List<WebElement> typesStatuses;

    public WebElement typeStatus(WebElement typeName) {
        return typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"1\"]/span"));
    }


    public List<WebElement> moreActions(WebElement typeName) {
        WebElement moreMenu = typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"3\"]//div/div/div"));
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu));
        moreMenu.click();
        return moreMenu.findElements(By.xpath("..//div[@class=\"popup__content\"]/div"));
    }

    public WebElement viewButton(WebElement typeName) {
        return typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"3\"]//div/button[2]"));
    }

    public WebElement editButton(WebElement typeName) {
        return typeName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"3\"]//div/button[1]"));

    }


    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement unitTypeDialog;


    public WebElement typeNameField() {
        return unitTypeDialog.findElement(By.xpath(".//input"));
    }

    public KendoSwitch statusButton() {
        return new KendoSwitch( unitTypeDialog.findElement(By.cssSelector("kendo-switch[name=\"status\"]")));
    }

    public WebElement submitButton() {
        return unitTypeDialog.findElement(By.xpath("./kendo-dialog-actions/button[contains(@class,\"button--primary\")]"));
    }

    public WebElement modifyTypeButton() {
        return unitTypeDialog.findElement(By.xpath("./kendo-dialog-actions/button[contains(@class,\"button--primary\")][2]"));
    }

    public WebElement confirmDeleteButton() {
        return unitTypeDialog.findElement(By.xpath(".//button[contains(@class,\"n-button--danger \")]"));
    }


}
