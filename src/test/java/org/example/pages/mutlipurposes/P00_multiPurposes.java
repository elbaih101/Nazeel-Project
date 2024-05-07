package org.example.pages.mutlipurposes;

import org.apache.commons.lang.StringUtils;
import org.example.Utils;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class P00_multiPurposes {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;
    JavascriptExecutor js;

    public P00_multiPurposes() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;

    }

    public P00_multiPurposes(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    ////List items ////
    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;
    public List<WebElement> getListItems(WebElement comboBox) {
        comboBox.findElement(By.xpath(".//span[@class=\"k-select\"]")).click();
        Utils.sleep(300);
        return listItems;
    }
    ////End List items ////
    @FindBy(xpath = "//select")
    public WebElement pageSize;

    public List<WebElement> pageSizes() {
        pageSize.click();
        return pageSize.findElements(By.xpath("./option"));
    }
//// user menu ////
    @FindBy(className = "user-menu")
    public WebElement userMenu;
    @FindBy(id="propertyDropDown")
    public WebElement propertiesComboBox;
    @FindBy(xpath = "//app-compnay-dropdown//kendo-combobox//input")
    public WebElement companyNameField;


    @FindBy(className = "user-menu__name")
    WebElement userNameDiv;


    public String userName() {
        actions.moveToElement(userNameDiv, 3, 4).perform();
        return bottomToolTip.getText();
    }
    @FindBy(className = "user-menu__branch")
    WebElement propertyNameSpan;


    public String propertyName() {
        actions.moveToElement(propertyNameSpan, 3, 4).perform();
        return bottomToolTip.getText();
    }
    //// end User Menu ////


    public WebElement secondLanguageField(WebElement multiLangField) {
        multiLangField.findElement(By.xpath("./following-sibling::button")).click();
        Utils.sleep(300);
        return multiLangField.findElement(By.xpath("./../../following-sibling::div/input[2]"));
    }


    @FindBy(xpath = "//div[contains(@class,\"p-tooltip-bottom\")]/div[@class=\"p-tooltip-text\"]")
    public WebElement bottomToolTip;
    public WebElement toolTip(WebElement element){
        actions.moveToElement(element, 3, 4).perform();
        Utils.sleep(100);
        return driver.findElement(By.xpath("//div[@class=\"p-tooltip-text\"]"));
    }
    @FindBy(xpath = "//div[@class=\"page-loading\"]")
    public WebElement loadingAnimation;


    @FindBy(className = "toast-message")
    public List <WebElement> toastMsgs;

    public void waitLoading() {
        wait.withTimeout(Duration.ofSeconds(50));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(loadingAnimation)));
    }

    @FindBy(xpath = "//div[contains(@class,\"n-pager__info\")]")
    WebElement pagination;

    public boolean checkPagination(List<WebElement> gridItems) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "on pagination");
        }
        int itemsCount = Integer.parseInt(StringUtils.substringBetween(pagination.getText(), "-", " "));
        System.out.println(gridItems.size() + " :" + itemsCount);
        return gridItems.size() == itemsCount;
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]")
    public WebElement popUp;

    public WebElement closeButton() {
        List<WebElement> buttons = new ArrayList<>();
        buttons = popUp.findElements(By.xpath(".//button[contains(text(),\"Close\")]"));
        if (buttons.isEmpty()) {
            buttons = popUp.findElements(By.xpath(".//button[contains(@class,\"n-button--danger-border\")]"));
        }


        return buttons.get(0);
    }
}
