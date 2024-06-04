package alia.nazeel.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

public class CustomAssert extends SoftAssert {

    CustomWebDriverWait wait = new CustomWebDriverWait(Duration.ofSeconds(10));

    /**
     * checks if a string contains another string
     *
     * @param a Actal String
     * @param b Expected String to be contained in a
     */
    public void AssertContains(String a, String b) {
        assertTrue(a.contains(b), "Expected: " + b + "\nActual: " + a);
    }

    /**
     * Check Each string of
     *
     * @param a Ist of Strins to combare
     * @param b the string to compare with
     */
    public void AssertContains(List<String> a, String b) {
        int matches = 0;
        String currentItem = null;
        for (String s : a) {
            currentItem = s;
            if (s.contains(b))
                matches++;
        }
        if (matches == 0)
            //noinspection DataFlowIssue
            assertTrue(false, "Expected: " + b + "\nActual: " + currentItem);
    }

    public void AssertEqualsIgnoreCase(String expected, String actual) {

        assertTrue(expected.equalsIgnoreCase(actual), "Expected :" + expected + "\nActual: " + actual);
    }

    public void AssertEqualsIgnoreCase(String expected, String actual, String expectedName) {
        assertTrue(expected.equalsIgnoreCase(actual), "Expected " + expectedName + ":" + expected + "\nActual: " + actual);
    }

    public void AssertAnyMatch(List<WebElement> a, Predicate<WebElement> condition) {

        assertTrue(a.stream().anyMatch(condition));
    }


    public void AssertAnyMatch(List<WebElement> a, Predicate<WebElement> condition, String msg) {

        assertTrue(a.stream().anyMatch(condition), msg);
    }

    public void AssertNonMatch(List<WebElement> a, Predicate<WebElement> condition) {

        assertTrue(a.stream().noneMatch(condition));
    }

    public void AssertNonMatch(List<WebElement> a, Predicate<WebElement> condition, String msg) {

        assertTrue(a.stream().noneMatch(condition), msg);
    }

    public void AssertToastMessageContains(String mesage) {
        List<WebElement> toastMsgs = DriverManager.getDriver().findElements(By.className("toast-message"));
        wait.until(ExpectedConditions.visibilityOfAllElements(toastMsgs));
        this.assertTrue(toastMsgs.getFirst().isDisplayed());
        this.assertTrue(toastMsgs.getFirst().getText().trim().toLowerCase().contains(mesage.toLowerCase()), "actual : " + toastMsgs.getFirst().getText().trim().toLowerCase() + "\nExpected : " + mesage.toLowerCase() + "\n");
    }

}
