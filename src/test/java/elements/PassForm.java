package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface PassForm extends ExtendedWebElement<PassForm> {
    @FindBy("//*[@id=\"passp-field-passwd\"]")
    @Description("Поле для ввода пароля авторизации")
    ExtendedWebElement passField();

    @FindBy("//button[contains(@class, \"passp-form-button\")]")
    @Description("Кнопка отправки пароля")
    ExtendedWebElement passFormButton();
}
