package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang.StringUtils;
import org.example.Utils;
import org.example.enums.PaymentMethods;
import org.example.enums.Vouchers;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P11_DigitalPaymentPage;
import org.example.pages.P17_CashDrawerPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.reservations.P03_2_ReservationFinancialPage;
import org.example.pages.vouchersPages.P10_VouchersPage;
import org.example.pages.vouchersPages.P16_VouchersPopUp;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class D08_Vouchers {
    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    final P03_2_ReservationFinancialPage reservationFinancialPage = new P03_2_ReservationFinancialPage(driver);
    final P16_VouchersPopUp vouchersPopUp = new P16_VouchersPopUp(driver);
    final P10_VouchersPage vouchersPage = new P10_VouchersPage(driver);
    final P00_multiPurposes multiPurposes = new P00_multiPurposes(driver);
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P11_DigitalPaymentPage digitalPaymentPage = new P11_DigitalPaymentPage(driver);
    final P17_CashDrawerPage cashDrawerPage = new P17_CashDrawerPage(driver);

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
        if (tab.equalsIgnoreCase(Vouchers.Draft.toString())) {
            selectedTab = vouchersPopUp.draftTab();
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
        selectedTab.click();
    }

    @And("enter maturity Date {string} and amount {string}")
    public void enterMaturityDate(String date, String amount) {
        Utils.setDate(vouchersPopUp.draftMaturityDate(), date);
        Utils.setAttribute(js, vouchersPopUp.draftMaturityDate(), "value", date);
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
    }
// gets added to it  "autogeerated" from successfull payments via digital payment
   public static List<String> voucherNums = new ArrayList<>();
    int i = 0;

    @Then("submit the voucher and check success message prefix {string} postfix {string}")
    public void submitTheVoucherAndCheckSuccessMessage(String prefix, String postfix) {
        wait.until(ExpectedConditions.elementToBeClickable(vouchersPopUp.submitButton()));
        vouchersPopUp.submitButton().click();
        voucherNums.add(StringUtils.substringBetween(multiPurposes.toastMsg.getText(), prefix, postfix));
        new D03_BlocksAndFloors().checkToastMesageContainsText(prefix + voucherNums.get(i) + postfix);
        i++;
    }

    @And("select Payment Method {string} and enter amount {string}")
    public void selectPaymentMethodAndEnterAmount(String paymentMethod, String amount) {
        List<WebElement> methods = vouchersPopUp.paymentMethos();

        wait.until(ExpectedConditions.visibilityOfAllElements(methods));
        methods.stream().filter(method -> method.getText().contains(paymentMethod)).toList().get(0).click();
        multiPurposes.waitLoading();
        if (!paymentMethod.equalsIgnoreCase(PaymentMethods.Cash.toString()) && !paymentMethod.equalsIgnoreCase(PaymentMethods.PayTabs.toString())) {
            vouchersPopUp.banks().get(new Random().nextInt(vouchersPopUp.banks().size()));
        }
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
    }

    @Given("open Refund Vouchers tab")
    public void openRefundVouchersTab() {
        wait.until(ExpectedConditions.elementToBeClickable(reservationFinancialPage.receiptsTap));
        reservationFinancialPage.refundsTap.click();
    }

    @Given("successfully create a voucher of type {string} amount {string} payment Method {string} maturity Date {string} and Creatian Date {string}")
    public void successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate(String voucherType, String amount, String paymentMethod, String maturityDate, String creatianDate) {
        String prefix = "";
        String Postfix = " Generated successfully";
        prefix = switch (voucherType) {
            case "Receipt", "SAReceipt" -> "Receipt Number. ";
            case "SD" -> "Security Deposit Receipt Voucher Number. ";
            case "Refund" -> "Refund Voucher Number. ";
            case "SDRefund" -> "Security Deposit Refund Voucher Number. ";
            case "Draft", "SADraft" -> "Draft Voucher Number. ";
            default -> prefix;
        };
        multiPurposes.waitLoading();
        if (!voucherType.contains("SA")) {
            if (voucherType.equalsIgnoreCase(Vouchers.Refund.toString()) || voucherType.equalsIgnoreCase(Vouchers.SDRefund.toString())) {
                openRefundVouchersTab();
                clickOnTheAddVoucherButton();
                selectVoucherTab(voucherType);

            }

            if (voucherType.equalsIgnoreCase(Vouchers.Receipt.toString()) || voucherType.equalsIgnoreCase(Vouchers.SD.toString())) {

                reservationFinancialPage.receiptsTap.click();
                clickOnTheAddVoucherButton();
                selectVoucherTab(voucherType);

            }

            if (voucherType.equalsIgnoreCase(Vouchers.Draft.toString())) {
                clickOnTheAddVoucherButton();
                selectVoucherTab(voucherType);

            }
        } else {
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/dashboard")));
            if (voucherType.equalsIgnoreCase(Vouchers.SADraft.toString())) {
                wait.until(ExpectedConditions.elementToBeClickable(vouchersPage.newVoucherButton));
                vouchersPage.newVoucherButton.click();
                wait.until(ExpectedConditions.elementToBeClickable(vouchersPopUp.selctGuestButton()));
                vouchersPopUp.selctGuestButton().click();
                new D06_DigitalPayment().selectGuest("RANDOM", "", "");
                vouchersPopUp.purposeField().sendKeys("testing automation SADraft");

            }
            if (voucherType.equalsIgnoreCase(Vouchers.SAReceipt.toString())) {
                vouchersPage.newVoucherButton.click();
                wait.until(ExpectedConditions.elementToBeClickable(vouchersPopUp.selctGuestButton()));
                vouchersPopUp.selctGuestButton().click();
                new D06_DigitalPayment().selectGuest("RADOM", "", "");
                vouchersPopUp.purposeField().sendKeys("testing automation SAReceipt");

            }
        }

        if (voucherType.contains("Draft")) {
            enterMaturityDate(maturityDate, amount);
        } else {
            selectPaymentMethodAndEnterAmount(paymentMethod, amount);
        }
        if (!creatianDate.equalsIgnoreCase("")) {
            vouchersPopUp.dateField().clear();
            Utils.setDate(vouchersPopUp.dateField(), creatianDate);
        }

        submitTheVoucherAndCheckSuccessMessage(prefix, Postfix);
    }

    @Then("Check {string} Voucher with state {string} edit mode")
    public void checkVoucherEditMode(String voucherType, String voucherState) {
        multiPurposes.waitLoading();
        for (String s : voucherNums) {
            if (voucherState.equalsIgnoreCase("Ended") || voucherState.equalsIgnoreCase("CashDrop")) {
                vouchersPage.editButton(vouchersPage.vouchersNums.stream().filter(num -> num.getText().equalsIgnoreCase(s)).toList().get(0), voucherType).click();
            } else if (voucherState.equalsIgnoreCase("Collected")) {
                vouchersPage.editButton(vouchersPage.receitRelatedDrafts.stream().filter(num -> num.getText().equalsIgnoreCase(s)).toList().get(0), voucherType).click();
            } else if (voucherState.equalsIgnoreCase("Generated")) {
                vouchersPage.editButton(vouchersPage.paymentMethods.stream().filter(method -> method.getText().equalsIgnoreCase("PayTabs")).toList().get(0),voucherType);
            }
            String expectedColor = "#fafafa";

            String actualColor = (vouchersPopUp.dateField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);

            actualColor = (vouchersPopUp.timeField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);

            actualColor = (vouchersPopUp.guestField(voucherType).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);

            actualColor = (vouchersPopUp.amountField().findElement(By.xpath("..")).getCssValue("background-color"));
            actualColor = Color.fromString(actualColor).asHex();
            asrt.assertEquals(actualColor, expectedColor);
            vouchersPopUp.discardButton().click();
        }
    }

    @And("click on draft more menu and choose collect by {string} payment")
    public void clickOnDraftMoreMenuAndChooseCollectByPayment(String paymentMethod) {
        //todo :: implement draft creation for easy testing
        if (paymentMethod.equalsIgnoreCase(PaymentMethods.Digital.toString())) {
            List<WebElement> amountsOfDraftNotFullyPaid = vouchersPage.draftsRemainigAmounts().stream().filter(element -> Character.valueOf(element.getText().trim().charAt(0)).compareTo('0') != 0).toList();
            WebElement selectedDraftAmount = amountsOfDraftNotFullyPaid.get(new Random().nextInt(amountsOfDraftNotFullyPaid.size()));
            vouchersPage.moreActions(selectedDraftAmount, "draft").stream().filter(element -> element.getText().trim().contains("Collect Via Digital Payment")).toList().get(0).click();
            D06_DigitalPayment.draftNo = digitalPaymentPage.draftNumber().getText();
        } else {
            vouchersPage.moreActions(vouchersPage.vouchersNums.stream().filter(num -> num.getText().equalsIgnoreCase(voucherNums.get(0))).toList().get(0), Vouchers.Draft.toString()).stream().filter(action -> action.getText().equalsIgnoreCase("Collect")).toList().get(0).click();
        }

    }

    @When("finish Draft Normal collecting process with amount {string} PaymentMethod {string}")
    public void finishDraftNormalCollectingProcess(String amount, String paymentMethod) {
        vouchersPopUp.paymentMethos().stream().filter(method -> method.getText().contains(paymentMethod)).toList().get(0).click();
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
        cashDrawerPage.dateto().clear();
        Utils.setDate(cashDrawerPage.dateto(), toDate);
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
}
