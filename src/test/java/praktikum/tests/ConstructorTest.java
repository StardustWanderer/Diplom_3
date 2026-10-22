package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import praktikum.pages.MainPage;

@Feature("Конструктор бургеров")
public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Выбор вкладки «Булки»")
    @Description("Проверяем переход к вкладке «Булки» из другого раздела конструктора")
    public void shouldSelectBunsTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        // Сначала переходим в другой раздел
        mainPage.selectSaucesTab();

        // Затем возвращаемся к булкам
        mainPage.selectBunsTab();

        Assert.assertTrue(
                "Вкладка «Булки» должна быть активной",
                mainPage.isBunsTabSelected()
        );
    }

    @Test
    @DisplayName("Выбор вкладки «Соусы»")
    @Description("Проверяем, что после нажатия на вкладку «Соусы» она становится активной")
    public void shouldSelectSaucesTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        mainPage.selectSaucesTab();

        Assert.assertTrue(
                "Вкладка «Соусы» должна быть активной",
                mainPage.isSaucesTabSelected()
        );
    }

    @Test
    @DisplayName("Выбор вкладки «Начинки»")
    @Description("Проверяем, что после нажатия на вкладку «Начинки» она становится активной")
    public void shouldSelectFillingsTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();

        mainPage.selectFillingsTab();

        Assert.assertTrue(
                "Вкладка «Начинки» должна быть активной",
                mainPage.isFillingsTabSelected()
        );
    }
}