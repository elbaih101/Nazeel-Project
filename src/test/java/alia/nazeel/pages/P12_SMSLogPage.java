package alia.nazeel.pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class P12_SMSLogPage extends BasePage
{
    public P12_SMSLogPage(WebDriver driver) {
 super(driver);   }


    @FindBy(xpath = "//button[contains(text(),\"View\")]")
    public WebElement viewButton;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> events;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> recepients;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> mobileNumbers;

    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> messagesBody;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public WebElement statuses;

    public List<WebElement> paytabsMessages() {
        List<WebElement> paytabsEvents = events.stream().filter(event -> event.getText().contains("PayTabs")).toList();
        List<WebElement> paytabsMessages = new ArrayList<>();
        paytabsEvents.forEach(event -> paytabsMessages.add(event.findElement(By.xpath(".."))));
        return paytabsMessages;
    }

    public WebElement msgBody(WebElement msg) {
        return msg.findElement(By.xpath(".//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement msgRecepient(WebElement msg) {
        return msg.findElement(By.xpath(".//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement msgMobileNum(WebElement msg) {
        return msg.findElement(By.xpath(".//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement msgStatus(WebElement msg) {
        return msg.findElement(By.xpath(".//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement msgSendDate(WebElement msg) {
        return msg.findElement(By.xpath(".//td[@data-kendo-grid-column-index=\"6\"]"));
    }

}
