package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class KendoDropDownList {

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
    }
