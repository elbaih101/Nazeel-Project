package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import alia.nazeel.pages.setuppages.outlets.P32_OutletItems;
import alia.nazeel.tools.CustomAssert;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.HashMap;

public class D14_3_Items {

    final WebDriver driver = DriverManager.getDriver();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    //Actions actions = new Actions(driver);
    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);
    final P32_OutletItems items = new P32_OutletItems(driver);



    @Given("go to items setup")
    public void goToItemsSetup() {
        wait.waitLoading();
        dashBoardPage.setupPageLink.click();
        wait.waitLoading();
        setupPage.outletsDropList.click();
        wait.waitLoading();
        setupPage.itemsLink.click();
    }

    final HashMap<String, String> itemMap = new HashMap<>();

    private void setItemMap(String name, String type, String outlet, String categ, String desc, String price, String tax, String state) {
        if (!outlet.isEmpty())
            itemMap.put("outlet", outlet);
        if (!type.isEmpty())
            itemMap.put("type", type);
        if (!categ.isEmpty())
            itemMap.put("categ", categ);
        if (!name.isEmpty())
            itemMap.put("name", name);
        if (!desc.isEmpty())
            itemMap.put("desc", desc);
        if (!state.isEmpty())
            itemMap.put("state", state);
        if (!price.isEmpty())
            itemMap.put("price", price);
        if (!tax.isEmpty())
            itemMap.put("tax", tax);
        //outletMap.putAll(Map.of("outlet", outlet, "ntmp", ntmp, "name", name, "desc", desc, "state", state));
    }

    private void fillItemData(String name, String type, String outlet, String categ, String desc, String price, String tax, String state) {
        if (!outlet.isEmpty())
            if (outlet.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", items.clearOutletSelectionButton);
            else
                //Fixme no such element exception  java
                items.outletsList().stream().filter(o -> o.getText().equalsIgnoreCase(outlet)).findFirst().orElseThrow().click();
        if (!categ.isEmpty()) {
            if (categ.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", items.clearCategorySelectionButton);
            else
                items.categoriesList().stream().filter(o -> o.getText().equalsIgnoreCase(categ)).findFirst().orElseThrow().click();
        }
        if (!type.isEmpty()) {
            if (type.equalsIgnoreCase("non"))
                js.executeScript("arguments[0].click();", items.clearTypeSelectionButton);
            else
                items.itemTypesList().stream().filter(o -> o.getText().equalsIgnoreCase(type)).findFirst().orElseThrow().click();
        }
        if (!name.isEmpty()) {
            items.itemNameField.clear();
            new P00_multiPurposes(driver).secondLanguageField(items.itemNameField).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!name.equalsIgnoreCase("non"))
                items.itemNameField.sendKeys(name);
        }
        switch (price.toLowerCase()) {
            case "free" -> {
                if (items.freeItemSwitch.getAttribute("class").contains("k-switch-off"))
                    items.freeItemSwitch.click();
            }
            case "userdefined" -> {
                if (items.userDefinedPriceSwitch.getAttribute("class").contains("k-switch-off"))
                    items.userDefinedPriceSwitch.click();
                items.priceInput_FilterField.clear();
                price = "100";
                items.priceInput_FilterField.sendKeys(price);
            }
            case "non" -> {
                if (items.freeItemSwitch.getAttribute("class").contains("k-switch-on"))
                    items.freeItemSwitch.click();
                if (items.userDefinedPriceSwitch.getAttribute("class").contains("k-switch-on"))
                    items.userDefinedPriceSwitch.click();
                items.priceInput_FilterField.clear();
            }
            default -> {
                if (items.freeItemSwitch.getAttribute("class").contains("k-switch-on"))
                    items.freeItemSwitch.click();
                if (items.userDefinedPriceSwitch.getAttribute("class").contains("k-switch-on"))
                    items.userDefinedPriceSwitch.click();
                items.priceInput_FilterField.clear();
                items.priceInput_FilterField.sendKeys(price);
            }

        }
        switch (tax.toLowerCase()) {
            case "applied" -> {
                if (items.taxExemptedSwitch.getAttribute("class").contains("k-switch-on"))
                    items.taxExemptedSwitch.click();
            }
            case "exempted" -> {
                if (items.taxExemptedSwitch.getAttribute("class").contains("k-switch-off"))
                    items.taxExemptedSwitch.click();
            }
        }
        if (!desc.isEmpty()) {
            items.descriptionField.clear();
            if (!desc.equalsIgnoreCase("non"))
                items.descriptionField.sendKeys(desc);
        }

        setItemMap(name, type, outlet, categ, desc, price, tax, state);
        if (state.equalsIgnoreCase("new"))
            itemMap.put("state", "Active");
        else if ((items.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (items.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            items.statusSwitch.click();
    }

    @When("creating item with name {string} and type {string} and outlet {string} and category {string} description {string} price {string} taxstate {string}")
    public void creatingItemWithNameAndTypeAndOutletAndCategoryDescription(String name, String type, String outlet, String categ, String desc, String price, String tax) {
        items.newItemButton.click();
        fillItemData(name, type, outlet, categ, desc, price, tax, "new");
        if (price.equalsIgnoreCase("free"))
            asrt.assertFalse(Utils.isEnabled(items.priceInput_FilterField), "the price field was not disabled ");
        if (price.equalsIgnoreCase("userdefined"))
            asrt.assertTrue(Utils.isEnabled(items.priceInput_FilterField), "the price field was not disabled ");
        items.submitButton.click();
        asrt.assertAll();
    }

    @Then("Check msg {string} and the item")
    public void checkMsgAndTheItem(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);

        if (msg.contains("Successfully")) {
            wait.waitLoading();
            WebElement selectedItem = items.names.stream().filter(c -> c.getText().equalsIgnoreCase(itemMap.get("name"))).findAny().orElseThrow();
            asrt.assertTrue(items.itemOutlet(selectedItem).getText().equalsIgnoreCase(itemMap.get("outlet")));
            asrt.assertTrue(items.itemName(selectedItem).getText().equalsIgnoreCase(itemMap.get("name")));
            asrt.assertTrue(items.itemCategory(selectedItem).getText().equalsIgnoreCase(itemMap.get("categ")));
            switch (itemMap.get("price")) {
                case "free", "userdefined" ->
                        asrt.assertTrue(items.itemPrice(selectedItem).getText().equalsIgnoreCase("0"));
                default ->
                        asrt.assertTrue(items.itemPrice(selectedItem).getText().equalsIgnoreCase(itemMap.get("price")));
            }

            if (itemMap.get("state").equalsIgnoreCase("active"))
                asrt.assertTrue(items.itemStatus(selectedItem).getAttribute("xlink:href").contains("icon-check"));
            else if (itemMap.get("state").equalsIgnoreCase("inactive"))
                asrt.assertTrue(items.itemStatus(selectedItem).getAttribute("xlink:href").contains("icon-minus"));
            asrt.assertAll();
        }

    }


    @When("editing item {string} name {string} and type {string} and outlet {string} and category {string} description {string} price {string} taxstate {string} state {string}")
    public void editingItemNameAndTypeAndOutletAndCategoryDescriptionPriceTaxstate(String oName, String nName, String type, String outlet, String categ, String desc, String price, String tax, String state) {
        WebElement selectedItem = extractItem(oName);
        items.itemEditButton(selectedItem).click();
        wait.waitLoading();
        fillItemData(nName, type, outlet, categ, desc, price, tax, state);
        items.submitButton.click();
    }

    private WebElement extractItem(String itemName) {
        WebElement selectedItem = items.names.stream().filter(i -> i.getText().equalsIgnoreCase(itemName)).findAny().orElseThrow();
        String itemStat = "Active";
        if (items.itemStatus(selectedItem).getAttribute("xlink:href").contains("icon-minus"))
            itemStat = "Inactive";
        setItemMap(items.itemName(selectedItem).getText(), "", items.itemOutlet(selectedItem).getText(), items.itemCategory(selectedItem).getText(), "", items.itemPrice(selectedItem).getText(), "", itemStat);
        return selectedItem;
    }

    @When("Filter Items With {string} as {string}")
    public void filterItemsWithAs(String filter, String value) {
        wait.waitLoading();
        items.filterButton.click();
        switch (filter.toLowerCase()) {
            case "status" ->
                    items.statusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "name" -> items.nameFilterField.sendKeys(value);
            case "price" -> items.priceInput_FilterField.sendKeys(value);
            case "outlet" ->
                    items.filterOutletsList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "category" -> {
                items.filterOutletsList().stream().filter(s -> s.getText().equalsIgnoreCase(StringUtils.substringBefore(value, " -"))).findAny().orElseThrow().click();
                items.categoryFilterList().stream().filter(c -> c.getText().equalsIgnoreCase(StringUtils.substringAfter(value, "- "))).findAny().orElseThrow().click();

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
        asrt.assertAll();
    }

    @When("deleting item {string}")
    public void deletingItem(String item) {
        WebElement seletedItem = extractItem(item);
        items.itemDeleteButton(seletedItem).click();
        items.popUpCOnfirmButton.click();
    }

    @Then("Check msg {string} and item {string}")
    public void checkMsgAndItem(String msg, String item) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            wait.waitLoading();
            asrt.assertFalse(items.names.stream().anyMatch(i -> i.getText().equalsIgnoreCase(item)));
            asrt.assertAll();
        }
    }

}
