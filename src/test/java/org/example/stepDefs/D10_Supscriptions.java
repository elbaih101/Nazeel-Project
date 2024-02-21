package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.enums.Services;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.masterdata_pages.P14_MasterData;
import org.example.pages.masterdata_pages.P21_SubscriptionPrices;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class D10_Supscriptions {
    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P14_MasterData masterData = new P14_MasterData(driver);
    P21_SubscriptionPrices subscriptionPrices = new P21_SubscriptionPrices(driver);


    @And("go to subscriptions prices page")
    public void goToSubscriptionsPricesPage() {
        dashBoardPage.masterDataLink.get(0).click();
        masterData.servicesDropList.click();
        masterData.subscriptionsPricesLink.click();
    }

    @When("adding {string} price for {string} with subscription Period {string} and price {string}")
    public void addingPriceForWithSubscriptionPeriodAndPrice(String type, String service, String period, String price) {
        new P00_multiPurposes(driver).waitLoading();
        subscriptionPrices.newPriceButton.click();
        subscriptionPrices.popUpServicesList().stream().filter(serv -> serv.getText().toLowerCase().contains(service.toLowerCase())).toList().get(0).click();

        if (!service.equalsIgnoreCase(Services.Nazeel.toString())) {
            subscriptionPrices.subscriptionType(type).click();
        } else {
            asrt.assertEquals(subscriptionPrices.nazeelAnnualFactorText().getAttribute("placeholder"), "Annual renewal price	 ×");
            try {
                wait.withTimeout(Duration.ofSeconds(1));
                subscriptionPrices.subscriptionType(type).isDisplayed();
                wait.withTimeout(Duration.ofSeconds(20));
            } catch (NoSuchElementException e) {
                asrt.assertTrue(true, "nazeel can omly be renew");
            }
        }
        subscriptionPrices.subscriptionPeriodField().clear();
        subscriptionPrices.subscriptionPeriodField().sendKeys(period);
        subscriptionPrices.priceField().clear();
        subscriptionPrices.priceField().sendKeys(price);
        asrt.assertEquals(subscriptionPrices.infos().getText(), "- System will not apply taxes for subscription orders of properties belonging to countries other than Saudi Arabia.\n" +
                "- System will apply this price for all countries.\n" +
                "- System will apply this price for all plans.");
        subscriptionPrices.saveButton().click();

        asrt.assertAll();
    }

    @And("the {string} price for {string} with subscription Period {string} and price {string} and status {string} is visible in grid")
    public void thePriceForWithSubscriptionPeriodAndPriceIsVisibleInGrid(String type, String service, String period, String price, String status) {
        WebElement createdPrice = createdPrice(type, service, period);
        asrt.assertTrue(subscriptionPrices.pricePrice(createdPrice).getText().toLowerCase().contains(price.toLowerCase()), "Expected :" + price + "\n Actual" + subscriptionPrices.pricePrice(createdPrice).getText());
        asrt.assertTrue(subscriptionPrices.priceStatus(createdPrice).getText().contains(status.toLowerCase()), "Expected :" + status + "\n Actual :" + subscriptionPrices.priceStatus(createdPrice).getText());
        asrt.assertAll();
    }

    @When("delete {string} price for {string} with subscription Period {string}")
    public void deletePriceForWithSubscriptionPeriod(String type, String service, String period) {
        WebElement createdPrice = createdPrice(type, service, period);
        subscriptionPrices.deleteButton(createdPrice).click();
        asrt.assertTrue(subscriptionPrices.deletedServiceName().getText().toLowerCase().contains(service));
        asrt.assertTrue(subscriptionPrices.deletedServicePeriod().getText().contains(period));
        subscriptionPrices.redButton().click();
    }

    @When("edit {string} price for {string} with subscription Period {string} to new status {string} new period {string} and new Price {string}")
    public void editPriceForWithSubscriptionPeriodToNewStatusNewPeriodAndNewPrice(String type, String service, String period, String newStatus, String newPeriod, String newPrice) {
        WebElement createdPrice = createdPrice(type, service, period);
        subscriptionPrices.priceEditButton(createdPrice).click();
        subscriptionPrices.subscriptionPeriodField().clear();
        subscriptionPrices.subscriptionPeriodField().sendKeys(newPeriod);
        subscriptionPrices.priceField().clear();
        subscriptionPrices.priceField().sendKeys(newPrice);
        if (newStatus.toLowerCase().contains("Inactive".toLowerCase())) {
            if (!subscriptionPrices.statusButton().getAttribute("aria-checked").equalsIgnoreCase("false")) {
                subscriptionPrices.statusButton().click();
            }
        } else {
            if (!subscriptionPrices.statusButton().getAttribute("aria-checked").equalsIgnoreCase("true")) {
                subscriptionPrices.statusButton().click();
            }
        }

        subscriptionPrices.saveButton().click();


    }

    private WebElement createdPrice(String type, String service, String period) {
        new P00_multiPurposes(driver).waitLoading();
        List<WebElement> servList = subscriptionPrices.services.stream().filter(serv -> serv.getText().toLowerCase().contains(service.toLowerCase())).toList();
        WebElement createdPrice = null;
        for (WebElement serv : servList) {
            if (subscriptionPrices.pricePeriod(serv).getText().toLowerCase().contains(period.toLowerCase())) {
                if (subscriptionPrices.priceType(serv).getText().toLowerCase().contains(type)) {
                    createdPrice = serv;
                    break;
                }
            }
        }
        return createdPrice;
    }

    @Given("Filter Records With Type {string} and Service {string} and Period {string}  and status {string}")
    public void filterRecordsWithTypeAndAndPeriodAndStatus(String type, String service, String period, String status) {
        subscriptionPrices.filterButton.click();
        if (!type.isEmpty()) {
            subscriptionPrices.filterTypesList().stream().filter(t -> t.getText().toLowerCase().equalsIgnoreCase(type)).toList().get(0).click();
        }
        if (!service.isEmpty()) {
            subscriptionPrices.filterServicesList().stream().filter(t -> t.getText().toLowerCase().equalsIgnoreCase(service)).toList().get(0).click();
        }
        if (!status.isEmpty()) {
            subscriptionPrices.filterStatusesList().stream().filter(t -> t.getText().toLowerCase().equalsIgnoreCase(status)).toList().get(0).click();
        }
        if (!period.isEmpty()) {
            subscriptionPrices.filterPeriodField.clear();
            subscriptionPrices.filterPeriodField.sendKeys(period);
        }
        subscriptionPrices.searchButton.click();
    }

    @Then("all Records Visible in grid are Type {string} and Service {string} and Period {string}  and status {string}")
    public void allRecordsVisibleInGridAreTypeAndAndPeriodAndStatus(String type, String service, String period, String status) {
        new P00_multiPurposes(driver).waitLoading();
        if (!status.isEmpty()){
        asrt.assertFalse(subscriptionPrices.statuses.stream().anyMatch(stat->!stat.getText().toLowerCase().contains(status.toLowerCase())));}
        if(!service.isEmpty()){
        asrt.assertFalse(subscriptionPrices.services.stream().anyMatch(ser->!ser.getText().toLowerCase().contains(service.toLowerCase())));}
        if (!type.isEmpty()){
        asrt.assertFalse(subscriptionPrices.types.stream().anyMatch(ty->!ty.getText().toLowerCase().contains(type.toLowerCase())));}
        if (!period.isEmpty()){
        asrt.assertFalse(subscriptionPrices.periods.stream().anyMatch(per->!per.getText().contains(period)));}
        asrt.assertAll();

    }
}
