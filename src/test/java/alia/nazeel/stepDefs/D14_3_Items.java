package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.outlets.P32_OutletItems;
import alia.nazeel.pojos.Item;
import alia.nazeel.tools.CustomAssert;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class D14_3_Items {

    final WebDriver driver = DriverManager.getDriver();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    //Actions actions = new Actions(driver);
    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P32_OutletItems items = new P32_OutletItems(driver);

    Item item;
    int newButtonClicks = 0;

    @Given("go to items setup")
    public void goToItemsSetup() {
        wait.waitLoading();
        dashBoardPage.setupPageLink.click();
        wait.waitLoading();
        setupPage.outletsDropList.click();
        wait.waitLoading();
        setupPage.itemsLink.click();
    }

    @When("validating the Fields in Item {string}")
    public void validatingTheFieldsInItem(String direction, DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            switch (direction.toLowerCase()) {
                case "creation" ->
                        creatingItemWithNameAndTypeAndOutletAndCategoryDescription(columns.get("name"), columns.get("type"), columns.get("outlet"), columns.get("categ"), columns.get("desc"), columns.get("price"), columns.get("tax"));
                case "editing" ->
                        editingItemNameAndTypeAndOutletAndCategoryDescriptionPriceTaxstate(columns.get("oName"), columns.get("nName"), columns.get("type"), columns.get("outlet"), columns.get("categ"), columns.get("desc"), columns.get("price"), columns.get("tax"), columns.get("state"));
            }
            checkMsgAndTheItem(columns.get("msg"));
        }
    }


    @When("filtering Items with below table Check the filtered criteria for Categories")
    public void filteringItemsWithBelowTableCheckTheFilteredCriteriaForCategories(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            filterItemsWithAs(columns.get("filter"), columns.get("value"));
            checkAllItemsRecordsAs(columns.get("filter"), columns.get("value"));
        }
    }

    @Then("assert theValidity of theFields and the Item")
    public void assertTheValidityOfTheFieldsAndTheItem() {
        asrt.assertAll();
    }

    @Then("Check the Validity of Items Search Criteria")
    public void checkTheValidityOfItemsSearchCriteria() {
        asrt.assertAll();
    }

    private void fillItemData(String name, String type, String outlet, String categ, String desc, String price, String tax, String state) {
        if (!Utils.isEmptyOrNull(outlet))
            if (outlet.equalsIgnoreCase("non"))
                items.outletComboBox.clearSelection();
            else {
                items.outletComboBox.selectByTextContainsIgnoreCase(outlet);
            }
        if (!Utils.isEmptyOrNull(categ)) {
            if (categ.equalsIgnoreCase("non"))
                items.categoryComboBox.clearSelection();
            else
                try {
                    items.categoryComboBox.selectByTextContainsIgnoreCase(categ);
                } catch (ElementClickInterceptedException e) {
                    items.swalpopUp.confirm();
                    wait.waitLoading();
                    items.categoryComboBox.selectByTextContainsIgnoreCase(categ);
                }
        }
        if (!Utils.isEmptyOrNull(type)) {
            if (type.equalsIgnoreCase("non"))
                items.typeComboBox.clearSelection();
            else
                items.typeComboBox.selectByTextContainsIgnoreCase(type);
        }
        if (!Utils.isEmptyOrNull(name)) {
            items.itemNameField.clear();
            if (!name.equalsIgnoreCase("non"))
                items.itemNameField.sendKeys(name, name);
        }
        if (price == null)
            price = "";
        switch (price.toLowerCase()) {
            case "free" -> items.freeItemSwitch.switchOn();

            case "userdefined" -> {
                items.userDefinedPriceSwitch.switchOn();
                items.priceInput_FilterField.clear();
                price = "100";
                items.priceInput_FilterField.sendKeys(price);
            }
            case "non" -> {
                items.freeItemSwitch.switchOf();
                items.userDefinedPriceSwitch.switchOf();
                items.priceInput_FilterField.clear();
            }
            default -> {
                items.freeItemSwitch.switchOf();
                items.userDefinedPriceSwitch.switchOf();
                items.priceInput_FilterField.clear();
                items.priceInput_FilterField.sendKeys(price);
            }
        }
        if (tax == null)
            tax = "";
        switch (tax.toLowerCase()) {
            case "applied" -> items.taxExemptedSwitch.switchOf();

            case "exempted" -> items.taxExemptedSwitch.switchOn();

        }
        if (!Utils.isEmptyOrNull(desc)) {
            items.descriptionField.clear();
            if (!desc.equalsIgnoreCase("non"))
                items.descriptionField.sendKeys(desc);
        }

        item = new Item(outlet, type, categ, name, desc, state, price, tax);
        if (state.equalsIgnoreCase("new"))
            item.setState("Active");
        else if ((!items.statusSwitch.isOn() && state.equalsIgnoreCase("active")) || (items.statusSwitch.isOn() && state.equalsIgnoreCase("inactive")))
            items.statusSwitch.click();
    }

    @When("creating item with name {string} and type {string} and outlet {string} and category {string} description {string} price {string} taxstate {string}")
    public void creatingItemWithNameAndTypeAndOutletAndCategoryDescription(String name, String type, String outlet, String categ, String desc, String price, String tax) {
        if (newButtonClicks == 0) {
            items.newItemButton.click();
            newButtonClicks++;
        }
        fillItemData(name, type, outlet, categ, desc, price, tax, "new");
        if (!Utils.isEmptyOrNull(price)) {
            if (price.equalsIgnoreCase("free"))
                asrt.assertFalse(Utils.isEnabled(items.priceInput_FilterField), "the price field was not disabled ");
            if (price.equalsIgnoreCase("userdefined"))
                asrt.assertTrue(Utils.isEnabled(items.priceInput_FilterField), "the price field was not disabled ");
        }
        items.submitButton.click();
        asrt.assertAll();
    }

    @Then("Check msg {string} and the item")
    public void checkMsgAndTheItem(String msg) {
        asrt.AssertToastMessagesContains(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            WebElement selectedItem = items.names.stream().filter(c -> c.getText().equalsIgnoreCase(item.getName())).findAny().orElseThrow();
            asrt.assertTrue(items.itemOutlet(selectedItem).getText().equalsIgnoreCase(item.getOutlet()));
            asrt.assertTrue(items.itemName(selectedItem).getText().equalsIgnoreCase(item.getName()));
            asrt.assertTrue(items.itemCategory(selectedItem).getText().equalsIgnoreCase(item.getCategory()));
            switch (item.getPrice()) {
                case "free", "userdefined" ->
                        asrt.assertTrue(items.itemPrice(selectedItem).getText().equalsIgnoreCase("0"));
                default -> asrt.assertTrue(items.itemPrice(selectedItem).getText().equalsIgnoreCase(item.getPrice()));
            }

            if (item.getState().equalsIgnoreCase("active"))
                asrt.assertTrue(items.itemStatus(selectedItem).getAttribute("xlink:href").contains("icon-check"));
            else if (item.getState().equalsIgnoreCase("inactive"))
                asrt.assertTrue(items.itemStatus(selectedItem).getAttribute("xlink:href").contains("icon-minus"));
        } else {
            clearItemData();
        }
    }

    private void clearItemData() {
        items.itemNameField.clear();
        items.categoryComboBox.clearSelection();
        items.outletComboBox.clearSelection();
        items.typeComboBox.clearSelection();
        items.freeItemSwitch.switchOf();
        items.userDefinedPriceSwitch.switchOf();
        items.taxExemptedSwitch.switchOf();
        items.priceInput_FilterField.clear();
    }


    @When("editing item {string} name {string} and type {string} and outlet {string} and category {string} description {string} price {string} taxstate {string} state {string}")
    public void editingItemNameAndTypeAndOutletAndCategoryDescriptionPriceTaxstate(String oName, String nName, String type, String outlet, String categ, String desc, String price, String tax, String state) {
        if (newButtonClicks == 0) {
            WebElement selectedItem = extractItem(oName);
            items.itemEditButton(selectedItem).click();
            wait.waitLoading();
            newButtonClicks++;
        }
        fillItemData(nName, type, outlet, categ, desc, price, tax, state);
        items.submitButton.click();
    }

    private WebElement extractItem(String itemName) {
        WebElement selectedItem = items.names.stream().filter(i -> i.getText().equalsIgnoreCase(itemName)).findAny().orElseThrow();
        String itemStat = "Active";
        if (items.itemStatus(selectedItem).getAttribute("xlink:href").contains("icon-minus"))
            itemStat = "Inactive";
        item = new Item(items.itemOutlet(selectedItem).getText(), "", items.itemCategory(selectedItem).getText(), items.itemName(selectedItem).getText(), "", itemStat, items.itemPrice(selectedItem).getText(), "");
        return selectedItem;
    }

    @When("Filter Items With {string} as {string}")
    public void filterItemsWithAs(String filter, String value) {

        if (newButtonClicks == 0) {
            wait.waitLoading();
            items.filterButton.click();
            newButtonClicks++;
        }
        switch (filter.toLowerCase()) {
            case "status" -> items.statusFilter.selectByTextContainsIgnoreCase(value);
            case "name" -> items.nameFilterField.sendKeys(value);
            case "price" -> items.priceInput_FilterField.sendKeys(value);
            case "outlet" -> items.outletFilter.selectByTextContainsIgnoreCase(value);
            case "category" -> {
                items.outletFilter.selectByTextContainsIgnoreCase(StringUtils.substringBefore(value, " -"));
                items.categoryFilter.selectByTextContainsIgnoreCase(StringUtils.substringAfter(value, "- "));
            }
        }
        items.searchFilterButton.click();
    }

    @Then("Check all items records {string} as {string}")
    public void checkAllItemsRecordsAs(String filter, String value) {
        wait.waitLoading();
        switch (filter) {
            case "status" -> {
                switch (value.toLowerCase()) {
                    case "active" ->
                            asrt.assertFalse(items.statuses.stream().anyMatch(s -> !s.getAttribute("xlink:href").contains("icon-minus")));
                    case "inactive" ->
                            asrt.assertFalse(items.statuses.stream().anyMatch(s -> !s.getAttribute("xlink:href").contains("icon-check")));
                }
            }
            case "name" -> asrt.assertFalse(items.names.stream().anyMatch(n -> !n.getText().contains(value)));
            case "price" -> asrt.assertFalse(items.prices.stream().anyMatch(p -> !p.getText().equalsIgnoreCase(value)));
            case "outlet" ->
                    asrt.assertFalse(items.outlets.stream().anyMatch(p -> !p.getText().equalsIgnoreCase(value)));
            case "catehory" -> {
                asrt.assertFalse(items.outlets.stream().anyMatch(p -> !p.getText().equalsIgnoreCase(StringUtils.substringBefore(value, " -"))));
                asrt.assertFalse(items.categories.stream().anyMatch(p -> !p.getText().equalsIgnoreCase(StringUtils.substringAfter(value, "- "))));
            }
        }
        clearItemFilters();
    }

    private void clearItemFilters() {
        items.outletFilter.clearSelection();
        items.categoryFilter.clearSelection();
        items.priceInput_FilterField.clear();
        items.statusFilter.clearSelection();
        items.nameFilterField.clear();
    }

    @When("deleting item {string}")
    public void deletingItem(String item) {
        WebElement seletedItem = extractItem(item);
        items.itemDeleteButton(seletedItem).click();
        items.swalpopUp.confirm();
    }

    @Then("Check msg {string} and item {string}")
    public void checkMsgAndItem(String msg, String item) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            asrt.assertFalse(items.names.stream().anyMatch(i -> i.getText().equalsIgnoreCase(item)));
        }
    }

}
