package org.example.pages.setuppages.unit_setup_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("unused")
public class P09_BaseRatePage {
    //Todo : finish the Base Rate page automation

    public P09_BaseRatePage() {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P09_BaseRatePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(),\"Edit\")]")
    public WebElement editBaseRateButton;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> lowWeekDaysRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> highWeekDaysRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> minimumRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> monthlyRates;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]//input[@role=\"spinbutton\"]")
    public List<WebElement> minimumMonthlyRates;


}
