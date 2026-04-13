package utils;

import models.EmailMessage;

public class MailTestData {
    private MailTestData() {
    }

    public static String uniqueSubject(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    public static EmailMessage buildMessage(String recipient, String subjectPrefix, String body) {
        return new EmailMessage(recipient, uniqueSubject(subjectPrefix), body);
    }
}
