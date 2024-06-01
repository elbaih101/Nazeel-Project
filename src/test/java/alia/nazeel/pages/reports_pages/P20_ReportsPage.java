package alia.nazeel.pages.reports_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class P20_ReportsPage extends BasePage
{


    public P20_ReportsPage(WebDriver driver) {
       super(driver);
    }

//Todo : finish importing rest of the reports
    @FindBy(xpath = "//a[@href=\"/reports/PayTabs-report\"]")
    public WebElement digitalPaymentsReportLink;
}
