package org.example.stepDefs;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.setuppages.P05_SetupPage;
import org.example.pages.setuppages.unit_setup_pages.P08_1_NewUnitPage;
import org.example.pages.setuppages.unit_setup_pages.P08_2_GroupOfUnitsPopUp;
import org.example.pages.setuppages.unit_setup_pages.P08_UnitsSetupPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.*;

public class D05_UnitsSetup {

    final WebDriver driver = Hooks.driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPagec = new P05_SetupPage(driver);
    final P08_UnitsSetupPage unitsSetupPage = new P08_UnitsSetupPage(driver);
    final P08_1_NewUnitPage newUnitPage = new P08_1_NewUnitPage(driver);
    final P08_2_GroupOfUnitsPopUp groupOfUnitsPopUp = new P08_2_GroupOfUnitsPopUp(driver);
    final Faker faker = new Faker();
    final String randomUnitNum = faker.numerify("Ran##");
    int numberofNewUnits;
    final Actions actions = new Actions(driver);
    final JavascriptExecutor js = (JavascriptExecutor) driver;


    @And("go to unit Setup page")
    public void goToUnitSetupPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.unitsDroplist.click();
        setupPagec.unitSetupLink.click();
    }

    @Given("open the new unit page")
    public void openTheNewUnitPageAndEnterTheRequiredDataWithRoomNumber() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.elementToBeClickable(unitsSetupPage.newUnitButton));
        unitsSetupPage.newUnitButton.click();

    }


    @And("enter unit required data with room number {string}")
    public void enterUnitRequiredDataWithRoomNumber(String unitNum) {
        newUnitPage.unitNumberField.clear();
        if (unitNum.equals("RANDOM")) {
            newUnitPage.unitNumberField.sendKeys(randomUnitNum);
        } else {
            newUnitPage.unitNumberField.sendKeys(unitNum);
        }

        List<WebElement> classes = newUnitPage.unitClasses();
        WebElement selectedClass = classes.get(new Random().nextInt(classes.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedClass));
        selectedClass.click();

        List<WebElement> types = newUnitPage.unitTypes();
        WebElement selectedType = types.get(new Random().nextInt(types.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedType));
        selectedType.click();

        wait.until(ExpectedConditions.elementToBeClickable(newUnitPage.canBeMergedButton));
        newUnitPage.canBeMergedButton.click();

        List<WebElement> blocks = newUnitPage.blocks();
        WebElement selectedBlock = blocks.get(new Random().nextInt(blocks.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedBlock));
        selectedBlock.click();

        List<WebElement> floors = newUnitPage.floors();
        WebElement selectedFloor = floors.get(new Random().nextInt(floors.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedFloor));
        selectedFloor.click();

        List<WebElement> kitchens = newUnitPage.kitchens();
        WebElement selectedKitchen = kitchens.get(new Random().nextInt(kitchens.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedKitchen));
        selectedKitchen.click();
        List<WebElement> halls = newUnitPage.halls();
        WebElement selectedHall = halls.get(new Random().nextInt(halls.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedHall));
        selectedHall.click();

    }

    @When("click on the add unit button")
    public void clickOnTheAddUnitButton() {
        newUnitPage.addUnitButton.click();
        D03_BlocksAndFloors blocksAndFloors = new D03_BlocksAndFloors();
        try {

            blocksAndFloors.checkToastMesageContainsText("Saved Successfully");
        } catch (AssertionError e) {
            if (e.getMessage().contains("Name exist before")) {
                enterUnitRequiredDataWithRoomNumber("RANDOM");
                clickOnTheAddUnitButton();
            }
        }
    }
