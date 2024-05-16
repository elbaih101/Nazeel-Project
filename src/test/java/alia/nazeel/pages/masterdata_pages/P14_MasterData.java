package alia.nazeel.pages.masterdata_pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P14_MasterData {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P14_MasterData(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
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