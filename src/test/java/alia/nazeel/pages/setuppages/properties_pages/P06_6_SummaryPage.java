package alia.nazeel.pages.setuppages.properties_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class P06_6_SummaryPage extends BasePage
{

    public P06_6_SummaryPage(WebDriver driver){
        super(driver);
    }


    @FindBy(xpath = "//Button[contains(text(),\"Save\")]")
    public WebElement saveButton;

}
