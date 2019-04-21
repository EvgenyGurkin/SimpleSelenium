package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface ToolbarBox extends ExtendedWebElement<ToolbarBox> {
    @FindBy("//div[contains(@title , \"(Delete)\")]")
    @Description("Кнопка удаления письма")
    ExtendedWebElement deleteMailButton();

    @FindBy("(//span[@class='checkbox_view'])[1]")
    @Description("Чекбокс для выделения всего списка писем")
    ExtendedWebElement checkAllCheckbox();

}
