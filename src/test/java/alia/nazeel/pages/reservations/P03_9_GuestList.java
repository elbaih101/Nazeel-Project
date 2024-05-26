package alia.nazeel.pages.reservations;

import alia.nazeel.kendoelements.ChipsMultiselect;
import alia.nazeel.kendoelements.Grid;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;

public class P03_9_GuestList {

    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;
    String basePath = "//units-rates-popup-wizard";

    public P03_9_GuestList(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


    @FindBy(xpath = "//reservation-guest-list-wizard//button[contains(@class,\"green-border\")]")
    public WebElement addDependentButton;
    @FindBy(xpath = "//kendo-dialog//button[contains(@class,\"n-button n-button--primary\")]")
    public WebElement confirmDependentsButton;

    @FindBy(xpath = "//app-reservation-group-guest-list-wizard//app-chips-multiselect")
     WebElement reservationRooms;
    public ChipsMultiselect reservationRooms(){
        return  new ChipsMultiselect(reservationRooms);
    }

    @FindBy(xpath = "//app-reservation-group-guest-list-wizard//button[contains(text(),\"Name\")]")
    public WebElement nameButton;
    @FindBy(xpath = "//app-reservation-group-guest-list-wizard//button[contains(text(),\"Mobile Number\")]")
    public WebElement mobileButton;
    @FindBy(xpath = "//app-reservation-group-guest-list-wizard//input[contains(@class,\"form-control\")]")
    public WebElement searchField;
    @FindBy(xpath = "//app-reservation-group-guest-list-wizard//button[contains(text(),\"Search\")]")
    public WebElement searchButton;
    @FindBy(xpath = "//app-reservation-group-guest-list-wizard//kendo-grid[@kendogridselectby]")
    WebElement searchGrid;

    public Grid searchGrid() {
        return new Grid(searchGrid);
    }

    @FindBy(xpath = "//app-reservation-group-guest-list-wizard//kendo-grid[@kendogridtemplateediting]")
    WebElement guestsGrid;

    public Grid guestsGrid() {
        return new Grid(guestsGrid);
    }

    public void checkOutGuest(WebElement guest) {
        guestsGrid().getGridCell(guest, 7).findElement(By.xpath(".//button[contains(@class,\"button--danger\")]")).click();
        new P00_multiPurposes(driver).swalPopUp().confirm();
    }

    public void undoCheckOutGuest(WebElement guest) {
        guestsGrid().getGridCell(guest, 7).findElement(By.xpath(".//button[contains(@class,\"button--green\")]")).click();
        new P00_multiPurposes(driver).swalPopUp().confirm();
    }

    public void editGuest(WebElement guest) {
        guestsGrid().getGridCell(guest, 7).findElement(By.xpath(".//button[contains(@class,\"button--primary\")]//*[name()=\"use\" and contains(@*,\"icon-edit\")]")).click();
    }
public String getGuestInfo(WebElement guest){
   actions.moveToElement(guestsGrid().getGridCell(guest, 7).findElement(By.xpath(".//button[contains(@class,\"button--primary\")]//*[name()=\"use\" and contains(@*,\"icon-edit\")]")),5,4).perform();
   return new P00_multiPurposes(driver).bottomToolTip.getText();
        //button[contains(@class,"button--primary")]//*[name()="use" and contains(@*,"icon-indo")]
}
}
