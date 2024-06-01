package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;

import java.util.List;

public class ChipsMultiselect {

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

}
