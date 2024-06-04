package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.outlets.P31_OutletCategories;
import alia.nazeel.tools.CustomAssert;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.HashMap;

public class D14_2_Categories {

    final WebDriver driver = DriverManager.getDriver();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    //Actions actions = new Actions(driver);
    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P31_OutletCategories categories = new P31_OutletCategories(driver);

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

    final HashMap<String, String> categMap = new HashMap<>();

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
        if (!outlet.isEmpty()) {
            if (outlet.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", categories.clearOutletSelectionButton);
            else
                categories.outletsList().stream().filter(o -> o.getText().equalsIgnoreCase(outlet)).findFirst().orElseThrow().click();
        }
        if (!ntmp.isEmpty()) {
            if (ntmp.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", categories.clearNTMPSelectionButton);
            else
                categories.nTMPCategoriesList().stream().filter(o -> o.getText().equalsIgnoreCase(ntmp)).findFirst().orElseThrow().click();
        }
        if (!name.isEmpty()) {
            categories.categoryNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            new P00_multiPurposes(driver).secondLanguageField(categories.categoryNameField).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
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

        if (msg.contains("Successfully")) {
            wait.waitLoading();
            WebElement selectedCategory = categories.names.stream().filter(c -> c.getText().equalsIgnoreCase(categMap.get("name"))).findAny().orElseThrow();
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
                    categories.statusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "name" -> categories.nameFilterField.sendKeys(value);
            case "ntmp" ->
                    categories.nTMPFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "outlet" ->
                    categories.filterOutletsList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
        }
        categories.searchFilterButton.click();
    }

    @Then("check all visible categs records {string} as {string}")
    public void checkAllVisibleCategsRecordsAs(String filter, String value) {
        wait.waitLoading();
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
        WebElement selectedCategory = extractCateg(oName);
        categories.categoryEditButton(selectedCategory).click();
        wait.waitLoading();
        fillCategData(outlet, ntmp, name, desc, state);
        categories.submitButton.click();


    }

    private WebElement extractCateg(String categName) {
        WebElement selectedCategory = categories.names.stream().filter(c -> c.getText().equalsIgnoreCase(categName)).findAny().orElseThrow();
        String catStat = "Active";
        if (categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-minus"))
            catStat = "Inactive";
        setCategMap(categories.categoryOutlet(selectedCategory).getText(), categories.categoryNTMP(selectedCategory).getText(), categories.categoryName(selectedCategory).getText(), "", catStat);
        return selectedCategory;
    }

    @When("deleting category {string}")
    public void deletingCategory(String name) {
        WebElement selectedCategory = extractCateg(name);
        categories.categoryDeleteButton(selectedCategory).click();
        categories.popUpCOnfirmButton.click();
    }

    @Then("Check msg {string} and category {string}")
    public void checkMsgAndCategory(String msg, String name) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            asrt.assertFalse(categories.names.stream().anyMatch(o -> o.getText().equalsIgnoreCase(name)));
            asrt.assertAll();
        }
    }
}
