package alia.nazeel.tools;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import alia.nazeel.enums.Driver_Mode;
import alia.nazeel.enums.Drivers;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    /**
     * @param dateInput Webelement input
     * @param date      date on format DD/MM/YYYY
     */
    public static void setDate(WebElement dateInput, String date) {
        dateInput.click();
        date = StringUtils.remove(date, '/');
        dateInput.sendKeys(Keys.ARROW_LEFT);
        dateInput.sendKeys(Keys.ARROW_LEFT);
        dateInput.sendKeys(date);
    }

    /**
     * function to click on an element and move it to another desired element location
     *
     * @param toMove the element to be moved
     * @param moveTo the element to move to
     * @param driver WebDriver
     */
    public static void moveelement(WebElement toMove, WebElement moveTo, WebDriver driver) {
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
     * this function stes the time in time fields with
     *
     * @param timeInput the time field
     * @param time      the time in the form HH:mm
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
     * @param element the free draw fied
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
     * it setes an attribute  for a web element using java script
     *
     * @param js       the java script excuter
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
     * @param element WebElemnet to be checked if enabled or not
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
     * @param downloadPath pathfor wich the file may exist
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
     * @param driver driver initiated to handle the tab oppening
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
     * Rounds the given double to the int pklaces
     *
     * @param value  double value of number to be roundedto be rounded to
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
     * starts a driver in the headless mode
     */
    public static WebDriver setDriver(Drivers driverName, Driver_Mode mode) {
        WebDriver driver = null;
        switch (driverName) {
            case Chrome -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions op = new ChromeOptions();
                op.addArguments("start-maximized", "--ignore-certificate-errors", "--ignore-urlfetcher-cert-requests"/*, "--guest"*/);
                if (mode.equals(Driver_Mode.Headless))
                    op.addArguments("headless");
                driver = new ChromeDriver(op);
            }
            case Edge -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions op = new EdgeOptions();
                op.addArguments("start-maximized", "--ignore-certificate-errors", "--ignore-urlfetcher-cert-requests", "--guest");
                if (mode.equals(Driver_Mode.Headless))
                    op.addArguments("headless");
                driver = new EdgeDriver(op);
            }
            case FireFox -> {
            }
            case Safari -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + driverName);
        }
        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;


    }

    /**
     * starts a web driver in ui mode
     */
//    public static WebDriver setDriverUiEdge() {
//        WebDriverManager.edgedriver().setup();
//        EdgeOptions op = new EdgeOptions();
//        op.addArguments("--ignore-certificate-errors", "--ignore-urlfetcher-cert-requests", "--guest");
//        op.setAcceptInsecureCerts(true);
//        WebDriver driver = new EdgeDriver(op);
//        driver.manage().window().setSize(new Dimension(1920, 1080));
//        return driver;
//    }
//
//    public static WebDriver setDriverUiChrome() {
//        WebDriverManager.chromedriver().setup();
//        ChromeOptions op = new ChromeOptions();
//        op.addArguments("ignore-certificate-errors", "ignore-urlfetcher-cert-requests", "--guest");
//        op.setAcceptInsecureCerts(true);
//        ChromeDriver driver = new ChromeDriver(op);
//        driver.manage().window().setSize(new Dimension(1920, 1080));
//        return driver;
//    }

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

    public static void handleCertificateAuth() {
        Thread certSelectionThread = null;
        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 10);
                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyPress(KeyEvent.VK_SPACE);
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        certSelectionThread = new Thread(r);
        certSelectionThread.start();
        if (certSelectionThread != null) {
            try {
                certSelectionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void KendoSelectByValue(WebDriver driver, WebElement select, String value)
    {
        var selectElement = new Select(select);
        List<WebElement> options = selectElement.getOptions();
        for (int i = 0; i < options.size(); i++)
        {
            WebElement element = options.get(i);
            if (element.getAttribute("value") == value || element.getAttribute("text") == value)
            {
                var id = select.getAttribute("id");
                ((JavascriptExecutor) driver).executeScript(String.format("$('#{0}').data('kendoDropDownList').select({1});", id, i));
                break;
            }
        }
    }
}