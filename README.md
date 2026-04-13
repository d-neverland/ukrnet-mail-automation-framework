## Test scenario/flow

1. Login to the mail box.
2. Assert, that the login is successful.
3. Create a new mail (fill addressee, subject and body fields).
4. Save the mail as a draft.
5. Verify, that the mail presents in ‘Drafts’ folder.
6. Verify the draft content (addressee, subject and body – should be the same as in 3).
7. Send the mail.
8. Verify, that the mail disappeared from ‘Drafts’ folder.
9. Verify, that the mail is in ‘Sent’ folder.

## Framework task

To build test automation framework based on WebDriver + Java + TestNG  task from the [Module 08] WebDriver.

The framework should have:

- WebDriverManager for managing drivers for different browsers;
- PageObject / PageFactory for abstract pages;
- Necessary business model (business objects for dedicated entities);
- Property files with test data for different environments (at least 2);
- XML suites for Smoke and Regression tests;
- Possibility to make a screenshot in case of test failure; The log should have information about the saved screenshot in this case;
- Flexibility on different parameters e.g., browser, test suite, environment (this flexibility will help CI integration in future);
- Add logging of every step (with log4j or any similar lib) for your solution implemented based on previous modules:

### Logging requirements

- Configure logs format in informative way
- Demonstrate usage of different log levels (debug, action, error, etc)
- Configure ability to write logs in console and to save logs in a file (a new file should be created for each day). By default logs are written in console and are stored in file.
- Test results should present on job graphics, and screenshots should be archived as artifacts.
