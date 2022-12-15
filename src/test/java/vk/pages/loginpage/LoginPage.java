package vk.pages.loginpage;

import aquality.selenium.forms.Form;
import lombok.Getter;
import org.openqa.selenium.By;

public class LoginPage extends Form {
    private static final By loginFormLocator = By.id("index_rcolumn");
    @Getter
    private final LoginForm loginForm = new LoginForm();

    public LoginPage() {
        super(loginFormLocator, "Login Page");
    }
}