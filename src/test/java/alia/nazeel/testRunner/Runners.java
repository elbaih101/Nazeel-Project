package alia.nazeel.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"alia.nazeel.stepDefs"},
                tags = "@LoadMoreAction",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumberBae/cucumber1.html",
                        "json:target/cucumberBae/cucumber1.json",
                        "junit:target/cucumberBae/cucumber1.xml",
                        "rerun:target/cucumberBae/cucumber.1txt",


                }


        )


public class Runners extends AbstractTestNGCucumberTests {
//   to run in parallel
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}