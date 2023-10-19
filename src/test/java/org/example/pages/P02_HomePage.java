package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class P02_HomePage
{
    public P02_HomePage(){
        PageFactory.initElements(Hooks.driver,this);

    }
    public P02_HomePage(WebDriver driver){
    PageFactory.initElements(driver,this);
}

    @FindBy(css = "a[class=\"n-sidebar__item ng-star-inserted\"]")
    public WebElement ReservationLink;
}
