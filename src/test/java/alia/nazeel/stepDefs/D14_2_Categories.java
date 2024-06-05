package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.outlets.P31_OutletCategories;
import alia.nazeel.pojos.Category;
import alia.nazeel.tools.CustomAssert;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Map;


public class D14_2_Categories {

    final WebDriver driver = DriverManager.getDriver();

    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P31_OutletCategories categories = new P31_OutletCategories(driver);

    Category category;

    int newButtonClicks = 0;

    @Given("go to categories Page")
    public void goToCategoriesPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.outletsDropList.click();
        setupPage.categoriesLink.click();
    }


    @When("Checking the validity of required fields of Category {string}")
    public void checkingTheValidityOfRequiredFieldsOfCategoryCreation(String direction, DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            switch (direction.toLowerCase()) {
                case "creation" ->
                        creatingCategoryOnOutletWithNTMPCategAsAndDescription(columns.get("name"), columns.get("outlet"), columns.get("ntmp"), columns.get("desc"));
                case "editing" ->
                        editingCategoryOutletAndNtmpAndNameDescriptionState(columns.get("oName"), columns.get("outlet"), columns.get("ntmp"), columns.get("nName"), columns.get("desc"),columns.get("state"));
            }
            checkMsgAndCategorey(columns.get("msg"));
        }
    }

    @When("filtering ctegories with below table Check the filtered criteria for Categories")
    public void filteringCtegoriesWithBelowTableCheckTheFilteredCriteriaForCategories(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            filteringCategsWithAs(columns.get("filter"), columns.get("value"));
            checkAllVisibleCategsRecordsAs(columns.get("filter"), columns.get("value"));
        }
    }

    @When("deleting Categs and check cnstarins")
    public void deletingOutletsAndCheckCnstarins(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            deletingCategory(columns.get("categ"));
            checkMsgAndCategoryDeletion(columns.get("msg"), columns.get("categ"));
        }
    }

    @Then("Check validity of Category fields and Category")
    public void checkValidityOfCategoryFieldsAndCategory() {
        asrt.assertAll();
    }

    @Then("check the Search Criteria Validity")
    public void checkTheSearchCriteriaValidity() {
        asrt.assertAll();
    }

    @Then("Check categs deletion Constrains validity")
    public void checkCategsDeletionConstrainsValidity() {
        asrt.assertAll();
    }

    public void creatingCategoryOnOutletWithNTMPCategAsAndDescription(String name, String outlet, String nTMP, String desc) {
        if (newButtonClicks == 0) {
            categories.newCategoryButton.click();
            newButtonClicks++;
        }
        fillCategData(outlet, nTMP, name, desc, "new");
        categories.submitButton.click();
    }


    private void fillCategData(String outlet, String ntmp, String name, String desc, String state) {
        if (!Utils.isEmptyOrNull(outlet)) {
            if (outlet.equalsIgnoreCase("non"))
                categories.outletsComboBox.clearSelection();
            else
                categories.outletsComboBox.selectByTextContainsIgnoreCase(outlet);
        }
        if (!Utils.isEmptyOrNull(ntmp)) {
            if (ntmp.equalsIgnoreCase("non"))
                categories.ntmpCategCmboBox.clearSelection();
            else
                categories.ntmpCategCmboBox.selectByTextContainsIgnoreCase(ntmp);
        }
        if (!Utils.isEmptyOrNull(name)) {
            categories.categoryNameField.clear();
            if (!name.equalsIgnoreCase("non"))
                categories.categoryNameField.sendKeys(name, name);
        }
        if (!Utils.isEmptyOrNull(desc)) {
            categories.descriptionField.clear();
            if (!desc.equalsIgnoreCase("non"))
                categories.descriptionField.sendKeys(desc);
        }
        category = new Category(name, desc, state, outlet, ntmp);

        if (state.equalsIgnoreCase("new"))
            category.setState("Active");
        else if ((categories.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (categories.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            categories.statusSwitch.click();
    }


    public void checkMsgAndCategorey(String msg) {
        asrt.AssertToastMessagesContains(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            WebElement selectedCategory = categories.names.stream().filter(c -> c.getText().equalsIgnoreCase(category.getName())).findAny().orElseThrow();
            asrt.assertTrue(categories.categoryOutlet(selectedCategory).getText().equalsIgnoreCase(category.getOutlet()));
            asrt.assertTrue(categories.categoryName(selectedCategory).getText().equalsIgnoreCase(category.getName()));
            asrt.assertTrue(categories.categoryNTMP(selectedCategory).getText().equalsIgnoreCase(category.getNtmpCateg()));
            if (category.getState().equalsIgnoreCase("active"))
                asrt.assertTrue(categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-check"));
            else if (category.getState().equalsIgnoreCase("inactive"))
                asrt.assertTrue(categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-minus"));
        } else {
            clearCategoryData();
        }
    }


    public void filteringCategsWithAs(String filter, String value) {
        if (newButtonClicks == 0) {
            categories.filterButton.click();
            newButtonClicks++;
        }
        switch (filter.toLowerCase()) {
            case "status" -> categories.statusFiletr.selectByTextContainsIgnoreCase(value);
            case "name" -> categories.nameFilterField.sendKeys(value);
            case "ntmp" -> categories.ntmpFilter.selectByTextContainsIgnoreCase(value);
            case "outlet" -> categories.outletsFilter.selectByTextContainsIgnoreCase(value);
        }
        categories.searchFilterButton.click();
    }


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
        clearCategFilters();
    }

    private void clearCategFilters() {
        categories.outletsFilter.clearSelection();
        categories.ntmpFilter.clearSelection();
        categories.nameFilterField.clear();
        categories.statusFiletr.clearSelection();
    }


    public void editingCategoryOutletAndNtmpAndNameDescriptionState(String oName, String outlet, String ntmp, String name, String desc, String state) {
        if (newButtonClicks == 0) {
            WebElement selectedCategory = extractCateg(oName);
            categories.categoryEditButton(selectedCategory).click();
            wait.waitLoading();
            newButtonClicks++;
        }
        fillCategData(outlet, ntmp, name, desc, state);
        categories.submitButton.click();


    }

    private WebElement extractCateg(String categName) {
        WebElement selectedCategory = categories.names.stream().filter(c -> c.getText().equalsIgnoreCase(categName)).findAny().orElseThrow();
        String catStat = "Active";
        if (categories.categoryStatus(selectedCategory).getAttribute("xlink:href").contains("icon-minus"))
            catStat = "Inactive";
        category = new Category(categories.categoryName(selectedCategory).getText(), "", catStat, categories.categoryOutlet(selectedCategory).getText(), categories.categoryNTMP(selectedCategory).getText());
        return selectedCategory;
    }


    public void deletingCategory(String name) {
        WebElement selectedCategory = extractCateg(name);
        categories.categoryDeleteButton(selectedCategory).click();
        categories.popUpCOnfirmButton.click();
    }


    public void checkMsgAndCategoryDeletion(String msg, String name) {
        wait.waitLoading();
        asrt.AssertToastMessagesContains(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            asrt.assertFalse(categories.names.stream().anyMatch(o -> o.getText().equalsIgnoreCase(name)));
        }
    }

    private void clearCategoryData() {
        categories.outletsComboBox.clearSelection();
        categories.ntmpCategCmboBox.clearSelection();
        categories.categoryNameField.clear();
        categories.descriptionField.clear();
    }

}
