package org.example;

import org.openqa.selenium.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     *
     * @param dateInput Webelement input
     * @param date date on format DDMMYYYY
     */
    public static void setDate(WebElement dateInput,String date) {
        dateInput.click();
        dateInput.sendKeys(Keys.ARROW_LEFT);
        dateInput.sendKeys(Keys.ARROW_LEFT);
        dateInput.sendKeys(date);
    }

    public static void setAttribute(JavascriptExecutor js, WebElement element, String attName, String attValue) {
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attName, attValue);
    }

    public static void click(JavascriptExecutor js, WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * @param element    file upload element
     * @param folderPath path for the required item folder
     * @param count      number of element present in the folder
     */

    public static void fileUpload(WebElement element, String folderPath, int count) {
        File folder = new File(folderPath);
        File[] photos = folder.listFiles();
        for (int i = 0; i < count; i++) {
            File uploadFile = photos != null ? photos[i] : null;
            element.sendKeys(uploadFile != null ? uploadFile.getAbsolutePath() : null);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * @param driver WebDriver
     * @param by     the method to find element
     * @return returns the WebElement
     */

    public static WebElement retryingFind(WebDriver driver, By by) {
        WebElement element = null;
        int attempts = 0;
        while (attempts < 2) {
            try {
                element = driver.findElement(by);

                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
        return element;
    }

    /**
     * @param node BAse webElement to find through ut
     * @param by   the method to find element
     * @return returns the WebElement
     */
    public static WebElement retryingFind(WebElement node, By by) {
        WebElement element = null;
        int attempts = 0;
        while (attempts < 2) {
            try {
                element = node.findElement(by);

                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
        return element;
    }

    public static boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName)) {
                // File has been found, it can now be deleted:
//                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    public void openNewTab(WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('');");
        Thread.sleep(100);
    }

    public void gotoTab(WebDriver driver, int tabIndex) throws InterruptedException {
        List<String> winHandles = new ArrayList<>(driver.getWindowHandles());
        Thread.sleep(500);
        driver.switchTo().window(winHandles.get(tabIndex));
    }
}
