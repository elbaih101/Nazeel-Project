package org.example.stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.setuppages.P05_SetupPage;
import org.example.pages.setuppages.outlets.P30_OutletsSetup;
import org.example.pages.setuppages.outlets.P31_OutletCategories;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;

public class D14_Outlets {
    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    Actions actions = new Actions(driver);
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPage = new P05_SetupPage(driver);
    P30_OutletsSetup outletsSetup = new P30_OutletsSetup(driver);
    P31_OutletCategories categories = new P31_OutletCategories(driver);


    @Given("go to outlets Setup Page")
    public void goToOutletsSetupPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.outletsDropList.click();
        setupPage.outletSetupLink.click();
    }

    @When("creating outlet with opState {string} and code {string} and name {string} description {string}")
    public void creatingOutletWithOpStateAndCodeAndNameDescription(String opState, String code, String name, String desc) {
        outletsSetup.newOutletButton.click();
        fillOutletData(opState, code, name, desc, "new");
        outletsSetup.submitButton.click();
    }

    HashMap<String, String> outletMap = new HashMap<>();

    private void setOutletMap(String opState, String code, String name, String desc, String state) {
        if (!opState.isEmpty())
            outletMap.put("opState", opState);
        if (!code.isEmpty())
            outletMap.put("code", code);
        if (!name.isEmpty())
            outletMap.put("name", name);
        if (!desc.isEmpty())
            outletMap.put("desc", desc);
        if (!state.isEmpty())
            outletMap.put("state", state);
        //outletMap.putAll(Map.of("opState", opState, "code", code, "name", name, "desc", desc, "state", state));
    }

    private void fillOutletData(String opState, String code, String name, String desc, String state) {
        if (!opState.isEmpty())
            if (opState.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", outletsSetup.clearOpStateSelection);
            else
                outletsSetup.opStatusesList().stream().filter(o -> o.getText().equalsIgnoreCase(opState)).findFirst().get().click();
        if (!code.isEmpty()) {
            outletsSetup.outletCodeField.clear();
            if (!code.equalsIgnoreCase("non"))
                outletsSetup.outletCodeField.sendKeys(code);
        }
        if (!name.isEmpty()) {
            outletsSetup.outletNameField.clear();
            if (!name.equalsIgnoreCase("non"))
                outletsSetup.outletNameField.sendKeys(name);
        }
        if (!desc.isEmpty()) {
            outletsSetup.descriptionField.clear();
            if (!desc.equalsIgnoreCase("non"))
                outletsSetup.descriptionField.sendKeys(desc);
        }

        setOutletMap(opState, code, name, desc, state);
        if (state.equalsIgnoreCase("new"))
            outletMap.put("state", "Active");
        else if ((outletsSetup.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (outletsSetup.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            outletsSetup.statusSwitch.click();
    }


    @Then("Check msg {string} and the outlet in the grid")
    public void checkMsgAndTheOutletInTheGrid(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        new P00_multiPurposes(driver).waitLoading();
        if (msg.contains("Successfully")) {
            WebElement selectedOutlet = outletsSetup.codes.stream().filter(c -> c.getText().equalsIgnoreCase(outletMap.get("code"))).findAny().get();
            asrt.assertTrue(outletsSetup.outletOPStatus(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("opState")));
            asrt.assertTrue(outletsSetup.outletName(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("name")));
            asrt.assertTrue(outletsSetup.outletCode(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("code")));
            asrt.assertTrue(outletsSetup.outletDescription(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("desc")));
            if (outletMap.get("state").equalsIgnoreCase("active"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-check"));
            else if (outletMap.get("state").equalsIgnoreCase("inactive"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-minus"));

        }
        asrt.assertAll();
    }

    @When("editing Outlet {string} opState {string} and code {string} and name {string} description {string} state {string}")
    public void editingOutletOpStateAndCodeAndNameDescription(String oName, String opState, String code, String nName, String desc, String state) {
        WebElement selectedOutlet = outletsSetup.names.stream().filter(n -> n.getText().equalsIgnoreCase(oName)).findAny().get();
       String outletStat="Active";
        if (outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-minus"))
            outletStat="Inactive";
        setOutletMap(outletsSetup.outletOPStatus(selectedOutlet).getText(), outletsSetup.outletCode(selectedOutlet).getText(), outletsSetup.outletName(selectedOutlet).getText(), outletsSetup.outletDescription(selectedOutlet).getText(),outletStat);
        outletsSetup.outletEditButton(selectedOutlet).click();
        new P00_multiPurposes(driver).waitLoading();
        fillOutletData(opState, code, nName, desc, state);
        outletsSetup.submitButton.click();

    }

    @When("Filtering With {string} as {string}")
    public void filteringWithAs(String filter, String value) {
        outletsSetup.filterButton.click();
        switch (filter.toLowerCase()) {
            case "status" ->
                    outletsSetup.statusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().get().click();
            case "name" -> outletsSetup.nameFilterField.sendKeys(value);
            case "code" -> outletsSetup.outletFilterCodeField.sendKeys(value);
            case "opstatus" ->
                    outletsSetup.opstatusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().get().click();
        }
        outletsSetup.searchFilterButton.click();
    }

    @Then("check all visible records {string} as {string}")
    public void checkAllVisibleRecordsAs(String filter, String value) {
        new P00_multiPurposes(driver).waitLoading();
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
        asrt.assertAll();
    }

    @When("deleting outlet {string}")
    public void deletingOutlet(String name) {
        WebElement selectedOutlet = outletsSetup.names.stream().filter(n -> n.getText().equalsIgnoreCase(name)).findAny().get();
        setOutletMap(outletsSetup.outletOPStatus(selectedOutlet).getText(), outletsSetup.outletCode(selectedOutlet).getText(), outletsSetup.outletName(selectedOutlet).getText(), outletsSetup.outletDescription(selectedOutlet).getText(), outletsSetup.outletStatus(selectedOutlet).getText());
        outletsSetup.outletDeleteButton(selectedOutlet).click();
        outletsSetup.popUpCOnfirmButton.click();
    }

    @Then("Check msg {string} and outlet {string} is deleted")
    public void checkOutletIsDeleted(String msg, String name) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully"))
            asrt.assertFalse(outletsSetup.names.stream().anyMatch(o -> o.getText().equalsIgnoreCase(name)));
        asrt.assertAll();
    }

    @Given("go to categories Page")
    public void goToCategoriesPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.outletsDropList.click();
        setupPage.categoriesLink.click();
    }

    @When("creating category {string} on outlet {string} with NTMP Categ as {string} and description {string}")
    public void creatingCategoryOnOutletWithNTMPCategAsAndDescription(String name, String outlet, String nTMP, String desc) {
        categories.newCategoryButton.click();
        fillCategData(outlet, nTMP, name, desc, "new");
        categories.submitButton.click();
    }

    HashMap<String, String> categMap = new HashMap<>();

    private void setCategMap(String outlet, String ntmp, String name, String desc, String state) {
        if (!outlet.isEmpty())
            categMap.put("outlet", outlet);
        if (!ntmp.isEmpty())
            categMap.put("ntmp", ntmp);
        if (!name.isEmpty())
            categMap.put("name", name);
        if (!desc.isEmpty())
            categMap.put("desc", desc);
        if (!state.isEmpty())
            categMap.put("state", state);
        //outletMap.putAll(Map.of("outlet", outlet, "ntmp", ntmp, "name", name, "desc", desc, "state", state));
    }

    private void fillCategData(String outlet, String ntmp, String name, String desc, String state) {
        if (!outlet.isEmpty())
            if (outlet.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", categories.clearOutletSelectionButton);
            else
                categories.outletsList().stream().filter(o -> o.getText().equalsIgnoreCase(outlet)).findFirst().get().click();
        if (!ntmp.isEmpty()) {
            if (ntmp.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", categories.clearNTMPSelectionButton);
            else
                categories.nTMPCategoriesList().stream().filter(o -> o.getText().equalsIgnoreCase(ntmp)).findFirst().get().click();
        }
        if (!name.isEmpty()) {
            categories.categoryNameField.clear();
            if (!name.equalsIgnoreCase("non"))
                categories.categoryNameField.sendKeys(name);
        }
        if (!desc.isEmpty()) {
            categories.descriptionField.clear();
            if (!desc.equalsIgnoreCase("non"))
                categories.descriptionField.sendKeys(desc);
        }

        setCategMap(outlet, ntmp, name, desc, state);
        if (state.equalsIgnoreCase("new"))
            categMap.put("state", "Active");
        else if ((categories.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (categories.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            categories.statusSwitch.click();
    }

    @Then("Check msg {string} and Categorey")
    public void checkMsgAndCategorey(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        new P00_multiPurposes(driver).waitLoading();
        if (msg.contains("Successfully")) {
            WebElement selectedCategory = categories.names.stream().filter(c -> c.getText().equalsIgnoreCase(categMap.get("name"))).findAny().get();
            asrt.assertTrue(categories.categoryOutlet(selectedCategory).getText().equalsIgnoreCase(categMap.get("outlet")));
            asrt.assertTrue(categories.categoryName(selectedCategory).getText().equalsIgnoreCase(categMap.get("name")));
            asrt.assertTrue(categories.categoryNTMP(selectedCategory).getText().equalsIgnoreCase(categMap.get("ntmp")));
            if (categMap.get("state").equalsIgnoreCase("active"))
                asrt.assertTrue(categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-check"));
            else if (categMap.get("state").equalsIgnoreCase("inactive"))
                asrt.assertTrue(categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-minus"));
            asrt.assertAll();
        }
    }

    @When("Filtering categs With {string} as {string}")
    public void filteringCategsWithAs(String filter, String value) {
        categories.filterButton.click();
        switch (filter.toLowerCase()) {
            case "status" ->
                    categories.statusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().get().click();
            case "name" -> categories.nameFilterField.sendKeys(value);
            case "ntmp" ->
                    categories.nTMPFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().get().click();
            case "outlet" ->
                    categories.filterOutletsList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().get().click();
        }
        categories.searchFilterButton.click();
    }

    @Then("check all visible categs records {string} as {string}")
    public void checkAllVisibleCategsRecordsAs(String filter, String value) {
        new P00_multiPurposes(driver).waitLoading();
        switch (filter.toLowerCase()) {
            case "status" -> {
                switch (value.toLowerCase()) {
                    case "active" ->
                            asrt.assertFalse(categories.statuses.stream().anyMatch(s -> !s.getAttribute("xlink:href").contains("icon-check")));
                    case "inactive" ->
                            asrt.assertFalse(categories.statuses.stream().anyMatch(s -> !s.getAttribute("xlink:href").contains("icon-minus")));
                }
            }
            case "name" ->
                    asrt.assertFalse(categories.names.stream().anyMatch(o -> !o.getText().equalsIgnoreCase(value)));
            case "outlet" ->
                    asrt.assertFalse(categories.outlets.stream().anyMatch(o -> !o.getText().equalsIgnoreCase(value)));
            case "ntmp" ->
                    asrt.assertFalse(categories.nTMPCateg.stream().anyMatch(o -> !o.getText().equalsIgnoreCase(value)));
        }
        asrt.assertAll();
    }

    @When("editing Category {string} outlet {string} and ntmp {string} and name {string} description {string} state {string}")
    public void editingCategoryOutletAndNtmpAndNameDescriptionState(String oName, String outlet, String ntmp, String name, String desc, String state) {
        WebElement selectedCategory = categories.names.stream().filter(c -> c.getText().equalsIgnoreCase(oName)).findAny().get();
        String catStat= "Active";
        if (categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-minus"))
            catStat="Inactive";
        setCategMap(categories.categoryOutlet(selectedCategory).getText(),categories.categoryNTMP(selectedCategory).getText(),categories.categoryName(selectedCategory).getText(),"",catStat);
        categories.categoryEditButton(selectedCategory).click();
        new P00_multiPurposes(driver).waitLoading();
        fillCategData(outlet,ntmp,name,desc,state);
        categories.submitButton.click();


    }

    @When("deleting category {string}")
    public void deletingCategory(String name) {
        WebElement selectedCategory = categories.names.stream().filter(c -> c.getText().equalsIgnoreCase(name)).findAny().get();
        String catStat= "Active";
        if (categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-minus"))
            catStat="Inactive";
        setCategMap(categories.categoryOutlet(selectedCategory).getText(),categories.categoryNTMP(selectedCategory).getText(),categories.categoryName(selectedCategory).getText(),"",catStat);
        categories.categoryDeleteButton(selectedCategory).click();
        categories.popUpCOnfirmButton.click();
    }

    @Then("Check msg {string} and category {string}")
    public void checkMsgAndCategory(String msg, String name) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully"))
            new P00_multiPurposes(driver).waitLoading();
            asrt.assertFalse(categories.names.stream().anyMatch(o -> o.getText().equalsIgnoreCase(name)));
        asrt.assertAll();
    }
}
