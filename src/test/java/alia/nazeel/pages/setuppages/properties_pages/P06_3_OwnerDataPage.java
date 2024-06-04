package alia.nazeel.pages.setuppages.properties_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@SuppressWarnings("unused")
public class P06_3_OwnerDataPage extends BasePage
{

    public P06_3_OwnerDataPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//Button[contains(text(),\"Next\")]")
    public WebElement nextButton;

}
