package org.example.pages.unit_setup_pages;


import org.apache.commons.lang.StringUtils;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class P08_UnitsSetupPage {

    WebDriver driver;
    WebDriverWait wait;


    public P08_UnitsSetupPage() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public P08_UnitsSetupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }


    @FindBy(xpath = "//div[contains(text(),\"New Unit\")]")
    public WebElement newUnitButton;
    @FindBy(xpath = "//div[@class=\"arrow-button__icon\"]")
    WebElement groupUnitsDropList;
    @FindBy(xpath = "//div[@class=\"k-popup\"]")
    WebElement addGroupUnits;
    @FindBy(xpath = "//button[contains(text(),\" Edit Group Of Units \")]")
    public WebElement editGroupUnitsButton;

    public WebElement addGroupUnitsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(groupUnitsDropList));
        driver.switchTo().activeElement();
        groupUnitsDropList.click();
        return addGroupUnits;
    }

    @FindBy(xpath = "//div[@class=\"unit-card unit-setup\"]")
    public List<WebElement> unitsCards;

    public WebElement unitNum(WebElement unitCard) {
        return unitCard.findElement(By.xpath(".//p[@class=\"unit-card__no\"]"));
    }

    public WebElement unitType(WebElement unitCard) {
        return unitCard.findElement(By.xpath("//p[@class=\"unit-card__type\"]"));
    }

    public WebElement unitEditButton(WebElement unitCard) {
        return unitCard.findElement(By.xpath(".//div[@class=\"unit-card__action--primary\"][1]"));
    }

    public WebElement unitViewButton(WebElement unitCard) {
        return unitCard.findElement(By.xpath(".//div[contains(@class,\"unit-card__action--primary\")][2]"));
    }

    public WebElement unitDeleteButton(WebElement unitCard) {
        return unitCard.findElement(By.xpath(".//div[contains(@class,\"unit-card__action--red\")]"));
    }

    @FindBy(xpath = "//div[contains(text(),\"Total\")]")
    WebElement totalDiv;

    public int totalUnitNumber() {
        wait.until(ExpectedConditions.textMatches(By.xpath("//div[contains(text(),\"Total\")]"), Pattern.compile("^.*\\d$")));
//        wait.until(new Function <WebDriver, Boolean>() {public Boolean apply(WebDriver driver){return totalDiv.getText().matches("^.*\\\\d$");}
//        });
        String total = StringUtils.substringAfter(totalDiv.getText().trim(), "Total : ").trim();
        try {
            return Integer.parseInt(total);
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string");
        }
        System.out.println("Continuing execution...");
        return 0;
    }

    @FindBy(xpath = "//div[@role=\"dialog\"]")
    public WebElement deleteUnitDialog;

    public WebElement confirmDeleteButton(){
        return deleteUnitDialog.findElement(By.xpath("//button[contains(text(),\"Confirm\")]"));
    }
    public WebElement discardDeleteButton(){
        return deleteUnitDialog.findElement(By.xpath("//button[contains(text(),\"Discard\")]"));
    }
    public WebElement deltedUnitNumber(){return deleteUnitDialog.findElement(By.xpath("//div[contains(text(),\"Unit Number\")]/following-sibling::div"));}



}
