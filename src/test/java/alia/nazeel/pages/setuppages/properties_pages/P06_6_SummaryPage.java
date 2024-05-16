package alia.nazeel.pages.setuppages.properties_pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@SuppressWarnings("unused")
public class P06_6_SummaryPage {

    public P06_6_SummaryPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//Button[contains(text(),\"Save\")]")
    public WebElement saveButton;

}
