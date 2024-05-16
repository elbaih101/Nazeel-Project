package alia.nazeel.pages.vouchersPages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@SuppressWarnings("unused")
public class P11_DigitalPaymentPage {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;



    public P11_DigitalPaymentPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement digitalPayDialog;

    public WebElement dialogTitle() {
        return digitalPayDialog.findElement(By.xpath("./kendo-dialog-titlebar//span"));
    }

    //// guest data /////////
    public WebElement reservationNumber() {
        return digitalPayDialog.findElement(By.xpath(".//div[contains(text(),\"Res. No.\")]/following-sibling::div"));
    }

    public WebElement reservationStatus() {
        return digitalPayDialog.findElement(By.xpath(".//div[contains(text(),\"Status\")]/following-sibling::div/span"));
    }

    public WebElement reservationGuestName() {
        return digitalPayDialog.findElement(By.xpath(".//div[contains(text(),\"Guest Name\")]/following-sibling::div"));
    }

    public WebElement reservationBalance() {
        return digitalPayDialog.findElement(By.xpath(".//div[contains(text(),\"Balance\")]/following-sibling::div"));
    }

    /////////// Draft Data /////
    public WebElement draftRemainingBalance() {
        return digitalPayDialog.findElement(By.xpath(".//div[contains(text(),\"Remaining Amount\")]/following-sibling::div"));
    }

    public WebElement draftCollectedAmount() {
        return digitalPayDialog.findElement(By.xpath(".//div[contains(text(),\"Collected Amount\")]/following-sibling::div"));
    }
    public WebElement draftNumber() {
        return digitalPayDialog.findElement(By.xpath(".//div[contains(text(),\"Draft No\")]/following-sibling::div"));
    }

    public WebElement generateLinkTab() {
        return digitalPayDialog.findElement(By.xpath(".//li[@role=\"tab\"]/span[contains(text(),\"Generate\")]"));
    }


    public WebElement receiptTab() {
        return digitalPayDialog.findElement(By.xpath(".//li[@role=\"tab\"]/span[text()=\"Receipt Voucher\"]"));
    }

    public WebElement securityDepositTab() {
        return digitalPayDialog.findElement(By.xpath(".//li[@role=\"tab\"]/span[contains(text(),\"Security Deposit\")]"));
    }

    public List<WebElement> collectViaList() {
        WebElement collectViaDropList = digitalPayDialog.findElement(By.xpath(".//label[contains(text(),\"Collect Via\")]/..//span/span/span"));
        wait.until(ExpectedConditions.elementToBeClickable(collectViaDropList));
        collectViaDropList.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(genralListBox.findElements(By.xpath("//li[@role=\"option\"]"))));
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    public WebElement guestSelectionButton() {
        return digitalPayDialog.findElement(By.xpath(".//label[contains(text(),\"Guest Name\")]/../../..//*[name()=\"svg\"]"));
    }

    public WebElement guestNameField() {
        return digitalPayDialog.findElement(By.xpath(".//label[contains(text(),\"Guest Name\")]/..//input"));
    }

    public WebElement commentField() {
        return digitalPayDialog.findElement(By.xpath(".//label[contains(text(),\"Comment\")]/..//input"));
    }

    public WebElement amountField() {
        return digitalPayDialog.findElement(By.xpath(".//label[contains(text(),\"Amount\")]/../..//input"));
    }

    public WebElement purposeField() {
        return digitalPayDialog.findElement(By.xpath(".//label[contains(text(),\"Purpose\")]/../..//input"));
    }

    public WebElement generateLinkButton() {
        return digitalPayDialog.findElement(By.xpath(".//button[contains(@class,\"button--green\")]"));
    }

    public WebElement generatedLinkField() {
        return digitalPayDialog.findElement(By.xpath(".//input[contains(@class,\"primary-link\")]"));
    }

    public WebElement sendViaSMSButton() {
        return digitalPayDialog.findElement(By.xpath("(.//button/*[name()='svg'])[3]"));
    }

    public WebElement sendViaWhattsappButton() {
        return digitalPayDialog.findElement(By.xpath("(.//button/*[name()='svg'])[2]"));
    }

    public WebElement copyLinkButton() {
        return digitalPayDialog.findElement(By.xpath("(.//button/*[name()='svg'])[1]"));
    }

    public WebElement closeButton() {
        return digitalPayDialog.findElement(By.xpath(".//button[contains(text(),\"Close\")]"));
    }

    //////// log tab ///////////
    public WebElement linksLogsTab() {
        return digitalPayDialog.findElement(By.xpath(".//li[@role=\"tab\"]/span[contains(text(),\"Log\")]"));
    }

    public List<WebElement> logsForList() {
        digitalPayDialog.findElement(By.xpath(".//label[contains(text(),\"Log For\")]/..//span/span/span")).click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));
    }

    //// log grid ///////////
    public List<WebElement> links() {
        return digitalPayDialog.findElements(By.xpath(".//kendo-grid-list//td[@data-kendo-grid-column-index=\"1\"]//a"));
    }

    public WebElement linkReservationNum(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"0\"]"));
    }

    public WebElement linkamount(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement linkType(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement   receiptsLinkStatus(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"4\"]/span"));
    }


    public WebElement generatedDays(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"5\"]/div/div"));
    }

    public WebElement generatedTimes(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"5\"]/div/div/following-sibling::div"));
    }

    public WebElement moreActons(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"7\"]//*[name()=\"svg\"]"));
    }

    public WebElement sendViaSmsAction(WebElement link) {
        return link.findElement(By.xpath("//kendo-popup//div[contains(text(),\"SMS\")]"));
    }

    public WebElement sendViaWhattsappAction(WebElement link) {
        return link.findElement(By.xpath("//kendo-popup//div[contains(text(),\"WhatsApp\")]"));
    }

    public WebElement getLatestStatusAction(WebElement link) {
        return link.findElement(By.xpath("//kendo-popup//div[contains(text(),\"Get Latest Status\")]"));
    }

    public WebElement copyAction(WebElement link) {
        return link.findElement(By.xpath("//kendo-popup//div[contains(text(),\"Copy\")]"));
    }
    // draft //
    public WebElement draftLinkStatus(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"3\"]/span"));
    }
    public WebElement drafNoinGrid(WebElement link) {
        return link.findElement(By.xpath("../../..//td[@data-kendo-grid-column-index=\"0\"]/span"));
    }
    /// whatsapp page///
    @FindBy(xpath = "//div[@id=\"main_block\"]/p")
    public WebElement whattsappBodyMsg;


}
