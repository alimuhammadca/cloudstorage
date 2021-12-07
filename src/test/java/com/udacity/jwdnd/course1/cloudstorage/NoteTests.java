package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private NotePage notePage;

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
    public void addNotePageTest() {
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
        //notePage = new NotePage(webDriver);
        //notePage.clickAddNoteButton();
        //notePage.setNoteTitle("title 1");
        //notePage.setNoteDescription("desc 1");
        //notePage.clickNoteSubmit();
        //assertEquals("title 1",notePage.getNoteTitle());
        //assertEquals("desc 1",notePage.getNoteDescription());
    }

}
