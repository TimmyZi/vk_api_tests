package vk.pages;

import aquality.selenium.forms.Form;
import lombok.Getter;
import org.openqa.selenium.By;
import vk.pages.forms.LeftSideMenuForm;
import vk.pages.forms.ProfileWallForm;

public class MyPage extends Form {
    private static final By profileLocator = By.id("profile_redesigned");
    @Getter
    private final LeftSideMenuForm leftSideMenuForm = new LeftSideMenuForm();
    @Getter
    private final ProfileWallForm profileWallForm = new ProfileWallForm();

    public MyPage() {
        super(profileLocator, "My Page");
    }
}