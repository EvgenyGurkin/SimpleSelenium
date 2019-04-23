package elements;

        import io.qameta.htmlelements.annotation.Description;
        import io.qameta.htmlelements.annotation.FindBy;
        import io.qameta.htmlelements.annotation.Param;
        import io.qameta.htmlelements.element.ExtendedWebElement;


public interface LangAndSettings extends ExtendedWebElement {
    @FindBy("//span[@class='b-selink__link mail-Settings-Lang']")
    @Description("Кнопка раскрывающая список языков")
    ExtendedWebElement langSettingsButton();

    @Description("Кнопка выбранного языка")
    @FindBy("{lang}")
    ExtendedWebElement allLangButtons(@Param("lang") String pathForLang);
}
