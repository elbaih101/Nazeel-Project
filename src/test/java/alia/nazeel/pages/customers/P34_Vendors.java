package alia.nazeel.pages.customers;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class P34_Vendors extends BasePage
{
    public P34_Vendors(WebDriver driver) {
       super(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newVendorButton;

    //grid//
    @FindBy(css = " app-vendor-list kendo-grid")
    KendoGrid vendorsGrid;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> vendorsNames;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> vATs;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> Phones;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> emails;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> cRNos;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"6\"]")
    List<WebElement> moreActions;

    public WebElement vendorViewButton(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[1]"));
    }

    public WebElement vendorDeleteButton(WebElement vendor) {
        vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//div[contains(@class,\"table__icon\")]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//div[contains(@class,\"popup__item--red\")]"));
    }

    public WebElement vendorEditButton(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[2]"));
    }

    public WebElement vendorName(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]"));
    }

    public WebElement vendorStatus(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement vendorVAT(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement vendorPhone(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement vendorEmail(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement vendorCRNo(WebElement vendor) {
        return vendor.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]"));
    }
    //end grid //

    /// data entery///
    @FindBy(xpath = "//input[@placeholder=\"Vendor Name\"]")
    public WebElement vendorNameField;

    @FindBy(xpath = "//kendo-combobox[@name=\"dialCode\"]")
    public KendoComboBox dialCode;

    @FindBy(name = "mobileNumber")
    public WebElement mobileField;
    @FindBy(xpath = "//kendo-searchbar/input")
    public WebElement dialcodeField;
    @FindBy(name = "email")
    public WebElement emailField;
    @FindBy(name = "taxNumber")
    public WebElement vATField;
    @FindBy(name = "CommercialRegistrationNumber")
    public WebElement cRNoField;
    @FindBy(name = "postalCode")
    public WebElement postalCodeField;
    @FindBy(xpath = "//textarea[@placeholder=\"Description\"]")
    public WebElement descriptionField;
    @FindBy(xpath = "//textarea[@placeholder=\"Address\"]")
    public WebElement addressField;
    @FindBy(xpath = "//button[@type=\"submit\"]")
    public WebElement submitButton;
    @FindBy(xpath = "//button[contains(@class,\"button--danger-border\")]")
    public WebElement discardButton;
    @FindBy(name="status")
    public WebElement statusSwitch;
    @FindBy(xpath = "//button[contains(@class,\"swal2-confirm\")]")
    public WebElement confirmDeleteButton;
    /// end Data entery///

    /// filter ///
    @FindBy(xpath = "//div[@class=\"n-table__top-btns\"]//button[contains(@class,\"button--primary\")]")
    public WebElement filterButton;


    @FindBy(xpath = "//kendo-combobox[@id=\"status\"]")
    public KendoComboBox statusFilter;
    @FindBy(name="NameEn")
    public WebElement nameFilterField;
    @FindBy(name="order")
    public WebElement vATFilterField;
    @FindBy(name="vatRegNo")
    public WebElement cRNoFilterField;
    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchFilterButton;

}

