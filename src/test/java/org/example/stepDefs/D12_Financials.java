package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.reservations.P03_1_ReservationMainDataPage;
import org.example.pages.reservations.P03_7_TaxesPopUp;
import org.example.pages.setuppages.P05_SetupPage;
import org.example.pages.setuppages.financialpages.P25_TaxesAndFees;
import org.example.pages.setuppages.financialpages.P26_CostCenter;
import org.example.pages.vouchersPages.P10_VouchersPage;
import org.example.pages.vouchersPages.P16_VouchersPopUp;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;

public class D12_Financials {
    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P05_SetupPage setupPage = new P05_SetupPage(driver);
    P25_TaxesAndFees taxesAndFees = new P25_TaxesAndFees(driver);
    P03_7_TaxesPopUp taxesPopUp = new P03_7_TaxesPopUp(driver);
    P26_CostCenter costCenter = new P26_CostCenter(driver);

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
                break;
            }
        }
        if (!name.isEmpty()) {
            taxesAndFees.taxes_FeesList().stream().filter(li -> li.getText().contains(name)).findFirst().get().click();
            taxMap.put("name", name);
        }
        if (!method.isEmpty()) {

            taxesAndFees.methodField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!method.equalsIgnoreCase("non")) {
                taxesAndFees.methodsList().stream().filter(me -> me.getText().contains(method)).findAny().get().click();

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
                if (aplOn.equalsIgnoreCase("non")) {
                } else {
                    taxesAndFees.appliedForList().stream().filter(ap -> ap.getText().contains(aplOn)).findAny().get().click();

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
                    taxesAndFees.chargedOnList().stream().filter(co -> co.getText().contains(chrgOn)).findAny().get().click();

                }
            } else {
                if (chrgOn.equalsIgnoreCase("non")) {
                } else {
                    if (!taxesAndFees.useForExpensesSwitch.getAttribute("class").contains("k-switch-on")) {
                        taxesAndFees.chargedOnSwitch.click();
                    }
                    taxesAndFees.chargedOnList().stream().filter(co -> co.getText().contains(chrgOn)).findAny().get().click();
                }


            }
            taxMap.put("chrgOn", chrgOn);
        }
        taxMap.put("stat", "Active");

    }

    DateTimeFormatter dateFormater = DateTimeFormat.forPattern("dd/MM/yyyy");

    @And("Check the type {string}  with name {string} and method {string} and amount {string} applied on {string} and start date {string} end date {string} Charged on {string} status {string}")
    public void checkTheTypeWithNameAndMethodAndAmountAppliedOnAndStartDateEndDateChargedOn(String type, String name, String method, String amount, String aplOn, String sDate, String eDate, String chrgOn, String stat) {

        if (aplOn.equalsIgnoreCase("non") || amount.equalsIgnoreCase("non") || method.equalsIgnoreCase("non") || sDate.equalsIgnoreCase("non") || dateFormater.parseDateTime(eDate).isBefore(dateFormater.parseDateTime(sDate))) {
        } else {
            WebElement cust = taxesAndFees.names.stream().filter(n -> n.getText().contains(name)).findAny().get();
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
        WebElement cust = taxesAndFees.names.stream().filter(n -> n.getText().toLowerCase().contains(name.toLowerCase())).findAny().get();
        taxesAndFees.editButton(cust).click();
        fillTaxData("", "", method, amount, aplOn, sDate, eDate, chrgOn);
        if (stat.equalsIgnoreCase("inactive") && taxesAndFees.statusSwitch.getAttribute("class").contains("k-switch-on")) {
            taxesAndFees.statusSwitch.click();
        } else if (stat.equalsIgnoreCase("active") && taxesAndFees.statusSwitch.getAttribute("class").contains("k-switch-of")) {
            taxesAndFees.statusSwitch.click();
        }
        taxMap.put("stat", stat);
        taxesAndFees.submitButon.click();
        taxesAndFees.popUpConfirmButton.get(0).click();
    }

    @Given("delete the customizatiin {string}")
    public void deleteTheCustomizatiin(String tax) {

        WebElement selectedTax = taxesAndFees.names.stream().filter(t -> t.getText().contains(tax)).findFirst().get();
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
        if (taxMap.get("aplOn").equalsIgnoreCase("non") || taxMap.get("amount").equalsIgnoreCase("non") || taxMap.get("method").equalsIgnoreCase("non") || taxMap.get("sDate").equalsIgnoreCase("non") || dateFormater.parseDateTime(taxMap.get("eDate")).isBefore(dateFormater.parseDateTime(taxMap.get("sDate")))) {
        } else {
            if (taxMap.get("stat").toLowerCase().contains("active")) {
                openReservationTaxesPopUp(taxMap.get("sDate"), taxMap.get("eDate"));
                WebElement selectedTax = taxesPopUp.taxesNames.stream().filter(t -> t.getText().toLowerCase().contains(taxName.toLowerCase())).findFirst().get();
                asrt.assertTrue(taxesPopUp.taxMethod(selectedTax).getText().contains(taxMap.get("amount")));
                if (taxMap.get("method").contains("Percentage")) {
                    asrt.assertTrue(taxesPopUp.taxMethod(selectedTax).getText().contains("%"));
                }
                asrt.assertEquals(taxesPopUp.taxStartDate(selectedTax).getText(), taxMap.get("sDate"));
                asrt.assertEquals(taxesPopUp.taxEndDate(selectedTax).getText(), taxMap.get("eDate"));
            } else {

                asrt.assertFalse(taxesPopUp.taxesNames.stream().anyMatch(t -> t.getText().contains(taxMap.get("name"))));
            }
            asrt.assertAll();
        }
    }

    private void openReservationTaxesPopUp(String startDate, String endDate) {
        new D01_MakingReservation().openReservationsPage();
        new D01_MakingReservation().clickOnAddNewReservation();
        new D01_MakingReservation().selectStartDateAndEndDate(startDate, endDate);
        new D01_MakingReservation().openUnitSelectionPopup();
        new D01_MakingReservation().selectAUnit("RANDOM");
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
        WebElement sDate = taxesAndFees.startDates.get(0);
        WebElement eDate = taxesAndFees.taxEndDate(sDate);

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
                selecetd = costCenter.categoriesList().stream().filter(c -> c.getText().contains(categ)).findAny().get();
                costMap.put("categ", selecetd.getText());
                selecetd.click();
            }

        }
        if (!name.isEmpty()) {
            costCenter.costCenterNameField.clear();
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
            case "random" -> selectedCost = costCenter.costNames.stream().findAny().get();
            case "" -> asrt.assertFalse(true, "enter the desired Cost center to be Edited");
            case null -> asrt.assertFalse(true, "null old name value");
            default ->
                    selectedCost = costCenter.costNames.stream().filter(c -> c.getText().toLowerCase().contains(name.toLowerCase())).findAny().get();
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
            case "name" ->costCenter.nameFilterField.sendKeys(data);
            case "categ"->costCenter.categoriesFilterList().stream().filter(c->c.getText().contains(data)).findAny().get().click();
            case "stat"->costCenter.statusFilterList().stream().filter(c->c.getText().contains(data)).findAny().get().click();
        }
        costCenter.searchButton.click();
    }

    @Then("Check the shown records {string} to contains {string}")
    public void checkTheShownRecordsToContains(String field, String data) {
        new P00_multiPurposes(driver).waitLoading();
        switch (field){
            case "name" ->asrt.assertFalse(costCenter.costNames.stream().anyMatch(n->!n.getText().contains(data)));
            case "categ"->asrt.assertFalse( costCenter.categories.stream().anyMatch(c->!c.getText().contains(data)));
            case "stat"->{switch (data.toLowerCase()){
                case "active"->asrt.assertFalse(costCenter.costStatuses.stream().anyMatch(s->s.getAttribute("xlink:href").contains("icon-minus"))) ;
                case "inactive"->asrt.assertFalse(costCenter.costStatuses.stream().anyMatch(s->s.getAttribute("xlink:href").contains("icon-check"))) ;

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
    }

    // icon-check  icon-minus  xlink:href
}
