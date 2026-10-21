package praktikum.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import praktikum.config.BrowserFactory;

public abstract class BaseTest {

    protected WebDriver driver;

    @Before
    public void setUp(){
        this.driver = BrowserFactory.createDriver();
    }

    @After
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
