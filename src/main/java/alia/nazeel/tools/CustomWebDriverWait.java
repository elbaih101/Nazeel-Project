package alia.nazeel.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Clock;
import java.time.Duration;

public class CustomWebDriverWait extends WebDriverWait
{

    public CustomWebDriverWait(WebDriver driver, Duration timeout)
    {
        super(driver, timeout);
    }

    public CustomWebDriverWait(WebDriver driver, Duration timeout, Duration sleep)
    {
        super(driver, timeout, sleep);
    }

    public CustomWebDriverWait(WebDriver driver, Duration timeout, Duration sleep, Clock clock, Sleeper sleeper)
    {
        super(driver, timeout, sleep, clock, sleeper);
    }
    public CustomWebDriverWait(Duration timeout){
        super(DriverManager.getDriver(),timeout);
    }

    public void waitLoading() {
        WebDriver driver =DriverManager.getDriver();
        try {
           driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            // Wait until the loading animation disappears or becomes stale
            this.withTimeout(Duration.ofSeconds(20))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .until(ExpectedConditions.invisibilityOf( driver.findElement(By.xpath("//app-loading-page/*"))));
        } catch (Exception e) {
            // Handle any exceptions or logging here
            e.getMessage();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
