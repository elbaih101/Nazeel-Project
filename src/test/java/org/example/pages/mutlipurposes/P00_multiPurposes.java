package org.example.pages.mutlipurposes;

import org.apache.commons.lang.StringUtils;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P00_multiPurposes {
    final WebDriver driver;
    final WebDriverWait wait;
    final Actions actions;
    public P00_multiPurposes() {
        PageFactory.initElements(Hooks.driver, this);
        this.driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public P00_multiPurposes(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }




    @FindBy(xpath = "//div[contains(@class,\"p-tooltip-bottom\")]/div[@class=\"p-tooltip-text\"]")
    public WebElement bottomToolTip;
    @FindBy(xpath = "//div[@class=\"page-loading\"]")
    public WebElement loadingAnimation;



    public void waitLoading(){
        wait.withTimeout(Duration.ofSeconds(5));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(loadingAnimation)));
    }
    @FindBy(xpath = "//div[contains(@class,\"n-pager__info\")]")
    WebElement pagination;
    public boolean checkPagination(List<WebElement> gridItems){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage()+"on pagination");
        }
        int itemsCount=  Integer.parseInt(StringUtils.substringBetween(pagination.getText(),"-"," "));
        System.out.println(gridItems.size()+" :"+itemsCount);
        return gridItems.size()==itemsCount;
    }
}
