package alia.nazeel.pages.setuppages;

import alia.nazeel.templates.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class P05_SetupPage extends BasePage
{

    public P05_SetupPage(WebDriver driver) {
        super(driver);
    }




    @FindBy(xpath = "//span[contains(text(),\"Company\")]")
    public WebElement companyDroplist;

    @FindBy(xpath = "//a[@href=\"/company/propertysetup\"]")
    public WebElement propertiesLink;

    @FindBy(xpath = "//span[contains(text(),\"Blocks & Floors\")]")
    public WebElement blocksAndFloorsDroplist;
    @FindBy(xpath = "//a[@href=\"/block-Floor/blooks\"]")
    public WebElement blocksLink;

    @FindBy(xpath = "//a[@href=\"/block-Floor/floors\"]")
    public WebElement floorsLink;
    @FindBy(xpath = "//span[contains(text(),\"Units\")]")
    public WebElement unitsDroplist;

    @FindBy(xpath = "//a[@href=\"/units-management/unit-type-customization\"]")
    public WebElement typeCustomizationLink;
    @FindBy(xpath = "//a[@href=\"/units-management/unit-setup\"]")
    public WebElement unitSetupLink;
    @FindBy(xpath = "//a[@href=\"/general-settings/unit-amenities\"]")
    public WebElement amenitiesLink;
    @FindBy(xpath = "//a[@href=\"/units-management/unit-merge-settings\"]")
    public WebElement mergeSettingsLink;

    @FindBy(xpath = "//a[@href=\"/financial/base-rate/edit\"]")
    public WebElement baseRateLink;
    @FindBy(xpath = "//a[@href=\"/financial/seasonal-rates\"]")
    public WebElement seasonalRateLink;
    @FindBy(xpath = "//a[@href=\"/financial/special-rates\"]")
    public WebElement specialRateLink;

    @FindBy(xpath = "//span[contains(text(),\"Reporting\")]")
    public WebElement printingDropList;

    @FindBy(xpath = "//a[@href=\"/reporting/printing-options\"]")
    public WebElement printingOptionLink;

    @FindBy(xpath = "//span[contains(text(),\"Financial\")]")
    public WebElement financialDropList;
    @FindBy(xpath = "//a[@href=\"/financial/taxesCustomization\"]")
    public WebElement taxesAndFeesLink;
    @FindBy(xpath = "//a[@href=\"/financial/CostCenter\"]")
    public WebElement costCentersLink;
    @FindBy(xpath = "//a[@href=\"/financial/currency-customization\"]")
    public WebElement currenciesLink;
    @FindBy(xpath = "//span[contains(text(),\"Rules\")]")
    public WebElement rulesDropList;
    @FindBy(xpath = "//a[@href=\"/general-settings/reservation\"]")
    public WebElement reservationRulesLink;
    @FindBy(xpath = "//a[@href=\"/policies/penalities\"]")
    public WebElement penaltiesLink;
    @FindBy(xpath = "//a[@href=\"/control-panel/discount-types\"]")
    public WebElement discountTypesLink;
    @FindBy(xpath = "//a[contains(@class,\"n-drawer__item\")]//span[contains(text(),\"Outlets\")]")
    public WebElement outletsDropList;
    @FindBy(xpath = "//a[@href=\"/items-addons/outlet-setup\"]")
    public WebElement outletSetupLink;
    @FindBy(xpath = "//a[@href=\"/items-addons/outlets-categories\"]")
    public WebElement categoriesLink;
    @FindBy(xpath = "//a[@href=\"/items-addons/items-setup\"]")
    public WebElement itemsLink;

    @FindBy(css = "a[href=\"/subscription-management/service-management\"]")
    public WebElement subscriptionsLink;

}
