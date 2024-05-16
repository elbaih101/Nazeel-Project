package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pages.setuppages.unit_setup_pages.*;
import alia.nazeel.tools.DriverManager;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.unit_setup_pages.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.*;

public class D05_UnitsSetup {

    final WebDriver driver = DriverManager.getDriver();
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPagec = new P05_SetupPage(driver);
    final P08_UnitsSetupPage unitsSetupPage = new P08_UnitsSetupPage(driver);
    final P08_1_NewUnitPage newUnitPage = new P08_1_NewUnitPage(driver);
    final P08_2_GroupOfUnitsPopUp groupOfUnitsPopUp = new P08_2_GroupOfUnitsPopUp(driver);
    P23_MergeSettings mergeSettings = new P23_MergeSettings(driver);
    P24_Amenities amenities = new P24_Amenities(driver);
    final Faker faker = new Faker();
    String randomUnitNum;
    int numberofNewUnits;
    final Actions actions = new Actions(driver);
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    List<String> tobemergedUnits = new ArrayList<>();

    @And("go to unit Setup page")
    public void goToUnitSetupPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.unitsDroplist.click();
        setupPagec.unitSetupLink.click();
    }

    @Given("open the new unit page")
    public void openTheNewUnitPage() {

        new P00_multiPurposes(driver).waitLoading();
        wait.until(ExpectedConditions.elementToBeClickable(unitsSetupPage.newUnitButton));
        unitsSetupPage.newUnitButton.click();

    }


    @And("enter unit required data with room number {string} mergable {string} class {string}")
    public void enterUnitRequiredDataWithRoomNumber(String unitNum, String mergable, String uClass) {

        newUnitPage.unitNumberField.clear();
        randomUnitNum = faker.numerify("R####");
        if (unitNum.equals("RANDOM")) {
            newUnitPage.unitNumberField.sendKeys(randomUnitNum);
        } else {
            newUnitPage.unitNumberField.sendKeys(unitNum);
        }
        if (mergable.equalsIgnoreCase("true")) {
            newUnitPage.canBeMergedButton.click();
        }

        List<WebElement> classes = newUnitPage.unitClasses();
        WebElement selectedClass;
        if (uClass.equalsIgnoreCase("Random")) {
            selectedClass = classes.get(new Random().nextInt(classes.size()));
        } else {
            selectedClass = classes.stream().filter(uC -> uC.getText().equalsIgnoreCase(uClass)).findFirst().orElseThrow();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedClass));
        selectedClass.click();

        List<WebElement> types = newUnitPage.unitTypes();
        WebElement selectedType = types.get(new Random().nextInt(types.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedType));
        selectedType.click();

        List<WebElement> blocks = newUnitPage.blocks();
        WebElement selectedBlock = blocks.get(new Random().nextInt(blocks.size()));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedBlock));
        selectedBlock.click();

        List<WebElement> floors = newUnitPage.floors();
        WebElement selectedFloor = floors.get(new Random().nextInt(floors.size()));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedFloor));
        selectedFloor.click();

        List<WebElement> kitchens = newUnitPage.kitchens();
        WebElement selectedKitchen = kitchens.get(new Random().nextInt(kitchens.size()));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedKitchen));
        selectedKitchen.click();

        List<WebElement> halls = newUnitPage.halls();
        WebElement selectedHall = halls.get(new Random().nextInt(halls.size()));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                enterUnitRequiredDataWithRoomNumber("RANDOM", "Flase", "unit");
                clickOnTheAddUnitButton();
            }
        }
        tobemergedUnits.add(randomUnitNum);
    }

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
                anymatch = unitsSetupPage.unitsCards.stream().anyMatch(element -> unitsSetupPage.unitNum(element).getText().trim().contains(randomUnitNum));
            } else {
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(unitsSetupPage.unitsCards)));
                anymatch = unitsSetupPage.unitsCards.stream().anyMatch(element -> unitsSetupPage.unitNum(element).getText().trim().equalsIgnoreCase(unitNum));
            }
            if (anymatch) {
                break;
            }
            pageNumber++;
        } while (!unitsSetupPage.nextPageButton.getAttribute("class").contains("disabled"));
        asrt.assertTrue(anymatch);
        asrt.assertAll();
    }


    ///////// edit a unit /////////////
    @When("open the view mode for a unit {string}")
    public void openTheViewModeForAUnit(String unitNum) {
        WebElement card;
        new P00_multiPurposes(driver).waitLoading();
        if (!unitNum.equalsIgnoreCase("RANDOM")) {
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
        wait.until(ExpectedConditions.elementToBeClickable(newUnitPage.editButton));
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
        new P00_multiPurposes(driver).waitLoading();
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
            if (e.getMessage().contains("Invalid action, had related data")) {

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
//fixme
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
            WebElement selectedType = types.stream().filter(element -> element.getText().contains(type)).findFirst().orElseThrow();
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

        //FixMe : kitchens

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

    @And("enter the unit number {string} and filter")
    public void enterTheUnitNumberAndFilter(String untNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(unitsSetupPage.filterUnitNumber));
        unitsSetupPage.filterUnitNumber.clear();
        unitsSetupPage.filterUnitNumber.sendKeys(untNumber);
        unitsSetupPage.filterSearchButton.click();
    }

    @Then("check all units visible contains  number {string}")
    public void checkAllUnitsVisibleContainsNumber(String unitNumber) {
        new P00_multiPurposes(driver).waitLoading();
        List<WebElement> filteredUnits = unitsSetupPage.unitsCards;
        wait.until(ExpectedConditions.visibilityOfAllElements(filteredUnits));
        asrt.assertTrue(filteredUnits.stream().allMatch(element -> unitsSetupPage.unitNum(element).getText().contains(unitNumber)));
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
        new P00_multiPurposes(driver).waitLoading();
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
        asrt.assertTrue(totalUnitsCount >= newTotalCount);
        asrt.assertAll();

    }

    @Given("successfully create {int} unit with room number {string} mergable {string} class {string}")
    public void successfullyCreateAUnitWithRoomNumberMergableClass(int count, String unitNum, String mergable, String uClass) {
        openTheNewUnitPage();
        for (int i = 0; i < count; i++) {
            enterUnitRequiredDataWithRoomNumber(unitNum, mergable, uClass);
            newUnitPage.save_addMoreButton.click();
            try {

                D03_BlocksAndFloors blocksAndFloors = new D03_BlocksAndFloors();
                blocksAndFloors.checkToastMesageContainsText("Saved Successfully");

            } catch (AssertionError e) {
                if (e.getMessage().contains("Name exist before")) {
                    enterUnitRequiredDataWithRoomNumber(unitNum, mergable, uClass);
                    newUnitPage.save_addMoreButton.click();
                }
            }
            tobemergedUnits.add(randomUnitNum);

            new P00_multiPurposes(driver).waitLoading();
        }
    }

    @And("create new merge rule between the two created units with class {string} unitA {string} unitB {string}")
    public void createNewMergeRuleBetweenTheTwoCreatedUnits(String uClass, String unitA, String unitB) {
        gotoMergeSettingsPage();
        mergeSettings.newMergeButton.click();
        mergeSettings.blocksList().get(0).click();
        mergeSettings.floorsList().get(0).click();
        if (!uClass.isEmpty()) {
            mergeSettings.classesList().stream().filter(uC -> uC.getText().equalsIgnoreCase(uClass)).findAny().orElseThrow().click();
        }
        switch (unitA) {
            case "generated" ->
                    mergeSettings.unitA().stream().filter(unit -> unit.getText().contains(tobemergedUnits.get(0))).findAny().orElseThrow().click();

            case "Random" -> mergeSettings.unitA().getFirst().click();

            case "" -> {
            }

            case null, default ->
                    mergeSettings.unitA().stream().filter(unit -> unit.getText().contains(unitA)).findAny().orElseThrow().click();

        }
        switch (unitB) {
            case "generated" ->
                    mergeSettings.unitB().stream().filter(unit -> unit.getText().contains(tobemergedUnits.get(1))).findAny().orElseThrow().click();

            case "Random" -> mergeSettings.unitB().getFirst().click();

            case "" -> {
            }

            case null, default ->
                    mergeSettings.unitB().stream().filter(unit -> unit.getText().contains(unitB)).findAny().orElseThrow().click();

        }


        mergeSettings.saveButton.click();
    }

    void gotoMergeSettingsPage() {
        if (!setupPagec.mergeSettingsLink.isDisplayed()) {
            setupPagec.unitsDroplist.click();
        }
        setupPagec.mergeSettingsLink.click();
    }

    @And("the merge setting bet the two numbers is visible on the grid with class {string}")
    public void theMergeSettingBetTheTwoNumbersIsVisibleOnTheGrid(String uClass) {
        WebElement createdMergeSetting = mergeSettings.unitsNumbers.stream().filter(m -> m.getText().contains(tobemergedUnits.get(0)) && m.getText().contains(tobemergedUnits.get(1))).findFirst().orElseThrow();
        asrt.assertTrue(createdMergeSetting.isDisplayed());
        asrt.assertTrue(mergeSettings.mergeRecordUnitsClasses(createdMergeSetting).getText().contains(uClass));
        goToUnitSetupPage();
        clickingOntheFilterButtonToOpenFilterMenue();
        for (String unitNum : tobemergedUnits) {
            enterTheUnitNumberAndFilter(unitNum);
            new P00_multiPurposes(driver).waitLoading();
            WebElement unitCard = unitsSetupPage.unitsCards.stream().filter(card -> unitsSetupPage.unitNum(card).getText().equalsIgnoreCase(unitNum)).findFirst().orElseThrow();
            asrt.assertTrue(unitsSetupPage.unitMergeIcon(unitCard).get(0).isDisplayed(), "unit:" + unitNum + "is not merged");
        }
        asrt.assertAll();
    }

    List<String> retreavedMergeUnits = new ArrayList<>();

    @Given("delete any merge setting and note the related units")
    public void deleteAnyMergeSettingAndNoteTheRelatedUnits() {
        gotoMergeSettingsPage();
        WebElement mergRecord = mergeSettings.unitsNumbers.get(0);
        String mergedUnits = mergRecord.getText();
        retreavedMergeUnits.add(StringUtils.substringAfter(mergedUnits, "- "));
        retreavedMergeUnits.add(StringUtils.substringBefore(mergedUnits, " -"));
        mergeSettings.deleteButton(mergRecord).click();
        mergeSettings.confirmDeleteButton.click();
    }

    @And("check the units no more merged")
    public void checkTheUnitsNoMoreMerged() {
        goToUnitSetupPage();
        clickingOntheFilterButtonToOpenFilterMenue();
        for (String unitNum : retreavedMergeUnits) {
            enterTheUnitNumberAndFilter(unitNum);
            new P00_multiPurposes(driver).waitLoading();
            WebElement unitCard = unitsSetupPage.unitsCards.stream().filter(card -> unitsSetupPage.unitNum(card).getText().equalsIgnoreCase(unitNum)).findFirst().orElseThrow();
            asrt.assertTrue(unitsSetupPage.unitMergeIcon(unitCard).isEmpty(), "unit:" + unitNum + "is merged");
        }
        asrt.assertAll();
    }

    @Given("Filter the merge unit page with unit {string}")
    public void filterTheMergeUnitPageWithUnit(String uNum) {

        gotoMergeSettingsPage();

        mergeSettings.filterButton.click();
        mergeSettings.unitSearchField.clear();
        if (!uNum.equalsIgnoreCase("Random")) {
            mergeSettings.unitSearchField.sendKeys(uNum);
        } else {
            retreavedMergeUnits.add(StringUtils.substringAfter(mergeSettings.unitsNumbers.get(0).getText(), "- "));
            mergeSettings.unitSearchField.sendKeys(retreavedMergeUnits.get(0));
        }
        mergeSettings.searchButton.click();
    }

    @Then("Check only one record is visible with the unit {string}")
    public void checkOnlyOneRecordIsVisibleWithTheUnit(String uNum) {
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertTrue(mergeSettings.unitsNumbers.size() == 1);
        if (!uNum.equalsIgnoreCase("Random")) {
            asrt.assertTrue(mergeSettings.unitsNumbers.get(0).getText().contains(uNum));
        } else {
            asrt.assertTrue(mergeSettings.unitsNumbers.get(0).getText().contains(retreavedMergeUnits.get(0)));
        }
        asrt.assertAll();

    }

    @Given("go to amenities Page")
    public void goToAmenitiesPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.unitsDroplist.click();
        setupPagec.amenitiesLink.click();
    }

    List<String> retreavedAmenities = new ArrayList<>();

    @When("adding new amenity")
    public void addingNewAmenity() {
        amenities.newAmenityButton.click();
        WebElement selecetd = amenities.amenitysList().getFirst();
        retreavedAmenities.add(selecetd.getText());
        selecetd.click();
        amenities.descriptionField.sendKeys("added amenity");
        amenities.submitButton.click();

    }

    @And("Check the newly added amenity is added")
    public void checkTheNewlyAddedAmenityIsAdded() {
        asrt.assertTrue(amenities.names.stream().anyMatch(am -> am.getText().contains(retreavedAmenities.get(0))));
        goToUnitSetupPage();
        openTheNewUnitPage();
        asrt.assertTrue(newUnitPage.amenities().stream().anyMatch(am -> am.getText().contains(retreavedAmenities.get(0))));
    }

    @Given("edit unit amenity description {string} and state {string}")
    public void editUnitAmenityDescriptionAndState(String description, String state) {
        amenities.editButton(amenities.names.getFirst()).click();
        new P00_multiPurposes(driver).waitLoading();
        if (state.equalsIgnoreCase("inactive") && amenities.statusSwitch.getAttribute("class").contains("k-switch-on")) {
            amenities.statusSwitch.click();
        } else if (state.equalsIgnoreCase("active") && amenities.statusSwitch.getAttribute("class").contains("k-switch-of")) {
            amenities.statusSwitch.click();
        }
        amenities.descriptionField.clear();
        amenities.descriptionField.sendKeys(description);
        amenities.submitButton.click();
    }

    @And("Check the edited amenity ddescriptioon {string} and state {string}")
    public void checkTheEditedAmenityDdescriptioonAndState(String description, String state) {
        new P00_multiPurposes(driver).waitLoading();
        amenities.viewButton(amenities.names.getFirst()).click();
        if (state.equalsIgnoreCase("inactive")) {
            asrt.assertTrue(amenities.amenityStatus.getText().contains(state));
        } else if (state.equalsIgnoreCase("active")) {
            asrt.assertTrue(amenities.amenityStatus.getText().contains(state));
        }
        asrt.assertEquals(amenities.descriptionField.getText(), description);
        asrt.assertAll();
    }

    @Given("apply {string} amenity to all units")
    public void applyAmenityToAllUnits(String amenity) {
        WebElement seectedAmenity;
        if (amenity.equalsIgnoreCase("Random")) {
            seectedAmenity = amenities.names.getFirst();
        } else {
            seectedAmenity = amenities.names.stream().filter(am -> am.getText().equalsIgnoreCase(amenity)).findAny().orElseThrow();
        }
        retreavedAmenities.add(0, seectedAmenity.getText());
        amenities.applyButton(seectedAmenity).click();
        amenities.submitButton.click();
    }

    @Then("Check the amenity is applied for any unit")
    public void checkTheAmenityIsAppliedForAnyUnit() {
        goToUnitSetupPage();
        openTheViewModeForAUnit("Random");
        asrt.assertTrue(newUnitPage.amenities().stream().anyMatch(am -> am.getText().contains(retreavedAmenities.get(0))));
    }

    @Given("delete amenity {string}")
    public void deleteAmenity(String amenity) {
        WebElement seectedAmenity;

        if (amenity.equalsIgnoreCase("related")) {
            seectedAmenity = amenities.names.getFirst();
        } else if (amenity.equalsIgnoreCase("nonrelated")) {
            seectedAmenity = amenities.names.getLast();
        } else {
            seectedAmenity = amenities.names.stream().filter(am -> am.getText().equalsIgnoreCase(amenity)).findAny().orElseThrow();
        }
        retreavedAmenities.add(0, seectedAmenity.getText());
        amenities.deleteButton(seectedAmenity).click();
        amenities.confirmDeleteButton.click();
    }

    @Then("Check toast mesage contains text {string} and the amenity is removed")
    public void checkToastMesageContainsTextAndTheAmenityIsRemoved(String msg) {
        D03_BlocksAndFloors blocksAndFloors = new D03_BlocksAndFloors();
        blocksAndFloors.checkToastMesageContainsText(msg);
        if (msg.contains("successfully")) {
            goToUnitSetupPage();
            openTheNewUnitPage();
            asrt.assertFalse(newUnitPage.amenities().stream().anyMatch(am -> am.getText().contains(retreavedAmenities.get(0))));
            asrt.assertAll();
        }


    }
}

