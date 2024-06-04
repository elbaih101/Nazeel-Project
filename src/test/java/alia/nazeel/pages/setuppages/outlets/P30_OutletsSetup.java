package alia.nazeel.pages.setuppages.outlets;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.MultiLangTextField;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class P30_OutletsSetup extends BasePage
{
    public P30_OutletsSetup(WebDriver driver) {
       super(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newOutletButton;
    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> opStates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> codes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> descriptions;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> categories_Items;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"6\"]")
    List<WebElement> moreActions;


    public WebElement outletEditButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[1]"));
    }

    public WebElement outletDeleteButton(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[2]"));
    }

    public WebElement outletName(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement outletOPStatus(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement outletCode(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement outletDescription(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement outletStatus(WebElement outlet) {
        return outlet.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]//*[name()=\"use\"]"));
    }
    //end grid //

@FindBy(css = "div[role=\"dialog\"] kendo-combobox[name=\"operating-status\"]")
public KendoComboBox opStatus;
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[@name=\"outlet-code\"]")
    public WebElement outletCodeField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//input[contains(@class,\"dropdown-toggle form-control\")]")
    public MultiLangTextField outletNameField;
    @FindBy(name = "text-area")
    public WebElement descriptionField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[@type=\"submit\"]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;

    @FindBy(xpath = "//div[@class=\"swal2-actions\"]//button[contains(@class,\"confirm\")]")
    public WebElement popUpCOnfirmButton;
    /// end pop up ///

    /// filter ///
    @FindBy(xpath = "//div[@class=\"n-table__top-btns\"]//button[contains(@class,\"button--primary\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//kendo-combobox[@name=\"status\"]")
    public KendoComboBox statusFilter;

    @FindBy(xpath = "//kendo-combobox[@name=\"operating-status\"]")
    public KendoComboBox opStatusFilter;

    @FindBy(id = "name")
    public WebElement nameFilterField;
    @FindBy(css = "[name = \"outlet-code\"] input")
    public WebElement outletFilterCodeField;

    @FindBy(xpath = "//button[@class=\"button button--primary\"]")
    public WebElement searchFilterButton;


    /* to be used in the Step Defination
      costMap.put("categ", costCenter.costCenterCategory(selectedCost).getText());
        if (costCenter.costCenterStatus(selectedCost).getAttribute("xlink:href").contains("icon-check"))
            costMap.put("stat", "Acive");
        else if (costCenter.costCenterStatus(selectedCost).getAttribute("xlink:href").contains("icon-minus"))
            costMap.put("stat", "Inacive");
     */
}
