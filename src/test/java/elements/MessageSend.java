package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface MessageSend extends ExtendedWebElement {
    @FindBy(".//div[@class='mail-Done-Title js-title-info']")
    @Description("Сообщение об успешной отправке письма")
    ExtendedWebElement succesMailSend();
}
