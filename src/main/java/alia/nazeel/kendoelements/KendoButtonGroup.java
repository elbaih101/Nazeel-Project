package alia.nazeel.kendoelements;

import org.openqa.selenium.*;

import java.util.List;

public class KendoButtonGroup implements WebElement
{
    WebElement buttonGroup;
    By buttonGroupBy = new By.ByTagName("kendo-buttongroup");
    final By buttonsBy = By.xpath(".//button");


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

    @Override
    public void click()
    {

    }

    @Override
    public void submit()
    {

    }

    @Override
    public void sendKeys(CharSequence... keysToSend)
    {

    }

    @Override
    public void clear()
    {

    }

    @Override
    public String getTagName()
    {
        return "";
    }

    @Override
    public String getDomProperty(String name)
    {
        return WebElement.super.getDomProperty(name);
    }

    @Override
    public String getDomAttribute(String name)
    {
        return WebElement.super.getDomAttribute(name);
    }

    @Override
    public String getAttribute(String name)
    {
        return "";
    }

    @Override
    public String getAriaRole()
    {
        return WebElement.super.getAriaRole();
    }

    @Override
    public String getAccessibleName()
    {
        return WebElement.super.getAccessibleName();
    }

    @Override
    public boolean isSelected()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }

    @Override
    public String getText()
    {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by)
    {
        return List.of();
    }

    @Override
    public WebElement findElement(By by)
    {
        return null;
    }

    @Override
    public SearchContext getShadowRoot()
    {
        return WebElement.super.getShadowRoot();
    }

    @Override
    public boolean isDisplayed()
    {
        return false;
    }

    @Override
    public Point getLocation()
    {
        return null;
    }

    @Override
    public Dimension getSize()
    {
        return null;
    }

    @Override
    public Rectangle getRect()
    {
        return null;
    }

    @Override
    public String getCssValue(String propertyName)
    {
        return "";
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException
    {
        return null;
    }
}
