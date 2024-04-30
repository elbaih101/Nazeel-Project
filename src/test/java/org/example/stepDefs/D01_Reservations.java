package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Nazeel_Calculations;
import org.example.Utils;
import org.example.pages.P01_LoginPage;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.reservations.*;
import org.example.pojos.Tax;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class D01_Reservations {
    WebDriver driver = Hooks.driver;



    final P02_DashBoardPage homePage = new P02_DashBoardPage(driver);
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    final P03_1_ReservationMainDataPage reservationMainDataPage = new P03_1_ReservationMainDataPage(driver);
    final WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
    final P03_5_UnitSelectionPopup unitSelectionPopup = new P03_5_UnitSelectionPopup(driver);
    final P03_6_EndReservationPopUp endReservationPopUp = new P03_6_EndReservationPopUp(driver);
    P03_ReservationsPage reservationsPage = new P03_ReservationsPage(driver);
    final SoftAssert asrt = new SoftAssert();
    Actions action = new Actions(Hooks.driver);


    @And("open reservations Page")
    public void openReservationsPage() {

        // homePage.homePageLink.click();
        // new P00_multiPurposes(driver).waitLoading();
        // wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(homePage.ReservationsLink)));
        //     homePage.ReservationsLink.click();
        js.executeScript("arguments[0].click();", homePage.ReservationsLink);
        new P00_multiPurposes(driver).waitLoading();
    }

    @When("Click on Add new Reservation")
    public void clickOnAddNewReservation() {
        new P00_multiPurposes(driver).waitLoading();
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
        new P00_multiPurposes(driver).waitLoading();
        reservationMainDataPage.selectUnitNowSpan.click();
    }

    @And("select a unit {string}")
    public void selectAUnit(String unit) {

        wait.until(ExpectedConditions.visibilityOf(reservationMainDataPage.panelBar));

        WebElement unitCard;
        if (unit.equalsIgnoreCase("RANDOM")) {
            unitCard = unitSelectionPopup.availableUnits();
        } else {
            unitCard = unitSelectionPopup.availableUnits();
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
        asrt.assertTrue(succesMessage.getText().contains(successText), "Expected toast: " + successText + "\n actual: " + succesMessage.getText() + "\n");
        new P00_multiPurposes(driver).waitLoading();
        WebElement resStatus = reservationMainDataPage.reservationStatus;
        asrt.assertEquals(resStatus.getText(), reservationState);
        asrt.assertAll();
    }


    public void fillReservationData(String source, String purpose, String unit, String guest) {
        selectReservationSourceAndPurpose(source, purpose);
        openUnitSelectionPopup();
        selectAUnit(unit);
        clickOnSelectguestNowButton();
        new D06_DigitalPayment().selectGuest(guest, "", "");
    }

    @Given("create a successfull reservation Source {string} purpose {string} Unit {string} Guest {string} state {string}")
    public void createASuccessfullReservation(String source, String purpose, String unit, String guest, String state) {

        clickOnAddNewReservation();
        fillReservationData(source, purpose, unit, guest);
        if (state.equalsIgnoreCase("confirmed"))
            clickOnSaveReservationButton();
        else if (state.toLowerCase().contains("in"))
            reservationMainDataPage.checkInButton.click();
        whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton();
    }

    @And("Choose Reservation Status as {string}")
    public void chooseReservationStatusAs(String status) {
        WebElement actionButton ;
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
        for (D08_Vouchers.VouchersMap v : D08_Vouchers.vouchersMaps) {
            v.setvState("Ended");
        }

    }

    void endReservation(String status) {
        if (status.toLowerCase().contains("Out".toLowerCase())) {
            endReservationPopUp.confirmCheckOutButton.click();

            while (!endReservationPopUp.header().isEmpty())

                if (!endReservationPopUp.skipButton().isEmpty()) {
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton().getFirst())));
                    new P00_multiPurposes(driver).waitLoading();
                    endReservationPopUp.skipButton().get(0).click();
                } else {

                    List<WebElement> methods = endReservationPopUp.paymentMethods();
                    wait.until(ExpectedConditions.visibilityOfAllElements(methods));
                    methods.stream().filter(method -> method.getText().contains("Cash")).toList().get(0).click();
                    new P00_multiPurposes(driver).waitLoading();
                    endReservationPopUp.confirmationButton().click();
                    new P00_multiPurposes(driver).waitLoading();
                }
        } else if (status.toLowerCase().contains("Canceled".toLowerCase())) {
            endReservationPopUp.confirmCancelButton().click();
            while (!endReservationPopUp.header().isEmpty())

                if (!endReservationPopUp.skipButton().isEmpty()) {
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton().getFirst())));
                    new P00_multiPurposes(driver).waitLoading();
                    endReservationPopUp.skipButton().getFirst().click();
                } else {
                    endReservationPopUp.reasons().get(0).click();
                    endReservationPopUp.confirmationButton().click();
                    if (!endReservationPopUp.skipButton().isEmpty()) {
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton().getFirst())));
                        new P00_multiPurposes(driver).waitLoading();
                        endReservationPopUp.skipButton().getFirst().click();
                    } else {
                        List<WebElement> methods = endReservationPopUp.paymentMethods();
                        wait.until(ExpectedConditions.visibilityOfAllElements(methods));
                        methods.stream().filter(method -> method.getText().contains("Cash")).toList().get(0).click();
                        new P00_multiPurposes(driver).waitLoading();
                        endReservationPopUp.confirmationButton().click();
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(endReservationPopUp.amountField())));
                    }
                }
        }

    }

    @Given("Create {string} Reservation withSource {string} purpose {string} Unit {string} Guest {string}")
    public void createReservationWithSourcePurposeUnitGuest(String reservationState, String source, String purpose, String unit, String guest) {
        createASuccessfullReservation(source, purpose, unit, guest, reservationState);
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
        new P00_multiPurposes(driver).waitLoading();
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

    @Then("check the cards count to be 12 and by loading more each time 12 new cards are displayed")
    public void checkTheCardsCountToBeAndByLoadingMoreEachTimeNewCardsAreDisplayed() {
        asrt.assertTrue(unitSelectionPopup.unitCards.size() == 12);
        unitSelectionPopup.loadMoreLink.click();
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertTrue(unitSelectionPopup.unitCards.size() == 24);
        //  unitSelectionPopup.floorsPanels.forEach(f-> asrt.assertTrue(Utils.isSorted(unitSelectionPopup.unitNums(f))));
        asrt.assertAll();

    }

    @When("filtering with {string} as {string}")
    public void filteringWithAs(String filter, String value) {
        reservationsPage.filterButton.click();
        switch (filter) {
            case "resType" ->
                    reservationsPage.filterResType().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "rentType" ->
                    reservationsPage.filterRentType().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "unitType" ->
                    reservationsPage.filterUnitTypes().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
        }
        reservationsPage.searchButton.click();
    }

    @And("open a reservation and return to reservations page")
    public void openAReservationAndReturnToReservationsPage() {
        reservationsPage.reservationsNumbers.getFirst().click();
        driver.navigate().back();
    }

    @Then("check all reservations records {string} as {string}")
    public void checkAllReservationsRecordsAs(String filter, String value) {
        new P00_multiPurposes(driver).waitLoading();
        switch (filter) {
            case "resType" -> {
                if (value.equalsIgnoreCase("single"))
                    asrt.assertTrue(reservationsPage.reservationsUnits_Types.stream().anyMatch(t -> !t.getText().contains("Group")));
                else
                    asrt.assertFalse(reservationsPage.reservationsUnits_Types.stream().anyMatch(t -> !t.getText().contains("Group")));
            }
            case "rentType" ->
                    asrt.assertFalse(reservationsPage.reservationsNights.stream().anyMatch(n -> !n.getText().toLowerCase().contains(value.toLowerCase())));
            // case "unitType" ->

        }
        asrt.assertAll();
    }

    @And("choose page size as {string}")
    public void choosePageSizeAs(String size) {
        new P00_multiPurposes(driver).waitLoading();
        Select s = new Select(new P00_multiPurposes(driver).pageSize);
        s.selectByValue(size);
    }

    @And("Check page size equal to {string}")
    public void checkPageSizeEqualTo(String size) {
        Select s = new Select(new P00_multiPurposes(driver).pageSize);
        asrt.assertTrue(s.getFirstSelectedOption().getText().contains(size));
        asrt.assertAll();
    }

    @When("refresh page")
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Then("check the search criteria is reset")
    public void checkTheSearchCriteriaIsReset() {
        new P00_multiPurposes(driver).waitLoading();
        Select s = new Select(new P00_multiPurposes(driver).pageSize);
        asrt.assertTrue(s.getFirstSelectedOption().getText().contains("20"));
        asrt.assertAll();
    }

    @And("fill Reservation Data with Source {string} purpose {string} Unit {string} Guest {string}")
    public void fillReservationDataWithSourcePurposeUnitGuest(String source, String purpose, String unit, String guest) {
        fillReservationData(source, purpose, unit, guest);
    }

    @Then("Check all Discounts types against Taxes Calculations and Balnce")
    public void checkAllDiscountsTypesAgainstTaxesCalculationsAndBalnce() {
        double discountValue = 10.0;
        P03_2_ReservationFinancialPage financialPage = new P03_2_ReservationFinancialPage(driver);
        financialPage.discountAmountField.sendKeys(Double.toString(discountValue));
        List<String> discountsTypes = new ArrayList<>();
        for (WebElement dicType : financialPage.discountsList()) {
            discountsTypes.add(dicType.getText());
        }
        financialPage.discountAmountField.click();
        for (String discType : discountsTypes) {
            financialPage.discountsList().stream().filter(d -> d.getText().equalsIgnoreCase(discType)).findFirst().orElse(null).click();
            Double discountAmount = Nazeel_Calculations.getDiscountAmount(financialPage.reservationRent(), discountValue, discType);
            Double reservationRentTaxes = Nazeel_Calculations.reservationRentTaxes(financialPage.reservationRent(), discountAmount, discType, appliedTaxes);
            Double subTotal = discType.contains("From Balance") ? financialPage.reservationRent() : financialPage.reservationRent() - discountAmount;
            Double total;
            asrt.assertTrue(reservationRentTaxes.equals(financialPage.reservationTaxes()), "Calculated Tax = " + reservationRentTaxes + "\n Found Taxes = " + financialPage.reservationTaxes());
            asrt.assertTrue( subTotal.equals(financialPage.reservationSubTotal()), "Expected SubTotal = " + subTotal + "\n Actual subTotal = " + financialPage.reservationSubTotal());
            asrt.assertTrue(discountAmount.equals(financialPage.reservationDiscount()), "Expected Discount = " + discountAmount + "\nActual Discount = " + financialPage.reservationDiscount());
            if (financialPage.isTaxInclusive()) {
                total =discType.contains("From Balance") ?subTotal-discountAmount : subTotal;
            } else {
                total = (subTotal + reservationRentTaxes);
            }
            asrt.assertTrue(total.equals(financialPage.reservationTotal()), "Expected Total = " + total + "\nActual Total = " + financialPage.reservationTotal());
        }
        asrt.assertAll();

    }

    List<Tax> appliedTaxes;

    @And("ge applied Taxes on reservation")
    public void geAppliedTaxesOnReservation() {
        reservationMainDataPage.veiwTaxesButton.click();
        P03_7_TaxesPopUp TaxesPopUp = new P03_7_TaxesPopUp(driver);
        appliedTaxes = TaxesPopUp.appliedTaxes();
        TaxesPopUp.closeButton.click();
    }
}
