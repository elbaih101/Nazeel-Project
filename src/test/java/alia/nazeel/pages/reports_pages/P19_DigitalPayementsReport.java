package alia.nazeel.pages.reports_pages;


 import alia.nazeel.tools.CustomWebDriverWait;
 import org.openqa.selenium.By;
 import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;
import java.util.List;

public class P19_DigitalPayementsReport {
    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;



    public P19_DigitalPayementsReport(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    public WebElement genralListBox;
    @FindBy(xpath = "//input[@placeholder=\"Generated Date From\"]")
    public WebElement dateFrom;
    @FindBy(xpath = "//input[@placeholder=\"Generated Date To\"]")
    public WebElement dateTo;
 @FindBy(xpath = "//input[@placeholder=\"Reservation Number\"]")
    public WebElement reservationNumberField;

    public List<WebElement> generatedFors() {
        driver.findElement(By.xpath("//label[contains(text(),\"Generated For\")]/following-sibling::kendo-combobox/span/span/span")).click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    public List<WebElement> statuses() {
        driver.findElement(By.xpath("//label[contains(text(),\"Status\")]/following-sibling::kendo-combobox/span/span/span")).click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    @FindBy(xpath = "//div[@class=\"guest-name-block\"]/div/div[2]")
    public WebElement guestSelectionButton;
     @FindBy(xpath = "//div[@class=\"guest-name-block\"]/div/div[1]")
    public WebElement discardGuestSelectionButton;

}
