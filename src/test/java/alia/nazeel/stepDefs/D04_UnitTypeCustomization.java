package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pages.setuppages.unit_setup_pages.P07_UnitTypeCustomization;
import alia.nazeel.tools.Utils;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.pages.masterdata_pages.P14_MasterData;
import alia.nazeel.pages.masterdata_pages.P15_MasterUnitsTypes;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.NoSuchElementException;

public class D04_UnitTypeCustomization {

    final WebDriver driver = DriverManager.getDriver();
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPagec = new P05_SetupPage(driver);
    final P07_UnitTypeCustomization typeCustomization = new P07_UnitTypeCustomization(driver);
    final P14_MasterData masterData = new P14_MasterData(driver);
    final P15_MasterUnitsTypes masterUnitsTypes = new P15_MasterUnitsTypes(driver);
    String existantUnitType;
    final Faker faker = new Faker();
    final JavascriptExecutor js = (JavascriptExecutor) driver;

    @And("go to unit type customization page")

    public void goToUnitTypeCustomizationPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.unitsDroplist.click();
        setupPagec.typeCustomizationLink.click();
        wait.until(ExpectedConditions.visibilityOf(typeCustomization.pagination));
        try {
            existantUnitType = typeCustomization.unitTypesNames.getLast().getText();

        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage() + "no unit type created");
            clickOnNewTypeButton();
            selectTypeAndEnterDescription("VIP", "adding existant type");
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
    public void uploadPhotoOfTheUnit(String photoPath, int count) {
        Utils.fileUpload(typeCustomization.imageUpload, photoPath, count);

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
        wait.until(ExpectedConditions.elementToBeClickable(typeCustomization.moreMenuButton(unitTypeName)));
        new P00_multiPurposes(driver).waitLoading();
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

    @Given("go to units master Data")
    public void goToUnitsMasterData() {
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.masterDataLink.get(0)));
        dashBoardPage.masterDataLink.get(0).click();
        wait.until(ExpectedConditions.elementToBeClickable(masterData.unitsDropList));
        masterData.unitsDropList.click();
        wait.until(ExpectedConditions.elementToBeClickable(masterData.unitsTypesLink));
        masterData.unitsTypesLink.click();
    }

    @Given("click on filter button and enter name of type {string} and status {string} and click search")
    public void clickOnFilterButtonAndEnterNameOfTypeAndStatusAndClickSearch(String typeName, String status) {
        wait.until(ExpectedConditions.elementToBeClickable(masterUnitsTypes.filterButton));
        new P00_multiPurposes(driver).waitLoading();
        masterUnitsTypes.filterButton.click();
        if (!typeName.isEmpty()) {
            masterUnitsTypes.nameSearchField.clear();
            masterUnitsTypes.nameSearchField.sendKeys(typeName);
        }
        if (!status.isEmpty()) {
            masterUnitsTypes.statuses().stream().filter(element -> element.getText().equalsIgnoreCase(status)).toList().get(0).click();
        }
        masterUnitsTypes.searchButton.click();
        new P00_multiPurposes(driver).waitLoading();
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(masterUnitsTypes.typesNames)));
    }

    @Then("Check the grid contains only types with names {string} and statues {string}")
    public void checkTheGridContainsOnlyTypesWithNamesAndStatues(String typeName, String status) {
        new P00_multiPurposes(driver).waitLoading();
        if (!typeName.isEmpty())

            asrt.assertFalse(masterUnitsTypes.typesNames.stream().anyMatch(element -> !element.getText().toLowerCase().contains(typeName.toLowerCase())), "wrong type name");
        if (!status.isEmpty())
            asrt.assertFalse(masterUnitsTypes.typesStatuses.stream().anyMatch(element -> !element.getText().equalsIgnoreCase(status)), "wrong status");
        asrt.assertAll();

    }

    @When("add anew unit type with name {string}")
    public void addAnewUnitTypeWithName(String typeName) {
        new P00_multiPurposes(driver).waitLoading();
        wait.until(ExpectedConditions.elementToBeClickable(masterUnitsTypes.newUnitTypeButton));
        masterUnitsTypes.newUnitTypeButton.click();
        masterUnitsTypes.typeNameField().clear();
        masterUnitsTypes.typeNameField().sendKeys(typeName);
        masterUnitsTypes.submitButton().click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successfully");
    }

    @Then("Check the type {string} is visible in the grid")
    public void checkTheTypeIsVisibleInTheGrid(String typeName) {
        clickOnFilterButtonAndEnterNameOfTypeAndStatusAndClickSearch(typeName, "");
        checkTheGridContainsOnlyTypesWithNamesAndStatues(typeName, "active");
    }

    @Given("click on edit button for type {string} and change name to {string}")
    public void clickOnEditButtonForTypeAndChangeNameTo(String oldName, String newName) {
        clickOnFilterButtonAndEnterNameOfTypeAndStatusAndClickSearch(oldName, "");
        WebElement type = masterUnitsTypes.typesNames.stream().findAny().orElseThrow();
        new P00_multiPurposes(driver).waitLoading();
        js.executeScript("arguments[0].click();", masterUnitsTypes.editButton(type));
        //  masterUnitsTypes.editButton(type).click();
        wait.until(ExpectedConditions.visibilityOf(masterUnitsTypes.typeNameField()));
        masterUnitsTypes.typeNameField().clear();
        masterUnitsTypes.typeNameField().sendKeys(newName);
        masterUnitsTypes.submitButton().click();
        clickOnFilterButtonAndEnterNameOfTypeAndStatusAndClickSearch(newName, "");

    }

    @Given("click on delete button for unit type associated with units")
    public void clickOnDeleteButtonForUnitTypeAssociatedWithUnits() {
        new P00_multiPurposes(driver).waitLoading();
        masterUnitsTypes.moreActions(masterUnitsTypes.typesNames.get(0)).stream().filter(element -> element.getText().trim().equalsIgnoreCase("delete")).toList().get(0).click();
        wait.until(ExpectedConditions.elementToBeClickable(masterUnitsTypes.confirmDeleteButton()));
        masterUnitsTypes.confirmDeleteButton().click();

    }

    @Given("create a unit type {string}")
    public void createAUnitType(String typeName) {
        if (typeName.equalsIgnoreCase("RANDOM")) {
            typeName = faker.funnyName().name();
        }
        addAnewUnitTypeWithName(typeName);
        checkTheTypeIsVisibleInTheGrid(typeName);
    }

    @And("delete the created unit type")
    public void deleteTheCreatedUnitType() {
        clickOnDeleteButtonForUnitTypeAssociatedWithUnits();
    }

    @Given("click on edit button for type {string} and change status to {string}")
    public void clickOnEditButtonForTypeAndChangeStatusTo(String name, String status) {

        clickOnFilterButtonAndEnterNameOfTypeAndStatusAndClickSearch(name, "");
        WebElement type = masterUnitsTypes.typesNames.stream().findAny().orElseThrow();
        new P00_multiPurposes(driver).waitLoading();
        js.executeScript("arguments[0].click();", masterUnitsTypes.editButton(type));
        if (status.equalsIgnoreCase("inactive")) {
            if (masterUnitsTypes.statusButton().getAttribute("aria-checked").contains("true")) {
                masterUnitsTypes.statusButton().click();
            }
        } else if (status.equalsIgnoreCase("active")) {
            if (masterUnitsTypes.statusButton().getAttribute("aria-checked").contains("false")) {
                masterUnitsTypes.statusButton().click();
            }
        }
        masterUnitsTypes.submitButton().click();
        clickOnFilterButtonAndEnterNameOfTypeAndStatusAndClickSearch(name, "");
    }
}
