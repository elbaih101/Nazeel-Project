package alia.nazeel.testRunner;
import alia.nazeel.templates.BaseTestNGCucumberRunner;
import alia.nazeel.pojos.UserDataReader;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
@SuppressWarnings("EmptyMethod")
@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"alia.nazeel.stepDefs"},
                tags = "@Group2",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumberBaseReport/cucumber2.html",
                        "json:target/cucumberBaseReport/cucumber2.json",
                        "junit:target/cucumberBaseReport/cucumber2.xml",
                        "rerun:target/cucumberBaseReport/cucumber2.txt",


                }


        )
public class Group2Runners extends BaseTestNGCucumberRunner {
//   to run in parallel
static {
    setUser(UserDataReader.getNextUser());}
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
