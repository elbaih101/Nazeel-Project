package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class KendoGrid {
    final WebElement grid;
    final private WebElement list;
    WebElement header;
    private final By lastCellBy = By.xpath("..//td[last()]");

    public KendoGrid(WebElement grid) {
        this.grid = grid;
        list = grid.findElement(By.xpath(".//tbody"));
        header = grid.findElement(By.xpath(".//thead"));
    }

    WebElement getHeader() {
        return header;
    }

    public WebElement getHeaderCellContains(String text) {
        List<WebElement> columnsHeads = header.findElements(By.tagName("th"));
        for (WebElement head : columnsHeads) {
            if (head.getText().toLowerCase().contains(text.toLowerCase()))
                return head;
        }
        return null;
    }

    public List<WebElement> getGridRows() {

        return list.findElements(By.cssSelector("tr")); // Assuming each row is a <tr> element
    }

    WebElement getPager() {
        return grid.findElement(By.cssSelector("kendo-pager"));
    }

    public void selectPageSize(int pageSize) {
        Select select = new Select(getPager().findElement(By.cssSelector(".n-pager__sizes kendo-pager-page-sizes select")));
        select.selectByValue(String.valueOf(pageSize));
    }

    public void gotoNextPage() {
        getPager().findElement(By.cssSelector("kendo-pager-next-buttons")).click();
    }

    public void gotoPreviousPage() {
        getPager().findElement(By.cssSelector("kendo-pager-prev-buttons")).click();
    }

    public void gotoPage(int page) {
        for (int i = 0; i < page; i++) {
            gotoNextPage();

        }
    }

    public int getTotalReocrdsSize() {
        return Integer.parseInt(getPager().findElement(By.cssSelector(".n-pager__info>span:last-of-type")).getText().replace(" of ", ""));
    }

    public int getPageSize() {
        return Integer.parseInt(getPager().findElement(By.cssSelector(".n-pager__sizes kendo-pager-page-sizes select")).getAttribute("value"));
    }

    public int getCurrentPage() {
        return (int) Math.ceil((double) Integer.parseInt(getPager().findElement(By.cssSelector("span.pagedir2")).getText().replace(" of ", "")) / (double) getPageSize());
    }

    public List<WebElement> getGridCells(int columnIndex) {
        return list.findElements(By.xpath(".//td[@data-kendo-grid-column-index='" + columnIndex + "']")); // Assuming each column is a <td> element
    }

    public WebElement getGridCell(int rowIndex, int columnIndex) {
        List<WebElement> rows = getGridRows();
        if (rowIndex >= rows.size()) {

            throw new IndexOutOfBoundsException("Row index out of bounds");
        }
        // Locate the cell using data-kendo-grid-column-index attribute
        return rows.get(rowIndex).findElement(By.xpath(".//td[@data-kendo-grid-column-index='" + columnIndex + "']"));
    }


    public WebElement getTableCell(int rowIndex, int columnnumber) {
        List<WebElement> rows = getGridRows();
        if (rowIndex >= rows.size()) {


            throw new IndexOutOfBoundsException("Row index out of bounds");
        }
        // Locate the cell using data-kendo-grid-column-index attribute
        return rows.get(rowIndex).findElement(By.xpath(".//td[" + columnnumber + "]"));
    }


    public WebElement getGridCell(WebElement baseCell, int columnIndex) {


        // Locate the cell using data-kendo-grid-column-index attribute
        return baseCell.findElement(By.xpath("..//td[@data-kendo-grid-column-index='" + columnIndex + "']"));
    }


    public void clickGridCell(int rowIndex, int columnIndex) {
        WebElement cell = getGridCell(rowIndex, columnIndex);
        cell.click();
    }


    public void setGridCellValue(int rowIndex, int columnIndex, String value) {
        WebElement cell = getGridCell(rowIndex, columnIndex);
        cell.clear();
        cell.sendKeys(value);
    }

    public WebElement getLastCell(WebElement record) {
        return record.findElement(lastCellBy);
    }

    public void clickEditRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-edit\")]]")).click();
    }

    public void clickViewRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-eye\")]]")).click();
    }

    public void clickPrintRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-print\")]]")).click();
    }

    public void clickChangeRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-change\")]]")).click();
    }

    public void clickDeleteRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-delete\")]]")).click();
    }

    public WebElement clickRecordMoreMenuButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);

        WebElement moreActionButton = actionsCell.findElement(By.xpath(".//div[.//*[name()=\"use\" and contains(@*,\"icon-more\")]]"));
        moreActionButton.click();
        return moreActionButton;
    }

    public void selectRecordActionByText(WebElement record, String text) {
        List<WebElement> actions = clickRecordMoreMenuButton(record).findElements(By.xpath("//ul[@class=\"n-table__more-actions-menu\"]//li"));
        WebElement selectedAction = actions.stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedAction.click();

    }
}