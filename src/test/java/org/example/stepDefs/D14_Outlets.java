package org.example.stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.example.Nazeel_Calculations;
import org.example.Utils;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P38_Outlets;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.setuppages.P05_SetupPage;
import org.example.pages.setuppages.outlets.P30_OutletsSetup;
import org.example.pages.setuppages.outlets.P31_OutletCategories;
import org.example.pages.setuppages.outlets.P32_OutletItems;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

//import java.time.Duration;
import java.util.HashMap;

public class D14_Outlets {
    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    //Actions actions = new Actions(driver);
    final SoftAssert asrt = new SoftAssert();
    //  final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPage = new P05_SetupPage(driver);
    P30_OutletsSetup outletsSetup = new P30_OutletsSetup(driver);
    P31_OutletCategories categories = new P31_OutletCategories(driver);
    P32_OutletItems items = new P32_OutletItems(driver);
    P38_Outlets outlets = new P38_Outlets(driver);

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
                outletsSetup.opStatusesList().stream().filter(o -> o.getText().equalsIgnoreCase(opState)).findFirst().orElseThrow().click();
        if (!code.isEmpty()) {
            outletsSetup.outletCodeField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!code.equalsIgnoreCase("non"))
                outletsSetup.outletCodeField.sendKeys(code);
        }
        if (!name.isEmpty()) {
            outletsSetup.outletNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
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

        if (msg.contains("Successfully")) {
            new P00_multiPurposes(driver).waitLoading();
            WebElement selectedOutlet = outletsSetup.codes.stream().filter(c -> c.getText().equalsIgnoreCase(outletMap.get("code"))).findAny().orElseThrow();
            asrt.assertTrue(outletsSetup.outletOPStatus(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("opState")));
            asrt.assertTrue(outletsSetup.outletName(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("name")));
            asrt.assertTrue(outletsSetup.outletCode(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("code")));
            asrt.assertTrue(outletsSetup.outletDescription(selectedOutlet).getText().equalsIgnoreCase(outletMap.get("desc")));
            if (outletMap.get("state").equalsIgnoreCase("active"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-check"));
            else if (outletMap.get("state").equalsIgnoreCase("inactive"))
                asrt.assertTrue(outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-minus"));
            asrt.assertAll();
        }

    }

    @When("editing Outlet {string} opState {string} and code {string} and name {string} description {string} state {string}")
    public void editingOutletOpStateAndCodeAndNameDescription(String oName, String opState, String code, String nName, String desc, String state) {
        WebElement selectedOutlet = extractOutlet(oName);
        outletsSetup.outletEditButton(selectedOutlet).click();
        new P00_multiPurposes(driver).waitLoading();
        fillOutletData(opState, code, nName, desc, state);
        outletsSetup.submitButton.click();

    }

    private WebElement extractOutlet(String outletName) {
        WebElement selectedOutlet = outletsSetup.names.stream().filter(n -> n.getText().equalsIgnoreCase(outletName)).findAny().orElseThrow();
        String outletStat = "Active";
        if (outletsSetup.outletStatus(selectedOutlet).getAttribute("xlink:href").contains("icon-minus"))
            outletStat = "Inactive";
        setOutletMap(outletsSetup.outletOPStatus(selectedOutlet).getText(), outletsSetup.outletCode(selectedOutlet).getText(), outletsSetup.outletName(selectedOutlet).getText(), outletsSetup.outletDescription(selectedOutlet).getText(), outletStat);
        return selectedOutlet;
    }

    @When("Filtering With {string} as {string}")
    public void filteringWithAs(String filter, String value) {
        outletsSetup.filterButton.click();
        switch (filter.toLowerCase()) {
            case "status" ->
                    outletsSetup.statusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
            case "name" -> {
                outletsSetup.nameFilterField.click();
                outletsSetup.nameFilterField.sendKeys(value);
            }
            case "code" -> outletsSetup.outletFilterCodeField.sendKeys(value);
            case "opstatus" ->
                    outletsSetup.opstatusesFilterList().stream().filter(s -> s.getText().equalsIgnoreCase(value)).findAny().orElseThrow().click();
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
        WebElement selectedOutlet = extractOutlet(name);
        outletsSetup.outletDeleteButton(selectedOutlet).click();
        outletsSetup.popUpCOnfirmButton.click();
    }

    @Then("Check msg {string} and outlet {string} is deleted")
    public void checkOutletIsDeleted(String msg, String name) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            new P00_multiPurposes(driver).waitLoading();
            asrt.assertFalse(outletsSetup.names.stream().anyMatch(o -> o.getText().equalsIgnoreCase(name)));
            asrt.assertAll();
        }
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
                categories.outletsList().stream().filter(o -> o.getText().equalsIgnoreCase(outlet)).findFirst().orElseThrow().click();
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
            new P00_multiPurposes(driver).waitLoading();
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
        WebElement selectedCategory = extractCateg(oName);
        categories.categoryEditButton(selectedCategory).click();
        new P00_multiPurposes(driver).waitLoading();
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
            new P00_multiPurposes(driver).waitLoading();
            asrt.assertFalse(categories.names.stream().anyMatch(o -> o.getText().equalsIgnoreCase(name)));
            asrt.assertAll();
        }
    }

    @Given("go to items setup")
    public void goToItemsSetup() {
        dashBoardPage.setupPageLink.click();
        setupPage.outletsDropList.click();
        setupPage.itemsLink.click();
    }

    HashMap<String, String> itemMap = new HashMap<>();

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
            new P00_multiPurposes(driver).secondLanguageField(items.itemNameField).sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
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
            categories.descriptionField.clear();
            if (!desc.equalsIgnoreCase("non"))
                categories.descriptionField.sendKeys(desc);
        }

        setItemMap(name, type, outlet, categ, desc, price, tax, state);
        if (state.equalsIgnoreCase("new"))
            categMap.put("state", "Active");
        else if ((categories.statusSwitch.getAttribute("class").contains("k-switch-off") && state.equalsIgnoreCase("active")) || (categories.statusSwitch.getAttribute("class").contains("k-switch-on") && state.equalsIgnoreCase("inactive")))
            categories.statusSwitch.click();
    }

    @When("creating item with name {string} and type {string} and outlet {string} and category {string} description {string} price {string} taxstate {string}")
    public void creatingItemWithNameAndTypeAndOutletAndCategoryDescription(String name, String type, String outlet, String categ, String desc, String price, String tax) {
        items.newItemButton.click();
        fillItemData(name, type, outlet, categ, desc, price, tax, "new");
        if (price.equalsIgnoreCase("userdefined") || price.equalsIgnoreCase("free"))
            asrt.assertFalse(Utils.isEnabled(items.priceInput_FilterField), "the price field was not disabled ");
        items.submitButton.click();
        asrt.assertAll();
    }

    @Then("Check msg {string} and the item")
    public void checkMsgAndTheItem(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);

        if (msg.contains("Successfully")) {
            new P00_multiPurposes(driver).waitLoading();
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
                asrt.assertTrue(categories.categoryStatus(selectedItem).getAttribute("xlink:href").contains("icon-check"));
            else if (itemMap.get("state").equalsIgnoreCase("inactive"))
                asrt.assertTrue(categories.categoryStatus(selectedItem).getAttribute("xlink:href").contains("icon-minus"));
            asrt.assertAll();
        }

    }


    @When("editing item {string} name {string} and type {string} and outlet {string} and category {string} description {string} price {string} taxstate {string} state {string}")
    public void editingItemNameAndTypeAndOutletAndCategoryDescriptionPriceTaxstate(String oName, String nName, String type, String outlet, String categ, String desc, String price, String tax, String state) {
        WebElement selectedItem = extractItem(oName);
        items.itemEditButton(selectedItem).click();
        new P00_multiPurposes(driver).waitLoading();
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
        new P00_multiPurposes(driver).waitLoading();
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
            new P00_multiPurposes(driver).waitLoading();
            asrt.assertFalse(items.names.stream().anyMatch(i -> i.getText().equalsIgnoreCase(item)));
            asrt.assertAll();
        }
    }

    @Given("navigate to outlet orders Page")
    public void navigateToOutletOrdersPage() {
        dashBoardPage.outletsDropList.click();
        dashBoardPage.outletsPageLink.click();
    }

    @When("creating an order for item {string} from outlet {string}")
    public void creatingAnOrderForItemFromOutlet(String itemName, String outletName) {
        WebElement selectedOutlet = outlets.outletsList.stream().filter(o -> outlets.outletName(o).getText().contains(outletName)).findFirst().orElse(outlets.outletsList.getFirst());
        selectedOutlet.click();
        new P00_multiPurposes(driver).waitLoading();
        WebElement selectedItem = outlets.outletItems.stream().filter(i -> i.getText().contains(itemName)).findFirst().orElse(outlets.outletItems.getFirst());
        selectedItem.click();
        outlets.nextButton.click();
        new P00_multiPurposes(driver).waitLoading();
    }

    @Then("Check the Tax and Discount Calculations")
    public void checkTheTaxAndDiscountCalculations() {
        double discountValue = 10.0;
        String discountType;

        boolean inclusive = outlets.inclusive();
        for (int i = 0; i <= 1; i++) {
            double orderSubTotal = 0.0;
            double totalbeforeTax;
            double totalAfteTax;
            outlets.genralDisocuntButton.click();
            outlets.discountValueField().sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
            outlets.discountValueField().sendKeys(Double.toString(discountValue));
            discountType = outlets.discountTypes().get(i).getText();
            outlets.saveDiscountButton.click();
            for (WebElement itemPriceCell : outlets.selectedItemsPrices) {
                double itemPrice = outlets.itemPriceAmount(outlets.itemPriceField(itemPriceCell));
                asrt.assertEquals(itemPrice, outlets.itemSubTotalAmount(itemPriceCell));
                orderSubTotal += itemPrice;
                asrt.assertFalse(Utils.isEnabled(outlets.insertDiscountButto(itemPriceCell)));
            }
            double discountAmount = Nazeel_Calculations.getDiscountAmount(orderSubTotal, discountValue, discountType);
            totalbeforeTax = orderSubTotal - discountAmount;
            double taxes = Nazeel_Calculations.outletOrderTaxes(orderSubTotal, discountAmount, inclusive);
            if (inclusive)
                totalAfteTax = totalbeforeTax;
            else
                totalAfteTax = totalbeforeTax + taxes;

            asrt.assertEquals( outlets.orderSubTotal(),orderSubTotal,discountType +"subtotal");
            asrt.assertEquals(outlets.orderGenralDiscountAmount(),discountAmount, discountType+"discountamount");
            asrt.assertEquals(outlets.orderTaxes(),taxes, discountType);
            asrt.assertEquals( outlets.orderAmountBeforeTaxes(),totalbeforeTax,discountType);
            asrt.assertEquals( outlets.orderAmountAfterTaxes(),totalAfteTax,discountType);
        }
        asrt.assertAll();
    }
}
