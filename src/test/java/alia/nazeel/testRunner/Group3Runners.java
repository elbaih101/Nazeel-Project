package alia.nazeel.testRunner;
import alia.nazeel.templates.BaseTestNGCucumberRunner;
import alia.nazeel.pojos.UserDataReader;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions
        (
                features = "src/main/resources/features",
                glue = {"alia.nazeel.stepDefs"},
                tags = "@Group3",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumber-reports/cucumber3.html",
                        "json:target/cucumber-reports/cucumber3.json",
                        "junit:target/cucumber-reports/cucumber3.xml",
                        "rerun:target/cucumber-reports/cucumber3.txt",
                }
        )
public class Group3Runners extends BaseTestNGCucumberRunner {
    //   to run in parallel
    static {
        setUser(UserDataReader.getNextUser());}
    @SuppressWarnings({"DefaultAnnotationParam", "EmptyMethod"})
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
