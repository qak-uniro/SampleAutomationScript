package com.uniroPaints.ERP.tests.auth;

import com.uniroPaints.ERP.tests.base.BaseTest;
import com.uniroPaints.ERP.utils.PropertyFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    PropertyFileReader prop = new PropertyFileReader();
    String userNameElement = prop.getProperty("login","user.name.element");
    String passwordElement = prop.getProperty("login","password.element");
    String loginButtonElement = prop.getProperty("login","login.button.element");
    String salutationMessageElement = prop.getProperty("home","salutation.message.element");


    @Test(priority = 1)
    public void testLogin() {

        waitUntilNextElement(By.xpath("//h1[normalize-space()='Login']"), 10);

        enterText(userNameElement, "admin");
        enterText(passwordElement, "1234");
        clickElement(loginButtonElement);

        // Wait for successful login confirmation
        waitUntilNextElement(By.xpath(salutationMessageElement), 15);
    }

    private WebElement waitUntilNextElement(By locator, int maxTimeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTimeOut));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void enterText(String xpath, String text) {
        WebElement element = waitUntilNextElement(By.xpath(xpath), 10);
        element.clear();
        element.sendKeys(text);
    }

    private void clickElement(String xpath) {
        WebElement element = waitUntilNextElement(By.xpath(xpath), 10);
        element.click();
    }
    public WebDriver getDriver() {
        return driver;
    }
}