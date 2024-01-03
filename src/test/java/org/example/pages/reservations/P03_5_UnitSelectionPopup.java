package org.example.pages.reservations;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

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
    @FindBy(xpath = "//div[contains(text(),\"Alert\")]")
    WebElement alertHeader;
}


/////////// alert pop up ///////////
