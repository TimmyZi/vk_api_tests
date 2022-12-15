package vk.pages;

import aquality.selenium.forms.Form;
import lombok.Getter;
import org.openqa.selenium.By;
import vk.pages.forms.LeftSideMenuForm;

public class HomePage extends Form {
    private static final By wideColumnLocator = By.id("wide_column");
    @Getter
    private final LeftSideMenuForm leftSideMenuForm = new LeftSideMenuForm();

    public HomePage() {
        super(wideColumnLocator, "Home Page");
    }
}