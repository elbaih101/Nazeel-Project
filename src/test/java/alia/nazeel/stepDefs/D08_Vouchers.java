package alia.nazeel.stepDefs;

import alia.nazeel.enums.PaymentMethods;
import alia.nazeel.enums.Vouchers;
import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.P17_CashDrawerPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pages.reservations.P03_2_ReservationFinancialPage;
import alia.nazeel.pages.vouchersPages.P10_VouchersPage;
import alia.nazeel.pages.vouchersPages.P11_DigitalPaymentPage;
import alia.nazeel.pojos.JsonDataTools;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.Utils;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import alia.nazeel.tools.API;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.pages.vouchersPages.P16_VouchersPopUp;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.*;

public class D08_Vouchers {

    final WebDriver driver = DriverManager.getDriver();

    final JavascriptExecutor js = (JavascriptExecutor) driver;


    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P03_2_ReservationFinancialPage reservationFinancialPage = new P03_2_ReservationFinancialPage(driver);
    final P16_VouchersPopUp vouchersPopUp = new P16_VouchersPopUp(driver);
    final P10_VouchersPage vouchersPage = new P10_VouchersPage(driver);
    final P00_multiPurposes multiPurposes = new P00_multiPurposes(driver);
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P11_DigitalPaymentPage digitalPaymentPage = new P11_DigitalPaymentPage(driver);
    final P17_CashDrawerPage cashDrawerPage = new P17_CashDrawerPage(driver);
    String createdVoucherType;
    String createdVoucherState;
    VouchersMap vMap;
    String selectedCorporate;

    @Then("check the created voucher owner to be the selected corporate")

    public void checkTheCreatedVoucherOwnerToBeTheSelectedCorporate() {
        wait.waitLoading();
        WebElement createdVoucher = vouchersPage.vouchersNums.stream().filter(t -> t.getText().equalsIgnoreCase(vMap.getvNumber())).findAny().orElseThrow();
        asrt.assertEquals(selectedCorporate, vouchersPage.voucherOwner(createdVoucher, vMap.getvType()).getText());
        asrt.assertAll();
    }
    public static class VouchersMap {
        String vType;
        String vState;
        String vNumber;

        public VouchersMap() {
        }

        public VouchersMap(String vType, String vState, String vNumber) {
            setvState(vState);
            setvType(vType);
            setvNumber(vNumber);
        }

        public String getvState() {
            return vState;
        }

        public void setvState(String vState) {
            this.vState = vState;
        }

        public String getvNumber() {
            return vNumber;
        }

        public void setvNumber(String vNumber) {
            this.vNumber = vNumber;
        }

        public String getvType() {
            return vType;
        }

        public void setvType(String vType) {
            this.vType = vType;
        }
    }

    static final List<VouchersMap> vouchersMaps = new ArrayList<>();

