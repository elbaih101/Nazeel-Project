package alia.nazeel.pages.setuppages.outlets;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.KendoSwitch;
import alia.nazeel.kendoelements.MultiLangTextField;
import alia.nazeel.kendoelements.SwalPopUp;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class P32_OutletItems extends BasePage
{
    public P32_OutletItems(WebDriver driver)
    {
        super(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newItemButton;

    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> prices;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> categories;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> outlets;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    List<WebElement> moreActions;

    public WebElement itemEditButton(WebElement outlet)
    {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//button[1]"));
    }

    public WebElement itemDeleteButton(WebElement outlet)
    {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//button[2]"));
    }

    public WebElement itemName(WebElement outlet)
    {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement itemOutlet(WebElement outlet)
    {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement itemPrice(WebElement outlet)
    {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement itemCategory(WebElement outlet)
    {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement itemStatus(WebElement outlet)
    {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]"));
    }
    //end grid //

    /// data entery///
    @FindBy(xpath = "//input[contains(@class,\"dropdown-toggle form-control\")]")
    public MultiLangTextField itemNameField;
    @FindBy(name = "text-area")
    public WebElement descriptionField;
    @FindBy(xpath = "//button[@type=\"submit\" and text()=\" Save \"]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public KendoSwitch statusSwitch;
    @FindBy(xpath = "//kendo-switch[@name=\"no-tax\"]")
    public KendoSwitch taxExemptedSwitch;
    @FindBy(xpath = "//kendo-switch[@name=\"no-price\"]")
    public KendoSwitch freeItemSwitch;
    @FindBy(xpath = "//kendo-switch[@name=\"priceIsUserDefined\"]")
    public KendoSwitch userDefinedPriceSwitch;

   @FindBy(xpath = "//kendo-combobox[@name=\"outlet\"]")
   public KendoComboBox outletComboBox;
   @FindBy(xpath = "//kendo-combobox[@name=\"category\"]")
   public KendoComboBox categoryComboBox;

   @FindBy(xpath = "//kendo-combobox[@name=\"type\"]")
   public KendoComboBox typeComboBox;


    @FindBy(xpath = "//kendo-numerictextbox[@name=\"price\"]//input")
    public WebElement priceInput_FilterField;
    @FindBy(css = "div.swal2-container")
    public SwalPopUp  swalpopUp;
    /// end Data entery///

    /// filter ///
    @FindBy(xpath = "//div[@class=\"n-table__top-btns\"]//button[contains(@class,\"button--primary\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//kendo-combobox[@name=\"status\"]")
    public KendoComboBox statusFilter;
    @FindBy(xpath = "//kendo-combobox[@name=\"outlet\"]")
    public KendoComboBox outletFilter;
    @FindBy(xpath = "//kendo-combobox[@name=\"category\"]")
    public KendoComboBox categoryFilter;
    @FindBy(id = "name")
    public WebElement nameFilterField;
    @FindBy(xpath = "//button[@class=\"button button--primary\"]")
    public WebElement searchFilterButton;

}
