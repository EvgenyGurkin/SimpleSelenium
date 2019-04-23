package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface ToolbarBox extends ExtendedWebElement {
    @FindBy("//div[contains(@title , \"(Delete)\")]")
    @Description("Кнопка удаления письма")
    ExtendedWebElement deleteMailButton();

    @FindBy("(//span[@class='checkbox_view'])[1]")
    @Description("Чекбокс для выделения всего списка писем")
    ExtendedWebElement checkAllCheckbox();

    @FindBy("//*[@id=\"nb-1\"]/body/div[2]/div[6]/div/div[3]/div[3]/div[2]/div[2]/div[2]/div/a/span[2]")
    @Description("Чекбокс для для поднятия строки вверх")
    ExtendedWebElement upButton();
}
