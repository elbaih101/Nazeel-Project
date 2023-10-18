package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class P03_ReservationPage
{
    public P03_ReservationPage()
    {
        PageFactory.initElements(Hooks.driver, this);
    }

    @FindBy(css = "button[class=\"n-button n-button--green ng-star-inserted\"]")
    public WebElement newReservationButton;

    //waitfor that
    //http://staging.nazeel.net:9002/reservations/wizard/add

    @FindBy(xpath = "//label[contains(text(),\"Reservation source \")]//following-sibling::kendo-dropdownlist")
    public WebElement reservationSource;


    @FindBy(xpath = "//kendo-popup//div[contains(text(),\"test3\")]")
    public WebElement test3ReservationSource;

    @FindBy(xpath = "//label[contains(text(),\"Visit purpose\")]//following-sibling::kendo-dropdownlist")
    public WebElement visitPurposeButton;

    @FindBy(xpath = "//kendo-popup//li[contains(text(),\"Family or friends\")]")
    public WebElement familyOrFriendsSelection;

    @FindBy(xpath = "//span[contains(text(),\"Select unit now\")]")
    public WebElement selectUnitNowSpan;


    @FindBy(xpath = "//kendo-dialog//kendo-panelbar-item")
    public WebElement panelBar;

    @FindBy(xpath = "//kendo-dialog-actions//span[contains(text(),\"Confirm\")]/..")
    public WebElement confirmBtn;

    @FindBy(xpath = "//span[contains(text(),\"Select guest now\")]/..")
    public WebElement selectGestButton;

    @FindBy(id="guestFormDialogContainer")
    public WebElement guestFormDialogContainer;
    @FindBy(xpath = "//button[contains(text(),\"Save reservation\")]")
    public WebElement saveReservationButton;

    @FindBy(xpath = "//kendo-dialog-actions//button")
    public WebElement reservationSummarySaveButton;
    @FindBy(xpath = "//div[@class=\"toast-message\"]")
    public  WebElement toastMessage;
    @FindBy(xpath = "//div[@class=\"page-header\"]//h2//span")
    public WebElement reservationStatus;

}
