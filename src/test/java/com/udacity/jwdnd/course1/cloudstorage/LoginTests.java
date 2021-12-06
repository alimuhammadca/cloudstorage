package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {
    public static final String LOGIN_ERROR_MESSAGE = "Invalid username or password";
    public static final String LOGOUT_MESSAGE = "You have been logged out";

    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private LoginPage loginPage;
    private SignupPage signupPage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        webDriver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        webDriver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(webDriver);
    }

    /**
     * This method tests if a user can login without
     * signing up
     */
    @Test
    public void unregisteredUserTest() {
        loginPage.setUsername("sa");
        loginPage.setPassword("123");
        loginPage.clickLogin();
        assertEquals(LOGIN_ERROR_MESSAGE, loginPage.getErrorMessage());
    }

    @Test
    public void registeredUserTest() {
        loginPage.setUsername("sa");
        loginPage.setPassword("123");
        loginPage.clickLogin();
        assertEquals(LOGIN_ERROR_MESSAGE, loginPage.getErrorMessage());
    }

}
