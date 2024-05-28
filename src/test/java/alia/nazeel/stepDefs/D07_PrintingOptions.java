package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;

import alia.nazeel.pages.setuppages.P13_PrintingOptionsPage;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import alia.nazeel.pages.setuppages.P05_SetupPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class D07_PrintingOptions {

    final WebDriver driver = DriverManager.getDriver();


    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P13_PrintingOptionsPage printingOptionsPage = new P13_PrintingOptionsPage(driver);
    final P05_SetupPage setupPage = new P05_SetupPage(driver);


    @And("go to printing options page")
    public void goToPrintingOptionsPage() {
        wait.waitLoading();
        dashBoardPage.setupPageLink.click();
        wait.until(ExpectedConditions.elementToBeClickable(setupPage.printingDropList));
        setupPage.printingDropList.click();
        wait.until(ExpectedConditions.elementToBeClickable(setupPage.printingOptionLink));
        setupPage.printingOptionLink.click();
    }

    @Then("Check the default contract view is single language and default paper type is blank papaer")
    public void checkTheDefaultContractViewIsSingleLanguageAndDefaultPaperTypeIsBlankPapaer() {
        asrt.assertTrue(printingOptionsPage.contractSingleLanguageRadioButton.isSelected(), "the radio button is not selected");
        printingOptionsPage.blankCheckBoxes.forEach(element -> asrt.assertTrue(element.isSelected(), element.getAttribute("type") + "is not selected"));
        asrt.assertAll();

    }

    @When("selcting all reports and clicking {string} button and check the checkboxes are selected")
    public void selctingAllReportsAndClickingButton(String paperType) {
        wait.waitLoading();
        if (!printingOptionsPage.selectAllCheckBox.isSelected()) {
            printingOptionsPage.selectAllCheckBox.click();
        }

        while (printingOptionsPage.reportsCheckBoxes.stream().anyMatch(checkBox -> !checkBox.isSelected())) {
            printingOptionsPage.selectAllCheckBox.click();
        }

        if (paperType.contains("Letter")) {
            printingOptionsPage.letterHeadSelectionButton.click();
            printingOptionsPage.saveChangesButton.click();
            printingOptionsPage.letterHeadCheckBoxes.forEach(element -> asrt.assertTrue(element.isSelected(), element.getAttribute("type") + "is not selected"));
        } else if (paperType.contains("Blank")) {
            printingOptionsPage.blankSelectionButton.click();
            printingOptionsPage.saveChangesButton.click();
            printingOptionsPage.blankCheckBoxes.forEach(element -> asrt.assertTrue(element.isSelected(), element.getAttribute("type") + "is not selected"));
        }
        asrt.assertAll();
    }

    @When("select {string} paper for {string} and check the box is selected")
    public void selectPaperForAndCheckTheBoxIsSelected(String paperType, String report) {
        WebElement reportName = printingOptionsPage.reportsNames.stream().filter(name -> name.getText().toLowerCase().contains(report.toLowerCase())).toList().getFirst();
        WebElement theCheckBox = null;
        if (paperType.contains("cashier")) {
            theCheckBox= printingOptionsPage.cashierCheckBox(reportName);
        } else if (paperType.contains("blank")) {
            theCheckBox= printingOptionsPage.blankCheckBox(reportName);
        }
        else if(paperType.contains("letterhead")){theCheckBox= printingOptionsPage.letterHeadCheckBox(reportName);}
        while (!theCheckBox.isSelected())
        {theCheckBox.click();}
        printingOptionsPage.saveChangesButton.click();
        asrt.assertTrue(theCheckBox.isSelected());
        asrt.assertAll();

    }

    @When("selecting the {string} Language template and check its selected")
    public void selectingTheLanguageTemplateAndCheckItsSelected(String template) {
        WebElement checkBox= null;
        if (template.toLowerCase().contains("double")){ checkBox=printingOptionsPage.contractDoubleLanguageRadioButton;}
        else if(template.toLowerCase().contains("single")){checkBox= printingOptionsPage.contractSingleLanguageRadioButton;}
        while (!checkBox.isSelected()){checkBox.click();}
        printingOptionsPage.saveChangesButton.click();
        asrt.assertTrue(checkBox.isSelected());

    }
}

