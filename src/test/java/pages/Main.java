package pages;

import elements.*;
import io.qameta.htmlelements.WebPage;
import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.extension.page.BaseUrl;

@BaseUrl("https://yandex.ru/")
public interface Main extends WebPage {

    // блок авторизации

    @FindBy("//div[contains(@class , \"card_login_yes\")]")
    @Description("Блок авторизации")
    Authorization authorization();

    @FindBy("//div[@class='passp-login-form']")
    @Description("Форма для ввода логина")
    LoginForm loginForm();

    @FindBy("//div[@class='passp-password-form']")
    @Description("Форма для ввода пароля")
    PassForm passForm();

    // блок настроек
    @FindBy("//div[@data-key='view=head-settings-controls']")
    @Description("Панель настроек")
    SettingsControl settingsControl();

    @FindBy("//div[@class='settings-popup']")
    @Description("Блок с выбором настроек")
    AllSettings allSettings();

    @FindBy("//div[@class='b-setup-aside']")
    @Description("Блок настроек языка, времени и основных данных")
    LangAndSettings langAndSettings();

    // блок отправки письма
    @FindBy("//div[@class='mail-Layout-Aside js-layout-left ui-resizable mail-Layout-Aside_maximum']")
    @Description("Блок управления письмами")
    LeftMailPanel leftMailPanel();

    @FindBy("//div[@data-key='view=compose-head']")
    @Description("Панель отправки и информации об адресате")
    ComposeHead composeHead();

    @FindBy("//div[@data-key='view=compose-fields-wrapper']")
    @Description("Форма для ввода адресата и темы письма")
    AdressAndTheme adressAndTheme();

    @FindBy("//div[@class='mail-Done js-done']")
    @Description("Панель появляющаяся при успешной отправке письма")
    MessageSend messageSend();

    // блок писем

    @FindBy("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']")
    @Description("Список писем")
    MessagesList messagesList();

    @FindBy("//div[@data-key='box=toolbar-box']")
    @Description("Блок управления письмами")
    ToolbarBox toolbarBox();

    @FindBy("//div[contains(@class , \"tooltip___entities_[object Object] mail-Statusline\")]")
    @Description("Панель предупреждения при удалении")
    DeleteWarning DeleteWarning();


}



