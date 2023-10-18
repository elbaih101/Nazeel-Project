package org.example;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class TestData
{

    public static String baseUrl ="http://staging.nazeel.net:9002/";
    public static String username= "Mostafa Hamed";

    public static String password = "123456&Mh";

    public static String accessCode = "00720";
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
