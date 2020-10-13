package ru.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.WebDriverSettings;
import ru.google.PageGmail;

import java.util.List;

public class Gmail extends WebDriverSettings {
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

        String mail = page.getEmailByName(sender);
        //String mail = "vpaw6@ya.ru";

        String body = "Count letters from " + sender + " is " + count;

        System.out.println("Email:" + mail);

        page.writeNewLetter(mail, subject, body);
    }
}
