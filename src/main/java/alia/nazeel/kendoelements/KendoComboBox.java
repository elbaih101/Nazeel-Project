package alia.nazeel.kendoelements;

import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


public class KendoComboBox {

    Logger logger = LoggerFactory.getLogger(KendoComboBox.class);
    WebElement comboBox;
    final By dropDownButtonBy = By.xpath(".//span[@class=\"k-select\"]");
    final By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    final By comboBoxInput = By.xpath(".//input");
    WebElement dropDownButton;
    List<WebElement> listItems;
    WebElement selectedItem;


    public KendoComboBox(WebElement list) {
        this.comboBox = list;
    }

    public KendoComboBox() {

    }

    public WebElement getSelectedinput() {
        return comboBox.findElement(comboBoxInput);
    }

    void getDropDownButton() {
        dropDownButton = comboBox.findElement(dropDownButtonBy);
    }

    void open() {
        getDropDownButton();
        dropDownButton.click();
    }

    public List<WebElement> getListItems() {
        return listItems = comboBox.findElements(listItemsBy);
    }

    public void selectByText(String text) {
        open();
        selectedItem = getListItems().stream().filter(i -> i.getText().equals(text)).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }

    public void selectBySearch(String text) {
        getSelectedinput().sendKeys(text);
        selectedItem = getListItems().stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }

    public void selectByTextIgnoreCase(String text) {
        open();
        selectedItem = getListItems().stream().filter(i -> i.getText().equalsIgnoreCase(text)).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }

    public void selectByTextContainsIgnoreCase(String text) {
        open();
        Utils.sleep(300);
        try {
                    selectedItem = getListItems().stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
             logger.error(e.getMessage()+"\n"+comboBox.getTagName()+"[class='"+comboBox.getAttribute("class")+"']"+ Arrays.toString(e.getStackTrace()));
             return;
        }
        selectedItem.click();
    }


    public void selectByIndex(int index) {
        open();
        Utils.sleep(300);
        selectedItem = getListItems().get(index);
        selectedItem.click();
    }

    public void clearSelection() {
        By clearBy = By.xpath(".//span[contains(@class,\"k-clear-value\")]");
        WebDriver driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));
        if (!Utils.isElementInvisible(clearBy, driver)) {
            WebElement clear = comboBox.findElement(clearBy);
            Actions action = (Actions) driver;
            action.moveToElement(comboBox, 3, 4).click().perform();
        } else
            getSelectedinput().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

}


