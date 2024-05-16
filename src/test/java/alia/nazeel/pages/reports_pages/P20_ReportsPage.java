package alia.nazeel.pages.reports_pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P20_ReportsPage {

    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;



    public P20_ReportsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

//Todo : finish importing rest of the reports
    @FindBy(xpath = "//a[@href=\"/reports/PayTabs-report\"]")
    public WebElement digitalPaymentsReportLink;
}
