package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class KendoDropDownList implements WebElement {

    WebElement dropDownlist;
    final By dropDownButtonBy = By.xpath(".//span[@class=\"k-select\"]");
    final By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    WebElement dropDownButton;
    List<WebElement> listItems;
    WebElement selectedItem;

    public KendoDropDownList(WebElement list) {
        this.dropDownlist = list;
    }

    public KendoDropDownList() {

    }

    public void getDropDownButton() {
        dropDownButton = dropDownlist.findElement(dropDownButtonBy);
    }

    public void open() {
        getDropDownButton();
        dropDownButton.click();
    }

    public List<WebElement> getListItems() {
        open();
        return  listItems = dropDownlist.findElements(listItemsBy);
    }

    public void selectByText(String text) {
         selectedItem = getListItems().stream().filter(i->i.getText().equals(text)).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }
    public void selectByTextIgnoreCase(String text)
    {
        selectedItem = getListItems().stream().filter(i -> i.getText().equalsIgnoreCase(text)).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }
    public void selectByTextContainsIgnoreCase(String text)
    {
        selectedItem = getListItems().stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }


    public void selectByIndex(int index) {
        selectedItem = getListItems().get(index);
        Utils.sleep(300);
        selectedItem.click();
    }
    public void clearSelection(WebDriver driver) {
        Actions action = (Actions) driver;
        WebElement clear = dropDownlist.findElement(By.xpath(".//span[contains(@class,\"k-clear-value\")]"));
        action.moveToElement(dropDownlist, 3, 4).click().perform();
    }
    @Override
    public void click() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public String getDomProperty(String name) {
        return WebElement.super.getDomProperty(name);
    }

    @Override
    public String getDomAttribute(String name) {
        return WebElement.super.getDomAttribute(name);
    }

    @Override
    public String getAttribute(String name) {
        return "";
    }

    @Override
    public String getAriaRole() {
        return WebElement.super.getAriaRole();
    }

    @Override
    public String getAccessibleName() {
        return WebElement.super.getAccessibleName();
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return List.of();
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public SearchContext getShadowRoot() {
        return WebElement.super.getShadowRoot();
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String propertyName) {
        return "";
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }
}
