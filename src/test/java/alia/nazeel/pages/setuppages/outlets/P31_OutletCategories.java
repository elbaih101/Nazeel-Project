package alia.nazeel.pages.setuppages.outlets;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.MultiLangTextField;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class P31_OutletCategories extends BasePage {
    public P31_OutletCategories(WebDriver driver) {
        super(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newCategoryButton;

    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> nTMPCateg;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> outlets;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    List<WebElement> moreActions;

    public WebElement categoryEditButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[1]"));
    }

    public WebElement categoryDeleteButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//button[2]"));
    }

    public WebElement categoryName(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement categoryOutlet(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement categoryNTMP(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement categoryStatus(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]"));
    }
    //end grid //

    /// pop up ///
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[contains(@class,\"dropdown-toggle form-control\")]")
    public MultiLangTextField categoryNameField;
    @FindBy(name = "text-area")
    public WebElement descriptionField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[@type=\"submit\"]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;
    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"outlet\"]")
    public KendoComboBox outletsComboBox;

    @FindBy(xpath = "//div[@role=\"dialog\"]//kendo-combobox[@name=\"ntmp\"]")
    public KendoComboBox ntmpCategCmboBox;
    @FindBy(xpath = "//div[@class=\"swal2-actions\"]//button[contains(@class,\"confirm\")]")
    public WebElement popUpCOnfirmButton;

    /// end popup///

    /// filter ///
    @FindBy(xpath = "//div[@class=\"n-table__top-btns\"]//button[contains(@class,\"button--primary\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//kendo-combobox[@name=\"status\"]")
    public KendoComboBox statusFiletr;
    @FindBy(xpath = "//kendo-combobox[@name=\"outlet\"]")
    public KendoComboBox outletsFilter;
    @FindBy(xpath = "//kendo-combobox[@name=\"ntmp\"]")
    public KendoComboBox ntmpFilter;
    @FindBy(id = "name")
    public WebElement nameFilterField;
    @FindBy(xpath = "//button[@class=\"button button--primary\"]")
    public WebElement searchFilterButton;

}
