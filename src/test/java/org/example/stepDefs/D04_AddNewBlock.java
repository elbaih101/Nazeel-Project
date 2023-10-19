package org.example.stepDefs;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P04_BlocksPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D04_AddNewBlock {


    Faker faker = new Faker();
    WebDriver driver = Hooks.driver;
    SoftAssert asrt = new SoftAssert();
    Actions actions = new Actions(driver);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P04_BlocksPage blocksPage = new P04_BlocksPage(driver);


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


    @Then("check new block is created with name {string} and discription {string}")
    public void checkNewBlockIsCreatedWithNameAndDiscription(String name, String discription) {
        for (WebElement blockName : blocksPage.blocksNames
        ) {
            if (blockName.getText().contains(name)) ;
            asrt.assertTrue(true);

            asrt.assertTrue(blocksPage.blockDescription(blockName).getText().contains(discription));
            asrt.assertAll();

        }

    }
}
