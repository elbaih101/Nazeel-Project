package alia.nazeel.pages.setuppages.properties_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

@SuppressWarnings("unused")
public class P06_3_OwnerDataPage extends BasePage
{

    public P06_3_OwnerDataPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public Select genralListBox;

    @FindBy(xpath = "//Button[contains(text(),\"Next\")]")
    public WebElement nextButton;

}
