package ru.google;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageGmail {

    private String url = "https://mail.google.com/";

    public WebDriver driver;
    public WebDriverWait wait;

    private String selectorGetEmailBySender = "//*[@role='row']//div[@class='afn']//span[@name='%s']";

    @FindBy(xpath = "//*[@role='button' and contains(@class, 'T-I-KE')]")
    WebElement newLetterButton;

    @FindBy(xpath = "//td//img[2]")
    WebElement maximizeNewLetterWindow;

    @FindBy(xpath = "//*[@name='to']")
    WebElement sendToNewLetter;

    @FindBy(xpath = "//*[@name = 'subjectbox']")
    WebElement subjectNewLetter;

    @FindBy(xpath = "//*[@role = 'textbox']")
    WebElement bodyNewLetter;

    @FindBy(xpath = "//*[@role='button' and contains(@aria-label, 'Enter')]")
    WebElement sendButtonNewLetter;

    @FindBy(xpath = "//*[@role='alert' and @style]//span[contains(text(),'Письмо отправлено')]")
    WebElement sendMessageSuccess;

    @FindAll(@FindBy(xpath = "//*[@role='row']//div[@class='afn']//span[@email]"))
    List<WebElement> listSenderNames;

    @Step("Отправка нового письма на адрес {mail} с темой {subject} текстом {body}")
    public void writeNewLetter(String mail, String subject, String body) {
        newLetterButton.click();
        maximizeNewLetterWindow.click();
        sendToNewLetter.click();
        sendToNewLetter.sendKeys(mail);

        subjectNewLetter.click();
        subjectNewLetter.sendKeys(subject);

        bodyNewLetter.click();
        bodyNewLetter.sendKeys(body);

        sendButtonNewLetter.click();

        wait.until(ExpectedConditions.visibilityOf(sendMessageSuccess));
    }

    public PageGmail(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, 10);
        driver.get(url);
    }

    @Step("Получение количества писем по {attribute} от {value}")
    public Integer getCountEmails(String attribute, String value) {
        int count = 0;
        for (WebElement email : listSenderNames) {
            if (email.getAttribute(attribute).equals(value)) {
                count++;
            }
        }
        return count;
    }

    @Step("Получение email по имени отправителя {name}")
    public String getEmailByName(String name) {
        String email = null;
        try {
            email = driver.findElement(By.xpath(String.format(selectorGetEmailBySender, name)))
                    .getAttribute("email");
        } catch (NoSuchElementException e) {
            System.out.println("Получатель " + name + " не найден. ");
        }

        return email;
    }
}
