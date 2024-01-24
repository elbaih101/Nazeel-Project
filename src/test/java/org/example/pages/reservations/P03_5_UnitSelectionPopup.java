package org.example.pages.reservations;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

public class P03_5_UnitSelectionPopup {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;

    public P03_5_UnitSelectionPopup() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P03_5_UnitSelectionPopup(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }
//todo :: finish the unit selection popup

    @FindBy(xpath = "//div[@class=\"usc-wid unit-card ng-star-inserted\"]")
    List<WebElement> unitCards;

    public List<WebElement> lockedUnits() {
        wait.until(ExpectedConditions.visibilityOfAllElements(unitCards));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        List<WebElement> lockedCards = unitCards.stream().filter(card -> !card.findElements(By.xpath(".//div[contains(@class,\"card__comments-flag\")]//*[name()='use' and contains(@*,\"lock\")]")).isEmpty()).toList();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return lockedCards;
    }

    public List<WebElement> availableUnits() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        List<WebElement> availableCards=unitCards.stream().filter(card -> card.findElements(By.xpath(".//div[contains(@class,\"card__comments-flag\")]//*[name()='use' and contains(@*,\"lock\")]")).isEmpty()).toList();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return availableCards;
    }

    @FindBy(xpath = "//kendo-dialog-actions//span[contains(text(),\"Confirm\")]/..")
    public WebElement confirmBtn;
    /////////// alert pop up ///////////
    @FindBy(xpath = "//div[contains(text(),\"Alert\")]")
    WebElement alertHeader;

    @FindBy(xpath = "//div[contains(@class,\"backdrop-show\")]//button[contains(@class,\"swal2-confirm\")]")
    public  List<WebElement> checkInConflictionConfirmBtn;
}


