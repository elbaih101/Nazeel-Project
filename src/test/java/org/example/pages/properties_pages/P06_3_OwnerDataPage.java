package org.example.pages.properties_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

@SuppressWarnings("unused")
public class P06_3_OwnerDataPage {

    public P06_3_OwnerDataPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }   public P06_3_OwnerDataPage(){
        PageFactory.initElements(Hooks.driver,this);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public Select genralListBox;

    @FindBy(xpath = "//Button[contains(text(),\"Next\")]")
    public WebElement nextButton;

}
