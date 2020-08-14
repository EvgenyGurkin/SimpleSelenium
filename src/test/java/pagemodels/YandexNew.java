package pagemodels;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import io.qameta.htmlelements.matcher.DisplayedMatcher;
import org.apache.commons.lang3.RandomStringUtils;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class YandexNew {
    private static final Logger logger = LoggerFactory.getLogger(YandexNew.class);
    private final String LOGIN = "123456asd";
    private final String PASSWORD = "123456asdas";
    Main mp;

    @BeforeMethod(alwaysRun = true, description = "Перешли по адресу yandex.ru")
    private void setUp() {
        WebPageFactory factory = new WebPageFactory();
        WebDriver driver;
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
    private void quickLogin() {
        mp.authorization().mailAuthorizationButton().click();
        mp.loginForm().loginField().sendKeys(LOGIN);
        mp.loginForm().loginFormButton().click();
        mp.passForm().passField().sendKeys(PASSWORD);
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

    @Step("Отправили сообщение с емайл адресом: {mailAddress}")
    private void sendLetter(String mailAddress) {
        mp.adressAndTheme().mailAddressField().sendKeys(mailAddress);
        mp.composeHead().sendMessage().click();
    }

    @Step("Проверка сообщения выдаваемого при отправке")
    private void assertSendMail() {
        try {
            if (mp.adressAndTheme().mailAddressError().isDisplayed()) {
                Assert.assertTrue(mp.adressAndTheme().mailAddressError().getText().endsWith(".")
                                || mp.adressAndTheme().mailAddressError().getText().contains(":"),
                        "Тест не пройден потому что ни одно из сообщений об ошибке адреса не было отображено");
            } else {
                Assert.assertTrue(mp.messageSend().succesMailSend().isDisplayed());
            }
        } catch (WebDriverException e) {
            logger.error("Сообщения об ошибке в адресе или успешной отправке не были найдены");
        }
    }

    @Step("Проверка счетчика писем")
    private int checkMailCounter() {
        return Integer.parseInt(mp.leftMailPanel().extrasMailCounter()
                .getText().replaceAll("[^0-9]+", ""));
    }

    @Step("Выделение всех писем по адресу: {address}")
    private void selectAllAddressMailCheckbox(String address) {
        mp.messagesList().allMailCheckBoxes(address).forEach(WebElement::click);
    }

    @Step("Поиск всех выделенных писем")
    private List<String> findCheckedMailsId() {
        return mp.messagesList().allCheckedMail()
                .stream()
                .map(e -> e.getAttribute("data-id"))
                .collect(Collectors.toList());
    }

    @Step("Поиск всех писем")
    private List<String> findMailsId() {
        return mp.messagesList().allMail()
                .stream()
                .map(e -> e.getAttribute("data-id"))
                .collect(Collectors.toList());
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
    private void assertDeleteLetters(List<String> temp1, List<String> temp2, int before, int after, boolean deleteLetters) {
        if (deleteLetters) {
            Assert.assertEquals(after, before - temp1.size());
            Assert.assertTrue(Collections.disjoint(temp1, temp2));
        } else {
            Assert.assertEquals(after, before);
            Assert.assertTrue(temp2.containsAll(temp1));
        }
        Allure.addAttachment("Результат", "text/plain", "На странице было выделено " + temp1.size()
                + " писем, после удаления на странице осталось "
                + temp2.size() + " писем." + "\n" + "До удаления было " + before
                + " писем, после удаления осталось " + after + " писем.");
    }

    @Step("Смена языка на {lang}")
    private void switchLanguage(Language lang) {
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
        quickLogin();
        goWriteLetter();
        sendLetter("Seiron2@yandex.ru");
        assertSendMail();
    }

    @Test(groups = "SM-2")
    private void sendMessageIncorrectAddress() {
        quickLogin();
        goWriteLetter();
        sendLetter(RandomStringUtils.randomAlphabetic(10));
        assertSendMail();
    }

    @Test(groups = "SM-3")
    private void sendMessageWithoutAddress() {
        quickLogin();
        goWriteLetter();
        sendLetter("");
        assertSendMail();
    }

    @Test(groups = "SW-1")
    private void switchLanguageTo1() {
        quickLogin();
        goInSettings();
        Language en = Language.EN;
        switchLanguage(en);
        assertLanguage(en);
    }

    @Test(groups = "SW-2")
    private void switchLanguageTo2() {
        quickLogin();
        goInSettings();
        Language ru = Language.RU;
        switchLanguage(ru);
        assertLanguage(ru);
    }

    @Test(groups = "DL-1")
    private void simpleDeleteLetters() {
        quickLogin();
        int before = checkMailCounter();
        selectAllAddressMailCheckbox("seiron1@yandex.ru");
        List<String> Temp1 = findCheckedMailsId();
        deleteLetters();
        List<String> Temp2 = findMailsId();
        int after = checkMailCounter();
        assertDeleteLetters(Temp1, Temp2, before, after, true);
    }

    @Test(groups = "DL-2")
    private void deleteNoLetters() {
        quickLogin();
        int before = checkMailCounter();
        selectAllAddressMailCheckbox("seiron1@yandex.ru");
        List<String> Temp1 = findCheckedMailsId();
        List<String> Temp2 = findMailsId();
        int after = checkMailCounter();
        assertDeleteLetters(Temp1, Temp2, before, after, false);
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

        private final String path;
        private final String langName;

        Language(String path, String langName) {
            this.path = path;
            this.langName = langName;
        }

        public String getPath() {
            return path;
        }
    }

}