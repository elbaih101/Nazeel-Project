package alia.nazeel.stepDefs;

import alia.nazeel.enums.Services;
import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.masterdata_pages.P14_MasterData;
import alia.nazeel.pages.masterdata_pages.P21_SubscriptionPrices;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.subscriptions.P36_1_RenewSubscription;
import alia.nazeel.pages.setuppages.subscriptions.P36_Subscriptions;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class D10_Supscriptions {

    final WebDriver driver = DriverManager.getDriver();

    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P14_MasterData masterData = new P14_MasterData(driver);
    final P21_SubscriptionPrices subscriptionPrices = new P21_SubscriptionPrices(driver);
    final P36_Subscriptions subscriptions = new P36_Subscriptions(driver);
    P36_1_RenewSubscription renewSubscriptionPage = new P36_1_RenewSubscription(driver);


    @And("go to subscriptions prices page")
    public void goToSubscriptionsPricesPage() {
        wait.waitLoading();
        dashBoardPage.masterDataLink.getFirst().click();
        masterData.servicesDropList.click();
        masterData.subscriptionsPricesLink.click();
    }

    @When("adding {string} price for {string} with subscription Period {string} and price {string}")
    public void addingPriceForWithSubscriptionPeriodAndPrice(String type, String service, String period, String price) {
        wait.waitLoading();
        subscriptionPrices.newPriceButton.click();
        subscriptionPrices.serviceComboBox.selectByTextContainsIgnoreCase(service);

        if (!service.equalsIgnoreCase(Services.Nazeel.toString())) {
            wait.waitLoading();
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
        asrt.assertEquals(subscriptionPrices.infos().getText(), """
                - System will not apply taxes for subscription orders of properties belonging to countries other than Saudi Arabia.
                - System will apply this price for all countries.
                - System will apply this price for all plans.""");
        subscriptionPrices.saveButton().click();

        asrt.assertAll();
    }

    @And("the {string} price for {string} with subscription Period {string} and price {string} and status {string} is visible in grid")
    public void thePriceForWithSubscriptionPeriodAndPriceIsVisibleInGrid(String type, String service, String period, String price, String status) {
        WebElement createdPrice = createdPrice(type, service, period);
        asrt.assertTrue(subscriptionPrices.pricePrice(createdPrice).getText().toLowerCase().contains(price.toLowerCase()), "Expected :" + price + "\n Actual" + subscriptionPrices.pricePrice(createdPrice).getText());
        asrt.assertTrue(subscriptionPrices.priceStatus(createdPrice).getText().toLowerCase().contains(status.toLowerCase()), "Expected :" + status + "\n Actual :" + subscriptionPrices.priceStatus(createdPrice).getText());
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
        wait.waitLoading();
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
            subscriptionPrices.typeFilter.selectByTextContainsIgnoreCase(type);
        }
        if (!service.isEmpty()) {
            subscriptionPrices.servicFilter.selectByTextContainsIgnoreCase(service);
        }
        if (!status.isEmpty()) {
            subscriptionPrices.statusFilter.selectByTextContainsIgnoreCase(status);
        }
        if (!period.isEmpty()) {
            subscriptionPrices.filterPeriodField.clear();
            subscriptionPrices.filterPeriodField.sendKeys(period);
        }
        subscriptionPrices.searchButton.click();
    }

    @Then("all Records Visible in grid are Type {string} and Service {string} and Period {string}  and status {string}")
    public void allRecordsVisibleInGridAreTypeAndAndPeriodAndStatus(String type, String service, String period, String status) {
        wait.waitLoading();
        if (!status.isEmpty()) {
            asrt.assertFalse(subscriptionPrices.statuses.stream().anyMatch(stat -> !stat.getText().toLowerCase().contains(status.toLowerCase())));
        }
        if (!service.isEmpty()) {
            asrt.assertFalse(subscriptionPrices.services.stream().anyMatch(ser -> !ser.getText().toLowerCase().contains(service.toLowerCase())));
        }
        if (!type.isEmpty()) {
            asrt.assertFalse(subscriptionPrices.types.stream().anyMatch(ty -> !ty.getText().toLowerCase().contains(type.toLowerCase())));
        }
        if (!period.isEmpty()) {
            asrt.assertFalse(subscriptionPrices.periods.stream().anyMatch(per -> !per.getText().contains(period)));
        }
        asrt.assertAll();

    }

    @Given("go to subscriptions Setup Page")
    public void goToSubscriptionsSetupPage() {
        wait.waitLoading();
        dashBoardPage.setupPageLink.click();
        wait.waitLoading();
        setupPage.subscriptionsLink.click();
    }

    @Given("open new Subscription Page")
    public void openNewSubscriptionPage() {
        wait.waitLoading();
        subscriptions.newSubscriptionButton.click();
    }

    @Then("Check the subscriptions periods and prices and expiry dates for the below table exist")
    public void checkTheSubscriptionsPeriodsAndPricesForTheBelowTableExist(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        wait.waitLoading();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/YYYY");
        for (Map<String, String> columns : rows) {
            WebElement selectedService = renewSubscriptionPage.selectService(columns.get("service"));
            String periodString = columns.get("period");
            Period period;
            renewSubscriptionPage.selectPeriod(selectedService, periodString);
            if (!periodString.contains("Day")) {
                asrt.assertTrue(Float.parseFloat(renewSubscriptionPage.getServicePrice(selectedService)) == Float.parseFloat(columns.get("price")));
            } else {
                asrt.assertTrue(renewSubscriptionPage.getField("Price", selectedService).isEnabled());
            }

            String currentExpiryDateString = renewSubscriptionPage.getField("Expiry Date", selectedService).getText();
            DateTime cureentExpiryDate = null;
            if (!currentExpiryDateString.isEmpty())
                cureentExpiryDate = dateTimeFormatter.parseDateTime(currentExpiryDateString);

            DateTime newExpryDate = dateTimeFormatter.parseDateTime(renewSubscriptionPage.getField("New Expiry Date", selectedService).findElement(By.xpath(".//input")).getAttribute("value"));
            Period subscriptionPEriod = renewSubscriptionPage.getSubscriptionPEriod(selectedService);

            if (cureentExpiryDate == null || cureentExpiryDate.isBeforeNow()) {
                asrt.assertTrue(newExpryDate.equals(DateTime.now().plus(subscriptionPEriod)));
            } else
                asrt.assertTrue(newExpryDate.equals(cureentExpiryDate.plus(subscriptionPEriod)));

        }
        asrt.assertAll();
    }


}
