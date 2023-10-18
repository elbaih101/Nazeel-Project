package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.TestData;
import org.example.pages.P01_LoginPage;
import org.example.pages.P02_HomePage;
import org.example.pages.P03_ReservationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class D01_MakingReservation
{
    P01_LoginPage loginPage = new P01_LoginPage();
    P02_HomePage homePage = new P02_HomePage();
    JavascriptExecutor js = (JavascriptExecutor) Hooks.driver;
    P03_ReservationPage reservationPage = new P03_ReservationPage();
    WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
    SoftAssert asrt =new SoftAssert();

    @Given("Logging in using The Login TestData username {string} password {string} ACC {string}")
    public void loggingInUsingTheLoginTestDataUsernamePasswordACC(String arg0, String arg1, String arg2)
    {
        loginPage.usernameField.sendKeys(TestData.username);
        loginPage.passwordField.sendKeys(TestData.password);
        loginPage.accField.sendKeys(TestData.accessCode);
        loginPage.loginButton.click();
    }

    @And("clicking on later to bypass user verification")
    public void clickingOnLaterToBypassUserVerification()
    {

        wait.until(ExpectedConditions.urlMatches("http://staging.nazeel.net:9002/dashboard"));
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.verificationButton));
        js.executeScript("arguments[0].click();", loginPage.verificationButton);
    }

    @Then("click on Reservation link to open reservation")
    public void clickOnReservationLinkToOpenReservation()
    {
        homePage.ReservationLink.click();
    }

    @And("Click on Add new Reservation")
    public void clickOnAddNewReservation()
    {
        wait.until(ExpectedConditions.urlContains("/reservations"));
        reservationPage.newReservationButton.click();
    }

    @And("Select Reservation source")
    public void selectReservationSource()
    {
        reservationPage.reservationSource.click();
        wait.until(ExpectedConditions.visibilityOf(reservationPage.test3ReservationSource));
        reservationPage.test3ReservationSource.click();
    }

    @And("Select Visit purpose")
    public void selectVisitPurpose()
    {
        reservationPage.visitPurposeButton.click();
        wait.until(ExpectedConditions.visibilityOf(reservationPage.familyOrFriendsSelection));
        reservationPage.familyOrFriendsSelection.click();
    }

    @And("Select Unit Now Span")
    public void selectUnitNowSpan()
    {
        reservationPage.selectUnitNowSpan.click();
    }

    @And("hover on any unit card then click confirm")
    public void hoverOnAnyUnitCardThenClickConfirm()
    {
//        wait.until(ExpectedConditions.visibilityOf(reservationPage.selectUnitDialog));
        wait.until(ExpectedConditions.visibilityOf(reservationPage.panelBar));
        List<WebElement> cards = reservationPage.panelBar.findElements(By.xpath("//div[@class=\"usc-wid unit-card ng-star-inserted\"]"));
        WebElement element = cards.get(new Random().nextInt(cards.size()));
        Actions action = new Actions(Hooks.driver);
        action.moveToElement(element);
        sleep();
        element.click();
        WebElement confirmBtn = reservationPage.confirmBtn;
//        wait.until(ExpectedConditions.attributeToBe(confirmBtn,"disabled","null"));
        confirmBtn.click();
    }


    @And("click on selectguest now button")
    public void clickOnSelectguestNowButton()
    {
//        reservationPage.selectGestButton.click();
        js.executeScript("arguments[0].click();", reservationPage.selectGestButton);


    }

    @When("select guest dialouge appears click on name to search by name")
    public void selectGuestDialougeAppearsClickOnNameToSearchByName()
    {
         WebElement guestFormDialogContainer = reservationPage.guestFormDialogContainer;

        wait.until(ExpectedConditions.visibilityOf(guestFormDialogContainer));
        WebElement nameButton= guestFormDialogContainer.findElement(By.xpath("//button[contains(text(),\"Name\")]"));
        js.executeScript("arguments[0].click();", nameButton);

    }

    @And("enter the guest name {string} in the Name field")
    public void enterTheGuestNameInTheNameField(String name)
    {
        WebElement guestFormDialogContainer = reservationPage.guestFormDialogContainer;
        WebElement nameField = guestFormDialogContainer.findElement(By.xpath("//label[contains(text(),\"Name\")]/following-sibling::input"));
        nameField.sendKeys(name);
        WebElement searchButton = guestFormDialogContainer.findElement(By.xpath("//button[contains(text(),\"Search\")]"));
        searchButton.click();
    }

    @And("click on Record After the guest Appears")
    public void clickOnRecordAfterTheGuestAppears()
    {
        WebElement guestPanel = reservationPage.guestFormDialogContainer.findElement(By.xpath("//span[contains(text(),\"Mostafa Hamed\")]"));
        wait.until(ExpectedConditions.visibilityOf(guestPanel));
        guestPanel.click();
        WebElement confirmButton= reservationPage.guestFormDialogContainer.findElement(By.xpath("//div[@class=\"u-flex-end\"]//button[contains(text(),\"Confirm\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        js.executeScript("arguments[0].click();", confirmButton);
    }

    @Then("click on check in button")
    public void clickOnCheckInButton()
    {

        WebElement saveReservationButton = reservationPage.saveReservationButton;
        wait.until(ExpectedConditions.elementToBeClickable(saveReservationButton));
        saveReservationButton.click();

    }


    @And("when reservation Summary dialouge appears click on save reservatuon Button")
    public void whenReservationSummaryDialougeAppearsClickOnSaveReservatuonButton()
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//kendo-dialog")));
        reservationPage.reservationSummarySaveButton.click();
    }

    @And("verify toast message appears with text {string} and the reservation status to be {string}")
    public void verifyToastMessageAppearsWithTextAndTheReservationStatusToBe(String successText, String confirmationText)
    {
        WebElement succesMessage =reservationPage.toastMessage;
        wait.until(ExpectedConditions.visibilityOf(succesMessage));
        asrt.assertTrue(succesMessage.getText().contains(successText));
        asrt.assertAll();
        WebElement resStatus =reservationPage.reservationStatus;
        Assert.assertTrue(resStatus.getText().contains(confirmationText));

    }
    void sleep(){try
    {
        Thread.sleep(2000);
    } catch (InterruptedException e)
    {
        e.printStackTrace();
    }}
}
