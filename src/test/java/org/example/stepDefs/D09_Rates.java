package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.setuppages.P05_SetupPage;
import org.example.pages.setuppages.unit_setup_pages.P09_RatesPages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class D09_Rates {
    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPage = new P05_SetupPage(driver);
    P09_RatesPages ratesPages = new P09_RatesPages(driver);


    @Given("go to Base Rate Page")
    public void goToBaseRatePage() {
        dashBoardPage.setupPageLink.click();
        setupPage.unitsDroplist.click();
        setupPage.baseRateLink.click();

    }

    @Given("open baseRate edit mode")
    public void openBaseRateEditMode() {
        new P00_multiPurposes(driver).waitLoading();
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
            WebElement selectedType = ratesPages.unitTypes.stream().filter(type -> type.getText().toLowerCase().contains(typeName.toLowerCase())).toList().get(0);
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

    Map<String, Integer> week = Map.of("sat", 1, "sun", 2, "mon", 3, "Tue", 4, "wen", 5, "thu", 6, "fri", 7);

    @When("selecting week day {string}")
    public void selectingWeekDay(String day) {
        ratesPages.highWeekDaysButton.click();
        WebElement selectedDay = ratesPages.highWeekDays().get(week.get(day));
        wait.until(ExpectedConditions.elementToBeClickable(selectedDay));
        selectedDay.click();
    }

    @Then("check weekdays after next  or before previous {string} are disabled")
    public void checkWeekdaysAfterNextOrBeforePreviousAreDisabled(String day) {
        asrt.assertTrue(ratesPages.highWeekDays().get(week.get(day) + 2).getAttribute("class").contains("k-state-disabled"));
        asrt.assertTrue(ratesPages.highWeekDays().get(week.get(day) - 2).getAttribute("class").contains("k-state-disabled"));
        asrt.assertTrue(!ratesPages.highWeekDays().get(week.get(day) + 1).getAttribute("class").contains("k-state-disabled"));
        asrt.assertTrue(!ratesPages.highWeekDays().get(week.get(day) - 1).getAttribute("class").contains("k-state-disabled"));
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
        new P00_multiPurposes(driver).waitLoading();
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
