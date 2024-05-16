package alia.nazeel.pages.customers;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P35_Guests {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P35_Guests(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
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

    public List<WebElement> filterStatusesList() {
        driver.findElement(By.xpath("//kendo-combobox[@id=\"status\"]//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> filterNationalityList() {
        driver.findElement(By.xpath("//kendo-combobox[@id=\"nationality\"]//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> filterGuestClassList() {
        driver.findElement(By.xpath("//kendo-combobox[@id=\"guestClass\"]//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> filterIdTypeList() {
        driver.findElement(By.xpath("//kendo-combobox[@id=\"idType\"]//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

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

    @FindBy(name="status")
    public WebElement statusSwitch;
    @FindBy(xpath = "//input[@placeholder=\"Type first name\"]")
    public WebElement firstNameField;

    @FindBy(xpath = "//input[@placeholder=\"Type Second Name\"]")
    public WebElement secondNameField;

    @FindBy(xpath = "//input[@placeholder=\"Type middle name\"]")
    public WebElement middleNameField;
    @FindBy(xpath = "//input[@placeholder=\"Type last name\"]")
    public WebElement lastNameField;
    @FindBy(xpath = "//label[contains(text(),\"Gregorian\")]/following-sibling::kendo-datepicker//input")
    public WebElement dateOfBirthField;


    @FindBy(xpath = "//kendo-combobox[@name=\"gender\"]")
    public WebElement genderComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"nationality\"]")
    public WebElement nationalityComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"GuestType\"]")
    public WebElement guestTypeComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"idType\"]")
    public WebElement idTypeComboBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"idSerial\"]")
    public WebElement idSerialComboBox;

    public List<WebElement> getListItems(WebElement comboBox) {
        comboBox.findElement(By.xpath(".//span[@class=\"k-select\"]")).click();
       Utils.sleep(300);
        return listItems;
    }

    public WebElement clearSelectionButton(WebElement comboBox) {
       WebElement clear =comboBox.findElement(By.xpath(".//span[contains(@class,\"k-clear-value\")]"));
        actions.moveToElement(comboBox, 3, 4).perform();
        return clear;
    }

    @FindBy(id = "idNumber")
    public WebElement idNumberField;

    public List<WebElement> classesList() {
        driver.findElement(By.xpath("//kendo-dropdownlist[@name=\"guestClass\"]//span[@class=\"k-select\"]")).click();
        return listItems;
    }

    @FindBy(xpath = "//div[@class=\"img-upload img-upload--light\"]")
    public WebElement imageField;

    public List<WebElement> dialCodes() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"dialCode\"]//span[@class=\"k-select\"]")).click();
        return listItems;
    }

    @FindBy(xpath = "//kendo-combobox[@name=\"dialCode\"]//input")
    public WebElement dialcodeField;
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

    @FindBy(className="guest-note__content")
    public List<WebElement>notesContentes;

    public WebElement noteHeader(WebElement noteContent){
        return noteContent.findElement(By.xpath("./preceding-sibling::div[@class=\"guest-note__header\"]"));
    }

    public WebElement noteEditButton(WebElement noteContent) {
        WebElement moreActions=  noteContent.findElement(By.xpath("./preceding-sibling::div[@class=\"guest-note__header\"]//div[contains(@class,\"guest-note__more\")]"));
        moreActions.click();
         return moreActions.findElement(By.xpath("./..//div[contains(text(),\"Edit\")]"));
    }
    public WebElement notedeleteButton(WebElement noteContent) {
        WebElement moreActions=  noteContent.findElement(By.xpath("./preceding-sibling::div[@class=\"guest-note__header\"]//div[contains(@class,\"guest-note__more\")]"));
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
    @FindBy(id="file-upload")
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

    public WebElement documentDownloadButton(WebElement document){
        document.findElement(By.xpath("../..//td//button")).click();
        return document.findElement(By.xpath(".//div[contains(text(),\"Download\")]"));
    }
    public WebElement documentDeleteButton(WebElement document){
        document.findElement(By.xpath("../..//td//button")).click();
        return document.findElement(By.xpath("./..//div[contains(text(),\"Delete\")]"));
    }
    @FindBy(xpath = "//button[contains(@class,\"button--danger\") and contains(text(),\"Yes\")]")
    public WebElement confirmDeleteDocumentButton;






    //// end guest Documents ////

}




