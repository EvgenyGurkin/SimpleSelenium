package mailtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class YandexMailTest {
    private static final Logger logger = LoggerFactory.getLogger(YandexMailTest.class);
    private ChromeDriver driver;

    private void moveToWriteMail() {
        driver.findElement(By.xpath("//a[@class='mail-ComposeButton js-main-action-compose']")).click(); //кнопка написать
    }

    private void writeUserAdress(String userAdress) {
        WebElement mailField = driver.findElement(By.xpath("//div[@class='js-compose-field mail-Bubbles']"));  // ячейка для адреса
        mailField.sendKeys(userAdress);
    }

    private void sendMessage() {
        driver.findElement(By.xpath("//*[@id=\"nb-16\"]")).click(); // кнопка отправить
    }

    private Object findElementMessageSendError() {
        Object returnDriver = null;
        try {
            if (driver.findElement(By.xpath("//div[@class='mail-Done-Title js-title-info']")).getText().equals("Письмо отправлено.") || driver.findElement(By.xpath("//div[@class='mail-Done-Title js-title-info']")).getText().equals("Message sent successfully.")) {
                returnDriver = driver.findElement(By.xpath("//div[@class='mail-Done-Title js-title-info']"));
                return returnDriver;
            }
        } catch (Exception ignored) {
        }
        try {
            if (driver.findElement(By.xpath("//div[contains(@class , \"field-to-error\")]")).getText().equals("Поле не заполнено. Необходимо ввести адрес.") || driver.findElement(By.xpath("//div[contains(@class , \"field-to-error\")]")).getText().equals("Please enter at least one email address.")) {
                returnDriver = driver.findElement(By.xpath("//div[contains(@class , \"field-to-error\")]"));
                return returnDriver;
            }
            if (driver.findElement(By.xpath("//div[contains(@class , \"field-to-error\")]")).getText().startsWith("Некорректные адреса:") || driver.findElement(By.xpath("//div[contains(@class , \"field-to-error\")]")).getText().startsWith("Invalid email addresses:")) {
                returnDriver = driver.findElement(By.xpath("//div[contains(@class , \"field-to-error\")]"));
            }
        } catch (Exception ignored) {
        }
        return returnDriver;
    }

    private void moveToSettings() {
        driver.findElement(By.xpath("//*[@id=\"nb-3\"]")).click();  // шестеренка
        driver.findElement(By.xpath("//span[@class=\"settings-popup-title-content\"]")).click(); // Все настройки

    }

    private void switchToLanguage(Language val) {
        driver.findElement(By.xpath("(//span[@class='b-selink__inner'])[1]")).click();
        try {
            if (driver.findElement(By.xpath(val.getPath())).isEnabled()) {
                driver.findElement(By.xpath(val.getPath())).click();
            }
        } catch (Exception e) {
            logger.error("Мы не меняли язык потому что нужный нам язык был уже установлен");
        }

        WebDriverWait wait = new WebDriverWait(driver, 3);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//span[@class='b-selink__inner'])[1]")));
        } catch (Exception ignored) {
        }
    }

    private int checkCounter() {
        WebElement before = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String bee = before.getText();
        bee = bee.replaceAll("[^0-9]+", "");
        return Integer.parseInt(bee);
    }

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

    private List<String> checkAllLettersId() {
        List<WebElement> allLetters = driver.findElements(By.xpath("//div[contains(@class, 'item-wrap')]"));
        List<String> lettersId = new ArrayList<>();
        for (WebElement let : allLetters) {
            String id = let.getAttribute("data-id");
            lettersId.add(id);
        }
        return lettersId;
    }

    private void clickOnCheckbox(String adress) {
        List<WebElement> checkboxes = driver.findElements(By.xpath("//span[@title='" + adress + "']//../..//span[contains(@class, \"nb-checkbox-normal\")]"));
        for (WebElement checkBox : checkboxes) {
            checkBox.click();
        }
    }

    private List<String> findChekedLettersId() {
        List<WebElement> allCheckedLetters = driver.findElements(By.xpath("//div[contains(@class, 'is-checked')]"));
        List<String> chekedLettersId = new ArrayList<>();
        for (WebElement letId : allCheckedLetters) {
            String id = letId.getAttribute("data-id");
            chekedLettersId.add(id);
        }

        return chekedLettersId;
    }

    private void clickDelete() {
        driver.findElement(By.xpath("//div[contains(@title , \"(Delete)\")]")).click();
        try {
            if (driver.findElement(By.xpath("//button[contains(@class, \"theme_action js-confirm-mops\")]")).isDisplayed()) {
                driver.findElement(By.xpath("//button[contains(@class, \"theme_action js-confirm-mops\")]")).click();
            }
        } catch (Exception ignored) {
        }
        WebDriverWait wait = new WebDriverWait(driver, 2);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@title , \"(Delete)\")]")));
        } catch (Exception ignored) {
        }
    }

    //@BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://yandex.ru");
        driver.findElement(By.xpath("//div[contains(@class , \"card_login_yes\")]")).click();
        WebElement login = driver.findElement(By.xpath("//*[@id=\"passp-field-login\"]"));
        String loginName = "Seiron1";
        login.sendKeys(loginName);
        login.submit();
        String password = "210890-=";
        WebElement pass = driver.findElement(By.xpath("//*[@id=\"passp-field-passwd\"]"));
        pass.sendKeys(password);
        pass.submit();
        driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Name']"));

    }

    @Test(groups = {"SM-1"}) // Проверка при отправке корректного адреса письма
    public void sendMessage1() {
        setUp();
        moveToWriteMail();
        writeUserAdress("Seiron111@yandex.ru");
        sendMessage();
        Assert.assertNotNull(findElementMessageSendError());
    }

    @Test(groups = {"SM-2"}) // Проверка при отправке некорректного адреса письма
    public void sendMessage2() {
        setUp();
        moveToWriteMail();
        writeUserAdress("abcabc");
        sendMessage();
        Assert.assertNotNull(findElementMessageSendError());
    }

    @Test(groups = {"SM-3"}) // Проверка при отправке пустого адреса!!!!!
    public void sendMessage3() {
        setUp();
        moveToWriteMail();
        sendMessage();
        Assert.assertNotNull(findElementMessageSendError());
    }

    @Test(groups = {"SW-1"})   // с русского на инглиш
    public void switchLanguage1() {
        setUp();
        moveToSettings();
        switchToLanguage(Language.EN);
        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText().toLowerCase(), Language.EN.langName.toLowerCase());
    }

    @Test(groups = {"SW-2"})   // с инглиша на русский
    public void switchLanguage2() {
        setUp();
        moveToSettings();
        switchToLanguage(Language.RU);
        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText().toLowerCase(), Language.RU.langName.toLowerCase());
    }

    @Test(groups = {"DL-1"}) // Проверка при удалении кнопкой Удалить
    public void deleteMessage1() {
        setUp();
        int before = checkCounter();
        clickOnCheckbox("seiron1@yandex.ru");
        List<String> temp1 = findChekedLettersId();
        clickDelete();
        List<String> temp2 = checkAllLettersId();
        int after = checkCounter();
        assertDeleteLetters(temp1, temp2, before, after, "delete");
    }

    @Test(groups = {"DL-2"})     // проверка выделения без удаления
    public void deleteMessage2() {
        setUp();
        int before = checkCounter();
        clickOnCheckbox("seiron2@yandex.ru");
        List<String> temp1 = findChekedLettersId();
        List<String> temp2 = checkAllLettersId();
        int after = checkCounter();
        assertDeleteLetters(temp1, temp2, before, after, "nodelete");
    }

    @AfterMethod
    public void close() {
        driver.quit();
    }

    enum Language {
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
}