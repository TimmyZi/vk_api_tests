package vk.pages.loginpage;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginForm extends Form {
    private static final By inputLoginLocator = By.id("index_email");
    private final ITextBox inputLoginTextBox = getElementFactory().getTextBox(inputLoginLocator, "Input Email Text Box");
    private final IButton signInButton = getElementFactory().getButton(By.xpath("//button[contains(@class, 'VkIdForm__signInButton')]"), "Sign In Button");

    public LoginForm() {
        super(inputLoginLocator, "Login Form");
    }

    public void setLogin(String login) {
        inputLoginTextBox.clearAndType(login);
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}