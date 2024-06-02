package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.customers.P22_Corporates;
import alia.nazeel.pages.customers.P34_Vendors;
import alia.nazeel.pages.customers.P35_Guests;
import alia.nazeel.pages.mutlipurposes.P00_3_CorporateSelectionPopUp;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class D11_1_Corporates {

    final WebDriver driver = DriverManager.getDriver();


    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P22_Corporates corporates = new P22_Corporates(driver);
    final P34_Vendors vendors = new P34_Vendors(driver);
    final P35_Guests guests = new P35_Guests(driver);
    final P00_3_CorporateSelectionPopUp selectCorpPopup = new P00_3_CorporateSelectionPopUp(driver);


    @And("go to corporates page")
    public void goToCorporatesPage() {
        wait.waitLoading();
        dashBoardPage.customersDropList.click();
        wait.waitLoading();
        dashBoardPage.corporatesLink.click();
    }

    @When("Creating a Corporate with name {string} and Country {string} ignoredFields {string} vat {string} bNumber {string} secBNumber {string} invalid {string}")
    public void creatingACorporateWithNameAndCountryWithout(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb, String invalidFields) {
        wait.waitLoading();
        corporates.newCorporateButton.click();
        fillCorporatedata(name, country, ignoredFields, vat, bNumb, secBNumb, invalidFields);

        try {
            wait.waitLoading();
            selectCorpPopup.saveButton.click();

        } catch (NoSuchElementException e) {
            wait.waitLoading();
            corporates.saveButton.click();
        }


    }

    private void fillCorporatedata(String name, String country, String ignoredFields, String vat, String bNumb, String secBNumb, String invalidFields) {
        if (!name.isEmpty()) {
            corporates.corpoateNameField.clear();
            corporates.corpoateNameField.sendKeys(name);
        }
        if (!country.isEmpty()) {
            corporates.countriesComboBox.selectByTextContainsIgnoreCase(country.toLowerCase());
        }
        if (!vat.isEmpty()) {
            corporates.vatField.clear();
            corporates.vatField.sendKeys(vat);
        }
        ignoredFeildsMethod(ignoredFields, bNumb, secBNumb);
        invalidCorporateFieldsMethod(invalidFields);
    }

    private void invalidCorporateFieldsMethod(String invalidFields) {
        if (!invalidFields.toLowerCase().contains("all")) {
            if (invalidFields.toLowerCase().contains("postalcode")) {
                corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
                corporates.postalCodeField.sendKeys("6548");
            }
            if (invalidFields.toLowerCase().contains("email")) {
                corporates.emaiField.clear();
                corporates.emaiField.sendKeys("123");
            }
            if (invalidFields.toLowerCase().contains("coersonnumber")) {
                corporates.cPersonPhoneFied.clear();
                corporates.cPersonPhoneFied.sendKeys(Faker.instance().number().digits(2));
            }
        }
    }

    private void ignoredFeildsMethod(String ignoredFields, String bNumb, String secBNumb) {
        if (!ignoredFields.toLowerCase().contains("all")) {
            if (!ignoredFields.toLowerCase().contains("postalcode")) {
                corporates.postalCodeField.clear();
                corporates.postalCodeField.sendKeys("65487");
            } else {
                corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
            }
            if (!ignoredFields.toLowerCase().contains("crnumber")) {
                corporates.cRNumberField.clear();
                corporates.cRNumberField.sendKeys("987654");
            }
            if (!ignoredFields.toLowerCase().contains("city")) {
                corporates.cityFieldEn.clear();
                corporates.cityFieldEn.click();
                corporates.cityFieldEn.sendKeys(Faker.instance().address().city());
            } else {
                corporates.cityFieldEn.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
                corporates.arField(corporates.cityFieldEn).sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
            }
            if (!ignoredFields.toLowerCase().contains("district")) {
                corporates.districtFieldEn.clear();
                corporates.districtFieldEn.sendKeys(Faker.instance().address().state());
            } else {
                corporates.districtFieldEn.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
                corporates.arField(corporates.districtFieldEn).sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
            }
            if (!ignoredFields.toLowerCase().contains("street")) {
                corporates.streetFieldEn.clearFirstLangField();
                corporates.streetFieldEn.sendKEysFirstLangField(Faker.instance().address().streetName());
            } else {
                corporates.streetFieldEn.clear();
            }
            if (!ignoredFields.toLowerCase().contains("bnumber")) {
                corporates.bNoField.clear();
                corporates.bNoField.sendKeys(bNumb);
            }
            if (!ignoredFields.toLowerCase().contains("secnumber")) {
                corporates.secNoField.clear();
                corporates.secNoField.sendKeys(secBNumb);
            }
            if (!ignoredFields.toLowerCase().contains("address")) {
                corporates.addressField.clear();
                corporates.addressField.sendKeys(Faker.instance().address().fullAddress());
            }
            if (!ignoredFields.toLowerCase().contains("email")) {
                corporates.emaiField.clear();
                corporates.emaiField.sendKeys(Faker.instance().internet().emailAddress());
            }
            if (!ignoredFields.toLowerCase().contains("phone")) {
                corporates.corPhoneField.clear();
                corporates.corPhoneField.sendKeys(Faker.instance().number().digits(9));
            }
            if (!ignoredFields.toLowerCase().contains("cperson")) {
                corporates.cPersonNameFied.clear();
                corporates.cPersonNameFied.sendKeys(Faker.instance().funnyName().name());
            }
            if (!ignoredFields.toLowerCase().contains("coersonnumber")) {
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
        wait.waitLoading();
        corporates.corporateEditButton(corporates.corporates.stream().filter(cor -> cor.getText().contains(name)).findFirst().orElseThrow()).click();


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

    @And("Remove postal-code")
    public void removePostalCode() {
        wait.waitLoading();

        corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));

        corporates.saveButton.click();
        wait.waitLoading();
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
