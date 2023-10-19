package org.example.pages;

import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class P04_BlocksPage {

    public P04_BlocksPage() {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P04_BlocksPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> blocksNames;
    @FindBy(xpath = "//Button[contains(text(),\" New Block \")]")
    public WebElement newBlockButton;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"3\"]")
    public List<WebElement> blocksDescriptions;

    //new Block popup
    @FindBy(xpath = "//div[1]/input[@placeholder=\"Type block name\"]")
    public WebElement blockNameField;
    @FindBy(xpath = "//div[1]/textarea[@name=\"text-area\"]")
    public WebElement descriptionField;
    @FindBy(xpath = "//Button[contains(text(),\"Add Block\")]")
    public WebElement addBlockButton;
    /////

    //View Block popup
    @FindBy(xpath = "//Button[contains(text(),\"Edit Block\")]")
    public WebElement viewModeEditButton;
    @FindBy(xpath = "//Button[contains(text(),\"Save Changes\")]")
    public WebElement editModeSaveButton;
    @FindBy(className = "toast-message")
    public WebElement toastMsg;



    public WebElement blockViewButton(String nameOFBlock){
        WebElement viewButton = null;
        for (WebElement blockName:blocksNames
             ) {
            if (blockName.getText().contains(nameOFBlock))
                 viewButton =    blockName.findElement(By.xpath("/following-sibling::td[@data-kendo-grid-column-index=\"4\"]//button[2]"));
        }
        return viewButton;
    }

    public WebElement blockDescription(WebElement blockName)
    {
        return blockName.findElement(By.xpath("/../td[@data-kendo-grid-column-index=\"3\"]"));

    }
}
