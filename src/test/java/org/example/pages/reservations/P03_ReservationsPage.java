package org.example.pages.reservations;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("unused")
public class P03_ReservationsPage {
    public P03_ReservationsPage()
    {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P03_ReservationsPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    @FindBy(css = "button[class=\"n-button n-button--green ng-star-inserted\"]")
    public WebElement newReservationButton;







    // Reservations Grid
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> reservationsNumbers;

}
