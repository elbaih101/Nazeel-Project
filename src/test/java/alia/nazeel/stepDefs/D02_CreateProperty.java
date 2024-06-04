package alia.nazeel.stepDefs;

import alia.nazeel.pages.P01_LoginPage;
import alia.nazeel.pages.P02_DashBoardPage;

import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.properties_pages.*;
import alia.nazeel.pojos.JsonDataTools;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import com.github.javafaker.Faker;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Locale;

public class D02_CreateProperty {

    final Locale locale = Locale.forLanguageTag("ar-SA");
    final Faker faker = new Faker(locale);
    final WebDriver driver = DriverManager.getDriver();
    final SoftAssert asrt = new SoftAssert();
    final Actions actions = new Actions(driver);
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P01_LoginPage loginPage = new P01_LoginPage(driver);
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPagec = new P05_SetupPage(driver);
    final P06_PropertiesPage propertiesPage = new P06_PropertiesPage(driver);
    final P06_1_PropeertyDataPage propeertyDataPage = new P06_1_PropeertyDataPage(driver);
    final P06_2_LocationDataPage locationDataPage = new P06_2_LocationDataPage(driver);
    final P06_3_OwnerDataPage ownerDataPage = new P06_3_OwnerDataPage(driver);
    final P06_4_SmsPage smsPage = new P06_4_SmsPage(driver);
    final P06_5_SubscriptionPage subscriptionPage = new P06_5_SubscriptionPage(driver);
    final P06_6_SummaryPage summaryPage = new P06_6_SummaryPage(driver);
    final String propertyName = faker.company().name();


    @Given("Logging in with end user {string} {string} {string}")
    public void loggingInWithEndUser(String username, String password, String acc) {
        Hooks.endUserLogin(driver, username, password, acc);
    }

    @Given("Logging in with superuser")
    public void loggingInWithSuperuser() {
        Hooks.superUserLogin(driver, "baih", "Aa@123456");
    }

    String createdProperty = JsonDataTools.getValueFromJsonFile("src/main/resources/testdata/property.json", "propertyName");

    @And("Select Property {string}")
    public void selectProperty(String propertyName) {

        if (propertyName.equalsIgnoreCase("created"))
            propertyName = createdProperty;

        /// find property ///
        try {
            wait.until(ExpectedConditions.visibilityOf(loginPage.propertyNameField));

            loginPage.propertyNameField.sendKeys(propertyName);
            actions.sendKeys(Keys.ENTER);
            actions.perform();
            wait.waitLoading();
            for (WebElement company :
                    loginPage.companysList) {
                if (company.getText().contains(propertyName))
                    company.click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("property not found");
        }
        wait.waitLoading();
        //close the announcement
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        try {
            dashBoardPage.closeAnnouncementButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("no announcements to close");
        }
        try {
            dashBoardPage.closeSubscriptionsAlertButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("no subscriptions alert");
        }

        //clicking on later to bypass user verification
        try {
            loginPage.verificationButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("User is verified");
        }
        try {
            dashBoardPage.contractAgreementButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("no agreement contract");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @And("Go to Properties Page")
    public void goToPropertiesPage() {


        dashBoardPage.setupPageLink.click();
        setupPagec.companyDroplist.click();

        wait.until(ExpectedConditions.visibilityOf(setupPagec.propertiesLink));
        setupPagec.propertiesLink.click();
    }


    @When("click on new propery button")
    public void clickOnNewProperyButton() {
        wait.waitLoading();
        propertiesPage.newPropertyButton.click();
    }

    @And("fill property Data")
    public void fillPropertyData() {
        wait.until(ExpectedConditions.visibilityOf(propeertyDataPage.reportNameField));
        propeertyDataPage.propertyNameField.sendKeys(propertyName);
        propeertyDataPage.propertyType().getFirst().click();
        propeertyDataPage.unitClass().getFirst().click();


        propeertyDataPage.propertyClass().getFirst().click();
        subscriptionPage.salesman().getFirst().click();
        propeertyDataPage.nextButton.click();
    }

    @And("fill Location Data")
    public void fillLocationData() {
        wait.until(ExpectedConditions.visibilityOf(locationDataPage.buildingNumberField));
        locationDataPage.country().getFirst().click();
        locationDataPage.region().getFirst().click();
        locationDataPage.city().getFirst().click();
        locationDataPage.district().getFirst().click();
        locationDataPage.street().getFirst().click();
        locationDataPage.buildingNumberField.sendKeys(String.valueOf(faker.number().numberBetween(1, 99)));
        locationDataPage.additionalNoField.sendKeys(String.valueOf(faker.number().numberBetween(1, 99)));
        locationDataPage.addressField.sendKeys(String.valueOf(faker.address()));
        locationDataPage.phoneNumberField.sendKeys(String.valueOf(faker.phoneNumber().cellPhone()));
        locationDataPage.mobileNumberField.sendKeys(String.valueOf(faker.phoneNumber().cellPhone()));
        locationDataPage.emailField.sendKeys(faker.internet().emailAddress());
        locationDataPage.postalCodeField.sendKeys(faker.address().zipCode());
        locationDataPage.nextButton.click();
    }

    @And("fill Owner Data")
    public void fillOwnerData() {
        wait.until(ExpectedConditions.visibilityOf(ownerDataPage.nextButton));
        ownerDataPage.nextButton.click();
    }

    @And("fill sms Data")
    public void fillSmsData() {
        wait.until(ExpectedConditions.visibilityOf(smsPage.smsBalanceField));
        smsPage.smsprovider().getFirst().click();
        WebElement selection = smsPage.smsSender().getFirst();
        wait.until(ExpectedConditions.elementToBeClickable(selection));
        selection.click();
        smsPage.smsBalanceField.sendKeys("80");
        smsPage.nextButton.click();
    }

    @And("Fill Subscription Data")
    public void fillSubscriptionData() {
        wait.until(ExpectedConditions.visibilityOf(subscriptionPage.startDateField));
        subscriptionPage.selecttoday();
        subscriptionPage.subscriptionPeriodField.sendKeys("12");
        subscriptionPage.depositerNameField.sendKeys(faker.name().fullName());
        subscriptionPage.startingAmountField.sendKeys("8000");
        subscriptionPage.startingAmountTaxField.sendKeys("800");
        subscriptionPage.annualRenewalPriceField.sendKeys("18");
        WebElement selection = propeertyDataPage.propertyAccount().getFirst();
        wait.until(ExpectedConditions.elementToBeClickable(selection));
        selection.click();
        propeertyDataPage.numberOfUnitsField.sendKeys("80");
        subscriptionPage.commentField.sendKeys("some comment");
        subscriptionPage.nextButton.click();
    }

    @And("Finish the Summary")
    public void finishTheSummary() {
        wait.until(ExpectedConditions.visibilityOf(summaryPage.saveButton));
        summaryPage.saveButton.click();
    }

    @Then("Check new property is created")
    public void checkNewPropertyIsCreated() {
        wait.until(ExpectedConditions.invisibilityOf(summaryPage.saveButton));
        asrt.assertTrue(propertiesPage.propertiesNames.stream().anyMatch(element -> {
            boolean contains = element.getText().contains(propertyName);
            if (contains)
                JsonDataTools.writeValueToJsonFile("src/main/resources/testdata/property.json", "propertyName", propertiesPage.propertyCode(element).getText().trim());
            return contains;
        }));

        asrt.assertAll();
    }


}
