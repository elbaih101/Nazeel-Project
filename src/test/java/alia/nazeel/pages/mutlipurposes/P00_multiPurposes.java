package alia.nazeel.pages.mutlipurposes;

import alia.nazeel.kendoelements.SwalPopUp;
import alia.nazeel.templates.BasePage;
import org.apache.commons.lang3.StringUtils;
import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.time.Duration;

import java.util.List;

public class P00_multiPurposes extends BasePage
{
    public P00_multiPurposes(WebDriver driver) {
      super(driver);
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
    @FindBy(id = "propertyDropDown")
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

    public WebElement toolTip(WebElement element) {
        actions.moveToElement(element, 3, 4).perform();
        Utils.sleep(100);
        return driver.findElement(By.xpath("//div[@class=\"p-tooltip-text\"]"));
    }

    @FindBy(xpath = "//app-loading-page/*")
    public WebElement loadingAnimation;


    @FindBy(className = "toast-message")
    public List<WebElement> toastMsgs;

    public void waitLoading() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            // Wait until the loading animation disappears or becomes stale
            wait.withTimeout(Duration.ofSeconds(20))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .until(ExpectedConditions.invisibilityOf(loadingAnimation));
        } catch (Exception e) {
            // Handle any exceptions or logging here
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void assertToastMessageContains(String msg) {

        asrt.assertTrue(toastMsgs.getFirst().isDisplayed());
        asrt.assertTrue(toastMsgs.getFirst().getText().trim().toLowerCase().contains(msg.toLowerCase()), "actual : " + toastMsgs.getFirst().getText().trim().toLowerCase() + "\nExpected : " + msg.toLowerCase() + "\n");

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
        List<WebElement> buttons;
        buttons = popUp.findElements(By.xpath(".//button[contains(text(),\"Close\")]"));
        if (buttons.isEmpty()) {
            buttons = popUp.findElements(By.xpath(".//button[contains(@class,\"n-button--danger-border\")]"));
        }


        return buttons.getFirst();
    }

    @FindBy(xpath = "//div[contains(@class,\"swal2-container\")]")
    WebElement swalPopUp;
    public SwalPopUp swalPopUp(){
        return new SwalPopUp(swalPopUp);
    }
}
