package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.mutlipurposes.P00_2_GuestSelectionPopUp;
import alia.nazeel.pages.reservations.P03_1_ReservationMainDataPage;
import alia.nazeel.pages.reservations.P03_2_ReservationFinancialPage;
import alia.nazeel.tools.CustomWebDriverWait;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import alia.nazeel.enums.Vouchers;
import alia.nazeel.pages.P12_SMSLogPage;
import alia.nazeel.pages.vouchersPages.P10_VouchersPage;
import alia.nazeel.pages.vouchersPages.P11_DigitalPaymentPage;
import alia.nazeel.pages.mutlipurposes.P00_1_PaytabsExternalPage;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class D06_DigitalPayment {

    final WebDriver driver = DriverManager.getDriver();
    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P10_VouchersPage vouchersPage = new P10_VouchersPage(driver);
    final P11_DigitalPaymentPage digitalPaymentPage = new P11_DigitalPaymentPage(driver);
    final P00_2_GuestSelectionPopUp guestSelectionPopUp = new P00_2_GuestSelectionPopUp(driver);
    final P12_SMSLogPage smsLogPage = new P12_SMSLogPage(driver);
    final P00_multiPurposes multiPurposes = new P00_multiPurposes(driver);
    final P00_1_PaytabsExternalPage paytabsExternalPage = new P00_1_PaytabsExternalPage(driver);
    final P03_2_ReservationFinancialPage p032ReservationFinancialPage = new P03_2_ReservationFinancialPage(driver);

    final Actions actions = new Actions(driver);
    final JavascriptExecutor js = (JavascriptExecutor) driver;

    @And("go to {string} Vouchers Page")
    public void goToDesiredVouchersPage(String vType) {
        wait.waitLoading();
        if (vType.equalsIgnoreCase(Vouchers.promissory.toString())) {
            wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.vouchersDropList));
            dashBoardPage.dashBoardLink.click();
            dashBoardPage.vouchersDropList.click();
            wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.PromissoryLink));
            dashBoardPage.PromissoryLink.click();
        } else if (vType.equalsIgnoreCase(Vouchers.Receipt.toString()) || vType.equalsIgnoreCase(Vouchers.SD.toString())) {
//            if (!dashBoardPage.receiptsLink.isDisplayed()) {
//                wait.waitLoading();
//                dashBoardPage.dashBoardLink.click();
//                wait.waitLoading();
//                dashBoardPage.vouchersDropList.click();
//            }
           js.executeScript("arguments[0].click();",dashBoardPage.receiptsLink);
//            dashBoardPage.receiptsLink.click();
        } else if (vType.equalsIgnoreCase("payment") || vType.equalsIgnoreCase(Vouchers.Refund.toString()) || vType.equalsIgnoreCase(Vouchers.SDRefund.toString()) || vType.equalsIgnoreCase(Vouchers.Expenses.toString())) {
            dashBoardPage.dashBoardLink.click();
            dashBoardPage.vouchersDropList.click();
            wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.paymentsLink));
            dashBoardPage.paymentsLink.click();
        }
        wait.waitLoading();
    }


    @Given("open the digital payment popup")
    public void openTheDigitalPaymentPopup() {

        WebElement digitalPaymentButton = vouchersPage.digialPaymentButton();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + " :: opening the digital payment popup");
        }
        wait.until(ExpectedConditions.elementToBeClickable(digitalPaymentButton));
        digitalPaymentButton.click();
    }

    int amount;

    @And("open the generate pay-link tab and select {string} and generate link with {string} amount {int} and purpose {string}")
    public void openTheGeneratePayLinkTabAndGenerateLinkWithAmountAndPurposeAndGuest(String voucherType, String service, int amount, String purpose) {
        if (voucherType.equalsIgnoreCase(Vouchers.Receipt.toString())) {
            digitalPaymentPage.receiptTab().click();
        } else if (voucherType.equalsIgnoreCase(Vouchers.SD.toString())) {
            digitalPaymentPage.securityDepositTab().click();
        } else
            digitalPaymentPage.generateLinkTab().click();

        List<WebElement> collectViaList = digitalPaymentPage.collectViaList();
        WebElement selectedService;
        if (service.equalsIgnoreCase("RANDOM")) {
            selectedService = collectViaList.get(new Random().nextInt(collectViaList.size()));
        } else {
            selectedService = collectViaList.stream().filter(services -> services.getText().equalsIgnoreCase(service)).toList().getFirst();
        }
        wait.until(ExpectedConditions.elementToBeClickable(selectedService));
        selectedService.click();
        if (!purpose.isEmpty()) {
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
            wait.until(ExpectedConditions.visibilityOfAllElements(guestClasses));
            wait.until(ExpectedConditions.elementToBeClickable(selectedClass));
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

        wait.waitLoading();
        try {

            guestSelectionPopUp.confirmButton().click();
        }catch (ElementClickInterceptedException e){
            if (!e.getMessage().contains("kendo-dialog-actions class=\"k-dialog-buttongroup")) {
                e.printStackTrace();
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        if (!guestSelectionPopUp.ignoreDiscountButton.isEmpty()) {
            guestSelectionPopUp.ignoreDiscountButton.getFirst().click();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
        boolean bol = linkfield.getAttribute("value").toLowerCase().contains("http".toLowerCase());
//        asrt.assertTrue(bol,"the value is value");
        asrt.assertTrue(bol);
        generatedLink = linkfield.getAttribute("value");

    }

    String generatedAmount;

    @Given("succesfully create a stand alone voucher with {string} amount {int} purpose {string} Guest {string}")
    public void succesfullyCreateAStandAloneVoucherWithAmountPuposeGuest(String service, int amount, String purpose, String guest) {
        openTheGeneratePayLinkTabAndGenerateLinkWithAmountAndPurposeAndGuest(Vouchers.SAReceipt.toString(), service, amount, purpose);
        openGuestSelection();
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
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
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
        wait.waitLoading();
        List<WebElement> sentMsg = smsLogPage.paytabsMessages().stream().filter(msg -> smsLogPage.msgRecepient(msg).getText().equalsIgnoreCase(selectedguestName) && sendDate.isBefore(dateFormater.parseDateTime(smsLogPage.msgSendDate(msg).getText().trim()))
        ).toList();
        System.out.println(smsLogPage.msgBody(sentMsg.getFirst()).getText().trim());
        asrt.assertTrue(smsLogPage.msgBody(sentMsg.getFirst()).getText().trim().contains(selectedguestName + msgPrefix + generatedAmount + msgPostfix), "msg body missmatch");
        asrt.assertTrue(smsLogPage.msgBody(sentMsg.getFirst()).getText().trim().contains(generatedLink), "link missmatch");
        asrt.assertAll();
    }


    @And("go to Reservation Financial Page")
    public void goToReservationFinancialPage() {
        P03_1_ReservationMainDataPage p031ReservationMainDataPage = new P03_1_ReservationMainDataPage(driver);
        Utils.sleep(300);
        wait.until(ExpectedConditions.elementToBeClickable(p031ReservationMainDataPage.financialAndPaymentsPage));
        js.executeScript("arguments[0].click();", p031ReservationMainDataPage.financialAndPaymentsPage);
//        p031ReservationMainDataPage.financialAndPaymentsPage.click();
        wait.waitLoading();
    }

    @And("Check the data matches the reservation data")
    public void checkTheDataMatchesTheReservationData() {
        asrt.assertTrue(p032ReservationFinancialPage.reservationNum() == Integer.parseInt(digitalPaymentPage.reservationNumber().getText()));
        //Fixme remember to change this if it contridicts with the purpose
        asrt.assertTrue(p032ReservationFinancialPage.reservationBalance().toString().equalsIgnoreCase(digitalPaymentPage.reservationBalance().getText().replace("-", "")));
        asrt.assertTrue(digitalPaymentPage.reservationGuestName().getText().trim().equalsIgnoreCase(digitalPaymentPage.guestNameField().getAttribute("value").trim()));

    }

    @And("Check purpose field contains {string}")
    public void checkPurposeFieldContains(String purpose) {
        asrt.assertTrue(digitalPaymentPage.purposeField().getAttribute("value").contains(purpose));
        asrt.assertAll();
    }

    @Given("create PayTabs link for receipt Voucher with {string} and generate link with {string} amount {int} and purpose {string}")
    public void createPayTabsLinkForReceiptVoucherWithAndGenerateLinkWithAmountAndPurpose(String voucherType, String service, int amount, String purpose) {
        openTheGeneratePayLinkTabAndGenerateLinkWithAmountAndPurposeAndGuest(voucherType, service, amount, purpose);
        checkTheDataMatchesTheReservationData();
        clickGenerateAndCheckToastMessage("Saved Successfully");
        checkTheLinkIsGeneratedInTheLinkField();

    }

    //paying paytabs link //
    @And("open the link and pay it successfully")
    public void openTheLinkAndPayItSuccessfully() {
        Faker faker = new Faker();
        digitalPaymentPage.copyLinkButton().click();
        String paste = Keys.chord(Keys.CONTROL, "v");
        digitalPaymentPage.commentField().sendKeys(paste);
        String copiedurl = digitalPaymentPage.commentField().getAttribute("value");
        String nazeelWindow = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to(copiedurl);
        wait.until(ExpectedConditions.visibilityOf(paytabsExternalPage.cardNumberField));
        paytabsExternalPage.cardNumberField.sendKeys("4000000000000002");
        paytabsExternalPage.expYear.sendKeys("50");
        paytabsExternalPage.expMonth.sendKeys("12");
        paytabsExternalPage.cvv.sendKeys("123");
        paytabsExternalPage.customeInfoButton.click();
        if (paytabsExternalPage.email.getAttribute("value").isEmpty()) {
            paytabsExternalPage.email.sendKeys(faker.internet().emailAddress());
        }
        paytabsExternalPage.address.sendKeys(faker.address().streetAddress() + faker.address().country());
        paytabsExternalPage.city.sendKeys("ryad");
        paytabsExternalPage.country().selectByVisibleText("Saudi Arabia");
        paytabsExternalPage.state().selectByIndex(1);
        paytabsExternalPage.areaCode.sendKeys("1234");
        paytabsExternalPage.payButton.click();
        wait.until(ExpectedConditions.urlContains("acstest/request"));
        paytabsExternalPage.authenticateButton.click();
        wait.until(ExpectedConditions.visibilityOf(paytabsExternalPage.transactionState));
        asrt.assertTrue(paytabsExternalPage.transactionState.getText().contains("successful"));
        driver.switchTo().window(nazeelWindow);
        D08_Vouchers v = new D08_Vouchers();
        D08_Vouchers.VouchersMap vMap = new D08_Vouchers.VouchersMap("GenReceipt", "Generated", "autoGenerated");
        D08_Vouchers.vouchersMaps.add(vMap);

    }

    //////
    @When("open the log tab and select {string}")
    public void openTheLogTabAndSelectPaytabs(String service) {
        wait.until(ExpectedConditions.elementToBeClickable(digitalPaymentPage.linksLogsTab()));
        digitalPaymentPage.linksLogsTab().click();
        digitalPaymentPage.logsForList().stream().filter(element -> element.getText().toLowerCase().contains(service.toLowerCase())).toList().getFirst().click();
    }

    @Then("check the generated link is present in the grid with state {string} and Voucher type {string}")
    public void checkTheGeneratedLinkIsPresentInTheGridWithStateAndVoucherType(String state, String voucherType) {
        wait.until(ExpectedConditions.visibilityOfAllElements(digitalPaymentPage.links()));
        WebElement link = digitalPaymentPage.links().stream().filter(element -> element.getAttribute("href").trim().contains(generatedLink)).toList().getFirst();
        if (voucherType.equalsIgnoreCase("promissory")) {
            asrt.assertTrue(digitalPaymentPage.propmissoryLinkStatus(link).getText().toLowerCase().contains(state.toLowerCase()));
            asrt.assertTrue(digitalPaymentPage.promissoryNoinGrid(link).getText().toLowerCase().contains(promissoryNo));

        } else {
            asrt.assertTrue(digitalPaymentPage.linkType(link).getText().trim().equalsIgnoreCase(voucherType.trim()), digitalPaymentPage.linkType(link).getText().trim() + ":" + voucherType.trim());
            asrt.assertEquals(digitalPaymentPage.receiptsLinkStatus(link).getText().toLowerCase(), state.toLowerCase());
        }

        asrt.assertAll();
    }

    @When("oppen the link and pay it successfully")
    public void oppenTheLinkAndPayItSuccessfully() {
    }

    @Given("go to Promissory Vouchers Page")
    public void goToPromissoryVouchersPage() {
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.vouchersDropList));
        dashBoardPage.vouchersDropList.click();
        wait.until(ExpectedConditions.elementToBeClickable(dashBoardPage.PromissoryLink));
        dashBoardPage.PromissoryLink.click();
    }

    @When("succefully create a paytabs Promissory collection link with amount {string} remaining amount")
    public void succefullyCreateAPaytabsPromissorycollectionLinkWithAmountRemainingAmount(String value) {
        double remaining = Double.parseDouble(digitalPaymentPage.promissoryRemainingBalance().getText());
        double amount = 0;
        if (value.toLowerCase().contains("less")) {
            amount = new Random().nextDouble(1, remaining);
        } else if (value.toLowerCase().contains("more")) {
            amount = new Random().nextDouble(remaining, (remaining + 5));
        } else if (value.toLowerCase().contains("equal")) {
            amount = remaining;
        }

        openTheGeneratePayLinkTabAndGenerateLinkWithAmountAndPurposeAndGuest("", "Paytabs", (int) amount, "");
        clickGenerateAndCheckToastMessage("Saved Successfully");
        checkTheLinkIsGeneratedInTheLinkField();
    }
//FixME : this statuc variable may casue issues pay attenttion
    public static String promissoryNo;


}
