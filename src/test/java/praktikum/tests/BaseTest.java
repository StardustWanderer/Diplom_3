package praktikum.tests;

import io.qameta.allure.Allure;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import praktikum.config.BrowserFactory;

public abstract class BaseTest {

    protected WebDriver driver;

    @Before
    public void setUp(){
        this.driver = BrowserFactory.createDriver();

        String browser = System.getProperty("browser", "chrome");
        Allure.label("browser", browser);
    }

    @After
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
