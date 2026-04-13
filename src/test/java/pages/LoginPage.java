package pages;

import models.UserCredentials;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "id-text-field-0")
    private WebElement loginInput;
    @FindBy(id = "id-text-field-1")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        logger.info("[ACTION] Authorize user '{}'", username);
        type(loginInput, username, "login input");
        type(passwordInput, password, "password input");
        click(loginButton, "login button");
    }

    public void login(UserCredentials userCredentials) {
        login(userCredentials.email(), userCredentials.password());
    }
}
