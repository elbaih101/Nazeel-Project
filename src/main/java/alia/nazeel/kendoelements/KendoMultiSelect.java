package alia.nazeel.kendoelements;

import alia.nazeel.templates.CustomWebElement;
import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;

import java.util.List;


public class KendoMultiSelect extends CustomWebElement
{
    WebElement multiSelect;
    final By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    final By checkbox = By.xpath("./input");
    private List<WebElement> listItems;
    List<WebElement> selectedItems;

    public KendoMultiSelect(WebElement multiSelect)
    {
        super(multiSelect);
        this.multiSelect = multiSelect;
    }



     void open()
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
    public boolean selectionExist(){
        boolean bol = multiSelect.findElements(By.xpath(".//li")).isEmpty();
        return !bol;
    }
}
