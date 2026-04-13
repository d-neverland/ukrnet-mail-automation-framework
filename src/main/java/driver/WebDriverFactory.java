package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    private static final String HEADLESS_ARG = "--headless";
    private static final String WINDOW_SIZE_ARG = "--window-size=1920,1080";

    public static WebDriver createDriver(Browser browser) {
        return createDriver(browser, false);
    }

    public static WebDriver createDriver(Browser browser, boolean headless) {
        return switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments(HEADLESS_ARG);
                }
                chromeOptions.addArguments(WINDOW_SIZE_ARG);
                yield new ChromeDriver(chromeOptions);
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments(HEADLESS_ARG);
                }
                firefoxOptions.addArguments(WINDOW_SIZE_ARG);
                yield new FirefoxDriver(firefoxOptions);
            }
        };
    }
}
