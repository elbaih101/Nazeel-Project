package alia.nazeel.stepDefs;

import alia.nazeel.tools.CustomWebDriverWait;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import alia.nazeel.pages.P02_DashBoardPage;

import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.unit_setup_pages.P09_RatesPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import org.testng.asserts.SoftAssert;

import java.time.Duration;

import java.util.Map;

public class D09_Rates {

    final WebDriver driver = DriverManager.getDriver();


    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P09_RatesPages ratesPages = new P09_RatesPages(driver);


    @Given("go to Base Rate Page")
    public void goToBaseRatePage() {
        dashBoardPage.setupPageLink.click();
        setupPage.unitsDroplist.click();
        setupPage.baseRateLink.click();

    }

    @Given("open baseRate edit mode")
    public void openBaseRateEditMode() {
        wait.waitLoading();
        ratesPages.editBaseRateButton.click();
    }

    @And("Clear fields")
    public void clearFields() {
        ratesPages.lowWeekDaysRates.forEach(WebElement::clear);
    }

    @When("clicking on save")
    public void clickingOnSave() {
        ratesPages.saveChangesButton.click();
    }

    @When("fill rates {string} with low {string} high {string} min {string} mon {string} monmin {string}")
    public void fillRatesWithLowHighMinMonMonmin(String typeName, String low, String high, String min, String mon, String monMin) {
       wait.waitLoading();
        if (typeName.equalsIgnoreCase("all")) {
            ratesPages.lowWeekDaysRates.forEach(WebElement::clear);
            ratesPages.lowWeekDaysRates.forEach(rate -> rate.sendKeys(low));
            ratesPages.highWeekDaysRates.forEach(WebElement::clear);
            ratesPages.highWeekDaysRates.forEach(rate -> rate.sendKeys(high));
            ratesPages.minimumRates.forEach(WebElement::clear);
            ratesPages.minimumRates.forEach(rate -> rate.sendKeys(min));
            if (!mon.isEmpty()){
            ratesPages.monthlyRates.forEach(WebElement::clear);
            ratesPages.monthlyRates.forEach(rate -> rate.sendKeys(mon));}
            if (!monMin.isEmpty()){
            ratesPages.minimumMonthlyRates.forEach(WebElement::clear);
            ratesPages.minimumMonthlyRates.forEach(rate -> rate.sendKeys(monMin));}
        } else {
            WebElement selectedType = ratesPages.unitTypes.stream().filter(type -> type.getText().toLowerCase().contains(typeName.toLowerCase())).toList().getFirst();
            ratesPages.lowRate(selectedType).clear();
            ratesPages.lowRate(selectedType).sendKeys(low);
            ratesPages.highRate(selectedType).clear();
            ratesPages.highRate(selectedType).sendKeys(high);
            ratesPages.minRate(selectedType).clear();
            ratesPages.minRate(selectedType).sendKeys(min);
            if (!mon.isEmpty()){
            ratesPages.monRate(selectedType).clear();
            ratesPages.monRate(selectedType).sendKeys(mon);}
            if (!monMin.isEmpty()){
            ratesPages.monMinRate(selectedType).clear();
            ratesPages.monMinRate(selectedType).sendKeys(monMin);}

        }

    }

    final Map<String, Integer> week = Map.of("sat", 1, "sun", 2, "mon", 3, "Tue", 4, "wen", 5, "thu", 6, "fri", 7);

    @When("selecting week day {string}")
    public void selectingWeekDay(String day) {
        ratesPages.highWeekDaysButton.click();
        wait.waitLoading();
        WebElement selectedDay = ratesPages.highWeekDays().get(week.get(day));
        selectedDay.click();
    }

    @Then("check weekdays after next  or before previous {string} are disabled")
    public void checkWeekdaysAfterNextOrBeforePreviousAreDisabled(String day) {
        asrt.assertTrue(ratesPages.highWeekDays().get(week.get(day) + 2).getAttribute("class").contains("k-state-disabled"));
        asrt.assertTrue(ratesPages.highWeekDays().get(week.get(day) - 2).getAttribute("class").contains("k-state-disabled"));
        asrt.assertFalse(ratesPages.highWeekDays().get(week.get(day) + 1).getAttribute("class").contains("k-state-disabled"));
        asrt.assertFalse(ratesPages.highWeekDays().get(week.get(day) - 1).getAttribute("class").contains("k-state-disabled"));
        asrt.assertAll();
    }


    @Given("open {string} Rate Page")
    public void openSeasonalRatePage(String ratePage) {
        dashBoardPage.setupPageLink.click();
        setupPage.unitsDroplist.click();
        if (ratePage.equalsIgnoreCase("seasonal")){
        setupPage.seasonalRateLink.click();}
        else if (ratePage.equalsIgnoreCase("special")){
        setupPage.specialRateLink.click();}
    }

    @Given("open new rate page")
    public void openNewRatePage() {
        wait.waitLoading();
        ratesPages.addNewRateButton.click();
    }

    @And("fill {string} rate name {string} startDate {string} endDate {string} type {string} with low {string} high {string} min {string}")
    public void fillRateNameStartDateEndDateTypeWithLowHighMinMonMonmin(String ratType,String name, String startDate, String endDate, String typeName, String low, String high, String min) {

        ratesPages.rateName.sendKeys(name);
        Utils.setDate(ratesPages.rateStartDateField, startDate);
        Utils.setDate(ratesPages.rateEndDateField, endDate);
        if (ratType.equalsIgnoreCase("seasonal")){
        fillRatesWithLowHighMinMonMonmin(typeName, low, high, min, "", "");}
        else if (ratType.equalsIgnoreCase("special")){
            fillRatesWithLowHighMinMonMonmin(typeName, low, min, min, "", "");}


    }


    @Given("open Special Rate Page")
    public void openSpecialRatePage() {
        dashBoardPage.setupPageLink.click();
        setupPage.unitsDroplist.click();
        setupPage.specialRateLink.click();
    }


}
