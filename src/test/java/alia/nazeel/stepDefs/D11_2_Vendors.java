package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.customers.P22_Corporates;
import alia.nazeel.pages.customers.P34_Vendors;
import alia.nazeel.pages.customers.P35_Guests;
import alia.nazeel.pages.mutlipurposes.P00_3_CorporateSelectionPopUp;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pages.vouchersPages.P10_VouchersPage;
import alia.nazeel.pages.vouchersPages.P16_VouchersPopUp;
import alia.nazeel.pojos.Vendor;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D11_2_Vendors {

    final WebDriver driver = DriverManager.getDriver();


    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P22_Corporates corporates = new P22_Corporates(driver);
    final P34_Vendors vendors = new P34_Vendors(driver);
    final P35_Guests guests = new P35_Guests(driver);
    final P00_3_CorporateSelectionPopUp selectCorpPopup = new P00_3_CorporateSelectionPopUp(driver);

    Vendor vendor;



    @And("go to Vendors page")
    public void goToVendorsPage() {
        wait.waitLoading();
        dashBoardPage.customersDropList.click();
        wait.waitLoading();
        dashBoardPage.vendorssLink.click();
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

        vendor = new Vendor(name, phone, email, vat, desc, state, cR, pCode, address);

        if (state.equalsIgnoreCase("new")) {
            vendor.setStatus("Active");
        } else if ((vendors.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (vendors.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
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
                wait.waitLoading();
                WebElement selectedVendor = vendors.vendorsNames.stream().filter(c -> c.getText().equalsIgnoreCase(vendor.getName())).findAny().orElseThrow();
                asrt.assertTrue(vendors.vendorPhone(selectedVendor).getText().contains(vendor.getPhone()), "Expected: " + vendor.getPhone() + " ,Actual: " + vendors.vendorPhone(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorEmail(selectedVendor).getText().equalsIgnoreCase(vendor.getEmail()), "Expected: " + vendor.getEmail() + " ,Actual: " + vendors.vendorEmail(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorVAT(selectedVendor).getText().equalsIgnoreCase(vendor.getVat()), "Expected: " + vendor.getVat() + " ,Actual: " + vendors.vendorVAT(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorCRNo(selectedVendor).getText().equalsIgnoreCase(vendor.getCrNo()), "Expected: " + vendor.getVat() + " ,Actual: " + vendors.vendorCRNo(selectedVendor).getText());
                asrt.assertTrue(vendors.vendorStatus(selectedVendor).getText().equalsIgnoreCase(vendor.getStatus()), "Expected: " + vendor.getStatus() + " ,Actual: " + vendors.vendorStatus(selectedVendor).getText());
                new D06_DigitalPayment().goToDesiredVouchersPage("Expenses");
                wait.waitLoading();
                new P10_VouchersPage(driver).newVoucherButton.click();
                if (vendor.getStatus().equalsIgnoreCase("active"))
                    asrt.assertTrue(new P16_VouchersPopUp(driver).vendorsList().stream().anyMatch(v -> v.getText().equalsIgnoreCase(vendor.getName())));
                else
                    asrt.assertFalse(new P16_VouchersPopUp(driver).vendorsList().stream().anyMatch(v -> v.getText().equalsIgnoreCase(vendor.getName())));
            } else if (existance.equalsIgnoreCase("deleted")) {
                wait.waitLoading();
                asrt.assertFalse(vendors.vendorsNames.stream().anyMatch(c -> c.getText().equalsIgnoreCase(vendor.getName())));
                new D06_DigitalPayment().goToDesiredVouchersPage("Expenses");
                wait.waitLoading();
                new P10_VouchersPage(driver).newVoucherButton.click();
                asrt.assertFalse(new P16_VouchersPopUp(driver).vendorsList().stream().anyMatch(v -> v.getText().equalsIgnoreCase(vendor.getName())));
            }
            asrt.assertAll();
        }
    }

    @When("eidting vendor {string}  name {string} phone {string} email {string} VAT {string} status {string}")
    public void eidtingVendorNamePhoneEmailVATStatus(String oName, String nName, String phone, String email, String vat, String status) {
        wait.waitLoading();
        WebElement selectedVendor = vendors.vendorsNames.stream().filter(c -> c.getText().equalsIgnoreCase(oName)).findAny().orElseThrow();
        vendor = new Vendor(vendors.vendorName(selectedVendor).getText(), vendors.vendorPhone(selectedVendor).getText(), vendors.vendorEmail(selectedVendor).getText(), vendors.vendorVAT(selectedVendor).getText(), "", vendors.vendorStatus(selectedVendor).getText(), vendors.vendorCRNo(selectedVendor).getText(), "", "");

        vendors.vendorEditButton(selectedVendor).click();
        wait.waitLoading();
        fillVendorData(nName, phone, email, vat, "", "", "", "", status);
        vendors.submitButton.click();
    }

    @When("deleting vendor {string}")
    public void deletingVendor(String name) {
        wait.waitLoading();
        WebElement selectedVendor = vendors.vendorsNames.stream().filter(c -> c.getText().equalsIgnoreCase(name)).findAny().orElseThrow();
        vendor = new Vendor(vendors.vendorName(selectedVendor).getText(), vendors.vendorPhone(selectedVendor).getText(), vendors.vendorEmail(selectedVendor).getText(), vendors.vendorVAT(selectedVendor).getText(), "", vendors.vendorStatus(selectedVendor).getText(), vendors.vendorCRNo(selectedVendor).getText(), "", "");
        vendors.vendorDeleteButton(selectedVendor).click();
        vendors.confirmDeleteButton.click();
    }

    @Then("check default vendor in the grid")
    public void checkDefaultVendorInTheGrid() {
        wait.waitLoading();
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
        wait.waitLoading();
        switch (filter.toLowerCase()) {
            case "name" -> asrt.assertTrue(vendors.vendorsNames.stream().anyMatch(vn -> !vn.getText().contains(value)));
            case "status" -> asrt.assertTrue(vendors.statuses.stream().anyMatch(vn -> !vn.getText().contains(value)));
            case "vat" -> asrt.assertTrue(vendors.vATs.stream().anyMatch(vn -> !vn.getText().contains(value)));
            case "crno" -> asrt.assertTrue(vendors.cRNos.stream().anyMatch(vn -> !vn.getText().contains(value)));
        }
        asrt.assertAll();
    }
}
