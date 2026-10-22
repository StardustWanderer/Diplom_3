package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
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

import static org.apache.http.HttpStatus.SC_OK;
import static praktikum.config.Urls.MAIN_PAGE_URL;

@Feature("Авторизация пользователя")
public class AuthorizationTest extends BaseTest {

    private final UserData userData = new UserData();
    private final ApiMethods apiMethods = new ApiMethods();

    private String email;
    private String password;
    private String accessToken;

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
                SC_OK,
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
    @DisplayName("Вход через кнопку «Войти в аккаунт»")
    @Description("Проверяем авторизацию пользователя через кнопку «Войти в аккаунт» на главной странице")
    public void shouldLoginViaButtonOnMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.sendUserDataAndLogin(email, password);

        waitForMainPage();

        Assert.assertEquals(
                "После авторизации пользователь должен попасть на главную страницу",
                MAIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    @Test
    @DisplayName("Вход через «Личный кабинет»")
    @Description("Проверяем авторизацию пользователя через ссылку «Личный кабинет» на главной странице")
    public void shouldLoginViaAccountButtonOnMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        LoginPage loginPage = mainPage.clickAccountButton();
        loginPage.sendUserDataAndLogin(email, password);

        waitForMainPage();

        Assert.assertEquals(
                "После авторизации пользователь должен попасть на главную страницу",
                MAIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    @Test
    @DisplayName("Вход со страницы регистрации")
    @Description("Проверяем авторизацию после перехода со страницы регистрации по ссылке «Войти»")
    public void shouldLoginViaEnterButtonOnRegisterPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickOnRegisterLink();

        LoginPage loginPageAfterRegistrationPage =
                registerPage.clickOnLoginLink();

        loginPageAfterRegistrationPage
                .sendUserDataAndLogin(email, password);

        waitForMainPage();

        Assert.assertEquals(
                "После авторизации пользователь должен попасть на главную страницу",
                MAIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    @Test
    @DisplayName("Вход со страницы восстановления пароля")
    @Description("Проверяем авторизацию после перехода со страницы восстановления пароля по ссылке «Войти»")
    public void shouldLoginViaEnterButtonOnForgotPasswordPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        LoginPage loginPage = mainPage.clickLoginButton();
        ForgotPasswordPage forgotPasswordPage =
                loginPage.clickOnRecoverPasswordLink();

        LoginPage loginPageAfterForgotPasswordPage =
                forgotPasswordPage.clickOnLoginLink();

        loginPageAfterForgotPasswordPage
                .sendUserDataAndLogin(email, password);

        waitForMainPage();

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

    @After
    public void cleanUp() {
        if (accessToken != null && !accessToken.isBlank()) {
            apiMethods.deleteUser(accessToken);
        }
    }
}