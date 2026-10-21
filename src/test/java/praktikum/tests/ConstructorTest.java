package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.Assert;
import org.junit.Test;

import praktikum.pages.MainPage;

@Feature("Конструктор бургеров")
public class ConstructorTest extends BaseTest {

    @Test
    @Description("Проверяем переходы между вкладками «Булки», «Соусы» и «Начинки»")
    public void shouldSwitchBetweenConstructorSections() {

        // Открываем главную страницу
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        // Переходим во вкладку «Соусы»
        mainPage.selectSaucesTab();

        Assert.assertTrue(
                "Вкладка «Соусы» должна быть активной",
                mainPage.isSaucesTabSelected()
        );

        // Переходим во вкладку «Начинки»
        mainPage.selectFillingsTab();

        Assert.assertTrue(
                "Вкладка «Начинки» должна быть активной",
                mainPage.isFillingsTabSelected()
        );

        // Переходим во вкладку «Булки»
        mainPage.selectBunsTab();

        Assert.assertTrue(
                "Вкладка «Булки» должна быть активной",
                mainPage.isBunsTabSelected()
        );
    }
}