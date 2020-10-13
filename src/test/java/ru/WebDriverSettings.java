package ru;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebDriverSettings {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public static void son(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeEach
    public void setupTest() throws MalformedURLException {
        String osName = System.getProperty("os.name");
        System.out.println(osName);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        //options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");

        //driver = new ChromeDriver(options);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");

        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        //driver = new FirefoxDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 10);

    }

    @AfterEach
    public void closeTest(){
        driver.quit();
    }

}
