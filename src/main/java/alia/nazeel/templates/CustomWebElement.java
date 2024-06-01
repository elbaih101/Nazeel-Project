package alia.nazeel.templates;

import org.openqa.selenium.WebElement;

/**
 * Base Custom WebElement for
 */
public class CustomWebElement
{
    private final WebElement element;

    public CustomWebElement(WebElement element) {
        this.element = element;
    }
}
