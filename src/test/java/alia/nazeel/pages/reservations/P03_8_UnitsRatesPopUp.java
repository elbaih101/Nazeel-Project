package alia.nazeel.pages.reservations;


import alia.nazeel.kendoelements.Grid;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P03_8_UnitsRatesPopUp {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;
    String basePath = "//units-rates-popup-wizard";

    public P03_8_UnitsRatesPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


    @FindBy(xpath = "//units-rates-popup-wizard//div[@class=\"calendar-container\"]")
    WebElement calenderGrid;
   public Grid calender(){return new Grid(calenderGrid);}
    @FindBy(xpath = "//units-rates-popup-wizard//kendo-dialog-actions//button[@class=\"button button--primary\"]")
    public WebElement confirmRatesButton;
}
