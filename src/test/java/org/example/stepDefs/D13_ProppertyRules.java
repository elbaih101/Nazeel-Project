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

    @Given("go to Reservation Rules Page")
    public void goToReservationRulesPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.rulesDropList.click();
        setupPage.reservationRulesLink.click();
    }

    @Given("choose {string} view and save")
    public void chooseViewAndSave(String view) {
        new P00_multiPurposes(driver).waitLoading();
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
        WebElement swit=null;
        switch (name.toLowerCase())
        {
            case "previousdayclac"->swit=reservationRules.previousDayClacSwitch;
            case "autoextenddaily"->swit=reservationRules.autoExtendDailySwitch;
            case "autoextendformonthly"->swit= reservationRules.autoExtendForMonthlySwitch;
            case "restrictchangeunit"->swit =reservationRules.restrictChangeUnitSwitch;
            case "reasonsrequire"->swit=reservationRules.reasonsRequireSwitch;
            case "enableunconfirmed"->swit=reservationRules.enableUnconfirmedSwitch;
            case "enablemonthly"->swit=reservationRules.enableMonthlySwitch;
            case "autonoshow"->swit=reservationRules.autoNoShowSwitch;
            case "utorejectota"->swit=reservationRules.autoRejectOTASwitch;
            case "mandatorycheckintoday"->swit=reservationRules.mandatoryCheckInTodaySwitch;
            case "skipbalancepay"->swit=reservationRules.skipBalancePaySwitch;
            case "resetsequence"->swit =reservationRules.resetSequenceSwitch;

        }
        if((state.equalsIgnoreCase("on")&&swit.getAttribute("class").contains("k-switch-off"))||(state.equalsIgnoreCase("off")&&swit.getAttribute("class").contains("k-switch-on")))
            swit.click();
    }

    @And("set auto extend to be {string}")
    public void setAutoExtendToBe(String arg0) {
    }

    @Then("save and check the msg and fields after edit")
    public void saveAndCheckTheMsgAndFieldsAfterEdit() {
    }
}
