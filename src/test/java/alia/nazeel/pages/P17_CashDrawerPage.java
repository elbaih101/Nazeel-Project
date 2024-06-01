package alia.nazeel.pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P17_CashDrawerPage extends BasePage
{
    public P17_CashDrawerPage(WebDriver driver) {
 super(driver);   }

    @FindBy(xpath = "//div[contains(@class,\"popup__btn--purple\")]")
    WebElement moreActionsButton;

    public WebElement dropCashButton() {
        wait.until(ExpectedConditions.elementToBeClickable(moreActionsButton));
        moreActionsButton.click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),\"Drop Cash\")]"))));
        return driver.findElement(By.xpath("//span[contains(text(),\"Drop Cash\")]"));
    }

    @FindBy(xpath = "//drop-cash-form")
    WebElement popup;

    public WebElement dateTo() {
        return popup.findElement(By.xpath(".//kendo-datepicker//input"));
    }

    public WebElement timeTo() {
        return popup.findElement(By.xpath(".//kendo-timepicker//input"));
    }

    public WebElement checkButton() {
        return popup.findElement(By.xpath(".//button[@type=\"submit\"]"));
    }

    public WebElement customAmountRadioButton() {
        return popup.findElement(By.xpath(".//label[@id=\"customAmount\"]"));
    }

    public WebElement customAmountField() {
        return popup.findElement(By.xpath(".//input[@name=\"amount\"]"));
    }

    public WebElement saveButton() {
        return popup.findElement(By.xpath(".//td[@data-kendo-grid-column-index=\"4\"]//button[1]"));
    }

    public WebElement nextButton() {
        return popup.findElement(By.xpath(".//button[contains(text(),\"Next\")]"));
    }


}
