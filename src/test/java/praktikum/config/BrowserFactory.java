package praktikum.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserFactory {

    private static final String YANDEX_DRIVER_PATH =
            "C:/WebDriver/bin/yandexdriver.exe";

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome");

        if (browser.equalsIgnoreCase("chrome")) {
            return startBrowserChrome();
        } else if (browser.equalsIgnoreCase("yandex")) {
            return startBrowserYandex();
        } else {
            throw new IllegalArgumentException(
                    "Неизвестный браузер: " + browser
            );
        }
    }

    private static WebDriver startBrowserChrome() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver startBrowserYandex() {
        System.setProperty(
                "webdriver.chrome.driver",
                YANDEX_DRIVER_PATH
        );

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}