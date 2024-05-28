package alia.nazeel.pages.reservations;


import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;

public class P03_8_UnitsRatesPopUp {

    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;
    String basePath = "//units-rates-popup-wizard";

    public P03_8_UnitsRatesPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


    @FindBy(xpath = "//units-rates-popup-wizard//div[@class=\"calendar-container\"]")
    WebElement calenderGrid;
   public KendoGrid calender(){return new KendoGrid(calenderGrid);}
    @FindBy(xpath = "//units-rates-popup-wizard//kendo-dialog-actions//button[@class=\"button button--primary\"]")
    public WebElement confirmRatesButton;
}
