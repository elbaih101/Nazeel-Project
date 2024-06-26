package alia.nazeel.pages.setuppages.properties_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

@SuppressWarnings("unused")
public class P06_4_SmsPage extends BasePage
{
    public P06_4_SmsPage(WebDriver driver){
       super(driver);
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
