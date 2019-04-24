package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface Authorization extends ExtendedWebElement {
    @FindBy(".//div[contains(@class , 'card_login_yes')]")
    @Description("Кнопка для перехода в авторизацию почтового ящика")
    ExtendedWebElement mailAuthorizationButton();
}
