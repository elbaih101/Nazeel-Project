package org.example.stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.TestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class Hooks
{
    public static WebDriver driver;

    @Before
public static void start()
    {
        WebDriverManager.edgedriver().setup();
        driver=new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(TestData.baseUrl);
    }


    @After
    public static void end()
    { try {
        Thread.sleep(5000);
    }catch(InterruptedException e){
        e.printStackTrace();}
        driver.quit();
    }
}
