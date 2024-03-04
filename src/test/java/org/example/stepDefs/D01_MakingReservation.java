package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils;
import org.example.pages.P01_LoginPage;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.reservations.P03_1_ReservationMainDataPage;
import org.example.pages.reservations.P03_5_UnitSelectionPopup;
import org.example.pages.reservations.P03_6_EndReservationPopUp;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class D01_MakingReservation {
    WebDriver driver = Hooks.driver;




    final P01_LoginPage loginPage = new P01_LoginPage();
    final P02_DashBoardPage homePage = new P02_DashBoardPage();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    final P03_1_ReservationMainDataPage reservationMainDataPage = new P03_1_ReservationMainDataPage(driver);
    final WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
    final P03_5_UnitSelectionPopup unitSelectionPopup = new P03_5_UnitSelectionPopup(driver);
    final P03_6_EndReservationPopUp endReservationPopUp = new P03_6_EndReservationPopUp(driver);
    final SoftAssert asrt = new SoftAssert();
    Actions action = new Actions(Hooks.driver);


    @And("open reservations Page")
    public void openReservationsPage() {

       // homePage.homePageLink.click();
       // new P00_multiPurposes(driver).waitLoading();
       // wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(homePage.ReservationsLink)));
           //     homePage.ReservationsLink.click();
        js.executeScript("arguments[0].click();",  homePage.ReservationsLink);
        new P00_multiPurposes(driver).waitLoading();
    }

    @When("Click on Add new Reservation")
    public void clickOnAddNewReservation() {
        //wait.until(ExpectedConditions.urlContains("/reservations"));
        wait.until(ExpectedConditions.elementToBeClickable(reservationMainDataPage.newReservationButton));
//        reservationPage.newReservationButton.click();
        js.executeScript("arguments[0].click();", reservationMainDataPage.newReservationButton);
    }

    @And("Select Reservation source {string} and visit purpose {string}")
    public void selectReservationSourceAndPurpose(String source, String purpose) {

        List<WebElement> reservationSources = reservationMainDataPage.reservationSources();
        WebElement selectedSource;
        if (source.equalsIgnoreCase("RANDOM")) {
            selectedSource = reservationSources.get(0);
        } else {
            selectedSource = reservationSources.stream().filter(element -> element.getText().contains(source)).toList().get(0);
        }
        wait.until(ExpectedConditions.visibilityOf(selectedSource));
        selectedSource.click();

        reservationMainDataPage.visitPurposeDropList.click();
        wait.until(ExpectedConditions.visibilityOf(reservationMainDataPage.familyOrFriendsSelection));
        reservationMainDataPage.familyOrFriendsSelection.click();
    }

    @And("open unit selection Popup")
    public void openUnitSelectionPopup() {
        reservationMainDataPage.selectUnitNowSpan.click();
    }

    @And("select a unit {string}")
    public void selectAUnit(String unit) {

        wait.until(ExpectedConditions.visibilityOf(reservationMainDataPage.panelBar));

       WebElement unitCard;
        if (unit.equalsIgnoreCase("RANDOM")) {
            unitCard = unitSelectionPopup.availableUnits();
        } else {
            unitCard =unitSelectionPopup.availableUnits();
        }
        action.moveToElement(unitCard).perform();
        unitCard.click();
        WebElement confirmBtn = unitSelectionPopup.confirmBtn;
//        wait.until(ExpectedConditions.attributeToBe(confirmBtn,"disabled","null"));
        if (!unitSelectionPopup.checkInConflictionConfirmBtn.isEmpty()) {
            unitSelectionPopup.checkInConflictionConfirmBtn.get(0).click();
        }
        confirmBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(unitCard));
    }


    @And("click on selectguest now button")
    public void clickOnSelectguestNowButton() {
        js.executeScript("arguments[0].click();", reservationMainDataPage.selectGestButton);
    }


    @Then("click on save Reservation button")
    public void clickOnSaveReservationButton() {
        WebElement saveReservationButton = reservationMainDataPage.saveReservationButton;
        wait.until(ExpectedConditions.elementToBeClickable(saveReservationButton));
        new P00_multiPurposes(driver).waitLoading();
        saveReservationButton.click();

    }


    @And("when reservation Summary dialouge appears click on save reservatuon Button")
    public void whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//kendo-dialog")));
        wait.until(ExpectedConditions.elementToBeClickable(reservationMainDataPage.reservationSummarySaveButton));
        reservationMainDataPage.reservationSummarySaveButton.click();
    }

    @And("verify toast message appears with text {string} and the reservation status to be {string}")
    public void verifyToastMessageAppearsWithTextAndTheReservationStatusToBe(String successText, String reservationState) {
        WebElement succesMessage = reservationMainDataPage.toastMessage;
        asrt.assertTrue(succesMessage.getText().contains(successText), "Expected toast: "+successText+"\n actual: "+succesMessage.getText()+"\n");
        new P00_multiPurposes(driver).waitLoading();
        WebElement resStatus = reservationMainDataPage.reservationStatus;
        asrt.assertEquals(resStatus.getText(), reservationState);
        asrt.assertAll();
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
        D06_DigitalPayment d06DigitalPayment = new D06_DigitalPayment();
        clickOnAddNewReservation();
        selectReservationSourceAndPurpose(source, purpose);
        openUnitSelectionPopup();
        selectAUnit(unit);
        clickOnSelectguestNowButton();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        d06DigitalPayment.selectGuest(guest, "", "");
        clickOnSaveReservationButton();
        whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton();
    }

    @And("Choose Reservation Status as {string}")
    public void chooseReservationStatusAs(String status) {
        WebElement actionButton = null;
        wait.until(ExpectedConditions.elementToBeClickable(reservationMainDataPage.reservatinMoreActionsMenu));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new P00_multiPurposes(driver).waitLoading();
        new P00_multiPurposes(driver).waitLoading();
        reservationMainDataPage.reservatinMoreActionsMenu.click();
        if (status.contains("In")) {
            actionButton = reservationMainDataPage.checkInMenuButton;
            wait.until(ExpectedConditions.elementToBeClickable(actionButton));
            actionButton.click();
        } else if (status.contains("Out")) {
            actionButton = reservationMainDataPage.checkoutMenuButton;
            wait.until(ExpectedConditions.elementToBeClickable(actionButton));
            actionButton.click();
            endReservation(status);
        } else if (status.contains("Canceled")) {
            actionButton = reservationMainDataPage.cancelReservationButton;
            wait.until(ExpectedConditions.elementToBeClickable(actionButton));
            actionButton.click();
            endReservation(status);
        }
    }

    void endReservation(String status) {
        if (status.toLowerCase().contains("Out".toLowerCase())) {
            endReservationPopUp.confirmCheckOutButton.click();

            while (!endReservationPopUp.header().isEmpty())
                try {
                    if (endReservationPopUp.skipButton().isDisplayed()) {
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton())));
                        new P00_multiPurposes(driver).waitLoading();
                        endReservationPopUp.skipButton().click();
                    }
                } catch (NoSuchElementException e) {

                    List<WebElement> methods = endReservationPopUp.paymentMethods();
                    wait.until(ExpectedConditions.visibilityOfAllElements(methods));
                    methods.stream().filter(method -> method.getText().contains("Cash")).toList().get(0).click();
                    new P00_multiPurposes(driver).waitLoading();
                    endReservationPopUp.confirmationButton().click();
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(endReservationPopUp.amountField())));
                }
        } else if (status.toLowerCase().contains("Canceled".toLowerCase())) {
            endReservationPopUp.confirmCancelButton().click();
            while (!endReservationPopUp.header().isEmpty())
                try {
                    if (endReservationPopUp.skipButton().isDisplayed()) {
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton())));
                        new P00_multiPurposes(driver).waitLoading();
                        endReservationPopUp.skipButton().click();
                    }
                } catch (NoSuchElementException e) {
                    endReservationPopUp.reasons().get(0).click();
                    endReservationPopUp.confirmationButton().click();
                    if (endReservationPopUp.skipButton().isDisplayed()) {
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton())));
                        new P00_multiPurposes(driver).waitLoading();
                        endReservationPopUp.skipButton().click();
                    }else{
                    List<WebElement> methods = endReservationPopUp.paymentMethods();
                    wait.until(ExpectedConditions.visibilityOfAllElements(methods));
                    methods.stream().filter(method -> method.getText().contains("Cash")).toList().get(0).click();
                    new P00_multiPurposes(driver).waitLoading();
                    endReservationPopUp.confirmationButton().click();
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(endReservationPopUp.amountField())));
                }}
        }

    }

    @Given("Create {string} Reservation withSource {string} purpose {string} Unit {string} Guest {string}")
    public void createReservationWithSourcePurposeUnitGuest(String reservationState, String source, String purpose, String unit, String guest) {
        createASuccessfullReservation(source, purpose, unit, guest);
        chooseReservationStatusAs(reservationState);
        String reservationStatus = "";
        if (reservationState.contains("In")) {
            reservationStatus = "Checked In";
        } else if (reservationState.contains("Out")) {
            reservationStatus = "Checked Out";
        } else if (reservationState.contains("canceled")) {
            reservationStatus = "Canceled";
        }
        verifyToastMessageAppearsWithTextAndTheReservationStatusToBe("Saved Successfully", reservationStatus);
    }


    @And("elect start date {string} and end Date {string}")
    public void selectStartDateAndEndDate(String sDate, String eDate) {
        //new P00_multiPurposes(driver).waitLoading();
        Utils.setDate(reservationMainDataPage.startDateField, sDate);
        Utils.setDate(reservationMainDataPage.endDateField, eDate);
    }

    @Then("Check the rent to equal {string}")
    public void checkTheRentToEqual(String rate) {
        asrt.assertEquals(rate, reservationMainDataPage.renttotal.getText());
        asrt.assertAll();
    }

    @And("click on select corporate")
    public void clickOnSelectCorporate() {
        new P00_multiPurposes(driver).waitLoading();
        reservationMainDataPage.selectCorporateButton.click();
    }
}
