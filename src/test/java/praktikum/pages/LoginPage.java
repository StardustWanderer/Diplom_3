package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // Поля формы входа
    private final By emailField =
            By.xpath(".//input[@name='name']");

    private final By passwordField =
            By.xpath(".//input[@name='Пароль']");

    // Кнопка и ссылки
    private final By enterButton =
            By.xpath(".//button[text()='Войти']");

    private final By registerLink =
            By.xpath(".//a[text()='Зарегистрироваться']");

    private final By recoverPasswordLink =
            By.xpath(".//a[text()='Восстановить пароль']");

    // Заголовок страницы
    private final By enterHeader =
            By.xpath(".//h2[text()='Вход']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Вводим данные и выполняем вход
    public void sendUserDataAndLogin(String email, String password) {
        sendUserEmail(email);
        sendUserPassword(password);
        clickOnEnterButton();
    }

    @Step("Вводим email пользователя")
    public void sendUserEmail(String email) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        );

        element.clear();
        element.sendKeys(email);
    }

    // Не размечаем метод @Step, чтобы не записывать пароль в отчёт.
    public void sendUserPassword(String password) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        );

        element.clear();
        element.sendKeys(password);
    }

    @Step("Нажимаем кнопку «Войти»")
    public void clickOnEnterButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(enterButton)
        ).click();
    }

    @Step("Переходим по ссылке «Зарегистрироваться»")
    public RegisterPage clickOnRegisterLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerLink)
        ).click();

        return new RegisterPage(driver);
    }

    @Step("Переходим по ссылке «Восстановить пароль»")
    public ForgotPasswordPage clickOnRecoverPasswordLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(recoverPasswordLink)
        ).click();

        return new ForgotPasswordPage(driver);
    }

    @Step("Проверяем видимость заголовка «Вход»")
    public boolean isEnterHeaderVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(enterHeader)
        ).isDisplayed();
    }
}