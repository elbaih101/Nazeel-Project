package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;

import alia.nazeel.pages.setuppages.rules_pages.P27_ReservationRules;
import alia.nazeel.tools.CustomAssert;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.rules_pages.P33_Penalties;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.time.Duration;
import java.util.HashMap;

public class D13_ProppertyRules {



    final WebDriver driver = DriverManager.getDriver();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    final CustomAssert asrt = new CustomAssert();
     final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P27_ReservationRules reservationRules = new P27_ReservationRules(driver);
    final P33_Penalties penalties = new P33_Penalties(driver);
    final String sucMsg = "Saved Successfully";

    @Given("go to Reservation Rules Page")
    public void goToReservationRulesPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.rulesDropList.click();
        setupPage.reservationRulesLink.click();
        wait.waitLoading();
    }

    @Given("choose {string} view and save")
    public void chooseViewAndSave(String view) {
        switch (view.toLowerCase()) {
            case "list" -> reservationRules.listViewButton.click();
            case "units" -> reservationRules.unitsViewButton.click();
            case "calender" -> reservationRules.calenderViewButton.click();
        }
        reservationRules.submitButton.click();
    }

    @Then("check {string} view is selecetd")
    public void checkViewIsSelecetd(String view) {
        wait.waitLoading();
        WebElement button = null;
        switch (view.toLowerCase()) {
            case "list" -> button = reservationRules.listViewButton;
            case "units" -> button = reservationRules.unitsViewButton;
            case "calender" -> button = reservationRules.calenderViewButton;
        }
        assert button != null;
        asrt.assertTrue(button.getAttribute("class").contains("k-state-active"));
        asrt.assertAll();
    }

    @When("set Checkin time {string} and Check out time {string} grace Period {string}")
    public void setCheckinTimeAndCheckOutTimeGracePeriod(String in, String out, String grace) {
        Utils.setTime(reservationRules.checkInTimeField, in);
        Utils.setTime(reservationRules.checkOutTimeField, out);
        reservationRules.gracePeriodsList().stream().filter(g -> g.getText().equals(grace)).findAny().orElseThrow().click();
    }

    @And("switch {string} SWitch {string}")
    public void switchSWitch(String name, String state) {
        WebElement swit = getSwitch(name);
        if ((state.equalsIgnoreCase("on") && swit.getAttribute("class").contains("k-switch-off")) || (state.equalsIgnoreCase("off") && swit.getAttribute("class").contains("k-switch-on")))
            swit.click();
    }

    private WebElement getSwitch(String name) {
        WebElement swit;
        wait.waitLoading();
        switch (name.toLowerCase()) {
            case "previousdayclac" -> swit = reservationRules.previousDayClacSwitch;
            case "autoextenddaily" -> swit = reservationRules.autoExtendDailySwitch;
            case "autoextendformonthly" -> swit = reservationRules.autoExtendForMonthlySwitch;
            case "restrictchangeunit" -> swit = reservationRules.restrictChangeUnitSwitch;
            case "reasonsrequire" -> swit = reservationRules.reasonsRequireSwitch;
            case "enableunconfirmed" -> swit = reservationRules.enableUnconfirmedSwitch;
            case "enablemonthly" -> swit = reservationRules.enableMonthlySwitch;
            case "autonoshow" -> swit = reservationRules.autoNoShowSwitch;
            case "autorejectota" -> swit = reservationRules.autoRejectOTASwitch;
            case "mandatorycheckintoday" -> swit = reservationRules.mandatoryCheckInTodaySwitch;
            case "skipbalancepay" -> swit = reservationRules.skipBalancePaySwitch;
            case "resetsequence" -> swit = reservationRules.resetSequenceSwitch;

            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        }
        return swit;
    }

    @And("set auto extend to be {string}")
    public void setAutoExtendToBe(String auEx) {
        if (!auEx.isEmpty()) {
            switchSWitch("autoextenddaily", "on");
            reservationRules.autoExtendAfterTimeField.clear();
            Utils.setTime(reservationRules.autoExtendAfterTimeField, auEx);
        }
    }

    @Then("save and check the msg {string} and fields after edit")
    public void saveAndCheckTheMsgAndFieldsAfterEdit(String msg) {
        reservationRules.submitButton.click();
        if (!msg.contains(sucMsg)) {
            asrt.assertTrue(reservationRules.msg.getText().contains(msg));
            asrt.assertAll();
        } else {
            new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        }
    }

    @And("Check the inTime {string} and out time {string} and auto etend after {string}")
    public void checkTheInTimeAndOutTimeAndAutoEtendAfter(String in, String out, String auEx) {
        asrt.assertEquals(reservationRules.checkInTimeField.getAttribute("value"), in);
        asrt.assertEquals(reservationRules.checkOutTimeField.getAttribute("value"), out);
        asrt.assertEquals(reservationRules.autoExtendAfterTimeField.getAttribute("value"), auEx, "Expected auto Extend: ");
        asrt.assertAll();


    }

    @Then("save and check switch {string} is {string}")
    public void saveAndCheckSwitchIs(String name, String state) {
        reservationRules.submitButton.click();
        new D03_BlocksAndFloors().checkToastMesageContainsText(sucMsg);
        wait.waitLoading();
        WebElement swit = getSwitch(name);
        switch (state.toLowerCase()) {
            case "on" -> asrt.assertTrue(swit.getAttribute("class").contains("k-switch-on"));


            case "off" -> {
                asrt.assertTrue(swit.getAttribute("class").contains("k-switch-off"));
                switch (name) {
                    case "autonoshow" -> {
                        asrt.assertFalse(Utils.isEnabled(reservationRules.autoNoShowTimeField));
                        asrt.assertFalse(Utils.isEnabled(reservationRules.autoNoShowReasonsDropList));

                    }
                    case "autorejectota" ->
                            asrt.assertFalse(Utils.isEnabled(reservationRules.cancelReasonsDropListButton));
                    case "restrictchangeunit" ->
                            asrt.assertFalse(Utils.isEnabled(reservationRules.unitAllowanceDropList));
                }
            }
        }

        asrt.assertAll();
    }

    @When("changing the unit allowance period {string}")
    public void changingTheUnitAllowancePeriod(String allowance) {
        switchSWitch("restrictchangeunit", "on");
        reservationRules.unitAllownces().stream().filter(al -> al.getText().equals(allowance)).findAny().orElseThrow().click();
        reservationRules.submitButton.click();
    }

    @Then("Check the period to be {string}")
    public void checkThePeriodToBe(String allowance) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(sucMsg);
        wait.waitLoading();
        asrt.assertTrue(reservationRules.unitAllowanceDropList.getText().equals(allowance));
        asrt.assertAll();
    }

    @When("changing the in time to {string} the auto no show time to {string} and reasons {string}")
    public void changingTheAutoNoShowTimeToAndReasons(String in, String noShow, String reas) {
        Utils.setTime(reservationRules.checkInTimeField, in);
        switchSWitch("autonoshow", "on");
        Utils.setTime(reservationRules.autoNoShowTimeField, noShow);
        if (!reas.isEmpty())
            reservationRules.autoNoShowReasons().stream().filter(al -> al.getText().equals(reas)).findAny().orElseThrow().click();
        reservationRules.submitButton.click();
    }

    @Then("Check the msg {string} the time to be  {string} and reasons {string}")
    public void checkTheMsgTheTimeToBeAndReasons(String msg, String noShTime, String reas) {
        if (!msg.contains(sucMsg)) {
            asrt.assertTrue(reservationRules.msg.getText().contains(msg));
        } else {
            new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        }
        asrt.assertTrue(reservationRules.autoNoShowTimeField.getAttribute("value").equalsIgnoreCase(noShTime), "Expected: " + noShTime + "\nActual: " + reservationRules.autoNoShowTimeField.getAttribute("value"));
        asrt.assertTrue(reservationRules.noShowReasonFiled.getAttribute("value").contains(reas), "reasons not same");
        asrt.assertAll();

    }

    @When("changing the auto cancel reason to {string}")
    public void changingTheAutoCancelReasonTo(String reas) {
        if (!dashBoardPage.onlineReservationsLink.isEmpty()) {
            switchSWitch("autorejectota", "on");
            reservationRules.cancelREasonsList().stream().filter(re -> re.getText().equals(reas)).findAny().orElseThrow().click();
            reservationRules.submitButton.click();
        }
    }

    @And("cancel reasons are {string}")
    public void cancelReasonsAre(String reas) {
        asrt.assertTrue(reservationRules.cancelReasonFiled.getAttribute("value").equals(reas));
        asrt.assertAll();
    }

    @Given("go to penalties Page")
    public void goToPenaltiesPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.rulesDropList.click();
        setupPage.penaltiesLink.click();
    }


    final HashMap<String, String> penaltyMap = new HashMap<>();

    private void setPenaltyMap(String name, String categ, String type, String amount, String calcOf, String desc, String state) {
        if (!name.isEmpty())
            penaltyMap.put("name", name);

        if (!categ.isEmpty())
            penaltyMap.put("categ", categ);

        if (!type.isEmpty()) {
            if (type.equalsIgnoreCase("amount"))
                penaltyMap.put("type", "$");
            else if (type.equalsIgnoreCase("percentage"))
                penaltyMap.put("type", "%");
        }

        if (!amount.isEmpty())
            penaltyMap.put("amount", amount);

        if (!calcOf.isEmpty())
            if (calcOf.equalsIgnoreCase("first night"))
                penaltyMap.put("calcOf", "1st Night");
            else
                penaltyMap.put("calcOf", calcOf);

        if (!desc.isEmpty())
            penaltyMap.put("desc", desc);

        if (!state.isEmpty()) {
            if (state.equalsIgnoreCase("new"))
                penaltyMap.put("state", "Active");
            else
                penaltyMap.put("state", state);
        }
    }

    private void fillPenaltyData(String name, String categ, String type, String amount, String calcOf, String desc, String state) {
        if (!name.isEmpty()) {
            penalties.penaltyNameField.clear();
            //Utils.setAttribute(js,penalties.penaltyArNameField,"value","");
            if (!name.equalsIgnoreCase("non"))
                penalties.penaltyNameField.sendKeys(name,name);
        }
        if (!categ.isEmpty()) {
            if (categ.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", penalties.categorySelectionClearButton);
            else
                penalties.categorysList_Filter().stream().filter(c -> c.getText().equalsIgnoreCase(categ)).findAny().orElseThrow().click();
        }
        if (!type.isEmpty()) {
            penalties.penaltytypesList().stream().filter(c -> c.getText().equalsIgnoreCase(type)).findAny().orElseThrow().click();
            if (type.equalsIgnoreCase("amount")) {
                asrt.assertFalse(Utils.isEnabled(penalties.calculatedofField));
                asrt.assertAll();
            }
        }


        if (!calcOf.isEmpty()) {
            if (calcOf.equalsIgnoreCase("non"))

                js.executeScript("arguments[0].click();", penalties.calcOfClearButton);
            else
                penalties.calculatedOfList_Filter().stream().filter(c -> c.getText().equalsIgnoreCase(calcOf)).findAny().orElseThrow().click();
        }
        if (!desc.isEmpty())
            penalties.descriptionField.clear();
        if (!desc.equalsIgnoreCase("non"))
            penalties.descriptionField.sendKeys(desc);


        if (!amount.isEmpty()) {
            if (amount.equalsIgnoreCase("undefined")) {
                if (!penalties.undefinedCheckBox.isSelected())
                    penalties.undefinedCheckBox.click();
                asrt.assertFalse(penalties.amountFiled.isEnabled());
                asrt.assertFalse(Utils.isEnabled(penalties.calculatedofField));
                asrt.assertAll();
            } else {
                if (penalties.undefinedCheckBox.isSelected())
                    penalties.undefinedCheckBox.click();
                penalties.amountFiled.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                if (!amount.equalsIgnoreCase("non"))
                    penalties.amountFiled.sendKeys(amount);
            }

        }

        if (!state.isEmpty()) {
            if (state.equalsIgnoreCase("new"))
                System.out.println();
            else if ((penalties.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (penalties.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
                penalties.statusSwitch.click();
        }
        setPenaltyMap(name, categ, type, amount, calcOf, desc, state);
    }

    @When("creating penalty with name {string} ctegory {string} type {string} amount {string} calculatedOF {string} Description {string}")
    public void creatingPenaltyWithNameCtegoryTypeAmountCalculatedOFDescription(String name, String categ, String
            type, String amount, String calcOF, String desc) {
        penalties.newpenaltyButton.click();
        fillPenaltyData(name, categ.replace("-", " "), type, amount, calcOF, desc, "new");
        penalties.submitButton.click();
    }

    @Then("Check msg {string} and the penalty")
    public void checkMsgAndThePenalty(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            WebElement createdPenalty = penalties.names.stream().filter(n -> n.getText().equalsIgnoreCase(penaltyMap.get("name"))).findAny().orElseThrow();
            asrt.AssertEqualsIgnoreCase(penalties.penaltyAmount(createdPenalty).getText(), penaltyMap.get("amount"));
            asrt.AssertEqualsIgnoreCase(penalties.penaltyCategory(createdPenalty).getText(), penaltyMap.get("categ"));
            asrt.AssertEqualsIgnoreCase(penalties.penaltyType(createdPenalty).getText(), penaltyMap.get("type"));
            asrt.AssertEqualsIgnoreCase(penalties.penaltyCOf(createdPenalty).getText().isEmpty() ? null : penalties.penaltyCOf(createdPenalty).getText(), penaltyMap.get("calcOf"));

            if (penaltyMap.get("state").equalsIgnoreCase("active"))
                asrt.assertTrue(penalties.penaltyStatus(createdPenalty).getAttribute("xlink:href").contains("icon-check"));
            else if (penaltyMap.get("state").equalsIgnoreCase("inactive"))
                asrt.assertTrue(penalties.penaltyStatus(createdPenalty).getAttribute("xlink:href").contains("icon-minus"));
            asrt.assertAll();
        }

    }

    @When("Filtering penalties With {string} as {string}")
    public void filteringPenaltiesWithAs(String filter, String value) {
        penalties.filterButton.click();
        switch (filter) {
            case "name" -> penalties.nameFilterField.sendKeys(value);
            case "amount" -> penalties.amountFilterField.sendKeys(value);
            case "type" ->
                    penalties.filterPenaltyTypeList().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "calcOf" ->
                    penalties.calculatedOfList_Filter().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "categ" ->
                    penalties.categorysList_Filter().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "state" ->
                    penalties.statusesFilterList().stream().filter(t -> t.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
        }
        penalties.searchButton.click();

    }

    @Then("Check all visible records {string} as {string}")
    public void checkAllVisibleRecordsAs(String filter, String value) {
        wait.waitLoading();
        switch (filter.toLowerCase()) {
            case "name" -> asrt.assertFalse(penalties.names.stream().anyMatch(n -> !n.getText().contains(value)));
            case "amount" -> asrt.assertFalse(penalties.amounts.stream().anyMatch(n -> !n.getText().contains(value)));
            case "type" -> {
                switch (value.toLowerCase()) {
                    case "absolute" ->
                            asrt.assertFalse(penalties.types.stream().anyMatch(n -> !n.getText().contains("$")));
                    case "percentage" ->
                            asrt.assertFalse(penalties.types.stream().anyMatch(n -> !n.getText().contains("%")));
                }
            }

            case "calcof" -> {
                if (value.equalsIgnoreCase("First Night"))
                    asrt.assertFalse(penalties.calculatedOfs.stream().anyMatch(n -> !n.getText().contains("1st Night")));
                else
                    asrt.assertFalse(penalties.calculatedOfs.stream().anyMatch(n -> !n.getText().contains(value)));
            }

            case "categ" -> asrt.assertFalse(penalties.categories.stream().anyMatch(n -> !n.getText().contains(value)));
            case "state" -> {
                switch (value.toLowerCase()) {
                    case "active" ->
                            asrt.assertTrue(penalties.statuses.stream().anyMatch(n -> !n.getAttribute("xlink:href").contains("icon-check")));
                    case "inactive" ->
                            asrt.assertTrue(penalties.statuses.stream().anyMatch(n -> !n.getAttribute("xlink:href").contains("icon-minus")));
                }
            }
        }
        asrt.assertAll();
    }

    private WebElement extractPenalty(String penaltyName) {
        WebElement seectedPenalty = penalties.names.stream().filter(i -> i.getText().equalsIgnoreCase(penaltyName)).findAny().orElseThrow();
        String penaltyStat = "Active";
        if (penalties.penaltyStatus(seectedPenalty).getAttribute("xlink:href").contains("icon-minus"))
            penaltyStat = "Inactive";
        setPenaltyMap(penalties.penaltyName(seectedPenalty).getText(), penalties.penaltyCategory(seectedPenalty).getText(), penalties.penaltyType(seectedPenalty).getText(), penalties.penaltyAmount(seectedPenalty).getText(), penalties.penaltyCOf(seectedPenalty).getText(), "", penaltyStat);
        return seectedPenalty;
    }

    @When("editing penalty {string} name {string} ctegory {string} type {string} amount {string} calculatedOF {string} Description {string} and state {string}")
    public void editingPenaltyNameCtegoryTypeAmountCalculatedOFDescriptionAndState(String oName, String
            nName, String categ, String type, String amount, String calcOF, String desc, String state) {
        WebElement selectedPenalty = extractPenalty(oName);
        penalties.penaltyEditButton(selectedPenalty).click();
        wait.waitLoading();
        fillPenaltyData(nName, categ, type, amount, calcOF, desc, state);
        penalties.submitButton.click();
    }

    @When("deleting penalty {string}")
    public void deletingPenalty(String penName) {
        WebElement selectedPenalty = extractPenalty(penName);
        penalties.penaltyDeleteButton(selectedPenalty).click();
        penalties.popUpCOnfirmButton.click();
    }

    @Then("Check msg {string} and penalty deletion")
    public void checkMsgAndPenaltyDeletion(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            asrt.assertFalse(penalties.names.stream().anyMatch(n -> n.getText().equalsIgnoreCase(penaltyMap.get("name"))));
        }
    }
}
