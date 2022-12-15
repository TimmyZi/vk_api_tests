package vk.pages.forms;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LeftSideMenuForm extends Form {
    private static final By leftSideMenuLocator = By.id("side_bar");
    private final ILink myPageLink = getElementFactory().getLink(By.id("l_pr"), "My Page Link");

    public LeftSideMenuForm() {
        super(leftSideMenuLocator, "Left Side Menu Form");
    }

    public void clickMyPageLink() {
        myPageLink.click();
    }
}