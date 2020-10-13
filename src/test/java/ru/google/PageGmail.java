package ru.google;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageGmail {

    public WebDriver driver;
    public WebDriverWait wait;

    protected final String login = System.getProperty("login", "user");
    protected final String password = System.getProperty("password", "password");

    private String selectorNewLetterButton = "//*[@role='button' and contains(@class, 'T-I-KE')]";
    private String selectorMaximizeNewLetterWindow = "//td//img[2]";
    private String selectorSendToNewLetter = "//*[@name='to']";
    private String selectorSubjectNewLetter = "//*[@name = 'subjectbox']";
    private String selectorBodyNewLetter = "//*[@role = 'textbox']";
    private String selectorSendButtonNewLetter = "//*[@role='button' and contains(@aria-label, 'Enter')]";

    private void son(int s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Отправка нового письма на адрес {mail} с темой {subject} текстом {body}")
    public void writeNewLetter(String mail, String subject, String body) {
        WebElement composeElement = wait.until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath(selectorNewLetterButton)));
        composeElement.click();
        WebElement maximizeEmailElement = wait.until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath(selectorMaximizeNewLetterWindow)));
        maximizeEmailElement.click();
        WebElement sendToElement = wait.until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath(selectorSendToNewLetter)));
        sendToElement.click();
        sendToElement.clear();
        sendToElement.sendKeys(mail);

        WebElement subjectElement = wait.until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath(selectorSubjectNewLetter)));
        subjectElement.click();
        subjectElement.clear();
        subjectElement.sendKeys(subject);

        WebElement emailBodyElement = wait.until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath(selectorBodyNewLetter)));
        emailBodyElement.click();
        emailBodyElement.clear();
        emailBodyElement.sendKeys(body);

        son(5000);

        WebElement sendMailElement = wait.until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath(selectorSendButtonNewLetter)));
        sendMailElement.click();
        son(5000);
    }

    public PageGmail(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, 10);
    }

    public List<WebElement> getEmails() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(
            driver.findElements(By.xpath("//*[@role='row']"))));
    }

    @Step("Получение количества писем по {attribute} от {value}")
    public Integer getCountEmails(String attribute, String value) {
        int count = 0;
        for (WebElement email : getEmails()) {
            if (email.isDisplayed() &&
                email.findElement(By.xpath(".//div[@class='afn']//span[@email]")).getAttribute(attribute).equals(value))
            {
                count++;
            }
        }
        return count;
    }

    @Step("Получение email по имени отправителя {name}")
    public String getEmailByName(String name) {
        return driver.findElement(By.xpath("//*[@role='row']//div[@class='afn']//span[@name='"+ name +"']"))
                    .getAttribute("email");
    }

    @Step("Логин в Gmail")
    public void loginGmail() {
        driver.get("https://accounts.google.com/signin/oauth/identifier?client_id=717762328687-iludtf96g1hinl76e4lc1b9a82g457nn.apps.googleusercontent.com&scope=profile%20email&redirect_uri=https%3A%2F%2Fstackauth.com%2Fauth%2Foauth2%2Fgoogle&state=%7B%22sid%22%3A1%2C%22st%22%3A%2259%3A3%3ABBC%2C16%3A9b15b0994c6df9fc%2C10%3A1591711286%2C16%3A66b338ce162d6599%2Ca78a0c663f0beb12c0559379b61a9f5d62868c4fbd2f00e46a86ac26796507a1%22%2C%22cdl%22%3Anull%2C%22cid%22%3A%22717762328687-iludtf96g1hinl76e4lc1b9a82g457nn.apps.googleusercontent.com%22%2C%22k%22%3A%22Google%22%2C%22ses%22%3A%22921f8f04441041069683cc2377152422%22%7D&response_type=code&o2v=1&as=NCQvtBXI4prkLLDbn4Re0w&flowName=GeneralOAuthFlow");
        WebElement userElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("identifierId")));
        userElement.click();
        userElement.clear();
        userElement.sendKeys(login);

        WebElement identifierNext = wait.until(ExpectedConditions.elementToBeClickable(By.id("identifierNext")));
        identifierNext.click();

        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"password\"]//input")));
        passwordElement.click();
        passwordElement.clear();
        passwordElement.sendKeys(password);

        WebElement passwordNext = wait.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext")));
        passwordNext.click();

        son(20000);

        driver.get("https://mail.google.com/");
    }
}
