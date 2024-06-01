package alia.nazeel.pages.reservations;


import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class P03_8_UnitsRatesPopUp extends BasePage
{
    String basePath = "//units-rates-popup-wizard";

    public P03_8_UnitsRatesPopUp(WebDriver driver) {
       super(driver);
    }


    @FindBy(xpath = "//units-rates-popup-wizard//div[@class=\"calendar-container\"]")
    WebElement calenderGrid;
   public KendoGrid calender(){return new KendoGrid(calenderGrid);}
    @FindBy(xpath = "//units-rates-popup-wizard//kendo-dialog-actions//button[@class=\"button button--primary\"]")
    public WebElement confirmRatesButton;
}
