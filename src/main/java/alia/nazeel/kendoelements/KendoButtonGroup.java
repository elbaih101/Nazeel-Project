package alia.nazeel.kendoelements;

import alia.nazeel.templates.CustomWebElement;
import org.openqa.selenium.*;

import java.util.List;

public class KendoButtonGroup extends CustomWebElement
{
    WebElement buttonGroup;
    By buttonGroupBy = new By.ByTagName("kendo-buttongroup");
    final By buttonsBy = By.xpath(".//button");

    public KendoButtonGroup(WebElement element) {
        super(element);
        this.buttonGroup =element;
    }


    public void selectButtonByName(String name)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getText().equals(name))
            {
                button.click();
                break;
            }
        }
    }

    public void selectButtonByNameIgnoreCase(String name)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getText().equalsIgnoreCase(name))
            {
                button.click();
                break;
            }
        }
    }

    public void selectButtonByNameContainsIgnoreCase(String name)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getText().toLowerCase().contains(name.toLowerCase()))
            {
                button.click();
                break;
            }
        }
    }

    public void selectButtonByIndex(int index)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        if (index < buttons.size())
        {
            buttons.get(index).click();
        }
    }

    public WebElement getActiveButton()
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getAttribute("class").contains("k-state-active"))
            {
                return button;
            }
        }
        return null;

    }
}
