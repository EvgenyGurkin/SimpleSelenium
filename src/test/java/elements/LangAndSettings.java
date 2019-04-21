package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedList;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface LangAndSettings extends ExtendedWebElement<LangAndSettings> {
    @FindBy("//span[@class='b-selink__link mail-Settings-Lang']")
    @Description("Кнопка раскрывающая список языков")
    ExtendedWebElement langSettingsButton();

    @FindBy("//a[@class='b-mail-dropdown__item__content ns-action']")
    @Description("Список выбора языка")
    ExtendedList<LangAndSettings> allLangButtons();
}
