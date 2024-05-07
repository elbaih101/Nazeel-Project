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
    public void AssertContains(List<String> a, String b) {
        for (String s:a){
        assertTrue(s.contains(b), "Expected: " + a + "\nActual: " + b);}
    }

    public void AssertEqualsIgnoreCase(String expected, String actual) {

        assertTrue(expected.equalsIgnoreCase(actual), "Expected :" + expected + "\nActual: " + actual);
    }

    public void AssertEqualsIgnoreCase(String expected, String actual, String expectedName) {
        assertTrue(expected.equalsIgnoreCase(actual), "Expected " + expectedName + ":" + expected + "\nActual: " + actual);
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
