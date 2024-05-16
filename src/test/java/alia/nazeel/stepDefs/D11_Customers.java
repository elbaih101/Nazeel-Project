package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pages.vouchersPages.P10_VouchersPage;
import alia.nazeel.pages.vouchersPages.P16_VouchersPopUp;
import alia.nazeel.tools.Utils;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.pages.customers.P22_Corporates;
import alia.nazeel.pages.customers.P34_Vendors;
import alia.nazeel.pages.customers.P35_Guests;
import alia.nazeel.pages.mutlipurposes.P00_3_CorporateSelectionPopUp;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class D11_Customers {

    WebDriver driver = DriverManager.getDriver();


    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P22_Corporates corporates = new P22_Corporates(driver);
    P34_Vendors vendors = new P34_Vendors(driver);
    P35_Guests guests = new P35_Guests(driver);
    P00_3_CorporateSelectionPopUp selectCorpPopup = new P00_3_CorporateSelectionPopUp(driver);


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
           selectCorpPopup.saveButton.click();

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
            new P00_multiPurposes(driver).secondLanguageField(vendors.vendorNameField).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
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

    @When("filtering vendors {string} as {string}")
    public void filteringVendorsAs(String filter, String value) {
        vendors.filterButton.click();
        switch (filter.toLowerCase()) {
            case "name" -> vendors.nameFilterField.sendKeys(value);
            case "status" ->
                    vendors.statusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "vat" -> vendors.vATFilterField.sendKeys(value);
            case "crno" -> vendors.cRNoFilterField.sendKeys(value);
        }
        vendors.searchFilterButton.click();
    }

    @Then("Check Records shown {string} as {string}")
    public void checkRecordsShownAs(String filter, String value) {
        new P00_multiPurposes(driver).waitLoading();
        switch (filter.toLowerCase()) {
            case "name" -> asrt.assertTrue(vendors.vendorsNames.stream().anyMatch(vn -> !vn.getText().contains(value)));
            case "status" -> asrt.assertTrue(vendors.statuses.stream().anyMatch(vn -> !vn.getText().contains(value)));
            case "vat" -> asrt.assertTrue(vendors.vATs.stream().anyMatch(vn -> !vn.getText().contains(value)));
            case "crno" -> asrt.assertTrue(vendors.cRNos.stream().anyMatch(vn -> !vn.getText().contains(value)));
        }
        asrt.assertAll();
    }

    @And("go to Guests page")
    public void goToGuestsPage() {
        new P00_multiPurposes(driver).waitLoading();
        dashBoardPage.customersDropList.click();
        dashBoardPage.guestsLink.click();
    }


    HashMap<String, String> guestMap = new HashMap<>();

    private void setGuestMap(String fName, String lName, String phone, String nat, String idType, String idNum, String state) {
        if (!phone.isEmpty())
            guestMap.put("phone", phone);
        if (!idType.isEmpty())
            guestMap.put("idType", idType);
        if (!idNum.isEmpty())
            guestMap.put("idNum", idNum);
        if (!fName.isEmpty()) {
            String name = fName;
            if (!lName.isEmpty())
                name = name + " " + lName;
            guestMap.put("name", name);
        }
        if (!nat.isEmpty())
            guestMap.put("nat", nat);
        if (!state.isEmpty())
            guestMap.put("state", state);

    }

    private void fillGuestData(String fName, String lName, String phone, String nat, String gType, String idType, String idNumb, String state, String ign, String inv) {
        if (!fName.isEmpty()) {
            guests.firstNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            WebElement fnArField = new P00_multiPurposes(driver).secondLanguageField(guests.firstNameField);
            fnArField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!fName.equalsIgnoreCase("non")) {
                guests.firstNameField.sendKeys(fName);
                fnArField.sendKeys(fName);
            }
        }
        if (!lName.isEmpty()) {
            guests.lastNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            WebElement lNArField = new P00_multiPurposes(driver).secondLanguageField(guests.lastNameField);
            lNArField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!lName.equalsIgnoreCase("non")) {
                guests.lastNameField.sendKeys(lName);
                lNArField.sendKeys(lName);
            }
        }
        if (!ign.contains("bDate")) {
            Utils.setDate(guests.dateOfBirthField, "12/3/1999");
        }
        if (inv.contains("bDate")) {
            Utils.setDate(guests.dateOfBirthField, DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
        }
        if (!ign.contains("Gender")) {
            guests.getListItems(guests.genderComboBox).getFirst().click();
        }
        if (!nat.isEmpty()) {
            if (nat.equalsIgnoreCase("non")) {
                guests.clearSelectionButton(guests.nationalityComboBox).click();
            } else {
                guests.getListItems(guests.nationalityComboBox).stream().filter(id -> id.getText().equalsIgnoreCase(nat)).findAny().orElseThrow().click();
            }
        }
        if (!gType.isEmpty()) {
            if (gType.equalsIgnoreCase("non")) {
                guests.clearSelectionButton(guests.guestTypeComboBox).click();
            } else {
                guests.getListItems(guests.guestTypeComboBox).getFirst().click();
            }
        }

        if (!idType.isEmpty()) {
            if (idType.equalsIgnoreCase("non")) {
                guests.clearSelectionButton(guests.idTypeComboBox).click();
            } else {
                guests.getListItems(guests.idTypeComboBox).stream().filter(id -> id.getText().equalsIgnoreCase(idType)).findAny().orElseThrow().click();
            }
        }
        if (!idNumb.isEmpty()) {
            guests.idNumberField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!idNumb.equalsIgnoreCase("non")) {
                guests.idNumberField.sendKeys(idNumb);
            }
        }
        if (!phone.isEmpty()) {
            if (phone.equalsIgnoreCase("non")) {
                guests.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                guests.dialcodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            } else if (phone.equalsIgnoreCase("dialonly")) {
                guests.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                guests.dialCodes().getFirst().click();
            } else if (phone.equalsIgnoreCase("nodial")) {
                guests.dialcodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                guests.mobileField.sendKeys("336987897");
            } else {
                guests.dialCodes().getFirst().click();
                guests.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                guests.mobileField.sendKeys(phone);
            }
        }

        if (!ign.contains("idSerial")) {
            guests.getListItems(guests.idSerialComboBox).getFirst().click();
        }
        if (inv.contains("Gender")) {
            guests.clearSelectionButton(guests.genderComboBox).click();
        }

        if (inv.contains("idSerial")) {
            guests.clearSelectionButton(guests.idSerialComboBox).click();
        }
        setGuestMap(fName, lName, phone, nat, idType, idNumb, state);
        if (state.equalsIgnoreCase("new"))
            guestMap.put("state", "Active");
        else if ((guests.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (guests.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            guests.statusSwitch.click();
    }

    @When("create Guest firstname {string} last name {string}  phone {string} nationality {string} Guest Type {string} id type {string} id number {string} ignored Fields {string} invailed Fields {string}")
    public void createGuestFirstnameLastNamePhoneIdTypeIdNumberIgnoredFieldsInvailedFields(String fName, String
            lName, String phone, String nat, String gType, String idType, String idNumb, String ign, String inv) {
        new P00_multiPurposes(driver).waitLoading();
        guests.newGuestButton.click();
        new P00_multiPurposes(driver).waitLoading();
        fillGuestData(fName, lName, phone, nat, gType, idType, idNumb, "new", ign, inv);
        guests.submitButton.click();
    }

    @Then("Check msg {string} and Guest is {string}")
    public void checkMsgAndGuestIs(String msg, String existance) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("successfully")) {
            if (existance.equalsIgnoreCase("added")) {
                new P00_multiPurposes(driver).waitLoading();
                WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestMap.get("idNum"))).findAny().orElseThrow();
                asrt.assertTrue(guests.guestNationality(selectedGuest).getText().equalsIgnoreCase(guestMap.get("nat"))
                        , "Expected: " + guestMap.get("nat") + " ,Actual: " + guests.guestNationality(selectedGuest).getText());

                asrt.assertTrue(guests.guestphone(selectedGuest).getText().contains(guestMap.get("phone")), "Expected: " + guestMap.get("phone") + " ,Actual: " + guests.guestphone(selectedGuest).getText());
                asrt.assertTrue(guests.guestIDType(selectedGuest).getText().equalsIgnoreCase(guestMap.get("idType")), "Expected: " + guestMap.get("idType") + " ,Actual: " + guests.guestIDType(selectedGuest).getText());
                asrt.assertTrue(guests.guestName(selectedGuest).getText().contains(guestMap.get("name")), "Expected: " + guestMap.get("name") + " ,Actual: " + guests.guestName(selectedGuest).getText());
            } else if (existance.equalsIgnoreCase("deleted")) {
                new P00_multiPurposes(driver).waitLoading();
                asrt.assertFalse(guests.guestsNames.stream().anyMatch(c -> c.getText().equalsIgnoreCase(guestMap.get("idNum"))));
            }
            asrt.assertAll();
        }
    }

    @When("edit Guest with id number {string} firstname {string} last name {string}  phone {string} nationality {string} Guest Type {string} id type {string} id number {string} status {string} ignored Fields {string} invailed Fields {string}")
    public void editGuestWithIdNumberFirstnameLastNamePhoneNationalityIdTypeIdNumberIgnoredFieldsInvailedFields(String oId, String fName, String lName, String phone, String nat, String gType, String idType, String nId, String state, String ign, String inv) {

        WebElement selectedGuest = getGuestData(oId);
        guests.guestEditButton(selectedGuest).click();
        new P00_multiPurposes(driver).waitLoading();
        fillGuestData(fName, lName, phone, nat, gType, idType, nId, state, ign, inv);
        guests.submitButton.click();

    }

    private WebElement getGuestData(String id) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedGuest;
        if (id.equalsIgnoreCase("random"))
            selectedGuest = guests.idNumbers.getFirst();
        else
            selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(id)).findAny().orElseThrow();
        String[] name = guests.guestName(selectedGuest).getText().split("\\s");
        setGuestMap(name[0], name[1], guests.guestphone(selectedGuest).getText().split("\\s")[1], guests.guestNationality(selectedGuest).getText(), guests.guestIDType(selectedGuest).getText(), guests.guestIDNumber(selectedGuest).getText(), guests.guestStatus(selectedGuest).getText());
        return selectedGuest;
    }

    @When("delete Guest with id number {string}")
    public void deleteGuestWithIdNumber(String idNum) {
        WebElement selectedGuest = getGuestData(idNum);
        guests.guestDeleteButton(selectedGuest).click();
        guests.confirmDeleteButton.click();
    }

    String compNote;
    String propNote;

    @When("adding a company note {string} and property ote {string} to guest with id {string}")
    public void addingACompanyNoteAndPropertyOteToGuestWithId(String compNote, String propNote, String guestId) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        new P00_multiPurposes(driver).waitLoading();
        guests.guestNotesTab.click();
        guests.companyNotesField.sendKeys(compNote);
        guests.addCompanyNoteButton.click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successed");
        this.compNote = compNote;
        guests.propertyNotesField.sendKeys(propNote);
        guests.addPropertyNoteButton.click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successed");
        this.propNote = propNote;
    }

    private List<Boolean> asseertGuestNote(String note) {
        WebElement selectedNote = guests.notesContentes.stream().filter(c -> c.getText().contains(note)).findAny().orElseThrow();
        List<Boolean> aserts = new ArrayList<>();
        aserts.add(guests.noteHeader(selectedNote).getText().contains(new P00_multiPurposes(driver).userName()));
        aserts.add(guests.noteHeader(selectedNote).getText().contains(new P00_multiPurposes(driver).propertyName()));
        return aserts;
    }

    @Then("check the note is added with the name of the user who created it and the property")
    public void checkTheNoteIsAddedWithTheNameOfTheUserWhoCreatedItAndTheProperty() {
        new P00_multiPurposes(driver).waitLoading();
        asseertGuestNote(compNote).forEach(asrt::assertTrue);
        asseertGuestNote(propNote).forEach(asrt::assertTrue);
        asrt.assertAll();
    }

    String editedNote;

    @When("editing a company note {string} to be {string} to guest with id {string}")
    public void editingACompanyNoteToBeToGuestWithId(String oldNote, String neNote, String guestId) {
        WebElement selectedNote = selectNoteFromGuest(oldNote, guestId);
        guests.noteEditButton(selectedNote).click();
        guests.editNoteField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        guests.editNoteField.sendKeys(neNote);
        guests.saveEditNoteButton.click();
        this.editedNote = neNote;

    }

    @Then("Check the Edited Note")
    public void checkTheEditedNote() {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successed");
        new P00_multiPurposes(driver).waitLoading();
        asseertGuestNote(editedNote).forEach(asrt::assertTrue);
        asrt.assertAll();

    }

    private WebElement selectNoteFromGuest(String oldNote, String guestId) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        new P00_multiPurposes(driver).waitLoading();
        guests.guestNotesTab.click();
        new P00_multiPurposes(driver).waitLoading();
        return guests.notesContentes.stream().filter(c -> c.getText().contains(oldNote)).findAny().orElseThrow();
    }

    String deltetdNote;

    @When("deleting guest Note {string} to guest with id {string}")
    public void deletingGuestNoteToGuestWithId(String note, String guestId) {
        WebElement selectedNote = selectNoteFromGuest(note, guestId);
        guests.notedeleteButton(selectedNote).click();
        guests.confirmDeleteButton.click();
        this.deltetdNote = note;
    }

    @Then("Check the success msg and the note is deleted")
    public void checkTheSuccessMsgAndTheNoteIsDeleted() {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Deleted Successfully");
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertFalse(guests.notesContentes.stream().anyMatch(c -> c.getText().contains(deltetdNote)));
    }

    String docName;

    @When("adding a Document to guest with id {string} naming it {string}")
    public void addingADocumentToGuestWithIdNamingIt(String guestId, String docName) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        new P00_multiPurposes(driver).waitLoading();
        guests.guestDocumentsTab.click();
        guests.addDocumentButton.click();
        Utils.fileUpload(guests.documentUploadField, "src/main/resources/Images", 1);
        guests.documentNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        guests.documentNameField.sendKeys(docName);
        guests.submitDocumentButton.click();
        guests.submitButton.click();
        guestMap.put("id", guestId);
        this.docName = docName;

    }

    WebElement selectedDoc;

    @Then("Check the added document visible with the name {string}")
    public void checkTheAddedDocumentVisibleWithTheName(String docName) {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successfully");
        selectedDoc = selectDocumentFromGuest(this.docName, guestMap.get("id"));
        asrt.assertTrue(selectedDoc != null);
        asrt.assertAll();
    }

    private WebElement selectDocumentFromGuest(String docName, String guestId) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        new P00_multiPurposes(driver).waitLoading();
        guests.guestDocumentsTab.click();
        new P00_multiPurposes(driver).waitLoading();
        return guests.documentNames.stream().filter(c -> c.getText().contains(docName)).findAny().orElse(null);
    }

    @When("deleting the document")
    public void deletingTheDocument() {
        guests.documentDeleteButton(selectedDoc).click();
        guests.confirmDeleteDocumentButton.click();

    }

    @Then("Check the document no more exist")
    public void checkTheDocumentNoMoreExist() {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successfully");
        selectedDoc = selectDocumentFromGuest(docName, guestMap.get("id"));
        asrt.assertTrue(selectedDoc == null);
        asrt.assertAll();
    }


    @When("Filtering guest {string} as {string}")
    public void filteringGuestAs(String filter, String value) {
        new P00_multiPurposes(driver).waitLoading();
        guests.filterButton.click();
        switch (filter.toLowerCase()) {
            case "status" ->
                    guests.filterStatusesList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "name" -> guests.nameFilterField.sendKeys(value);
            case "phone" -> guests.mobileFilterField.sendKeys(value);
            case "nationality" ->
                    guests.filterNationalityList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "class" ->
                    guests.filterGuestClassList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "idtype" ->
                    guests.filterIdTypeList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "idnum" -> guests.iDFilterField.sendKeys(value);
        }
        guests.searchButton.click();
    }

    @Then("Check all guests shown {string} as {string}")
    public void checkAllGuestsShownAs(String filter, String value) {
        new P00_multiPurposes(driver).waitLoading();
        List<WebElement> theList = new ArrayList<>();
        switch (filter.toLowerCase()) {
            case "status" -> {
                theList = guests.statuses;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
            case "name" -> {
                theList = guests.guestsNames;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().contains(value)));
            }
            case "phone" -> {
                theList = guests.mobiles;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().contains(value)));
            }
            case "nationality" -> {
                theList = guests.nationalities;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
            case "class" -> {
                theList = guests.classes;
                if (!theList.isEmpty()) {
                    P00_multiPurposes p00MultiPurposes = new P00_multiPurposes(driver);
                    asrt.assertTrue(theList.stream().anyMatch(s -> !p00MultiPurposes.toolTip(s.findElement(By.xpath("./div/div"))).getText().toLowerCase().contains(value.toLowerCase())));
                }
            }
            case "idtype" -> {
                theList = guests.iDTypes;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
            case "idnum" -> {
                theList = guests.idNumbers;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
        }
        asrt.assertAll();
    }

    public String selectCorporate(String corporateName, String corpPhone, String corpEmail, String corpVAT) {
        WebElement criteriaButton;
        String searchText;
        if (corporateName.equalsIgnoreCase("RANDOM")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("name")).findAny().orElseThrow();
            searchText="";
        } else if (corpPhone.equalsIgnoreCase("") && corpEmail.equalsIgnoreCase("") && corpVAT.equalsIgnoreCase("")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("name")).findAny().orElseThrow();
            searchText=corporateName;
        } else if (corporateName.equalsIgnoreCase("") && corpEmail.equalsIgnoreCase("") && corpVAT.equalsIgnoreCase("")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("phone")).findAny().orElseThrow();
            searchText=corpPhone;
        } else if (corporateName.equalsIgnoreCase("") && corpPhone.equalsIgnoreCase("") && corpVAT.equalsIgnoreCase("")) {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("email")).findAny().orElseThrow();
            searchText=corpEmail;
        }else {
            criteriaButton = selectCorpPopup.searchCriteriasButtons.stream().filter(t -> t.getText().equalsIgnoreCase("vat no.")).findAny().orElseThrow();
            searchText=corpEmail;
        }
        wait.until(ExpectedConditions.elementToBeClickable(criteriaButton));

        criteriaButton.click();
        selectCorpPopup.searchFiled.sendKeys(searchText);
        selectCorpPopup.searchButton.click();

        List<WebElement> corpNames = selectCorpPopup.corporatesNAmes;
        WebElement selectedCorp = corpNames.get(new Random().nextInt(corpNames.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selectedCorp));
        String corpName =selectedCorp.getText();
        selectedCorp.click();
        return corpName;

        }


}
