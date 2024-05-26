package alia.nazeel.pages.setuppages.financialpages;

import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.time.Duration;
import java.util.List;

public class P28_DiscountTypes {
    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;


    public P28_DiscountTypes(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    ///// controls /////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newDiscountButton;
    ////   Filter
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;

    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;

    //grid//
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> moveable;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> types;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> reportNames;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    List<WebElement> descriptions;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> moreActions;
    //end grid //

    //// pop up///
    public List<WebElement> discountsList() {
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//label[contains(text(),\"Discount\")]/following-sibling::kendo-combobox//span[@class=\"k-select\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    @FindBy(xpath = "//kendo-combobox[@name=\"discount-type\"]//input")
    public WebElement discountField;

    @FindBy(xpath = "//label[contains(text(),\"Name\")]/following-sibling::div//input")
    public WebElement reportNameField;
    @FindBy(xpath = "//textArea")
    public WebElement descriptionField;
    @FindBy(xpath = "//kendo-dialog-actions//button[contains(@class,\"button--primary \")]")
    public WebElement submitButton;
    @FindBy(xpath = "//kendo-switch[@name=\"status\"]")
    public WebElement statusSwitch;

    /// end pop up ///
    public WebElement deleteButton(WebElement discount) {
        discount.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//div/div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--red\") and contains(text(),\"Delete\")]"));
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"button--danger\")][2]")
    public WebElement confirmDeleteButton;

    public WebElement deactivateButton(WebElement discount) {
        discount.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//div/div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--red\") and contains(text(),\"Deactivate\")]"));
    }

    public WebElement activateButton(WebElement discount) {
        discount.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//div/div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--green\") and contains(text(),\"Activate\")]"));
    }

    public WebElement discountDescription(WebElement discount) {
        return discount.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement discountState(WebElement discount) {
        return discount.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }
    public WebElement discountMoveHandle(WebElement discount){
        return discount.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"0\"]//div[contains(@class,\"cdk-drag-handle\")]"));
    }
}
