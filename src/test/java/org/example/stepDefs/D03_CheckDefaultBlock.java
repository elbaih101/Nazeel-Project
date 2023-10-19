package org.example.stepDefs;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P04_BlocksPage;
import org.example.pages.P05_SetupPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D03_CheckDefaultBlock {


    WebDriver driver = Hooks.driver;
    SoftAssert asrt = new SoftAssert();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPagec = new P05_SetupPage(driver);
    P04_BlocksPage blocksPage = new P04_BlocksPage(driver);

    @And("go to Blocks Page")
    public void goToBlocksPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.blocksAndFloorsDroplist.click();
        setupPagec.blocksLink.click();
    }

    @Then("Check Default Block name to be {string} and Description to be {string}")
    public void checkDefaultBlockNameToBeAndDescriptionToBe(String blockName, String blockDescripion) {
        wait.until(ExpectedConditions.visibilityOf(blocksPage.newBlockButton));
        asrt.assertTrue(blocksPage.blocksNames.get(0).getText().contains(blockName));
        asrt.assertTrue(blocksPage.blocksDescriptions.get(0).getText().contains(blockDescripion));
        asrt.assertAll();
    }

}
