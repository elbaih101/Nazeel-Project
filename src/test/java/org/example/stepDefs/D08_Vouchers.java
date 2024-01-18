package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.commons.lang.StringUtils;
import org.example.Utils;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.reservations.P03_2_ReservationFinancialPage;
import org.example.pages.vouchersPages.P16_VouchersPopUp;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class D08_Vouchers {
    WebDriver driver = Hooks.driver;

    public D08_Vouchers(WebDriver driver) {
        this.driver = driver;
    }

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P03_2_ReservationFinancialPage reservationFinancialPage = new P03_2_ReservationFinancialPage(driver);
    P16_VouchersPopUp vouchersPopUp = new P16_VouchersPopUp(driver);
    P00_multiPurposes multiPurposes = new P00_multiPurposes(driver);


    @Given("click on the add voucher button")
    public void clickOnTheAddVoucherButton() {
        wait.until(ExpectedConditions.elementToBeClickable(reservationFinancialPage.receiptsTap));
        multiPurposes.waitLoading();
        reservationFinancialPage.receiptsTap.click();
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(reservationFinancialPage.addVoucherButton)));
        reservationFinancialPage.addVoucherButton.click();
    }

    @And("select voucher {string} tab")
    public void selectVoucherTab(String tab) {
        WebElement selectedTab = null;
        wait.until(ExpectedConditions.visibilityOf(vouchersPopUp.receiptTab()));
        if (tab.equalsIgnoreCase("Draft")) {
            selectedTab = vouchersPopUp.draftTab();
        } else if (tab.equalsIgnoreCase("Receipt")) {
            selectedTab = vouchersPopUp.receiptTab();
        } else if (tab.equalsIgnoreCase("SD")) {
            selectedTab = vouchersPopUp.securityDepositetTab();
        } else if (tab.equalsIgnoreCase("Refund")) {
            selectedTab = vouchersPopUp.refundTab();
        } else if (tab.equalsIgnoreCase("SDRefund")) {
            selectedTab = vouchersPopUp.securityDeposuteRefundTab();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedTab));

        selectedTab.click();
    }

    @And("eneter maturity Date {string} and amount {string}")
    public void eneterMaturityDate(String date, String amount) {
        Utils.setDate(vouchersPopUp.draftMaturityDate(), date);
        Utils.setAttribute(js, vouchersPopUp.draftMaturityDate(), "value", date);
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
    }

    String voucherNum;

    @Then("submit the voucher and check success message prefix {string} postfix {string}")
    public void submitTheVoucherAndCheckSuccessMessage(String prefix, String postfix) {
        wait.until(ExpectedConditions.elementToBeClickable(vouchersPopUp.submitButton()));
        vouchersPopUp.submitButton().click();
        voucherNum = StringUtils.substringBetween(multiPurposes.toastMsg.getText(), prefix, postfix);
        new D03_BlocksAndFloors().checkToastMesageContainsText(prefix + voucherNum + postfix);
    }

    @And("select Payment Method {string} and enter amount {string}")
    public void selectPaymentMethodAndEnterAmount(String paymentMethod, String amount) {
        List<WebElement> methods = vouchersPopUp.paymentMethos();

        wait.until(ExpectedConditions.visibilityOfAllElements(methods));
        methods.stream().filter(method -> method.getText().contains(paymentMethod)).toList().get(0).click();
        multiPurposes.waitLoading();
        if (!paymentMethod.equalsIgnoreCase("Cash") && !paymentMethod.equalsIgnoreCase("PayYabs")) {
            vouchersPopUp.banks().get(new Random().nextInt(vouchersPopUp.banks().size()));
        }
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
    }

    @Given("open Refund Vouchers tab")
    public void openRefundVouchersTab() {
        wait.until(ExpectedConditions.elementToBeClickable(reservationFinancialPage.receiptsTap));
        reservationFinancialPage.receiptsTap.click();
    }

    @Given("successfully create a voucher of type {string} amount {string} payment Method {string} maturity Date {string}")
    public void successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate(String voucherType , String amount, String paymentMethod, String maturityDate) {
        String prefix="";
        String Postfix=" Generated successfully!";
        switch (voucherType) {
            case "Receipt":
                prefix="Receipt Number. ";
            case "SD":
                prefix="Security Deposit Receipt Voucher Number. ";
            case "Refund":
                prefix="Refund Voucher Number. ";
            case "SDRefund":
                prefix="Security Deposit Refund Voucher Number. ";
            case "Draft":
                prefix="Draft Voucher Number. ";
        }
        clickOnTheAddVoucherButton();
        if (voucherType.equalsIgnoreCase("Refund")||voucherType.equalsIgnoreCase("SDRefund"))
        {openRefundVouchersTab();}
        selectVoucherTab(voucherType);

        selectPaymentMethodAndEnterAmount(paymentMethod,amount);
        if (voucherType.equalsIgnoreCase("Draft")){
        eneterMaturityDate(maturityDate,amount);}

        submitTheVoucherAndCheckSuccessMessage(prefix,Postfix);
    }
}
