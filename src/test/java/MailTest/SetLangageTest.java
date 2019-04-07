package MailTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;



public  class SetLangageTest {
    public ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "F:\\JAVA\\HomeStage\\bin\\chromedriver.exe");
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
        driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Name js-folders-item-name']"));

    }
   @Test  // Проверка того что мы залогинлись
   public void signIn (){
       WebElement mail = driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Name js-folders-item-name']"));
       Assert.assertTrue(mail.isDisplayed());
   }
  @Test // Проверка того что мы вошли в настройки
   public void switchSettings() {
       driver.findElement(By.xpath("//*[@id=\"nb-3\"]")).click();  // шестеренка
       driver.findElement(By.xpath("//*[@id=\"settings-dropdown\"]/div/div/div/a/span")).click(); // Все настройки
       String settingsAdress = driver.getCurrentUrl();
       Assert.assertTrue(settingsAdress.contains("#setup"));
   }
   @Test   // с русского на инглиш
   public void switchLanguage1() {
       driver.findElement(By.xpath("//*[@id=\"nb-3\"]")).click();  // шестеренка
       driver.findElement(By.xpath("//*[@id=\"settings-dropdown\"]/div/div/div/a/span")).click(); // Все настройки
       driver.findElement(By.xpath("(//span[@class='b-selink__inner'])[1]")).click(); // панель языка
       WebElement element1 = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']"));
       String language1 = element1.getText();
       if (! language1.equals("English")) {
           driver.findElement(By.xpath("(//div[@class='b-mail-dropdown__item b-mail-dropdown__item_with-icon b-mail-dropdown__item_simple'])[1]")).click();
       }
       driver.get("https://mail.yandex.ru/#setup");
       WebElement element = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']"));
       String language = element.getText();
       Assert.assertEquals(language, "English" );
   }
    @Test   // с инглиша на русский
    public void switchLanguage2() {
        driver.findElement(By.xpath("//*[@id=\"nb-3\"]")).click();  // шестеренка
        driver.findElement(By.xpath("//*[@id=\"settings-dropdown\"]/div/div/div/a/span")).click(); // Все настройки
        driver.findElement(By.xpath("(//span[@class='b-selink__inner'])[1]")).click(); // панель языка
        WebElement element1 = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']"));
        String language1 = element1.getText();
        if (language1.equals("English")) {
            driver.findElement(By.xpath("(//div[@class='b-mail-dropdown__item b-mail-dropdown__item_with-icon b-mail-dropdown__item_simple'])[1]")).click();
        }
        driver.get("https://mail.yandex.ru/#setup");
        WebElement element = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']"));
        String language = element.getText();
        Assert.assertFalse(language.equals("English") );
    }
   @AfterMethod
   public void close() {
       driver.quit();
   }




}