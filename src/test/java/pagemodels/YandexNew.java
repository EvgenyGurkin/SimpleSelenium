package pagemodels;

import elements.MessagesList;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import io.qameta.htmlelements.element.ExtendedList;
import io.qameta.htmlelements.element.ExtendedWebElement;
import io.qameta.htmlelements.matcher.DisplayedMatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YandexNew {
    private static final Logger logger = LoggerFactory.getLogger(YandexNew.class);
    WebPageFactory factory = new WebPageFactory();
    WebDriver driver;
    Main mp;

    @BeforeMethod(alwaysRun = true, description = "Перешли по адресу yandex.ru")
    private void setUp() {
        driver = new ChromeDriver();
        mp = factory.get(driver, Main.class);
        mp.getWrappedDriver().manage().window().maximize();
        mp.go();
        mp.authorization().mailAuthorizationButton()
                .waitUntil("Ожидание кнопки войти", DisplayedMatcher.displayed(), 5);
    }

    @AfterMethod(alwaysRun = true, description = "Выключили драйвер")
    private void tearDown() {
        mp.getWrappedDriver().close();
    }

    @Step("Зашли в почту {emailName} под паролем {password}")
    private void quickLogin(String emailName, String password) {
        mp.authorization().mailAuthorizationButton().click();
        mp.loginForm().loginField().sendKeys(emailName);
        mp.loginForm().loginFormButton().click();
        mp.passForm().passField().sendKeys(password);
        mp.passForm().passFormButton().click();
        mp.leftMailPanel().goToWriteButton()
                .waitUntil("Ожидание того что мы успешно залогинились", DisplayedMatcher.displayed(), 5);
    }

    @Step("Перешли в режим настройки почты")
    private void goInSettings() {
        mp.settingsControl().settingsGhostButton().click();
        mp.allSettings().allSettingsButton()
                .waitUntil("Ожидание кнопки", DisplayedMatcher.displayed(), 5);
        mp.allSettings().allSettingsButton().click();
        mp.langAndSettings().waitUntil("Ожидание кнопки", DisplayedMatcher.displayed(), 5);
    }

    @Step("Перешли в раздел отправки письма")
    private void goWriteLetter() {
        mp.leftMailPanel().goToWriteButton().click();
    }

    @Step("Отправили сообщение с емайл адресом: {mailAdress}")
    private void sendLetter(String mailAdress) {
        mp.adressAndTheme().mailAdressField().sendKeys(mailAdress);
        mp.composeHead().sendMessage().click();
    }

    @Step("Проверка сообщения выдаваемого при отправке")
    private void assertSendMail() {
        try {
            if (mp.adressAndTheme().mailAdressError().isDisplayed()) {
                if (mp.adressAndTheme().mailAdressError().getText().endsWith(".")) {
                    Assert.assertTrue(mp.adressAndTheme().mailAdressError().isDisplayed());
                } else if (mp.adressAndTheme().mailAdressError().getText().contains(":")) {
                    Assert.assertTrue(mp.adressAndTheme().mailAdressError().isDisplayed());
                } else {
                    Assert.fail("Тест не пройден потому что ни одно из сообщений об ошибке адреса не было отображено");
                }
            }
        } catch (WebDriverException e) {
            logger.error("Сообщения об ошибке в адресе не были найдены");
        }
        try {
            if (mp.messageSend().succesMailSend().isDisplayed()) {
                Assert.assertTrue(mp.messageSend().succesMailSend().isDisplayed());
            }
        } catch (WebDriverException e) {
            logger.error("Сообщение об успешной отправке письма не было найдено");
        }
    }

    @Step("Проверка счетчика писем")
    private int checkMailCounter() {
        return Integer.parseInt(mp.leftMailPanel().extrasMailCounter()
                .getText().replaceAll("[^0-9]+", ""));
    }

    @Step("Выделение всех писем по адресу: {adress}")
    private void selectAllAdressMailCheckbox(String adress) {
        mp.messagesList().allMailCheckBoxes(adress).forEach(WebElement::click);
    }

    @Step("Поиск всех выделенных писем")
    private List<String> findChekedMailsId() {
        ExtendedList<MessagesList> allCheckedLetters = mp.messagesList().allChekedMail();
        List<String> chekedLettersId = new ArrayList<>();
        for (ExtendedWebElement letId : allCheckedLetters) {
            String id = letId.getAttribute("data-id");
            chekedLettersId.add(id);
        }
        return chekedLettersId;
    }

    @Step("Поиск всех писем")
    private List<String> findMailsId() {
        ExtendedList<MessagesList> allCheckedLetters = mp.messagesList().allMail();
        List<String> chekedLettersId = new ArrayList<>();
        for (ExtendedWebElement letId : allCheckedLetters) {
            String id = letId.getAttribute("data-id");
            chekedLettersId.add(id);
        }
        return chekedLettersId;
    }

    @Step("Удаление выделенных писем")
    private void deleteLetters() {
        mp.toolbarBox().deleteMailButton().click();
        try {
            mp.DeleteWarning().conformDeleteButton().click();
        } catch (WebDriverException e) {
            logger.error("Панель подтверждения удаления не была отображена");
        }

        try {
            mp.allSettings().allSettingsButton()
                    .waitUntil("Ожидание отображения каунтера", DisplayedMatcher.displayed(), 3);
        } catch (Exception e) {
            logger.error("Искуственное ожидание, для того чтобы на странице успели обновится все элементы");
        }
    }

    @Step("Проверка удаления писем")
    private void assertDeleteLetters(List<String> temp1, List<String> temp2, int before, int after, String status) {
        if (status.equals("delete")) {
            Assert.assertEquals(after, before - temp1.size());
            Assert.assertTrue(Collections.disjoint(temp1, temp2));

        }
        if (status.equals("nodelete")) {
            Assert.assertEquals(after, before);
            Assert.assertTrue(temp2.containsAll(temp1));

        }
        Allure.addAttachment("Результат", "text/plain", "На странице было выделено " + temp1.size()
                + " писем, после удаления на странице осталось "
                + temp2.size() + " писем." + "\n" + "До удаления было " + before
                + " писем, после удаления осталось " + after + " писем.");
    }

    @Step("Смена языка на {lang}")
    private void swithLanguage(Language lang) {
        mp.langAndSettings().langSettingsButton().click();
        try {
            mp.langAndSettings().allLangButtons(lang.getPath()).click();
        } catch (Exception e) {
            logger.error("Кнопка смены языка не была найдена потому что уже установлен нужный нам язык");
        }
        mp.langAndSettings().langSettingsButton().click();
        mp.langAndSettings().langSettingsButton()
                .waitUntil("Ожидание кнопки", DisplayedMatcher.displayed(), 5);
    }

    @Step("Проверка языка")
    private void assertLanguage(Language needed) {
        Assert.assertEquals(mp.langAndSettings().langSettingsButton()
                .getText().toLowerCase(), needed.langName.toLowerCase());
    }

    @Test(groups = "SM-1")
    private void sendMessageCorrect() {
        quickLogin("Seiron1", "210890-=");
        goWriteLetter();
        sendLetter("Seiron2@yandex.ru");
        assertSendMail();
    }

    @Test(groups = "SM-2")
    private void sendMessageIncorrectAdress() {
        quickLogin("Seiron1", "210890-=");
        goWriteLetter();
        sendLetter("asdsa");
        assertSendMail();
    }

    @Test(groups = "SM-3")
    private void sendMessageWithoutAdress() {
        quickLogin("Seiron1", "210890-=");
        goWriteLetter();
        sendLetter("");
        assertSendMail();
    }

    @Test(groups = "SW-1")
    private void switchLanguageTo1() {
        quickLogin("Seiron1", "210890-=");
        goInSettings();
        swithLanguage(Language.EN);
        assertLanguage(Language.EN);
    }

    @Test(groups = "SW-2")
    private void switchLanguageTo2() {
        quickLogin("Seiron1", "210890-=");
        goInSettings();
        swithLanguage(Language.RU);
        assertLanguage(Language.RU);
    }

    @Test(groups = "DL-1")
    private void simpleDeletLetters() {
        quickLogin("Seiron1", "210890-=");
        int before = checkMailCounter();
        selectAllAdressMailCheckbox("seiron1@yandex.ru");
        List<String> Temp1 = findChekedMailsId();
        deleteLetters();
        List<String> Temp2 = findMailsId();
        int after = checkMailCounter();
        assertDeleteLetters(Temp1, Temp2, before, after, "delete");
    }

    @Test(groups = "DL-2")
    private void deleteNoLetters() {
        quickLogin("Seiron1", "210890-=");
        int before = checkMailCounter();
        selectAllAdressMailCheckbox("seiron1@yandex.ru");
        List<String> Temp1 = findChekedMailsId();
        List<String> Temp2 = findMailsId();
        int after = checkMailCounter();
        assertDeleteLetters(Temp1, Temp2, before, after, "nodelete");
    }

    public enum Language {
        EN("en", "English"),
        RU("ru", "Русский"),
        TR("tr", "Türkçe"),
        TT("tt", "Татарча"),
        UK("uk", "Українська"),
        AZ("az", "Azərbaycan"),
        BE("be", "Беларуская"),
        HY("hy", "Հայերեն"),
        KA("ka", "Грузинский"),
        RO("ro", "Română"),
        KK("kk", "Қазақ");

        private String path;
        private String langName;

        Language(String path, String langName) {
            this.path = path;
            this.langName = langName;
        }

        public String getPath() {
            return path;
        }
    }

}