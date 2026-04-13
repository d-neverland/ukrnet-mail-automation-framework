package tests;

import config.FrameworkConfig;
import driver.Browser;
import driver.WebDriverFactory;
import listeners.TestExecutionListener;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import utils.DriverHolder;

@Listeners(TestExecutionListener.class)
public abstract class BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void configureLogging() {
        java.util.logging.LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LOGGER.debug("SLF4J bridge for java.util.logging is configured");
    }

    protected WebDriver driver;

    @BeforeMethod
    public void setUp(ITestContext context) {
        String browserName = context.getCurrentXmlTest().getParameter("browser");
        String resolvedBrowserName = FrameworkConfig.getBrowserName(browserName);
        Browser browser = Browser.getByNameIgnoreCase(resolvedBrowserName);

        LOGGER.info(
                "[ACTION] Preparing driver for browser='{}', env='{}', headless={}",
                browser,
                FrameworkConfig.getEnvironment(),
                FrameworkConfig.isHeadless()
        );

        driver = WebDriverFactory.createDriver(browser, FrameworkConfig.isHeadless());
        DriverHolder.setDriver(driver);
        driver.manage().window().maximize();
        driver.get(FrameworkConfig.getBaseUrl());
        LOGGER.debug("Opened base URL {}", FrameworkConfig.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            LOGGER.info("[ACTION] Closing browser session");
            driver.quit();
        }
        DriverHolder.removeDriver();
    }
}
