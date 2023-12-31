package org.example.pages.properties_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("unused")
public class P06_PropertiesPage {
    public P06_PropertiesPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }   public P06_PropertiesPage(){
        PageFactory.initElements(Hooks.driver,this);
    }


    @FindBy(xpath = "//Button[contains(text(),\" New Property  \")]")
    public WebElement newPropertyButton;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> propertiesNames;
}
