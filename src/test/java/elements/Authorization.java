package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface Authorization extends ExtendedWebElement<Authorization> {
    @FindBy("//a[contains(@class , \"button_js_inited\")]")
    @Description("Кнопка для перехода в авторизацию почтового ящика")
    ExtendedWebElement mailAuthorizationButton();
}
