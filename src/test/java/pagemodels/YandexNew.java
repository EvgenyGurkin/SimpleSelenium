package pagemodels;

import io.qameta.htmlelements.WebPageFactory;
import io.qameta.htmlelements.extension.page.BaseUrl;
import org.openqa.selenium.WebDriver;
import pages.Main;

@BaseUrl("https://yandex.ru/")
public class YandexNew {


    private WebPageFactory factory;
    private WebDriver driver;
    Main mainPage = factory.get(driver, Main.class);
    //public void initFactory() {
    //    this.driver = new ChromeDriver();
    //    this.factory = new WebPageFactory();


    public void quickLogin() {

        mainPage.go();
        mainPage.authorization().mailAuthorizationButton().click();
        mainPage.loginForm().loginField().sendKeys("Seiron1");
        mainPage.loginForm().loginFormButton().click();
        mainPage.passForm().passField().sendKeys("210890-=");
        mainPage.passForm().passFormButton().click();
    }


    public void goInSettings() {
        mainPage.settingsControl().settingsGhostButton().click();
        mainPage.allSettings().allSettingsButton().click();
    }

    public void switchLanguage() {
        mainPage.langAndSettings().langSettingsButton();
        mainPage.langAndSettings().allLangButtons();
    }

    public void goWriteLetter() {
        mainPage.leftMailPanel().goToWriteButton().click();
    }

    public void sendLetter(String mailArdess) {
        mainPage.adressAndTheme().mailAdressField().sendKeys(mailArdess);
        mainPage.composeHead().sendMessage().click();
    }

    public void deleteAllLetters() {
        mainPage.toolbarBox().checkAllCheckbox().click();
        mainPage.toolbarBox().deleteMailButton().click();
    }
}