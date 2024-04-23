package org.example.pages;

import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P38_Outlets {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P38_Outlets(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


    @FindBy(xpath = "//div[contains(@class,\"outlet-card\")]")
    public List<WebElement> outletsList;

    public WebElement outletName(WebElement outlet) {
        return outlet.findElement(By.xpath("./div"));
    }

    @FindBy(className = "item-card")
    public List<WebElement> outletItems;

    public WebElement itemName(WebElement outletItem) {
        return outletItem.findElement(By.xpath("./div"));
    }

    @FindBy(name = "item-name")
    public WebElement filterItemNameFeld;
    @FindBy(name = "outletItemsPages.category")
    WebElement itemCategoriescomboBox;
    @FindBy(xpath = "//kendo-combobox[@textfield=\"outletNameItemNamePrice\"]")
    WebElement itemsListCombiBox;


    public List<WebElement> itemsList() {
        return new P00_multiPurposes(driver).getListItems(itemsListCombiBox);
    }

    public List<WebElement> itemsCategoriesList() {
        return new P00_multiPurposes(driver).getListItems(itemCategoriescomboBox);
    }

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> selectedItemsNames;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> selectedItemsPrices;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> selectedItemsQuantities;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"7\"]")
    public List<WebElement> selectedItemsDeletes;

    public WebElement itemPriceField(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"2\"]/span";
        locator = item.findElements(By.xpath(locator)).isEmpty() ? "..//td[@data-kendo-grid-column-index=\"2\"]//input" : locator;
        return item.findElement(By.xpath(locator));
    }

    public Double itemPriceAmount(WebElement itemPrice) {
        String price = itemPrice.getText();
        price = price.isEmpty() ? itemPrice.getAttribute("value") : price;
        return Double.parseDouble(price.trim());
    }

    public WebElement itemQuantity(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"3\"]//input";
        return item.findElement(By.xpath(locator));
    }

    public Double itemSubTotalAmount(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"4\"]";
        String subTotal = item.findElement(By.xpath(locator)).getText();
        return Double.parseDouble(subTotal.trim());
    }

    public Double itemDiscountAmount(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"5\"]";
        String discount = item.findElement(By.xpath(locator)).getText();
        return Double.parseDouble(discount.trim());
    }

    public WebElement insertDiscountButto(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"5\"]//*[@name()=\"use\"]";
        return item.findElement(By.xpath(locator));
    }

    public Double itemAmount(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"6\"]";
        String total = item.findElement(By.xpath(locator)).getText();
        return Double.parseDouble(total.trim());
    }

    public WebElement itemremoveButton(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"7\"]//button";
        return item.findElement(By.xpath(locator));
    }

    @FindBy(xpath = "//div[contains(text(),\"Total Amount\")]")
    public WebElement totalPriceSection;
    @FindBy(xpath = "//button[contains(text(),\"Next\")]")
    public WebElement nextButton;
    @FindBy(id = "complimentary")
    public WebElement complimentaryCheckBox;
    @FindBy(xpath = "//button[contains(text(),\"Walk-In order\")]")
    public WebElement walkinOrderButton;
    @FindBy(xpath = "//button[contains(text(),\"Transfer to Res\")]")
    public WebElement transToResButton;

    @FindBy(className = "input-with-dropdown")
    WebElement discountComboBox;

    public List<WebElement> discountTypes() {
        return new P00_multiPurposes(driver).getListItems(discountComboBox);
    }

    public WebElement discountValueField() {
        return discountComboBox.findElement(By.xpath(".//input"));
    }

    @FindBy(xpath = "//div[contains(@class,\"order-total\")]")
    WebElement orderTotals;

    public Double orderSubTotal() {
        WebElement subTotal = orderTotals.findElement(By.xpath(".//span[(text()=\"Sub-Total\")]/following-sibling::b"));
        return Double.parseDouble((subTotal.getText()));
    }

    public Double orderGenralDiscountAmount() {
        WebElement subTotal = orderTotals.findElement(By.xpath(".//span[(text()=\"General Discount\")]/following-sibling::b"));
        return Double.parseDouble((subTotal.getText()));
    }

    public Double orderAmountBeforeTaxes() {
        WebElement subTotal = orderTotals.findElement(By.xpath(".//span[(text()=\"Amount:\")]/following-sibling::b"));
        return Double.parseDouble((subTotal.getText()));
    }

    public Double orderTaxes() {
        WebElement subTotal = orderTotals.findElement(By.xpath(".//span[(text()=\" Tax(es) \")]/following-sibling::b"));
        return Double.parseDouble((subTotal.getText()));
    }
    public boolean inclusive() {
        return !orderTotals.findElements(By.xpath(".//span[(text()=\" Tax(es) \")]/span")).isEmpty();

    }

    public Double orderAmountAfterTaxes() {
        WebElement subTotal = orderTotals.findElement(By.xpath(".//span[(text()=\"Total Amount:\")]/following-sibling::b"));
        return Double.parseDouble((subTotal.getText()));
    }

    public Double orderBalance() {
        WebElement subTotal = orderTotals.findElement(By.xpath(".//span[(text()=\"Balance:\")]/following-sibling::b"));
        return Double.parseDouble((subTotal.getText()));
    }
}
