package alia.nazeel.pages.setuppages.subscriptions;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.templates.BasePage;
import alia.nazeel.tools.Utils;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class P36_1_RenewSubscription extends BasePage {

    public P36_1_RenewSubscription(WebDriver driver) {
        super(driver);
    }

    int[][] dimension = new int[3][2];
    @FindBy(css = "subscription-renew> kendo-grid")
    List<WebElement> mainServiceselement;
    @FindBy(xpath = "//subscription-renew//div[contains(@class,\"page-header\")]//div[@class=\"n-button\"]")
    public WebElement walletBalanceDiv;
    @FindBy(xpath = "//kendo-panelbar-item[.//*[starts-with(text(),\"Subscriped\")]]")
    WebElement subscripedPanel;
    @FindBy(xpath = "//kendo-panelbar-item[.//*[starts-with(text(),\"Not Subscriped\")]]")
    WebElement notSubscripedPanel;

    public List<KendoGrid> mainServicesGrids() {
        wait.waitLoading();
        List<KendoGrid> grids = new ArrayList<>();
        for (WebElement e : mainServiceselement) {
            grids.add(new KendoGrid(e));
        }
        return grids;
    }

    public List<KendoGrid> subscripedServices() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            if (!subscripedPanel.getAttribute("class").contains("k-state-expanded"))
                subscripedPanel.click();
            List<WebElement> list = subscripedPanel.findElements(By.xpath(".//kendo-grid"));
            List<KendoGrid> grids = new ArrayList<>();
            for (WebElement e : list) {
                grids.add(new KendoGrid(e));
            }
            return grids;
        } catch (NoSuchElementException e) {
            LoggerFactory.getLogger(this.getClass()).error("no subscribed extras");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        return new ArrayList<>();
    }

    public List<KendoGrid> unSubscripedServices() {
        if (!notSubscripedPanel.getAttribute("class").contains("k-state-expanded"))
            notSubscripedPanel.click();
        List<WebElement> list = notSubscripedPanel.findElements(By.xpath(".//kendo-grid"));
        List<KendoGrid> grids = new ArrayList<>();
        for (WebElement e : list) {
            grids.add(new KendoGrid(e));
        }
        return grids;
    }


    public WebElement selectService(String serviceName) {
        AtomicReference<WebElement> service = new AtomicReference<>();
        int i = 0;
        int j = 0;
        if (serviceName.toLowerCase().contains("nazeel") || serviceName.toLowerCase().contains("sms"))
            for (KendoGrid kendoGrid : mainServicesGrids()) {
                for (WebElement n : kendoGrid.getGridCells(1)) {
                    if (n.getText().toLowerCase().contains(serviceName.toLowerCase())) {
                        WebElement selectionCheckBox = kendoGrid.getGridCell(n, 0).findElement(By.xpath(".//input"));
                        if (!selectionCheckBox.isSelected())
                            selectionCheckBox.click();
                        service.set(n);
                        dimension[i][j] = 1;
                        return service.get();
                    }
                }
                j++;
            }
        i++;
        j = 0;

        for (KendoGrid kendoGrid : subscripedServices()) {
            for (WebElement n : kendoGrid.getGridCells(1)) {
                if (n.getText().toLowerCase().contains(serviceName.toLowerCase())) {
                    kendoGrid.getGridCell(n, 0).findElement(By.xpath(".//input")).click();
                    service.set(n);
                    dimension[i][j] = 1;
                    return service.get();
                }
            }
            j++;
        }
        i++;
        j = 0;
        for (KendoGrid kendoGrid : unSubscripedServices()) {
            for (WebElement n : kendoGrid.getGridCells(1)) {
                if (n.getText().toLowerCase().contains(serviceName.toLowerCase())) {
                    kendoGrid.getGridCell(n, 0).findElement(By.xpath(".//input")).click();
                    service.set(n);
                    dimension[i][j] = 1;
                    return service.get();
                }
            }
        }
        if (service.get() == null)
            throw new RuntimeException("service not Found in Services");
        return null;
    }

    public void selectPeriod(WebElement service, String text) {

        KendoGrid selectedGrid = getSelectedGrid();
        int columnIndex = Integer.parseInt(selectedGrid.getHeaderCellContains("subscription period").getAttribute("aria-colindex")) - 1;
        KendoComboBox periodComboBox = new KendoComboBox(selectedGrid.getGridCell(service, columnIndex).findElement(By.xpath(".//kendo-combobox")));
        if (text.toLowerCase().contains("day")) {
            periodComboBox.selectByTextContainsIgnoreCase("day");
            text = Utils.extractIntegers(text).getFirst().toString();
            if (!text.isEmpty()){
                periodComboBox.findElementBy(By.xpath("./following-sibling::kendo-numerictextbox//input")).sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
                periodComboBox.findElementBy(By.xpath("./following-sibling::kendo-numerictextbox//input")).sendKeys(text);}
        } else
            periodComboBox.selectByTextContainsIgnoreCase(text);
    }


    public void addBalance(WebElement service, String balance) {
        WebElement balanceField = getField("added balance", service);
        if (balanceField != null) {
            balanceField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            balanceField.sendKeys(balance);
        }
    }

    public void setPrice(WebElement service, String price) {

        WebElement priceField = getField("Price", service);
        assert priceField != null;
        priceField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        priceField.sendKeys(price);
    }

    public void setVat(WebElement service, String vat) {

        WebElement vatField = getField("vat", service);
        assert vatField != null;
        vatField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        vatField.sendKeys(vat);
    }

    public String getServicePrice(WebElement selectedService) {
        String price = getField("Price", selectedService).findElement(By.cssSelector("input")).getAttribute("aria-valuenow");
        return price.replaceAll(",", "");
    }

    public WebElement getField(String headerName, WebElement service) {
        KendoGrid selectedGrid = getSelectedGrid();
        int columnIndex = Integer.parseInt(selectedGrid.getHeaderCellContains(headerName).getAttribute("aria-colindex")) - 1;
        return selectedGrid.getGridCell(service, columnIndex);
    }

    public Period getSubscriptionPEriod(WebElement service) {
        Period period;
        String periodString;
        WebElement periodField = getField("Period", service);

        periodString = periodField.findElement(By.xpath(".//kendo-combobox//input")).getAttribute("value");

        if (periodString.contains("Day")) {
            periodString = periodField.findElement(By.xpath(".//kendo-numerictextbox//input")).getAttribute("value");
            periodString = String.format("P%sD", periodString);
        } else {
            periodString = Utils.extractIntegers(periodString).getFirst().toString();
            periodString = String.format("P%sM", periodString);
        }
        period = Period.parse(periodString);

        return period;

    }

    public void sendComment(WebElement service, String comment) {

        WebElement commentField = getField("comment", service);
        assert commentField != null;
        commentField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        commentField.sendKeys(comment);
    }

    private KendoGrid getSelectedGrid() {
        int[] coordinates = Utils.findElement2D(dimension, 1);
        int columnIndex;
        List<KendoGrid> selectedGridGroup = new ArrayList<>();
        KendoGrid selectedGrid;
        switch (Objects.requireNonNull(coordinates)[0]) {
            case 0 -> selectedGridGroup = mainServicesGrids();

            case 1 -> selectedGridGroup = subscripedServices();


            case 2 -> selectedGridGroup = unSubscripedServices();

        }
        selectedGrid = selectedGridGroup.get(coordinates[1]);
        return selectedGrid;
    }
}
