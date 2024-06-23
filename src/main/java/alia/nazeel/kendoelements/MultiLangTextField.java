package alia.nazeel.kendoelements;

import alia.nazeel.templates.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class MultiLangTextField extends CustomWebElement {

    String firstLangText;
    String secondLangText;

    WebElement multiLangTextField;
    By menuBy = By.xpath("./../../following-sibling::div");
    By secondInput = By.xpath("./../../following-sibling::div/input[2]");
    By secondLangInputButtonBy = By.xpath("./following-sibling::button");

    public MultiLangTextField(WebElement multiLangTextField) {
        super(multiLangTextField);
        this.multiLangTextField = multiLangTextField;
    }

    public void sendKeys(String... text) {
        sendKeysFirstLangField(text[0]);
        if (text.length > 1) {
            sendKEysSecondLangField(text[1]);
        }
    }

    public void clear() {
        clearFirstLangField();
        clearSecondLangField();

    }

    WebElement secondLangField() {
        if (!multiLangTextField.findElement(menuBy).getAttribute("class").contains("show")) {
            clicktheMultiTextButton();
        }
        return multiLangTextField.findElement(secondInput);
    }

    private void clicktheMultiTextButton() {
        multiLangTextField.findElement(secondLangInputButtonBy).click();
    }

    public void clearFirstLangField() {
        multiLangTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

    public void clearSecondLangField() {
        secondLangField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        clicktheMultiTextButton();
    }

    public void sendKeysFirstLangField(String keys) {
        firstLangText = keys;
        multiLangTextField.sendKeys(keys);
    }

    public void sendKEysSecondLangField(String keys) {
        secondLangText = keys;
        secondLangField().sendKeys(keys);
        clicktheMultiTextButton();
    }

    //Todo
    public String getFirstLangText() {
        return firstLangText = multiLangTextField.getAttribute("value");
    }

    public String getSecondLangText() {
        secondLangText = secondLangField().getAttribute("value");
        clicktheMultiTextButton();
        return secondLangText;
    }
}
