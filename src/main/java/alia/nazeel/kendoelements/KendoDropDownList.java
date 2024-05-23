package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;

import java.util.List;

public class KendoDropDownList implements WebElement {

    WebElement DropDownlist;
    By dropDownButtonBy = By.xpath(".//span[@class=\"k-select\"]");
    By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    WebElement dropDownButton;
    List<WebElement> listItems;
    WebElement selectedItem;

    public KendoDropDownList(WebElement list) {
        this.DropDownlist = list;
    }

    public KendoDropDownList() {

    }

    public void getDropDownButton() {
        dropDownButton = DropDownlist.findElement(dropDownButtonBy);
    }

    public void open() {
        getDropDownButton();
        dropDownButton.click();
    }

    public List<WebElement> getListItems() {
        open();
        return  listItems = DropDownlist.findElements(listItemsBy);
    }

    public void selectByText(String text) {
         selectedItem = getListItems().stream().filter(i->i.getText().equals(text)).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }


    public void selectByIndex(int index) {
        selectedItem = getListItems().get(index);
        Utils.sleep(300);
        selectedItem.click();
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
