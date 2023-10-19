package org.example.pages.properties_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class P06_6_SummaryPage {

    public P06_6_SummaryPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }   public P06_6_SummaryPage(){
        PageFactory.initElements(Hooks.driver,this);
}


    @FindBy(xpath = "//Button[contains(text(),\"Save\")]")
    public WebElement saveButton;

}
