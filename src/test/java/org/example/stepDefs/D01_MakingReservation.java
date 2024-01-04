package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P01_LoginPage;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.reservations.P03_1_ReservationMainDataPage;
import org.example.pages.reservations.P03_5_UnitSelectionPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class D01_MakingReservation {
    final WebDriver driver = Hooks.driver;
    final P01_LoginPage loginPage = new P01_LoginPage();
    final P02_DashBoardPage homePage = new P02_DashBoardPage();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    final P03_1_ReservationMainDataPage reservationPage = new P03_1_ReservationMainDataPage(driver);
    final WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
    final P03_5_UnitSelectionPopup unitSelectionPopup = new P03_5_UnitSelectionPopup(driver);
    final SoftAssert asrt = new SoftAssert();
    Actions action = new Actions(Hooks.driver);


    @And("open reservations Page")
    public void openReservationsPage() {
        wait.until(ExpectedConditions.elementToBeClickable(homePage.ReservationsLink));
        homePage.ReservationsLink.click();
    }

    @When("Click on Add new Reservation")
    public void clickOnAddNewReservation() {
        //wait.until(ExpectedConditions.urlContains("/reservations"));
        wait.until(ExpectedConditions.elementToBeClickable(reservationPage.newReservationButton));
        reservationPage.newReservationButton.click();
    }

    @And("Select Reservation source {string} and visit purpose {string}")
    public void selectReservationSourceAndPurpose(String source, String purpose) {

        List<WebElement> reservationSources = reservationPage.reservationSources();
        WebElement selectedSource;
        if (source.equalsIgnoreCase("RANDOM")) {
            selectedSource = reservationSources.get(new Random().nextInt(reservationSources.size()));
        } else {
            selectedSource = reservationSources.stream().filter(element -> element.getText().contains(source)).toList().get(0);
        }
        wait.until(ExpectedConditions.visibilityOf(selectedSource));
        selectedSource.click();

        reservationPage.visitPurposeDropList.click();
        wait.until(ExpectedConditions.visibilityOf(reservationPage.familyOrFriendsSelection));
        reservationPage.familyOrFriendsSelection.click();
    }

    @And("open unit selection Popup")
    public void openUnitSelectionPopup() {
        reservationPage.selectUnitNowSpan.click();
    }

    @And("select a unit {string}")
    public void selectAUnit(String unit) {

        wait.until(ExpectedConditions.visibilityOf(reservationPage.panelBar));
        List<WebElement> cards = unitSelectionPopup.availableUnits();
        WebElement unitCaerd;
        if (unit.equalsIgnoreCase("RANDOM")) {
            unitCaerd = cards.get(new Random().nextInt(cards.size()));
        } else {
            unitCaerd = cards.get(new Random().nextInt(cards.size()));
        }
        action.moveToElement(unitCaerd).perform();
        unitCaerd.click();
        WebElement confirmBtn = unitSelectionPopup.confirmBtn;
//        wait.until(ExpectedConditions.attributeToBe(confirmBtn,"disabled","null"));
        confirmBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(unitCaerd));
    }


    @And("click on selectguest now button")
    public void clickOnSelectguestNowButton() {
        js.executeScript("arguments[0].click();", reservationPage.selectGestButton);
    }


    @Then("click on save Reservation button")
    public void clickOnSaveReservationButton() {
        WebElement saveReservationButton = reservationPage.saveReservationButton;
        wait.until(ExpectedConditions.elementToBeClickable(saveReservationButton));
        saveReservationButton.click();

    }


    @And("when reservation Summary dialouge appears click on save reservatuon Button")
    public void whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//kendo-dialog")));
        wait.until(ExpectedConditions.elementToBeClickable(reservationPage.reservationSummarySaveButton));
        reservationPage.reservationSummarySaveButton.click();
    }

    @And("verify toast message appears with text {string} and the reservation status to be {string}")
    public void verifyToastMessageAppearsWithTextAndTheReservationStatusToBe(String successText, String confirmationText) {
        WebElement succesMessage = reservationPage.toastMessage;
        wait.until(ExpectedConditions.visibilityOf(succesMessage));
        asrt.assertTrue(succesMessage.getText().contains(successText));
        asrt.assertAll();
        WebElement resStatus = reservationPage.reservationStatus;
        Assert.assertTrue(resStatus.getText().contains(confirmationText));
        clickOnSaveReservationButton();
        whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton();

    }

    void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Given("create a successfull reservation Source {string} purpose {string} Unit {string} Guest {string}")
    public void createASuccessfullReservation(String source, String purpose, String unit, String guest) {
        openReservationsPage();
        D06_DigitalPayment d06DigitalPayment = new D06_DigitalPayment();
        clickOnAddNewReservation();
        selectReservationSourceAndPurpose(source, purpose);
        openUnitSelectionPopup();
        selectAUnit(unit);
        clickOnSelectguestNowButton();
        d06DigitalPayment.selectGuest(guest, "", "");
        clickOnSaveReservationButton();
        whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton();
    }

    @And("Choose Reservation Status as {string}")
    public void chooseReservationStatusAs(String status) {
        if (status.equalsIgnoreCase("Checked-In")) {
            wait.until(ExpectedConditions.elementToBeClickable(reservationPage.reservatinMoreActionsMenu));
            reservationPage.reservatinMoreActionsMenu.click();
            reservationPage.checkInMenuButton.click();
        }


    }
}
