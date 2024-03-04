package org.example.pages.reservations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P03_7_TaxesPopUp {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;


    public P03_7_TaxesPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> taxesNames;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> taxesAmounts;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> taxesMethods;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"4\"]//input")
    public List<WebElement> taxesInclusiveness;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"5\"]")
    public List<WebElement> taxesAppliedFors;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"6\"]")
    public List<WebElement> taxesStartDates;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"7\"]")
    public List<WebElement> taxesEndDates;
    @FindBy(xpath = "//div[@role=\"dialog\"]//td[@data-kendo-grid-column-index=\"8\"]//input")
    public List<WebElement> taxesApplyStatuses;

    @FindBy(xpath = "//div[@role=\"dialog\"]//button[contains(@class,\"button--primary\")]")
    public WebElement saveButton;

    public WebElement taxMethod(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement taxAppliedFor(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]"));
    }

    public WebElement taxApplyCheckBox(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"8\"]//input"));
    }

    public WebElement taxStartDate(WebElement tax) {
        WebElement dateField = tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"6\"]"));
        if (dateField.getText().isEmpty())
            return dateField.findElement(By.xpath(".//input"));
        else
            return dateField;
    }

    public WebElement taxEndDate(WebElement tax) {
        WebElement dateField = tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"7\"]"));
        if (dateField.getText().isEmpty())
            return dateField.findElement(By.xpath(".//input"));
        else
            return dateField;
    }

    public boolean isTaxIncusive(WebElement tax) {
        return tax.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]//input")).isSelected();
    }


}