// FIXME : revise Below function
    @And("check unit card in the card grid with number {string}")
    public void checkUnitCardInTheCardGridWithNumber(String unitNum) {
        int pageNumber = 1;
        boolean anymatch;
        do {
            if (pageNumber != 1) {
                unitsSetupPage.nextPageButton.click();
            }
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(unitsSetupPage.unitsCards)));
                if (unitNum.equals("RANDOM")) {
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(unitsSetupPage.unitsCards)));
                    anymatch =unitsSetupPage.unitsCards.stream().anyMatch(element -> unitsSetupPage.unitNum(element).getText().trim().contains(randomUnitNum));
                } else {
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(unitsSetupPage.unitsCards)));
                    anymatch =unitsSetupPage.unitsCards.stream().anyMatch(element -> unitsSetupPage.unitNum(element).getText().trim().equalsIgnoreCase(unitNum));
                }
                if (anymatch){break;}
            pageNumber++;
        } while (!unitsSetupPage.nextPageButton.getAttribute("class").contains("disabled"));
        asrt.assertTrue(anymatch);
        asrt.assertAll();
    }


    ///////// edit a unit /////////////
    @When("open the view mode for a unit {string}")
    public void openTheViewModeForAUnit(String unitNum) {
        WebElement card;
        if (!unitNum.equals("RANDOM")) {
            card = unitsSetupPage.unitsCards.stream().filter(element -> unitsSetupPage.unitNum(element).getText().contains(unitNum)).toList().get(0);
        } else {
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

    int noFrom;
    int noTo;

    @And("enter the required data with number of units {int} and type {string} and block {string}")
    public void enterTheRequiredDataWithNumberOfUnits(int numberofInsertUnits, String type, String block) {

        int totalUnitsNo = unitsSetupPage.totalUnitNumber();
        numberofNewUnits = numberofInsertUnits;
        noFrom = (totalUnitsNo + numberofInsertUnits) - (numberofInsertUnits - 1);
        noTo = (totalUnitsNo + numberofInsertUnits);
        try {

            List<WebElement> blocks = groupOfUnitsPopUp.blocks();
            WebElement selectedBlock;
            if (block.equalsIgnoreCase("RANDOM")) {
                selectedBlock = blocks.get(new Random().nextInt(blocks.size()));
            } else {
                selectedBlock = blocks.stream().filter(element -> element.getText().contains(block)).toList().get(0);
            }

            wait.until(ExpectedConditions.elementToBeClickable(selectedBlock));
            selectedBlock.click();
        } catch (ElementNotInteractableException e) {
            System.out.println("only one block can't select");
        }
        List<WebElement> floors = groupOfUnitsPopUp.floorss();
        WebElement selectedFlor = floors.get(new Random().nextInt(floors.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedFlor));
        selectedFlor.click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("wating failed for unit types");
        }

        List<WebElement> types = groupOfUnitsPopUp.unitType();
        WebElement selectedType;
        if (type.equalsIgnoreCase("RANDOM")) {
            selectedType = types.get(new Random().nextInt(types.size()));
        } else {
            selectedType = types.stream().filter(element -> element.getText().contains(type)).toList().get(0);
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedType));
        selectedType.click();

        List<WebElement> formats = groupOfUnitsPopUp.formats();
        WebElement selectedFormat = formats.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(selectedFormat));
        selectedFormat.click();

        groupOfUnitsPopUp.fromNumber.sendKeys(Integer.toString(noFrom));
        groupOfUnitsPopUp.toNumber.sendKeys(Integer.toString(noTo));

    }

    @Then("submit adding group of units")
    public void submitAddingGroupOfUnits() {
        groupOfUnitsPopUp.saveButton.click();
    }
