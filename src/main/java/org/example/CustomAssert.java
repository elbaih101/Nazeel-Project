package org.example;

import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.function.Predicate;

public class CustomAssert extends SoftAssert {

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
     * @param a
     * @param b
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


}
