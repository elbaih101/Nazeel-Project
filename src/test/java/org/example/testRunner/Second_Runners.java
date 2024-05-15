package org.example.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"org.example.stepDefs"},
                tags = "@CorporateSetup",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumber.html",
                        "json:target/cucumber.json",
                        "junit:target/cucumber.xml",
                        "rerun:target/cucumber.txt",


                }


        )


public class Second_Runners extends AbstractTestNGCucumberTests {
//   to run in parallel
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
