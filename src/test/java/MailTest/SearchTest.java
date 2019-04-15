package MailTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class SearchTest {
    private ChromeDriver driver;

    private void writeRequest(String userRequest) {
        WebElement textField = driver.findElement(By.xpath("//*[@id=\"text\"]"));  // ячейка для адреса
        textField.sendKeys(userRequest);
    }

    private void sendRequest() {
        driver.findElement(By.xpath("//button[contains(@class , \"button suggest2\")]")).click(); // кнопка найти
    }

    private Object findMisspelMessage() {
        try {
            driver.findElement(By.xpath("//div[@class='misspell__message']"));
        } catch (NoSuchElementException exception) {
            driver.findElement(By.xpath("//div[@class='serp-adv__found']"));
        }
        return driver;
    }


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://yandex.ru");
    }


    @Test(groups = {"SS-1"})
    public void search() {
        writeRequest(".openqa.selenium.NoSuchElementException: no such element: Unable to locate element: {\\\"method\\\":\\\"xpath\\\",\\\"selector\\\":\\\"//div[@class='serp-adv__found']\\\"} (Session info: chrome=72.0.3626.121) (Driver info: chromedriver=2.36.540470 (e522d04694c7ebea4ba8821272dbef4f9b818c91),platform=Windows NT 10.0.17134 x86_64) (WARNING: The server did not provide any stacktrace information) Command duration or timeo\";");
        sendRequest();
        findMisspelMessage();
        Assert.assertNotNull(findMisspelMessage());

    }
}
