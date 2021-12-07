package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.SignupTests.SIGNUP_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizedLoginTests {
    public static final String LOGIN_ERROR_MESSAGE = "Invalid username or password";
    public static final String LOGOUT_MESSAGE = "You have been logged out";

    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private HomePage homePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        webDriver.quit();
    }

    @Test
    @Order(1)
    public void userLoginTest() {
        webDriver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(webDriver);
        signupPage.setFirstName("John");
        signupPage.setLastName("Doe");
        signupPage.setUsername("sa");
        signupPage.setPassword("123");
        signupPage.clickSignup();
        webDriver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(webDriver);
        loginPage.setUsername("sa");
        loginPage.setPassword("123");
        loginPage.clickLogin();
        assertEquals("Home", webDriver.getTitle());
    }

    @Test
    @Order(2)
    public void userLogoutTest() {
        webDriver.findElement(By.id("logoutButton")).click();
        assertEquals("Login", webDriver.getTitle());
    }

    @Test
    @Order(3)
    public void userHomeTest() {
        webDriver.get("http://localhost:" + port + "/home");
        assertEquals("Login", webDriver.getTitle());
    }

}
