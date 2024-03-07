package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.setuppages.P05_SetupPage;
import org.example.pages.setuppages.rules_pages.P27_ReservationRules;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D13_ProppertyRules {


    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPage = new P05_SetupPage(driver);
    P27_ReservationRules reservationRules = new P27_ReservationRules(driver);
    String sucMsg = "Saved Successfully";

    @Given("go to Reservation Rules Page")
    public void goToReservationRulesPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.rulesDropList.click();
        setupPage.reservationRulesLink.click();
        new P00_multiPurposes(driver).waitLoading();
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
        new P00_multiPurposes(driver).waitLoading();
        WebElement button = null;
        switch (view.toLowerCase()) {
            case "list" -> button = reservationRules.listViewButton;
            case "units" -> button = reservationRules.unitsViewButton;
            case "calender" -> button = reservationRules.calenderViewButton;
        }
        asrt.assertTrue(button.getAttribute("class").contains("k-state-active"));
        asrt.assertAll();
    }

    @When("set Checkin time {string} and Check out time {string} grace Period {string}")
    public void setCheckinTimeAndCheckOutTimeGracePeriod(String in, String out, String grace) {
        Utils.setTime(reservationRules.checkInTimeField, in);
        Utils.setTime(reservationRules.checkOutTimeField, out);
        reservationRules.gracePeriodsList().stream().filter(g -> g.getText().equals(grace)).findAny().get().click();
    }

    @And("switch {string} SWitch {string}")
    public void switchSWitch(String name, String state) {
        WebElement swit = getSwitch(name);
        if ((state.equalsIgnoreCase("on") && swit.getAttribute("class").contains("k-switch-off")) || (state.equalsIgnoreCase("off") && swit.getAttribute("class").contains("k-switch-on")))
            swit.click();
    }

    private WebElement getSwitch(String name) {
        WebElement swit = null;
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
            reservationRules.msg.getText().contains(msg);
        } else {
            new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        }
    }

    @And("Check the inTime {string} and out time {string} and auto etend after {string}")
    public void checkTheInTimeAndOutTimeAndAutoEtendAfter(String in, String out, String auEx) {
        asrt.assertTrue(reservationRules.checkInTimeField.getAttribute("value").equalsIgnoreCase(in));
        asrt.assertTrue(reservationRules.checkOutTimeField.getAttribute("value").equalsIgnoreCase(out));
        asrt.assertTrue(reservationRules.autoExtendAfterTimeField.getAttribute("value").equalsIgnoreCase(auEx));
        asrt.assertAll();


    }

    @Then("save and check switch {string} is {string}")
    public void saveAndCheckSwitchIs(String name, String state) {
        reservationRules.submitButton.click();
        new D03_BlocksAndFloors().checkToastMesageContainsText(sucMsg);
        new P00_multiPurposes(driver).waitLoading();
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
        reservationRules.unitAllownces().stream().filter(al -> al.getText().equals(allowance)).findAny().get().click();
        reservationRules.submitButton.click();
    }

    @Then("Check the period to be {string}")
    public void checkThePeriodToBe(String allowance) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(sucMsg);
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertTrue(reservationRules.unitAllowanceDropList.getText().equals(allowance));
        asrt.assertAll();
    }

    @When("changing the in time to {string} the auto no show time to {string} and reasons {string}")
    public void changingTheAutoNoShowTimeToAndReasons(String in, String noShow, String reas) {
        Utils.setTime(reservationRules.checkInTimeField, in);
        switchSWitch("autonoshow", "on");
        Utils.setTime(reservationRules.autoNoShowTimeField, noShow);
        if (!reas.isEmpty())
            reservationRules.autoNoShowReasons().stream().filter(al -> al.getText().equals(reas)).findAny().get().click();
        reservationRules.submitButton.click();
    }

    @Then("Check the msg {string} the time to be  {string} and reasons {string}")
    public void checkTheMsgTheTimeToBeAndReasons(String msg, String noShTime, String reas) {
        if (!msg.contains(sucMsg)) {
            reservationRules.msg.getText().contains(msg);
        } else {
            new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        }
        asrt.assertTrue(reservationRules.autoNoShowTimeField.getAttribute("value").equalsIgnoreCase(noShTime), "Expected: " + noShTime + "\nActual: " + reservationRules.autoNoShowTimeField.getAttribute("value"));
        asrt.assertTrue(reservationRules.noShowReasonFiled.getAttribute("value").contains(reas), "reasons not same");
        asrt.assertAll();

    }

    @When("changing the auto cancel reason to {string}")
    public void changingTheAutoCancelReasonTo(String reas) {
        switchSWitch("autorejectota", "on");
        reservationRules.cancelREasonsList().stream().filter(re -> re.getText().equals(reas)).findAny().get().click();
        reservationRules.submitButton.click();
    }

    @And("cancel reasons are {string}")
    public void cancelReasonsAre(String reas) {
        asrt.assertTrue(reservationRules.cancelReasonFiled.getAttribute("value").equals(reas));
        asrt.assertAll();
    }
}
