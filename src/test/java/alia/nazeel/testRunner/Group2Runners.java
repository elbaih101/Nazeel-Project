package alia.nazeel.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@SuppressWarnings("EmptyMethod")
@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"org.example.stepDefs"},
                tags = "@Group2",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumberBaseReport/cucumber2.html",
                        "json:target/cucumberBaseReport/cucumber2.json",
                        "junit:target/cucumberBaseReport/cucumber2.xml",
                        "rerun:target/cucumberBaseReport/cucumber2.txt",


                }


        )


public class Group2Runners extends AbstractTestNGCucumberTests {
//   to run in parallel
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
