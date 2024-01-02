package org.example.pages.reservations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@SuppressWarnings("unused")
public class P03_2_ReservationFinancialPage {

    public P03_2_ReservationFinancialPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

@FindBy(xpath = "//th/div/button[contains(@class,\"arrow\")]")
     WebElement moreAddOptionsButton;
    @FindBy(xpath = "//div[contains(text(),\"Digital Payment\")]")
    WebElement digitalPaymentOption;

    public WebElement digialPaymentButton(){
        moreAddOptionsButton.click();
        return digitalPaymentOption;
    }



}
