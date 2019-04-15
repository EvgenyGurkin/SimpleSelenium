package MailTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class YandexMailTest {
    private ChromeDriver driver;


    enum Language {
        EN("//a[@data-params='lang=en']"),
        RU("//a[@data-params='lang=ru']"),
        TR("//a[@data-params='lang=tr']"),
        TT("//a[@data-params='lang=tt']"),
        UK("//a[@data-params='lang=uk']"),
        AZ("//a[@data-params='lang=az']"),
        BE("//a[@data-params='lang=be']"),
        HY("//a[@data-params='lang=hy']"),
        KA("//a[@data-params='lang=ka']"),
        RO("//a[@data-params='lang=ro']"),
        KK("//a[@data-params='lang=kk']");

        private String path;

        private Language(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

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
        driver.findElement(By.xpath("//div[contains(@class , \"field-to-error\")]"));
        return driver;
    }


    private void moveToSettings() {
        driver.findElement(By.xpath("//*[@id=\"nb-3\"]")).click();  // шестеренка
        driver.findElement(By.xpath("//span[@class=\"settings-popup-title-content\"]")).click(); // Все настройки

    }

    private String checkArdessPosition() {
        return driver.getCurrentUrl();
    }

    private String checkLanguage() {
        WebElement mainLang = driver.findElement(By.xpath("//*[@id=\"nb-1\"]"));
        return mainLang.getAttribute("lang");
    }

    private void switchToLanguage(Language val) {
        driver.findElement(By.xpath("(//span[@class='b-selink__inner'])[1]")).click();
        try {
            if (driver.findElement(By.xpath(val.getPath())).isEnabled()) {
                driver.findElement(By.xpath(val.getPath())).click();
            }
        } catch (Exception ignored) {
        }
        analyzeLog();

    }
   // private void switchToLanguage() {
   //     if (!checkLanguage().equals("English")) {
   //         driver.findElement(By.xpath("(//span[@class='b-selink__inner'])[1]")).click(); // панель языка
   //         driver.findElement(By.xpath("(//div[@class='b-mail-dropdown__item b-mail-dropdown__item_with-icon b-mail-dropdown__item_simple'])[1]")).click();
   //     }
   // }


    private int numberOfLetters() {
        WebElement before = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String bee = before.getText();
        bee = bee.replaceAll("[^0-9]+", "");
        return Integer.parseInt(bee);
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
    }

    private void refreshSite() {
        driver.navigate().refresh();
    }

    public void analyzeLog() {
        LogEntries logEntries = driver.manage().logs().get(LogType.DRIVER);
        for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            //do something useful with the data
        }
    }

    @BeforeMethod
    public void setUp() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.DRIVER, Level.INFO);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(caps);
        //driver = new ChromeDriver();
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


  // @Test(groups = {"SM-1"}) // Проверка при отправке корректного адреса письма
  // public void sendMessage1() {
  //     //setUp();
  //     moveToWriteMail();
  //     writeUserAdress("Seiron1@yandex.ru");
  //     sendMessage();
  //     findElementMessageSend();
  //     Assert.assertNotNull(findElementMessageSend());
  // }

  // @Test(groups = {"SM-2"}) // Проверка при отправке некорректного адреса письма
  // public void sendMessage2() {
  //     //setUp();
  //     moveToWriteMail();
  //     writeUserAdress("abcabc");
  //     sendMessage();
  //     findElementMessageSendError();
  //     Assert.assertNotNull(findElementMessageSendError());
  // }

  // @Test(groups = {"SM-3"}) // Проверка при отправке пустого адреса!!!!!
  // public void sendMessage3() {
  //     //setUp();
  //     moveToWriteMail();
  //     sendMessage();
  //     findElementMessageSend();
  //     Assert.assertNotNull(findElementMessageSend());
  // }

   @Test(groups = {"SW-1"})   // с русского на инглиш
   public void switchLanguage1() {
       moveToSettings();
       checkLanguage();
       switchToLanguage(Language.EN);
       refreshSite();
       checkLanguage();
       Assert.assertEquals(checkLanguage(), "en");
   }
  @Test(groups = {"SW-2"})   // с инглиша на русский
   public void switchLanguage2() {
       moveToSettings();
       checkLanguage();
       switchToLanguage(Language.RU);
       refreshSite();
       checkLanguage();
      Assert.assertEquals(checkLanguage(), "ru");
   }


   // @Test(groups = {"DL-1"}) // Проверка при удалении кнопкой Удалить
   // public void deleteMessage1() {
   //     clickOnCheckbox("seiron1@yandex.ru");
   //     findChekedLettersId();
   //     clickDelete();
   //     refreshSite();
   //     checkAllLettersId();
   //     Assert.assertTrue(Collections.disjoint(checkAllLettersId(), findChekedLettersId()));
//
   // }
//
//
   // @Test(groups = {"DL-2"})
   // // Проверка при выборе чекбоксов при удалении мы должны понять что мы выбрали те чекбоксы которые выбрали!!!!
   // public void deleteMessage2() {
   //     clickOnCheckbox("seiron1@yandex.ru");
   //     findChekedLettersId();
   //     refreshSite();
   //     checkAllLettersId();
   //     Assert.assertTrue(checkAllLettersId().containsAll(findChekedLettersId()));
   // }


    @AfterMethod
    public void close() {
        driver.quit();
    }
}
