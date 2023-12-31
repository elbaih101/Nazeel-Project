package org.example.pages.properties_pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P06_4_SmsPage {
final WebDriverWait wait;
    public P06_4_SmsPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }   public P06_4_SmsPage(){
        PageFactory.initElements(Hooks.driver,this);
        wait=new WebDriverWait(Hooks.driver, Duration.ofSeconds(20));
    }
    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;

    @FindBy(xpath = "//kendo-combobox[@name=\"sms_provider\"]/span/span")
     WebElement smsProviderSelectionBox;
    @FindBy(xpath = "//kendo-combobox[@name=\"smsSenderNames\"]/span/span/span")
     WebElement smsSenderNameSelectionBox;

    @FindBy(xpath = "//input[@name=\"Name\"]")
    public WebElement smsBalanceField;

    @FindBy(xpath = "//Button[contains(text(),\"Next\")]")
    public WebElement nextButton;

public List<WebElement> smsprovider(){
    smsProviderSelectionBox.click();
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
}
public List<WebElement> smsSender(){
    wait.until(ExpectedConditions.elementToBeClickable(smsSenderNameSelectionBox));
    smsSenderNameSelectionBox.click();
    return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
}
}
