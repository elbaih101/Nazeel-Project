package alia.nazeel.kendoelements;

import alia.nazeel.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class KendoGrid implements WebElement {
    final WebElement grid;
    WebElement list;
    WebElement header;
    WebElement pager;

    public KendoGrid(WebElement grid) {
        this.grid = grid;

    }

    public List<WebElement> getGridRows() {
        header = grid.findElement(By.xpath(".//thead"));
        list = grid.findElement(By.xpath(".//tbody"));
        return list.findElements(By.cssSelector("tr")); // Assuming each row is a <tr> element
    }

    WebElement getPager() {
        return pager = grid.findElement(By.cssSelector("kendo-pager"));
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

    public void clickEditRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(By.xpath(".//td[last()]"));
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-edit\")]]")).click();
    }

    public void clickViewRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(By.xpath(".//td[last()]"));
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-eye\")]]")).click();
    }

    public void clickPrintRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(By.xpath(".//td[last()]"));
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-print\")]]")).click();
    }
    public void clickChangeRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(By.xpath(".//td[last()]"));
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-change\")]]")).click();
    }
    public void clickDeleteRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(By.xpath(".//td[last()]"));
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-delete\")]]")).click();
    }

    public WebElement clickRecordMoreMenuButton(WebElement record) {
        WebElement actionsCell = record.findElement(By.xpath(".//td[last()]"));

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

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }

    @Override
    public void click() {
    }

    @Override
    public void submit() {
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
    }

    @Override
    public void clear() {
    }

    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public String getDomProperty(String name) {
        return WebElement.super.getDomProperty(name);
    }

    @Override
    public String getDomAttribute(String name) {
        return WebElement.super.getDomAttribute(name);
    }

    @Override
    public String getAttribute(String name) {
        return "";
    }

    @Override
    public String getAriaRole() {
        return WebElement.super.getAriaRole();
    }

    @Override
    public String getAccessibleName() {
        return WebElement.super.getAccessibleName();
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return List.of();
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public SearchContext getShadowRoot() {
        return WebElement.super.getShadowRoot();
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String propertyName) {
        return "";
    }
}