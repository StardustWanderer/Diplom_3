package praktikum.tests;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import praktikum.data.UserData;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;
import praktikum.utils.ApiMethods;

import static praktikum.config.Urls.LOGIN_PAGE_URL;

public class RegistrationTest extends BaseTest {

    private final UserData userData = new UserData();
    private final ApiMethods apiMethods = new ApiMethods();

    private String email;
    private String password;

    @Test
    public void shouldRegisterUserSuccessfully() {

        // Генерируем данные нового пользователя
        String name = userData.generateName();
        email = userData.generateEmail();
        password = userData.generateValidPassword();

        // Переходим на страницу регистрации
        RegisterPage registerPage = goToRegisterPage();

        //заполняем поля "Имя", "Email", "Пароль" на странице регистрации и кликаем на кнопку "Зарегистрироваться"
        registerPage.sendUserDataAndRegister(name, email, password);

        LoginPage loginPage = new LoginPage(driver);

        //проверяем, что виден заголовок "Вход" и открытый URL соответствует странице логина
        Assert.assertTrue("После регистрации должен отображаться заголовок «Вход»", loginPage.isEnterHeaderVisible());
        Assert.assertEquals("После регистрации должен произойти редирект на страницу авторизации", LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    public void registrationWithShortPasswordReturnsError(){
        // Генерируем данные нового пользователя
        String name = userData.generateName();
        email = userData.generateEmail();
        password = userData.generateInvalidPassword();

        // Переходим на страницу регистрации
        RegisterPage registerPage = goToRegisterPage();

        //Сохраняем URL страницы регистрации
        String registrationPageUrl = driver.getCurrentUrl();

        //заполняем поля "Имя", "Email", "Пароль" на странице регистрации и кликаем на кнопку "Зарегистрироваться"
        registerPage.sendUserDataAndRegister(name, email, password);

        //проверяем, что появилась ошибка «Некорректный пароль» и URL не изменился
        Assert.assertTrue("Должно отображаться сообщение «Некорректный пароль»", registerPage.isPasswordErrorMessageVisible());
        Assert.assertEquals("При некорректном пароле URL не должен изменяться", registrationPageUrl, driver.getCurrentUrl());
    }

    private RegisterPage goToRegisterPage(){
        //создаем экземпляр MainPage и передаем драйвер
        MainPage mainPage = new MainPage(driver);

        // Открываем главную страницу
        mainPage.openMainPage();

        //Переходим на страницу авторизации по кнопке «Войти в аккаунт»
        LoginPage loginPage = mainPage.clickAccountButton();

        //Переходим по ссылке «Зарегистрироваться»
        return loginPage.clickOnRegisterLink();
    }

    // Удаляем созданного пользователя через API
    @After
    public void cleanUp() {

        // Если данные пользователя не были сгенерированы, очистка не нужна
        if (email == null || password == null) {
            return;
        }

        // Авторизуемся через API с данными зарегистрированного пользователя
        Response loginResponse = apiMethods.loginUser(email, password);

        // Если авторизация не удалась, токен получить не получится
        if (loginResponse == null || loginResponse.statusCode() != 200) {
            return;
        }

        // Получаем токен из ответа API
        String accessToken = loginResponse.jsonPath().getString("accessToken");

        // Удаляем пользователя, только если токен получен
        if (accessToken != null && !accessToken.isBlank()) {
            apiMethods.deleteUser(accessToken);
        }
    }
}