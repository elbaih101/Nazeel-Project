package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;

import java.util.List;

public class ChipsMultiselect implements WebElement {

    WebElement chipsMultiSelect;
    List<WebElement> chips;

    public ChipsMultiselect(WebElement chipsMultiSelect) {
        this.chipsMultiSelect = chipsMultiSelect;
    }

    public ChipsMultiselect() {
    }

    public List<WebElement> getChips() {
        return chips = chipsMultiSelect.findElements(By.xpath(".//div[contains(@class,\"multiselect__item\")]"));
    }

    public void selectByText(String... text) {
        getChips().forEach(chip -> {
            boolean selected = chip.getAttribute("class").contains("selected");
            if (selected && !Utils.containsString(text, chip.getText()))
                chip.click();
            else if (Utils.containsString(text, chip.getText()) && !selected)
                chip.click();
        });
    }

    public void selectByTextIgnoreCase(String... text) {
        getChips().forEach(chip -> {
            boolean selected = chip.getAttribute("class").contains("selected");
            if (selected && !Utils.containsStringIgnoreCase(text, chip.getText()))
                chip.click();
            else if (Utils.containsStringIgnoreCase(text, chip.getText()) && !selected)
                chip.click();
        });
    }

    public void selectByTextContainsIgnoreCase(String... text) {
        getChips().forEach(chip -> {
            boolean selected = chip.getAttribute("class").contains("selected");
            if (selected && !Utils.containsStringThatContainsIgnoreCase(text, chip.getText()))
                chip.click();
            else if (Utils.containsStringThatContainsIgnoreCase(text, chip.getText()) && !selected)
                chip.click();
        });
    }

    public void selectByIndex(int index) {
        WebElement chip = getChips().get(index);
        boolean selected = chip.getAttribute("class").contains("selected");
        if (!selected)
            chip.click();
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
