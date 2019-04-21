package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedList;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface MessagesList extends ExtendedWebElement<MessagesList> {
    @FindBy("_nb-checkbox-flag _nb-checkbox-normal-flag")
    @Description("Список чекбоксов писем")
    ExtendedList<LangAndSettings> allMailCheckBoxes();

}
