package alia.nazeel.templates;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BaseGridPage {
    protected WebDriver driver;
    protected By gridLocator;

    public BaseGridPage(WebDriver driver, By gridLocator) {
        this.driver = driver;
        this.gridLocator = gridLocator;
    }

    public List<WebElement> getGridRows() {
        WebElement grid = driver.findElement(gridLocator);
        return grid.findElements(By.cssSelector("tr")); // Assuming each row is a <tr> element
    }

    public WebElement getGridCell(int rowIndex, int columnIndex) {
        List<WebElement> rows = getGridRows();
        if (rowIndex >= rows.size()) {
            throw new IndexOutOfBoundsException("Row index out of bounds");
        }
        // Locate the cell using data-kendo-grid-column-index attribute
        WebElement cell = rows.get(rowIndex).findElement(By.xpath(".//td[@data-kendo-grid-column-index='" + columnIndex + "']"));
        return cell;
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
}