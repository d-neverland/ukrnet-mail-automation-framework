package tests;

import config.FrameworkConfig;
import models.EmailMessage;
import models.UserCredentials;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ComposePage;
import pages.InboxPage;
import pages.LoginPage;
import utils.MailTestData;

public class MailWorkflowTest extends BaseTest {

    private final UserCredentials userCredentials = FrameworkConfig.getUserCredentials();
    private final String recipient = FrameworkConfig.getDefaultRecipient();

    @BeforeMethod(alwaysRun = true)
    public void testSetUp(ITestContext context) {
        super.setUp(context);
    }

    @AfterMethod(alwaysRun = true)
    public void testTearDown() {
        super.tearDown();
    }

    @Test(groups = {"smoke", "regression"})
    public void mailFlowTest() {
        EmailMessage emailMessage = MailTestData.buildMessage(
                recipient,
                "Plain text ",
                "Simple body text"
        );

        verifyDraftAndSentFlow(emailMessage);
    }

    @Test(groups = {"regression"})
    public void sendMailWithLongBodyDraft() {
        EmailMessage emailMessage = MailTestData.buildMessage(
                recipient,
                "Long body ",
                "This is a longer body text for verification of draft save and send flow."
        );

        verifyDraftAndSentFlow(emailMessage);
    }

    @Test(groups = {"regression"})
    public void sendMailWithSpecialCharactersDraft() {
        EmailMessage emailMessage = MailTestData.buildMessage(
                recipient,
                "Special chars !@#$% ",
                "Body with digits 123 and symbols !@#"
        );

        verifyDraftAndSentFlow(emailMessage);
    }

    private void verifyDraftAndSentFlow(EmailMessage emailMessage) {
        LoginPage loginPage = new LoginPage(driver);
        InboxPage inboxPage = new InboxPage(driver);
        ComposePage composePage = new ComposePage(driver);

        loginPage.login(userCredentials);
        Assert.assertTrue(inboxPage.isLoginSuccessful(), "Login failed");

        inboxPage.clickCompose();
        composePage.fillEmail(emailMessage);

        inboxPage.openDrafts();
        Assert.assertTrue(
                inboxPage.isDraftPresent(emailMessage.subject()),
                "Draft is not present in Drafts folder"
        );

        inboxPage.openDraftBySubject(emailMessage.subject());
        composePage.sendEmail();

        inboxPage.openDrafts();
        Assert.assertTrue(
                inboxPage.isDraftAbsent(emailMessage.subject()),
                "Draft is still present in Drafts folder after sending"
        );

        inboxPage.openSent();
        Assert.assertTrue(
                inboxPage.isSentPresent(emailMessage.subject()),
                "Email is not present in Sent folder"
        );
    }
}
