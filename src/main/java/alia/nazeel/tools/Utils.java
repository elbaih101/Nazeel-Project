package alia.nazeel.tools;

import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;
import java.util.*;

/**
 * Class containing various utility methods for WebDriver operations.
 *
 * @author Moustafa Elbaih
 */
@SuppressWarnings("GrazieInspection")
public class Utils {

    /**
     * @param dateInput Web element input
     * @param date      date on format DD/MM/YYYY
     */
    public static void setDate(WebElement dateInput, String date) {
        dateInput.click();
        date = StringUtils.remove(date, '/');
        dateInput.sendKeys(Keys.HOME);
        dateInput.sendKeys(date);
    }

    /**
     * sets a given date time string into a date time field web element
     * consider the date time format it only removes  the '/' and ':' and '' and append strings in the place
     *
     * @param dateInput the input date time WebElement
     * @param dateTime  String on form of (YYYY/MM/DD HH:MM AM) or (DD/MM/YYYY HH:mm AM)
     */
    public static void setDateTime(WebElement dateInput, String dateTime) {
        dateInput.click();
        dateTime = StringUtils.remove(dateTime, '/');
        dateTime = StringUtils.remove(dateTime, ':');
        dateTime = StringUtils.remove(dateTime, ' ');
        dateTime = StringUtils.remove(dateTime, '-');
        dateInput.sendKeys(Keys.HOME);
        dateInput.sendKeys(dateTime);
    }


    /**
     * this function sets the time in time fields with
     *
     * @param timeInput the time field
     * @param time      the time in the form (HH:mm AM/PM)
     */
    public static void setTime(WebElement timeInput, String time) {
        timeInput.click();
        time = StringUtils.remove(time, ':');
        time = StringUtils.remove(time, ' ');
        timeInput.sendKeys(Keys.ARROW_LEFT);
        timeInput.sendKeys(Keys.ARROW_LEFT);
        timeInput.sendKeys(time);

    }

    /**
     * Checks if a given time falls between two times.
     *
     * @param timeToCheck the time to check
     * @param startTime   the start time of the range
     * @param endTime     the end time of the range
     * @return true if the timeToCheck is within the range, false otherwise
     */
    public static boolean isTimeWithinRange(DateTime timeToCheck, DateTime startTime, DateTime endTime) {


        // Check if the time falls between the start and end time
        if (startTime.isBefore(endTime)) {
            return timeToCheck.isAfter(startTime) && timeToCheck.isBefore(endTime);
        } else { // Handle cases where the time range wraps around midnight
            return timeToCheck.isAfter(startTime) || timeToCheck.isBefore(endTime);
        }
    }

    /**
     * function to click on an element and move it to another desired element location
     *
     * @param toMove the element to be moved
     * @param moveTo the element to move to
     * @param driver WebDriver
     */
    public static void moveElement(WebElement toMove, WebElement moveTo, WebDriver driver) {
        new Actions(driver)
                .moveToElement(toMove)
                .pause(Duration.ofSeconds(1))
                .clickAndHold(toMove)
                .pause(Duration.ofSeconds(1))
                .moveByOffset(1, 0)
                .moveToElement(moveTo)
                .moveByOffset(1, 0)
                .pause(Duration.ofSeconds(1))
                .release().perform();
    }

    /**
     * function to sleep the thread with desired time in millis
     *
     * @param milliSeconds time in millis
     */
    public static void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public static boolean isSorted(List<String> listOfStrings) {
        return isSorted(listOfStrings, listOfStrings.size());
    }

    /**
     * this function uses the actions to draw a simple outline to check signature and free draw fields
     *
     * @param driver  WebDriver
     * @param element the free draw filed
     */
    public static void draw(WebDriver driver, WebElement element) {
        Actions builder = new Actions(driver);
        Action drawAction = builder.moveToElement(element, 135, 15) //start points x axis and y axis.
                .click()
                .moveByOffset(200, 60) // 2nd points (x1,y1)
                .click()
                .moveByOffset(100, 70)// 3rd points (x2,y2)
                .doubleClick()
                .build();
        drawAction.perform();
    }

    /**
     * check if a list of strings is sorted from a desired index
     *
     * @param listOfStrings the list of strings
     * @param index         start position of the function
     * @return boolean result of the the function
     */
    public static boolean isSorted(List<String> listOfStrings, int index) {
        if (index < 2) {
            return true;
        } else if (listOfStrings.get(index - 2).compareTo(listOfStrings.get(index - 1)) > 0) {
            return false;
        } else {
            return isSorted(listOfStrings, index - 1);
        }
    }

    /**
     * supposedly gets the styles of an element
     */
    public static String getStyles = "var s = '';" +
            "var o = getComputedStyle(arguments[0]);" +
            "for(var i = 0; i < o.length; i++){" +
            "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
            "return s;";

