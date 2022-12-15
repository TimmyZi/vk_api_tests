package vk.pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class PasswordPage extends Form {
    private static final By submitLocator = By.xpath("//button[contains(@type, 'submit')]");
    private final IButton submitButton = getElementFactory().getButton(submitLocator, "Submit Button");
    private final ITextBox inputPasswordTextBox = getElementFactory().getTextBox(By.name("password"), "Input Password Text Box");

    public PasswordPage() {
        super(submitLocator, "Id Page");
    }

    public void setPassword(String password) {
        inputPasswordTextBox.clearAndType(password);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }
}