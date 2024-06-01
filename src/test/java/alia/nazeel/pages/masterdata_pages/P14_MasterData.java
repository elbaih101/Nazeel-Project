package alia.nazeel.pages.masterdata_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class P14_MasterData extends BasePage
{
    public P14_MasterData(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//span[contains(text(),\"Units\")]")
    public WebElement unitsDropList;
    @FindBy(xpath = "//span[contains(text(),\"Services\")]")
    public WebElement servicesDropList;
    @FindBy(xpath = "//a[@href=\"/master-amenities/unitsTypes\"]")
    public WebElement unitsTypesLink;
    @FindBy(xpath = "//a[@href=\"/subscription-management/Subscriptions-Prices\"]")
    public WebElement subscriptionsPricesLink;

}
