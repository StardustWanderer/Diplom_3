package praktikum.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class BrowserFactory {

    private static final String YANDEX_DRIVER_PATH =
            "C:/WebDriver/bin/yandexdriver.exe";

    private static final String YANDEX_BROWSER_EXE_PATH =
            "C:/Program Files/Yandex/YandexBrowser/Application/browser.exe";


    public static WebDriver createDriver() {
        //дефолтное значение
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

    //Selenium Manager перехватывает запуск Яндекс Браузера и подсовывает driver от Chrome,
    // по этому явно укажем путь к драйверу и исполняемому файлу
    private static WebDriver startBrowserYandex() {
        // 1. Жестко задаем свой файл драйвера через Service, отключая авто-подбор Selenium Manager
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(YANDEX_DRIVER_PATH))
                .build();

        // 2. Явно указываем путь к бинарнику Яндекс Браузера
        ChromeOptions options = new ChromeOptions();
        options.setBinary(YANDEX_BROWSER_EXE_PATH);

        // 3. Запускаем драйвер с нужным драйвером и указанием пути к
        WebDriver driver = new ChromeDriver(service, options);
        driver.manage().window().maximize();
        return driver;
    }
}