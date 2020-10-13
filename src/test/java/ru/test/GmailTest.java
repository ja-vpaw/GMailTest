package ru.test;

import org.junit.jupiter.api.Test;
import ru.WebDriverSettings;
import ru.google.PageGmail;

public class GmailTest extends WebDriverSettings {
    @Test
    public void  enterGmail() {
        System.out.println(this.getClass().getName());
        String sender = System.getProperty("sender", "Google");
        String subject = "Тестовое задание. Васин Павел";

        PageGmail page = new PageGmail(driver);

        page.loginGmail();

        //List<WebElement> inboxEmails = page.getEmails();

        int count = page.getCountEmails("name", sender);
        System.out.println("Count:" + count);

        son(1000);

        String body = "Count letters from " + sender + " is " + count;

        String mail = page.getEmailByName(sender);

        // Если найдены письма от sender
        if (count > 0) {
            System.out.println("Email:" + mail);
            page.writeNewLetter(mail, subject, body);
        }
    }
}
