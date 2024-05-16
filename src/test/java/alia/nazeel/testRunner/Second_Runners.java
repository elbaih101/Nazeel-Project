package alia.nazeel.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@SuppressWarnings("EmptyMethod")
@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"org.example.stepDefs"},
                tags = "@corporate_vouchers,orders",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumberBae/cucumber2.html",
                        "json:target/cucumberBae/cucumber2.json",
                        "junit:target/cucumberBae/cucumber2.xml",
                        "rerun:target/cucumberBae/cucumber2.txt",


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
