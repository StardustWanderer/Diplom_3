package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import praktikum.data.UserData;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;
import praktikum.utils.ApiMethods;

import static org.apache.http.HttpStatus.SC_OK;
import static praktikum.config.Urls.LOGIN_PAGE_URL;

@Feature("Регистрация пользователя")
public class RegistrationTest extends BaseTest {

    private final UserData userData = new UserData();
    private final ApiMethods apiMethods = new ApiMethods();

    private String email;
    private String password;

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверяем регистрацию нового пользователя и переход на страницу авторизации")
    public void shouldRegisterUserSuccessfully() {
        String name = userData.generateName();
        email = userData.generateEmail();
        password = userData.generateValidPassword();

        RegisterPage registerPage = goToRegisterPage();
        registerPage.sendUserDataAndRegister(name, email, password);

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(
                "После регистрации должен отображаться заголовок «Вход»",
                loginPage.isEnterHeaderVisible()
        );

        Assert.assertEquals(
                "После регистрации должен произойти редирект на страницу авторизации",
                LOGIN_PAGE_URL,
                driver.getCurrentUrl()
        );
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    @Description("Проверяем отображение ошибки при попытке регистрации с паролем недопустимой длины")
    public void registrationWithShortPasswordReturnsError() {
        String name = userData.generateName();
        email = userData.generateEmail();
        password = userData.generateInvalidPassword();

        RegisterPage registerPage = goToRegisterPage();

        String registrationPageUrl = driver.getCurrentUrl();

        registerPage.sendUserDataAndRegister(name, email, password);

        Assert.assertTrue(
                "Должно отображаться сообщение «Некорректный пароль»",
                registerPage.isPasswordErrorMessageVisible()
        );

        Assert.assertEquals(
                "При некорректном пароле URL не должен изменяться",
                registrationPageUrl,
                driver.getCurrentUrl()
        );
    }

    private RegisterPage goToRegisterPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        LoginPage loginPage = mainPage.clickAccountButton();

        return loginPage.clickOnRegisterLink();
    }

    @After
    public void cleanUp() {
        if (email == null || password == null) {
            return;
        }

        Response loginResponse = apiMethods.loginUser(email, password);

        if (loginResponse == null
                || loginResponse.statusCode() != SC_OK) {
            return;
        }

        String accessToken =
                loginResponse.jsonPath().getString("accessToken");

        if (accessToken != null && !accessToken.isBlank()) {
            apiMethods.deleteUser(accessToken);
        }
    }
}