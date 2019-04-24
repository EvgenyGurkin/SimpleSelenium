package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface SettingsControl extends ExtendedWebElement {

    @FindBy(".//*[@id='nb-3']")
    @Description("Кнопка вызова списка настроек")
    ExtendedWebElement settingsGhostButton();
}
