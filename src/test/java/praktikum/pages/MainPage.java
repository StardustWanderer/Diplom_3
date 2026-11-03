package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static praktikum.config.Urls.MAIN_PAGE_URL;

public class MainPage extends BasePage {

    // Кнопки главной страницы
    private final By loginButton =
            By.xpath(".//button[text()='Войти в аккаунт']");

    private final By orderButton =
            By.xpath(".//button[text()='Оформить заказ']");

    private final By accountButton =
            By.xpath(".//a/p[text()='Личный Кабинет']");

    // Вкладки конструктора бургеров
    private final By titleBun =
            By.xpath(".//span[text()='Булки']/parent::div");

    private final By titleSauce =
            By.xpath(".//span[text()='Соусы']/parent::div");

    private final By titleFilling =
            By.xpath(".//span[text()='Начинки']/parent::div");

    // Класс активной вкладки
    private static final String SELECTED_CLASS = "tab_tab_type_current";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открываем главную страницу Stellar Burgers")
    public void openMainPage() {
        driver.get(MAIN_PAGE_URL);
    }

    @Step("Нажимаем «Войти в аккаунт»")
    public LoginPage clickLoginButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();

        return new LoginPage(driver);
    }

    @Step("Нажимаем «Личный кабинет»")
    public LoginPage clickAccountButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(accountButton)
        ).click();

        return new LoginPage(driver);
    }

    @Step("Проверяем видимость кнопки «Оформить заказ»")
    public boolean isOrderButtonVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(orderButton)
        ).isDisplayed();
    }

    @Step("Выбираем вкладку «Булки»")
    public void selectBunsTab() {
        selectTab(titleBun);
    }

    @Step("Выбираем вкладку «Соусы»")
    public void selectSaucesTab() {
        selectTab(titleSauce);
    }

    @Step("Выбираем вкладку «Начинки»")
    public void selectFillingsTab() {
        selectTab(titleFilling);
    }

    @Step("Проверяем, что вкладка «Булки» выбрана")
    public boolean isBunsTabSelected() {
        return isTabSelected(titleBun);
    }

    @Step("Проверяем, что вкладка «Соусы» выбрана")
    public boolean isSaucesTabSelected() {
        return isTabSelected(titleSauce);
    }

    @Step("Проверяем, что вкладка «Начинки» выбрана")
    public boolean isFillingsTabSelected() {
        return isTabSelected(titleFilling);
    }

    // Общая логика выбора вкладки
    private void selectTab(By tabLocator) {
        wait.until(
                ExpectedConditions.elementToBeClickable(tabLocator)
        ).click();
    }

    // Проверка CSS-класса активной вкладки
    private boolean isTabSelected(By tabLocator) {
        try {
            return wait.until(
                    ExpectedConditions.attributeContains(
                            tabLocator,
                            "class",
                            SELECTED_CLASS
                    )
            );
        } catch (TimeoutException e) {
            return false;
        }
    }
}