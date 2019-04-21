package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface AdressAndTheme extends ExtendedWebElement<AdressAndTheme> {
    @FindBy("(//div[@class='mail-Compose-Field-Input'])[1]")
    @Description("Поле для ввода адреса")
    ExtendedWebElement mailAdressField();
}
