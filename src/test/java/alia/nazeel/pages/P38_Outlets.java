package alia.nazeel.pages;

import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.kendoelements.KendoMultiSelect;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;
import java.util.List;

public class P38_Outlets {

    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;

    public P38_Outlets(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


    @FindBy(xpath = "//div[contains(@class,\"outlet-card\")]")
    public List<WebElement> outletsList;

    public WebElement outletName(WebElement outlet) {
        return outlet.findElement(By.xpath("./div"));
    }

    @FindBy(className = "item-card")
    public List<WebElement> outletItems;


    @FindBy(name = "item-name")
    public WebElement filterItemNameFeld;
    @FindBy(name = "outletItemsPages.category")
    WebElement itemCategoriescomboBox;
    @FindBy(xpath = "//kendo-combobox[@textfield=\"outletNameItemNamePrice\"]")
    WebElement itemsListComboBox;


    public List<WebElement> itemsList() {
        return new P00_multiPurposes(driver).getListItems(itemsListComboBox);
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


    @FindBy(xpath = "//label[contains(text(),\"Status\")]/span")
    public WebElement orderStatus;
    @FindBy(xpath = "//label[contains(text(),\"Invoice\")]/span")
    public WebElement orderInvoiceNumber;
    @FindBy(xpath = "//kendo-grid[2]//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> receiptVouchersNums;

    /**
     * returns item price field from the order grid
     *
     * @param item WebElement item related cell
     * @return WebElement the field of the item Span/Input
     */
    public WebElement itemPriceField(WebElement item) {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"2\"]/span";
        locator = item.findElements(By.xpath(locator)).isEmpty() ? "..//td[@data-kendo-grid-column-index=\"2\"]//input" : locator;
        return item.findElement(By.xpath(locator));
    }

    /**
     * returns item price field from the item selection grid
     *
     * @return WebElement the field of the item Span/Input
     */
    public WebElement itemPriceField() {
        wait.withTimeout(Duration.ofMillis(100));
        String locator = "..//td[@data-kendo-grid-column-index=\"1\"]/span";
        locator = driver.findElements(By.xpath(locator)).isEmpty() ? "//td[@data-kendo-grid-column-index=\"1\"]//input" : locator;
        return driver.findElement(By.xpath(locator));
    }


    public Double itemPriceAmount(WebElement itemPriceField) {
        String price = itemPriceField.getText();
        price = price.isEmpty() ? itemPriceField.getAttribute("value") : price;
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
        String locator = "..//td[@data-kendo-grid-column-index=\"5\"]//*[name()=\"use\"]";
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
    @FindBy(xpath = "//button[contains(text(),\"Save\")]")
    public WebElement saveDiscountButton;

    @FindBy(xpath = "//span[contains(text(),\"General Discount\")]/preceding-sibling::div//*[name()=\"use\"]")
    public WebElement genralDisocuntButton;
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

    @FindBy(xpath = "//div[contains(@class,\"guest-name__actions\")]/div[1]")
    public WebElement selctGuestButton;
    @FindBy(xpath = "//div[contains(@class,\"guest-name__actions\")]/div[2]")
    public WebElement selctCorporateButton;
    @FindBy(name = "paymentMethod")
    WebElement paymentmethodCmpoBox;

    public List<WebElement> payMethodsList() {
        return new P00_multiPurposes(driver).getListItems(paymentmethodCmpoBox);
    }

    @FindBy(xpath = "//app-walk-in-pop-up//button[contains(text(),\"Add\")]")
    public WebElement addPayMethodButton;
    @FindBy(xpath = "//app-walk-in-pop-up//kendo-datepicker//input")
    public WebElement issueDateField;
    @FindBy(xpath = "//kendo-timepicker//input")
    public WebElement issueTimeField;
    @FindBy(xpath = "//app-walk-in-pop-up//button[contains(text(),\"Create Order\")]")
    public WebElement submitOrderButton;

    @FindBy(css = "app-transfer-order-to-reservation kendo-multiselect")
    WebElement resStatusMultiSelect;

    public KendoMultiSelect resStatusMultiSelect() {
        return new KendoMultiSelect(resStatusMultiSelect);
    }
    @FindBy(css = "app-transfer-order-to-reservation kendo-grid")
    WebElement resGrid;

    public KendoGrid resGrid() {
        return new KendoGrid(resGrid);
    }
    @FindBy(xpath = "//app-transfer-order-to-reservation//label[contains(text(),\"Res. No.\")]//following-sibling::input")
    public WebElement reservationNumberSearchField;
    @FindBy(css = "app-transfer-order-to-reservation button")
    public WebElement searchReservationButton;
    @FindBy(css = "app-add-modfiy-outlet-order-units-issue-date-popup kendo-dialog-actions button.n-button--primary")
    public WebElement confirmResSelectionButton;
}
