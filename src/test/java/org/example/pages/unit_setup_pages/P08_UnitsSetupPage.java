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

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//div[contains(text(),\"New Unit\")]")
    public WebElement newUnitButton;
    @FindBy(xpath = "//button[contains(text(),\"Filter\")]")
    public WebElement filterButton;
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

    @FindBy(xpath = "//div[contains(@class,\"unit-card unit-setup\")]")
    public List<WebElement> unitsCards;

    @FindBy(xpath = "//div[@class=\"unit-card__flags\"]//*[name()='svg']")
    public List<WebElement> UnitsFlags;
//    public WebElement unitCardInactivFlag(WebElement unitCard) {
//        return unitCard.findElement(By.xpath("//div[@class=\"unit-card__flags\"]//*[name()='svg']"));
//    }

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

    public WebElement confirmDeleteButton() {
        return deleteUnitDialog.findElement(By.xpath("//button[contains(text(),\"Confirm\")]"));
    }

    public WebElement discardDeleteButton() {
        return deleteUnitDialog.findElement(By.xpath("//button[contains(text(),\"Discard\")]"));
    }

    public WebElement deltedUnitNumber() {
        return deleteUnitDialog.findElement(By.xpath("//div[contains(text(),\"Unit Number\")]/following-sibling::div"));
    }

    //////////////// filter menue /////////////
    @FindBy(xpath = "//button[contains(text(),\"Search\")]")
    public WebElement filterSearchButton;

    @FindBy(xpath = "//input[@placeholder=\"Select Status\"]/../following-sibling::span/span")
    WebElement statusdropList;

    public List<WebElement> statusList() {
        wait.until(ExpectedConditions.elementToBeClickable(statusdropList));
        statusdropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//input[@placeholder=\"Select Block\"]/../following-sibling::span/span")
    WebElement blocksDropList;

    public List<WebElement> blocksList() {
        wait.until(ExpectedConditions.elementToBeClickable(blocksDropList));
        blocksDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//input[@placeholder=\"Select Floor\"]/../following-sibling::span/span")
    WebElement floorsDropList;

    public List<WebElement> floorsList() {
        wait.until(ExpectedConditions.elementToBeClickable(floorsDropList));
        floorsDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//input[@placeholder=\"Pick unit type\"]/../following-sibling::span/span")
    WebElement unitTypesDropList;

    public List<WebElement> unitTypesList() {
        wait.until(ExpectedConditions.elementToBeClickable(unitTypesDropList));
        unitTypesDropList.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    @FindBy(xpath = "//input[@placeholder=\"Enter Unit No.\"]")
    public WebElement filterUnitNumber;


//    @FindBy(xpath = "//input[@placeholder=\"Pick unit type\"]/../following-sibling::span/span")
//    WebElement unitGroupsDropList;
//
//    public List<WebElement> unitGroupsList() {
//        wait.until(ExpectedConditions.elementToBeClickable(unitGroupsDropList));
//        unitGroupsDropList.click();
//        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
//    }

}
