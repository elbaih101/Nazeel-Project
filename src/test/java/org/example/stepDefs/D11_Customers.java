package org.example.stepDefs;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.customers.P22_Corporates;
import org.example.pages.customers.P34_Vendors;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.vouchersPages.P10_VouchersPage;
import org.example.pages.vouchersPages.P16_VouchersPopUp;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;

public class D11_Customers {
    WebDriver driver = Hooks.driver;


    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P22_Corporates corporates = new P22_Corporates(driver);
    P34_Vendors vendors = new P34_Vendors(driver);


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
        if (!name.isEmpty()) {
            corporates.corpoateNameField.clear();
            corporates.corpoateNameField.sendKeys(name);
        }
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
                corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
            }
            if (!ignoredFields.equalsIgnoreCase("cRNumber")) {
                corporates.cRNumberField.clear();
                corporates.cRNumberField.sendKeys("987654");
            }
            if (!ignoredFields.equalsIgnoreCase("city")) {
                corporates.cityFieldEn.clear();
                corporates.cityFieldEn.click();
                corporates.cityFieldEn.sendKeys(Faker.instance().address().city());
            } else {
                corporates.cityFieldEn.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
                corporates.arField(corporates.cityFieldEn).sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
            }
            if (!ignoredFields.equalsIgnoreCase("district")) {
                corporates.districtFieldEn.clear();
                corporates.districtFieldEn.sendKeys(Faker.instance().address().state());
            } else {
                corporates.districtFieldEn.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
                corporates.arField(corporates.districtFieldEn).sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
            }
            if (!ignoredFields.equalsIgnoreCase("street")) {
                corporates.streetFieldEn.clear();
                corporates.streetFieldEn.sendKeys(Faker.instance().address().streetName());
            } else {
                corporates.streetFieldEn.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
                corporates.arField(corporates.streetFieldEn).sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));
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

        corporates.postalCodeField.sendKeys((Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE)));

        corporates.saveButton.click();
        new P00_multiPurposes(driver).waitLoading();
    }

    @And("go to Vendors page")
    public void goToVendorsPage() {
        new P00_multiPurposes(driver).waitLoading();
        dashBoardPage.customersDropList.click();
        dashBoardPage.vendorssLink.click();
    }

    HashMap<String, String> vendorMap = new HashMap<>();

    private void setVendorMap(String name, String phone, String email, String vat, String cR, String pCode, String desc, String address, String state) {
        if (!phone.isEmpty())
            vendorMap.put("phone", phone);
        if (!email.isEmpty())
            vendorMap.put("email", email);
        if (!vat.isEmpty())
            vendorMap.put("vat", vat);
        if (!name.isEmpty())
            vendorMap.put("name", name);
        if (!desc.isEmpty())
            vendorMap.put("desc", desc);
        if (!state.isEmpty())
            vendorMap.put("state", state);
        if (!cR.isEmpty())
            vendorMap.put("cR", cR);
        if (!pCode.isEmpty())
            vendorMap.put("pCode", pCode);
        if (!address.isEmpty())
            vendorMap.put("address", address);
        //outletMap.putAll(Map.of("outlet", outlet, "ntmp", ntmp, "name", name, "desc", desc, "state", state));
    }

    private void fillVendorData(String name, String phone, String email, String vat, String cR, String pCode, String desc, String address, String state) {
        if (!phone.isEmpty()) {
            if (phone.equalsIgnoreCase("non")) {
                vendors.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                vendors.dialcodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            } else if (phone.equalsIgnoreCase("dialonly")) {
                vendors.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                vendors.dialCodes().getFirst().click();
            } else if (phone.equalsIgnoreCase("nodial")) {
                vendors.dialcodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                vendors.mobileField.sendKeys("336987");
            } else {
                vendors.dialCodes().getFirst().click();
                vendors.mobileField.sendKeys(phone);
            }
        }

        if (!email.isEmpty()) {
            vendors.emailField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!email.equalsIgnoreCase("non"))
                vendors.emailField.sendKeys(email);
        }
        if (!vat.isEmpty()) {
            vendors.vATField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!vat.equalsIgnoreCase("non"))
                vendors.vATField.sendKeys(vat);
        }
        if (!name.isEmpty()) {
            vendors.vendorNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!name.equalsIgnoreCase("non"))
                vendors.vendorNameField.sendKeys(name);
        }
        if (!cR.isEmpty()) {
            vendors.cRNoField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (cR.equalsIgnoreCase("non"))
                vendors.cRNoField.sendKeys(cR);
        }
        if (!pCode.isEmpty()) {
            vendors.postalCodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (pCode.equalsIgnoreCase("non"))
                vendors.postalCodeField.sendKeys(cR);


        }
        if (!desc.isEmpty()) {
            vendors.descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!desc.equalsIgnoreCase("non"))
                vendors.descriptionField.sendKeys(desc);
        }
        if (!address.isEmpty()) {
            vendors.addressField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!desc.equalsIgnoreCase("non"))
                vendors.addressField.sendKeys(address);
        }


        setVendorMap(name, phone, email, vat, cR, pCode, desc, address, state);
        if (state.equalsIgnoreCase("new"))
            vendorMap.put("state", "Active");
        else if ((vendors.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (vendors.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            vendors.statusSwitch.click();
    }

    @When("create vendor name {string} phone {string} email {string} VAT {string} CRNo {string} PostalCode {string} description {string} address {string}")
    public void createVendorNamePhoneEmailVATCRNoPostalCodeDescriptionAddress(String name, String phone, String email, String vat, String cR, String pCode, String desc, String address) {
        vendors.newVendorButton.click();
        fillVendorData(name, phone, email, vat, cR, pCode, desc, address, "new");
        vendors.submitButton.click();

    }

    @Then("Check msg {string} and vendor is {string}")
    public void checkMsgAndVendorInTheGrid(String msg, String existance) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("successfully")) {
            if (existance.equalsIgnoreCase("added")) {
                new P00_multiPurposes(driver).waitLoading();
                WebElement selectedVendor = vendors.vendorsNames.stream().filter(c -> c.getText().equalsIgnoreCase(vendorMap.get("name"))).findAny().orElseThrow();
                asrt.assertTrue(vendors.vendorPhone(selectedVendor).getText().contains(vendorMap.get("phone")), "Expected: " + vendorMap.get("phone") + " ,Actual: " + vendors.vendorPhone(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorEmail(selectedVendor).getText().equalsIgnoreCase(vendorMap.get("email")), "Expected: " + vendorMap.get("email") + " ,Actual: " + vendors.vendorEmail(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorVAT(selectedVendor).getText().equalsIgnoreCase(vendorMap.get("vat")), "Expected: " + vendorMap.get("vat") + " ,Actual: " + vendors.vendorVAT(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorCRNo(selectedVendor).getText().equalsIgnoreCase(vendorMap.get("cR")), "Expected: " + vendorMap.get("cR") + " ,Actual: " + vendors.vendorCRNo(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorStatus(selectedVendor).getText().equalsIgnoreCase(vendorMap.get("state")), "Expected: " + vendorMap.get("state") + " ,Actual: " + vendors.vendorStatus(selectedVendor).getText());
                new D06_DigitalPayment().goToDesiredVouchersPage("Expenses");
                new P00_multiPurposes(driver).waitLoading();
                new P10_VouchersPage(driver).newVoucherButton.click();
                if (vendorMap.get("state").equalsIgnoreCase("active"))
                    asrt.assertTrue(new P16_VouchersPopUp(driver).vendorsList().stream().anyMatch(v -> v.getText().equalsIgnoreCase(vendorMap.get("name"))));
                else
                    asrt.assertFalse(new P16_VouchersPopUp(driver).vendorsList().stream().anyMatch(v -> v.getText().equalsIgnoreCase(vendorMap.get("name"))));
            } else if (existance.equalsIgnoreCase("deleted")) {
                new P00_multiPurposes(driver).waitLoading();
                asrt.assertFalse(vendors.vendorsNames.stream().anyMatch(c -> c.getText().equalsIgnoreCase(vendorMap.get("name"))));
                new D06_DigitalPayment().goToDesiredVouchersPage("Expenses");
                new P00_multiPurposes(driver).waitLoading();
                new P10_VouchersPage(driver).newVoucherButton.click();
                asrt.assertFalse(new P16_VouchersPopUp(driver).vendorsList().stream().anyMatch(v -> v.getText().equalsIgnoreCase(vendorMap.get("name"))));
            }
            asrt.assertAll();
        }
    }

    @When("eidting vendor {string}  name {string} phone {string} email {string} VAT {string} status {string}")
    public void eidtingVendorNamePhoneEmailVATStatus(String oName, String nName, String phone, String email, String vat, String status) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedVendor = vendors.vendorsNames.stream().filter(c -> c.getText().equalsIgnoreCase(oName)).findAny().orElseThrow();
        setVendorMap(vendors.vendorName(selectedVendor).getText(), vendors.vendorPhone(selectedVendor).getText(), vendors.vendorEmail(selectedVendor).getText(), vendors.vendorVAT(selectedVendor).getText(), vendors.vendorCRNo(selectedVendor).getText(), "", "", "", vendors.vendorStatus(selectedVendor).getText());
        vendors.vendorEditButton(selectedVendor).click();
        new P00_multiPurposes(driver).waitLoading();
        fillVendorData(nName, phone, email, vat, "", "", "", "", status);
        vendors.submitButton.click();
    }

    @When("deleting vendor {string}")
    public void deletingVendor(String name) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedVendor = vendors.vendorsNames.stream().filter(c -> c.getText().equalsIgnoreCase(name)).findAny().orElseThrow();
        setVendorMap(vendors.vendorName(selectedVendor).getText(), vendors.vendorPhone(selectedVendor).getText(), vendors.vendorEmail(selectedVendor).getText(), vendors.vendorVAT(selectedVendor).getText(), vendors.vendorCRNo(selectedVendor).getText(), "", "", "", vendors.vendorStatus(selectedVendor).getText());
        vendors.vendorDeleteButton(selectedVendor).click();
        vendors.confirmDeleteButton.click();
    }

    @Then("check default vendor in the grid")
    public void checkDefaultVendorInTheGrid() {
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertTrue(vendors.vendorsNames.stream().anyMatch(c -> c.getText().equalsIgnoreCase("Default vendor")));
        asrt.assertAll();
    }
}
