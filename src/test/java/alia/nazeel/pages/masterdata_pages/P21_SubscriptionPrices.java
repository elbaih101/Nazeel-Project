package alia.nazeel.pages.masterdata_pages;


import alia.nazeel.templates.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

public class P21_SubscriptionPrices extends BasePage
{


    public P21_SubscriptionPrices(WebDriver driver) {
       super(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]//li[@role=\"option\"]")
    List<WebElement> listItems;

    @FindBy(xpath = "//button[contains(@class,\"n-button--green\")]")
    public WebElement newPriceButton;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> services;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"1\"]")
    public List<WebElement> statuses;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> periods;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> prices;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> types;
    @FindBy(xpath = "//td[@data-kendo-grid-column-index=\"5\"]")
    List<WebElement> actionsGrid;
    @FindBy(xpath = "//div[@role=\"dialog\"]")
    WebElement subscriptiosPricespopup;
    @FindBy(xpath = "//button[@class=\"n-button n-button--primary\"]")
    public WebElement filterButton;
    @FindBy(xpath = "//button[@class=\"button button--primary\"]")
    public WebElement searchButton;
    @FindBy(xpath = "//label[contains(text(),\"Period\")]/following-sibling::kendo-numerictextbox//input")
    public WebElement filterPeriodField;

    public List<WebElement> filterServicesList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Service\")]/following-sibling::kendo-combobox//span/span/span")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> filterStatusesList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Status\")]/following-sibling::kendo-combobox//span/span/span")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> filterTypesList() {
        driver.findElement(By.xpath("//label[contains(text(),\"Type\")]/following-sibling::kendo-combobox//span/span/span")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public List<WebElement> popUpServicesList() {
        subscriptiosPricespopup.findElement(By.xpath(".//label[contains(text(),\"Service\")]/following-sibling::kendo-combobox//span/span/span")).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
        return listItems;
    }

    public WebElement subscriptionPeriodField() {
        return subscriptiosPricespopup.findElement(By.xpath(".//input[@role=\"spinbutton\"]"));
    }

    public WebElement priceField() {
        return subscriptiosPricespopup.findElement(By.xpath("//input[contains(@name,\"price\")][not(@disabled)]"));
    }

    public WebElement subscriptionType(String type) {
        WebElement selectedType = null;
        if (type.equalsIgnoreCase("new")) {
            selectedType = subscriptiosPricespopup.findElement(By.xpath("//kendo-buttongroup[@role=\"radiogroup\"]/button[1]"));
        } else if (type.equalsIgnoreCase("renew")) {
            selectedType = subscriptiosPricespopup.findElement(By.xpath("//kendo-buttongroup[@role=\"radiogroup\"]/button[2]"));
        }
        return selectedType;
    }

    public WebElement saveButton() {
        return subscriptiosPricespopup.findElement(By.xpath(".//button[contains(@class,\"n-button--primary \")]"));
    }

    public WebElement statusButton() {
        return subscriptiosPricespopup.findElement(By.xpath(".//span[@role=\"switch\"]"));
    }

    public WebElement priceEditButton(WebElement price) {
        return price.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//button"));
    }

    public WebElement deleteButton(WebElement price) {
        price.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"5\"]//div/*[name()=\"svg\"]")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.xpath("//div[contains(@class,\"popup__item popup__item--red\")]"));
    }

    public WebElement infos() {
        return subscriptiosPricespopup.findElement(By.xpath(".//p"));
    }

    public WebElement nazeelAnnualFactorText() {
        return subscriptiosPricespopup.findElement(By.xpath(".//input[@name=\"priceFactortext\"]"));
    }

    public WebElement pricePeriod(WebElement price) {
        return price.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"2\"]"));
    }

    public WebElement pricePrice(WebElement price) {
        return price.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"3\"]"));
    }

    public WebElement priceType(WebElement price) {
        return price.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"4\"]"));
    }

    public WebElement priceStatus(WebElement price) {
        return price.findElement(By.xpath("./..//td[@data-kendo-grid-column-index=\"1\"]"));
    }

    public WebElement redButton() {
        return subscriptiosPricespopup.findElement(By.xpath(".//button[contains(@class,\"n-button--danger-border\")]"));
    }

    public WebElement deletedServiceName() {
        return subscriptiosPricespopup.findElement(By.xpath(".//label[contains(text(),\"Service\")]/following-sibling::span"));
    }

    public WebElement deletedServicePeriod() {
        return subscriptiosPricespopup.findElement(By.xpath(".//label[contains(text(),\"Period \")]/following-sibling::span"));
    }
}
