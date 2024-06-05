package alia.nazeel.stepDefs;

import alia.nazeel.pojos.Outlet;
import alia.nazeel.tools.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.outlets.P30_OutletsSetup;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


//import java.time.Duration;
import java.time.Duration;
import java.util.*;

public class D14_1_Outlets {

    final WebDriver driver = DriverManager.getDriver();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    //Actions actions = new Actions(driver);
    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P30_OutletsSetup outletsSetup = new P30_OutletsSetup(driver);

    Outlet outlet;
    int newButtonClicks = 0;

    @Given("go to outlets Setup Page")
    public void goToOutletsSetupPage() {
        wait.waitLoading();
        dashBoardPage.setupPageLink.click();
        wait.waitLoading();
        setupPage.outletsDropList.click();
        wait.waitLoading();
        setupPage.outletSetupLink.click();
    }

    @When("Checking the validity of required fields of Outlet creation")
    public void checkigThevalidityOfRequiredFieldsOfOutletCreation(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            creatingOutletWithOpStateAndCodeAndNameDescription(columns.get("opState"), columns.get("code"), columns.get("name"), columns.get("desc"));
            assertValidityOfFieldsAndOutlet(columns.get("msg"));
        }

    }

    @When("Checkig thevalidity of required fields of Outlet editing")
    public void checkigThevalidityOfRequiredFieldsOfOutletEditing(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            editingOutletOpStateAndCodeAndNameDescription(columns.get("oName"), columns.get("opState"), columns.get("code"), columns.get("nName"), columns.get("desc"), columns.get("state"));
            assertValidityOfFieldsAndOutlet(columns.get("msg"));
        }

    }

    @When("deleting Outlets Check Constrains")
    public void deletinfOutletsCheckConstrains(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            deletingOutlet(columns.get("outlet"));
            checkOutletIsDeleted(columns.get("msg"), columns.get("outlet"));
        }
    }

    @When("Filtering With the below table Check the filtered Criteria for Outlets")
    public void filteringWithTheBelwTableCheclTheFilteredCriteriaForOutlets(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            filteringWithAs(columns.get("filter"), columns.get("value"));
            checkAllVisibleRecordsAs(columns.get("filter"), columns.get("value"));
        }
    }

    @Then("Check Outlet deletion validation")
    public void checkOutletDeletionValidation() {
        asrt.assertAll();
    }

    @Then("assert all filtering success")
    public void assertAllFilteringSuccess() {
        asrt.assertAll();
    }

    @Then("Check validity of Outlet fields and outlet")
    public void checkValidityOfOutletFieldsAndOutlet() {
        asrt.assertAll();
    }

    private void assertValidityOfFieldsAndOutlet(String msg) {
        asrt.AssertToastMessagesContains(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            WebElement selectedOutlet = outletsSetup.codes.stream().filter(c -> c.getText().equalsIgnoreCase(outlet.getCode())).findAny().orElseThrow();
            asrt.assertTrue(outletsSetup.outletOPStatus(selectedOutlet).getText().equalsIgnoreCase(outlet.getOpState()));
            asrt.assertTrue(outletsSetup.outletName(selectedOutlet).getText().equalsIgnoreCase(outlet.getName()));
            asrt.assertTrue(outletsSetup.outletCode(selectedOutlet).getText().equalsIgnoreCase(outlet.getCode()));
            asrt.assertTrue(outletsSetup.outletDescription(selectedOutlet).getText().equalsIgnoreCase(outlet.getDesc()));
            if (outlet.getState().equalsIgnoreCase("active"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-check"));
            else if (outlet.getState().equalsIgnoreCase("inactive"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-minus"));

        } else
            clearOtletFields();
    }

    private void clearOtletFields() {
        outletsSetup.opStatus.clearSelection();
        outletsSetup.outletNameField.clear();
        outletsSetup.descriptionField.clear();
        outletsSetup.outletCodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }


    public void creatingOutletWithOpStateAndCodeAndNameDescription(String opState, String code, String name, String desc) {
        if (newButtonClicks == 0) {
            outletsSetup.newOutletButton.click();
            newButtonClicks++;
        }
        fillOutletData(opState, code, name, desc, "new");
        outletsSetup.submitButton.click();
    }


    private void fillOutletData(String opState, String code, String name, String desc, String state) {
        if (!Utils.isEmptyOrNull(opState))
            if (opState.equalsIgnoreCase("non"))
                outletsSetup.opStatus.clearSelection();
            else
                outletsSetup.opStatus.selectByTextContainsIgnoreCase(opState);
        if (!Utils.isEmptyOrNull(code)) {
            outletsSetup.outletCodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!code.equalsIgnoreCase("non"))
                outletsSetup.outletCodeField.sendKeys(code);
        }
        if (!Utils.isEmptyOrNull(name)) {
            outletsSetup.outletNameField.clear();
            if (!name.equalsIgnoreCase("non")) {
                outletsSetup.outletNameField.sendKeys(name, name);
            }
        }
        if (!Utils.isEmptyOrNull(desc)) {
            outletsSetup.descriptionField.clear();
            if (!desc.equalsIgnoreCase("non"))
                outletsSetup.descriptionField.sendKeys(desc);
        }
        outlet = new Outlet(name, code, opState, desc, state);
        if (state.equalsIgnoreCase("new"))
            outlet.setState("Active");
        else if ((outletsSetup.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (outletsSetup.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            outletsSetup.statusSwitch.click();
    }


    public void checkMsgAndTheOutletInTheGrid(String msg) {
        asrt.AssertToastMessagesContains(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            WebElement selectedOutlet = outletsSetup.codes.stream().filter(c -> c.getText().equalsIgnoreCase(outlet.getCode())).findAny().orElseThrow();
            asrt.assertTrue(outletsSetup.outletOPStatus(selectedOutlet).getText().equalsIgnoreCase(outlet.getOpState()));
            asrt.assertTrue(outletsSetup.outletName(selectedOutlet).getText().equalsIgnoreCase(outlet.getName()));
            asrt.assertTrue(outletsSetup.outletCode(selectedOutlet).getText().equalsIgnoreCase(outlet.getCode()));
            asrt.assertTrue(outletsSetup.outletDescription(selectedOutlet).getText().equalsIgnoreCase(outlet.getDesc()));
            if (outlet.getState().equalsIgnoreCase("active"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-check"));
            else if (outlet.getState().equalsIgnoreCase("inactive"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-minus"));
        }
        asrt.assertAll();

    }


    public void editingOutletOpStateAndCodeAndNameDescription(String oName, String opState, String code, String nName, String desc, String state) {
        wait.waitLoading();
        if (newButtonClicks == 0) {
            WebElement selectedOutlet = extractOutlet(oName);
            outletsSetup.outletEditButton(selectedOutlet).click();
            wait.waitLoading();
            newButtonClicks++;
        }
        fillOutletData(opState, code, nName, desc, state);

        outletsSetup.submitButton.click();

    }

    private WebElement extractOutlet(String outletName) {
        WebElement selectedOutlet = outletsSetup.names.stream().filter(n -> n.getText().equalsIgnoreCase(outletName)).findAny().orElseThrow();
        String outletStat = "Active";
        if (outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-minus"))
            outletStat = "Inactive";
        outlet = new Outlet(outletsSetup.outletName(selectedOutlet).getText(), outletsSetup.outletCode(selectedOutlet).getText(), outletsSetup.outletOPStatus(selectedOutlet).getText(), outletsSetup.outletDescription(selectedOutlet).getText(), outletStat);
        return selectedOutlet;
    }

    @When("Filtering With {string} as {string}")
    public void filteringWithAs(String filter, String value) {
        if (newButtonClicks == 0) {
            outletsSetup.filterButton.click();
            wait.waitLoading();
            newButtonClicks++;
        }
        switch (filter.toLowerCase()) {
            case "status" ->
                    outletsSetup.statusFilter.selectByTextContainsIgnoreCase(value);
            case "name" -> {
                outletsSetup.nameFilterField.click();
                outletsSetup.nameFilterField.sendKeys(value);
            }
            case "code" -> outletsSetup.outletFilterCodeField.sendKeys(value);
            case "opstatus" ->
                    outletsSetup.opStatusFilter.selectByTextContainsIgnoreCase(value);
        }
        outletsSetup.searchFilterButton.click();
    }

    @Then("check all visible records {string} as {string}")
    public void checkAllVisibleRecordsAs(String filter, String value) {
        wait.waitLoading();
        switch (filter.toLowerCase()) {
            case "status" -> {
                switch (value.toLowerCase()) {
                    case "active" ->
                            asrt.assertFalse(outletsSetup.statuses.stream().anyMatch(s -> !s.getAttribute("xlink:href").contains("icon-check")));
                    case "inactive" ->
                            asrt.assertFalse(outletsSetup.statuses.stream().anyMatch(s -> !s.getAttribute("xlink:href").contains("icon-minus")));
                }
            }
            case "name" ->
                    asrt.assertFalse(outletsSetup.names.stream().anyMatch(o -> !o.getText().equalsIgnoreCase(value)));
            case "code" ->
                    asrt.assertFalse(outletsSetup.codes.stream().anyMatch(o -> !o.getText().equalsIgnoreCase(value)));
            case "opstatus" ->
                    asrt.assertFalse(outletsSetup.opStates.stream().anyMatch(o -> !o.getText().equalsIgnoreCase(value)));
        }
        clearFilters();
    }

    private void clearFilters() {
        outletsSetup.opStatusFilter.clearSelection();
        outletsSetup.statusFilter.clearSelection();
        outletsSetup.nameFilterField.clear();
        outletsSetup.outletFilterCodeField.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
    }


    public void deletingOutlet(String name) {
        WebElement selectedOutlet = extractOutlet(name);
        outletsSetup.outletDeleteButton(selectedOutlet).click();
        outletsSetup.popUpCOnfirmButton.click();
    }


    public void checkOutletIsDeleted(String msg, String name) {
        wait.waitLoading();
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            asrt.assertFalse(outletsSetup.names.stream().anyMatch(o -> o.getText().equalsIgnoreCase(name)));
        }
    }

}
