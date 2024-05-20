package alia.nazeel.testRunner;

import alia.nazeel.templates.BaseTestNGCucumberRunner;
import alia.nazeel.pojos.UserDataReader;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"alia.nazeel.stepDefs"},
                tags = "@Group1 and not @Property",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumberBaseReport/cucumber1.html",
                        "json:target/cucumberBaseReport/cucumber1.json",
                        "junit:target/cucumberBaseReport/cucumber1.xml",
                        "rerun:target/cucumberBaseReport/cucumber.1txt",


                }


        )
public class Group1Runners extends BaseTestNGCucumberRunner {
    static {
        setUser(UserDataReader.getNextUser());}
//   to run in parallel
    @SuppressWarnings({"DefaultAnnotationParam", "EmptyMethod"})
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}