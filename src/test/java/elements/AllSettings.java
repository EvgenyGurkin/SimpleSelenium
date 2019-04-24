package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface AllSettings extends ExtendedWebElement {

    @FindBy(".//span[@class='settings-popup-title-content']")
    @Description("Кнопка перехода в раздел настроек")
    ExtendedWebElement allSettingsButton();
}
