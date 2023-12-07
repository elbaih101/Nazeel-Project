package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P04_BlocksPage;
import org.example.pages.P05_SetupPage;
import org.example.pages.P06_FloorsPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class D03_BlocksAndFloors {


    WebDriver driver = Hooks.driver;
    SoftAssert asrt = new SoftAssert();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPagec = new P05_SetupPage(driver);
    P04_BlocksPage blocksPage = new P04_BlocksPage(driver);
    P06_FloorsPage floorsPage = new P06_FloorsPage(driver);


    ////////////////////////// check default block /////////////////////////////////
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
////////////////////////// Add new Block /////////////////////////////////


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
    public void checkToastMesageContainsText(String mesage) {
        asrt.assertTrue(blocksPage.toastMsg.isDisplayed());
        asrt.assertEquals(blocksPage.toastMsg.getText().trim(), mesage);
        asrt.assertAll();
    }


    @Then("check new block with name {string} and discription {string}")
    public void checkNewBlockIsCreatedWithNameAndDiscription(String name, String discription) {
        asrt.assertTrue(blocksPage.blockDescription(name).getText().contains(discription));
        asrt.assertAll();


    }

    //////////////////////////////////////////////////////////////////////
////////////////////////// Edit block from view Button /////////////////////////////////
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

/////////////////////////////////////////////////////////////////////////
    ////////////////////////// filter blocks by name //////////////////////////////

    @And("click on filter button")
    public void clickOnFilterButton() {
        blocksPage.filterButton.click();

    }

    @And("enter the name of block {string} in search criteria")
    public void enterTheNameOfBlockInSearchCriteria(String name) {
        blocksPage.filterNmeField.sendKeys(name);
    }

    @And("click filter search Button")
    public void clickFilterSearchButton() {
        blocksPage.filterSearchButton.click();
    }


    @Then("check filtered blocks cotains name {string}")
    public void checkFilteredBlocksCotainsName(String name) {
        asrt.assertTrue(blocksPage.blocksNames.getFirst().getText().contains(name));
        asrt.assertAll();
    }


    //////////////////////////////////////////////////////////////////////////////
    /////////////////////// filter with dicription /////////////////////////////////
    @And("enter discription of the block {string}")
    public void enterDiscriptionOfTheBlock(String description) {
        blocksPage.filterDexcriptionField.sendKeys(description);
    }

    @Then("check filtered blocks cotains description {string}")
    public void checkFilteredBlocksCotainsDescription(String description) {
        asrt.assertTrue(blocksPage.blocksDescriptions.getFirst().getText().contains(description), "description missmatch");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Delete block ///////////////////////////////////////////////
    @And("clicking on block's more menue button for block {string}")
    public void clickingOnBlockSMoreMenueButtonForBlock(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(blocksPage.blockMoreMenu(name)));
        blocksPage.blockMoreMenu(name).click();
        wait.until(ExpectedConditions.visibilityOf(blocksPage.blockDeleteButton));
    }


    @And("click on block's delete button")
    public void clickOnBlockSDeleteButton() {
        blocksPage.blockDeleteButton.click();
        wait.until(ExpectedConditions.visibilityOf(blocksPage.confirmationPopUp));
    }

    @And("click on confirmation messsage yes button")
    public void clickOnConfirmationMesssageYesButton() {
        blocksPage.confirmationSaveButton().click();
        wait.until(ExpectedConditions.visibilityOf(blocksPage.toastMsg));
    }

//////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// ##Floors## /////////////////////////////////////

    ////////////////// Check Default Floor ///////////////////////
    @And("go to Floors Page")
    public void goToFloorsPage() {
        dashBoardPage.setupPageLink.click();
        setupPagec.blocksAndFloorsDroplist.click();
        setupPagec.floorsLink.click();
    }

    @Then("Check Default Floor name to be {string} and Description to be {string}")
    public void checkDefaultFloorNameToBeAndDescriptionToBe(String name, String description) {
        WebElement floor;
        if (floorsPage.floorsNames.stream().anyMatch(webElement -> webElement.getText().contains(name)))
            asrt.assertEquals(floorsPage.floorDescription(name).getText().trim(), description);
        asrt.assertAll();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Add new floor ////////////////////////////////////////////////
    @And("click on new floor button")
    public void clickOnNewFloorButton() {
        floorsPage.newFloorButton.click();

    }

    @And("Fill the floor name {string} and discription {string}")
    public void fillTheFloorNameAndDiscription(String name, String description) {
        wait.until(ExpectedConditions.visibilityOf(floorsPage.floorNameField));
        wait.until(ExpectedConditions.elementToBeClickable(floorsPage.floorNameField));

        floorsPage.floorNameField.sendKeys(name);
        floorsPage.descriptionField.sendKeys(description);
        floorsPage.addFloorButton.click();
    }

    @Then("check new floor with name {string} and discription {string}")
    public void checkNewFloorWithNameAndDiscription(String name, String descripton) {
        if (floorsPage.floorsNames.stream().anyMatch(webElement -> webElement.getText().contains(name))) {
            asrt.assertEquals(floorsPage.floorDescription(name).getText().trim(), descripton, "descriptions doesnt match ");
            asrt.assertTrue(Integer.parseInt(floorsPage.floorOrder(name).getText()) > Integer.parseInt(floorsPage.floorOrder("1").getText()));
            asrt.assertAll();

        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// Edit Floor /////////////////////////////////////////////////////

    @And("click on the view Button for Floor name {string}")
    public void clickOnTheViewButtonForFloorName(String name) {
        WebElement viewButton = floorsPage.floorViewButton(name);
        wait.until(ExpectedConditions.elementToBeClickable(viewButton));
        viewButton.click();
    }

    @When("click on floor edit Button")
    public void clickOnFloorEditButton() {
        WebElement editButton = floorsPage.viewModeEditButton;
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        editButton.click();

    }

    @And("enter new floor name {string} and new discriptn {string}")
    public void enterNewFloorNameAndNewDiscriptn(String name, String description) {
        floorsPage.floorNameField.sendKeys(name);
        floorsPage.descriptionField.sendKeys(description);


    }

    @And("click on save button for floor edit mood")
    public void clickOnSaveButtonForFloorEditMood() {
        WebElement saveButton = floorsPage.editModeSaveButton;
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Filter floors with block name //////////////////////////////
    String filteredblockname;

    @And("click on the block drop list and select block {string}")
    public void clickOnTheBlockDropListAndSelectBlock(String blockName) {
        List<WebElement> blocksList = floorsPage.filterblocks();
        WebElement selectedBlock = blocksList.get(new Random().nextInt(blocksList.size()));
        filteredblockname = selectedBlock.getText().trim();
        selectedBlock.click();
    }

    @Then("check the grid contaiing only floors with block name {string}")
    public void checkTheGridContaiingOnlyFloorsWithBlockName(String blockName) {
        asrt.assertFalse(floorsPage.blocksNames.stream().anyMatch(webElement -> !webElement.getText().contains(filteredblockname)));
        asrt.assertAll();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////// filter by floor name ///////////////////////////////////////////////////////

    @When("enter name of floor {string} in search criteria")
    public void enterNameOfFloorInSearchCriteria(String floorName) {
        floorsPage.filterNmeField.sendKeys(floorName);
    }

    @Then("Check filtered floors contains only name of {string}")
    public void checkFilteredFloorsContainsOnlyNameOf(String floorName) {
        asrt.assertTrue(floorsPage.floorsNames.stream().anyMatch(webElement -> !webElement.getText().contains(floorName)));
        asrt.assertAll();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////// filter floors by order ///////////////////////////////////////////////////////
    @When("enter order of floor {string} in search criteria")
    public void enterOrderOfFloorInSearchCriteria(String order) {
        floorsPage.filterOrderField.sendKeys(order);
    }

    @Then("Check filtered floors contains only order of {string}")
    public void checkFilteredFloorsContainsOnlyOrderOf(String order) {
        asrt.assertTrue(floorsPage.floorsOrder.stream().anyMatch(webElement -> !webElement.getText().contains(order)));
        asrt.assertAll();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////// filter floors by description ////////////////////////////////////////////////////
    @When("enter description of floor {string} in search criteria")
    public void enterDescriptionOfFloorInSearchCriteria(String description) {
        floorsPage.filterDescriptionField.sendKeys(description);
    }

    @Then("Check filtered floors contains only description of {string}")
    public void checkFilteredFloorsContainsOnlyDescriptionOf(String description) {
        asrt.assertTrue(floorsPage.floorsDescriptions.stream().anyMatch(webElement -> !webElement.getText().contains(description)), "found description diffrent than" + description);
        asrt.assertAll();
    }

    ///////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// delete floors ////////////////////////////
    @When("click on floor with order {string} more menu button")
    public void clickOnFloorWithOrderMoreMenuButton(String order) {
        if (!order.equals("topfloor")) {
            WebElement moreMenu = floorsPage.floorMoreMenu(null, order);
            wait.until(ExpectedConditions.elementToBeClickable(moreMenu));
            moreMenu.click();
        } else {
                String topFloorORder =floorsPage.floorsOrder.getLast().getText();
                if (topFloorORder.equals("1"))
                    System.out.println("this block has only 1 floor");
            WebElement moreMenu = floorsPage.floorMoreMenu(null,topFloorORder);
            wait.until(ExpectedConditions.elementToBeClickable(moreMenu));
            moreMenu.click();
        }

    }

    @And("click on floor's delete button")
    public void clickOnFloorSDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(floorsPage.floorDeleteButton));
        floorsPage.floorDeleteButton.click();
        try {
            floorsPage.confirmationSaveButton().click();

        } catch (NoSuchElementException e) {
            System.out.println("no confirmation popup");
            floorsPage.floorDeleteButton.click();
        }
    }
}
