package alia.nazeel.pages.mutlipurposes;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class P00_1_PaytabsExternalPage extends BasePage
{
    public P00_1_PaytabsExternalPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id=\"number\"]")
    public WebElement cardNumberField;
    @FindBy(xpath = "//input[@id=\"expmonth\"]")
    public WebElement expMonth;
    @FindBy(xpath = "//input[@id=\"expyear\"]")
    public WebElement expYear;
    @FindBy(xpath = "//input[@id=\"cvv\"]")
    public WebElement cvv;
    @FindBy(xpath = "//a[@href=\"#customer-info\"]")
    public WebElement customeInfoButton;
    @FindBy(xpath = "//input[@id=\"customer-address\"]")
    public WebElement address;
    @FindBy(xpath = "//input[@id=\"customer-email\"]")
    public WebElement email;
    @FindBy(xpath = "//input[@id=\"customer-zip\"]")
    public WebElement areaCode;

    @FindBy(xpath = "//input[@id=\"customer-city\"]")
    public WebElement city;
    @FindBy(xpath = "//select[@id=\"customer-country\"]")
    public WebElement countrydroplist;
    public Select country(){
        countrydroplist.click();
        return new Select(countrydroplist);
    }
    @FindBy(xpath = "//select[@id=\"customer-state\"]")
    public WebElement statedroplist;
    public Select state(){
        statedroplist.click();
        return new Select(statedroplist);
    }
    @FindBy(xpath = "//button[@id=\"payBtn\"]")
    public WebElement payButton;
    @FindBy(xpath = "//a[@onclick=\"return confirm_cancel();\"]")
    public WebElement canselButton;
    @FindBy(xpath = "//input[@value=\"Authenticated\"]")
    public WebElement authenticateButton;

    @FindBy(xpath = "//input[@value=\"Rejected\"]")
    public WebElement rejectButton;
    @FindBy(xpath = "//h2")
    public WebElement transactionState;


}
