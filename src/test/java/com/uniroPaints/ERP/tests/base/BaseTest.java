package com.uniroPaints.ERP.tests.base;

import com.uniroPaints.ERP.utils.PropertyFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public class BaseTest {

    protected static WebDriver driver;

    PropertyFileReader prop = new PropertyFileReader();
    String url = prop.getProperty("config","url");

    @BeforeTest
    public void setup() {
        if (driver == null) { // Start browser only if not already started
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get(url);
        }
    }

//    @AfterTest
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//            driver = null; // Reset driver for the next test run
//        }
//    }
}

