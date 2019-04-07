package MailTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;


import static java.util.concurrent.TimeUnit.SECONDS;


public  class DeleteMail {
    public ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "F:\\JAVA\\HomeStage\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
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

    @Test // Проверка при удалении клавишей
    public void deleteMessage1() {
        WebElement before = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String bee = before.getText();
        bee = bee.replaceAll("[^0-9]+", "");
         int be = Integer.parseInt (bee);
        driver.findElement(By.xpath("(//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'])[1]")).click(); // клик на  чекбокс. при смене последней цифры можно прокликать остальные чекбоксы
        driver.findElement(By.xpath("(//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'])[3]")).click();
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DELETE).build().perform();
        driver.get("https://mail.yandex.ru/#inbox");
        WebElement after = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String aft = after.getText();
        aft = aft.replaceAll("[^0-9]+", "");
        int af = Integer.parseInt (aft);
        Assert.assertFalse(be == af);
    }
    @Test // Проверка при удалении кнопкой Удалить
    public void deleteMessage2() {
        WebElement before = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String bee = before.getText();
        bee = bee.replaceAll("[^0-9]+", "");
        int be = Integer.parseInt (bee);
        driver.findElement(By.xpath("(//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'])[1]")).click(); // клик на  чекбокс. при смене последней цифры можно прокликать остальные чекбоксы
        driver.findElement(By.xpath("(//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'])[3]")).click();
        driver.findElement(By.xpath("//span[@class='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete']")).click();
        driver.get("https://mail.yandex.ru/#inbox");
        WebElement after = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String aft = after.getText();
        aft = aft.replaceAll("[^0-9]+", "");
        int af = Integer.parseInt (aft);
        Assert.assertFalse(be == af);
    }
    @Test // Проверка при удалении клавишей, а потом кнопкой Удалить
    public void deleteMessage3() {
        WebElement before = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String bee = before.getText();
        bee = bee.replaceAll("[^0-9]+", "");
        int be = Integer.parseInt (bee);
        driver.findElement(By.xpath("(//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'])[1]")).click(); // клик на  чекбокс. при смене последней цифры можно прокликать остальные чекбоксы
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DELETE).build().perform();
        driver.findElement(By.xpath("(//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'])[3]")).click();
        driver.findElement(By.xpath("//span[@class='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete']")).click();
        driver.get("https://mail.yandex.ru/#inbox");
        WebElement after = driver.findElement(By.xpath("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]"));
        String aft = after.getText();
        aft = aft.replaceAll("[^0-9]+", "");
        int af = Integer.parseInt (aft);
        Assert.assertFalse(be == af);
    }
}

   //@AfterMethod
   //public void close() {
   //    driver.quit();
   //}





//          (//span[@class='mail-NestedList-Item-Info-Extras'])[1]   - счетчик мейлов
//*[@id="nb-10"]/span[1]  - первый чекбокс
//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag'] - остальные чекбоксы
//span[@class='checkbox_view']  - чекбокс всех чекбоксов
//span[@class='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete'] - кнопка уолить
