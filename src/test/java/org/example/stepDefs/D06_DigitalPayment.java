package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P012_SMSLogPage;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.P10_VouchersPage;
import org.example.pages.P11_DigitalPaymentPage;
import org.example.pages.mutlipurposes.P00_multiPurposes;
import org.example.pages.reservations.P03_1_ReservationMainDataPage;
import org.example.pages.reservations.P03_2_ReservationFinancialPage;
import org.example.pages.reservations.P03_4_GuestSelectionPopUp;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class D06_DigitalPayment {

    final WebDriver driver = Hooks.driver;
    final SoftAssert asrt = new SoftAssert();
    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P10_VouchersPage vouchersPage = new P10_VouchersPage(driver);
    final P11_DigitalPaymentPage digitalPaymentPage = new P11_DigitalPaymentPage(driver);
    final P03_4_GuestSelectionPopUp guestSelectionPopUp = new P03_4_GuestSelectionPopUp(driver);
    final P012_SMSLogPage smsLogPage = new P012_SMSLogPage(driver);
    final P00_multiPurposes multiPurposes = new P00_multiPurposes(driver);
    P03_2_ReservationFinancialPage p032ReservationFinancialPage = new P03_2_ReservationFinancialPage(driver);

    final Actions actions = new Actions(driver);

    @And("go to Receipt Vouchers Page")
    public void goToReceiptVouchersPage() {
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.vouchersDropList));
        dashBoardPage.vouchersDropList.click();
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.receiptsLink));
        dashBoardPage.receiptsLink.click();
    }

    @Given("open the digital payment popup")
    public void openTheDigitalPaymentPopup() {

        WebElement digitalPaymentButton = vouchersPage.digialPaymentButton();
        wait.until(ExpectedConditions.elementToBeClickable(digitalPaymentButton));
        digitalPaymentButton.click();
    }

    int amount;

    @And("open the generate pay-link tab and select {string} and generate link with {string} amount {int} and purpose {string}")
    public void openTheGeneratePayLinkTabAndGenerateLinkWithAmountAndPurposeAndGuest(String voucherType, String service, int amount, String purpose) {
        if (voucherType.contains("Receipt")) {
            digitalPaymentPage.receiptTab().click();
        } else if (voucherType.contains("SD")) {
            digitalPaymentPage.securityDepositTab().click();
        }
        else
        digitalPaymentPage.generateLinkTab().click();

        List<WebElement> collectViaList = digitalPaymentPage.collectViaList();
        WebElement selectedService;
        if (service.equalsIgnoreCase("RANDOM")) {
            selectedService = collectViaList.get(new Random().nextInt(collectViaList.size()));
        } else {
            selectedService = collectViaList.stream().filter(services -> services.getText().equalsIgnoreCase(service)).toList().get(0);
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedService));
        selectedService.click();
        if (purpose.equalsIgnoreCase("")) {
        } else {
            digitalPaymentPage.purposeField().sendKeys(purpose);
        }

        digitalPaymentPage.amountField().sendKeys(Integer.toString(amount));
        this.amount = amount;


    }

    @And("open guest selection")
    public void openGuestSelection() {
        wait.until(ExpectedConditions.elementToBeClickable(digitalPaymentPage.guestSelectionButton()));
        digitalPaymentPage.guestSelectionButton().click();

    }

    String selectedguestName;

    @And("Select Guest {string} or ID {string} or Mobile {string}")
    public void selectGuest(String guestName, String id, String mobile) {


        if (guestName.equalsIgnoreCase("RANDOM") || guestName.equalsIgnoreCase("class")) {
            List<WebElement> guestClasses = guestSelectionPopUp.guestClasses();
            WebElement selectedClass = guestClasses.get(new Random().nextInt(guestClasses.size()));
            selectedClass.click();
        } else if (id.equalsIgnoreCase("") && mobile.equalsIgnoreCase("")) {
            wait.until(ExpectedConditions.elementToBeClickable(guestSelectionPopUp.nameButton()));
            guestSelectionPopUp.nameButton().click();
            guestSelectionPopUp.searchField().sendKeys(guestName);
        } else if (guestName.equalsIgnoreCase("") && id.equalsIgnoreCase("")) {
            wait.until(ExpectedConditions.elementToBeClickable(guestSelectionPopUp.mobileButton()));
            guestSelectionPopUp.mobileButton().click();
            guestSelectionPopUp.searchField().sendKeys(mobile);
        } else {
            guestSelectionPopUp.searchField().sendKeys(id);
        }


        wait.until(ExpectedConditions.elementToBeClickable(guestSelectionPopUp.searchButton()));
        guestSelectionPopUp.searchButton().click();

        List<WebElement> guestsNames = guestSelectionPopUp.guestsNames();
        WebElement selecetdGuest = guestsNames.get(new Random().nextInt(guestsNames.size()));
        wait.until(ExpectedConditions.elementToBeClickable(selecetdGuest));
        selecetdGuest.click();
        selectedguestName = selecetdGuest.getText();

        wait.until(ExpectedConditions.elementToBeClickable(guestSelectionPopUp.confirmButton()));
        guestSelectionPopUp.confirmButton().click();
        if (!guestSelectionPopUp.ignoreDiscountButton.isEmpty()) {
            guestSelectionPopUp.ignoreDiscountButton.get(0).click();
        }

    }

    final D03_BlocksAndFloors blocksAndFloors = new D03_BlocksAndFloors();

    @Then("click generate Link and check Toast message {string}")
    public void clickGenerateAndCheckToastMessage(String msgTxt) {
        wait.until(ExpectedConditions.elementToBeClickable(digitalPaymentPage.generateLinkButton()));
        digitalPaymentPage.generateLinkButton().click();

        blocksAndFloors.checkToastMesageContainsText(msgTxt);
    }

    String generatedLink;

    @And("Check the link is generated in the link field")
    public void checkTheLinkIsGeneratedInTheLinkField() {
        WebElement linkfield = digitalPaymentPage.generatedLinkField();
        wait.until(ExpectedConditions.attributeContains(linkfield, "value", "http"));
        boolean bol =linkfield.getAttribute("value").toLowerCase().contains("https://secure.paytabs.sa/payment".toLowerCase());
        asrt.assertTrue(bol,"the value is value");
        generatedLink = linkfield.getText();
        asrt.assertAll();
    }

    String generatedAmount;

    @Given("succesfully create a stand alone voucher with {string} amount {int} purpose {string} Guest {string}")
    public void succesfullyCreateAStandAloneVoucherWithAmountPuposeGuest(String service, int amount, String purpose, String guest) {
        openTheGeneratePayLinkTabAndGenerateLinkWithAmountAndPurposeAndGuest("Rexeipt", service, amount, purpose);
        selectGuest(guest, "", "");
        clickGenerateAndCheckToastMessage("Saved Successfully");
        checkTheLinkIsGeneratedInTheLinkField();
        generatedAmount = Integer.toString(amount);
    }

    //////////////// whattsapp checking ///////////////
    @When("click on send via Whattsapp Button")
    public void clickOnSendViaWhattsappButton() {
        WebElement sendViaWhattsappButton = digitalPaymentPage.sendViaWhattsappButton();
        actions.moveToElement(sendViaWhattsappButton);
        actions.moveToElement(sendViaWhattsappButton, 3, 4).perform();
        asrt.assertTrue(multiPurposes.bottomToolTip.getText().contains("send Link Via What's App to"));
        asrt.assertAll();
        wait.until(ExpectedConditions.elementToBeClickable(sendViaWhattsappButton));
        sendViaWhattsappButton.click();

    }

    @Then("check the opened Whattsapp page contains message body guest name{string} the amount {string}hotelname Link")
    public void checkTheOpenedWhattsappPageContainsMessageBodyGuestNameHotelnameLink(String msgPostfix, String msgPrefix) {

        String nazeelTab = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 1;
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.visibilityOf(digitalPaymentPage.whattsappBodyMsg));

        asrt.assertTrue(digitalPaymentPage.whattsappBodyMsg.getText().contains(selectedguestName + msgPostfix + generatedAmount + msgPrefix));
        asrt.assertTrue(digitalPaymentPage.whattsappBodyMsg.getText().contains(generatedLink));
        asrt.assertAll();

    }

    DateTime sendDate;

    ///////////// SMS Checking //////////////
    @When("clicking the send via SMS and check toast msg {string}")
    public void clickingTheSendViaSMSAndCheckToastMsg(String msg) {
        WebElement sendViaSMSButton = digitalPaymentPage.sendViaSMSButton();
        actions.moveToElement(sendViaSMSButton);
        actions.moveToElement(sendViaSMSButton, 3, 4).perform();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        asrt.assertTrue(multiPurposes.bottomToolTip.getText().contains("send Link Via SMS to"));
        asrt.assertAll();
        wait.until(ExpectedConditions.elementToBeClickable(sendViaSMSButton));
        sendViaSMSButton.click();
        sendDate = DateTime.now();
        digitalPaymentPage.closeButton().click();
    }

    @Then("go to sms log page and check the msg body guest name{string} the amount {string}hotelname Link")
    public void goToSmsLogPageAndCheckTheMsgBodyGuestNameTheAmountHotelnameLink(String msgPrefix, String msgPostfix) {
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.smsDropList));
        dashBoardPage.smsDropList.click();
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.smsLogPage));
        dashBoardPage.smsLogPage.click();
        wait.until(ExpectedConditions.elementToBeClickable(smsLogPage.viewButton));
        smsLogPage.viewButton.click();

        DateTimeFormatter dateFormater = DateTimeFormat.forPattern("dd/MM/yyyy hh:mm a");

        List<WebElement> sentMsg = smsLogPage.paytabsMessages().stream().filter(msg -> smsLogPage.msgRecepient(msg).getText().equalsIgnoreCase(selectedguestName) && sendDate.isBefore(dateFormater.parseDateTime(smsLogPage.msgSendDate(msg).getText().trim()))
        ).toList();
        System.out.println(smsLogPage.msgBody(sentMsg.get(0)).getText().trim());
        asrt.assertTrue(smsLogPage.msgBody(sentMsg.get(0)).getText().trim().contains(selectedguestName + msgPrefix + generatedAmount + msgPostfix), "msg body missmatch");
        asrt.assertTrue(smsLogPage.msgBody(sentMsg.get(0)).getText().trim().contains(generatedLink), "link missmatch");
        asrt.assertAll();
    }


    @And("go to Reservation Financial Page")
    public void goToReservationFinancialPage() {
        P03_1_ReservationMainDataPage p031ReservationMainDataPage = new P03_1_ReservationMainDataPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(p031ReservationMainDataPage.financialAndPaymentsPage));
        wait.until(ExpectedConditions.invisibilityOf(new P00_multiPurposes().loadingAnimation));
        p031ReservationMainDataPage.financialAndPaymentsPage.click();
    }

    @And("Check the data matches the reservation data")
    public void checkTheDataMatchesTheReservationData() {
        asrt.assertTrue(p032ReservationFinancialPage.reservationNum() == Integer.parseInt(digitalPaymentPage.reservationNumber().getText()));
        asrt.assertTrue(p032ReservationFinancialPage.balance.getText().trim().equalsIgnoreCase(digitalPaymentPage.reservationBalance().getText()));
        asrt.assertTrue(digitalPaymentPage.reservationGuestName().getText().trim().equalsIgnoreCase(digitalPaymentPage.guestNameField().getAttribute("value").trim()));

    }

    @And("Check purpose field contains {string}")
    public void checkPurposeFieldContains(String arg0) {

    }
}
