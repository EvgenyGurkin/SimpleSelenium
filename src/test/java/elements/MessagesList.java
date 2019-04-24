package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.annotation.Param;
import io.qameta.htmlelements.element.ExtendedList;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface MessagesList extends ExtendedWebElement<MessagesList> {
    @Description("Чекбоксы всех писем по адресу  {adress}")
    @FindBy(".//span[@title='{{ adress }}']//../..//span[contains(@class,'nb-checkbox-normal')]")
    ExtendedList<MessagesList> allMailCheckBoxes(@Param("adress") String adress);

    @Description("Все выделенные письма")
    @FindBy(".//div[contains(@class, 'is-checked')]")
    ExtendedList<MessagesList> allChekedMail();

    @Description("Все письма")
    @FindBy(".//div[contains(@class, 'item-wrap')]")
    ExtendedList<MessagesList> allMail();


}
