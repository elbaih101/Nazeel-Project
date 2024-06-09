package alia.nazeel.pages.reservations;

import alia.nazeel.kendoelements.ChipsMultiselect;
import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class P03_9_GuestList extends BasePage {
    final private String groupGuestList = "//app-reservation-group-guest-list-wizard";

    public P03_9_GuestList(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//reservation-guest-list-wizard//button[contains(@class,\"green-border\")]")
    public WebElement addDependentButton;
    @FindBy(xpath = "//kendo-dialog//button[contains(@class,\"n-button n-button--primary\")]")
    public WebElement confirmDependentsButton;

    @FindBy(xpath = groupGuestList + "//app-chips-multiselect")
    public ChipsMultiselect reservationRooms;


    @FindBy(xpath = groupGuestList + "//button[contains(text(),\"Name\")]")
    public WebElement nameButton;
    @FindBy(xpath = groupGuestList + "//button[contains(text(),\"Mobile Number\")]")
    public WebElement mobileButton;
    @FindBy(xpath = groupGuestList + "//input[contains(@class,\"form-control\")]")
    public WebElement searchField;
    @FindBy(xpath = groupGuestList + "//button[contains(text(),\"Search\")]")
    public WebElement searchButton;
    @FindBy(xpath = groupGuestList + "//kendo-grid[@kendogridselectby]")
    WebElement searchGrid;

    public KendoGrid searchGrid() {
        return new KendoGrid(searchGrid);
    }

    @FindBy(xpath = groupGuestList + "//kendo-grid[@kendogridtemplateediting]")
    WebElement guestsGrid;

    public KendoGrid guestsGrid() {
        return new KendoGrid(guestsGrid);
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

    public String getGuestInfo(WebElement guest) {
        actions.moveToElement(guestsGrid().getGridCell(guest, 7).findElement(By.xpath(".//button[contains(@class,\"button--primary\")]//*[name()=\"use\" and contains(@*,\"icon-edit\")]")), 5, 4).perform();
        return new P00_multiPurposes(driver).bottomToolTip.getText();
        //button[contains(@class,"button--primary")]//*[name()="use" and contains(@*,"icon-indo")]
    }
}
