package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.customers.P22_Corporates;
import alia.nazeel.pages.mutlipurposes.P00_3_CorporateSelectionPopUp;
import alia.nazeel.tools.CustomAssert;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import com.github.javafaker.Faker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class D11_1_Corporates {

    final WebDriver driver = DriverManager.getDriver();


    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P22_Corporates corporates = new P22_Corporates(driver);
    final P00_3_CorporateSelectionPopUp selectCorpPopup = new P00_3_CorporateSelectionPopUp(driver);


    @And("go to corporates page")
    public void goToCorporatesPage() {
        wait.waitLoading();
        dashBoardPage.customersDropList.click();
        wait.waitLoading();
        dashBoardPage.corporatesLink.click();
    }

    @When("Checking the validation over the required Fields\\(name, Country ,VAT,first and second building numbers,) in corporate Creation")
    public void checkingTheValidationOverTheRequiredFieldsNameCountryVATFirstAndSecondBuildingNumbersInCorporateCreation(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            creatingACorporateWithNameAndCountryWithout(columns.get("name"), columns.get("country"), columns.get("empty"), columns.get("vat"), columns.get("bNum"), columns.get("secBNum"), columns.get("invalid"));
            assertToastMessageAndValidityThenClearFields(columns.get("msg"));
        }
    }

    @Then("assert Validity of Fields")
    public void assertValidityOfFields() {
        asrt.assertAll();
    }

    private void assertToastMessageAndValidityThenClearFields(String message) {
        asrt.AssertToastMessagesContains(message);
        clearCorporateData();
    }

    private void clearCorporateData() {
        corporates.corpoateNameField.clear();
        corporates.countriesComboBox.clearSelection();
        corporates.vatField.clear();
        corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
        corporates.countryDialCodeComboBox.clearSelection();
        corporates.cRNumberField.clear();
        corporates.cityField.clear();
        corporates.districtField.clear();
        corporates.streetField.clear();
        corporates.bNoField.clear();
        corporates.secNoField.clear();
        corporates.addressField.clear();
        corporates.emaiField.clear();
        corporates.corPhoneField.clear();
        corporates.cPersonNameFied.clear();
        corporates.cPersonPhoneFied.clear();
    }

    int newButtonClicks = 0;

    @When("Creating a Corporate with name {string} and Country {string} ignoredFields {string} vat {string} bNumber {string} secBNumber {string} invalid {string}")
    public void creatingACorporateWithNameAndCountryWithout(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb, String invalidFields) {
        wait.waitLoading();
        if (newButtonClicks == 0) {
            corporates.newCorporateButton.click();
            newButtonClicks++;
        }
        fillCorporatedata(name, country, ignoredFields, vat, bNumb, secBNumb, invalidFields);

        try {
            wait.waitLoading();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            selectCorpPopup.saveButton.click();

        } catch (NoSuchElementException e) {
            wait.waitLoading();
            corporates.saveButton.click();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    private void fillCorporatedata(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb, String invalidFields) {
        if (!Utils.isEmptyOrNull(name)) {
            corporates.corpoateNameField.clear();
            if (!name.equalsIgnoreCase("non"))
                corporates.corpoateNameField.sendKeys(name);
        }
        if (!Utils.isEmptyOrNull(country)) {
            corporates.countriesComboBox.clearSelection();
            if (!country.equalsIgnoreCase("non"))
                corporates.countriesComboBox.selectByTextContainsIgnoreCase(country.toLowerCase());

        }
        if (!Utils.isEmptyOrNull(vat)) {
            corporates.vatField.clear();
            if (!vat.equalsIgnoreCase("non"))
                corporates.vatField.sendKeys(vat);
        }
        if (!Utils.isEmptyOrNull(bNumb)) {
            corporates.bNoField.clear();
            if (!bNumb.equalsIgnoreCase("non"))
                corporates.bNoField.sendKeys(bNumb);
        }
        if (!Utils.isEmptyOrNull(secBNumb)) {
            corporates.secNoField.clear();
            if (!secBNumb.equalsIgnoreCase("non"))
                corporates.secNoField.sendKeys(secBNumb);
        }
        emptyFeildsMethod(ignoredFields);
        invalidCorporateFieldsMethod(invalidFields);
    }

    private void invalidCorporateFieldsMethod(String invalidFields) {
        if ((!Utils.isEmptyOrNull(invalidFields) && !invalidFields.toLowerCase().contains("all"))) {

            if (invalidFields.toLowerCase().contains("postalcode")) {
                corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
                corporates.postalCodeField.sendKeys("6548");
            }
            if (invalidFields.toLowerCase().contains("email")) {
                corporates.emaiField.clear();
                corporates.emaiField.sendKeys("123");
            }
            if (invalidFields.toLowerCase().contains("cpersonnumber")) {
                corporates.countryDialCodeComboBox.selectByTextContainsIgnoreCase("966");
                corporates.cPersonPhoneFied.clear();
                corporates.cPersonPhoneFied.sendKeys(Faker.instance().number().digits(2));
            }
        }
    }

    private void emptyFeildsMethod(String emptyFields) {
        if ((!Utils.isEmptyOrNull(emptyFields) && !emptyFields.toLowerCase().contains("all"))) {

            if (!emptyFields.toLowerCase().contains("postalcode")) {
                corporates.postalCodeField.clear();
                corporates.postalCodeField.sendKeys("65487");
            } else {
                corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
            }
            if (!emptyFields.toLowerCase().contains("crnumber")) {
                corporates.cRNumberField.clear();
                corporates.cRNumberField.sendKeys("987654");
            } else {
                corporates.cRNumberField.clear();
            }
            if (!emptyFields.toLowerCase().contains("city")) {
                corporates.cityField.clear();
                corporates.cityField.sendKeys(Faker.instance().address().city(), Faker.instance(Locale.forLanguageTag("ar-SA")).address().city());
            } else {
                corporates.cityField.clear();
            }
            if (!emptyFields.toLowerCase().contains("district")) {
                corporates.districtField.clear();
                corporates.districtField.sendKeys(Faker.instance().address().state(), Faker.instance(Locale.forLanguageTag("ar-SA")).address().state());
            } else {
                corporates.districtField.clear();
            }
            if (!emptyFields.toLowerCase().contains("street")) {
                corporates.streetField.clearFirstLangField();
                corporates.streetField.sendKeys(Faker.instance().address().streetName(), Faker.instance(Locale.forLanguageTag("ar-SA")).address().streetName());
            } else {
                corporates.streetField.clear();
            }
            if (!emptyFields.toLowerCase().contains("address")) {
                corporates.addressField.clear();
                corporates.addressField.sendKeys(Faker.instance().address().fullAddress());
            } else {
                corporates.addressField.clear();
            }
            if (!emptyFields.toLowerCase().contains("email")) {
                corporates.emaiField.clear();
                corporates.emaiField.sendKeys(Faker.instance(Locale.ENGLISH).internet().emailAddress());
            } else {
                corporates.emaiField.clear();
            }
            if (!emptyFields.toLowerCase().contains("phone")) {
                corporates.corPhoneField.clear();
                corporates.corPhoneField.sendKeys(Faker.instance().number().digits(9));
            } else {
                corporates.corPhones.clear();
            }
            if (!emptyFields.toLowerCase().contains("cperson")) {
                corporates.cPersonNameFied.clear();
                corporates.cPersonNameFied.sendKeys(Faker.instance().funnyName().name());
            } else {
                corporates.cPersonNameFied.clear();
            }
            if (!emptyFields.toLowerCase().contains("coersonnumber")) {
                corporates.cPersonPhoneFied.clear();
                corporates.cPersonPhoneFied.sendKeys(Faker.instance().number().digits(9));
            } else {
                corporates.cPersonPhoneFied.clear();
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
        wait.waitLoading();
        corporates.corporateEditButton(corporates.corporates.stream().filter(cor -> cor.getText().contains(name)).findFirst().orElseThrow()).click();


    }

    @When("Checking the validation over the required Fields\\(name, Country ,VAT,first and second building numbers,) in corporate Edit")
    public void checkingTheValidationOverTheRequiredFieldsNameCountryVATFirstAndSecondBuildingNumbersInCorporateEdit(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            editCorporateNameAndCountryIgnoredFieldsVatBNumberSecBNumber(columns.get("name"), columns.get("country"), columns.get("empty"), columns.get("vat"), columns.get("bNum"), columns.get("secBNum"));
            assertToastMessageAndValidityThenClearFields(columns.get("msg"));
        }
    }

    @And("Edit Corporate name {string} and Country {string} ignoredFields {string} vat {string} bNumber {string} secBNumber {string}")
    public void editCorporateNameAndCountryIgnoredFieldsVatBNumberSecBNumber(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb) {
        wait.waitLoading();
        fillCorporatedata(name, country, ignoredFields, vat, bNumb, secBNumb, "");
        wait.waitLoading();
        corporates.saveButton.click();
    }

    @Given("delete corporate {string}")
    public void deleteCorporate(String name) {
        wait.waitLoading();
        corporates.deleteButton(corporates.corporates.stream().filter(cor -> cor.getText().contains(name)).findFirst().orElseThrow()).click();
        driver.findElement(By.xpath("//div[@role=\"dialog\"]//button[contains(@class,\"n-button--danger\")][2]")).click();
    }


    public String selectCorporate(String corporateName, String corpPhone, String corpEmail, String corpVAT) {
        WebElement criteriaButton;
        String searchText;
        if (corporateName.equalsIgnoreCase("RANDOM")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("name")).findAny().orElseThrow();
            searchText = "";
        } else if (corpPhone.equalsIgnoreCase("") && corpEmail.equalsIgnoreCase("") && corpVAT.equalsIgnoreCase("")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("name")).findAny().orElseThrow();
            searchText = corporateName;
        } else if (corporateName.equalsIgnoreCase("") && corpEmail.equalsIgnoreCase("") && corpVAT.equalsIgnoreCase("")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("phone")).findAny().orElseThrow();
            searchText = corpPhone;
        } else if (corporateName.equalsIgnoreCase("") && corpPhone.equalsIgnoreCase("") && corpVAT.equalsIgnoreCase("")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("email")).findAny().orElseThrow();
            searchText = corpEmail;
        } else {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("vat no.")).findAny().orElseThrow();
            searchText = corpEmail;
        }
        wait.until(ExpectedConditions.elementToBeClickable(criteriaButton));

        criteriaButton.click();
        selectCorpPopup.searchFiled.sendKeys(searchText);
        selectCorpPopup.searchButton.click();

        List<WebElement> corpNames = selectCorpPopup.corporatesNAmes;
        WebElement selectedCorp = corpNames.get(new Random().nextInt(corpNames.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedCorp));
        String corpName = selectedCorp.getText();
        selectedCorp.click();
        return corpName;

    }

}
