package org.example.stepDefs;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P01_LoginPage;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P05_SetupPage;
import org.example.pages.properties_pages.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Locale;

public class D02_CreateProperty {

    Locale locale = new Locale("SA");
    Faker faker=new Faker(locale);
   WebDriver driver =Hooks.driver;
SoftAssert asrt =new SoftAssert();
    Actions actions =new Actions(driver);
   WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(20));
    P01_LoginPage loginPage = new P01_LoginPage(driver);
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPagec=new P05_SetupPage(driver);
    P06_PropertiesPage propertiesPage =new P06_PropertiesPage(driver);
    P06_1_PropeertyDataPage propeertyDataPage= new P06_1_PropeertyDataPage(driver);
    P06_2_LocationDataPage locationDataPage =new P06_2_LocationDataPage(driver);
    P06_3_OwnerDataPage ownerDataPage =new P06_3_OwnerDataPage(driver);
    P06_4_SmsPage smsPage=new P06_4_SmsPage(driver);
    P06_5_SubscriptionPage subscriptionPage=new P06_5_SubscriptionPage(driver);
    P06_6_SummaryPage summaryPage=new P06_6_SummaryPage(driver);
    String  propertyName= faker.company().name();
    @Given("Logging in with superuser")
    public void loggingInWithSuperuser() {
        Hooks.superUserLogin("baih","Aa@123456");
    }


    @And("Select Property {string}")
    public void selectProperty(String propertyName) throws InterruptedException {
        loginPage.propertyNameField.sendKeys(propertyName);
        actions.sendKeys(Keys.ENTER);
        actions.perform();
        Thread.sleep(2000);
        for (WebElement company:
        loginPage.companysList) {
         if (company.getText().contains(propertyName))
             company.click();
        }
        //close the announcement
        wait.until(ExpectedConditions.visibilityOf(dashBoardPage.setupPageLink));
        if (dashBoardPage.closeAnnouncementButton.isDisplayed())
            wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.closeAnnouncementButton));
        Thread.sleep(2000);
        dashBoardPage.closeAnnouncementButton.click();

    }

    @And("Go to Properties Page")
    public void goToPropertiesPage() throws InterruptedException {


        dashBoardPage.setupPageLink.click();
        setupPagec.companyDroplist.click();

        wait.until(ExpectedConditions.visibilityOf(setupPagec.propertiesLink));
        setupPagec.propertiesLink.click();
    }


    @When("click on new propery button")
    public void clickOnNewProperyButton() {
        propertiesPage.newPropertyButton.click();
    }

    @And("fill property Data")
    public void fillPropertyData() {
        wait.until(ExpectedConditions.visibilityOf(propeertyDataPage.reportNameField));
        propeertyDataPage.propertyNameField.sendKeys(propertyName);
        propeertyDataPage.propertyType().get(0).click();
        propeertyDataPage.unitClass().get(0).click();
        WebElement selection=propeertyDataPage.propertyAccount().get(0);
        wait.until(ExpectedConditions.elementToBeClickable(selection));
        selection.click();
        propeertyDataPage.numberOfUnitsField.sendKeys("80");
        propeertyDataPage.propertyClass().get(0).click();
        propeertyDataPage.nextButton.click();
    }

    @And("fill Location Data")
    public void fillLocationData() {
        wait.until(ExpectedConditions.visibilityOf(locationDataPage.buildingNumberField));
        locationDataPage.country().get(0).click();
        locationDataPage.region().get(0).click();
        locationDataPage.city().get(0).click();
        locationDataPage.district().get(0).click();
        locationDataPage.street().get(0).click();
        locationDataPage.buildingNumberField.sendKeys(String.valueOf(faker.number().numberBetween(1,99)));
        locationDataPage.additionalNoField.sendKeys(String.valueOf(faker.number().numberBetween(1,99)));
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
        smsPage.smsprovider().get(0).click();
        WebElement selection =smsPage.smsSender().get(0);
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
        subscriptionPage.salesman().get(0).click();
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

        for (WebElement property:propertiesPage.propertiesNames) {
                asrt.assertTrue(property.getText().contains(propertyName));
        }
        asrt.assertAll();
    }

    }
