package alia.nazeel.pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class P01_LoginPage extends BasePage
{


    public P01_LoginPage(WebDriver driver){
super(driver);      }
    @SuppressWarnings("unused")
    @FindBy(id="usern")
   public WebElement usernameField;

    @SuppressWarnings("unused")
    @FindBy(id="pass")
   public WebElement passwordField;

    @SuppressWarnings("unused")
    @FindBy(id="acc")
    public WebElement accField;

    @SuppressWarnings("unused")
    @FindBy(xpath = "//button[contains(text(),\"Login\")]")
    public WebElement loginButton;
    @SuppressWarnings("unused")
    @FindBy(xpath = "//span[contains(text(),\"Later\")]/..")
    public WebElement verificationButton;

    @SuppressWarnings("unused")
    @FindBy(xpath = "//input[@name=\"propertyNameOrCode\"]")
    public WebElement propertyNameField;
    @SuppressWarnings("unused")
    @FindBy(xpath = "//input[@placeholder=\"Search company\"]")
    public WebElement companyNameField;

    @SuppressWarnings("unused")
    @FindAll({@FindBy(xpath = "//kendo-grid-list//td/span")})
    public List<WebElement> companysList;


//    button[class="n-button n-button--primary-border ng-star-inserted"]
//    class="ng-star-inserted"
}
