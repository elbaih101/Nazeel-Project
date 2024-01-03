package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P01_LoginPage;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.reservations.P03_1_ReservationMainDataPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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
    final WebDriver driver = Hooks.driver;
    final P01_LoginPage loginPage = new P01_LoginPage();
    final P02_DashBoardPage homePage = new P02_DashBoardPage();
    final JavascriptExecutor js = (JavascriptExecutor) Hooks.driver;
    final P03_1_ReservationMainDataPage reservationPage = new P03_1_ReservationMainDataPage(driver);
    final WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
    final SoftAssert asrt =new SoftAssert();
    Actions action = new Actions(Hooks.driver);

//    @Given("Logging in using The Login TestData username {string} password {string} ACC {string}")
//    public void loggingInUsingTheLoginTestDataUsernamePasswordACC(String arg0, String arg1, String arg2)
//    {
//        loginPage.usernameField.sendKeys(TestData.username);
//        loginPage.passwordField.sendKeys(TestData.password);
//        loginPage.accField.sendKeys(TestData.accessCode);
//        loginPage.loginButton.click();
//    }

//    @And("clicking on later to bypass user verification")
//    public void clickingOnLaterToBypassUserVerification()
//    {
//
//        wait.until(ExpectedConditions.urlMatches("http://staging.nazeel.net:9002/dashboard"));
//        wait.until(ExpectedConditions.elementToBeClickable(loginPage.verificationButton));
//        js.executeScript("arguments[0].click();", loginPage.verificationButton);
//    }

    @And("open reservations Page")
    public void openReservationsPage()
    {
        wait.until(ExpectedConditions.elementToBeClickable(homePage.ReservationsLink));
        homePage.ReservationsLink.click();
    }

    @When("Click on Add new Reservation")
    public void clickOnAddNewReservation()
    {
        wait.until(ExpectedConditions.urlContains("/reservations"));
        wait.until(ExpectedConditions.elementToBeClickable(reservationPage.newReservationButton));
        reservationPage.newReservationButton.click();
    }

    @And("Select Reservation source {string} and visit purpose {string}")
    public void selectReservationSource(String source,String purpose)
    {

        List<WebElement> reservationSources =reservationPage.reservationSources();
        WebElement selectedSource;
        if (source.equalsIgnoreCase("RANDOM")) {
            selectedSource = reservationSources.get(new Random().nextInt(reservationSources.size()));
        }else {selectedSource = reservationSources.stream().filter(element -> element.getText().contains(source)).toList().get(0);}
        wait.until(ExpectedConditions.visibilityOf(selectedSource));
        selectedSource.click();

//        reservationPage.reservationSourceDropList.click();
//        wait.until(ExpectedConditions.visibilityOf(reservationPage.test3ReservationSource));
//        reservationPage.test3ReservationSource.click();

        reservationPage.visitPurposeDropList.click();
        wait.until(ExpectedConditions.visibilityOf(reservationPage.familyOrFriendsSelection));
        reservationPage.familyOrFriendsSelection.click();
    }

    @And("open unit selection Popup")
    public void openUnitSelectionPopup()
    {
        reservationPage.selectUnitNowSpan.click();
    }
//todo :: integerate unit selection popup
    @And("select a unit")
    public void selectAUnit()
    {
//        wait.until(ExpectedConditions.visibilityOf(reservationPage.selectUnitDialog));
        wait.until(ExpectedConditions.visibilityOf(reservationPage.panelBar));
        List<WebElement> cards = reservationPage.panelBar.findElements(By.xpath("//div[@class=\"usc-wid unit-card ng-star-inserted\"]"));
        WebElement unitCaerd = cards.get(new Random().nextInt(cards.size()));
        action.moveToElement(unitCaerd).perform();
        sleep();
        unitCaerd.click();
        WebElement confirmBtn = reservationPage.confirmBtn;
//        wait.until(ExpectedConditions.attributeToBe(confirmBtn,"disabled","null"));
        confirmBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(unitCaerd));
    }


    @And("click on selectguest now button")
    public void clickOnSelectguestNowButton()
    {
//        reservationPage.selectGestButton.click();
        js.executeScript("arguments[0].click();", reservationPage.selectGestButton);


    }

//    @When("select guest dialouge appears click on name to search by name")
//    public void selectGuestDialougeAppearsClickOnNameToSearchByName()
//    {
//         WebElement guestFormDialogContainer = reservationPage.guestFormDialogContainer;
//
//        wait.until(ExpectedConditions.visibilityOf(guestFormDialogContainer));
//        WebElement nameButton= guestFormDialogContainer.findElement(By.xpath("//button[contains(text(),\"Name\")]"));
//        js.executeScript("arguments[0].click();", nameButton);
//
//    }
//
//    @And("enter the guest name {string} in the Name field")
//    public void enterTheGuestNameInTheNameField(String name)
//    {
//        WebElement guestFormDialogContainer = reservationPage.guestFormDialogContainer;
//        WebElement nameField = guestFormDialogContainer.findElement(By.xpath("//label[contains(text(),\"Name\")]/following-sibling::input"));
//        nameField.sendKeys(name);
//        WebElement searchButton = guestFormDialogContainer.findElement(By.xpath("//button[contains(text(),\"Search\")]"));
//        searchButton.click();
//    }
//
//    @And("click on Record After the guest Appears")
//    public void clickOnRecordAfterTheGuestAppears()
//    {
//        WebElement guestPanel = reservationPage.guestFormDialogContainer.findElement(By.xpath("//span[contains(text(),\"Mostafa Hamed\")]"));
//        wait.until(ExpectedConditions.visibilityOf(guestPanel));
//        guestPanel.click();
//        WebElement confirmButton= reservationPage.guestFormDialogContainer.findElement(By.xpath("//div[@class=\"u-flex-end\"]//button[contains(text(),\"Confirm\")]"));
//        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
//        js.executeScript("arguments[0].click();", confirmButton);
//    }

    @Then("click on save Reservation button")
    public void clickOnSaveReservationButton()
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
