package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface ComposeHead extends ExtendedWebElement<ComposeHead> {
    @FindBy("//*[@id=\"nb-16\"]")
    @Description("Кнопка отправки письма")
    ExtendedWebElement sendMessage();
}
