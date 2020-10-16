package ru.google;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageLogin {

    private String url = "https://accounts.google.com/";

    protected final String login;
    protected final String password;

    public WebDriver driver;
    public WebDriverWait wait;

    @FindBy(id = "identifierId")
    WebElement identifierInput;

    @FindBy(id = "identifierNext")
    WebElement identifierNext;

    @FindBy(xpath = "//*[@id='password']//input")
    WebElement passwordInput;

    @FindBy(id = "passwordNext")
    WebElement passwordNext;

    public PageLogin(WebDriver driver, String login, String password){
        this.login = login;
        this.password = password;
        this.driver=driver;
        wait = new WebDriverWait(driver, 10);
        System.out.println(driver.getTitle());
        if(!driver.getTitle().contains("Google"))
            driver.get(url);
    }

    @Step("Логин в Google")
    public void loginGoogle(){
        identifierInput.click();
        identifierInput.sendKeys(login);

        identifierNext.click();

        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.click();
        passwordInput.sendKeys(password);

        passwordNext.click();

        driver.get("https://www.google.com/");
    }
}
