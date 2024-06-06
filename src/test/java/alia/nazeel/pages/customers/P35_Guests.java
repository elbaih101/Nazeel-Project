package alia.nazeel.pages.customers;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.KendoDateTimePicker;
import alia.nazeel.kendoelements.KendoDropDownList;
import alia.nazeel.kendoelements.KendoSwitch;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class P35_Guests extends BasePage {
    public P35_Guests(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newGuestButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    @FindBy(xpath = "//label[contains(text(),\"Name\")]/following-sibling::input")
    public WebElement nameFilterField;
    @FindBy(xpath = "//label[contains(text(),\"Mobile\")]/following-sibling::input")
    public WebElement mobileFilterField;
    @FindBy(xpath = "//kendo-combobox[@id=\"status\"]")
    public KendoComboBox statusFilter;
    @FindBy(xpath = "//kendo-combobox[@id=\"nationality\"]")
    public KendoComboBox nationalityFilter;
    @FindBy(xpath = "//kendo-combobox[@id=\"guestClass\"]")
    public KendoComboBox guestClassFilter;
    @FindBy(xpath = "//kendo-combobox[@id=\"idType\"]")
    public KendoComboBox idTypeFilter;

    @FindBy(id = "guestNumber")
    public WebElement gusetNumberFilterField;
    @FindBy(id = "idNumber")
    public WebElement iDFilterField;

    //// end Filter     /////

    //////       Grid  //// ///
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> classes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> guestsNames;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> nationalities;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> mobiles;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> iDTypes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"6\"]")
    public List<WebElement> idNumbers;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"7\"]")
    List<WebElement> moreActions;

    public WebElement guestStatus(WebElement guest) {
        return guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement guestNationality(WebElement guest) {
        return guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement guestphone(WebElement guest) {
        return guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement guestIDType(WebElement guest) {
        return guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]"));
    }

    public WebElement guestIDNumber(WebElement guest) {
        return guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]"));
    }

    public WebElement guestName(WebElement guest) {
        return guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement guestEditButton(WebElement guest) {
        return guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"7\"]//button[2]"));
    }

    public WebElement guestDeleteButton(WebElement guest) {
        guest.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"7\"]//div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--red\")]"));
    }

    //////     end Grid  //// ///

    //// guest data ////

    @FindBy(name = "status")
    public KendoSwitch statusSwitch;
    @FindBy(xpath = "//input[@placeholder=\"Type first name\"]")
    public WebElement firstNameField;

    @FindBy(xpath = "//input[@placeholder=\"Type Second Name\"]")
    public WebElement secondNameField;

    @FindBy(xpath = "//input[@placeholder=\"Type middle name\"]")
    public WebElement middleNameField;
    @FindBy(xpath = "//input[@placeholder=\"Type last name\"]")
    public WebElement lastNameField;
    @FindBy(xpath = "//label[contains(text(),\"Gregorian\")]/following-sibling::kendo-datepicker//input")
    public KendoDateTimePicker dateOfBirth;


    @FindBy(xpath = "//kendo-combobox[@name=\"gender\"]")
    public KendoComboBox genderComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"nationality\"]")
    public KendoComboBox nationalityComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"GuestType\"]")
    public KendoComboBox guestTypeComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"idType\"]")
    public KendoComboBox idTypeComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"idSerial\"]")
    public KendoComboBox idSerialComboBox;

    @FindBy(id = "idNumber")
    public WebElement idNumberField;

    @FindBy(xpath = "//kendo-dropdownlist[@name=\"guestClass\"]")
    public KendoDropDownList guestClass;

    @FindBy(xpath = "//div[@class=\"img-upload img-upload--light\"]")
    public WebElement imageField;

    @FindBy(xpath = "//kendo-combobox[@name=\"dialCode\"]")
    public KendoComboBox dialCode;
    @FindBy(name = "mobileNumber")
    public WebElement mobileField;
    @FindBy(id = "email")
    public WebElement emailField;
    @FindBy(id = "workPlace")
    public WebElement workPlaceField;
    @FindBy(id = "workPhoneNo")
    public WebElement workPhoneNumField;
    @FindBy(id = "address")
    public WebElement addressField;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    public WebElement submitButton;
    @FindBy(xpath = "//button[contains(@class,\"n-button--danger\") and contains(text(),\"Confirm\")]")
    public WebElement confirmDeleteButton;
    ////// end guest data ///

    //// guest Notes //
    @FindBy(xpath = "//span[contains(text(),\"Guest Notes\")]")
    public WebElement guestNotesTab;
    @FindBy(xpath = "//label[contains(text(),\"Company Note\")]/following-sibling::multi-lang-ta//textarea")
    public WebElement companyNotesField;
    @FindBy(xpath = "//button[contains(text(),\"Company Note\")]")
    public WebElement addCompanyNoteButton;
    @FindBy(xpath = "//label[contains(text(),\"Property Note\")]/following-sibling::multi-lang-ta//textarea")
    public WebElement propertyNotesField;
    @FindBy(xpath = "//button[contains(text(),\"Property Note\")]")
    public WebElement addPropertyNoteButton;
    @FindBy(xpath = "//button[contains(text(),\"Save Changes\")]")
    public WebElement saveNotesButton;

    @FindBy(className = "guest-note__content")
    public List<WebElement> notesContentes;

    public WebElement noteHeader(WebElement noteContent) {
        return noteContent.findElement(By.xpath("./preceding-sibling::div[@class=\"guest-note__header\"]"));
    }

    public WebElement noteEditButton(WebElement noteContent) {
        WebElement moreActions = noteContent.findElement(By.xpath("./preceding-sibling::div[@class=\"guest-note__header\"]//div[contains(@class,\"guest-note__more\")]"));
        moreActions.click();
        return moreActions.findElement(By.xpath("./..//div[contains(text(),\"Edit\")]"));
    }

    public WebElement notedeleteButton(WebElement noteContent) {
        WebElement moreActions = noteContent.findElement(By.xpath("./preceding-sibling::div[@class=\"guest-note__header\"]//div[contains(@class,\"guest-note__more\")]"));
        moreActions.click();
        return moreActions.findElement(By.xpath("./..//div[contains(text(),\"Delete\")]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//textarea")
    public WebElement editNoteField;
    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(text(),\"Save Changes\")]")
    public WebElement saveEditNoteButton;


    ///end guest notes ////
    //// guest document ////
    @FindBy(xpath = "//span[contains(text(),\"Guest Documents\")]")
    public WebElement guestDocumentsTab;

    @FindBy(xpath = "//th//button")
    public WebElement addDocumentButton;
    @FindBy(id = "file-upload")
    public WebElement documentUploadField;
    @FindBy(xpath = "//input[@name=\"document-name\"]")
    public WebElement documentNameField;
    @FindBy(xpath = "//button[contains(text(),\"Add Document\")]")
    public WebElement submitDocumentButton;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> documentNames;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> documentNotes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> documentsCreationDates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    List<WebElement> documentMoreActions;

    public WebElement documentDownloadButton(WebElement document) {
        document.findElement(By.xpath("../..//td//button")).click();
        return document.findElement(By.xpath(".//div[contains(text(),\"Download\")]"));
    }

    public WebElement documentDeleteButton(WebElement document) {
        document.findElement(By.xpath("../..//td//button")).click();
        return document.findElement(By.xpath("./..//div[contains(text(),\"Delete\")]"));
    }

    @FindBy(xpath = "//button[contains(@class,\"button--danger\") and contains(text(),\"Yes\")]")
    public WebElement confirmDeleteDocumentButton;


    //// end guest Documents ////

}




