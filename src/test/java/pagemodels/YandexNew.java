package pagemodels;

import elements.MessagesList;
import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import io.qameta.htmlelements.element.ExtendedList;
import io.qameta.htmlelements.element.ExtendedWebElement;
import io.qameta.htmlelements.matcher.DisplayedMatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YandexNew {
    public enum Language {
        EN("//a[@data-params='lang=en']", "English"),
        RU("//a[@data-params='lang=ru']", "Русский"),
        TR("//a[@data-params='lang=tr']", "Türkçe"),
        TT("//a[@data-params='lang=tt']", "Татарча"),
        UK("//a[@data-params='lang=uk']", "Українська"),
        AZ("//a[@data-params='lang=az']", "Azərbaycan"),
        BE("//a[@data-params='lang=be']", "Беларуская"),
        HY("//a[@data-params='lang=hy']", "Հայերեն"),
        KA("//a[@data-params='lang=ka']", "Грузинский"),
        RO("//a[@data-params='lang=ro']", "Română"),
        KK("//a[@data-params='lang=kk']", "Қазақ");

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


    WebPageFactory factory = new WebPageFactory();
    WebDriver driver;
    Main mainPage;

    @BeforeMethod(alwaysRun = true, description = "Перешли по адресу yandex.ru")
    private void setUp() {
        driver = new ChromeDriver();
        mainPage = factory.get(driver, Main.class);
        mainPage.getWrappedDriver().manage().window().maximize();
        mainPage.go();
        mainPage.authorization().mailAuthorizationButton()
                .waitUntil("Ожидание кнопки войти", DisplayedMatcher.displayed(), 5);
    }

    @AfterMethod(alwaysRun = true, description = "Выключили драйвер")
    private void tearDown() {
        mainPage.getWrappedDriver().close();
    }

    @Step("Зашли в почту {emailName} под паролем {password}")
    private void quickLogin(String emailName, String password) {
        mainPage.authorization().mailAuthorizationButton().click();
        mainPage.loginForm().loginField().sendKeys(emailName);
        mainPage.loginForm().loginFormButton().click();
        mainPage.passForm().passField().sendKeys(password);
        mainPage.passForm().passFormButton().click();
    }

    @Step("Перешли в режим настройки почты")
    private void goInSettings() {
        mainPage.settingsControl().settingsGhostButton().click();
        mainPage.allSettings().allSettingsButton().waitUntil("Ожидание кнопки", DisplayedMatcher.displayed(), 5);
        mainPage.allSettings().allSettingsButton().click();
        mainPage.langAndSettings().waitUntil("Ожидание кнопки", DisplayedMatcher.displayed(), 5);
    }


    @Step("Перешли в раздел отправки письма")
    private void goWriteLetter() {
        mainPage.leftMailPanel().goToWriteButton().click();
    }

    @Step("Отправили сообщение с емайл адресом: {mailAdress}")
    private void sendLetter(String mailAdress) {
        mainPage.adressAndTheme().mailAdressField().sendKeys(mailAdress);
        mainPage.composeHead().sendMessage().click();
    }


    @Step("Проверка сообщения выдаваемого при отправке")
    private void assertSendMail() {
        try {
            if (mainPage.adressAndTheme().mailAdressError().isDisplayed()) {
                if (mainPage.adressAndTheme().mailAdressError().getText().equals("Поле не заполнено. Необходимо ввести адрес.") || mainPage.adressAndTheme().mailAdressError().getText().equals("Please enter at least one email address.")) {
                    Assert.assertTrue(mainPage.adressAndTheme().mailAdressError().isDisplayed());
                } else if (mainPage.adressAndTheme().mailAdressError().getText().contains("Некорректные адреса:") || mainPage.adressAndTheme().mailAdressError().getText().contains("Invalid email addresses:")) {
                    Assert.assertTrue(mainPage.adressAndTheme().mailAdressError().isDisplayed());
                } else {
                    Assert.assertEquals(2, 1);
                }
            }
        } catch (WebDriverException ignored) {
        }
        try {
            if (mainPage.messageSend().succesMailSend().isDisplayed()) {
                Assert.assertTrue(mainPage.messageSend().succesMailSend().isDisplayed());
            }
        } catch (WebDriverException ignored) {
        }
    }

    @Step("Проверка счетчика писем")
    private int checkMailCounter() {
        return Integer.parseInt(mainPage.leftMailPanel().extrasMailCounter().getText().replaceAll("[^0-9]+", ""));
    }

    @Step("Выделение всех писем по адресу: {adress}")
    private void selectAllAdressMailCheckbox(String adress) {
        mainPage.messagesList().allMailCheckBoxes(adress).forEach(WebElement::click);
    }

    @Step("Поиск всех выделенных писем")
    private List<String> findChekedMailsId() {
        ExtendedList<MessagesList> allCheckedLetters = mainPage.messagesList().allChekedMail();
        List<String> chekedLettersId = new ArrayList<>();
        for (ExtendedWebElement letId : allCheckedLetters) {
            String id = letId.getAttribute("data-id");
            chekedLettersId.add(id);
        }
        return chekedLettersId;
    }

    @Step("Поиск всех писем")
    private List<String> findMailsId() {
        ExtendedList<MessagesList> allCheckedLetters = mainPage.messagesList().allMail();
        List<String> chekedLettersId = new ArrayList<>();
        for (ExtendedWebElement letId : allCheckedLetters) {
            String id = letId.getAttribute("data-id");
            chekedLettersId.add(id);
        }
        return chekedLettersId;
    }

    @Step("Удаление выделенных писем")
    private void deleteLetters() {
        mainPage.toolbarBox().deleteMailButton().click();
        try {
            mainPage.DeleteWarning().conformDeleteButton().click();
        } catch (WebDriverException ignore) {
        }

        try {
            mainPage.allSettings().allSettingsButton().waitUntil("Ожидание отображения каунтера", DisplayedMatcher.displayed(), 3);
        } catch (Exception ignored) {
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
    }

    @Step("Смена языка на {lang}")
    private void swithLanguage(Language lang) {
        mainPage.langAndSettings().langSettingsButton().click();
        try {
            mainPage.langAndSettings().allLangButtons(lang.getPath()).click();
        } catch (Exception ignored) {
        }
        mainPage.langAndSettings().langSettingsButton().click();
        mainPage.langAndSettings().langSettingsButton().waitUntil("Ожидание кнопки", DisplayedMatcher.displayed(), 5);
    }

    @Step("Проверка языка")
    private void assertLanguage(Language needed) {
        Assert.assertEquals(mainPage.langAndSettings().langSettingsButton().getText().toLowerCase(), needed.langName.toLowerCase());
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

}