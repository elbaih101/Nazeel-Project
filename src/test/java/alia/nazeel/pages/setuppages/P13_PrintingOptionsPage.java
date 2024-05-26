package alia.nazeel.pages.setuppages;


import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;
import java.util.List;

public class P13_PrintingOptionsPage {
    final WebDriver driver;
    final CustomWebDriverWait wait;
    final Actions actions;



    public P13_PrintingOptionsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//button[contains(text(),\"Save Changes\")]")
    public WebElement saveChangesButton;
    @FindBy(xpath = "//button[contains(text(),\"Letter Head\")]")
    public WebElement letterHeadSelectionButton;
    @FindBy(xpath = "//button[contains(text(),\"Blank\")]")
    public WebElement blankSelectionButton;
    @FindBy(xpath = "//div[contains(text(),\"Single Language\")]/../../../preceding-sibling::div/input")
    public WebElement contractSingleLanguageRadioButton;
    @FindBy(xpath = "//div[contains(text(),\"Double Language\")]/../../../preceding-sibling::div/input")
    public WebElement contractDoubleLanguageRadioButton;

    @FindBy(xpath = "//th//input")
    public WebElement selectAllCheckBox;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]//input")
    public List<WebElement> reportsCheckBoxes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]//input")
    public List<WebElement> letterHeadCheckBoxes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]//input")
    public List<WebElement> blankCheckBoxes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]//input")
    public List<WebElement> cashierCheckBoxes;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]/span")
    public List<WebElement> reportsNames;

    public WebElement letterHeadCheckBox(WebElement reportName){
        return reportName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"2\"]//input"));
    }
    public WebElement blankCheckBox(WebElement reportName){
        return reportName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"3\"]//input"));
    }
    public WebElement cashierCheckBox(WebElement reportName){
        return reportName.findElement(By.xpath("../..//td[@data-kendo-grid-column-index=\"4\"]//input"));
    }


}
