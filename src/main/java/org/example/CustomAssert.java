package org.example;


import org.testng.asserts.SoftAssert;

public class CustomAssert extends SoftAssert {


    public void AssertContains(String a,String b)
    {
        assertTrue(a.contains(b),"Expected: "+ a+"\nActual: "+b);
    }
    public void AssertEqualsIgnoreCase(String a,String b){
        String expectedName="";
        assertTrue(a.equalsIgnoreCase(b),"Expected "+expectedName+":"+a+"\nActual: "+b);
    } public void AssertEqualsIgnoreCase(String a,String b,String expectedName){

    }
}