    /**
     * it sets an attribute  for a web element using java script
     *
     * @param js       the java script executer
     * @param element  the element to be changed its attribute
     * @param attName  the attribute name
     * @param attValue the attribute value to be changed to
     */
    public static void setAttribute(JavascriptExecutor js, WebElement element, String attName, String attValue) {
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attName, attValue);
    }

    /**
     * returns if an element is enabled or not
     *
     * @param element WebElement to be checked if enabled or not
     * @return boolean enabled or not
     */
    public static boolean isEnabled(WebElement element) {
        boolean bol = true;
        if (!element.isEnabled())
            return false;
        if (element.getAttribute("aria-disabled") != null && element.getAttribute("aria-disabled").equalsIgnoreCase("true"))
            return false;
        try {
            element.findElement(By.xpath("./ancestor-or-self::*[contains(@class,\"k-state-disabled\")]"));
            bol = false;

        } catch (NoSuchElementException ignored) {
        }
        return bol;
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
        File[] files = folder.listFiles();
        for (int i = 0; i < count; i++) {
            File uploadFile = files != null ? files[i] : null;
            element.sendKeys(uploadFile != null ? uploadFile.getAbsolutePath() : null);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //noinspection CallToPrintStackTrace
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

    /**
     * Check whether a file is downloaded by searching a path for the file name
     *
     * @param downloadPath path for where the file may exist
     * @param fileName     name of the file
     * @return boolean ensuring file is downloaded or not
     */
    public static boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        assert dirContents != null;
        for (File dirContent : dirContents) {
            if (dirContent.getName().equals(fileName)) {
                // File has been found, it can now be deleted:
//                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    /**
     * using java script to opn a new empty tab in browser
     *
     * @param driver driver initiated to handle the tab opening
     * @throws InterruptedException Exception
     */
    public void openNewTab(WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('');");
        Thread.sleep(100);
    }

    /**
     * goes to a tab by its index
     *
     * @param driver   WebDriver initiated to handle the tab switching
     * @param tabIndex index of the tab
     * @throws InterruptedException exception
     */
    public void gotoTab(WebDriver driver, int tabIndex) throws InterruptedException {
        List<String> winHandles = new ArrayList<>(driver.getWindowHandles());
        Thread.sleep(500);
        driver.switchTo().window(winHandles.get(tabIndex));
    }

    /**
     * Rounds the given double to the int places
     *
     * @param value  double value of number to be rounded to be rounded to
     * @param places int number of places
     * @return double rounded number
     */
    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }


    /**
     * @param scenario the current Scenario running
     * @param driver   WebDriver initiated to take screenshot
     * @throws Exception Throws WebDriver/ClassCast Exceptions
     */
    public static void screenShotOnFailure(Scenario scenario, WebDriver driver) throws Exception {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String testName = scenario.getName();

                FileUtils.writeByteArrayToFile(new File("target/screenshots/" + testName + "/" + scenario.getId() + scenario.getLine() + ".png"), screenshot);
                scenario.attach(screenshot, "image/png", testName);
            } catch (WebDriverException wde) {
                System.err.println(wde.getMessage());
            } catch (ClassCastException cce) {
                //noinspection CallToPrintStackTrace
                cce.printStackTrace();
            }
        }
    }


    /**
     * function to return pre sett edge printing and download options
     *
     * @return EdgeOptions pre configured
     */
    public static EdgeOptions edgePrintingAndDownloadOptions() {
        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("prefs", new String[]{"download.default_directory", "download_path"});

        //printer config
        options.addArguments("--kiosk-printing");
        //download config    // relates to this import ::   import com.microsoft.edge.seleniumtools.EdgeOptions;
        HashMap<String, Object> edgePrefs = new HashMap<>();
        edgePrefs.put("download.default_directory", "F:\\java maven projects\\Nazeel-Project\\src\\main\\resources\\downloaded");
        options.setExperimentalOption("prefs", edgePrefs);

        options.addArguments("print.printer_Microsoft_Print_to_PDF.print_to_filename", "F:\\java maven projects\\Nazeel-Project\\src\\main\\resources\\downloaded");
        return options;

    }


    public static void KendoSelectByValue(WebDriver driver, WebElement select, String value) {
        var selectElement = new Select(select);
        List<WebElement> options = selectElement.getOptions();
        for (int i = 0; i < options.size(); i++) {
            WebElement element = options.get(i);
            if (element.getAttribute("value").equals(value) || element.getAttribute("text").equals(value)) {
                var id = select.getAttribute("id");
                ((JavascriptExecutor) driver).executeScript(String.format("$('#%s').data('kendoDropDownList').select(%d);", id, i));
                break;
            }
        }
    }

    public static boolean isIndexInArray(int[] indicesArray, int index) {
        Set<Integer> indicesSet = new HashSet<>();
        for (int i : indicesArray) {
            indicesSet.add(i);
        }
        return indicesSet.contains(index);
    }

    public static boolean containsString(String[] arr, String target) {
        for (String str : arr) {
            if (str.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStringIgnoreCase(String[] arr, String target) {
        for (String str : arr) {
            if (str.equalsIgnoreCase(target)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStringThatContainsIgnoreCase(String[] arr, String target) {
        target = target.toLowerCase();
        for (String str : arr) {
            if (str.toLowerCase().contains(target)) {
                return true;
            }
        }
        return false;
    }


    public static void scroll(String direction, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        switch (direction.toLowerCase()) {
            case "up" -> js.executeScript("arguments[0].scrollTop = 0", element);
        case "down"->js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", element);
        }
    }
}
