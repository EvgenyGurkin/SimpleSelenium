package MailTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;



public  class SendMail {
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
    @Test // Проверка того что мы вошли в отправку писем
    public void toSendMessage (){
        driver.findElement(By.xpath("//a[@class='mail-ComposeButton js-main-action-compose']")).click(); //кнопка написать
        String settingsAdress = driver.getCurrentUrl();
        Assert.assertTrue(settingsAdress.contains("#compose"));
    }
    @Test // Проверка при корректном адресе письма
    public void sendMessage1 () {
        driver.findElement(By.xpath("//a[@class='mail-ComposeButton js-main-action-compose']")).click(); //кнопка написать
        WebElement mailField = driver.findElement(By.xpath("//div[@class='js-compose-field mail-Bubbles']"));  // ячейка для адреса
        String userAdress = "Seiron1@yandex.ru";
        mailField.sendKeys(userAdress);
        driver.findElement(By.xpath("//*[@id=\"nb-16\"]")).click(); // кнопка отправить
        WebElement done = driver.findElement(By.xpath("//div[@class='mail-Done-Title js-title-info']"));  // оповещение об успешной отправке
        Assert.assertTrue(done.isDisplayed());
    }
    @Test // Проверка при некорректном адресе письма
    public void sendMessage2 () {
        driver.findElement(By.xpath("//a[@class='mail-ComposeButton js-main-action-compose']")).click(); //кнопка написать
        WebElement mailField = driver.findElement(By.xpath("//div[@class='js-compose-field mail-Bubbles']"));  // ячейка для адреса
        String userAdress = "asdasd";
        mailField.sendKeys(userAdress);
        driver.findElement(By.xpath("//*[@id=\"nb-16\"]")).click(); // кнопка отправить
        WebElement error = driver.findElement(By.xpath("//*[@id=\"nb-1\"]/body/div[2]/div[5]/div/div[3]/div[3]/div[2]/div[5]/div/div[1]/div[2]/div[1]/div/div[2]/div/div/div/div"));  // оповещение о некорректном адресе
        Assert.assertTrue(error.isDisplayed());
    }
    //@Test // Проверка при отсутствии адреса письма
    //public void sendMessage3 () {
    //    driver.findElement(By.xpath("//a[@class='mail-ComposeButton js-main-action-compose']")).click(); //кнопка написать
    //    driver.findElement(By.xpath("//*[@id=\"nb-16\"]")).click(); // кнопка отправить
    //    WebElement error0 = driver.findElement(By.xpath("//*[@id=\"nb-1\"]/body/div[2]/div[5]/div/div[3]/div[3]/div[2]/div[5]/div/div[1]/div[2]/div[1]/div/div[2]/div/div/div/div"));  // оповещение об успешной отправке
    //    error0.getText();
    //    Assert.assertEquals(error0, "Поле не заполнено. Необходимо ввести адрес.");
    //}
    @AfterMethod
    public void close() {
        driver.quit();
    }
}