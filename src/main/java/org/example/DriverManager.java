package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.enums.Driver_Mode;
import org.example.enums.Drivers;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

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


    public static void initializeDriver(Drivers driverName, Driver_Mode mode) {
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
        setDriver(driver);


    }
}

