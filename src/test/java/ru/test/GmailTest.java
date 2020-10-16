package ru.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import ru.WebDriverSettings;
import ru.google.PageGmail;
import ru.google.PageLogin;

public class GmailTest extends WebDriverSettings {
    @Test
    public void  enterGmail() {
        System.out.println(this.getClass().getName());
        String login = System.getProperty("login", "user");
        String password = System.getProperty("password", "password");
        String sender = System.getProperty("sender", "no-reply@accounts.google.com");
        String subject = "Тестовое задание. Васин Павел";

        PageLogin pageLogin = new PageLogin(driver, login, password);
        PageFactory.initElements(driver, pageLogin);
        pageLogin.loginGoogle();

        PageGmail pageGmail = PageFactory.initElements(driver, PageGmail.class);

        int count = pageGmail.getCountEmails("email", sender);
        System.out.println("Count:" + count);

        String body = "Count letters from " + sender + " is " + count;

        System.out.println("Email: " + sender);
        pageGmail.writeNewLetter(sender, subject, body);
    }
}
