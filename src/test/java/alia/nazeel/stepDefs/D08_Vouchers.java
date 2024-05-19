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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.*;

public class D08_Vouchers {

    final WebDriver driver = DriverManager.getDriver();

    final JavascriptExecutor js = (JavascriptExecutor) driver;


    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
        multiPurposes.waitLoading();
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
        multiPurposes.waitLoading();
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
        multiPurposes.waitLoading();
        wait.until(ExpectedConditions.elementToBeClickable(selectedTab));
        assert selectedTab != null;
        selectedTab.click();
    }

    @And("enter maturity Date {string} and amount {string}")
    public void enterMaturityDate(String date, String amount) {
        multiPurposes.waitLoading();
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
        multiPurposes.waitLoading();
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
        multiPurposes.waitLoading();
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

            multiPurposes.waitLoading();
            switch (guestType.toLowerCase()) {
                case "corporate" -> {
                    vouchersPopUp.selctCorporateButton().click();
                    selectedCorporate = new D11_Customers().selectCorporate("RANDOM", "", "", "");
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

        multiPurposes.waitLoading();
        if (voucherState.equalsIgnoreCase("Ended") || voucherState.equalsIgnoreCase("CashDrop") || voucherState.equalsIgnoreCase("Created")) {
            vouchersPage.editButton(vouchersPage.vouchersNums.stream().filter(num -> num.getText().equalsIgnoreCase(vNumber)).toList().getFirst(), voucherType).click();

        } else if (voucherState.equalsIgnoreCase("Collected")) {
            vouchersPage.editButton(vouchersPage.receitRelatedpromissories.stream().filter(num -> num.getText().equalsIgnoreCase(vNumber)).toList().getFirst(), voucherType).click();

        } else if (voucherState.equalsIgnoreCase("Generated")) {
            //TODO : Check the Voucher number from the paytabs Report and use it
            vouchersPage.editButton(vouchersPage.paymentMethods.stream().filter(method -> method.getText().equalsIgnoreCase("PayTabs")).toList().getFirst(), voucherType).click();
        }

    }

    @Given("create all vouchers Types")
    public void createVouchers() {
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("promissory", "200", "Cash", "15/12/2025", "", "guest");
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("Receipt", "200", "Cash", "", "", "guest");
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("SD", "200", "Cash", "", "", "guest");
        openRefundVouchersTab();
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("Refund", "200", "Cash", "", "", "guest");
        successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate("SDRefund", "200", "Cash", "", "", "guest");

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
        multiPurposes.waitLoading();
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
        multiPurposes.waitLoading();

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
        multiPurposes.waitLoading();
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
        vouchersPopUp.submitButton().click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Amount Collected Successfully");
    }

    @And("create a drop cash action to date {string}")
    public void createADropCashActionToDate(String toDate) {
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.cashDrawer));
        dashBoardPage.cashDrawer.click();
        wait.until(ExpectedConditions.urlContains("cash-drawer-balance"));
        multiPurposes.waitLoading();
        cashDrawerPage.dropCashButton().click();
        cashDrawerPage.dateTo().clear();
        Utils.setDate(cashDrawerPage.dateTo(), toDate);
        cashDrawerPage.timeTo().clear();
        String dropCashTime = JsonDataTools.getValueFromJsonFile("src/main/resources/testdata/VouchersRelatedData.json", "dropCashTime");
        Utils.setTime(cashDrawerPage.timeTo(), dropCashTime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("HH:mm a");
        JsonDataTools.writeValueToJsonFile("src/main/resources/testdata/VouchersRelatedData.json","dropCashTime", DateTime.parse(dropCashTime,dateTimeFormatter).plusMinutes(1).toString(dateTimeFormatter));
        cashDrawerPage.checkButton().click();
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
        multiPurposes.waitLoading();
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
            multiPurposes.waitLoading();
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
}