// FIXME : Revise below function after edits to the method
    @Then("check the newly added units")
    public void checkTheNewlyAddedUnits() {
        Set<String> newUnitsNumber = new HashSet<>();
        Set<String> newUnits = new HashSet<>();
//        newUnitsNumber.add(Integer.toString(noFrom));
        for (int i = noFrom; i <= noTo; i++) {
            newUnitsNumber.add(Integer.toString(i));
        }
        int pageNumber = 1;
        do {
            if (pageNumber != 1) {
                unitsSetupPage.nextPageButton.click();
            }

              newUnits.addAll(unitsSetupPage.unitNums());
//            for (int i = 0; i < newUnitsNumber.size(); i++) {
//                int j = i;
//                wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(unitsSetupPage.unitsCards)));
//                try {
//                    newUnits.addAll(unitsSetupPage.unitsCards.stream().filter(element -> unitsSetupPage.unitNum(element).getText().trim().contains(newUnitsNumber.get(j))).toList());
//                } catch (StaleElementReferenceException ignored) {
//                }
//
//            }
            pageNumber++;
        } while (!unitsSetupPage.nextPageButton.getAttribute("class").contains("disabled"));
//        asrt.assertTrue(newUnits.size() == newUnitsNumber.size());
        asrt.assertTrue(newUnits.containsAll(newUnitsNumber));
        asrt.assertAll();
    }


    WebElement deletedCard;

    @When("clicking delete button for unit {string}")
    public void clickingDeleteButtonForAUnit(String unitNum) {


        if (unitNum.equals("RANDOM")) {
            deletedCard = unitsSetupPage.unitsCards.get(new Random().nextInt(unitsSetupPage.unitsCards.size()));
        } else {
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
            if (!e.getMessage().contains("Invalid action, had related data")) {
                System.out.println("assertion error" + e.getMessage());
            }

        }
        try {

            asrt.assertTrue(deletedCard.getText().contains(" "));
            asrt.assertAll();
        } catch (StaleElementReferenceException ex) {
            asrt.assertTrue(true);
            asrt.assertAll();
        }
    }

    /////////////  edit group of units //////////////////
