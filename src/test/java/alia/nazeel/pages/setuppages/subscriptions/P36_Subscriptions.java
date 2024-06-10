package alia.nazeel.pages.setuppages.subscriptions;

import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class P36_Subscriptions extends BasePage {

    public P36_Subscriptions(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//app-service-management//div[@class=\"page-header\"]//button[contains(text(),\"Subscription Order\")]")
    public WebElement newSubscriptionButton;

    @FindBy(xpath = "//app-service-management//div[contains(@class,\"page-header\")]//div[@class=\"n-button\"]")
    public WebElement walletBalanceDiv;


}
