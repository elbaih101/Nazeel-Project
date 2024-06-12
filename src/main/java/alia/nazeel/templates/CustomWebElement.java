package alia.nazeel.templates;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Base Custom WebElement for
 */
public class CustomWebElement
{
     public WebElement element;

    public CustomWebElement(WebElement element) {
        this.element = element;
    }

    public  boolean isEnabled() {
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
}
