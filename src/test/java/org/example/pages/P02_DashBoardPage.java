package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class P02_DashBoardPage
{
    public P02_DashBoardPage(){
        PageFactory.initElements(Hooks.driver,this);

    }
    public P02_DashBoardPage(WebDriver driver){
    PageFactory.initElements(driver,this);
}

    @FindBy(css = "a[class=\"n-sidebar__item ng-star-inserted\"]")
    public WebElement ReservationLink;
    @FindBy(xpath = "//Button[contains(text(),\"Close\")]")
    public WebElement closeAnnouncementButton;
    @FindBy(xpath = "//a[@href=\"/setup\"]")
    public WebElement setupPageLink;

    @FindBy(xpath = "//button[contains(text(),\" Agree on contract terms \")]")
    public WebElement contractAgreementButton;

    @FindBy(xpath = "//a[@href=\"/master-data\"]")
    public List<WebElement> masterDataLink;

}
