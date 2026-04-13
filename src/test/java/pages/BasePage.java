package pages;

import config.FrameworkConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, FrameworkConfig.getDefaultTimeout());
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element, String elementName) {
        logger.info("[ACTION] Click '{}'", elementName);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(WebElement element, String text, String elementName) {
        logger.info("[ACTION] Type into '{}'", elementName);
        WebElement webElement = wait.until(ExpectedConditions.visibilityOf(element));
        webElement.clear();
        webElement.sendKeys(text);
        logger.debug("Entered {} characters into '{}'", text.length(), elementName);
    }
}

