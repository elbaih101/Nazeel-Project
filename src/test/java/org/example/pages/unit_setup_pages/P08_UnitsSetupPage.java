package org.example.pages.unit_setup_pages;


import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class P08_UnitsSetupPage {

    public P08_UnitsSetupPage() {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P08_UnitsSetupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;

    @FindBy(xpath = "//Button[contains(text(),\"New Unit\")]")
    public WebElement newUnitButton;

    @FindBy(xpath = "//div[@class=\"unit-card unit-setup\"]")
    public List<WebElement> unitsCards;

    public WebElement unitNum(WebElement unitCard) {
        return unitCard.findElement(By.xpath("//p[@class=\"unit-card__no\"]"));
    }

    public WebElement unitType(WebElement unitCard) {
        return unitCard.findElement(By.xpath("//p[@class=\"unit-card__type\"]"));
    }
}