//FIXME
    @Given("open the edit group of units popup")
    public void openTheEditGroupOfUnitsPopup() {
        wait.until(ExpectedConditions.elementToBeClickable(unitsSetupPage.editGroupUnitsButton));
        new P00_multiPurposes(driver).waitLoading();
        unitsSetupPage.editGroupUnitsButton.click();
    }

    @When("Select units to be edited criteria of type {string} type_exclusivly {string}")
    public void selectUnitsToBeEditedCriteria(String type, String exclusivestate) {
        if (type.equalsIgnoreCase("RANDOM") && exclusivestate.equalsIgnoreCase("NO")) {
            try {
                List<WebElement> blocks = groupOfUnitsPopUp.blocks();
                WebElement selectedBlock = blocks.get(new Random().nextInt(blocks.size()));
                wait.until(ExpectedConditions.elementToBeClickable(selectedBlock));
                selectedBlock.click();

                ///////// NoSuchElementException  : for the element may not exist ////////
            } catch (NullPointerException e) {
                System.out.println("only one block can't select");
            }
            List<WebElement> floors = groupOfUnitsPopUp.floorss();
            WebElement selectedFlor = floors.get(new Random().nextInt(floors.size()));
            wait.until(ExpectedConditions.elementToBeClickable(selectedFlor));
            selectedFlor.click();
            List<WebElement> types = groupOfUnitsPopUp.unitType();
            WebElement selectedType = types.get(new Random().nextInt(types.size()));
            wait.until(ExpectedConditions.elementToBeClickable(selectedType));
            selectedType.click();
            groupOfUnitsPopUp.fromNumber.sendKeys(Integer.toString(1));
            groupOfUnitsPopUp.toNumber.sendKeys(Integer.toString(100));
        } else if (exclusivestate.equalsIgnoreCase("yes") && !type.equalsIgnoreCase("RANDOM")) {
            List<WebElement> types = groupOfUnitsPopUp.unitType();
            WebElement selectedType = types.stream().filter(element -> element.getText().contains(type)).toList().get(0);
            wait.until(ExpectedConditions.elementToBeClickable(selectedType));
            selectedType.click();
        }

        groupOfUnitsPopUp.nextButon.click();

    }

    @And("select all units")
    public void selectAllUnits() {
        wait.until(ExpectedConditions.visibilityOfAllElements(groupOfUnitsPopUp.unitstoSelect));
        js.executeScript("arguments[0].click()", groupOfUnitsPopUp.selectAllCheckBox);
//        groupOfUnitsPopUp.selectAllCheckBox.click();
        wait.until(ExpectedConditions.elementToBeClickable(groupOfUnitsPopUp.nextButon));
        groupOfUnitsPopUp.nextButon.click();

    }

    @Then("edit all the features related to the selected units and save")
    public void editAllTheFeaturesRelatedToTheSelectedUnitsAndSave() {
        groupOfUnitsPopUp.featureCheckBox("Block - Floor").click();
        try {

            List<WebElement> blocks = groupOfUnitsPopUp.editblocks();
            WebElement selectedBlock = blocks.get(new Random().nextInt(blocks.size()));
            wait.until(ExpectedConditions.elementToBeClickable(selectedBlock));
            selectedBlock.click();
            /////// ElementNotInteractableException  :for the elment exists but cant interact /////////
        } catch (ElementNotInteractableException e) {
            System.out.println("only one block can't select");
        }
        List<WebElement> floors = groupOfUnitsPopUp.editFloors();
        WebElement selectedFlor = floors.get(new Random().nextInt(floors.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedFlor));
        selectedFlor.click();

        groupOfUnitsPopUp.featureCheckBox("Unit type").click();
        List<WebElement> types = groupOfUnitsPopUp.editUnitType();
        WebElement selectedType = types.get(new Random().nextInt(types.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedType));
        selectedType.click();

        groupOfUnitsPopUp.featureCheckBox("Hall").click();
        List<WebElement> halls = groupOfUnitsPopUp.halls();
        WebElement selectedHall = halls.get(new Random().nextInt(halls.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedHall));
        selectedHall.click();

        //Todo : kitchens

//        groupOfUnitsPopUp.featureCheckBox("Kitchen").click();
//        List<WebElement> kitchens = groupOfUnitsPopUp.kitchens();
//        WebElement selectedKitchen = kitchens.get(new Random().nextInt(kitchens.size()));
//        wait.until(ExpectedConditions.elementToBeClickable(selectedKitchen));
//        selectedKitchen.click();

        groupOfUnitsPopUp.featureCheckBox("Single Beds").click();
        groupOfUnitsPopUp.editSingleBedsField.sendKeys("3");

        groupOfUnitsPopUp.featureCheckBox("Double Beds").click();
        groupOfUnitsPopUp.editDoubleBedsField.sendKeys("1");

        groupOfUnitsPopUp.editDialogSaveButton.click();

    }

    @Given("clicking onthe filter button to open filter menue")
    public void clickingOntheFilterButtonToOpenFilterMenue() {
        new P00_multiPurposes(driver).waitLoading();
        wait.until(ExpectedConditions.elementToBeClickable(unitsSetupPage.filterButton));
        unitsSetupPage.filterButton.click();
    }

    String selectedState;

    @And("Selecting Status {string} and Filtring")
    public void selectingStatusAndFiltring(String state) {
        List<WebElement> statusList = unitsSetupPage.statusList();
        WebElement selecetdStatus;
        if (state.equals("RANDOM")) {
            selecetdStatus = statusList.get(new Random().nextInt(statusList.size()));
            selectedState = selecetdStatus.getText();
        } else {
            selecetdStatus = statusList.stream().filter(element -> element.getText().contains(state)).toList().get(0);
            selectedState = selecetdStatus.getText();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selecetdStatus));
        selecetdStatus.click();
        unitsSetupPage.filterSearchButton.click();
    }

    @Then("Check all visible units card have the status {string}")
    public void checkAllVisibleUnitsCardHaveTheStatus(String state) {
        List<WebElement> filteredUnits = unitsSetupPage.unitsCards;
        List<WebElement> inactiveUnitsFlags = unitsSetupPage.UnitsFlags;
        if (selectedState.equals("Inactive")) {

            asrt.assertTrue(filteredUnits.size() == inactiveUnitsFlags.size());
        } else if (selectedState.equals("Active")) {
            asrt.assertTrue(inactiveUnitsFlags.isEmpty());
        } else {
            asrt.assertTrue(true);
        }
        asrt.assertAll();
    }

    @And("enter the unit number {int} and filter")
    public void enterTheUnitNumberAndFilter(int untNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(unitsSetupPage.filterUnitNumber));
        unitsSetupPage.filterUnitNumber.sendKeys(Integer.toString(untNumber));
        unitsSetupPage.filterSearchButton.click();
    }
