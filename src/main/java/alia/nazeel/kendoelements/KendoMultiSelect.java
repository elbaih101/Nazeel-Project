package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;

import java.util.List;


public class KendoMultiSelect implements WebElement
{
    WebElement multiSelect;
    @SuppressWarnings("SpellCheckingInspection")
    final By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    final By checkbox = By.xpath("./input");
    private List<WebElement> listItems;
    List<WebElement> selectedItems;

    public KendoMultiSelect(WebElement multiSelect)
    {
        this.multiSelect = multiSelect;
    }

    public KendoMultiSelect()
    {

    }


    public void open()
    {
        multiSelect.click();
    }

    public List<WebElement> getListItems()
    {
        open();
        Utils.sleep(300);
        return listItems = multiSelect.findElements(listItemsBy);
    }

    public void selectByText(String ...text)
    {
        getListItems().forEach(listItem -> {
            WebElement checkBox = listItem.findElement(checkbox);
            if (checkBox.isSelected() &&!Utils.containsString(text,listItem.getText()))
                listItem.click();
            else if (Utils.containsString(text,listItem.getText())&& !checkBox.isSelected())
                listItem.click();
        });
    }

    public void selectByTextIgnoreCase(String ...text)
    {
        getListItems().forEach(listItem -> {
            WebElement checkBox = listItem.findElement(checkbox);
            if (checkBox.isSelected() && !Utils.containsStringIgnoreCase(text, listItem.getText()))
                listItem.click();
            else if (Utils.containsStringIgnoreCase(text, listItem.getText())&& !checkBox.isSelected())
                listItem.click();
        });
    }

    public void selectByTextContainsIgnoreCase(String ...text)
    {
        getListItems().forEach(listItem -> {

            WebElement checkBox = listItem.findElement(checkbox);
            if (checkBox.isSelected() && !Utils.containsStringThatContainsIgnoreCase(text, listItem.getText()))
                listItem.click();
            else if (Utils.containsStringThatContainsIgnoreCase(text, listItem.getText())&& !checkBox.isSelected())
                listItem.click();
        });
    }

    public void unselectAll()
    {
        getListItems().forEach(listItem -> {
            WebElement checkBox = listItem.findElement(checkbox);
            if (checkBox.isSelected())
                listItem.click();
        });

    }

    public void selectAll()
    {
        getListItems().forEach(listItem -> {
            WebElement checkBox = listItem.findElement(checkbox);
            if (!checkBox.isSelected())
                listItem.click();
        });
    }

    public void unselectByText(String ...text)
    {
        getListItems().forEach(listItem -> {
            WebElement checkBox = listItem.findElement(checkbox);
            if (checkBox.isSelected() && Utils.containsStringThatContainsIgnoreCase(text, listItem.getText()))
                listItem.click();
        });

    }

    public void selectByIndex(int ...index)
    {
        for (int i = 0; i < getListItems().size(); i++)
        {
            WebElement checkBox = listItems.get(i).findElement(checkbox);

            if (checkBox.isSelected()&& Utils.isIndexInArray(index, i))
                listItems.get(i).click();
            else if (!checkBox.isSelected()&&!Utils.isIndexInArray(index, i))
                listItems.get(i).click();
        }
    }
    public void unselectByIndex(int ...index){
        for (int i = 0; i < getListItems().size(); i++)
        {
            WebElement checkBox = listItems.get(i).findElement(checkbox);

            if (checkBox.isSelected()&& Utils.isIndexInArray(index, i))
                listItems.get(i).click();
            else if (!checkBox.isSelected()&&!Utils.isIndexInArray(index, i))
                listItems.get(i).click();
        }
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
