package org.example.stepDefs;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P22_Corporates;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D11_CorporatesSetup {
    WebDriver driver = Hooks.driver;


    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P22_Corporates corporates = new P22_Corporates(driver);


    @And("go to corporates page")
    public void goToCorporatesPage() {
        new P00_multiPurposes(driver).waitLoading();
        dashBoardPage.customersDropList.click();
        dashBoardPage.corporatesLink.click();
    }

    @When("Creating a Corporate with name {string} and Country {string} ignoredFields {string} vat {string} bNumber {string} secBNumber {string}")
    public void creatingACorporateWithNameAndCountryWithout(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb) {
        new P00_multiPurposes(driver).waitLoading();
        corporates.newCorporateButton.click();
        corporatedata(name, country, ignoredFields, vat, bNumb, secBNumb);

        try {
            wait.withTimeout(Duration.ofMillis(500));
            corporates.popupSaveButton.click();

        } catch (NoSuchElementException e) {
            wait.withTimeout(Duration.ofSeconds(10));
            corporates.saveButton.click();
        }


    }

    private void corporatedata(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb) {
        if (!name.isEmpty())
        {corporates.corpoateNameField.clear();
        corporates.corpoateNameField.sendKeys(name);}
        if (!country.isEmpty()) {
            corporates.countries().stream().filter(c -> c.getText().toLowerCase().contains(country.toLowerCase())).findFirst().orElseThrow().click();
        }
        if (!vat.isEmpty()) {
            corporates.vatField.clear();
            corporates.vatField.sendKeys(vat);
        }
        if (!ignoredFields.toLowerCase().contains("all")) {
            if (!ignoredFields.toLowerCase().contains("postalcode")) {
                corporates.postalCodeField.clear();
                corporates.postalCodeField.sendKeys("65487");
            } else {
                corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE)));
            }
            if (!ignoredFields.equalsIgnoreCase("cRNumber")) {
                corporates.cRNumberField.clear();
                corporates.cRNumberField.sendKeys("987654");
            }
            if (!ignoredFields.equalsIgnoreCase("city")) {
                corporates.cityField.clear();
                corporates.cityField.click();
                corporates.cityField.sendKeys(Faker.instance().address().city());
            } else {
                corporates.cityField.sendKeys((Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE)));

            }
            if (!ignoredFields.equalsIgnoreCase("district")) {
                corporates.districtField.clear();
                corporates.districtField.sendKeys(Faker.instance().address().state());
            } else {
                corporates.districtField.sendKeys((Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE)));
            }
            if (!ignoredFields.equalsIgnoreCase("street")) {
                corporates.streetField.clear();
                corporates.streetField.sendKeys(Faker.instance().address().streetName());
            } else {
                corporates.streetField.sendKeys((Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE)));
            }
            if (!ignoredFields.equalsIgnoreCase("bNumber")) {
                corporates.bNoField.clear();
                corporates.bNoField.sendKeys(bNumb);
            }
            if (!ignoredFields.equalsIgnoreCase("secNumber")) {
                corporates.secNoField.clear();
                corporates.secNoField.sendKeys(secBNumb);
            }
            if (!ignoredFields.equalsIgnoreCase("address")) {
                corporates.addressField.clear();
                corporates.addressField.sendKeys(Faker.instance().address().fullAddress());
            }
            if (!ignoredFields.equalsIgnoreCase("email")) {
                corporates.emaiField.clear();
                corporates.emaiField.sendKeys(Faker.instance().internet().emailAddress());
            }
            if (!ignoredFields.equalsIgnoreCase("phone")) {
                corporates.corPhoneField.clear();
                corporates.corPhoneField.sendKeys(Faker.instance().number().digits(9));
            }
            if (!ignoredFields.equalsIgnoreCase("cPerson")) {
                corporates.cPersonNameFied.clear();
                corporates.cPersonNameFied.sendKeys(Faker.instance().funnyName().name());
            }
            if (!ignoredFields.equalsIgnoreCase("cPersonNumber")) {
                corporates.cPersonPhoneFied.clear();
                corporates.cPersonPhoneFied.sendKeys(Faker.instance().number().digits(9));
            }
        }
    }

    @Given("open the new Corporate Page")
    public void openTheNewCorporatePage() {
        corporates.newCorporateButton.click();
    }

    @Then("Check Zatca Logo is in Required Corporate Fields")
    public void checkZatcaLogoIsInRequiredCorporateFields() {
        asrt.assertTrue(corporates.postalCodeField.findElement(By.xpath("./ancestor-or-self::div[contains(@class,\"tax\")]")).isDisplayed());
    }

    @And("open edit mode for corporate {string}")
    public void openEditModeForCorporate(String name) {
        new P00_multiPurposes(driver).waitLoading();
        corporates.corporateEditButton(corporates.corporates.stream().filter(cor -> cor.getText().contains(name)).findFirst().orElseThrow()).click();


    }

    @And("Edit Corporate name {string} and Country {string} ignoredFields {string} vat {string} bNumber {string} secBNumber {string}")
    public void editCorporateNameAndCountryIgnoredFieldsVatBNumberSecBNumber(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb) {
        new P00_multiPurposes(driver).waitLoading();
        corporatedata(name, country, ignoredFields, vat, bNumb, secBNumb);
        new P00_multiPurposes(driver).waitLoading();
        corporates.saveButton.click();
    }

    @Given("delete corporate {string}")
    public void deleteCorporate(String name) {
        new P00_multiPurposes(driver).waitLoading();
        corporates.deleteButton(corporates.corporates.stream().filter(cor -> cor.getText().contains(name)).findFirst().orElseThrow()).click();
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//button[contains(@class,\"n-button--danger\")][2]")).click();
    }

    @And("Remove postal-code")
    public void removePostalCode() {
        new P00_multiPurposes(driver).waitLoading();

        corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE)));

        corporates.saveButton.click();
        new P00_multiPurposes(driver).waitLoading();
    }
}
