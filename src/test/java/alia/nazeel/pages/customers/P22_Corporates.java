package alia.nazeel.pages.customers;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.kendoelements.MultiLangTextField;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class P22_Corporates extends BasePage {
    public P22_Corporates(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newCorporateButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    @FindBy(xpath = "//label[contains(text(),\"Corporate Name\")]/following-sibling::input")
    public WebElement nameSearchField;
    @FindBy(xpath = "//label[contains(text(),\"Mobile\")]/following-sibling::input")
    public WebElement mobileSearchField;
    @FindBy(xpath = "//label[contains(text(),\"Discount\")]/following-sibling::input")
    public WebElement discountSearchField;
    @FindBy(xpath = "//label[contains(text(),\"Email\")]/following-sibling::input")
    public WebElement emailSearchField;
    @FindBy(xpath = "//label[contains(text(),\"Person Name\")]/following-sibling::input")
    public WebElement cNameSearchField;
    @FindBy(xpath = "//label[contains(text(),\"Person Phone\")]/following-sibling::input")
    public WebElement cPhoneSearchField;
    @FindBy(xpath = "//input[@name=\"vatRegNo\"]")
    public WebElement vatSearchField;

    @FindBy(xpath = "//label[contains(text(),\"Status\")]/following-sibling::kendo-combobox")
    KendoComboBox filterStatusComboBox;

    //// end Filter     /////

    //////       Grid  //// ///
    @FindBy(css = "app-corporates-list kendo-grid")
    KendoGrid corporatesGrid;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> corporates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> corPhones;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> emails;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> vats;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    List<WebElement> persons;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"6\"]")
    List<WebElement> personsPhones;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"7\"]")
    List<WebElement> discounts;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"8\"]")
    List<WebElement> actionsGrid;

    public WebElement corporateEditButton(WebElement corporate) {
        return corporate.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"8\"]//button[2]"));
    }

    public WebElement deleteButton(WebElement corporate) {
        corporate.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"8\"]//div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--red\")]"));
    }

    //////     end Grid  //// ///


    //// corporate Details ///

    @FindBy(xpath = "//label[contains(text(),\"Corporate Name \")]/following-sibling::div//input")
    public WebElement corpoateNameField;

    @FindBy(id = "postalCode")
    public WebElement postalCodeField;
    //   @FindBy(xpath = "//label[contains(text(),\"Postal Code\")]/following-sibling::div//input")


    @FindBy(xpath = "//label[contains(text(),\"VAT\")]/following-sibling::div//input")
    public WebElement vatField;
    @FindBy(xpath = "//label[contains(text(),\"Discount\")]/following-sibling::input")
    public WebElement discountAmountField;
    @FindBy(xpath = "//input[@id=\"CommercialRegistrationNumber\"]")
    public WebElement cRNumberField;

    @FindBy(xpath = "//label[contains(text(),\"Discount Method\")]/following-sibling::kendo-combobox")
    KendoComboBox discountMethodComboBx;

    @FindBy(css = "kendo-combobox[name=\"Country\"]")
    public KendoComboBox countriesComboBox;

    @FindBy(xpath = "//label[contains(text(),\"City\")]/following-sibling::div//input")
    public WebElement cityFieldEn;
    @FindBy(xpath = "//label[contains(text(),\"District\")]/following-sibling::div//input")
    public WebElement districtFieldEn;

    public WebElement arField(WebElement enField) {
        enField.findElement(By.xpath("./following-sibling::button")).click();
        return enField.findElement(By.xpath("./../../following-sibling::div/input[2]"));
    }

    @FindBy(xpath = "//label[contains(text(),\"Street\")]/following-sibling::div//input")
    public MultiLangTextField streetFieldEn;

    @FindBy(xpath = "//label[contains(text(),\"Building Number\")]/following-sibling::div//input")
    public WebElement bNoField;
    @FindBy(xpath = "//label[contains(text(),\"Secondary No.\")]/following-sibling::div//input")
    public WebElement secNoField;

    @FindBy(xpath = "//label[contains(text(),\"Address\")]/following-sibling::textarea")
    public WebElement addressField;

    @FindBy(xpath = "//label[contains(text(),\"Email\")]/following-sibling::input")
    public WebElement emaiField;
    @FindBy(xpath = "//label[contains(text(),\"Corporate Phone\")]/following-sibling::input")
    public WebElement corPhoneField;
    @FindBy(xpath = "//label[contains(text(),\"Contact Person Name\")]/following-sibling::input")
    public WebElement cPersonNameFied;

    @FindBy(xpath = "//input[@name=\"contactPersonPhone\"]")
    public WebElement cPersonPhoneFied;

    @FindBy(css = "kendo-combobox[name=\"countryDialcode\"]")
    public KendoComboBox countryDialCodeComboBox;
    @FindBy(xpath = "//button[contains(@class,\"n-button n-button--primary\")]")
    public WebElement saveButton;
    //in reservation popup


}
