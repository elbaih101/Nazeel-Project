package alia.nazeel.pages.reservations;

import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;



import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class P03_5_UnitSelectionPopup {

    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;


    public P03_5_UnitSelectionPopup(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    //UnitType tab ///
    @FindBy(xpath = "//app-reservation-wizard//kendo-buttongroup/button[contains(text(),\"Unit Type\")]")
    public WebElement unitTypeButton;
    @FindBy(xpath = "//select-units-wizard//div[@class=\"row\"]/div")
    public List<WebElement> unitTypes;
    public WebElement addUnitOfTypeButtton(WebElement unitType){
        unitType.findElement(By.xpath("./div[contains(@class,\"unit-type-card\")]")).click();
        return unitType.findElement(By.xpath(".//button[2]"));
    }
    public WebElement removeUnitOfTypeButtton(WebElement unitType){
        unitType.findElement(By.xpath("./div[contains(@class,\"unit-type-card\")]")).click();
        return unitType.findElement(By.xpath(".//button[1]"));
    }
    public WebElement unitsCountOfType(WebElement unitType){
        return unitType.findElement(By.xpath(".//span[@class=\"Font_count\"]"));
    }
    @FindBy(xpath = "//app-reservation-wizard//kendo-panelbar-item")
    public WebElement unitsselectionpanel;
    @FindBy(xpath = "//div[@class=\"usc-wid unit-card ng-star-inserted\"]")
    public List<WebElement> unitCards;

    public List<WebElement> lockedUnits() {
        wait.until(ExpectedConditions.visibilityOfAllElements(unitCards));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        List<WebElement> lockedCards = unitCards.stream().filter(card -> !card.findElements(By.xpath(".//div[contains(@class,\"card__comments-flag\")]//*[name()='use' and contains(@*,\"lock\")]")).isEmpty()).toList();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return lockedCards;
    }

    public WebElement availableUnits() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        WebElement availableCards = unitCards.stream().filter(card -> card.findElements(By.xpath(".//div[contains(@class,\"card__comments-flag\")]//*[name()='use' and contains(@*,\"lock\")]")).isEmpty()).findFirst().orElseThrow();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return availableCards;
    }

    @FindBy(xpath = "//select-units-wizard//kendo-dialog-actions//button[@type=\"submit\"]")
    public WebElement confirmBtn;

    @FindBy(className = "Load_More")
    public WebElement loadMoreLink;

    public List<String> unitNums(WebElement floor) {
        List<WebElement> unitNumsElements = floor.findElements(By.xpath(".//p[contains(@class,\"unit-card__no\")]"));
        List<String> unitNums = new ArrayList<>();
        unitNumsElements.forEach(element -> unitNums.add(element.getText()));
        return unitNums;
    }

    @FindBy(xpath = "//kendo-panelbar-item")
    public List<WebElement> floorsPanels;
    /////////// alert pop up ///////////
    @FindBy(xpath = "//div[contains(text(),\"Alert\")]")
    WebElement alertHeader;


    @FindBy(xpath = "//div[contains(@class,\"backdrop-show\")]//button[contains(@class,\"swal2-confirm\")]")
    public List<WebElement> checkInConflictionConfirmBtn;

    @FindBy(xpath = "//reservation-change-unit-reason-wizard")
    public WebElement changeUnitReasonsWizard;
    @FindBy(xpath = "//reservation-change-unit-reason-wizard//button[contains(text(),\"Change Unit\")]")
    public WebElement applyChangeUnitButton;

}


