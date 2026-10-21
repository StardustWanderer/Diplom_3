package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.data.UserData;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;
import praktikum.utils.ApiMethods;

import java.time.Duration;

import static praktikum.config.Urls.MAIN_PAGE_URL;

@Feature("Авторизация пользователя")
public class AuthorizationTest extends BaseTest {

    private final UserData userData = new UserData();
    private final ApiMethods apiMethods = new ApiMethods();

    private String email;
    private String password;
    private String accessToken;

    // Создаём пользователя через API перед тестом
    @Before
    public void createUserViaApi() {

        String name = userData.generateName();
        email = userData.generateEmail();
        password = userData.generateValidPassword();

        Response registerResponse =
                apiMethods.registerUser(name, email, password);

        Assert.assertNotNull(
                "Ответ API регистрации не должен быть null",
                registerResponse
        );

        Assert.assertEquals(
                "Пользователь должен успешно зарегистрироваться через API",
                200,
                registerResponse.statusCode()
        );

        accessToken = registerResponse.jsonPath()
                .getString("accessToken");

        Assert.assertNotNull(
                "Ответ регистрации должен содержать accessToken",
                accessToken
        );
    }

    @Test
    @Description("Авторизация пользователя по кнопке «Войти в аккаунт» на главной")
    public void shouldLoginViaButtonOnMainPage() {

        // Создаём объект главной страницы
        MainPage mainPage = new MainPage(driver);

        // Открываем главную страницу
        mainPage.openMainPage();

        // Переходим на страницу авторизации по кнопке «Войти в аккаунт»
        LoginPage loginPage = mainPage.clickLoginButton();

        //на странице авторизации заполняем поля "email" и "password" и кликаем на кнопку войти
        loginPage.sendUserDataAndLogin(email, password);

        //ждем чтобы загрузилась главная страница
        waitForMainPage();

        // Проверяем переход на главную страницу
        Assert.assertEquals(
                "После авторизации пользователь должен попасть на главную страницу",
                MAIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    @Test
    @Description("Авторизация пользователя по кнопке «Личный кабинет» на главной")
    public void shouldLoginViaAccountButtonOnMainPage(){

        // Создаём объект главной страницы
        MainPage mainPage = new MainPage(driver);

        // Открываем главную страницу
        mainPage.openMainPage();

        // Переходим на страницу авторизации по кнопке «Личный кабинет»
        LoginPage loginPage = mainPage.clickAccountButton();

        //на странице авторизации заполняем поля "email" и "password" и кликаем на кнопку войти
        loginPage.sendUserDataAndLogin(email, password);

        //ждем чтобы загрузилась главная страница
        waitForMainPage();

        // Проверяем переход на главную страницу
        Assert.assertEquals(
                "После авторизации пользователь должен попасть на главную страницу",
                MAIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    @Test
    @Description("Авторизация пользователя по ссылке «Войти» на странице регистрации")
    public void shouldLoginViaEnterButtonOnRegisterPage(){
        // Создаём объект главной страницы
        MainPage mainPage = new MainPage(driver);

        // Открываем главную страницу
        mainPage.openMainPage();

        // Переходим на страницу авторизации по кнопке «Войти в аккаунт»
        LoginPage loginPage = mainPage.clickLoginButton();

        //Переходим на страницу регистрации по ссылке «Зарегистрироваться»
        RegisterPage registerPage = loginPage.clickOnRegisterLink();

        //Переходим на страницу авторизации по ссылке «Войти»
        LoginPage loginPageAfterRegistrationPage = registerPage.clickOnLoginLink();

        //на странице авторизации заполняем поля "email" и "password" и кликаем на кнопку войти
        loginPageAfterRegistrationPage.sendUserDataAndLogin(email, password);

        //ждем чтобы загрузилась главная страница
        waitForMainPage();

        // Проверяем переход на главную страницу
        Assert.assertEquals(
                "После авторизации пользователь должен попасть на главную страницу",
                MAIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    @Test
    @Description("Авторизация пользователя по ссылке «Войти» на странице восстановления пароля")
    public void shouldLoginViaEnterButtonOnForgotPasswordPage(){
        // Создаём объект главной страницы
        MainPage mainPage = new MainPage(driver);

        // Открываем главную страницу
        mainPage.openMainPage();

        // Переходим на страницу авторизации по кнопке «Войти в аккаунт»
        LoginPage loginPage = mainPage.clickLoginButton();

        //Переходим на страницу восстановления пароля по ссылке «Восстановить пароль»
        ForgotPasswordPage forgotPasswordPage = loginPage.clickOnRecoverPasswordLink();

        //Переходим на страницу авторизации по ссылке «Войти» на странице восстановления пароля
        LoginPage loginPageAfterForgotPasswordPage = forgotPasswordPage.clickOnLoginLink();

        //на странице авторизации заполняем поля "email" и "password" и кликаем на кнопку войти
        loginPageAfterForgotPasswordPage.sendUserDataAndLogin(email, password);

        //ждем чтобы загрузилась главная страница
            waitForMainPage();

        // Проверяем переход на главную страницу
        Assert.assertEquals(
                "После авторизации пользователь должен попасть на главную страницу",
                MAIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    private void waitForMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MAIN_PAGE_URL));
    }

    // Удаляем тестового пользователя через API
    @After
    public void cleanUp() {

        if (accessToken != null && !accessToken.isBlank()) {
            apiMethods.deleteUser(accessToken);
        }
    }
}