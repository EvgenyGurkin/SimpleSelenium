package MailTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.testng.TestNG;
import java.util.concurrent.TimeUnit;


public class YandexMailTest {
    public ChromeDriver driver;


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

    private Object findElementMessageSend() {
        driver.findElement(By.xpath("//div[@class='mail-Done-Title js-title-info']"));
        return driver;
    }

    private Object findElementMessageSendError() {
        driver.findElement(By.xpath("//*[@id=\"nb-1\"]/body/div[2]/div[5]/div/div[3]/div[3]/div[2]/div[5]/div/div[1]/div[2]/div[1]/div/div[2]/div/div/div/div"));
        return driver;
    }

    private Object findElementInMail() {
        driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Name']"));
        return driver;
    }

    private void moveToSettings() {
        driver.findElement(By.xpath("//*[@id=\"nb-3\"]")).click();  // шестеренка
        driver.findElement(By.xpath("//*[@id=\"settings-dropdown\"]/div/div/div/a/span")).click(); // Все настройки

    }

    private String checkArdessPosition() {
        String adressPosition = driver.getCurrentUrl();
        return adressPosition;
    }

    private String checkLanguage() {
        WebElement element = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']"));
        String language = element.getText();
        return language;
    }

    private void switchToEng() {
        if (!checkLanguage().equals("English")) {
            driver.findElement(By.xpath("(//span[@class='b-selink__inner'])[1]")).click(); // панель языка
            driver.findElement(By.xpath("(//div[@class='b-mail-dropdown__item b-mail-dropdown__item_with-icon b-mail-dropdown__item_simple'])[1]")).click();
        }
    }

    private void switchToRus() {
        if (checkLanguage().equals("English")) {
            driver.findElement(By.xpath("(//span[@class='b-selink__inner'])[1]")).click(); // панель языка
            driver.findElement(By.xpath("(//div[@class='b-mail-dropdown__item b-mail-dropdown__item_with-icon b-mail-dropdown__item_simple'])[1]")).click();
        }
    }
    private int numberOfLetters () {
        WebElement before = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String bee = before.getText();
        bee = bee.replaceAll("[^0-9]+", "");
        int be = Integer.parseInt(bee);
        return be;
    }

    private void clickOnCheckbox (int item) {
        WebElement checkBox = driver.findElement(By.xpath("(//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'])["+item+"]"));
        checkBox.click();
    }
    private void clickDelete () {
        driver.findElement(By.xpath("//span[@class='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete']")).click();
    }
    private void pressDelete () {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DELETE).build().perform();
    }
    private void refreshSite () {
        driver.navigate().refresh();
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://yandex.ru");
        driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div/div[1]/div/a[1]")).click();
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

    @Test(groups = {"DL-1"})  // Проверка того что мы залогинлись

    public void signIn() {
        findElementInMail();
        Assert.assertNotNull(findElementInMail());
    }


    @Test(groups = {"SM-1"}) // Проверка того что мы вошли в отправку писем
    public void toSendMessage() {
        moveToWriteMail();
        checkArdessPosition();
        Assert.assertTrue(checkArdessPosition().contains("#compose"));
    }

    @Test(groups = {"SM-2"}) // Проверка при отправке корректного адреса письма
    public void sendMessage1() {
        moveToWriteMail();
        writeUserAdress("Seiron1@yandex.ru");
        sendMessage();
        findElementMessageSend();
        Assert.assertNotNull(findElementMessageSend());
    }

    @Test(groups = {"SM-3"}) // Проверка при отправке некорректного адреса письма
    public void sendMessage2() {
        moveToWriteMail();
        writeUserAdress("abcabc");
        sendMessage();
        findElementMessageSendError();
        Assert.assertNotNull(findElementMessageSendError());
    }

    @Test(groups = {"SW-1"}) // Проверка того что мы вошли в настройки
    public void switchSettings() {
        moveToSettings();
        checkArdessPosition();
        Assert.assertTrue(checkArdessPosition().contains("#setup"));
    }

    @Test(groups = {"SW-2"})   // с русского на инглиш
    public void switchLanguage1() {
        moveToSettings();
        checkLanguage();
        switchToEng();
        refreshSite();
        checkLanguage();
        Assert.assertEquals(checkLanguage(), "English");
    }

    @Test(groups = {"SW-3"})   // с инглиша на русский
    public void switchLanguage2() {
        moveToSettings();
        checkLanguage();
        switchToRus();
        refreshSite();
        checkLanguage();
        Assert.assertFalse(checkLanguage().equals("English"));
    }

    @Test(groups = {"DL-2"}) // Проверка при удалении клавишей
    public void deleteMessage1() {
        int be = numberOfLetters ();
        clickOnCheckbox(2);
        clickOnCheckbox(4);
        pressDelete();
        refreshSite();
        int af = numberOfLetters ();
        Assert.assertFalse(be == af);
    }

    @Test(groups = {"DL-2"}) // Проверка при удалении кнопкой Удалить
    public void deleteMessage2() {
        int be = numberOfLetters ();
        clickOnCheckbox(2);
        clickOnCheckbox(4);
        clickDelete();
        refreshSite();
        int af = numberOfLetters ();
        Assert.assertFalse(be == af);
    }
    @Test(groups = {"DL-3"}) // Проверка при удалении клавишей, а потом кнопкой Удалить
    public void deleteMessage3() {
        int be = numberOfLetters ();
        clickOnCheckbox(2);
        pressDelete();
        clickOnCheckbox(3);
        clickDelete();
        refreshSite();
        int af = numberOfLetters ();
        Assert.assertFalse(be == af);
    }
    @AfterMethod
    public void close() {
        driver.quit();
    }
}
