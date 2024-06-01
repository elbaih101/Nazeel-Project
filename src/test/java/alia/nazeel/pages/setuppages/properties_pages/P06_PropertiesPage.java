package alia.nazeel.pages.setuppages.properties_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@SuppressWarnings("unused")
public class P06_PropertiesPage extends BasePage
{
    public P06_PropertiesPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//Button[contains(text(),\" New Property  \")]")
    public WebElement newPropertyButton;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> propertiesNames;

    public WebElement propertyCode(WebElement property){
      return   property.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"1\"]"));
    }
}
