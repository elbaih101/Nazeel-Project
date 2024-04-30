package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.example.Utils;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.reservations.P03_1_ReservationMainDataPage;
import org.example.pages.reservations.P03_7_TaxesPopUp;
import org.example.pages.setuppages.P05_SetupPage;
import org.example.pages.setuppages.financialpages.P25_TaxesAndFees;
import org.example.pages.setuppages.financialpages.P26_CostCenter;
import org.example.pages.setuppages.financialpages.P28_DiscountTypes;
import org.example.pages.setuppages.financialpages.P29_Currencies;
import org.example.pages.vouchersPages.P10_VouchersPage;
import org.example.pages.vouchersPages.P16_VouchersPopUp;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class D12_Financials {
    WebDriver driver = Hooks.driver;

    // JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPage = new P05_SetupPage(driver);
    P25_TaxesAndFees taxesAndFees = new P25_TaxesAndFees(driver);
    P03_7_TaxesPopUp taxesPopUp = new P03_7_TaxesPopUp(driver);
    P26_CostCenter costCenter = new P26_CostCenter(driver);
    P28_DiscountTypes discountTypes = new P28_DiscountTypes(driver);
    P29_Currencies currencies = new P29_Currencies(driver);

    @Given("open Taxes and Fees Page")
    public void openTaxesAndFeesPage() {
        new P00_multiPurposes(driver).waitLoading();
        dashBoardPage.setupPageLink.click();
        setupPage.financialDropList.click();
        setupPage.taxesAndFeesLink.click();
    }

    @Given("create new type {string} with name {string} and method {string} and amount {string} applied on {string} and start date {string} end date {string} Charged on {string}")
    public void createNewTypeWithNameAndMethodAndAmountAppliedOnAndStartDateEndDateChargedOn(String type, String name, String method, String amount, String aplOn, String sDate, String eDate, String chrgOn) {
        new P00_multiPurposes(driver).waitLoading();
        taxesAndFees.newTax_FeeButton.click();
        fillTaxData(type, name, method, amount, aplOn, sDate, eDate, chrgOn);
        taxesAndFees.submitButon.click();
    }

    HashMap<String, String> taxMap = new HashMap<>();

    public void fillTaxData(String type, String name, String method, String amount, String aplOn, String sDate, String eDate, String chrgOn) {
        new P00_multiPurposes(driver).waitLoading();
        switch (type) {
            case "Fee" -> taxesAndFees.feesButton.click();
            case "Tax" -> taxesAndFees.taxesButton.click();
            case null, default -> {
            }
        }
        if (!name.isEmpty()) {
            taxesAndFees.taxes_FeesList().stream().filter(li -> li.getText().contains(name)).findFirst().orElseThrow().click();
            taxMap.put("name", name);
        }
        if (!method.isEmpty()) {

            taxesAndFees.methodField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!method.equalsIgnoreCase("non")) {
                taxesAndFees.methodsList().stream().filter(me -> me.getText().contains(method)).findAny().orElseThrow().click();

            }
            taxMap.put("method", method);
        }
        if (!amount.isEmpty()) {
            taxesAndFees.amountField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!amount.equalsIgnoreCase("non")) {
                taxesAndFees.amountField.sendKeys(amount);

            }
            taxMap.put("amount", amount);
        }
        if (!aplOn.isEmpty()) {
            if (!taxesAndFees.selectedAppliedFor.isEmpty()) {
                if (aplOn.equalsIgnoreCase("non")) {
                    for (WebElement item : taxesAndFees.selectedAppliedFor) {
                        taxesAndFees.deleteselctedButtton(item).click();

                    }
                }
            } else {
                if (!aplOn.equalsIgnoreCase("non")) {
                    taxesAndFees.appliedForList().stream().filter(ap -> ap.getText().contains(aplOn)).findAny().orElseThrow().click();
                }
            }
            taxMap.put("aplOn", aplOn);
        } else {
            taxMap.put("aplOn", "Rent");
        }
        if (!sDate.isEmpty()) {
            taxesAndFees.startDate.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!sDate.equalsIgnoreCase("non")) {
                Utils.setDate(taxesAndFees.startDate, sDate);

            }
            taxMap.put("sDate", sDate);
        }
        if (!eDate.isEmpty()) {
            taxesAndFees.endDate.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            wait.withTimeout(Duration.ofMillis(300));
            if (!taxesAndFees.popUpConfirmButton.isEmpty()) {
                taxesAndFees.popUpConfirmButton.getFirst().click();
            }
            Utils.setDate(taxesAndFees.endDate, eDate);
            wait.withTimeout(Duration.ofSeconds(10));
            taxesAndFees.endDate.sendKeys(eDate);
            taxesAndFees.popUpConfirmButton.getFirst().click();
            taxMap.put("eDate", eDate);
        }
        if (!chrgOn.isEmpty()) {
            if (!taxesAndFees.selectedChrgdOn.isEmpty()) {
                if (chrgOn.equalsIgnoreCase("non")) {
                    if (taxesAndFees.useForExpensesSwitch.getAttribute("class").contains("k-switch-on")) {
                        taxesAndFees.chargedOnSwitch.click();
                    }
                } else {
                    for (WebElement item : taxesAndFees.selectedAppliedFor) {
                        taxesAndFees.deleteselctedButtton(item).click();
                    }
                    taxesAndFees.chargedOnList().stream().filter(co -> co.getText().contains(chrgOn)).findAny().orElseThrow().click();

                }
            } else {
                if (!chrgOn.equalsIgnoreCase("non")) {
                    if (!taxesAndFees.useForExpensesSwitch.getAttribute("class").contains("k-switch-on")) {
                        taxesAndFees.chargedOnSwitch.click();
                    }
                    taxesAndFees.chargedOnList().stream().filter(co -> co.getText().contains(chrgOn)).findAny().orElseThrow().click();
                }


            }
            taxMap.put("chrgOn", chrgOn);
        }
        taxMap.put("stat", "Active");

    }

    DateTimeFormatter dateFormater = DateTimeFormat.forPattern("dd/MM/yyyy");

    @And("Check the type {string}  with name {string} and method {string} and amount {string} applied on {string} and start date {string} end date {string} Charged on {string} status {string}")
    public void checkTheTypeWithNameAndMethodAndAmountAppliedOnAndStartDateEndDateChargedOn(String type, String name, String method, String amount, String aplOn, String sDate, String eDate, String chrgOn, String stat) {

        if (!(aplOn.equalsIgnoreCase("non") || amount.equalsIgnoreCase("non") || method.equalsIgnoreCase("non") || sDate.equalsIgnoreCase("non") || dateFormater.parseDateTime(eDate).isBefore(dateFormater.parseDateTime(sDate)))) {
            WebElement cust = taxesAndFees.names.stream().filter(n -> n.getText().contains(name)).findAny().orElseThrow();
            if (!type.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxType(cust).getText().equalsIgnoreCase(type), "Expected :" + type + "\n Actual :" + taxesAndFees.taxType(cust).getText());
            }
            if (!method.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxMethod(cust).getText().toLowerCase().contains(method.toLowerCase()), "Expected :" + method + "\n Actual :" + taxesAndFees.taxMethod(cust).getText());
            }
            if (!amount.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxAmount(cust).getText().contains(amount), "Expected :" + amount + "\n Actual :" + taxesAndFees.taxAmount(cust).getText());
            }
            if (!aplOn.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxAppliedOn(cust).getText().toLowerCase().contains(aplOn.toLowerCase()), "Expected :" + aplOn + "\n Actual :" + taxesAndFees.taxAppliedOn(cust).getText());
            }
            if (!sDate.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxStartDate(cust).getText().toLowerCase().contains(sDate), "Expected :" + sDate + "\n Actual :" + taxesAndFees.taxStartDate(cust).getText());
            }
            if (!eDate.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxEndDate(cust).getText().toLowerCase().contains(eDate), "Expected :" + eDate + "\n Actual :" + taxesAndFees.taxEndDate(cust).getText());
            }
            if (!chrgOn.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxChargedOn(cust).getText().toLowerCase().contains(chrgOn.toLowerCase()), "Expected :" + chrgOn + "\n Actual :" + taxesAndFees.taxChargedOn(cust).getText());
            }
            if (!stat.isEmpty()) {
                asrt.assertTrue(taxesAndFees.taxStatus(cust).getText().toLowerCase().contains(stat.toLowerCase()), "Expected :" + stat + "\n Actual :" + taxesAndFees.taxStatus(cust).getText());
            }

            asrt.assertAll();

        }
    }

    @Given("edit customization {string} method {string} amount {string} applied on {string} startDate {string} endDate {string} Charged on {string} status {string}")
    public void editCustomizationMethodAmountAppliedOnStartDateEndDateChargedOn(String name, String method, String amount, String aplOn, String sDate, String eDate, String chrgOn, String stat) {
        WebElement cust = taxesAndFees.names.stream().filter(n -> n.getText().toLowerCase().contains(name.toLowerCase())).findAny().orElseThrow();
        taxesAndFees.editButton(cust).click();
        fillTaxData("", "", method, amount, aplOn, sDate, eDate, chrgOn);
        if (stat.equalsIgnoreCase("inactive") && taxesAndFees.statusSwitch.getAttribute("class").contains("k-switch-on")) {
            taxesAndFees.statusSwitch.click();
        } else if (stat.equalsIgnoreCase("active") && taxesAndFees.statusSwitch.getAttribute("class").contains("k-switch-of")) {
            taxesAndFees.statusSwitch.click();
        }
        taxMap.put("stat", stat);
        taxesAndFees.submitButon.click();
        if (!taxesAndFees.popUpConfirmButton.isEmpty())
            taxesAndFees.popUpConfirmButton.get(0).click();
    }

    @Given("delete the customizatiin {string}")
    public void deleteTheCustomizatiin(String tax) {

        WebElement selectedTax = taxesAndFees.names.stream().filter(t -> t.getText().contains(tax)).findFirst().orElseThrow();
        taxesAndFees.deleteButton(selectedTax).click();
        taxesAndFees.confirmDeleteButton.click();
    }

    @And("Check msg {string} and the tax {string} is not visible on grid")
    public void checkTheTaxIsNotVisibleOnGrid(String msg, String tax) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (!msg.contains("can not be deleted")) {
            asrt.assertFalse(taxesAndFees.names.stream().anyMatch(t -> t.getText().contains(tax)));
            asrt.assertAll();
        }
    }

    @And("Check the tax {string} is applied on the reservations")
    public void checkTheTaxIsAppliedOnTheReservations(String taxName) {
        if (!(taxMap.get("aplOn").equalsIgnoreCase("non") || taxMap.get("amount").equalsIgnoreCase("non") || taxMap.get("method").equalsIgnoreCase("non") || taxMap.get("sDate").equalsIgnoreCase("non") || dateFormater.parseDateTime(taxMap.get("eDate")).isBefore(dateFormater.parseDateTime(taxMap.get("sDate"))))) {
            if (taxMap.get("stat").toLowerCase().contains("active")) {
                openReservationTaxesPopUp(taxMap.get("sDate"), taxMap.get("eDate"));
                WebElement selectedTax = taxesPopUp.taxesNames.stream().filter(t -> t.getText().toLowerCase().contains(taxName.toLowerCase())).findFirst().orElseThrow();
                asrt.assertTrue(taxesPopUp.taxMethod(selectedTax).getText().contains(taxMap.get("amount")));
                if (taxMap.get("method").contains("Percentage")) {
                    asrt.assertTrue(taxesPopUp.taxMethod(selectedTax).getText().contains("%"));
                }
                asrt.assertEquals(taxMap.get("sDate"),taxesPopUp.taxStartDate(selectedTax).getText());
                asrt.assertEquals(taxMap.get("eDate"),taxesPopUp.taxEndDate(selectedTax).getText());
            } else {

                asrt.assertFalse(taxesPopUp.taxesNames.stream().anyMatch(t -> t.getText().contains(taxMap.get("name"))));
            }
            asrt.assertAll();
        }
    }

    private void openReservationTaxesPopUp(String startDate, String endDate) {
        new D01_Reservations().openReservationsPage();
        new D01_Reservations().clickOnAddNewReservation();
        new D01_Reservations().selectStartDateAndEndDate(startDate, endDate);
        new D01_Reservations().openUnitSelectionPopup();
        new D01_Reservations().selectAUnit("RANDOM");
        new P00_multiPurposes(driver).waitLoading();
        new P03_1_ReservationMainDataPage(driver).veiwTaxesButton.click();
    }

    @Given("change the calculation to {string}")
    public void changeTheCalculationTo(String calcType) {
        taxesAndFees.taxSettingButton.click();
        if (calcType.equalsIgnoreCase("inclusive") && taxesAndFees.inclisuveSwitch.getAttribute("class").contains("k-switch-off")) {
            taxesAndFees.inclisuveSwitch.click();
        } else if (calcType.equalsIgnoreCase("exclusive") && taxesAndFees.inclisuveSwitch.getAttribute("class").contains("k-switch-on")) {
            taxesAndFees.inclisuveSwitch.click();
        }
        taxesAndFees.saveCalcButton.click();
    }

    @Then("Check the taxes are with the {string} type")
    public void checkTheTaxesAreWithTheType(String calcType) {
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertTrue(taxesAndFees.calcstate.getText().contains(calcType), "Expected: " + calcType + "\nActual: " + taxesAndFees.calcstate.getText());
//        WebElement sDate = taxesAndFees.startDates.get(0);
//        WebElement eDate = taxesAndFees.taxEndDate(sDate);

        if (calcType.equalsIgnoreCase("inclusive")) {
            taxesPopUp.taxesInclusiveness.forEach(t -> asrt.assertTrue(t.isSelected()));
        } else {
            taxesPopUp.taxesInclusiveness.forEach(t -> asrt.assertFalse(t.isSelected()));
        }
        asrt.assertAll();
    }


    @Given("open cost Centers Page")
    public void openCostCentersPage() {
        new P00_multiPurposes(driver).waitLoading();
        dashBoardPage.setupPageLink.click();
        setupPage.financialDropList.click();
        setupPage.costCentersLink.click();
    }

    HashMap<String, String> costMap = new HashMap<>();

    @When("adding new Cost Center with name {string} category {string}")
    public void addingNewCostCenter(String name, String categ) {
        costCenter.newCostCenterButton.click();
        fillCostCenterData(name, categ, "");
        costCenter.submitButton.click();
    }

    private void fillCostCenterData(String name, String categ, String stat) {
        WebElement selecetd;
        if (!categ.isEmpty()) {
            new P00_multiPurposes(driver).waitLoading();
            if (categ.equalsIgnoreCase("random")) {
                selecetd = costCenter.categoriesList().getFirst();
                costMap.put("categ", selecetd.getText());
                selecetd.click();
            } else if (categ.equalsIgnoreCase("non")) {
                costCenter.categoreyField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                costMap.put("categ", "non");
            } else {
                selecetd = costCenter.categoriesList().stream().filter(c -> c.getText().contains(categ)).findAny().orElseThrow();
                costMap.put("categ", selecetd.getText());
                selecetd.click();
            }

        }
        if (!name.isEmpty()) {
            costCenter.costCenterNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!name.equalsIgnoreCase("non"))
                costCenter.costCenterNameField.sendKeys(name);

            costMap.put("name", name);
        }
        costCenter.descriptionField.sendKeys("added cost Center");
        if (!stat.isEmpty()) {
            if ((stat.equalsIgnoreCase("active") && costCenter.statusSwitch.getAttribute("class").contains("k-switch-off")))
                costCenter.statusSwitch.click();

            else if ((stat.equalsIgnoreCase("inactive") && costCenter.statusSwitch.getAttribute("class").contains("k-switch-on")))
                costCenter.statusSwitch.click();
            costMap.put("stat", stat);
        }
    }

    @And("Check the newly added costCenter is added")
    public void checkTheNewlyAddedCostCenterIsAdded() {
        checkTheLastChangedCostCenter();
    }

    private void checkTheLastChangedCostCenter() {
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertTrue(costCenter.costNames.stream().anyMatch(am -> am.getText().contains(costMap.get("name"))), "Expected: " + costMap.get("name") + " to be present in the grid");
        P10_VouchersPage vouchersPage = new P10_VouchersPage(driver);
        new D06_DigitalPayment().goToDesiredVouchersPage("Payment");
        new P00_multiPurposes(driver).waitLoading();
        vouchersPage.newVoucherButton.click();
        P16_VouchersPopUp vouchersPopUp = new P16_VouchersPopUp(driver);
        if (!costMap.get("stat").equalsIgnoreCase("Inactive"))
            asrt.assertTrue(vouchersPopUp.costCentersList().stream().anyMatch(c -> c.getText().contains(costMap.get("name"))), "Expected: " + costMap.get("name") + "to be Listed");
        else
            asrt.assertFalse(vouchersPopUp.costCentersList().stream().anyMatch(c -> c.getText().contains(costMap.get("name"))), "Expected: " + costMap.get("name") + "to not be Listed");
        asrt.assertAll();
    }

    @Given("edit cost Center {string} name {string} categ {string} status {string}")
    public void editCostCenterNameCategStatus(String oName, String nName, String categ, String stat) {
        WebElement selectedCost = getCostCenterByName(oName);
        costCenter.editButton(selectedCost).click();
        fillCostCenterData(nName, categ, stat);
        costCenter.submitButton.click();
    }

    private WebElement getCostCenterByName(String name) {
        WebElement selectedCost = null;

        switch (name.toLowerCase()) {
            case "random" -> selectedCost = costCenter.costNames.stream().findAny().orElseThrow();
            case "" -> asrt.assertFalse(true, "enter the desired Cost center to be Edited");
            //  case null -> asrt.assertFalse(true, "null old name value");
            default ->
                    selectedCost = costCenter.costNames.stream().filter(c -> c.getText().toLowerCase().contains(name.toLowerCase())).findAny().orElseThrow();
        }
        asrt.assertAll();
        costMap.put("name", selectedCost.getText());
        costMap.put("categ", costCenter.costCenterCategory(selectedCost).getText());
        if (costCenter.costCenterStatus(selectedCost).getAttribute("xlink:href").contains("icon-check"))
            costMap.put("stat", "Acive");
        else if (costCenter.costCenterStatus(selectedCost).getAttribute("xlink:href").contains("icon-minus"))
            costMap.put("stat", "Inacive");
        return selectedCost;
    }

    @And("Check the updated cost Center")
    public void checkTheUpdatedCostCenter() {
        if (!costMap.containsValue("non")) {
            checkTheLastChangedCostCenter();
        }
    }

    @Given("delete cost center {string}")
    public void deleteCostCenter(String name) {
        WebElement selectedCostCenter = getCostCenterByName(name);
        costCenter.deleteButton(selectedCostCenter).click();
        costCenter.confirmDeleteButton.click();
    }

    @Then("Check message {string} and the cost center after delete action")
    public void checkMessageAndTheCostCenterAfterDeleteAction(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (!msg.contains("can not be deleted")) {
            new P00_multiPurposes(driver).waitLoading();
            asrt.assertFalse(costCenter.costNames.stream().anyMatch(t -> t.getText().contains(costMap.get("name"))));
            asrt.assertAll();
        }
    }

    @Given("filter by {string} {string}")
    public void filterBy(String field, String data) {
        costCenter.filterButton.click();
        switch (field) {
            case "name" -> costCenter.nameFilterField.sendKeys(data);
            case "categ" ->
                    costCenter.categoriesFilterList().stream().filter(c -> c.getText().contains(data)).findAny().orElseThrow().click();
            case "stat" ->
                    costCenter.statusFilterList().stream().filter(c -> c.getText().contains(data)).findAny().orElseThrow().click();
        }
        costCenter.searchButton.click();
    }

    @Then("Check the shown records {string} to contains {string}")
    public void checkTheShownRecordsToContains(String field, String data) {
        new P00_multiPurposes(driver).waitLoading();
        switch (field) {
            case "name" -> asrt.assertFalse(costCenter.costNames.stream().anyMatch(n -> !n.getText().contains(data)));
            case "categ" -> asrt.assertFalse(costCenter.categories.stream().anyMatch(c -> !c.getText().contains(data)));
            case "stat" -> {
                switch (data.toLowerCase()) {
                    case "active" ->
                            asrt.assertFalse(costCenter.costStatuses.stream().anyMatch(s -> s.getAttribute("xlink:href").contains("icon-minus")));
                    case "inactive" ->
                            asrt.assertFalse(costCenter.costStatuses.stream().anyMatch(s -> s.getAttribute("xlink:href").contains("icon-check")));

                }

            }
        }
        asrt.assertAll();
    }

    @Given("go to discount Types page")
    public void goToDiscountTypesPage() {
        new P00_multiPurposes(driver).waitLoading();
        dashBoardPage.setupPageLink.click();
        setupPage.financialDropList.click();
        setupPage.discountTypesLink.click();
        new P00_multiPurposes(driver).waitLoading();
    }

    String discountDescription;

    @When("Create new Discount Type {string} description {string}")
    public void createNewDiscountType(String type, String desc) {
        discountTypes.newDiscountButton.click();
        filldiscountData(type, desc);
    }

    private void filldiscountData(String type, String desc) {
        discountTypes.discountsList().stream().filter(d -> d.getText().equalsIgnoreCase(type)).findAny().orElseThrow().click();
        wait.until(ExpectedConditions.textToBePresentInElementValue(discountTypes.reportNameField, type));
        discountTypes.descriptionField.clear();
        discountTypes.descriptionField.sendKeys(desc);
        discountTypes.submitButton.click();
        discountDescription = desc;
    }

    @And("Check the Discount {string} in the grid with state {string} is {string}")
    public void checkTheDiscountInTheGrid(String type, String state, String recState) {
        new P00_multiPurposes(driver).waitLoading();
        WebElement tocheck;
        switch (recState) {
            case "present" -> {
                if (!type.equalsIgnoreCase("random")) {
                    final WebElement[] dType = {null};
                    asrt.assertTrue(discountTypes.types.stream().anyMatch(d -> {
                        boolean b = d.getText().equalsIgnoreCase(type);
                        if (b)
                            dType[0] = d;
                        return b;
                    }));
                    asrt.assertTrue(discountTypes.discountDescription(dType[0]).getText().contains(discountDescription));
                    tocheck = dType[0];
                } else {
                    tocheck = selectedDiscount;
                }
                asrt.assertTrue(discountTypes.discountState(tocheck).getText().contains(state));
            }
            case "deleted" ->
                    asrt.assertFalse(discountTypes.types.stream().anyMatch(d -> d.getText().equalsIgnoreCase(type)));
        }
        asrt.assertAll();
    }

    @And("the type {string} is no more in the addition list")
    public void TheTypeIsNoMoreInTheAdditionList(String type) {
        discountTypes.newDiscountButton.click();
        asrt.assertFalse(discountTypes.discountsList().stream().anyMatch(d -> d.getText().equalsIgnoreCase(type)));
        asrt.assertAll();
    }

    WebElement selectedDiscount;

    @When("dactivating discount {string}")
    public void dactivatingDiscount(String type) {
        if (!type.equalsIgnoreCase("random"))
            discountTypes.deactivateButton(discountTypes.types.stream().filter(d -> d.getText().equalsIgnoreCase(type)).findAny().orElseThrow()).click();
        else {
            selectedDiscount = discountTypes.statuses.stream().filter(d -> d.getText().equalsIgnoreCase("active")).findAny().orElseThrow();
            discountTypes.deactivateButton(selectedDiscount).click();
        }
    }

    @When("reactivating discount type {string}")
    public void reactivatingDiscountType(String type) {
        if (!type.equalsIgnoreCase("random"))
            discountTypes.activateButton(discountTypes.types.stream().filter(d -> d.getText().equalsIgnoreCase(type)).findAny().orElseThrow()).click();
        else {
            discountTypes.activateButton(selectedDiscount).click();
        }
    }

    @When("deleting discount {string} without related data")
    public void deletingDiscountWithoutRelatedData(String type) {
        if (!type.equalsIgnoreCase("random"))
            discountTypes.deleteButton(discountTypes.types.stream().filter(d -> d.getText().equalsIgnoreCase(type)).findAny().orElseThrow()).click();
        else {
            selectedDiscount = discountTypes.statuses.getLast();
            discountTypes.deleteButton(selectedDiscount).click();
        }
        discountTypes.confirmDeleteButton.click();
    }

    List<String> discountsListNames = new ArrayList<>();

    @When("replacing the order of the first record with the last")
    public void replacingTheOrderOfTheFirstRecordWithTheLast() {
        discountTypes.types.forEach(d -> discountsListNames.add(d.getText()));
        Utils.moveelement(discountTypes.discountMoveHandle(discountTypes.types.getFirst()), discountTypes.discountMoveHandle(discountTypes.types.getLast()), driver);

    }

    @And("check the order is changed")
    public void checkTheOrderIsChanged() {
        new P00_multiPurposes(driver).waitLoading();
        asrt.assertFalse(discountsListNames.getFirst().equalsIgnoreCase(discountTypes.types.getFirst().getText()), "Expected: " + discountsListNames.getFirst() +
                "\n Actual: " + discountTypes.types.getFirst().getText());
        asrt.assertAll();
    }


    @Given("go to Currencies Page")
    public void goToCurrenciesPage() {
        dashBoardPage.setupPageLink.click();
        setupPage.financialDropList.click();
        setupPage.currenciesLink.click();
    }

    @When("adding new Currency {string} with exchange Rate {string} and default {string}")
    public void addingNewCurrencyWithExchangeRateAndDefault(String curr, String exRate, String isDef) {
        currencies.newCurrencyButton.click();
        fillCurrencyData(curr, exRate, isDef, "new");
        currencies.submit_EditButton.click();

    }

    HashMap<String, String> currencyMap = new HashMap<>();

    private void setCurrency(String currName, String symbol, String exRate, String isDef, String state) {
        currencyMap.put("currName", currName);
        currencyMap.put("symbol", symbol);
        currencyMap.put("exRate", exRate);
        currencyMap.put("isDef", isDef);
        currencyMap.put("state", state);
    }

    private void fillCurrencyData(String curr, String exRate, String isDef, String state) {
        if (!curr.isEmpty()) {
            WebElement selectedCurr = currencies.currenciesList().stream().filter(c -> c.getText().toLowerCase().contains(curr.toLowerCase())).findAny().orElseThrow();
            currencyMap.put("currName", StringUtils.substringBefore(selectedCurr.getText(), " - "));
            currencyMap.put("symbol", StringUtils.substringAfter(selectedCurr.getText(), " - "));
            selectedCurr.click();
        }
        if (!exRate.isEmpty()) {
            currencies.exchangeRateField.clear();
            if (!exRate.contains("non"))
                currencies.exchangeRateField.sendKeys(exRate);
            currencyMap.put("exRate", exRate);
        }

        if ((isDef.equalsIgnoreCase("true") && currencies.defaultWitch.getAttribute("class").contains("k-switch-off")) ||
                (isDef.equalsIgnoreCase("false") && currencies.defaultWitch.getAttribute("class").contains("k-switch-on"))) {
            currencies.defaultWitch.click();
            currencies.dialogConfirmButton.click();
        }
        currencyMap.put("isDef", isDef);
        if (!state.isEmpty()) {
            if ((state.equalsIgnoreCase("active") && currencies.statusSwitch.getAttribute("class").contains("k-switch-off")) ||
                    (state.equalsIgnoreCase("inactive") && currencies.statusSwitch.getAttribute("class").contains("k-switch-on"))) {
                currencies.statusSwitch.click();
                currencyMap.put("state", state);
            } else if (state.equalsIgnoreCase("new"))
                currencyMap.put("state", "Active");

        }

    }

    @And("check msg {string} and the new currency is added in the grid")
    public void checkTheNewCurrencyIsAddedInTheGrid(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("Successfully")) {
            WebElement createdCurr = currencies.currenciesNames.stream().filter(c -> c.getText().contains(currencyMap.get("currName"))).findFirst().orElseThrow();
            asrt.assertTrue(currencies.currencySymbol(createdCurr).getText().equalsIgnoreCase(currencyMap.get("symbol")), "symbol is wrong");
            asrt.assertTrue(currencies.currencyStatus(createdCurr).getText().equalsIgnoreCase(currencyMap.get("state")), "state not right");
            if (currencyMap.get("isDef").equalsIgnoreCase("true")) {
                asrt.assertTrue(currencies.currencySetting(createdCurr).getText().contains("Default"), "the currency was not default");
                asrt.assertTrue(currencies.currencyExchangeRate(createdCurr).getText().equalsIgnoreCase("1"), "exRate not right");
            } else
                asrt.assertTrue(currencies.currencyExchangeRate(createdCurr).getText().equalsIgnoreCase(currencyMap.get("exRate")), "exRate not right");
            new D06_DigitalPayment().goToDesiredVouchersPage("Receipt");
            new P00_multiPurposes(driver).waitLoading();
            new P10_VouchersPage(driver).newVoucherButton.click();
            List<WebElement> currencies = new P16_VouchersPopUp(driver).currenciesList();
            asrt.assertTrue(currencies.stream().anyMatch(c -> c.getText().equalsIgnoreCase(currencyMap.get("symbol"))));
            asrt.assertAll();
        }

    }

    @When("editing Currency {string} to {string} and exchangeRate {string} and state {string} and default {string}")
    public void editingCurrencyToAndExchangeRateAndStateAndDefault(String oCurr, String nCurr, String exRate, String state, String isDef) {
        WebElement selectedCurr = currencies.symbols.stream().filter(c -> c.getText().equalsIgnoreCase(oCurr)).findAny().orElseThrow();
        setCurrency(currencies.currencyName(selectedCurr).getText(), currencies.currencySymbol(selectedCurr).getText(), currencies.currencyExchangeRate(selectedCurr).getText(), currencies.currencySetting(selectedCurr).getText(), currencies.currencyStatus(selectedCurr).getText());
        currencies.currencyEditButton(selectedCurr).click();
        new P00_multiPurposes(driver).waitLoading();
        fillCurrencyData(nCurr, exRate, isDef, state);
        currencies.submit_EditButton.click();
    }

    @When("deleting {string} currency")
    public void deletingCurrency(String curr) {
        WebElement selectedCurr;
        if (curr.equalsIgnoreCase("default")) {
            selectedCurr = currencies.settings.stream().filter(c -> c.getText().equalsIgnoreCase(curr)).findAny().orElseThrow();
        } else {
            selectedCurr = currencies.symbols.stream().filter(c -> c.getText().equalsIgnoreCase(curr)).findAny().orElseThrow();
        }
        setCurrency(currencies.currencyName(selectedCurr).getText(), currencies.currencySymbol(selectedCurr).getText(), currencies.currencyExchangeRate(selectedCurr).getText(), currencies.currencySetting(selectedCurr).getText(), currencies.currencyStatus(selectedCurr).getText());
        currencies.deleteButton(selectedCurr).click();
        if (!curr.equalsIgnoreCase("default"))
            currencies.dialogConfirmButton.click();
    }

    @Then("Check {string} and the currency is deleted")
    public void checkAndTheCurrencyIsDeleted(String msg) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (!msg.contains("Successfully")) {
            WebElement createdCurr = currencies.currenciesNames.stream().filter(c -> c.getText().contains(currencyMap.get("currName"))).findFirst().orElseThrow();
            asrt.assertTrue(currencies.currencySymbol(createdCurr).getText().equalsIgnoreCase(currencyMap.get("symbol")), "symbol is wrong");
            asrt.assertTrue(currencies.currencyStatus(createdCurr).getText().equalsIgnoreCase(currencyMap.get("state")), "state not right");
            if (currencyMap.get("isDef").equalsIgnoreCase("true")) {
                asrt.assertTrue(currencies.currencySetting(createdCurr).getText().contains("Default"), "the currency was not default");
                asrt.assertTrue(currencies.currencyExchangeRate(createdCurr).getText().equalsIgnoreCase("1"), "exRate not right");
            } else
                asrt.assertTrue(currencies.currencyExchangeRate(createdCurr).getText().equalsIgnoreCase(currencyMap.get("exRate")), "exRate not right");

        } else {
            asrt.assertFalse(currencies.currenciesNames.stream().anyMatch(c -> c.getText().equalsIgnoreCase(currencyMap.get("currName"))));
            new D06_DigitalPayment().goToDesiredVouchersPage("Receipt");
            new P10_VouchersPage(driver).newVoucherButton.click();
            List<WebElement> currencies = new P16_VouchersPopUp(driver).currenciesList();
            asrt.assertFalse(currencies.stream().anyMatch(c -> c.getText().equalsIgnoreCase(currencyMap.get("symbol"))));

        }
        asrt.assertAll();
    }
}
