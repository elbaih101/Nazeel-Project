package org.example.pages.unit_setup_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class P08_1_NewUnitPage {

    public P08_1_NewUnitPage() {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P08_1_NewUnitPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(id = "unit-number")
    public WebElement unitNumberField;

    @FindBy(xpath = "//label[contains(text(),\"Unit Class\")]/..//span/span/span")
    WebElement unitClassDropList;
    @FindBy(xpath = "//label[contains(text(),\"Unit Type\")]/..//span/span/span")
    WebElement unitTypeDropList;
    @FindBy(xpath = "//label[contains(text(),\"Block\")]/..//span/span/span")
    WebElement BlocksDroplistButton;
 @FindBy(xpath = "//label[contains(text(),\"Floor\")]/..//span/span/span")
    WebElement FloorsDroplistButton;

}
