package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@SuppressWarnings("unused")
public class P05_SetupPage {

    public P05_SetupPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }   public P05_SetupPage(){
        PageFactory.initElements(Hooks.driver,this);
    }


    @FindBy(xpath = "//span[contains(text(),\"Company\")]")
    public WebElement companyDroplist;

    @FindBy(xpath = "//a[@href=\"/company/propertysetup\"]")
    public WebElement propertiesLink;

    @FindBy(xpath = "//span[contains(text(),\"Blocks & Floors\")]")
    public WebElement blocksAndFloorsDroplist;
    @FindBy(xpath = "//a[@href=\"/block-Floor/blooks\"]")
    public WebElement blocksLink;

    @FindBy(xpath = "//a[@href=\"/block-Floor/floors\"]")
    public WebElement floorsLink;
    @FindBy(xpath = "//span[contains(text(),\"Units\")]")
    public WebElement unitsDroplist;
    @FindBy(xpath = "//a[@href=\"/units-management/unit-type-customization\"]")
    public WebElement typeCustomizationLink;
  @FindBy(xpath = "//a[@href=\"/units-management/unit-setup\"]")
    public WebElement unitSetupLink;

}
