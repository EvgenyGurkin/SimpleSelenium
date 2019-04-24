package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface ToolbarBox extends ExtendedWebElement {
    @FindBy(".//div[contains(@title , '(Delete)')]")
    @Description("Кнопка удаления письма")
    ExtendedWebElement deleteMailButton();
}
