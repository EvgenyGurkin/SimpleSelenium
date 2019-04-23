package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface DeleteWarning extends ExtendedWebElement {
    @FindBy("//button[contains(@class , \"js-confirm-mops\")]")
    @Description("Кнопка подтверждения удаления")
    ExtendedWebElement conformDeleteButton();
}
