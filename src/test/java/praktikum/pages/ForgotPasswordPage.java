package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForgotPasswordPage extends BasePage {

    private final By loginLink =
            By.xpath(".//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажимаем ссылку «Войти»")
    public LoginPage clickOnLoginLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();

        return new LoginPage(driver);
    }
}