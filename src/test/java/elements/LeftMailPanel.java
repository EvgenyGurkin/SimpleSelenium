package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface LeftMailPanel extends ExtendedWebElement {
    @FindBy("//a[@class='mail-ComposeButton js-main-action-compose']")
    @Description("Кнопка перехода в раздел написания письма")
    ExtendedWebElement goToWriteButton();

    @FindBy("(//span[@class='mail-NestedList-Item-Info-Extras'])[1]")
    @Description("Счетчик количества писем")
    ExtendedWebElement extrasMailCounter();
}
