package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InboxPage extends BasePage{

    private static final String DRAFT_BY_SUBJECT_XPATH = "//span[@class='ftTyQ6mI' and normalize-space()='%s']";
    private static final String SENT_BY_SUBJECT_XPATH = "//a[contains(@href,'/sent/')][.//*[normalize-space()='%s']]";

    @FindBy(xpath = "//a[contains(@href,'inbox')]")
    private WebElement inboxLabel;

    @FindBy(xpath = "//a[contains(@href,'draft')]")
    private WebElement draftsFolder;

    @FindBy(xpath = "//a[contains(@href,'sent')]")
    private WebElement sentFolder;

    @FindBy(xpath = "//button[@type='button' and text()='Написати листа']")
    private WebElement composeButton;

    @FindBy(xpath = "//div[contains(@class,'p9AQfHXy')]//button[@type='button']")
    private WebElement accountMenuButton;

    @FindBy(xpath = "//button[@type='button' and normalize-space()='Вийти з акаунта']")
    private WebElement logoutButton;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginSuccessful() {
        try {
            logger.debug("Waiting until inbox page is opened");
            wait.until(ExpectedConditions.urlContains("/desktop/"));
            wait.until(ExpectedConditions.elementToBeClickable(composeButton));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickCompose() {
        click(composeButton, "compose button");
    }

    public void openDrafts() {
        click(draftsFolder, "drafts folder");
    }

    public void openSent() {
        click(sentFolder, "sent folder");
    }

    private By draftBySubject(String subject) {
        return By.xpath(DRAFT_BY_SUBJECT_XPATH.formatted(subject));
    }

    private By sentBySubject(String subject) {
        return By.xpath(SENT_BY_SUBJECT_XPATH.formatted(subject));
    }

    public void openDraftBySubject(String subject) {
        logger.info("[ACTION] Open draft by subject '{}'", subject);
        wait.until(ExpectedConditions.elementToBeClickable(draftBySubject(subject))).click();
    }

    public boolean isDraftPresent(String subject) {
        try {
            logger.debug("Checking that draft '{}' is present", subject);
            wait.until(ExpectedConditions.visibilityOfElementLocated(draftBySubject(subject)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isDraftAbsent(String subject) {
        try {
            logger.debug("Checking that draft '{}' is absent", subject);
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return shortWait.until(ExpectedConditions.invisibilityOfElementLocated(draftBySubject(subject)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isSentPresent(String subject) {
        try {
            logger.debug("Checking that sent email '{}' is present", subject);
            wait.until(ExpectedConditions.visibilityOfElementLocated(sentBySubject(subject)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void logout() {
        click(accountMenuButton, "account menu button");
        click(logoutButton, "logout button");
    }
}
