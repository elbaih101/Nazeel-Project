package org.example;


import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CustomAssert extends SoftAssert {


    public void AssertContains(String a, String b) {
        assertTrue(a.contains(b), "Expected: " + a + "\nActual: " + b);
    }

    public void AssertEqualsIgnoreCase(String a, String b) {

        assertTrue(a.equalsIgnoreCase(b), "Expected :" + a + "\nActual: " + b);
    }

    public void AssertEqualsIgnoreCase(String a, String b, String expectedName) {
        assertTrue(a.equalsIgnoreCase(b), "Expected " + expectedName + ":" + a + "\nActual: " + b);
    }

    public void AssertAnyMatch(List<Object> a, Predicate<Object> condition) {

        assertTrue(a.stream().anyMatch(condition));
    }

    public void AssertAnyMatch(List<Object> a, Predicate<Object> condition, String msg) {

        assertTrue(a.stream().anyMatch(condition), msg);
    }

    public void AssertNonMatch(List<Object> a, Predicate<Object> condition) {

        assertTrue(a.stream().noneMatch(condition));
    }

    public void AssertNonMatch(List<Object> a, Predicate<Object> condition, String msg) {

        assertTrue(a.stream().noneMatch(condition), msg);
    }


}
