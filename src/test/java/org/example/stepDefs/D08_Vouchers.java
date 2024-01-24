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
import java.util.List;
import java.util.Random;

public class D08_Vouchers {
    WebDriver driver = Hooks.driver;

    JavascriptExecutor js = (JavascriptExecutor) driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    P03_2_ReservationFinancialPage reservationFinancialPage = new P03_2_ReservationFinancialPage(driver);
    P16_VouchersPopUp vouchersPopUp = new P16_VouchersPopUp(driver);
    P10_VouchersPage vouchersPage = new P10_VouchersPage(driver);
    P00_multiPurposes multiPurposes = new P00_multiPurposes(driver);
    P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    P11_DigitalPaymentPage digitalPaymentPage =new P11_DigitalPaymentPage(driver);

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

    @Given("successfully create a voucher of type {string} amount {string} payment Method {string} maturity Date {string}")
    public void successfullyCreateAVoucherOfTypeAmountPaymentMethodMaturityDate(String voucherType, String amount, String paymentMethod, String maturityDate) {
        String prefix = "";
        String Postfix = " Generated successfully!";
        prefix = switch (voucherType) {
            case "Receipt" -> "Receipt Number. ";
            case "SD" -> "Security Deposit Receipt Voucher Number. ";
            case "Refund" -> "Refund Voucher Number. ";
            case "SDRefund" -> "Security Deposit Refund Voucher Number. ";
            case "Draft" -> "Draft Voucher Number. ";
            default -> prefix;
        };

        if (voucherType.equalsIgnoreCase(Vouchers.Refund.toString()) || voucherType.equalsIgnoreCase(Vouchers.SDRefund.toString())) {
            openRefundVouchersTab();
            }

        if (voucherType.equalsIgnoreCase(Vouchers.Receipt.toString()) || voucherType.equalsIgnoreCase(Vouchers.SD.toString())) {
           reservationFinancialPage.receiptsTap.click();
            }
        clickOnTheAddVoucherButton();
        selectVoucherTab(voucherType);

        selectPaymentMethodAndEnterAmount(paymentMethod, amount);
        if (voucherType.equalsIgnoreCase(Vouchers.Draft.toString())) {
            eneterMaturityDate(maturityDate, amount);
        }

        submitTheVoucherAndCheckSuccessMessage(prefix, Postfix);
    }

    @Then("Check {string} Voucher with state {string} edit mode")
    public void checkVoucherEditMode(String voucherType, String voucherState) {
        multiPurposes.waitLoading();
        if (voucherState.equalsIgnoreCase("Ended")) {
            vouchersPage.editButton(vouchersPage.vouchersNums.stream().filter(num -> num.getText().equalsIgnoreCase(voucherNum)).toList().get(0), voucherType).click();
        }
        else if (voucherState.equalsIgnoreCase("Collected")){vouchersPage.editButton(vouchersPage.receitRelatedDrafts.stream().filter(num -> num.getText().equalsIgnoreCase(voucherNum)).toList().get(0), voucherType).click();}
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
    }
    @And("click on draft more menu and choose collect by {string} payment")
    public void clickOnDraftMoreMenuAndChooseCollectByPayment(String paymentMethod) {
        //todo :: implement draft creation for easy testing
        if(paymentMethod.equalsIgnoreCase(PaymentMethods.Digital.toString())){
            List<WebElement> amountsOfDraftNotFullyPaied = vouchersPage.draftsRemainigAmounts().stream().filter(element -> Character.valueOf(element.getText().trim().charAt(0)).compareTo('0')!=0).toList();
            WebElement selectedDraftAmount = amountsOfDraftNotFullyPaied.get(new Random().nextInt(amountsOfDraftNotFullyPaied.size()));
            vouchersPage.moreActions(selectedDraftAmount,"draft").stream().filter(element -> element.getText().trim().contains("Collect Via Digital Payment")).toList().get(0).click();
           D06_DigitalPayment.draftNo=digitalPaymentPage.draftNumber().getText();}
        else {vouchersPage.moreActions(vouchersPage.vouchersNums.stream().filter(num->num.getText().equalsIgnoreCase(voucherNum)).toList().get(0),Vouchers.Draft.toString()).stream().filter(action->action.getText().equalsIgnoreCase("Collect")).toList().get(0).click();
        }

    }

    @When("finish Draft Normal collecting process with amount {string} PaymentMethod {string}")
    public void finishDraftNormalCollectingProcess(String amount,String paymentMethod) {
        vouchersPopUp.paymentMethos().stream().filter(method->method.getText().equalsIgnoreCase(paymentMethod)).toList().get(0).click();
        multiPurposes.waitLoading();
        vouchersPopUp.amountField().clear();
        vouchersPopUp.amountField().sendKeys(amount);
        vouchersPopUp.submitButton().click();
       new  D03_BlocksAndFloors().checkToastMesageContainsText("Amount Collected Successfully");
    }
}
