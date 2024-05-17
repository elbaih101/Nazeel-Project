package alia.nazeel.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"alia.nazeel.stepDefs"},
                tags = "@Group4",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumberBaseReport/cucumber4.html",
                        "json:target/cucumberBaseReport/cucumber4.json",
                        "junit:target/cucumberBaseReport/cucumber4.xml",
                        "rerun:target/cucumberBaseReport/cucumber4.txt",


                }


        )


public class Group4Runners extends AbstractTestNGCucumberTests {
//   to run in parallel
    @SuppressWarnings({"DefaultAnnotationParam", "EmptyMethod"})
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
