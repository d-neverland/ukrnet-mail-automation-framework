package pages;

import models.EmailMessage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ComposePage extends BasePage {

    @FindBy(id = "compose-to")
    private WebElement receiverField;

    @FindBy(id = "compose-subject")
    private WebElement subjectField;

    @FindBy(id = "tinymce")
    private WebElement bodyField;

    @FindBy(xpath = "//button[normalize-space()='Надіслати']")
    private WebElement sendButton;

    public ComposePage(WebDriver driver) {
        super(driver);
    }

    public void fillEmail(String to, String subject, String body) {
        logger.info("[ACTION] Fill email for recipient '{}' with subject '{}'", to, subject);
        WebElement toField = wait.until(ExpectedConditions.elementToBeClickable(receiverField));
        toField.click();
        toField.sendKeys(to);
        toField.sendKeys(Keys.TAB);

        WebElement subjField = wait.until(ExpectedConditions.elementToBeClickable(subjectField));
        subjField.click();
        subjField.clear();
        subjField.sendKeys(subject);

        wait.until(ExpectedConditions.attributeToBe(subjectField, "value", subject));

        fillBody(body);
    }

    public void fillEmail(EmailMessage emailMessage) {
        fillEmail(emailMessage.recipient(), emailMessage.subject(), emailMessage.body());
    }

    public void sendEmail() {
        click(sendButton, "send button");
    }

    private void fillBody(String body) {
        logger.info("[ACTION] Fill email body");
        switchToEditorFrame();

        WebElement bodyField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("tinymce"))
        );

        bodyField.click();
        bodyField.sendKeys(body);

        driver.switchTo().defaultContent();
    }

    private void switchToEditorFrame() {
        logger.debug("Switching to compose editor iframe");
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        for (int i = 0; i < iframes.size(); i++) {
            driver.switchTo().defaultContent();
            driver.switchTo().frame(i);

            if (!driver.findElements(By.id("tinymce")).isEmpty()) {
                return;
            }
        }

        driver.switchTo().defaultContent();
        throw new NoSuchElementException("Could not find iframe containing body with id='tinymce'");
    }
}

