package alia.nazeel.pages.setuppages.rules_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P33_Penalties {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P33_Penalties(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newpenaltyButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;
    @FindBy(xpath = "//button[contains(@class,\"n-button--secondary\")]")
    public WebElement penalitiesSettingButton;
    @FindBy(xpath = "//span[contains(text(),\"Early Check-in\")]/preceding-sibling::kendo-switch")
    public WebElement earlyCheckInDetectSwitch;
    @FindBy(xpath = "//span[contains(text(),\"Late Check-out\")]/preceding-sibling::kendo-switch")
    public WebElement lateCheckOutDetectSwitch;
    @FindBy(xpath = "//span[contains(text(),\"Skip Cancel / No show\")]/preceding-sibling::kendo-switch")
    public WebElement skipCancel_NoShowPenalities;
    @FindBy(xpath = "//div[@role=\"popover\"]//button[contains(@class,\"button--primary\")]")
    public WebElement saveSettingsButton;


    /////end controls /////
    //// grid ////
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> names;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> amounts;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> types;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> calculatedOfs;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> categories;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]//*[name()=\"use\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"6\"]")
    List<WebElement> moreActions;


    public WebElement penaltyEditButton(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[1]"));
    }

    public WebElement penaltyDeleteButton(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]//button[2]"));
    }

    public WebElement penaltyName(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]"));
    }

    public WebElement penaltyAmount(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement penaltyType(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement penaltyCOf(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement penaltyCategory(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement penaltyStatus(WebElement penalty) {
        return penalty.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//*[name()=\"use\"]"));
    }

    //end grid
    //filter //
    public List<WebElement> statusesFilterList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"status\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }


    public List<WebElement> filterStatusesList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"status\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @FindBy(xpath = "//kendo-combobox[@name=\"calculatedOf\"]//input")
    public WebElement calculatedofField;

    public List<WebElement> calculatedOfList_Filter() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"calculatedOf\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> filterPenaltyTypeList() {
        driver.findElement(By.xpath("//kendo-combobox[@name=\"penaltyType\"]//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> categorysList_Filter() {
        driver.findElement(By.xpath("//label[contains(text(),\"Category\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public List<WebElement> penaltytypesList() {
        driver.findElement(By.xpath("//kendo-dropdownlist[@name=\"penaltyType\"]")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @FindBy(xpath = "//label[contains(text(),\"Category\")]/following-sibling::kendo-combobox//span[@title=\"clear\"]")
    public WebElement categorySelectionClearButton;

    @FindBy(xpath = "//kendo-combobox[@name=\"calculatedOf\"]//span[@title=\"clear\"]")
    public WebElement calcOfClearButton;
    @FindBy(id = "name")
    public WebElement nameFilterField;
    @FindBy(id = "undefined")
    public WebElement undefinedCheckBox;
    @FindBy(xpath = "//input[@placeholder=\"Enter Amount\"]")
    public WebElement amountFilterField;
    // end filter ///

    /// data entery ///
    @FindBy(xpath = "//input[@type=\"text\"][1]")
    public WebElement penaltyNameField;
    @FindBy(xpath = "//input[@type=\"text\"]/following-sibling::button[contains(@class,\"en_button\")]")
    public WebElement arabicButton;
    @FindBy(xpath = "//input[@type=\"text\"][2]")
    public WebElement penaltyArNameField;
    @FindBy(name = "text-area")
    public WebElement descriptionField;
    @FindBy(xpath = "//button[@type=\"submit\" and text()=\" Save \"]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;
    @FindBy(xpath = "//kendo-switch[@name=\"includeTax\"]")
    public WebElement taxExcludedSwitch;
    @FindBy(id = "amount")
    public WebElement amountFiled;
    @FindBy(xpath = "//div[@class=\"swal2-actions\"]//button[contains(@class,\"confirm\")]")
    public WebElement popUpCOnfirmButton;

}
