package alia.nazeel.stepDefs;

import alia.nazeel.tools.*;
import io.cucumber.datatable.DataTable;
import alia.nazeel.pages.reservations.*;
import alia.nazeel.pojos.Tax;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;



import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class D01_Reservations {

    final WebDriver driver = DriverManager.getDriver();


    final P02_DashBoardPage homePage = new P02_DashBoardPage(driver);
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    final P03_1_ReservationMainDataPage reservationMainDataPage = new P03_1_ReservationMainDataPage(driver);
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(15));
    final P03_5_UnitSelectionPopup unitSelectionPopup = new P03_5_UnitSelectionPopup(driver);
    final P03_6_EndReservationPopUp endReservationPopUp = new P03_6_EndReservationPopUp(driver);
    final P03_ReservationsPage reservationsPage = new P03_ReservationsPage(driver);
    final P03_8_UnitsRatesPopUp unitsRatesPopUp = new P03_8_UnitsRatesPopUp(driver);
    final P03_9_GuestList guestList = new P03_9_GuestList(driver);
    final CustomAssert asrt = new CustomAssert();
    final Actions action = new Actions(driver);
    @And("open reservations Page")
    public void openReservationsPage() {

        // homePage.homePageLink.click();
        // wait.waitLoading();
        // wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(homePage.ReservationsLink)));
        //     homePage.ReservationsLink.click();
        js.executeScript("arguments[0].click();", homePage.ReservationsLink);
        wait.waitLoading();
    }

    @When("Click on Add new Reservation")
    public void clickOnAddNewReservation() {
        wait.waitLoading();
//        reservationPage.newReservationButton.click();
        js.executeScript("arguments[0].click();", reservationsPage.newReservationButton);
        wait.waitLoading();
    }

    @And("Select Reservation source {string} and visit purpose {string}")
    public void selectReservationSourceAndPurpose(String source, String purpose) {

        List<WebElement> reservationSources = reservationMainDataPage.reservationSources();
        WebElement selectedSource;
        if (source.equalsIgnoreCase("RANDOM")) {
            selectedSource = reservationSources.getFirst();
        } else {
            selectedSource = reservationSources.stream().filter(element -> element.getText().contains(source)).toList().getFirst();
        }
        wait.until(ExpectedConditions.visibilityOf(selectedSource));
        selectedSource.click();

        reservationMainDataPage.visitPurposeDropList.click();
        wait.until(ExpectedConditions.visibilityOf(reservationMainDataPage.familyOrFriendsSelection));
        reservationMainDataPage.familyOrFriendsSelection.click();
    }

    @And("open unit selection Popup")
    public void openUnitSelectionPopup() {
        wait.waitLoading();
        reservationMainDataPage.addUnitButton.click();
    }

    @And("select a unit {string}")
    public void selectAUnit(String unit) {

        if (unit.equalsIgnoreCase("bytype")) {
            unitSelectionPopup.unitTypeButton.click();
            wait.waitLoading();


            unitSelectionPopup.addUnitOfTypeButtton(unitSelectionPopup.unitTypes.getFirst()).click();


        } else {

            wait.until(ExpectedConditions.visibilityOf(unitSelectionPopup.unitsselectionpanel));
            wait.waitLoading();
            WebElement unitCard;
            if (unit.equalsIgnoreCase("RANDOM")) {
                unitCard = unitSelectionPopup.availableUnits();
            } else {
                unitCard = unitSelectionPopup.availableUnits();
            }
            action.moveToElement(unitCard).perform();
            unitCard.click();
        }
        WebElement confirmBtn = unitSelectionPopup.confirmBtn;
//        wait.until(ExpectedConditions.attributeToBe(confirmBtn,"disabled","null"));
        if (!unitSelectionPopup.checkInConflictionConfirmBtn.isEmpty()) {
            unitSelectionPopup.checkInConflictionConfirmBtn.getFirst().click();
        }
        confirmBtn.click();
        wait.waitLoading();
//        wait.until(ExpectedConditions.invisibilityOf(unitCard));
    }


    @And("click on selectguest now button")
    public void clickOnSelectguestNowButton() {
        js.executeScript("arguments[0].click();", reservationMainDataPage.selectGestButton);
    }


    @Then("click on save Reservation button")
    public void clickOnSaveReservationButton() {
        WebElement saveReservationButton = reservationMainDataPage.saveReservationButton;
        wait.until(ExpectedConditions.elementToBeClickable(saveReservationButton));
        wait.waitLoading();
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
        wait.waitLoading();
        WebElement resStatus = reservationMainDataPage.reservationStatus;
        asrt.assertEquals(resStatus.getText(), reservationState);
        asrt.assertAll();
    }


    @And("Choose Reservation Status as {string}")
    public void chooseReservationStatusAs(String status) {
        WebElement actionButton;
        wait.waitLoading();
        reservationMainDataPage.reservatinMoreActionsMenu.click();
        if (status.contains("In")) {
            actionButton = reservationMainDataPage.checkInMenuButton;
            wait.until(ExpectedConditions.elementToBeClickable(actionButton));
            actionButton.click();
        } else if (status.toLowerCase().contains("out")) {
            actionButton = reservationMainDataPage.checkoutMenuButton;
            wait.until(ExpectedConditions.elementToBeClickable(actionButton));
            actionButton.click();
            endReservationPopUp.checkoutTodayButton.click();
            endReservation(status);
        } else if (status.toLowerCase().contains("cancel")) {
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
        if (status.toLowerCase().contains("out")) {
            endReservationPopUp.confirmEndButton.click();
            while (!endReservationPopUp.header().isEmpty())

                if (!endReservationPopUp.skipButton().isEmpty()) {
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton().getFirst())));
                    wait.waitLoading();
                    endReservationPopUp.skipButton().getFirst().click();
                } else {

                    List<WebElement> methods = endReservationPopUp.paymentMethods();
                    wait.until(ExpectedConditions.visibilityOfAllElements(methods));
                    methods.stream().filter(method -> method.getText().contains("Cash")).toList().getFirst().click();
                    wait.waitLoading();
                    endReservationPopUp.confirmationButton().click();
                    wait.waitLoading();
                }
        } else if (status.toLowerCase().contains("canceled")) {
            endReservationPopUp.confirmCancelButton().click();
            while (!endReservationPopUp.header().isEmpty())

                if (!endReservationPopUp.skipButton().isEmpty()) {
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton().getFirst())));
                    wait.waitLoading();
                    endReservationPopUp.skipButton().getFirst().click();
                } else {
                    endReservationPopUp.reasons().getFirst().click();
                    endReservationPopUp.confirmationButton().click();
                    if (!endReservationPopUp.skipButton().isEmpty()) {
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(endReservationPopUp.skipButton().getFirst())));
                        wait.waitLoading();
                        endReservationPopUp.skipButton().getFirst().click();
                    } else {
                        List<WebElement> methods = endReservationPopUp.paymentMethods();
                        wait.until(ExpectedConditions.visibilityOfAllElements(methods));
                        methods.stream().filter(method -> method.getText().contains("Cash")).toList().getFirst().click();
                        wait.waitLoading();
                        endReservationPopUp.confirmationButton().click();
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(endReservationPopUp.amountField())));
                    }
                }
        }

    }

    @Given("Create {string} {string} Reservation withSource {string} purpose {string} Unit {string} Guest {string} startDate {string} endDate {string}")
    public void createSingleReservationWithSourcePurposeUnitGuest(String type, String reservationState, String source, String purpose, String unit, String guest, String sDate, String eDate) {
        createASuccessfullSingleReservation(type, source, purpose, unit, guest, reservationState, sDate, eDate);
        String reservationStatus = "Confirmed";
        if (reservationState.contains("In")) {
            reservationStatus = "Checked In";
        } else if (reservationState.contains("Out")) {
            reservationStatus = "Checked Out";
        } else if (reservationState.contains("canceled")) {
            reservationStatus = "Canceled";
        }
        verifyToastMessageAppearsWithTextAndTheReservationStatusToBe("Saved Successfully", reservationStatus);
        wait.waitLoading();
    }

    public void createASuccessfullSingleReservation(String type, String source, String purpose, String unit, String guest, String state, String sDate, String eDate) {

        clickOnAddNewReservation();
        fillReservationData(type, source, purpose, unit, guest, sDate, eDate);
        saveReservationAs(state);
    }

    @And("Save reservation as {string}")
    public void saveReservationAs(String state) {
        if (state.equalsIgnoreCase("confirmed"))
            clickOnSaveReservationButton();
        else if (state.toLowerCase().contains("in"))
            wait.waitLoading();
        reservationMainDataPage.checkInButton.click();
        whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton();
    }

    public void fillReservationData(String type, String source, String purpose, String unit, String guest, String sDate, String eDate) {
        selectresType(type);
        selectReservationSourceAndPurpose(source, purpose);
        selectStartDateAndEndDate(sDate, eDate);
        openUnitSelectionPopup();
        selectAUnit(unit);
        if (!guest.isEmpty()) {
            clickOnSelectguestNowButton();
            new D06_DigitalPayment().selectGuest(guest, "", "");
        }
    }

    private void selectresType(String type) {
        wait.until(ExpectedConditions.visibilityOfAllElements(reservationMainDataPage.resTypesButtons));
        reservationMainDataPage.resTypesButtons.stream().filter(b -> b.getText().equalsIgnoreCase(type)).findFirst().orElseThrow().click();
    }

    @And("elect start date {string} and end Date {string}")
    public void selectStartDateAndEndDate(String sDate, String eDate) {
        wait.waitLoading();
        if (!sDate.isEmpty())
            Utils.setDate(reservationMainDataPage.startDateField, sDate);
        if (!eDate.isEmpty())
            Utils.setDate(reservationMainDataPage.endDateField, eDate);
    }

    @Then("Check the rent to equal {string}")
    public void checkTheRentToEqual(String rate) {
        asrt.assertEquals(rate, reservationMainDataPage.renttotal.getText());
        asrt.assertAll();
    }

    @And("click on select corporate")
    public void clickOnSelectCorporate() {
        wait.waitLoading();
        reservationMainDataPage.selectCorporateButton.click();
    }

    @Then("check the cards count to be 12 and by loading more each time 12 new cards are displayed")
    public void checkTheCardsCountToBeAndByLoadingMoreEachTimeNewCardsAreDisplayed() {
        asrt.assertTrue(unitSelectionPopup.unitCards.size() == 12);
        unitSelectionPopup.loadMoreLink.click();
        wait.waitLoading();
        asrt.assertTrue(unitSelectionPopup.unitCards.size() == 24);
        unitSelectionPopup.floorsPanels.forEach(f -> asrt.assertTrue(Utils.isSorted(unitSelectionPopup.unitNums(f))));
        asrt.assertAll();

    }

    @When("filtering with {string} as {string}")
    public void filteringWithAs(String filter, String value) {
        wait.waitLoading();
        if (!driver.findElement(By.xpath("//div[contains(@class,\"filter-form__container\")]")).getAttribute("class").contains("is-open"))
            reservationsPage.filterButton.click();
        switch (filter.toLowerCase()) {
            case "restype" ->
                    reservationsPage.filterResType().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "renttype" ->
                    reservationsPage.filterRentType().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "unittype" ->
                    reservationsPage.filterUnitTypes().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "corporate" -> reservationsPage.selectCorp(value).click();
            case "resstate" -> reservationStateFiltering(value);


        }
        reservationsPage.searchButton.click();
    }

    private void reservationStateFiltering(String value) {
        switch (value) {
            case "Confirmed", "Checked In", "" -> reservationsPage.filterCustomStatuses().forEach(s -> {
                WebElement checkBox = s.findElement(By.xpath("./input"));
                if (checkBox.isSelected() && !s.getText().equals(value))
                    s.click();
                else if (s.getText().equals(value) && !checkBox.isSelected())
                    s.click();
            });
            case "All Reservations", "Open Reservations", "In-House Guests", "Pending Reservations",
                 "On Arrival Reservations (Not Checked-In)", "On Arrival Reservations (Checked-In)",
                 "On Arrival Reservations (All)", "On Departure Reservations (Not Checked-Out)",
                 "On Departure Reservations (Checked-Out)", "On Departure Reservations (All)",
                 "Checked-Out Reservations", "Cancelled Reservations" ->
                    reservationsPage.filterPreDefinedStatuses().stream().filter(s -> s.getText().equals(value)).findFirst().orElseThrow().click();
        }

    }

    @And("open a reservation and return to reservations page")
    public void openAReservationAndReturnToReservationsPage() {
        if (!reservationsPage.reservationsNumbers.isEmpty()) {
            reservationsPage.reservationsNumbers.getFirst().click();
            driver.navigate().back();
        }
    }

    @Then("check all reservations records {string} as {string}")
    public void checkAllReservationsRecordsAs(String filter, String value) {
        wait.waitLoading();
        switch (filter) {
            case "resType" -> {
                if (value.equalsIgnoreCase("single"))
                    asrt.assertTrue(reservationsPage.reservationsUnits_Types.stream().anyMatch(t -> !t.getText().contains("Group")));
                else
                    asrt.assertFalse(reservationsPage.reservationsUnits_Types.stream().anyMatch(t -> !t.getText().contains("Group")));
            }
            case "rentType" ->
                    asrt.assertFalse(reservationsPage.reservationsNights.stream().anyMatch(n -> !n.getText().toLowerCase().contains(value.toLowerCase())));
            case "corporate" -> {
                if (value.isEmpty())
                    asrt.AssertAnyMatch(reservationsPage.reservationsGuests_Corps, c -> c.findElements(By.xpath("./span[2]")).isEmpty());
                assertReservationsFilters(reservationsPage.reservationsGuests_Corps, value, "the corporate is not fifltered right");
            }
            case "resState" -> assertReservationsStstuses(value);
        }
        asrt.assertAll();
    }

    private void assertReservationsStstuses(String value) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/YYYY");
        switch (value) {
            case "Confirmed", "Checked In", "Checked Out", "Expired", "Cancelled" ->
                    assertReservationsFilters(reservationsPage.reservationsStatuses, value, "status not filtered right");
            case "All Reservations" -> {
            }

            case "Open Reservations" ->
                    assertReservationsFilters(reservationsPage.reservationsStatuses, "CheckedIn", "status not filtered right");

            case "In-House Guests" -> {
                assertReservationsFilters(reservationsPage.reservationsStatuses, "CheckedIn", "status not filtered right");
                asrt.AssertNonMatch(reservationsPage.reservationsCheckInDates, i -> !Utils.isTimeWithinRange(DateTime.now(dateTimeFormatter.getZone()), DateTime.parse(i.getText(), dateTimeFormatter), DateTime.parse(reservationsPage.reservationCheckOutDate(i).getText(), dateTimeFormatter)));
            }

            case "Pending Reservations" ->
                    asrt.AssertNonMatch(reservationsPage.reservationsStatuses, i -> !(i.getText().contains("UnConfirmed") || i.getText().contains("Confirmed")), "status not filtered right");
            case "On Arrival Reservations (Not Checked-In)" -> {
                asrt.AssertNonMatch(reservationsPage.reservationsStatuses, i -> !(i.getText().contains("UnConfirmed") || i.getText().contains("Confirmed")), "status not filtered right");
                assertDatesAreToday(reservationsPage.reservationsCheckInDates, dateTimeFormatter);
            }
            case "On Arrival Reservations (Checked-In)" -> {
                assertReservationsFilters(reservationsPage.reservationsStatuses, "CheckedIn", "status not filtered right");
                assertDatesAreToday(reservationsPage.reservationsCheckInDates, dateTimeFormatter);
            }
            case "On Arrival Reservations (All)" ->
                    assertDatesAreToday(reservationsPage.reservationsCheckInDates, dateTimeFormatter);
            case "On Departure Reservations (Not Checked-Out)" -> {
                assertReservationsFilters(reservationsPage.reservationsStatuses, "CheckedIn", "status not filtered right");
                assertDatesAreToday(reservationsPage.reservationsCheckOutDates, dateTimeFormatter);
            }
            case "On Departure Reservations (Checked-Out)" -> {
                assertReservationsFilters(reservationsPage.reservationsStatuses, "CheckedOut", "status not filtered right");
                assertDatesAreToday(reservationsPage.reservationsCheckOutDates, dateTimeFormatter);
            }
            case "On Departure Reservations (All)" ->
                    assertDatesAreToday(reservationsPage.reservationsCheckOutDates, dateTimeFormatter);

            case "Checked-Out Reservations" ->
                    assertReservationsFilters(reservationsPage.reservationsStatuses, "CheckedOut", "status not filtered right");
            case "Cancelled Reservations" ->
                    asrt.AssertNonMatch(reservationsPage.reservationsStatuses, i -> !(i.getText().contains("Canceled") || i.getText().contains("Expired")), "status not filtered right");

        }
    }

    private void assertReservationsFilters(List<WebElement> reservationsFilters, String value, String assertionMessage) {
        asrt.AssertNonMatch(reservationsFilters, i -> !i.getText().contains(value), assertionMessage);
    }

    private void assertDatesAreToday(List<WebElement> DatesElements, DateTimeFormatter dateTimeFormatter) {
        asrt.AssertNonMatch(DatesElements, i -> !DateTime.now(dateTimeFormatter.getZone()).equals(DateTime.parse(i.getText())));
    }

    @And("choose page size as {string}")
    public void choosePageSizeAs(String size) {
        wait.waitLoading();
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
        wait.waitLoading();
        Select s = new Select(new P00_multiPurposes(driver).pageSize);
        asrt.assertTrue(s.getFirstSelectedOption().getText().contains("20"));
        asrt.assertAll();
    }

    @And("fill {string} Reservation Data with Source {string} purpose {string} Unit {string} Guest {string} startDate {string} endDate {string}")
    public void fillReservationDataWithSourcePurposeUnitGuest(String type, String source, String purpose, String unit, String guest, String sDate, String eDate) {
        fillReservationData(type, source, purpose, unit, guest, sDate, eDate);
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
            financialPage.discountsList().stream().filter(d -> d.getText().equalsIgnoreCase(discType)).findFirst().orElseThrow().click();
            Double discountAmount = Nazeel_Calculations.getDiscountAmount(financialPage.reservationRent(), discountValue, discType);
            Double reservationRentTaxes = Nazeel_Calculations.reservationRentTaxes(financialPage.reservationRent(), discountAmount, discType, appliedTaxes);
            Double subTotal = discType.contains("From Balance") ? financialPage.reservationRent() : financialPage.reservationRent() - discountAmount;
            Double total;
            asrt.assertTrue(reservationRentTaxes.equals(financialPage.reservationTaxes()), "Calculated Tax = " + reservationRentTaxes + "\n Found Taxes = " + financialPage.reservationTaxes());
            asrt.assertTrue(subTotal.equals(financialPage.reservationSubTotal()), "Expected SubTotal = " + subTotal + "\n Actual subTotal = " + financialPage.reservationSubTotal());
            asrt.assertTrue(discountAmount.equals(financialPage.reservationDiscount()), "Expected Discount = " + discountAmount + "\nActual Discount = " + financialPage.reservationDiscount());
            if (financialPage.isTaxInclusive()) {
                total = discType.contains("From Balance") ? subTotal - discountAmount : subTotal;
            } else {
                total = (subTotal + reservationRentTaxes);
            }
            asrt.assertTrue(total.equals(financialPage.reservationTotal()), "Expected Total = " + total + "\nActual Total = " + financialPage.reservationTotal());
        }
        asrt.assertAll();

    }

    List<Tax> appliedTaxes;

    @And("get applied Taxes on reservation")
    public void getAppliedTaxesOnReservation() {
        reservationMainDataPage.veiwTaxesButton.click();
        P03_7_TaxesPopUp TaxesPopUp = new P03_7_TaxesPopUp(driver);
        appliedTaxes = TaxesPopUp.appliedTaxes();
        TaxesPopUp.closeButton.click();
    }


    @When("Change Rate of the reservation to {string}")
    public void changeRateOfTheReservationTo(String rate) {
        reservationMainDataPage.diffrentRatesInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        reservationMainDataPage.diffrentRatesInput.sendKeys(rate);


    }

    List<String> expectedToasts;

    @Then("check toast messages from the list and the reservation notcreatd")
    public void checkToastMessagesFromTheListAndTheReservation(DataTable table) {
        expectedToasts = table.asList(String.class);
        wait.waitLoading();
        List<WebElement> actualToasts = new P00_multiPurposes(driver).toastMsgs;
        asrt.assertEquals(reservationMainDataPage.reservationNumber.getText(), "New reservation");
        for (String t : expectedToasts) {
            asrt.AssertAnyMatch(actualToasts, i -> i.getText().contains(t));
        }
        asrt.assertAll();
    }

    @Then("Check the failedPopUp with msg {string}")
    public void checkTheFailedPopUpWithMsg(String msg) {
        asrt.AssertContains(reservationMainDataPage.resFailedPopUp.getText(), msg);
        asrt.assertAll();
    }


    @Given("open a reservation of state {string}")
    public void openAReservationOfstate(String state) {
        filteringWithAs("resstate", state);
        wait.waitLoading();
        if (!reservationsPage.reservationsNumbers.isEmpty()) {
            reservationsPage.reservationsNumbers.getFirst().click();
        }

    }

    @Then("Check the disabled date time fields to be {string}")
    public void checkTheDisabledDateTimeFieldsToBe(String disabledFields) {
        checkDatesDisabled(disabledFields);
        asrt.assertAll();

    }

    private void checkDatesDisabled(String disabledFields) {
        if (disabledFields.toLowerCase().contains("checkin")) {
            asrt.assertTrue(!Utils.isEnabled(reservationMainDataPage.startDateField));
        }
        if (disabledFields.toLowerCase().contains("checkout")) {
            asrt.assertTrue(!Utils.isEnabled(reservationMainDataPage.endDateField));
        }
    }

    @When("editing check in date to be tomorrow and saving")
    public void editingCheckInDateToBeTomorrowAndSaving() {
        wait.waitLoading();
        selectStartDateAndEndDate(DateTime.now().plusDays(1).toString(DateTimeFormat.forPattern("dd/MM/YYYY")), "");
        wait.waitLoading();
        clickOnSaveReservationButton();
    }

    @And("Change the checkout Time to now and save")
    public void changeTheCheckoutTimeToNowAndSave() {

        reservationMainDataPage.endtimeButton.click();
        LocalTime now = DateTime.now().toLocalTime();
        wait.until(ExpectedConditions.visibilityOf(reservationMainDataPage.HoursField));
        reservationMainDataPage.HoursField.sendKeys(now.toString(DateTimeFormat.forPattern("HH")));
        if (!now.toString(DateTimeFormat.forPattern("a")).equalsIgnoreCase(reservationMainDataPage.dayNightButton.getText())) {
            reservationMainDataPage.dayNightButton.click();
            reservationMainDataPage.setTimeButton.click();
        }
        clickOnSaveReservationButton();
    }

    @Then("checking out with today should not emerge the penalty tab")
    public void checkingOutWithTodayShouldNotEmergeThePenaltyTab() {
        reservationMainDataPage.reservatinMoreActionsMenu.click();
        WebElement actionButton = reservationMainDataPage.checkoutMenuButton;
        wait.until(ExpectedConditions.elementToBeClickable(actionButton));
        actionButton.click();
        endReservationPopUp.checkoutTodayButton.click();
        asrt.assertTrue(Color.fromString(endReservationPopUp.penaltyTabHeader.getCssValue("color")).asHex().equals("#dfe6ee"));
        endReservation("Check Out");
        asrt.assertAll();
    }

    @Given("Create a {string} monthly Reservation from {string} for {string} months")
    public void createAMonthlyReservationFromForMonths(String resState, String sDate, String rentPeriod) {
        if (sDate.equalsIgnoreCase("lastmonth"))
            sDate = DateTime.now().minusMonths(1).toString(DateTimeFormat.forPattern("dd/MM/YYYY"));
        clickOnAddNewReservation();
        reservationMainDataPage.rentalPeriodDropList().selectByText("Monthly");
        wait.waitLoading();
        fillReservationData("single", "Random", "Random", "Random", "Random", sDate, "");
        reservationMainDataPage.rentalPeriodField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        reservationMainDataPage.rentalPeriodField.sendKeys(rentPeriod);
        wait.waitLoading();
        saveReservationAs(resState);
        wait.waitLoading();
    }

    @And("Change the unit on the reservation")
    public void changeTheUnitOnTheReservation() {
        reservationMainDataPage.unitChangeButton(reservationMainDataPage.resUnits().getFirst()).click();
        wait.until(ExpectedConditions.visibilityOf(reservationMainDataPage.confirmChangeUnitMessage));
        String firstsegment = "Are you sure you want to change this unit";
        String secondSement = "Guests assigned to this unit will be moved to the new one\nYou will no longer be able to change calender view for this reservation";
        asrt.assertTrue(Pattern.matches(Pattern.quote(firstsegment) + ".*" + Pattern.quote(secondSement), reservationMainDataPage.confirmChangeUnitMessage.getText()));
        reservationMainDataPage.confirmChangeUnitButton.click();
        selectAUnit("Random");
        wait.until(ExpectedConditions.visibilityOf(unitSelectionPopup.applyChangeUnitButton));
        unitSelectionPopup.applyChangeUnitButton.click();
        wait.waitLoading();
        clickOnSaveReservationButton();
        wait.waitLoading();
    }

    @Then("Check cant change callender type and start date is disabled")
    public void checkCantChangeCallenderType() {
        asrt.assertTrue(!reservationMainDataPage.unitsTypes().getFirst().findElements(By.xpath("//div[contains(@class,\"replace-info-icon \")]")).isEmpty());
        openRatesPopUp();
        for (int i = 1; i < unitsRatesPopUp.calender().getGridRows().size(); i++) {
            int j = 2;
            if (unitsRatesPopUp.calender().getTableCell(i, j).getAttribute("class").contains("calendar__disabled-cell")) {
                asrt.assertFalse(unitsRatesPopUp.calender().getTableCell(i, 3).getAttribute("class").contains("calendar__disabled-cell"));
                asrt.assertFalse(unitsRatesPopUp.calender().getTableCell(i, 4).getAttribute("class").contains("calendar__disabled-cell"));
            } else {
                asrt.assertTrue(unitsRatesPopUp.calender().getTableCell(i, 3).getAttribute("class").contains("calendar__disabled-cell"));
                asrt.assertTrue(unitsRatesPopUp.calender().getTableCell(i, 4).getAttribute("class").contains("calendar__disabled-cell"));
            }
            checkDatesDisabled("check in");
        }
        unitsRatesPopUp.confirmRatesButton.click();
        wait.waitLoading();
        chooseReservationStatusAs("canceled");
        asrt.assertAll();
    }


    public void openRatesPopUp() {
        reservationMainDataPage.unitsRatesPopUpButton.click();
    }

    @Then("add dependent to single reservtion {string}")
    public void addDependentToSingleReservtion(String depName) {
        reservationMainDataPage.dpendentsButton.click();

        guestList.addDependentButton.click();

        new D06_DigitalPayment().selectGuest(depName, "", "");
    }

    @Then("Check all selectedunits are by type")
    public void checkAllSelectedunitsAreByType() {
        asrt.AssertNonMatch(reservationMainDataPage.resUnits(), i -> !i.getText().equals("---"));
        asrt.assertAll();
    }

    @And("add dependent {string} to the the room {string} and save")
    public void addADependentToTheTheRoomAndSave(String dep, String roomNum) {
        reservationMainDataPage.dpendentsButton.click();

        selectDependent(dep);
        asrt.AssertAnyMatch(guestList.guestsGrid().getGridCells(2), (c -> c.getText().equalsIgnoreCase(dep.equalsIgnoreCase("random") ? "depndent guest" : dep)));
        guestList.confirmDependentsButton.click();
        wait.waitLoading();
    }

    private void selectDependent(String dep) {
        guestList.reservationRooms().selectByIndex(0);
        guestList.nameButton.click();
        guestList.searchField.sendKeys(dep.equalsIgnoreCase("random") ? "depndent guest" : dep);
        guestList.searchButton.click();
        wait.waitLoading();
        guestList.searchGrid().getGridCells(0).stream().filter(c -> c.getText().equalsIgnoreCase(dep.equalsIgnoreCase("random") ? "depndent guest" : dep)).findFirst().orElseThrow().click();
        wait.waitLoading();

    }

    @When("checking out dependent {string}")
    public void checkingOutHeDependent(String depName) {
        reservationMainDataPage.dpendentsButton.click();
        wait.waitLoading();
        WebElement guest = guestList.guestsGrid().getGridCells(2).stream().filter(c -> c.getText().equalsIgnoreCase(depName.equalsIgnoreCase("random") ? "depndent guest" : depName)).findFirst().orElseThrow();
        guestList.checkOutGuest(guest);
        guestList.confirmDependentsButton.click();
        wait.waitLoading();
    }

    @Then("adding dependent {string} again Check toast message {string} and the dependet has undo chek out button")
    public void addingTheSameDependentAgainCheckToastMessageAndTheDependetHasUndoChekOutButton(String depName, String msg) {
        reservationMainDataPage.dpendentsButton.click();
        WebElement guest = guestList.guestsGrid().getGridCells(2).stream().filter(c -> c.getText().equalsIgnoreCase(depName.equalsIgnoreCase("random") ? "depndent guest" : depName)).findFirst().orElseThrow();
        selectDependent("random");
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        asrt.assertTrue(guestList.guestsGrid().getGridCell(guest, 7).findElement(By.xpath(".//button[contains(@class,\"button--green\")]")).isDisplayed());

        asrt.assertAll();
        guestList.confirmDependentsButton.click();
    }
}
