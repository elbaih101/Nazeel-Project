package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class P01_LoginPage
{
    public P01_LoginPage()
    {
        PageFactory.initElements(Hooks.driver,this);
    }

    public P01_LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    @FindBy(id="usern")
   public WebElement usernameField;

    @FindBy(id="pass")
   public WebElement passwordField;

    @FindBy(id="acc")
    public WebElement accField;

    @FindBy(xpath = "//button[contains(text(),\"Login\")]")
    public WebElement loginButton;
    @FindBy(xpath = "//span[contains(text(),\"Later\")]/..")
    public WebElement verificationButton;

    @FindBy(xpath = "//input[@name=\"propertyNameOrCode\"]")
    public WebElement propertyNameField;
    @FindBy(xpath = "//input[@placeholder=\"Search company\"]")
    public WebElement companyNameField;

    @FindAll({@FindBy(xpath = "//kendo-grid-list//td/span")})
    public List<WebElement> companysList;


//    button[class="n-button n-button--primary-border ng-star-inserted"]
//    class="ng-star-inserted"
}
