package org.example.stepDefs;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P05_SetupPage;
import org.example.pages.unit_setup_pages.P08_1_NewUnitPage;
import org.example.pages.unit_setup_pages.P08_2_GroupOfUnitsPopUp;
import org.example.pages.unit_setup_pages.P08_UnitsSetupPage;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class D05_UnitsSetup {

    WebDriver driver = Hooks.driver;
    SoftAssert asrt = new SoftAssert();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPagec = new P05_SetupPage(driver);
    P08_UnitsSetupPage unitsSetupPage = new P08_UnitsSetupPage(driver);
    P08_1_NewUnitPage newUnitPage = new P08_1_NewUnitPage(driver);
    P08_2_GroupOfUnitsPopUp addGRoupOfUnitsPopUp = new P08_2_GroupOfUnitsPopUp(driver);
    Faker faker = new Faker();
    String randomUnitNum = faker.numerify("Ran##");
    int numberofNewUnits;
    Actions actions = new Actions(driver);


    @And("go to unit Setup page")
    public void goToUnitSetupPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.unitsDroplist.click();
        setupPagec.unitSetupLink.click();
    }

    @Given("open the new unit page")
    public void openTheNewUnitPageAndEnterTheRequiredDataWithRoomNumber() {
        unitsSetupPage.newUnitButton.click();

    }


    @And("enter unit required data with room number {string}")
    public void enterUnitRequiredDataWithRoomNumber(String unitNum) {
        newUnitPage.unitNumberField.clear();
        newUnitPage.unitNumberField.sendKeys(randomUnitNum);
        if (!unitNum.equals("RANDOM"))
            newUnitPage.unitNumberField.sendKeys(unitNum);

        List<WebElement> classes = newUnitPage.unitClasses();
        classes.get(new Random().nextInt(classes.size())).click();
        List<WebElement> types = newUnitPage.unitTypes();
        types.get(new Random().nextInt(types.size())).click();
        newUnitPage.canBeMergedButton.click();
        List<WebElement> blocks = newUnitPage.blocks();
        wait.until(ExpectedConditions.elementToBeClickable(blocks.get(new Random().nextInt(blocks.size()))));
        blocks.get(new Random().nextInt(blocks.size())).click();
        List<WebElement> floors = newUnitPage.floors();
        floors.get(new Random().nextInt(floors.size())).click();
        List<WebElement> kitchens = newUnitPage.kitchens();
        kitchens.get(new Random().nextInt(kitchens.size())).click();
        List<WebElement> halls = newUnitPage.halls();
        halls.get(new Random().nextInt(halls.size())).click();

    }

    @When("click on the add unit button")
    public void clickOnTheAddUnitButton() {
        newUnitPage.addUnitButton.click();
    }

    @And("check unit card in the card grid with number {string}")
    public void checkUnitCardInTheCardGridWithNumber(String unitNum) {
        if (!unitNum.equals("RANDOM")) {
            asrt.assertTrue(unitsSetupPage.unitsCards.stream().anyMatch(element -> unitsSetupPage.unitNum(element).getText().contains(unitNum)));
            asrt.assertAll();
        } else {
            asrt.assertTrue(unitsSetupPage.unitsCards.stream().anyMatch(element -> unitsSetupPage.unitNum(element).getText().contains(randomUnitNum)));
            asrt.assertAll();
        }
    }


    ///////// edit a unit /////////////
    @When("open the view mode for a unit {string}")
    public void openTheViewModeForAUnit(String unitNum) {
        WebElement card;
        if (!unitNum.equals("RANDOM")){
            card = unitsSetupPage.unitsCards.stream().filter(element -> unitsSetupPage.unitNum(element).getText().contains(unitNum)).toList().get(0);}
        else {
            card = unitsSetupPage.unitsCards.get(new Random().nextInt(unitsSetupPage.unitsCards.size()));
        }
        actions.moveToElement(card);
        actions.perform();
        // wait.until(ExpectedConditions.visibilityOf(unitsSetupPage.unitViewButton(card)));
        actions.moveToElement(unitsSetupPage.unitViewButton(card));
        actions.click();
        actions.perform();
    }

    @Then("check the url contains {string} click on the edit button to enter edit mode and check the url contains {string}")
    public void checkTheUrlContainsClickOnTheEditButtonToEnterEditModeAndCheckTheUrlContains(String view, String edit) {
        asrt.assertTrue(driver.getCurrentUrl().contains(view));
        newUnitPage.editButton.click();
        asrt.assertTrue(driver.getCurrentUrl().contains(edit));
        asrt.assertAll();
        wait.until(ExpectedConditions.visibilityOf(newUnitPage.saveChangesButton));
    }

    @And("save the edited unit")
    public void saveTheEditedUnit() {
        newUnitPage.saveChangesButton.click();
    }

    /////////// add group of units //////////////
    @Given("open the add group of units popup")
    public void openTheAddGroupOfUnitsPopup() {
        WebElement addgroupunit = unitsSetupPage.addGroupUnitsButton();
        wait.until(ExpectedConditions.elementToBeClickable(addgroupunit));
        addgroupunit.click();

    }

    @And("enter the required data with number of units {int}")
    public void enterTheRequiredDataWithNumberOfUnits(int numberofInsertUnits) {
        int totalUnitsNo = unitsSetupPage.totalUnitNumber();
        numberofNewUnits = numberofInsertUnits;
        int noFrom = (totalUnitsNo + numberofInsertUnits) - (numberofInsertUnits - 1);
        int noTo = (totalUnitsNo + numberofInsertUnits);
        try {

            List<WebElement> blocks = addGRoupOfUnitsPopUp.blocks();
            WebElement selectedBlock = blocks.get(new Random().nextInt(blocks.size()));
            wait.until(ExpectedConditions.elementToBeClickable(selectedBlock));
            selectedBlock.click();
        } catch (ElementNotInteractableException e) {
            System.out.println("only one block can't select");
        }
        List<WebElement> floors = addGRoupOfUnitsPopUp.floorss();
        WebElement selectedFlor = floors.get(new Random().nextInt(floors.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedFlor));
        selectedFlor.click();
        List<WebElement> types = addGRoupOfUnitsPopUp.unitType();
        WebElement selectedType = types.get(new Random().nextInt(types.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedType));
        selectedType.click();
        List<WebElement> formats = addGRoupOfUnitsPopUp.formats();
        WebElement selectedFormat = formats.get(new Random().nextInt(formats.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedFormat));
        selectedFormat.click();
        addGRoupOfUnitsPopUp.fromNumber.sendKeys(Integer.toString(noFrom));
        addGRoupOfUnitsPopUp.toNumber.sendKeys(Integer.toString(noTo));

    }

    @Then("submit adding group of units")
    public void submitAddingGroupOfUnits() {
        addGRoupOfUnitsPopUp.saveButton.click();
    }

    @Then("check the newly added units")
    public void checkTheNewlyAddedUnits() {
        List<WebElement> newUnits = unitsSetupPage.unitsCards.stream().filter(element -> Integer.parseInt(unitsSetupPage.unitNum(element).getText().trim()) > unitsSetupPage.totalUnitNumber()).toList();
        asrt.assertTrue(newUnits.size() == numberofNewUnits);
        asrt.assertAll();
    }


    WebElement deletedCard;

    @When("clicking delete button for unit {string}")
    public void clickingDeleteButtonForAUnit(String unitNum) {


        if (unitNum.equals("RANDOM")){
            deletedCard = unitsSetupPage.unitsCards.get(new Random().nextInt(unitsSetupPage.unitsCards.size()));}
        else {
            deletedCard = unitsSetupPage.unitsCards.stream().filter(element -> unitsSetupPage.unitNum(element).getText().contains(unitNum)).toList().get(0);

        }
        actions.moveToElement(deletedCard);
        actions.perform();
        // wait.until(ExpectedConditions.visibilityOf(unitsSetupPage.unitViewButton(deletedCard)));
        actions.moveToElement(unitsSetupPage.unitDeleteButton(deletedCard));
        actions.click();
        actions.perform();
    }

    @Then("Check the deleted unit number matches the selected unit number")
    public void checkTheDeletedUnitNumberMatchesTheSelectedUnitNumber() {
        asrt.assertTrue(unitsSetupPage.unitNum(deletedCard).getText().trim().equals(unitsSetupPage.deltedUnitNumber().getText().trim()));
        asrt.assertAll();
    }

    @When("clicking the confirm delete button and Check toast mesage contains text {string}")
    public void clickingTheConfirmDeleteButton(String msg) {
        wait.until(ExpectedConditions.elementToBeClickable(unitsSetupPage.confirmDeleteButton()));
        unitsSetupPage.confirmDeleteButton().click();
        D03_BlocksAndFloors blocksAndFloors = new D03_BlocksAndFloors();
        try {
            blocksAndFloors.checkToastMesageContainsText(msg);

        } catch (AssertionError e) {
            if (e.getMessage().contains("Invalid action, had related data")) ;
            {

                unitsSetupPage.discardDeleteButton().click();

//                wait.until(ExpectedConditions.stalenessOf(unitsSetupPage.deleteUnitDialog));
                clickingDeleteButtonForAUnit("RANDOM");
                checkTheDeletedUnitNumberMatchesTheSelectedUnitNumber();
                clickingTheConfirmDeleteButton(msg);
            }

        }
    }

}

