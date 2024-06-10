package alia.nazeel.pages.setuppages.subscriptions;

import alia.nazeel.kendoelements.KendoComboBox;
import alia.nazeel.kendoelements.KendoGrid;
import alia.nazeel.templates.BasePage;
import alia.nazeel.tools.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class P36_1_RenewSubscription extends BasePage {

    public P36_1_RenewSubscription(WebDriver driver) {
        super(driver);
    }

    int[][] dimension;
    @FindBy(css = "subscription-renew> kendo-grid")
    List<KendoGrid> mainServices;
    @FindBy(xpath = "//subscription-renew//div[contains(@class,\"page-header\")]//div[@class=\"n-button\"]")
    public WebElement walletBalanceDiv;
    @FindBy(xpath = "//kendo-panelbar-item[.//*[starts-with(text(),\"Subscriped\")]]")
    WebElement subscripedPanel;
    @FindBy(xpath = "//kendo-panelbar-item[.//*[starts-with(text(),\"Not Subscriped\")]]")
    WebElement notSubscripedPanel;


    public List<KendoGrid> subscripedServices() {
        if (!subscripedPanel.getAttribute("class").contains("k-state-expanded"))
            subscripedPanel.click();
        List<WebElement> list = subscripedPanel.findElements(By.xpath("//kendo-grid"));
        List<KendoGrid> grids = new ArrayList<>();
        for (WebElement e : list) {
            grids.add(new KendoGrid(e));
        }
        return grids;
    }

    public List<KendoGrid> unSubscripedServices() {
        if (!notSubscripedPanel.getAttribute("class").contains("k-state-expanded"))
            notSubscripedPanel.click();
        List<WebElement> list = notSubscripedPanel.findElements(By.xpath("//kendo-grid"));
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
        for (KendoGrid kendoGrid : mainServices) {
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
        int columnIndex = Integer.parseInt(selectedGrid.getHeaderCellContains("subscription period").getAttribute("aria-colindex")) + 1;
        KendoComboBox periodComboBox = new KendoComboBox(selectedGrid.getGridCell(service, columnIndex).findElement(By.cssSelector("kendo-combobox")));
        if (text.toLowerCase().contains("day")) {
            periodComboBox.selectByTextContainsIgnoreCase("day");
            periodComboBox.findElementBy(By.xpath("./following-sibling::kendo-numerictextbox//input")).sendKeys(Utils.extractIntegers(text).getFirst().toString());
        } else
            periodComboBox.selectByTextContainsIgnoreCase(text);
    }


    public void addBalance(WebElement service, String balance) {
                KendoGrid selectedGrid = getSelectedGrid();
        int columnIndex = Integer.parseInt(selectedGrid.getHeaderCellContains("added balance").getAttribute("aria-colindex")) + 1;
        WebElement balanceField = selectedGrid.getGridCell(service, columnIndex);
        if (balanceField != null) {
            balanceField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            balanceField.sendKeys(balance);
        }
    }

    public void setPrice(WebElement service, String price) {

        KendoGrid selectedGrid = getSelectedGrid();
        int columnIndex = Integer.parseInt(selectedGrid.getHeaderCellContains("Price").getAttribute("aria-colindex")) + 1;
        WebElement priceField = selectedGrid.getGridCell(service, columnIndex);
        assert priceField != null;
        priceField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        priceField.sendKeys(price);
    }

    public void setVat(WebElement service, String vat) {

        KendoGrid selectedGrid = getSelectedGrid();
        int columnIndex = Integer.parseInt(selectedGrid.getHeaderCellContains("vat").getAttribute("aria-colindex")) + 1;
        WebElement vatField = selectedGrid.getGridCell(service, columnIndex);
        assert vatField != null;
        vatField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        vatField.sendKeys(vat);
    }

    public void sendComment(WebElement service, String comment) {

        KendoGrid selectedGrid = getSelectedGrid();
        int columnIndex = Integer.parseInt(selectedGrid.getHeaderCellContains("comment").getAttribute("aria-colindex")) + 1;
        WebElement commentField = selectedGrid.getGridCell(service, columnIndex);
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
            case 0 -> selectedGridGroup = mainServices;

            case 1 -> selectedGridGroup = subscripedServices();


            case 2 -> selectedGridGroup = unSubscripedServices();

        }
        selectedGrid = selectedGridGroup.get(coordinates[1]);
        return selectedGrid;
    }
}
