package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface LoginForm extends ExtendedWebElement {
    @FindBy("//*[@id=\"passp-field-login\"]")
    @Description("Поле для логина авторизации")
    ExtendedWebElement loginField();

    @FindBy("//button[contains(@class, \"passp-form-button\")]")
    @Description("Кнопка отправки логина")
    ExtendedWebElement loginFormButton();
}
