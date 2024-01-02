package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P05_SetupPage;
import org.example.pages.unit_setup_pages.P07_UnitTypeCustomization;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.NoSuchElementException;

public class D04_UnitTypeCustomization {

    final WebDriver driver = Hooks.driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPagec = new P05_SetupPage(driver);
    final P07_UnitTypeCustomization typeCustomization = new P07_UnitTypeCustomization(driver);
    String existantUnitType;


    @And("go to unit type customization page")

    public void goToUnitTypeCustomizationPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.unitsDroplist.click();
        setupPagec.typeCustomizationLink.click();
        wait.until(ExpectedConditions.visibilityOf(typeCustomization.pagination));
       try {
           existantUnitType = typeCustomization.unitTypesNames.getLast().getText();

       }catch (NoSuchElementException e){
           System.out.println(e.getMessage()+"no unit type created");
           clickOnNewTypeButton();
           selectTypeAndEnterDescription("VIP","adding existant type");
           clickOnTheSubmitButton();
           wait.until(ExpectedConditions.visibilityOf(typeCustomization.unitTypesNames.getLast()));
           existantUnitType = typeCustomization.unitTypesNames.getLast().getText();
       }

    }


    @When("click on new type button")
    public void clickOnNewTypeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(typeCustomization.newTypeButton));
        typeCustomization.newTypeButton.click();
        asrt.assertTrue(driver.getCurrentUrl().trim().equals(typeCustomization.newTypeUrl), "didnt redirect to new type page");
        asrt.assertAll();
    }

    @And("select type {string} and enter description {string}")
    public void selectTypeAndEnterDescription(String roomType, String description) {
        String roomTypeNme;
        if (roomType.equals("duplicate")) {
            roomTypeNme = existantUnitType;
        } else {
            roomTypeNme = roomType;
        }

        typeCustomization.selectUnitTypes().stream().filter(element -> element.getText().contains(roomTypeNme)).toList().get(0).click();
        typeCustomization.unitDescription.clear();
        typeCustomization.unitDescription.sendKeys(description);
    }


    @And("upload photos {string} {int} of the unit")
    public void uploadPhotoOfTheUnit(String photoPath,int count) {
        Utils.fileUpload(typeCustomization.imageUpload, photoPath,count);

    }

    @And("click on the submit button")
    public void clickOnTheSubmitButton() {
        typeCustomization.submitButton.click();
    }

    @Then("check the room type {string} is added")
    public void checkTheRoomTypeIsAdded(String roomType) {
        asrt.assertTrue(typeCustomization.unitTypesNames.stream().anyMatch(element -> element.getText().contains(roomType)));
        asrt.assertAll();
    }

    ///////////////////////////////////// edit unit type ///////////////////////////////////////
    @When("click on edit Button for the unit Type {string}")
    public void clickOnEditButtonForTheUnitType(String unitTypeName) {
        wait.until(ExpectedConditions.elementToBeClickable(typeCustomization.unitTypeEditButton(unitTypeName)));
        typeCustomization.unitTypeEditButton(unitTypeName).click();
    }

////////////////////////////// delete unit type ////////////////////////////////////

    String delteUnitTyeName;
    String delteUnitTyeDescription;

    @When("click on more menu for unit type {string} and click delete button")
    public void clickOnMoreMenuForUnitTypeAndClickDeleteButton(String unitTypeName) {
        delteUnitTyeName = unitTypeName;
        delteUnitTyeDescription = typeCustomization.unitTypeDescription(unitTypeName).getText();
        wait.until(ExpectedConditions.elementToBeClickable( typeCustomization.moreMenuButton(unitTypeName)));
        typeCustomization.moreMenuButton(unitTypeName).click();
        typeCustomization.unitTypeDeleteButton.click();
    }

    @Then("check del unit type dialog contains selected unit type and description")
    public void checkDelUnitTypeDialogContainsSelectedUnitTypeAndDescription() {
        wait.until(ExpectedConditions.visibilityOf(typeCustomization.deDialogUnitTypeName()));
        asrt.assertTrue(typeCustomization.deDialogUnitTypeName().getText().equals(delteUnitTyeName));
        asrt.assertTrue(typeCustomization.delDialogUnitTypeDescription().getText().equals(delteUnitTyeDescription));
    }

    @When("click on del unit type dilaog's confirm button")
    public void clickOnDelUnitTypeDilaogSConfirmButton() {
        typeCustomization.delDialogConfirmButton().click();
    }

    @And("Check the room type {string} isn't visible in the grid")
    public void checkTheRoomTypeIsnTVisibleInTheGrid(String unitTypeName) {
        asrt.assertFalse(typeCustomization.unitTypesNames.stream().anyMatch(element -> element.getText().equals(unitTypeName)));
        asrt.assertAll();
    }

    @Then("check unit Type pagination")
    public void checkUnitTypePagination() {
      asrt.assertTrue(typeCustomization.checkPagination());
      asrt.assertAll();
    }
}
