package alia.nazeel.pages.setuppages;

import alia.nazeel.templates.BasePage;
import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@SuppressWarnings("unused")
public class P06_FloorsPage extends BasePage
{


    public P06_FloorsPage(WebDriver driver) {
       super(driver);
    }

    @FindBy(xpath = "//ul[@role=\"listbox\"]")
    WebElement genralListBox;
    @FindBy(xpath = "//Button[contains(text(),\" New Floor  \")]")
    public WebElement newFloorButton;
    @FindBy(xpath = "//div[contains(@class,\"n-pager__info\")]")
    WebElement pagination;

    public boolean checkPagination() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "on pagination");
        }
        int itemsCount = Integer.parseInt(StringUtils.substringBetween(pagination.getText(), "-", " "));
        return floorsNames.size() == itemsCount;
    }

    //////  floors grid ///////////
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"0\"]")
    public List<WebElement> blocksNames;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> floorsNames;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"4\"]")
    public List<WebElement> floorsDescriptions;
    @FindBy(xpath = "//kendo-grid-list//td[@data-kendo-grid-column-index=\"2\"]")
    public List<WebElement> floorsOrder;

    public WebElement floorViewButton(String nameOFFloor) {

        for (WebElement floorName : floorsNames) {
            if (floorName.getText().contains(nameOFFloor)) {
                return floorName.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"5\"]//button[2]"));
            }
        }
        return null;
    }

    public WebElement floorMoreMenu(String nameOFFloor, String orderOFFloor) {
        String menuPath = "..//td[@data-kendo-grid-column-index=\"5\"]//div/div";
        if (nameOFFloor != null)
            for (WebElement floor : floorsNames) {

                if (floor.getText().contains(nameOFFloor)) {
                    return floor.findElement(By.xpath(menuPath));
                }
            }
        if (orderOFFloor != null)
            for (WebElement floor : floorsOrder
            ) {


                if (floor.getText().contains(orderOFFloor)) {
                    return floor.findElement(By.xpath(menuPath));
                }
            }

        return null;
    }

    public WebElement floorDescription(String nameOFFloor) {
        for (WebElement floorName : floorsNames) {
            if (floorName.getText().contains(nameOFFloor)) {
                return floorName.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"4\"]"));
            }
        }
        return null;
    }

    public WebElement floorOrder(String nameOFFloor) {
        for (WebElement floorName : floorsNames) {
            if (floorName.getText().contains(nameOFFloor)) {
                return floorName.findElement(By.xpath("..//td[@data-kendo-grid-column-index=\"1\"]"));
            }
        }
        return null;
    }

    @FindBy(xpath = "//div[@class=\"popup__item popup__item--red\"][contains(text(),\" Delete \")]")
    public WebElement floorDeleteButton;


    /////// filter menu ////////////
    @FindBy(xpath = "//Button[contains(text(),\" Filter \")]")
    public WebElement filterButton;
    @FindBy(xpath = "//label[contains(text(),\"Block\")]/..//span/span/span")
    WebElement filterBlocksDroplistButton;
    @FindBy(xpath = "//input[@name=\"order\"]")
    public WebElement filterOrderField;
    @FindBy(xpath = "//input[@name=\"name\"]")
    public WebElement filterNmeField;
    @FindBy(id = "desc")
    public WebElement filterDescriptionField;
    @FindBy(xpath = "//Button[contains(text(),\" Search \")]")
    public WebElement filterSearchButton;

    //////// new floor popup //////////////
    @FindBy(xpath = "//div[@role=\"dialog\"]//label[contains(text(),\"Block\")]/..//span/span/span")
    WebElement blocksDroplistButton;
    @FindBy(xpath = "//kendo-dialog//div[1]/input")
    public WebElement floorNameField;
    @FindBy(xpath = "//kendo-dialog//div[1]/textarea[@name=\"text-area\"]")
    public WebElement descriptionField;
    @FindBy(xpath = "//Button[contains(text(),\" Add Floor \")]")
    public WebElement addFloorButton;


    //////////// View mode popup ////////////////////
    @FindBy(xpath = "//Button[contains(text(),\"Edit Floor\")]")
    public WebElement viewModeEditButton;

    /////////// edit mode popup //////////////
    @FindBy(xpath = "//Button[contains(text(),\"Save Changes\")]")
    public WebElement editModeSaveButton;
    @FindBy(className = "toast-message")
    public WebElement toastMsg;


    @FindBy(xpath = "//div[@class=\"swal2-popup swal2-modal swal2-show\"]")
    public WebElement confirmationPopUp;


    public List<WebElement> blocks() {
        blocksDroplistButton.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }

    public List<WebElement> filterblocks() {
        filterBlocksDroplistButton.click();
        return genralListBox.findElements(By.xpath("//li[@role=\"option\"]"));

    }


    public WebElement confirmationSaveButton() {
        return confirmationPopUp.findElement(By.xpath("//button[contains(text(),\"Yes\")]"));
    }


}
