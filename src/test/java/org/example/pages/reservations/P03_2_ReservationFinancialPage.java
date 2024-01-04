package org.example.pages.reservations;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@SuppressWarnings("unused")
public class P03_2_ReservationFinancialPage {

    public P03_2_ReservationFinancialPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
@FindBy(xpath = "//h2")
WebElement reservationNumberHeader;
@FindBy(xpath = "//th/div/button[contains(@class,\"arrow\")]")
     WebElement moreAddOptionsButton;
    @FindBy(xpath = "//div[contains(text(),\"Digital Payment\")]")
    WebElement digitalPaymentOption;

    public WebElement digialPaymentButton(){
        moreAddOptionsButton.click();
        return digitalPaymentOption;
    }

    public int reservationNum(){
        String reservationNumber = StringUtils.substringAfter(reservationNumberHeader.getText().trim(), " Reservation ").trim();
        try {
            return Integer.parseInt(reservationNumber);
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string");
        }
        System.out.println("Continuing execution...");
        return 0;
    }
@FindBy(xpath = "//div[@class=\"financial-summary\"]//div[contains(text(),\"Balance\")]/following-sibling::div/span")
    public WebElement balance;


}
