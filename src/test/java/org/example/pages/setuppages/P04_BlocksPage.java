package org.example.pages.setuppages;

import org.apache.commons.lang.StringUtils;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@SuppressWarnings("unused")
public class P04_BlocksPage {

    public P04_BlocksPage() {
        PageFactory.initElements(Hooks.driver, this);
    }

    public P04_BlocksPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }



    @FindBy(xpath = "//div[contains(@class,\"n-pager__info\")]")
    WebElement pagination;
    public boolean checkPagination(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage()+"on pagination");
        }
        int itemsCount=  Integer.parseInt(StringUtils.substringBetween(pagination.getText(),"-"," "));
        System.out.println(blocksNames.size()+" :"+itemsCount);
        return blocksNames.size()==itemsCount;
    }
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> blocksNames;
    @FindBy(xpath = "//Button[contains(text(),\" New Block \")]")
    public WebElement newBlockButton;
    @FindBy(xpath = "//Button[contains(text(),\" Filter \")]")
    public WebElement filterButton;
    @FindBy(id = "name")
    public WebElement filterNmeField;
    @FindBy(id = "description")
    public WebElement filterDexcriptionField;
    @FindBy(xpath = "//Button[contains(text(),\" Search \")]")
    public WebElement filterSearchButton;

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
    public WebElement   toastMsg;


    @FindBy(xpath = "//div[@class=\"swal2-popup swal2-modal swal2-show\"]")
    public WebElement confirmationPopUp;

    @FindBy(xpath = "//div[@class=\"popup__item popup__item--red\"][contains(text(),\" Delete \")]")
    public WebElement blockDeleteButton;

    public WebElement blockViewButton(String nameOFBlock) {
        for (WebElement blockName : blocksNames) {
            if (blockName.getText().contains(nameOFBlock)) {
                return blockName.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"4\"]//button[2]"));
            }
        }
        return null;
    }

    public WebElement blockMoreMenu(String nameOFBlock) {
        for (WebElement blockName : blocksNames) {
            if (blockName.getText().contains(nameOFBlock)) {
                return blockName.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"4\"]//div/div/div"));
            }
        }
        return null;
    }

    public WebElement blockDescription(String nameOFBlock) {
        for (WebElement blockName : blocksNames) {
            if (blockName.getText().contains(nameOFBlock)) {
                return blockName.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"3\"]"));
            }
        }
        return null;
    }

    public WebElement blockNumberOfFloors(String nameOFBlock) {
        for (WebElement blockName : blocksNames) {
            if (blockName.getText().contains(nameOFBlock)) {
                return blockName.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"2\"]"));
            }
        }
        return null;
    }


    public WebElement confirmationSaveButton() {
        return confirmationPopUp.findElement(By.xpath("//button[contains(text(),\"Yes\")]"));
    }
}