// FIXME :revise below Function      stale element reference: stale element not found
    @Then("check all units visible contains  number {int}")
    public void checkAllUnitsVisibleContainsNumber(int unitNumber) {
        new P00_multiPurposes(driver).waitLoading();
        List<WebElement> filteredUnits = unitsSetupPage.unitsCards;
        wait.until(ExpectedConditions.visibilityOfAllElements(filteredUnits));
        asrt.assertTrue(filteredUnits.stream().allMatch(element -> unitsSetupPage.unitNum(element).getText().contains(Integer.toString(unitNumber))));
        asrt.assertAll();

    }

    String selectedUnitTypeName;

    @And("Select Type {string} and filter")
    public void selectTypeAndFilter(String unitType) {
        List<WebElement> typesList = unitsSetupPage.unitTypesList();
        WebElement selecetdType;
        if (unitType.equals("RANDOM")) {
            selecetdType = typesList.get(new Random().nextInt(typesList.size()));
            selectedUnitTypeName = selecetdType.getText().trim();
        } else {
            selecetdType = typesList.stream().filter(element -> element.getText().trim().contains(unitType.trim())).toList().get(0);
            selectedUnitTypeName = selecetdType.getText();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selecetdType));
        selecetdType.click();
        unitsSetupPage.filterSearchButton.click();

    }

    @Then("check all visible units have type {string}")
    public void checkAllVisibleUnitsHaveType(String unitType) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("waiting to refresh the cards failed");
        }
        List<WebElement> filteredUnits = unitsSetupPage.unitsCards;
        if (unitType.equals("RANDOM")) {
            asrt.assertTrue(filteredUnits.stream().allMatch(card -> unitsSetupPage.unitType(card).getText().trim().contains(selectedUnitTypeName.trim())));
        } else {
            asrt.assertTrue(filteredUnits.stream().allMatch(card -> unitsSetupPage.unitType(card).getText().trim().contains(unitType.trim())));
        }
        asrt.assertAll();

    }

    String selectedBlockName;
    String selectedFloorName;
    int totalUnitsCount;

    @And("select block {string} and floor {string} and filter")
    public void selectBlockAndFilter(String block, String floor) {
        totalUnitsCount = unitsSetupPage.totalUnitNumber();
        List<WebElement> blocksList = unitsSetupPage.blocksList();
        WebElement selecetdblock;
        if (block.equals("RANDOM")) {
            selecetdblock = blocksList.get(new Random().nextInt(blocksList.size()));
            selectedBlockName = selecetdblock.getText().trim();
        } else {
            selecetdblock = blocksList.stream().filter(element -> element.getText().trim().contains(block.trim())).toList().get(0);
            selectedBlockName = selecetdblock.getText();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selecetdblock));
        selecetdblock.click();

        List<WebElement> floorsList = unitsSetupPage.floorsList();
        WebElement selectedFloor;
        if (block.equals("RANDOM")) {
            selectedFloor = floorsList.get(new Random().nextInt(floorsList.size()));
            selectedFloorName = selectedFloor.getText().trim();
        } else {
            selectedFloor = floorsList.stream().filter(element -> element.getText().trim().contains(floor.trim())).toList().get(0);
            selectedFloorName = selectedFloor.getText();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedFloor));
        selectedFloor.click();
        unitsSetupPage.filterSearchButton.click();

    }

    @Then("check the data presnet are related to the selected block")
    public void checkTheDataPresnetAreRelatedToTheSelectedBlock() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int newTotalCount = unitsSetupPage.totalUnitNumber();
        asrt.assertTrue(totalUnitsCount > newTotalCount);
        asrt.assertAll();

    }
}

