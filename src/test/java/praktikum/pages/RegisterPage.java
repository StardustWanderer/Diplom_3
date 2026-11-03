package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    private final By loginLink =
            By.xpath(".//a[text()='Войти']");

    private final By nameField =
            By.xpath(".//label[text()='Имя']/parent::div/input");

    private final By emailField =
            By.xpath(".//label[text()='Email']/parent::div/input");

    private final By passwordField =
            By.xpath(".//input[@name='Пароль']");

    private final By registerButton =
            By.xpath(".//button[text()='Зарегистрироваться']");

    private final By passwordErrorMessage =
            By.xpath(".//p[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Заполняем форму и отправляем её.
    // Не помечаем этот метод @Step, чтобы не записывать
    // пароль в параметры шага Allure.
    public void sendUserDataAndRegister(
            String name,
            String email,
            String password
    ) {
        sendUserName(name);
        sendUserEmail(email);
        sendUserPassword(password);
        clickOnRegisterButton();
    }

    @Step("Вводим имя пользователя")
    public void sendUserName(String name) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameField)
        );

        element.clear();
        element.sendKeys(name);
    }

    @Step("Вводим email пользователя")
    public void sendUserEmail(String email) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        );

        element.clear();
        element.sendKeys(email);
    }

    // Не добавляем @Step, чтобы не раскрывать пароль в отчёте.
    public void sendUserPassword(String password) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        );

        element.clear();
        element.sendKeys(password);
    }

    @Step("Нажимаем кнопку «Зарегистрироваться»")
    public void clickOnRegisterButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();
    }

    @Step("Переходим по ссылке «Войти»")
    public LoginPage clickOnLoginLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();

        return new LoginPage(driver);
    }

    @Step("Проверяем видимость сообщения «Некорректный пароль»")
    public boolean isPasswordErrorMessageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorMessage)).isDisplayed();
    }
}