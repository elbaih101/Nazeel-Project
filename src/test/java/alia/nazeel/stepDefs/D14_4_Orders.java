package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.P38_Outlets;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.tools.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D14_4_Orders {

    final WebDriver driver = DriverManager.getDriver();
    final JavascriptExecutor js = (JavascriptExecutor) driver;
    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P38_Outlets outlets = new P38_Outlets(driver);

    @Given("navigate to outlet orders Page")
    public void navigateToOutletOrdersPage() {
        wait.waitLoading();
        dashBoardPage.outletsDropList.click();
        wait.waitLoading();
        dashBoardPage.outletsPageLink.click();
    }

    int taxCalcMethod;

    @When("creating an order for item {string} from outlet {string}")
    public void creatingAnOrderForItemFromOutlet(String itemName, String outletName) {
        selectingItemFromOutlet(itemName, outletName);
        //here iam doing two operation sin one getting dscount before ore after taxes and clicking the next button
        taxCalcMethod = Nazeel_Calculations.getTaxCalculationMethod(driver, () -> outlets.nextButton.click());
        wait.waitLoading();
    }


    private void selectItem(String itemName) {
        WebElement selectedItem = outlets.outletItems.stream().filter(i -> i.getText().contains(itemName)).findFirst().orElse(outlets.outletItems.getFirst());
        selectedItem.click();
    }

    private void selectOutlet(String outletName) {
        WebElement selectedOutlet = outlets.outletsList.stream().filter(o -> outlets.outletName(o).getText().contains(outletName)).findFirst().orElse(outlets.outletsList.getFirst());
        selectedOutlet.click();
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
            outlets.discountValueField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
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
            double taxes = Nazeel_Calculations.outletOrderTaxes(orderSubTotal, discountAmount, inclusive, taxCalcMethod);
            if (inclusive)
                totalAfteTax = totalbeforeTax;
            else
                totalAfteTax = totalbeforeTax + taxes;

            asrt.assertEquals(outlets.orderSubTotal(), orderSubTotal, discountType + "subtotal");
            asrt.assertEquals(outlets.orderGenralDiscountAmount(), discountAmount, discountType + "discountamount");
            asrt.assertEquals(outlets.orderTaxes(), taxes, discountType);
            asrt.assertEquals(outlets.orderAmountBeforeTaxes(), totalbeforeTax, discountType);
            asrt.assertEquals(outlets.orderAmountAfterTaxes(), totalAfteTax, discountType);
        }
        asrt.assertAll();
    }

    int ExcutedOnce = 0;

    @And("submiting the order as {string} for a {string} issue date {string}")
    public void submitingTheOrderAsForA(String orderType, String owner, String issueDate) {
        if (orderType.toLowerCase().contains("walk")) {
            outlets.walkinOrderButton.click();
            wait.waitLoading();
            if (owner.equalsIgnoreCase("corporate")) {
                outlets.selctCorporateButton.click();
                new D11_1_Corporates().selectCorporate("corp data related", "", "", "");
            } else {
                outlets.selctGuestButton.click();
                new D06_DigitalPayment().selectGuest("Random", "", "");
            }
            //Fixme fix the date format
            switch (issueDate.toLowerCase()) {
                case "futuredate" ->
                        Utils.setDate(outlets.issueDateField, DateTimeFormat.forPattern("MM/dd/yyyy").print(DateTime.now().plusDays(1)));
                case "futuretime" ->
                        Utils.setTime(outlets.issueTimeField, DateTimeFormat.forPattern("HH:mm").print(DateTime.now().plusHours(4)));
                case "pastdate" ->
                        Utils.setDate(outlets.issueDateField, DateTimeFormat.forPattern("MM/dd/yyyy").print(DateTime.now().plusDays(-1)));
                case "" -> {
                }
                default -> Utils.setDate(outlets.issueDateField, issueDate);
            }
            if (ExcutedOnce == 0) {
                outlets.payMethodsList().stream().filter(t -> t.getText().equalsIgnoreCase("cash")).findFirst().orElseThrow().click();
                wait.waitLoading();
                outlets.addPayMethodButton.click();
            }

            confirmOrderAndGetInvoiceAndReceipt(outlets.submitOrderButton::click);

        } else if (orderType.toLowerCase().contains("reservation")) {
            selectResFromOrder(owner);
            confirmOrderAndGetInvoiceAndReceipt(outlets.confirmResSelectionButton::click);

        }
        ExcutedOnce++;

    }

    private void selectResFromOrder(String owner) {
        chooseTransfereToRes();
        searchResinOrder(owner);
        outlets.resGrid().getGridCell(0, 0).click();
    }

    private void confirmOrderAndGetInvoiceAndReceipt(Runnable r) {
        API api = new API();
        String body = api.getResponseBody(driver, "api/hotel-services/orders/create", r);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        if (!json.get("data").isJsonNull()) {
            invoiceNo = json.get("data").getAsJsonObject().get("invoiceNumber").getAsString();
            receiptNo = json.get("data").getAsJsonObject().get("vouchersSequanceNumber").getAsJsonArray().get(0).getAsString();
        }
    }

    private void chooseTransfereToRes() {
        outlets.transToResButton.click();
        wait.waitLoading();
    }

    private void searchResinOrder(String owner) {
        outlets.reservationNumberSearchField.sendKeys(owner);
        outlets.searchReservationButton.click();
    }

    String invoiceNo;
    String receiptNo;

    @Then("Check {string} order is created")
    public void checkOrderIsCreated(String orderType) {
        List<String> toastMessages = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOfAllElements((new P00_multiPurposes(driver).toastMsgs)));
        List<WebElement> toasts = new P00_multiPurposes(driver).toastMsgs;
        for (WebElement msg : toasts) {
            toastMessages.add(msg.getText());
        }
        wait.waitLoading();
        if (orderType.toLowerCase().contains("walk")) {
            asrt.AssertEqualsIgnoreCase(outlets.orderStatus.getText(), "paid");
            asrt.assertFalse(outlets.receiptVouchersNums.isEmpty());
            asrt.AssertContains(toastMessages, invoiceNo);
            asrt.AssertContains(toastMessages, receiptNo);
            asrt.assertEquals(outlets.orderInvoiceNumber.getText(), invoiceNo);
            asrt.assertEquals(outlets.receiptVouchersNums.getFirst().getText(), receiptNo);

        }
        asrt.assertAll();
    }

    @Then("check the issue date validation")
    public void checkTheIssueDateValidation() {
        P00_multiPurposes multiPurposes = new P00_multiPurposes(driver);
        List<String> issueDates = Arrays.asList("futureDate", "futureTime", "pastDate");
        for (String issueDate : issueDates) {
            submitingTheOrderAsForA("walkin", "corporate", issueDate);
            if (issueDate.equalsIgnoreCase("pastDate"))
                checkOrderIsCreated("walkin");
            else {
                multiPurposes.assertToastMessageContains("Issue Date Must Not Exceed Today Date");
            }
        }

        asrt.assertAll();
    }

    float itemPrice;

    @When("selecting item {string} from outlet {string}")
    public void selectingItemFromOutlet(String item, String outletName) {

        API api = new API();
        JsonObject json = JsonParser.parseString(api.getResponseBody(driver, "AddOns/OutletItemSetup/GetByOutletId", () -> selectOutlet(outletName))).getAsJsonObject();
        JsonArray items = json.getAsJsonArray("data");
        JsonObject foundItem = null;
        String jsonProperty = "";
        String itemName = item;
        if (item.equalsIgnoreCase("user defined"))
            jsonProperty = "priceIsUserDefined";

        if (item.equalsIgnoreCase("tax exempted"))
            jsonProperty = "taxExcluded";
        if (item.equalsIgnoreCase("user defined") || item.equalsIgnoreCase("tax exempted")) {
            for (JsonElement i : items
            ) {
                if (i.getAsJsonObject().get(jsonProperty).getAsBoolean()) {
                    foundItem = i.getAsJsonObject();
                }
            }
            asrt.assertFalse(foundItem == null, "item Not Found");
            itemName = foundItem.get("nameEn").getAsString();
            itemPrice = foundItem.get("price").getAsFloat();
        }

        selectItem(itemName);

    }

    @Then("check item price is rewritable")
    public void checkItemPriceIsRewritable() {
        asrt.assertTrue(Utils.isEnabled(outlets.itemPriceField()));
        asrt.assertEquals(Float.parseFloat(outlets.itemPriceField().getAttribute("value")), itemPrice);
        asrt.assertAll();
    }


    @Then("Check the order Tax amount to be {float}")
    public void checkTheOrderTaxAmountToBe(float taxAmount) {
        asrt.assertEquals(outlets.orderTaxes(), taxAmount);
        asrt.assertAll();
    }

    @And("open Transfere to reservation pop up and Check can't be transfered to ended reservaion")
    public void openTransfereToReservationPopUpAndCheckCanTBeTransferedToEndedReservaion() {
        chooseTransfereToRes();
        String[] ended = {"Cancelled", "Checked Out", "Expired", "No Show"};
        asrt.AssertNonMatch(outlets.resGrid().getGridCells(3), s -> Utils.containsStringThatContainsIgnoreCase(ended, s.getText()));
        searchResinOrder("24000001");
        asrt.assertTrue(outlets.resGrid().getGridRows().isEmpty());
    }

    @And("open Transfere to reservation pop up and Check can't be transfered to reservation of diffrent tax type")
    public void openTransfereToReservationPopUpAndCheckCanTBeTransferedToReservationOfDiffrentTaxType() {
        selectResFromOrder("24000002");
        new D03_BlocksAndFloors().checkToastMesageContainsText("Taxes & Fees customization settings of order did not match those applied for rent!");
    }

}
