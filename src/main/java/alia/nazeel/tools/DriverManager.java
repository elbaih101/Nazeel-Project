package alia.nazeel.tools;

import alia.nazeel.enums.Driver_Mode;
import alia.nazeel.enums.Drivers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * retreeaves the thread webDriver
     *
     * @return WebDriver the local Thread WebDriver
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * stores  the driver provided in its local thread reference
     *
     * @param driver WebDriver inistantiated in the thread
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }


    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    /**
     * this function initialize a local driver of any type provided then it stores it in its local thread so its thread safe
     *
     * @param driverName enum Drivers driver name provided from list of drivers
     * @param mode       enum Driver_Mode the running mode of the driver
     */
    public static void initializeDriver(Drivers driverName, Driver_Mode mode) {
        WebDriver driver ;
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
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions op = new FirefoxOptions();
                op.addArguments("start-maximized", "--ignore-certificate-errors", "--ignore-urlfetcher-cert-requests", "--guest");
                if (mode.equals(Driver_Mode.Headless))
                    op.addArguments("headless");
                driver = new FirefoxDriver(op);
            }
            case Safari -> {
                WebDriverManager.safaridriver().setup();
                SafariOptions op = new SafariOptions();
                driver = new SafariDriver(op);
            }
            default -> throw new IllegalStateException("Unexpected value: " + driverName);
        }
        driver.manage().window().setSize(new Dimension(1920, 1080));
        setDriver(driver);


    }

    public static Drivers getWebDriverType(WebDriver driver) {
        Drivers driverType = null;

        if (driver instanceof ChromeDriver) {
            driverType = Drivers.Chrome;
        } else if (driver instanceof EdgeDriver) {
            driverType = Drivers.Edge;
        } else if (driver instanceof FirefoxDriver) {
            driverType = Drivers.FireFox;
        } else if (driver instanceof SafariDriver) {
            driverType = Drivers.Safari;
        }

        return driverType;
    }
}