    @When("go to Payment Vouchers Page")
    public void goToPaymentVouchersPage() {
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.vouchersDropList));
        dashBoardPage.vouchersDropList.click();
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.paymentsLink));
        dashBoardPage.paymentsLink.click();
    }

    @Given("click on the add voucher button")
    public void clickOnTheAddVoucherButton() {
        wait.until(ExpectedConditions.elementToBeClickable(reservationFinancialPage.receiptsTap));
        wait.waitLoading();
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(reservationFinancialPage.addVoucherButton)));
        reservationFinancialPage.addVoucherButton.click();
    }

    @And("select voucher {string} tab")
    public void selectVoucherTab(String tab) {
        WebElement selectedTab = null;
        wait.until(ExpectedConditions.visibilityOf(vouchersPopUp.popupTitle));
        if (tab.equalsIgnoreCase(Vouchers.promissory.toString())) {
            selectedTab = vouchersPopUp.promissoryTab();
        } else if (tab.equalsIgnoreCase(Vouchers.Receipt.toString())) {
            selectedTab = vouchersPopUp.receiptTab();
        } else if (tab.equalsIgnoreCase(Vouchers.SD.toString())) {
            selectedTab = vouchersPopUp.securityDepositetTab();
        } else if (tab.equalsIgnoreCase(Vouchers.Refund.toString())) {
            selectedTab = vouchersPopUp.refundTab();
        } else if (tab.equalsIgnoreCase(Vouchers.SDRefund.toString())) {
            selectedTab = vouchersPopUp.securityDeposuteRefundTab();
        }
        wait.waitLoading();
        wait.until(ExpectedConditions.elementToBeClickable(selectedTab));
        assert selectedTab != null;
        selectedTab.click();
    }

    @And("enter maturity Date {string} and amount {string}")
    public void enterMaturityDate(String date, String amount) {
        wait.waitLoading();
        Utils.setDate(vouchersPopUp.PromissoryMaturityDate(), date);
        Utils.setAttribute(js, vouchersPopUp.PromissoryMaturityDate(), "value", date);
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
    }

    // gets added to it  "autogeerated" from successfull payments via digital payment
    public String voucherNums;
    int i = 0;

    @Then("submit the voucher and check success message prefix {string} postfix {string}")
    public void submitTheVoucherAndCheckSuccessMessage(String prefix, String postfix) {
        //   wait.until(ExpectedConditions.elementToBeClickable(vouchersPopUp.submitButton()));
        //  voucherNums = StringUtils.substringBetween(multiPurposes.toastMsgs.getFirst().getText().toLowerCase(), prefix.toLowerCase(), postfix.toLowerCase());
        try {

            new D03_BlocksAndFloors().checkToastMesageContainsText(prefix + voucherNums + postfix);
            i++;
        } catch (AssertionError e) {
            if (multiPurposes.toastMsgs.getFirst().getText().contains("Invalid voucher issue date/ time value")) {
                Reporter.log("checked can't create voucher with date after drop cash date");
                asrt.assertTrue(true);
                asrt.assertAll();
            } else {
                asrt.assertFalse(false);
                System.out.println(e.getMessage() + "\nActual : " + multiPurposes.toastMsgs.getFirst().getText() + "\nExpected : " + prefix + voucherNums + postfix);
                Reporter.log(e.getMessage() + "\nActual : " + multiPurposes.toastMsgs.getFirst().getText() + "\nExpected : " + prefix + voucherNums + postfix);
            }
        }
    }

    @And("select Payment Method {string} and enter amount {string}")
    public void selectPaymentMethodAndEnterAmount(String paymentMethod, String amount) {
        List<WebElement> methods = vouchersPopUp.paymentMethods();

        wait.until(ExpectedConditions.visibilityOfAllElements(methods));
        methods.stream().filter(method -> method.getText().contains(paymentMethod)).toList().getFirst().click();
        wait.waitLoading();
        if (!paymentMethod.equalsIgnoreCase(PaymentMethods.Cash.toString()) && !paymentMethod.equalsIgnoreCase(PaymentMethods.PayTabs.toString())) {
            vouchersPopUp.banks().get(new Random().nextInt(vouchersPopUp.banks().size())).click();
        }
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
    }

    @Given("open Refund Vouchers tab")
    public void openRefundVouchersTab() {
        wait.until(ExpectedConditions.elementToBeClickable(reservationFinancialPage.receiptsTap));
        reservationFinancialPage.refundsTap.click();
    }

    @Given("successfully create a voucher of type {string} amount {string} payment Method {string} maturity Date {string} and Creatian Date {string} for a {string}")
    public void successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate(String voucherType, String amount, String paymentMethod, String maturityDate, String creatianDate, String guestType) {
        String prefix = "";
        //Receipt Number. 000057 generated successfully
        String Postfix = " Generated successfully";
        prefix = switch (voucherType.toLowerCase()) {
            case "receipt", "sareceipt" -> "Receipt Number. ";
            case "sd" -> "Security Deposit Receipt Voucher Number. ";
            case "refund" -> "Refund Voucher Number. ";
            case "sdrefund" -> "Security Deposit Refund Voucher Number. ";
            case "promissory", "sapromissory" -> "Promissory note Number. ";
            default -> prefix;
        };
        String url = voucherType.toLowerCase().contains("sa") ? "/api/Financial/Voucher/create" : "api/Reservation/create";
        wait.waitLoading();
        if (!voucherType.toLowerCase().contains("sa")) {
            reservationVouchersInitialize(voucherType);
        } else {
            standAloneVouchersInitialize(voucherType, guestType);
        }

        if (voucherType.contains("promissory")) {
            if (amount.toLowerCase().contains("less")) {
                enterMaturityDate(maturityDate, String.valueOf(new Random().nextDouble(reservationFinancialPage.reservationBalance())));
            } else if (amount.toLowerCase().contains("more")) {
                enterMaturityDate(maturityDate, String.valueOf(new Random().nextDouble(reservationFinancialPage.reservationBalance() + 1, reservationFinancialPage.reservationBalance() + 5)));
            } else {
                enterMaturityDate(maturityDate, amount);
            }

        } else {
            selectPaymentMethodAndEnterAmount(paymentMethod, amount);
        }
        if (!creatianDate.equalsIgnoreCase("")) {
            vouchersPopUp.dateField().clear();
            Utils.setDate(vouchersPopUp.dateField(), creatianDate);
            Utils.setTime(vouchersPopUp.timeField(), JsonDataTools.getValueFromJsonFile("src/main/resources/testdata/VouchersRelatedData.json", "dropCashTime"));
        }
        API api = new API();
        JsonObject json = Json.parse(api.getResponseBody(driver, url, () -> vouchersPopUp.submitButton().click())).asObject();
        voucherNums = json.getString("data", null);
        submitTheVoucherAndCheckSuccessMessage(prefix, Postfix);
        createdVoucherType = voucherType;
        createdVoucherState = "Created";
        vMap = new VouchersMap(voucherType, "Created", voucherNums);
        vouchersMaps.add(vMap);
    }

    private void standAloneVouchersInitialize(String voucherType, String guestType) {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/dashboard")));
        if (voucherType.equalsIgnoreCase(Vouchers.SAPromissory.toString())) {
            wait.until(ExpectedConditions.elementToBeClickable(vouchersPage.newVoucherButton));
            vouchersPage.newVoucherButton.click();
            wait.until(ExpectedConditions.elementToBeClickable(vouchersPopUp.selctGuestButton()));
            vouchersPopUp.selctGuestButton().click();
            new D06_DigitalPayment().selectGuest("RANDOM", "", "");
            vouchersPopUp.purposeField().sendKeys("testing automation SAPromissory");

        }
        if (voucherType.equalsIgnoreCase(Vouchers.SAReceipt.toString())) {
            vouchersPage.newVoucherButton.click();

            wait.waitLoading();
            switch (guestType.toLowerCase()) {
                case "corporate" -> {
                    vouchersPopUp.selctCorporateButton().click();
                    selectedCorporate = new D11_1_Corporates().selectCorporate("RANDOM", "", "", "");
                }
                case "guest" -> {
                    vouchersPopUp.selctGuestButton().click();
                    new D06_DigitalPayment().selectGuest("RANDOM", "", "");
                }
            }
            vouchersPopUp.purposeField().sendKeys("testing automation SAReceipt");

        }
    }

    private void reservationVouchersInitialize(String voucherType) {
        if (voucherType.equalsIgnoreCase(Vouchers.Refund.toString()) || voucherType.equalsIgnoreCase(Vouchers.SDRefund.toString())) {
            reservationFinancialPage.refundsTap.click();
            clickOnTheAddVoucherButton();
            selectVoucherTab(voucherType);

        }

        if (voucherType.equalsIgnoreCase(Vouchers.Receipt.toString()) || voucherType.equalsIgnoreCase(Vouchers.SD.toString())) {

            reservationFinancialPage.receiptsTap.click();
            clickOnTheAddVoucherButton();
            selectVoucherTab(voucherType);

        }

        if (voucherType.equalsIgnoreCase(Vouchers.promissory.toString())) {
            reservationFinancialPage.receiptsTap.click();
            clickOnTheAddVoucherButton();
            selectVoucherTab(voucherType);

        }
    }

    void openEditModeForVoucher(String voucherType, String voucherState, String vNumber) {

        wait.waitLoading();
        if (voucherState.equalsIgnoreCase("Ended") || voucherState.equalsIgnoreCase("CashDrop") || voucherState.equalsIgnoreCase("Created")) {
            vouchersPage.editButton(vouchersPage.vouchersNums.stream().filter(num -> num.getText().equalsIgnoreCase(vNumber)).toList().getFirst(), voucherType).click();

        } else if (voucherState.equalsIgnoreCase("Collected")) {
            vouchersPage.editButton(vouchersPage.receitRelatedpromissories.stream().filter(num -> num.getText().equalsIgnoreCase(vNumber)).toList().getFirst(), voucherType).click();

        } else if (voucherState.equalsIgnoreCase("Generated")) {
            //TODO : Check the Voucher number from the paytabs Report and use it
            vouchersPage.editButton(vouchersPage.methods.stream().filter(method -> method.getText().equalsIgnoreCase("PayTabs")).toList().getFirst(), voucherType).click();
        }

    }

    @Given("create all vouchers Types")
    public void createVouchers() {
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("promissory", "50", "Cash", "15/12/2025", "", "guest");
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("Receipt", "50", "Cash", "", "", "guest");
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("SD", "50", "Cash", "", "", "guest");
        openRefundVouchersTab();
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("Refund", "50", "Cash", "", "", "guest");
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("SDRefund", "50", "Cash", "", "", "guest");

    }

    @Then("Check all Created Vouchers edit mode and edit payment method to {string} and check msg {string}")
    public void checkVouchersAndEditPaymentMethods(String method, String msg) {
        for (VouchersMap v : vouchersMaps) {
            checkVoucher(v);
            if (!(v.getvType().equalsIgnoreCase(Vouchers.promissory.toString()) || v.getvType().equalsIgnoreCase(Vouchers.GenReceipt.toString())))
                editVoucherPaymentMethod(method, msg);
            else
                vouchersPopUp.discardButton().click();
        }
    }

    public void checkVoucher(VouchersMap vMap) {
        new D06_DigitalPayment().goToDesiredVouchersPage(vMap.vType);
        checkEditMode(vMap.vType, vMap.vState, vMap.vNumber);
    }

    public void checkEditMode(String voucherType, String voucherState, String voucherNumber) {
        wait.waitLoading();
        openEditModeForVoucher(voucherType, voucherState, voucherNumber);
        String expectedColor = "#fafafa";
        wait.withTimeout(Duration.ofSeconds(1));
        String actualColor;


        if (voucherType.equalsIgnoreCase(Vouchers.promissory.toString())) {
            actualColor = (vouchersPopUp.amountField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.amountField()), "amount field is enabled ");


        } else {
            actualColor = (vouchersPopUp.dateField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.dateField()));
            if (voucherState.equalsIgnoreCase("ended")) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                actualColor = (vouchersPopUp.paymentMethodField().findElement(By.xpath("..")).getCssValue("background-color"));
                actualColor = Color.fromString(actualColor).asHex();
                asrt.assertNotEquals(actualColor, expectedColor);
                asrt.assertTrue(Utils.isEnabled(vouchersPopUp.paymentMethodField()), "payment method is disabled despite state is ended");
            } else {
                actualColor = (vouchersPopUp.dateField().findElement(By.xpath("..")).getCssValue("background-color"));
                actualColor = Color.fromString(actualColor).asHex();
                asrt.assertEquals(actualColor, expectedColor);
                asrt.assertFalse(Utils.isEnabled(vouchersPopUp.paymentMethodField()), "payment method is enabled despite state not ended");
            }
            actualColor = (vouchersPopUp.timeField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.timeField()), "time field is enabled");

            actualColor = (vouchersPopUp.guestField(voucherType).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertTrue(!vouchersPopUp.guestField(voucherType).isEnabled(), "Guest field is enabled");

            actualColor = (vouchersPopUp.amountField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.amountField()), "amount field is enabled ");
        }
        asrt.assertAll();
        wait.withTimeout(Duration.ofSeconds(10));
    }

    @Then("Check {string} Voucher with state {string} edit mode")
    public void checkVoucherEditMode(String voucherType, String voucherState) {
        wait.waitLoading();

        openEditModeForVoucher(voucherType, voucherState, vMap.getvNumber());
        String expectedColor = "#fafafa";
        wait.withTimeout(Duration.ofSeconds(1));
        String actualColor;


        if (voucherType.equalsIgnoreCase(Vouchers.promissory.toString())) {
            actualColor = (vouchersPopUp.amountField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.amountField()), "amount field is enabled ");


        } else {
            actualColor = (vouchersPopUp.dateField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.dateField()));
            if (voucherState.equalsIgnoreCase("ended")) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                actualColor = (vouchersPopUp.paymentMethodField().findElement(By.xpath("..")).getCssValue("background-color"));
                actualColor = Color.fromString(actualColor).asHex();
                asrt.assertNotEquals(actualColor, expectedColor);
                asrt.assertTrue(Utils.isEnabled(vouchersPopUp.paymentMethodField()), "payment method is disabled despite state is ended");
            } else {
                actualColor = (vouchersPopUp.dateField().findElement(By.xpath("..")).getCssValue("background-color"));
                actualColor = Color.fromString(actualColor).asHex();
                asrt.assertEquals(actualColor, expectedColor);
                asrt.assertFalse(Utils.isEnabled(vouchersPopUp.paymentMethodField()), "payment method is enabled despite state not ended");
            }
            actualColor = (vouchersPopUp.timeField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.timeField()), "time field is enabled");

            actualColor = (vouchersPopUp.guestField(voucherType).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertTrue(!vouchersPopUp.guestField(voucherType).isEnabled(), "Guest field is enabled");

            actualColor = (vouchersPopUp.amountField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            asrt.assertFalse(Utils.isEnabled(vouchersPopUp.amountField()), "amount field is enabled ");
        }
        asrt.assertAll();
        vouchersPopUp.discardButton().click();
        wait.withTimeout(Duration.ofSeconds(10));
    }


    @And("click on Promissory more menu and choose collect by {string} payment")
    public void clickOnPromissoryMoreMenuAndChooseCollectByPayment(String paymentMethod) {

        if (paymentMethod.equalsIgnoreCase(PaymentMethods.Digital.toString())) {
            List<WebElement> amountsOfPromissoryNotFullyPaid = vouchersPage.promissoriesRemainigAmounts().stream().filter(element -> element.getText().trim().charAt(0) != '0').toList();
            WebElement selectedPromissoryAmount = amountsOfPromissoryNotFullyPaid.get(new Random().nextInt(amountsOfPromissoryNotFullyPaid.size()));
            vouchersPage.moreActions(selectedPromissoryAmount, "promissory").stream().filter(element -> element.getText().trim().contains("Collect Via Digital Payment")).toList().getFirst().click();
            D06_DigitalPayment.promissoryNo = digitalPaymentPage.promissoryNumber().getText();
        } else {
            vouchersPage.moreActions(vouchersPage.vouchersNums.stream().filter(num -> num.getText().equalsIgnoreCase(vMap.getvNumber())).toList().getFirst(), Vouchers.promissory.toString()).stream().filter(action -> action.getText().equalsIgnoreCase("Collect")).toList().getFirst().click();
        }

    }

    @When("finish promissory Normal collecting process with amount {string} PaymentMethod {string}")
    public void finishpromissoryNormalCollectingProcess(String amount, String paymentMethod) {
        vouchersPopUp.paymentMethods().stream().filter(method -> method.getText().contains(paymentMethod)).toList().getFirst().click();
        wait.waitLoading();
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
        vouchersPopUp.submitButton().click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Amount Collected Successfully");
    }

    @And("create a drop cash action to date {string}")
    public void createADropCashActionToDate(String toDate) {
        wait.waitLoading();
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.cashDrawer));
        dashBoardPage.cashDrawer.click();
        wait.until(ExpectedConditions.urlContains("cash-drawer-balance"));
        wait.waitLoading();
        cashDrawerPage.dropCashButton().click();
        cashDrawerPage.dateTo().clear();
        String dropCashTime = JsonDataTools.getValueFromJsonFile("src/main/resources/testdata/VouchersRelatedData.json", "dropCashTime");
        String dropCashDate = JsonDataTools.getValueFromJsonFile("src/main/resources/testdata/VouchersRelatedData.json", "dropCashDate");
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm a");
        DateTimeFormatter dateFormmater = DateTimeFormat.forPattern("dd/MM/YYYY");
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm a");
        DateTime newDateTime = DateTime.parse(dropCashDate + " " + dropCashTime, dateTimeFormatter).plusMinutes(1);

        if (!toDate.equalsIgnoreCase("dropdate")) {
            Utils.setDate(cashDrawerPage.dateTo(), toDate);
        } else {
            Utils.setDate(cashDrawerPage.dateTo(), dropCashDate);
        }
        Utils.setTime(cashDrawerPage.timeTo(), dropCashTime);
        JsonDataTools.writeValueToJsonFile("src/main/resources/testdata/VouchersRelatedData.json", "dropCashTime", newDateTime.toString(timeFormatter));
        JsonDataTools.writeValueToJsonFile("src/main/resources/testdata/VouchersRelatedData.json", "dropCashDate", newDateTime.toString(dateFormmater));
        cashDrawerPage.checkButton().click();
        wait.waitLoading();
        cashDrawerPage.customAmountRadioButton().click();
        cashDrawerPage.customAmountField().clear();
        cashDrawerPage.customAmountField().sendKeys("1");
        cashDrawerPage.saveButton().click();
        cashDrawerPage.nextButton().click();
        vouchersPopUp.guestField("DropCash").sendKeys("some owner");
        vouchersPopUp.purposeField().sendKeys("test");
        cashDrawerPage.checkButton().click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Added Successfully");

    }

    @When("editing the Created Voucher's  amount {string} payment Method {string} maturity Date {string} and Creatian Date {string}")
    public void editingTheCreatedVoucherSAmountPaymentMethodMaturityDateAndCreatianDate(String amount, String payMethod, String maturityDate, String creatDate) {
        wait.waitLoading();
        openEditModeForVoucher(createdVoucherType, createdVoucherState, voucherNums);
        if (!creatDate.isEmpty()) {
            vouchersPopUp.dateField().clear();
            Utils.setDate(vouchersPopUp.dateField(), creatDate);
        }
        if (!amount.isEmpty()) {
            vouchersPopUp.amountField().clear();
            vouchersPopUp.amountField().sendKeys(amount);
        }
        if (!payMethod.isEmpty()) {
            vouchersPopUp.paymentMethods().stream().filter(method -> method.getText().equalsIgnoreCase(payMethod)).toList().getFirst().click();
        }
        if (!maturityDate.isEmpty()) {
            vouchersPopUp.PromissoryMaturityDate().clear();
            Utils.setDate(vouchersPopUp.PromissoryMaturityDate(), maturityDate);
        }
        wait.until(ExpectedConditions.elementToBeClickable(vouchersPopUp.submitButton()));
        vouchersPopUp.submitButton().click();
    }

    public void editVoucherPaymentMethod(String newMethod, String msg) {
        wait.withTimeout(Duration.ofSeconds(1));
        PaymentMethods p = Arrays.stream(PaymentMethods.values()).filter(pM -> pM.toString().toLowerCase().contains(newMethod.toLowerCase())).findFirst().orElseThrow();
        vouchersPopUp.paymentMethods().stream().filter(pM -> pM.getText().equals(p.toString())).findFirst().orElseThrow().click();
        if (!p.equals(PaymentMethods.Cash)) {
            vouchersPopUp.banks().getFirst().click();
        }
        vouchersPopUp.submitButton().click();
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
    }


    @And("edit {string} Voucher with state {string} Payment method to {string} and check msg {string}")
    public void editVoucherPaymentMethodTo(String voucherType, String voucherState, String newMethod, String msg) {
        if (!voucherType.equalsIgnoreCase(Vouchers.promissory.toString())) {
            wait.waitLoading();
            openEditModeForVoucher(voucherType, voucherState, voucherNums);
            wait.withTimeout(Duration.ofSeconds(1));
            PaymentMethods p = Arrays.stream(PaymentMethods.values()).filter(pM -> pM.toString().toLowerCase().contains(newMethod.toLowerCase())).findFirst().orElseThrow();
            vouchersPopUp.paymentMethods().stream().filter(pM -> pM.getText().equals(p.toString())).findFirst().orElseThrow().click();
            if (!p.equals(PaymentMethods.Cash)) {
                vouchersPopUp.banks().getFirst().click();
            }
            vouchersPopUp.submitButton().click();
            new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        }

    }

    @Given("open Drop Cash Vouchers Page")
    public void openDropCashVouchersPage() {
        wait.waitLoading();
        dashBoardPage.vouchersDropList.click();
        dashBoardPage.dropCashVoucherssLink.click();
    }

    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY hh:mm a");
    DateTime startFrom;
    DateTime startTo;
    DateTime endFrom;
    DateTime endTo;

    @When("filtering the start period of the drop cash from {string} to {string} and the end Period from {string} to {string}")
    public void filteringTheStartPeriodOfTheDropCashFromToAndTheEndPeriodFromTo(String startFrom, String startTo, String endFrom, String endTo) {
        vouchersPage.filterButton.click();
        Utils.setDateTime(vouchersPage.startPeriodFromDateField, startFrom);
        Utils.setDateTime(vouchersPage.startPeriodToDateField, startTo);
        Utils.setDateTime(vouchersPage.endPeriodFromDateField, endFrom);
        Utils.setDateTime(vouchersPage.endPeriodToDateField, endTo);
        vouchersPage.searchButton.click();

        this.startTo = DateTime.parse(startTo, formatter);
        this.startFrom = DateTime.parse(startFrom, formatter);
        this.endFrom = DateTime.parse(endFrom, formatter);
        this.endTo = DateTime.parse(endTo, formatter);
    }
    //todo containue the drop csh filter

    @Then("Check all records shown match the searched dates")
    public void checkAllRecordsShownMatchTheSearchedDates() {
        wait.waitLoading();
        asrt.assertTrue(vouchersPage.dropCashDateTimeFroms.stream().allMatch(t -> Utils.isTimeWithinRange(DateTime.parse(t.getText(), formatter), startFrom, startTo)));
        asrt.assertTrue(vouchersPage.dropCashDateTimeTos.stream().allMatch(t -> Utils.isTimeWithinRange(DateTime.parse(t.getText(), formatter), endFrom, endTo)));
        asrt.assertAll();
    }
    @Given("filter receipt vouchers with {string} as {string}")
    public void filterReceiptVouchersWithAs(String arg0, String arg1) {
        vouchersPage.filterButton.click();
        vouchersPage.fromDatePicker.selectDateTime("12/03/1900 05:10 AM");
        Utils.sleep(10000);
    }
}
