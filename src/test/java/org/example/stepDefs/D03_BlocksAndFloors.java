package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P04_BlocksPage;
import org.example.pages.P05_SetupPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D03_BlocksAndFloors {


    WebDriver driver = Hooks.driver;
    SoftAssert asrt = new SoftAssert();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPagec = new P05_SetupPage(driver);
    P04_BlocksPage blocksPage = new P04_BlocksPage(driver);


    //////////////////////////check default block/////////////////////////////////
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


///////////////////////////////////////////////////////////////////////////////
//////////////////////////Add new Block/////////////////////////////////


    @When("click on new block Button")
    public void clickOnNewBlockButton() {
        wait.until(ExpectedConditions.visibilityOfAllElements(blocksPage.newBlockButton));
        blocksPage.newBlockButton.click();
    }

    @And("Fill the Block name {string} and discription {string}")
    public void fillTheBlockNameAndDiscription(String name, String discription) {
        wait.until(ExpectedConditions.visibilityOf(blocksPage.blockNameField));
        wait.until(ExpectedConditions.elementToBeClickable(blocksPage.blockNameField));
        blocksPage.blockNameField.sendKeys(name);
        blocksPage.descriptionField.sendKeys(discription);
        blocksPage.addBlockButton.click();
    }

    @Then("Check toast mesage contains text {string}")
    public void checkToastMesageContainsText(String arg0) {
        asrt.assertTrue(blocksPage.toastMsg.isDisplayed());
        asrt.assertEquals(blocksPage.toastMsg.getText(), "Saved Successfully");
        asrt.assertAll();
    }

    @Then("check new block is created")
    public void checkNewBlockIsCreated() {
    }


    @Then("check new block with name {string} and discription {string}")
    public void checkNewBlockIsCreatedWithNameAndDiscription(String name, String discription) {
        for (WebElement blockName : blocksPage.blocksNames
        ) {
            if (blockName.getText().contains(name)) ;
            asrt.assertTrue(true);

            asrt.assertTrue(blocksPage.blockDescription(blockName).getText().contains(discription));
            asrt.assertAll();

        }

    }
    //////////////////////////////////////////////////////////////////////
//////////////////////////Edit block from view Button/////////////////////////////////
@And("click on the view Button for Block name {string}")
public void clickOnTheViewButtonForBlockName(String blockName) {
    blocksPage.blockViewButton(blockName).click();
    wait.until(ExpectedConditions.visibilityOf(blocksPage.blockViewButton(blockName)));

}


    @When("click on edit Button")
    public void clickOnEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(blocksPage.viewModeEditButton));
        blocksPage.viewModeEditButton.click();

    }

    @And("enter new name {string} and new discriptn {string}")
    public void enterNewNameAndNewDiscriptn(String newName, String newDescription) {
        wait.until(ExpectedConditions.visibilityOf(blocksPage.blockNameField));
        blocksPage.blockNameField.clear();
        blocksPage.blockNameField.sendKeys(newName);
        blocksPage.descriptionField.clear();
        blocksPage.descriptionField.sendKeys(newDescription);

    }

    @And("click on save button for block edit mood")
    public void clickOnSaveButtonForBlockEditMood() {
        blocksPage.editModeSaveButton.click();
    }


}
