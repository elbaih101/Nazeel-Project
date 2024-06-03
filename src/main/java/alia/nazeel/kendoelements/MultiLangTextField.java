package alia.nazeel.kendoelements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class MultiLangTextField {

    String firstlangText;
    String secondLangText;

    WebElement multiLangTextField;
    By secondInput = By.xpath("./../../following-sibling::div/input[2]");
    By secondLangInputButtonBy = By.xpath("./following-sibling::button");

    public MultiLangTextField(WebElement multiLangTextField) {
        this.multiLangTextField = multiLangTextField;
    }

    public void sendKEys(String... text) {
        sendKEysFirstLangField(text[0]);
        if (text.length > 1) {
            sendKEysSecondLangField(text[1]);
        }
    }

    public void clear() {
        multiLangTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        secondLangField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

    WebElement secondLangField() {
        multiLangTextField.findElement(secondLangInputButtonBy).click();
        return multiLangTextField.findElement(secondInput);
    }

    public void clearFirstLangField() {
        multiLangTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

    public void clearSecondLangField() {
        secondLangField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }
    public void sendKEysFirstLangField(String keys)
    {
        firstlangText=keys;
        multiLangTextField.sendKeys(keys);
    }
    public void sendKEysSecondLangField(String keys)
    {
        secondLangText=keys;
     secondLangField().sendKeys(keys);
    }
   //Todo
    public String getFirstLangText(){return firstlangText= multiLangTextField.getAttribute("value");}
    public String getSecondLangText(){return secondLangText= secondLangField().getAttribute("value");}
}
